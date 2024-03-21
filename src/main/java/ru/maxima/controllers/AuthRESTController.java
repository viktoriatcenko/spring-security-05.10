package ru.maxima.controllers;

import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.maxima.dto.AuthenticationDTO;
import ru.maxima.dto.PersonDTO;
import ru.maxima.models.Person;
import ru.maxima.security.PersonDetails;
import ru.maxima.service.PersonService;
import ru.maxima.service.RegistrationService;
import ru.maxima.util.JWTUtil;
import ru.maxima.validation.PersonValidator;

import java.util.Map;

@RestController
@RequestMapping("/authenticate")
public class AuthRESTController {

    private final RegistrationService registrationService;
    private final PersonValidator validator;
    private final PersonService personService;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;


    public AuthRESTController(RegistrationService registrationService, PersonValidator validator, PersonService personService, JWTUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.registrationService = registrationService;
        this.validator = validator;
        this.personService = personService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/registration")
    public Map<String, String> performRegistration(@RequestBody @Valid PersonDTO personDTO,
                                                   BindingResult bindingResult) {

        Person person = personService.convertToPerson(personDTO);

        validator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) {
            return Map.of("message", "Ошибка");
        }

        registrationService.save(person);

        String token = jwtUtil.generateToken(person.getName());

        return Map.of("jwt-token", token);
    }


    @GetMapping("/showUserInfo")
    public Map<String, String> showUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        return Map.of("username", personDetails.getUsername());
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody AuthenticationDTO authenticationDTO) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(authenticationDTO.getUsername(),
                        authenticationDTO.getPassword());

        try {
            authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        } catch (AuthenticationException e) {
            return Map.of("message", "Ошибка");
        }

        String token = jwtUtil.generateToken(authenticationDTO.getUsername());

        return Map.of("jwt-token", token);

    }
}
