package com.kopyshov.weatherwebapplication.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Data
public class UserData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<UserAuth> userAuthTokens = new HashSet<>();
}
