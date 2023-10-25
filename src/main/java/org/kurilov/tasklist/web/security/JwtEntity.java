package org.kurilov.tasklist.web.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @author Ivan Kurilov on 20.10.2023
 */
@Data
@AllArgsConstructor
public class JwtEntity implements UserDetails {

    private final String name;
    private final String username;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;
    private Long id;

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
        return true;
    }
}
