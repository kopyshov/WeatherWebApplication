package com.kopyshov.weatherwebapplication.common.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@org.hibernate.annotations.NamedQuery(
        name = "findByUsernameAndPass",
        query = "from UserData where username = :username AND password = :password"
)
public class UserData implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true, updatable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    @EqualsAndHashCode.Exclude
    private Set<UserToken> userTokens = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "userdata_location",
            joinColumns = @JoinColumn(name = "userdata_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "location_id", referencedColumnName = "location_id")
    )
    private Set<Location> added = new HashSet<>();

    public void addLocation(Location location) {
        added.add(location);
    }

    public void removeLocation(Location location) {
        added.remove(location);
    }

    public UserData(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(username);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UserData other = (UserData) obj;
        return username != null && username.equals(other.getUsername());
    }
}
