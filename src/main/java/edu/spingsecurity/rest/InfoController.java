package edu.spingsecurity.rest;

import edu.spingsecurity.model.User;
import edu.spingsecurity.security.CurrentUserService;
import edu.spingsecurity.security.UserDetailsAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class InfoController {

    private final CurrentUserService currentUserService;

    @GetMapping("/info")
    public Map<String, String> getInfo() {
        return Collections.singletonMap("version", "1.0");
    }

    @GetMapping("/whoami")
    public Map<String, String> getWhoAmI() {
        User user = currentUserService.getCurrentUser();
        return Collections.singletonMap("name", user.getLogin());
    }
}
