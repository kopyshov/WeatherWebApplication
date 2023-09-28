package com.kopyshov.weatherwebapplication.common.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table
@Data
@NoArgsConstructor
@org.hibernate.annotations.NamedQuery(
        name = "findBySelector",
        query = "from UserToken where selector = :selector"
)
public class UserToken implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String selector;
    private String validator;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private UserData user;

    public UserToken(UserData user, String selector, String validator) {
        this.selector = selector;
        this.validator = validator;
        this.user = user;
    }
}
