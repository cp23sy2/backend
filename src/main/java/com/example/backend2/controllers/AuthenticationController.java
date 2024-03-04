package com.example.backend2.controllers;


import com.example.backend2.models.AuthenticationResponse;
import com.example.backend2.models.Login;
import com.example.backend2.services.LdapAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private LdapAuthenticationService ldapAuthenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody Login loginRequest) {
        return ldapAuthenticationService.Authenticate(loginRequest.getUsername(), loginRequest.getPassword());
    }

}
