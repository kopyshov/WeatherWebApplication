package com.kopyshov.weatherwebapplication.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table
@Data
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
}
