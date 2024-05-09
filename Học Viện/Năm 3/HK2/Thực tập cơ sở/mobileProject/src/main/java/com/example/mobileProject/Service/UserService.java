package com.example.mobileProject.Service;

import com.example.mobileProject.Model.User;
import com.example.mobileProject.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // được đánh dấu là một module và được thêm voà IoC container khi IoC quét
public class UserService {
    @Autowired
    UserRepository userRepository;
    public int registerNewUserServiceMethod(String first_name, String last_name, String email,String username, String password){
        return userRepository.registerNewUser(first_name,last_name,email,username,password);
    }


//  CHECK WHETHER USER EXIST IN DB BY EMAIL OR USERNAME
    public List<String> checkUserEmail(String Email){
        return userRepository.checkEmail(Email);
    }
    public List<String> checkUsername(String username){ return userRepository.checkUsername(username);}


//  GET USER'S PASSWORD BY EMAIL OR USERNAME
    public String checkUserPasswordByEmail(String Email){
        return userRepository.checkPassword(Email);
    }
    public String checkUserPasswordByUsername(String Username){
        return userRepository.checkPasswordByUsername(Username);
    }



//    GET USER DETAIL BY EMAIL OR USERNAME
    public User getUserDetailByUsername(String username){
//        return userRepository.findByUsername(username);
        return userRepository.getUserDetailByUsername(username);
    }
    public User getUserDetailByEmail(String email){
        return userRepository.getUserDetail(email);
    }


// GET USER LIST
public List<User> getUserList(){
        return userRepository.getUserList();
}


//    DELETE USER
    public void deleteUserDetailByUsername(String username){userRepository.deleteByUsername(username);}

}
