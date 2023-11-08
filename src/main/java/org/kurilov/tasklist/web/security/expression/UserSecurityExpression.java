package org.kurilov.tasklist.web.security.expression;

import lombok.RequiredArgsConstructor;
import org.kurilov.tasklist.domain.user.Role;
import org.kurilov.tasklist.web.security.JwtEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author Ivan Kurilov on 01.11.2023
 */
@Component("userSecurityExpression")
@RequiredArgsConstructor
public class UserSecurityExpression {

    public boolean canAccessUser(final Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtEntity user = (JwtEntity) authentication.getPrincipal();
        Long userid = user.getId();

        return userid.equals(id) || hasAnyRole(authentication);
    }

    private boolean hasAnyRole(final Authentication authentication) {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(Role.ROLE_ADMIN.name());
        return authentication.getAuthorities().contains(authority);
    }
}
