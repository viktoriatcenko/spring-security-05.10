package ru.maxima.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.maxima.models.Person;
import ru.maxima.service.PersonService;
import ru.maxima.service.RegistrationService;
import ru.maxima.validation.PersonValidator;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final PersonValidator validator;
    private final PersonService service;
    private final RegistrationService registrationService;

    @Autowired
    public AuthController(PersonValidator validator, PersonService service, RegistrationService registrationService) {
        this.validator = validator;
        this.service = service;
        this.registrationService = registrationService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/registration")
    public String giveRegistrationPage(@ModelAttribute("personFromPage") Person person) {
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("personFromPage") @Valid Person person,
                                      BindingResult result) {
        validator.validate(person, result);

        if (result.hasErrors()) {
            return "/auth/registration";
        }

        registrationService.save(person);

        return "redirect:/auth/login";
    }

    @GetMapping("/admin")
    public String admin() {
        return "auth/admin";
    }
}
