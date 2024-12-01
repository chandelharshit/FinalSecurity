package com.example.finalSecurity.service;


import com.example.finalSecurity.model.Users;
import com.example.finalSecurity.repository.UserRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
//@RequiredArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<Users> getallUsersDetails(){
        List<Users> ls=userRepository.findAll();
        if (ls.isEmpty()) {
            // Handle empty list case, return an empty list or throw a custom exception
            return new ArrayList<>();
        }
        return ls;
    }
}
