package com.bilal.banking.dto;

import com.bilal.banking.model.User;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserDto {

    private  Integer id;

    @NotNull(message = "le prénom ne doit pas être null")
    @NotEmpty(message = "entrer un prénom svp ")
    @NotBlank(message = "le prénom ne doit pas être vide")
    private String firstname;

    @NotNull
    @NotEmpty
    @NotBlank
    private String lastname;

    @NotNull
    @NotEmpty
    @NotBlank
    @Email
    private String email;

    @NotNull
    @NotEmpty
    @NotBlank
    @Size(min = 8, max = 16)
    private String password;

    private String iban;

    private boolean active;


    public static UserDto fromEntity(User user){

        return UserDto.builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .iban(user.getAccount() == null ? "" : user.getAccount().getIban())
                .password(user.getPassword())
                .active(user.isActive())
                .build();
    }

    public static User toEntity(UserDto user){

        return User.builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }


}
