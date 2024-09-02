package com.example.recruiting_application.service;

import com.example.recruiting_application.dto.MailDto;
import com.example.recruiting_application.model.ForgetPassword;
import com.example.recruiting_application.model.User;
import com.example.recruiting_application.repository.ForgetPassRepo;
import com.example.recruiting_application.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.Random;
@Service
public class UserService implements UserDetailsService {

    private final UserRepo repo;
    private final PasswordEncoder passwordEncoder;
    private final ForgetPassRepo forgetPassRepo;
    private final EmailService emailService;

    @Autowired
    public UserService(UserRepo repo, PasswordEncoder passwordEncoder, ForgetPassRepo forgetPassRepo, EmailService emailService) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
        this.forgetPassRepo = forgetPassRepo;
        this.emailService = emailService;
    }

    public User registerUser(User user) {
        if(repo.findByEmail(user.getEmail()) != null){
            throw new IllegalArgumentException("Email is already registered.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repo.save(user);
    }

    public User findUserByEmail(String email) {
        return repo.findByEmail(email);
    }

    public boolean validateUser(String email, String password) {
        User user = repo.findByEmail(email);
        if (user != null) {
            return passwordEncoder.matches(password, user.getPassword());
        }
        return false;
    }

    public boolean updatePassword(String email, String newPassword) {
        User user = repo.findByEmail(email);
        if (user != null) {
            user.setPassword(passwordEncoder.encode(newPassword));
            repo.save(user);
            return true;
        }
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repo.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles("USER")
                .build();
    }

    public boolean verifyEmail(String token) {
        Optional<ForgetPassword> forgetPasswordOpt = forgetPassRepo.findByOtp(Integer.valueOf(token));

        if (forgetPasswordOpt.isPresent()) {
            ForgetPassword forgetPassword = forgetPasswordOpt.get();
            if (new Date().before(forgetPassword.getExpirationTime())) {
                forgetPassRepo.delete(forgetPassword);
                return true;
            }
        }
        return false;
    }
}