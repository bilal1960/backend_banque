package com.bilal.banking.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User extends AbstractEntity implements UserDetails {

    private String firstname;

    private String lastname;

    @Column(unique = true)
    private String email;

    private String password;

    @Builder.Default
    private boolean active = false;


    @OneToOne
    private Address adress;


    @OneToMany(mappedBy = "user")
    private List<Transaction> transactions;

    @OneToMany(mappedBy = "user")
    private  List<Contact> contacts;

    @OneToOne
    private Account account;

    @OneToOne
    private Role role;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }
}
