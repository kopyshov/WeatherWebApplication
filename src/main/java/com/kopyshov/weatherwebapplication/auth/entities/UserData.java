package com.kopyshov.weatherwebapplication.auth.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Data
@NoArgsConstructor
@org.hibernate.annotations.NamedQuery(
        name = "findByUsernameAndPass",
        query = "from UserData where username = :username AND password = :password"
)
public class UserData implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, updatable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private Set<UserToken> userTokenTokens = new HashSet<>();

    public UserData(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
