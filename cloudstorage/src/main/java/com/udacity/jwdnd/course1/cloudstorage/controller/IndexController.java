package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class IndexController {

    @GetMapping("/home")
    public ModelAndView homePage() {
        ModelAndView mav = new ModelAndView("/home");
        return mav; // Return the name of the Thymeleaf template
    }

    @GetMapping("")
    public ModelAndView indexPage() {
        ModelAndView mav = new ModelAndView("/index");
        return mav; // Return the name of the Thymeleaf template
    }

    @GetMapping("/login")
    public ModelAndView loginPage() {
        ModelAndView mav = new ModelAndView("/login");
        return mav; // Return the name of the Thymeleaf template
    }

    // Handling file upload
    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        // Process file upload
        return "redirect:/home"; // Redirect back to the home page
    }

    // Handling note creation
    @PostMapping("/notes/add")
    public String addNote(@RequestParam("title") String title, @RequestParam("content") String content) {
        // Process note creation
        return "redirect:/home"; // Redirect back to the home page
    }

    // Handling credential creation
    @PostMapping("/credentials/add")
    public String addCredential(@RequestParam("username") String username, @RequestParam("password") String password) {
        // Process credential creation
        return "redirect:/home"; // Redirect back to the home page
    }
}
