package edu.spingsecurity.security;

import edu.spingsecurity.model.User;
import edu.spingsecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByLoginAndFetchRoles(username).get();
        UserDetailsAdapter adapter = new UserDetailsAdapter(user);
        return adapter;

//        return userRepository.findByLogin(username)
//            .map(x->new UserDetailsAdapter(x))
//            .orElseThrow(()->new UsernameNotFoundException("Not found user"));
    }
}