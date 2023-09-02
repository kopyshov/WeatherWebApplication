package com.kopyshov.weatherwebapplication.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class UserAuth {
    @Id
    @GeneratedValue
    private Long id;

    private String selector;
    private String validator;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private UserData user;

}
