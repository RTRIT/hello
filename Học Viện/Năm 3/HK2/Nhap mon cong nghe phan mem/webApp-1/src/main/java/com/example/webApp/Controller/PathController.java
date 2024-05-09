package com.example.webApp.Controller;

import com.example.webApp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PathController {
    @Autowired
    UserService userService;

    @GetMapping("/home")
    public String home(Model model){
        return "index";
    }
    @GetMapping("/Admin")
    public String admin(Model model){
        model.addAttribute("userlist", userService.getUserList());
        return "Admin";
    }


}
