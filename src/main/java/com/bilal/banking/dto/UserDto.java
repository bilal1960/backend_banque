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

    @NotNull(message = "le nom ne doit pas être null")
    @NotEmpty(message = "entrer un nom svp")
    @NotBlank(message = "Le nom ne doit pas être vide")
    private String lastname;

    @NotNull(message = "l'email  ne doit pas être vide")
    @NotEmpty(message = "entrer un email svp ")
    @NotBlank(message = "L'email  ne doit pas être vide")
    @Email(message = "L'email n'est pas conforme")
    private String email;

    @NotNull(message = "Le password ne doit pas être null")
    @NotEmpty(message = "entrer un password svp ")
    @NotBlank(message = "le password ne doit pas être vide")
    @Size(min = 8, max = 16,message = "le mot de passe doit être entre 8 et 16 caractère")
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
