package com.athulraju.springsecclient.contoller;

import com.athulraju.springsecclient.entity.User;
import com.athulraju.springsecclient.entity.VerificationToken;
import com.athulraju.springsecclient.event.RegistrationEvenPublisher;
import com.athulraju.springsecclient.model.PasswordModel;
import com.athulraju.springsecclient.model.UserModel;
import com.athulraju.springsecclient.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.UUID;

@RestController
@Slf4j
public class Registrationcontroller {

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @PostMapping("/register")
    public String RegisterUser(@RequestBody UserModel userModel, final HttpServletRequest request){
        
        User user=userService.registerUser(userModel);
        eventPublisher.publishEvent(new RegistrationEvenPublisher(user,applicationUrl(request)));


        return "success";
    }


    @GetMapping("/verifyRegistration")
    public String verifyRegistrationToken(@RequestParam("token") String token){
       String result= userService.validateToke(token);
       if(result.equalsIgnoreCase("valid")){
           return "user verified successfully";
       }
       return  "bad user";
    }


    @GetMapping("/resendVerificationToken")
    public String resendVerificationToken(@RequestParam("token") String oldToken,HttpServletRequest request) {

        VerificationToken verificationToken = userService.generateVerificationToken(oldToken);
        User user=verificationToken.getUser();
        resendVerificationToken(user,verificationToken,applicationUrl(request));

        return "verification link resend";

    }

    @PostMapping("/resetPassword")
    public String resetPasswdToken(@RequestBody PasswordModel passwordModel,HttpServletRequest request){
        User user=userService.getUserPasswdByToken(passwordModel.getEmailId());
        String url="";
        if(user!=null){
            String token= UUID.randomUUID().toString();
            userService.createNewPasswdToken(user,token);
            url=resendPassswordTokenUrl(user,token,applicationUrl(request));
        }
        return url;
    }

    @PostMapping("/savePassword")
    public String savePasswordToken(@RequestParam("token") String token,@RequestBody PasswordModel passwordModel){
        String result=userService.validatePassword(token);
        if(!result.equalsIgnoreCase("valid")){
            return "invalid";
        }
        Optional<User> user=userService.getUserByPasswordByToken(token);
        if(user.isPresent()){
            userService.changePassword(user.get(),passwordModel.getNewPassword());
            return "paswword changed";

        }else {
            return "invalid";
        }
    }

    @PostMapping("/changePassword")
    public String changePassword(@RequestBody PasswordModel passwordModel){
      User user=userService.getUserPasswdByToken(passwordModel.getEmailId());
      if(!userService.checkIfOldPasswordIsValid(user,passwordModel.getOldPassword())){
          return "invalid ";
      }
      userService.changePassword(user,passwordModel.getNewPassword());
      return "password changed";
    }


    private String resendPassswordTokenUrl(User user, String token, String applicationUrl) {
     String url=applicationUrl+"/savePassword?token="+token;
     log.info("Click the link to Reset your Password: {}",
             url);
     return url;
    }




    private void resendVerificationToken(User user, VerificationToken verificationToken, String applicationUrl) {

        String url=applicationUrl +"/verifyRegistration?token="+verificationToken;

        log.info("Click the link to verify your account: {}",url);
    }


    private String applicationUrl(HttpServletRequest request) {
        return "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
    }
}
