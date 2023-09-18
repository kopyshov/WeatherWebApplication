package com.kopyshov.weatherwebapplication.buisness;

import com.kopyshov.weatherwebapplication.auth.entities.UserData;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Data
@NoArgsConstructor
public class Location {
    @Id
    private Long id;
    private String name;
    private String latitude;
    private String longitude;
    @ManyToMany(mappedBy = "added")
    private Set<UserData> addedBy = new HashSet<>();
}
