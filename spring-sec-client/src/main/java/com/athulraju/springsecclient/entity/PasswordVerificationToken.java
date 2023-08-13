package com.athulraju.springsecclient.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class PasswordVerificationToken {


    private static final int EXPIRATIONTIME=10;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",nullable = false,foreignKey = @ForeignKey(name="FK_PASSWORD_VERIFY_TOKEN"))
    private User user;

    private Date ExpirationTime;
    public PasswordVerificationToken(User user, String token){
        super();
        this.user=user;
        this.token=token;
        this.ExpirationTime=calculateExpirationTime(EXPIRATIONTIME);

    }

    public PasswordVerificationToken(String token){
        super();
        this.token=token;
        this.ExpirationTime=calculateExpirationTime(EXPIRATIONTIME);
    }

    private Date calculateExpirationTime(int expirationTime) {
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE,expirationTime);
        return new Date(calendar.getTime().getTime());
    }

}
