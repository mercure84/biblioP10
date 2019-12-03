package com.biblioP7.webController;

import com.biblioP7.beans.Membre;

import com.biblioP7.beans.RegisterForm;
import com.biblioP7.feignClient.MembreServiceClient;
import com.biblioP7.security.EncryptedPasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class SignUpController {

    @Autowired
    MembreServiceClient membreServiceClient;


    @GetMapping("/client/signUp")
    public String afficherSignUp (Model model){
        model.addAttribute("registerForm", new RegisterForm());
        return "signUp";
    }


    @PostMapping("/client/signUp")
    public String soumettreSignUp (@ModelAttribute RegisterForm registerForm){


        membreServiceClient.ajouterMembre(registerForm);

        return "index";
    }




}
