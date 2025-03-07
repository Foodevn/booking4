package com.example.booking4.Models;

public class Setting {
    String Languages;
    String Email;
    String Password;

    public Setting(String languages, String email, String password) {
        Languages = languages;
        Email = email;
        Password = password;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setLanguages(String languages) {
        Languages = languages;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public String getPassword() {
        return Password;
    }

    public String getLanguages() {
        return Languages;
    }

    public Setting() {

    }


}
