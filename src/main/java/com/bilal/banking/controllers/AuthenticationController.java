package com.bilal.banking.controllers;

import com.bilal.banking.dto.AuthenticationRequest;
import com.bilal.banking.dto.AuthenticationResponse;
import com.bilal.banking.dto.UserDto;
import com.bilal.banking.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
           @RequestBody UserDto user){

       return ResponseEntity.ok( userService.register(user));

    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ){
       return ResponseEntity.ok(userService.authenticate(request));
    }

}
