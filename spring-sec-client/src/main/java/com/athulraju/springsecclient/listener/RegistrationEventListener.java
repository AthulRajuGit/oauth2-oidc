package com.athulraju.springsecclient.listener;

import com.athulraju.springsecclient.entity.User;
import com.athulraju.springsecclient.event.RegistrationEvenPublisher;
import com.athulraju.springsecclient.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class RegistrationEventListener implements ApplicationListener<RegistrationEvenPublisher> {

    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(RegistrationEvenPublisher event) {

        User user=event.getUser();
        String token= UUID.randomUUID().toString();
        userService.saveVerificationToken(user,token);

        String url=event.getApplicationurl()+"/verifyRegistration?token="+token;
        log.info("verify the link to url :{}",url);
    }
}
