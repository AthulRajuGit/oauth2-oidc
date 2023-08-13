package com.athulraju.springsecclient.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction.*;

@RestController
public class HelloController {

    @Autowired
    private WebClient webClient;

    @GetMapping("/api/hello")
    public String helloWorld(){
        return  "Hello world";
    }

    @GetMapping("/api/users")
    public String[] Users(@RegisteredOAuth2AuthorizedClient("api-client-authorization-code") OAuth2AuthorizedClient oAuth2AuthorizedClient){
     return this.webClient.get()
             .uri("http://127.0.0.1:8090/api/users")
             .attributes(oauth2AuthorizedClient(oAuth2AuthorizedClient))
             .retrieve()
             .bodyToMono(String[].class)
             .block();

    }

}
