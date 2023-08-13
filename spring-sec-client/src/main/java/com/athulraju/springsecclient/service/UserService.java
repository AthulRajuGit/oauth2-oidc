package com.athulraju.springsecclient.service;

import com.athulraju.springsecclient.entity.User;
import com.athulraju.springsecclient.entity.VerificationToken;
import com.athulraju.springsecclient.model.UserModel;

import java.util.Optional;

public interface UserService {
    User registerUser(UserModel userModel);

    void saveVerificationToken(User user, String token);

    String validateToke(String token);

    VerificationToken generateVerificationToken(String oldToken);

    User getUserPasswdByToken(String emailId);

    void createNewPasswdToken(User user, String token);

    String validatePassword(String token);

    Optional<User> getUserByPasswordByToken(String token);

    void changePassword(User user, String newPassword);

    boolean checkIfOldPasswordIsValid(User user, String oldPassword);
}
