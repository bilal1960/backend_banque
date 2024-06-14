package com.bilal.banking.services.impl;

import com.bilal.banking.dto.AccountDto;
import com.bilal.banking.dto.UserDto;
import com.bilal.banking.model.Account;
import com.bilal.banking.model.User;
import com.bilal.banking.repository.AccountRepository;
import com.bilal.banking.repository.UserRepository;
import com.bilal.banking.services.AccountService;
import com.bilal.banking.services.UserService;
import com.bilal.banking.validators.ObjectValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AccountService accountService;
    private final ObjectValidator<UserDto> validator;
    @Override
    public Integer save(UserDto dto) {
        validator.validate(dto);
        User user = UserDto.toEntity(dto);
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
    public Integer validateAccount(Integer id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No user was found for user account validation"));
        user.setActive(true);

        AccountDto account =  AccountDto.builder()
                .user(UserDto.fromEntity(user))
                .build();

        accountService.save(account);
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
}
