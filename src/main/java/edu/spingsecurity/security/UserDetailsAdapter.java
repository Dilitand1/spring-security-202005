package edu.spingsecurity.security;


import edu.spingsecurity.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public class UserDetailsAdapter implements UserDetails {

    public static UserDetailsAdapter anonymous() {
        User user = new User();
        user.setLogin("anon");
        user.setCompanies(Collections.EMPTY_LIST);
        user.setRoles(Collections.singletonList("anon"));
        return new UserDetailsAdapter(user);
    }

    private final User originalUser;

    public UserDetailsAdapter(User originalUser) {
        this.originalUser = originalUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return originalUser.getRoles().stream()
            .map(role->"ROLE_" + role.toUpperCase())
            .map(upperrole->new SimpleGrantedAuthority(upperrole)).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return originalUser.getPassword();
    }

    @Override
    public String getUsername() {
        return originalUser.getLogin();
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
        return true;
    }

    public User getOriginaluser() {
        return originalUser;
    }

}
