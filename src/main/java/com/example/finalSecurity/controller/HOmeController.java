package com.example.finalSecurity.controller;

import com.example.finalSecurity.DTO.LoginDTO;
import com.example.finalSecurity.model.Student;
import com.example.finalSecurity.model.Users;
import com.example.finalSecurity.repository.UserRepository;
import com.example.finalSecurity.service.JwtService;
import com.example.finalSecurity.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.PrimitiveIterator;

@RestController
//@RequiredArgsConstructor
public class HOmeController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDetailsService userDetailsService;

    List<Student> students = new ArrayList<>(List.of(
            new Student(1, "Harshit", "Hindi"),
            new Student(2, "Supriya", "English")
    ));

    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jswtservice;

    @GetMapping("/")
    public String getHomePage() {
        return "This is HomePage";
    }

    @GetMapping("/getSession")
    public String getSessionID(HttpServletRequest request) {
        return request.getSession().getId();
    }


    @GetMapping("/students")

    public List<Student> getStudents() {
        return students;
    }

    @PostMapping("/student")
    public String postStudent(@RequestBody Student student) {
        try {
            return "Saved Successfully";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/users")
    public List<Users> getUsers() {
        return userService.getallUsersDetails();
    }

    @GetMapping("/log")
    public Users getLoginEndpointInfo() {
        return userRepository.findByName("Supriya");
    }

    @PostMapping("/login")
    public String loginMethod(@RequestBody LoginDTO user) {
        System.out.println(user);
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getName(), user.getPass())
            );
            if (authentication.isAuthenticated()) {
                return "Login successful for user: " + user.getName() + jswtservice.generateToken(user.getName());
            } else {
                return "Login failed";
            }
        } catch (AuthenticationException e) {
            return "Invalid username or password";
        }

//        return user;
    }

}
