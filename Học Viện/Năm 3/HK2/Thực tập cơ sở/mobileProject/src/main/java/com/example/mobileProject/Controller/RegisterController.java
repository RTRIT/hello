package com.example.mobileProject.Controller;

import com.example.mobileProject.Model.User;
import com.example.mobileProject.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class RegisterController {
    //Tìm module tương ứng trong IoC Container và Inject voà
    @Autowired
    UserService userService;
    @PostMapping("/user/register")
    public ResponseEntity registerNewUser(@RequestParam("first_name") String first_name,
                                          @RequestParam("last_name") String last_name,
                                          @RequestParam("email") String email,
                                          @RequestParam("username") String username,
                                          @RequestParam("password") String password

                                         ){

        if(first_name.isEmpty() || last_name.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty()){
            return new ResponseEntity<>("Please complete all fields", HttpStatus.BAD_REQUEST);
        }
        // Kiểm tra xem field có rỗng không
        if(!(userService.checkUsername(username).isEmpty())){
            return new ResponseEntity<>("This username is already have!", HttpStatus.BAD_REQUEST);
        }
        //Kiểm tra xem username có trong db chưa
        String hashed_password = BCrypt.hashpw(password,BCrypt.gensalt());
        //Hashed password

        int result = userService.registerNewUserServiceMethod(first_name, last_name, email ,username, hashed_password);

        if(result != 1){
            return new ResponseEntity<>("Failed", HttpStatus.BAD_REQUEST);
        }
        //Register user into DB

        return new ResponseEntity<>("Success", HttpStatus.OK);
    }



}
