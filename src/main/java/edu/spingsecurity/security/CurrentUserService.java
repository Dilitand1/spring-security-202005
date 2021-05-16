package edu.spingsecurity.security;

import edu.spingsecurity.model.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {

    public User getCurrentUser() {
        UserDetailsAdapter adapter =
            (UserDetailsAdapter) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = adapter.getOriginaluser();
        return user;
    }
}
