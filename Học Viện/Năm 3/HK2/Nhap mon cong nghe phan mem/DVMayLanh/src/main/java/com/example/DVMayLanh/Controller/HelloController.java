package com.example.DVMayLanh.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.StringWriter;

@Controller
public class HelloController {
    @RequestMapping("/")
    @GetMapping("/")
    public String homepage() {
        return "index";  // Trả về trang index.html
    }

}
