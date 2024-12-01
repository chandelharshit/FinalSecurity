package com.example.finalSecurity.config;

import com.example.finalSecurity.model.Users;
import com.example.finalSecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users user=userRepository.findByName(username);

        if(user==null){
            System.out.println("User nOt Found"+ username);
            throw  new UsernameNotFoundException("User Not Found");
        }

        return new UserPrincipal(user);
    }
}
