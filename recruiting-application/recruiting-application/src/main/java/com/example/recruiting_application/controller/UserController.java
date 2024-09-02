package com.example.recruiting_application.controller;

import com.example.recruiting_application.dto.MailDto;
import com.example.recruiting_application.model.ForgetPassword;
import com.example.recruiting_application.model.User;
import com.example.recruiting_application.repository.ForgetPassRepo;
import com.example.recruiting_application.repository.UserRepo;
import com.example.recruiting_application.service.EmailService;
import com.example.recruiting_application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {



    private UserService userService;

    private ForgetPassRepo forgetPassRepo;
    private EmailService emailService;

    private  UserRepo userRepo;

    public UserController(UserService userService, ForgetPassRepo forgetPassRepo, EmailService emailService, UserRepo userRepo) {
        this.userService = userService;
        this.forgetPassRepo = forgetPassRepo;
        this.emailService = emailService;
        this.userRepo = userRepo;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestParam String email, @RequestParam String password) {
        System.out.println("hii");
        boolean valid = userService.validateUser(email, password);
        Map<String, Object> response = new HashMap<>();
        response.put("success", valid);
        if (valid) {
            response.put("message", "Login successful");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Invalid credentials");
            return ResponseEntity.status(401).body(response);
        }
    }

    @PostMapping("/register")
    public @ResponseBody ResponseEntity<Map<String, Object>> register(@RequestBody User user) {
        Map<String, Object> response = new HashMap<>();
        try {
            User newUser = userService.registerUser(user);
            response.put("success", true);
            response.put("message", "User registered successfully.");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "An error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/password-recovery")
    public ResponseEntity<String> requestPasswordReset(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");
        User user = userRepo.findByEmail(email);
        if (user != null) {
            int otp = generateOtp();
            Date expirationTime = new Date(System.currentTimeMillis() + 15 * 60 * 1000); // 15 minutes expiry

            ForgetPassword forgetPassword = new ForgetPassword();
            forgetPassword.setUser(user);
            forgetPassword.setOtp(otp);
            forgetPassword.setExpirationTime(expirationTime);
            forgetPassRepo.save(forgetPassword);

            MailDto mailBod = new MailDto(
                    email,
                    "no-reply@example.com",
                    "Password Recovery",
                    "Your OTP is: " + otp + "\nIt is valid for 15 minutes."
            );
            emailService.sendSimpleMessage(mailBod);
            return ResponseEntity.ok("Password recovery email sent successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(
            @RequestParam String email,
            @RequestParam Integer otp,
            @RequestParam String newPassword) {

        Optional<ForgetPassword> forgetPasswordOptional = forgetPassRepo.findByUserEmailAndOtp(email, otp);

        if (forgetPasswordOptional.isPresent()) {
            ForgetPassword forgetPassword = forgetPasswordOptional.get();
            if (new Date().before(forgetPassword.getExpirationTime())) {
                if (!validatePassword(newPassword)) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password does not meet security requirements.");
                }
                boolean success = userService.updatePassword(email, newPassword);
                forgetPassRepo.delete(forgetPassword);
                return ResponseEntity.ok(success ? "Password reset successfully." : "Password reset failed.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("OTP expired.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid OTP.");
        }
    }

    private int generateOtp() {
        return (int)(Math.random() * 900000) + 100000;
    }

    private boolean validatePassword(String password) {
        return password.length() >= 8;
    }


}
