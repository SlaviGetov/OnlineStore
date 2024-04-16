package com.failedsaptrainees.onlinestore.DTO.Forms;

import jakarta.validation.constraints.NotEmpty;

public class UserDetailsFormDTO {

    @NotEmpty
    private String fullName;

    private String password;

    @NotEmpty
    private String role;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
