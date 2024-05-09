package com.mobile_app.mobileApp.rest_controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class RegisterApiController {
    @PostMapping("/user/register")
    public String registerUser(@RequestBody String requestBody) {
        // Handle user registration logic here
        return "index";
    }

}
