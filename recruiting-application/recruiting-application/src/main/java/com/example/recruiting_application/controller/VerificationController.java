//package com.example.recruiting_application.controller;
//
//import com.example.recruiting_application.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api")
//public class VerificationController {
//
//    @Autowired
//    private UserService userService;
//
//    @PostMapping("/verify-email")
//    public ResponseEntity<?> verifyEmail(@RequestBody TokenDto tokenDto) {
//        boolean isVerified = userService.verifyEmail(tokenDto.getToken());
//        if (isVerified) {
//            return ResponseEntity.ok().body("Email verification successful.");
//        } else {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Verification failed. Invalid token.");
//        }
//    }
//}
//
//
//
//class TokenDto {
//    private String token;
//
//    // Getter and Setter
//    public String getToken() {
//        return token;
//    }
//
//    public void setToken(String token) {
//        this.token = token;
//    }
//}
