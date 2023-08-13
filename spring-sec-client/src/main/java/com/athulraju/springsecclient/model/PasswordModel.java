package com.athulraju.springsecclient.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

public class PasswordModel {

    private String oldPassword;
    private String newPassword;
    private String emailId;

}
