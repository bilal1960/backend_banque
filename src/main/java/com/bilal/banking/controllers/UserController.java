package com.bilal.banking.controllers;

import com.bilal.banking.dto.UserDto;
import com.bilal.banking.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private UserService service;

    @PostMapping("/")
    public ResponseEntity<Integer> save(
            @RequestBody UserDto userDto
    ){
        return ResponseEntity.ok(service.save(userDto));
    }
    @GetMapping("/")
    public ResponseEntity<List<UserDto>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }
    @GetMapping("/{user-id}")
    public ResponseEntity<UserDto> findById(
            @PathVariable("user-id") Integer userId
    ){
        return ResponseEntity.ok(service.findById(userId));
    }
    @PatchMapping("/validate/{user-id}")
    public ResponseEntity<Integer> validateAccount(
            @PathVariable("user-id") Integer userId
    ){
        return ResponseEntity.ok(service.validateAccount(userId));
    }

    @PatchMapping("/invalidate/{user-id}")
    public ResponseEntity<Integer> invalidateAccount(
            @PathVariable("user-id") Integer userId
    ){
        return ResponseEntity.ok(service.invalidateAccount(userId));
    }
    @DeleteMapping("/{user-id}")
    public ResponseEntity<Void> delete(
            @PathVariable("user-id") Integer userId

    ){
        service.delete(userId);
        return ResponseEntity.accepted().build();
    }
}
