package com.bilal.banking.services;

import com.bilal.banking.dto.AuthenticationRequest;
import com.bilal.banking.dto.AuthenticationResponse;
import com.bilal.banking.dto.UserDto;

public interface UserService extends AbstractService<UserDto> {

    Integer validateAccount(Integer id);

    Integer invalidateAccount(Integer id);

    AuthenticationResponse register(UserDto user);

    AuthenticationResponse authenticate(AuthenticationRequest request);
}
