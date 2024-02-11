package com.cof.sympuzz;

public class HelperClass {
    String Username, Email, DateOfBirth, Password;

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        DateOfBirth = dateOfBirth;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public HelperClass(String username, String email, String dateOfBirth, String password) {
        Username = username;
        Email = email;
        DateOfBirth = dateOfBirth;
        Password = password;
    }

    public HelperClass() {
    }
}
