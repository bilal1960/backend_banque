package com.bilal.banking.controllers;

import com.bilal.banking.config.JwtUtils;
import com.bilal.banking.dto.AuthenticationRequest;
import com.bilal.banking.dto.AuthenticationResponse;
import com.bilal.banking.dto.UserDto;
import com.bilal.banking.repository.UserRepository;
import com.bilal.banking.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private  final JwtUtils jwtUtils;


    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
           @RequestBody UserDto user){

       return ResponseEntity.ok( userService.register(user));

    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())

        );
        final UserDetails user = userRepository.findByEmail(request.getEmail()).get();
        final String token = jwtUtils.generateToken(user);
        return  ResponseEntity.ok(
                AuthenticationResponse.builder()
                        .token(token)
                        .build()
        );
    }

}
