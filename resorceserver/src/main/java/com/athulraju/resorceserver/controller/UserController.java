package com.athulraju.resorceserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/api/hello")
    public String[] getUsers(){
        return new String[]{"athul","raju","lincy"};
    }
}
