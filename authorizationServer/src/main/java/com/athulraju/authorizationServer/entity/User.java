package com.athulraju.authorizationServer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="User_tbl")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String  firstName;
    private String lastName;
    private String emailId;
    @Column(length = 60)
    private String password;
    private String Role;
    private boolean enabled;
}
