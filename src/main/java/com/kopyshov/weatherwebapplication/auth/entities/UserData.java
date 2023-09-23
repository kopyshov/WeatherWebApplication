package com.kopyshov.weatherwebapplication.auth.entities;

import com.kopyshov.weatherwebapplication.buisness.Location;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
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

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinTable(name = "userdata_location",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "location_id"))
    private Set<Location> added = new HashSet<>();

    public UserData(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public int hashCode() {
        return 17;
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
