package com.athulraju.springsecclient.service;

import com.athulraju.springsecclient.entity.PasswordVerificationToken;
import com.athulraju.springsecclient.entity.User;
import com.athulraju.springsecclient.entity.VerificationToken;
import com.athulraju.springsecclient.model.UserModel;
import com.athulraju.springsecclient.repository.PasswordTokenRepository;
import com.athulraju.springsecclient.repository.UserRepository;
import com.athulraju.springsecclient.repository.VerifcationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerifcationTokenRepository verifcationTokenRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private PasswordTokenRepository passwordTokenRepository;
    @Override
    public User registerUser(UserModel userModel) {
         User user=new User();
         user.setEmailId(userModel.getEmailId());
         user.setPassword(encoder.encode(userModel.getPassword()));
         user.setFirstName(userModel.getFirstName());
         user.setLastName(userModel.getLastName());
         user.setRole("USER");
         userRepository.save(user);
         return user;
    }

    @Override
    public void saveVerificationToken(User user, String token) {
        VerificationToken verificationToken=new VerificationToken(user,token);
        verifcationTokenRepository.save(verificationToken);

    }

    @Override
    public String validateToke(String token) {
        VerificationToken verificationToken=verifcationTokenRepository.findBytoken(token);
        if(verificationToken==null){
            return "invalid";
        }
        User user=verificationToken.getUser();
        Calendar cal=Calendar.getInstance();

        if((verificationToken.getExpirationTime().getTime()-cal.getTime().getTime())<=0){
             verifcationTokenRepository.delete(verificationToken);
             return "expired";
        }

        user.setEnabled(true);
        userRepository.save(user);
        return "valid";
    }

    @Override
    public VerificationToken generateVerificationToken(String oldToken) {

        VerificationToken verificationToken=verifcationTokenRepository.findBytoken(oldToken);
        verificationToken.setToken(UUID.randomUUID().toString());
        verifcationTokenRepository.save(verificationToken);
        return verificationToken;
    }

    @Override
    public User getUserPasswdByToken(String emailId) {
        return userRepository.findByEmailId(emailId);

    }

    @Override
    public void createNewPasswdToken(User user, String token) {
        PasswordVerificationToken passwordVerificationToken=new PasswordVerificationToken(user,token);
        passwordTokenRepository.save(passwordVerificationToken);
    }

    @Override
    public String validatePassword(String token) {
       PasswordVerificationToken passwordVerificationToken =passwordTokenRepository.findByToken(token);
       if(passwordVerificationToken==null){
           return "invalid token";
       }

       User user=passwordVerificationToken.getUser();

       Calendar cal=Calendar.getInstance();

       if((passwordVerificationToken.getExpirationTime().getTime()-cal.getTime().getTime())<=0){
           passwordTokenRepository.delete(passwordVerificationToken);
            return "expired";
        }


        return "valid";
    }

    @Override
    public Optional<User> getUserByPasswordByToken(String token) {
        return Optional.ofNullable(passwordTokenRepository.findByToken(token).getUser());
    }

    @Override
    public void changePassword(User user, String newPassword) {
        user.setPassword(encoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public boolean checkIfOldPasswordIsValid(User user, String oldPassword) {
       return encoder.matches(oldPassword, user.getPassword());
    }

}
