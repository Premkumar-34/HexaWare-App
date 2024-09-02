//package com.example.recruiting_application.controller;
//
//import com.example.recruiting_application.dto.MailDto;
//import com.example.recruiting_application.model.ForgetPassword;
//import com.example.recruiting_application.model.User;
//import com.example.recruiting_application.repository.ForgetPassRepo;
//import com.example.recruiting_application.repository.UserRepo;
//import com.example.recruiting_application.service.EmailService;
//import com.example.recruiting_application.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Date;
//import java.util.Optional;
//import java.util.Random;
//import java.util.UUID;
//
//@RestController
//@CrossOrigin(origins = "http://127.0.0.1:5500")
//@RequestMapping("/api")
//public class ForgetPassController {
//
//    private final UserRepo userRepo;
//    private final EmailService emailService;
//    private final ForgetPassRepo forgetPassRepo;
//    private final UserService userService;
//    @Autowired
//
//    public ForgetPassController(UserRepo userRepo, EmailService emailService, ForgetPassRepo forgetPassRepo, UserService userService) {
//        this.userRepo = userRepo;
//        this.emailService = emailService;
//        this.forgetPassRepo = forgetPassRepo;
//        this.userService = userService;
//    }
//
//
//
//
//    @PostMapping("/password-recovery/{email}")
//    public ResponseEntity<String> verifyEmail(@PathVariable String email) {
//        System.out.println("Hello");
//        User user = userRepo.findByEmail(email);
//        if (user != null) {
//            int otp = generateOtp();
//            Date expirationTime = new Date(System.currentTimeMillis() + 15 * 60 * 1000); // 15 minutes expiry
//
//            ForgetPassword forgetPassword = new ForgetPassword();
//            forgetPassword.setUser(user);
//            forgetPassword.setOtp(otp);
//            forgetPassword.setExpirationTime(expirationTime);
//            forgetPassRepo.save(forgetPassword);
//
//            MailDto.MailBody mailBody = new MailDto.MailBody(
//                    email,
//                    "no-reply@example.com",
//                    "Password Recovery",
//                    "Your OTP is: " + otp + "\nIt is valid for 15 minutes."
//            );
//            emailService.sendSimpleMessage(mailBody);
//            return ResponseEntity.ok("Password recovery email sent successfully.");
//        }
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
//    }
//
//    private int generateOtp() {
//        // Simple OTP generation logic (you can improve this)
//        return (int)(Math.random() * 900000) + 100000;
//    }
//
//    @PostMapping("/reset-password")
//    public ResponseEntity<String> resetPassword(
//            @RequestParam("email") String email,
//            @RequestParam("otp") Integer otp,
//            @RequestParam("newPassword") String newPassword) {
//
//        Optional<ForgetPassword> forgetPasswordOptional = forgetPassRepo.findByUserEmailAndOtp(email, otp);
//
//        if (forgetPasswordOptional.isPresent()) {
//            ForgetPassword forgetPassword = forgetPasswordOptional.get();
//            if (new Date().before(forgetPassword.getExpirationTime())) {
//                if (!validatePassword(newPassword)) {
//                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password does not meet security requirements.");
//                }
//                boolean success = userService.updatePassword(email, newPassword);
//                forgetPassRepo.delete(forgetPassword);
//                return ResponseEntity.ok(success ? "Password reset successfully." : "Password reset failed.");
//            } else {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("OTP expired.");
//            }
//        } else {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid OTP.");
//        }
//    }
//
//
//
//    private boolean validatePassword(String password) {
//        // Implement your password policy here (e.g., length, complexity, etc.)
//        return password.length() >= 8; // Simple example: password must be at least 8 characters long
//
//
//    }
//}
