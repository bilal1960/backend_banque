package com.bilal.banking.services.impl;

import com.bilal.banking.config.JwtUtils;
import com.bilal.banking.dto.AccountDto;
import com.bilal.banking.dto.AuthenticationRequest;
import com.bilal.banking.dto.AuthenticationResponse;
import com.bilal.banking.dto.UserDto;
import com.bilal.banking.model.Account;
import com.bilal.banking.model.Role;
import com.bilal.banking.model.User;
import com.bilal.banking.repository.RoleRepository;
import com.bilal.banking.repository.UserRepository;
import com.bilal.banking.services.AccountService;
import com.bilal.banking.services.UserService;
import com.bilal.banking.validators.ObjectValidator;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String ROLE_USER = "ROLE_USER";
    private final UserRepository userRepository;
    private final AccountService accountService;
    private final ObjectValidator<UserDto> validator;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private  final AuthenticationManager authManager;
    private final RoleRepository roleRepository;
     @Override
    public Integer save(UserDto dto) {
        validator.validate(dto);
        User user = UserDto.toEntity(dto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user).getId();
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(UserDto::fromEntity)
                .collect(Collectors.toList());

    }

    @Override
    public UserDto findById(Integer id) {

        return userRepository.findById(id)
                .map(UserDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("No user was found with the provided ID :"+ id));
    }

    @Override
    public void delete(Integer id) {

        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Integer validateAccount(Integer id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No user was found for user account validation"));

        if (user.getAccount() == null) {
            // create a bank account
            AccountDto account = AccountDto.builder()
                    .user(UserDto.fromEntity(user))
                    .build();
            var savedAccount = accountService.save(account);
            user.setAccount(
                    Account.builder()
                            .id(savedAccount)
                            .build()
            );
        }
        user.setActive(true);
        userRepository.save(user);
        return user.getId();
    }

    @Override
    public Integer invalidateAccount(Integer id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No user was found for user account validation"));

        user.setActive(false);
        userRepository.save(user);

        return user.getId();
    }

    @Override
    @Transactional
    public AuthenticationResponse register(UserDto dto) {
        validator.validate(dto);
        User user = UserDto.toEntity(dto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(
             findOrCreateRole(ROLE_USER)
        );

        var savedUser = userRepository.save(user);
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", savedUser.getId());
        claims.put("fullName", savedUser.getFirstname() + " " + savedUser.getLastname());
        String token = jwtUtils.generateToken(savedUser, claims);
        return AuthenticationResponse.builder()
                .token(token)
                .build();

    }

    private Role findOrCreateRole(String roleName){
      Role role = roleRepository.findByName(roleName)
              .orElse(null);
      if (role == null){
         return roleRepository.save(
                 Role.builder()
                         .name(roleName)
                         .build()
         );
      }

      return role;
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        final User user = userRepository.findByEmail(request.getEmail()).get();
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("fullName", user.getFirstname() + " " + user.getLastname());
        final String token = jwtUtils.generateToken(user, claims);
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }
}
