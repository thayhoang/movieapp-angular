package com.hoangmn.controller;

import com.hoangmn.exception.BadRequestException;
import com.hoangmn.model.User;
import com.hoangmn.payload.JwtResponse;
import com.hoangmn.payload.LoginRequest;
import com.hoangmn.payload.MessageResponse;
import com.hoangmn.payload.SignupRequest;
import com.hoangmn.service.JwtAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
public class JwtAuthenticationController {

    @Autowired
    private JwtAuthenticationService service;

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
        JwtResponse response = service.authenticateUser(loginRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> registerUser(@RequestBody SignupRequest signUpRequest) {

        try {
            service.validateRequest(signUpRequest);
            User registerUser = service.buildRegisterUser(signUpRequest);
            service.save(registerUser);
        } catch (BadRequestException badRequestException) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(badRequestException.getMessage()));
        }

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
