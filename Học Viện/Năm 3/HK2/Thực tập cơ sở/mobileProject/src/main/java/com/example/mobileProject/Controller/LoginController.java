package com.example.mobileProject.Controller;

import com.example.mobileProject.JWT.JwtUtil;
import com.example.mobileProject.Model.LoginForm;
import com.example.mobileProject.Model.User;
import com.example.mobileProject.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectSerializer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class LoginController {
    @Autowired
    UserService userService;
    @Autowired
    JwtUtil jwtUtil;
    @PostMapping("/user/login")
    //Gửi request có body theo LoginForm
    public ResponseEntity login(@RequestBody LoginForm form) {
        if (form == null || form.getUsername() == null || form.getPassword() == null) {return new ResponseEntity<>("Request body is missing required fields", HttpStatus.BAD_REQUEST);}
        // Check if the request body is properly formatted

        List<String> user_name = userService.checkUsername(form.getUsername());
        // Get User's username:

        if (user_name.isEmpty()) {return new ResponseEntity<>("Invalid username or password", HttpStatus.BAD_REQUEST);}
        // Check if user exists by username

        String hashedPassword = userService.checkUserPasswordByUsername(form.getUsername());
        //lấy hashed password từ databasae bằng username của user

        if(!BCrypt.checkpw(form.getPassword(),hashedPassword)){return new ResponseEntity<>("Incorrect username or password", HttpStatus.BAD_REQUEST);}
        //So sánh input password vs hashed password bằng checkpw() của Bcrypt

        User user = userService.getUserDetailByUsername(form.getUsername());
        //Get User detail with username
        String token = "{\"token\":\"" + jwtUtil.generateToken(form.getUsername(), user.getRole()) + "\"}";
        // Generate new token with the provided username

        return new ResponseEntity<>(token, HttpStatus.OK);
        // Return a user
        // Spring convert object to json automatically because of jackson built in
    }

    @PostMapping("/user/loginOauth")
    //Gửi request có body theo LoginForm
    public ResponseEntity login(@RequestBody User user) {
        if (user == null || user.getUsername() == null ) {return new ResponseEntity<>("Request body is missing required fields", HttpStatus.BAD_REQUEST);}
        // Check if the request body is properly formatted



        String token = "{\"token\":\"" + jwtUtil.generateToken(user.getUsername(), "user") + "\"}";
        // Generate new token with the provided username

        return new ResponseEntity<>(token, HttpStatus.OK);
        // Return a user
        // Spring convert object to json automatically because of jackson built in
    }

}
