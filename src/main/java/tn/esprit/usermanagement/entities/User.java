package tn.esprit.usermanagement.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import tn.esprit.usermanagement.enumerations.BanType;
import tn.esprit.usermanagement.enumerations.Role;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "_user")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Double longitude;
    private  Double latitude;


    private Boolean enabled= null;
    private Boolean firtAttempt = true;
    @Enumerated(EnumType.STRING)
    private Role role;
@JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<JwtToken> jwtTokens;

    // Delivery attributes
    private Integer phoneNumber;
    private String secteur;

    private String country;
    @Column(name = "max_poids")
    private int maxPoids;
    @Column(name = "nombres_des_commandes ")
    private int nbrCommande;
    private String listeColis;
    private String listeClient ;






    @Enumerated(EnumType.STRING)
    private BanType banType;
    private LocalDateTime bannedAt;
    private Boolean twoFactorsAuth;
    private Boolean phoneNumberVerif;
    private Boolean emailVerif;
    private Integer banNumber;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }


}
