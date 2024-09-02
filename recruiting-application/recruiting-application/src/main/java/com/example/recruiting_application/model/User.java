package com.example.recruiting_application.model;

import jakarta.persistence.*;

@Entity
@Table(name = "all_users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    @Column(nullable = false, unique = true)
    private String email;
    private String password;
    private String verificationToken;
    private boolean isVerified;

    @OneToOne(mappedBy = "user")
    private ForgetPassword forgetPassword;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerificationToken() {
        return verificationToken;
    }

    public void setVerificationToken(String verificationToken) {
        this.verificationToken = verificationToken;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public ForgetPassword getForgetPassword() {
        return forgetPassword;
    }

    public void setForgetPassword(ForgetPassword forgetPassword) {
        this.forgetPassword = forgetPassword;
    }
}
