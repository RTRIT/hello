package com.mobile_app.mobileApp.rest_controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class UserApiController {
    @GetMapping("/test")
    public String testEndpoint(){
        return "Test end point is working";
    }
}
