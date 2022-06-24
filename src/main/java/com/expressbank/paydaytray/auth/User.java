package com.expressbank.paydaytray.auth;

import com.expressbank.paydaytray.enums.Role;
import com.expressbank.paydaytray.model.Stock;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name="Users")
@Data
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 40)
    private String username;
    @Column(length = 40)
    private String email;
    @Column(length = 100)
    private String password;
    private Double balance = 0D;
    @OneToMany(mappedBy = "user")
    private Set<Stock> stocks;
    private Role role = Role.USER;
    @OneToOne(mappedBy = "user")
    private ConfirmationToken confirmationToken;
    private Boolean enabled = false;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       return Arrays.asList(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
