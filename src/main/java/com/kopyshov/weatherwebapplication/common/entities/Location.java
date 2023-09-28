package com.kopyshov.weatherwebapplication.common.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@org.hibernate.annotations.NamedQuery(
        name = "findByCoordinates",
        query = "from Location where latitude = :latitude AND longitude = :longitude"
)
public class Location implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private Long id;
    private String name;

    @NaturalId
    @Column(nullable = false)
    private String latitude;
    @NaturalId
    @Column(nullable = false)
    private String longitude;


    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Location other = (Location) obj;
        return latitude != null && latitude.equals(other.getLatitude())
                && longitude !=null && longitude.equals(other.getLongitude());
    }
}
