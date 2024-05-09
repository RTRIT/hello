package com.example.webApp.Service;

import com.example.webApp.Model.User;
import com.example.webApp.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    public List<User> getUserList(){
        return userRepository.getUserList();
    }
}
