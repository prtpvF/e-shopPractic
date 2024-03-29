package ru.mihail.springcourse.FirstSecurityApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.mihail.springcourse.FirstSecurityApp.models.Person;
import ru.mihail.springcourse.FirstSecurityApp.services.RegistrationServices;
import ru.mihail.springcourse.FirstSecurityApp.util.PersonValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final RegistrationServices registrationServices;
    private final PersonValidator personValidator;
        @Autowired
    public AuthController(RegistrationServices registrationServices, PersonValidator personValidator) {

            this.registrationServices = registrationServices;
            this.personValidator = personValidator;
        }

    @GetMapping("/login")
    public String loginPage(){
            return "auth/login";
    }

   @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("person") Person person){

        return  "auth/registration";
    }

    @PostMapping("/registration")
    public String preformRegistration(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
     personValidator.validate(person,bindingResult);

        if(bindingResult.hasErrors()) {

            return "/auth/registration";
        }
        System.out.println("работай дура3");

        registrationServices.register(person);
        return "redirect:/auth/login";
    }

}
