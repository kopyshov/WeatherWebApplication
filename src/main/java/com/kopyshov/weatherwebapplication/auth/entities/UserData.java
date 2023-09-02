package com.kopyshov.weatherwebapplication.auth.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Data
public class UserData implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private Set<UserToken> userTokenTokens = new HashSet<>();
}
