package com.athulraju.springsecclient.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {

     private static final String[] WHITELIST_URL={
             "/register","/hello","/verifyRegistration","/resetPassword","/savePassword","/changePassword"
     };


    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.cors(AbstractHttpConfigurer::disable).csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(auth->auth.requestMatchers(WHITELIST_URL).permitAll()
                .requestMatchers("/api/**").authenticated())
                .oauth2Login(oauth2login->oauth2login.loginPage("/oauth2/authorization/api-client-oidc")).oauth2Client(Customizer.withDefaults()).build();

    }
}
