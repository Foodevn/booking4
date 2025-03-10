package com.example.booking4.Models;

public class User {
    private String id;
    private String email;
    private String name;
    private String phone;
    private String avatar;

    public User() {
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }


    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(String id) {
        this.id = id;
    }
}
