package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CredentialController {

    @Autowired CredentialService credentialService;

    @PostMapping("/addCredential")
    public String createNote(@ModelAttribute("credential") Credentials credentials, Model model, Authentication authentication){
        String username = authentication.getName();
        credentialService.addNewCredential(username, model, credentials);
        return "result";
    }

    @GetMapping("/deleteCredential")
    public String deleteNote(@RequestParam("credentialId") Integer credentialId, @ModelAttribute("credential") Credentials credentials, Model model){
        credentialService.deleteCredential(credentialId);
        model.addAttribute("deleteSuccess", true);
        return "result";
    }
}
