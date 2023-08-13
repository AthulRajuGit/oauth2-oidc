package com.athulraju.springsecclient.event;

import com.athulraju.springsecclient.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;


@Getter
@Setter

public class RegistrationEvenPublisher extends ApplicationEvent {



    private final User user;
    private final String Applicationurl;
    public RegistrationEvenPublisher(User user,String Applicationurl) {
        super(user);
        this.user=user;
        this.Applicationurl=Applicationurl;
    }
}
