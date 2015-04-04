package com.yastart.papaya.Model;

public class User {

    private String id; // Test user id 102363055574899025750
    private String username;
    private String email;

    // Getters & Setters

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static User getTestUser() {
        User u = new User();
        u.setId("102363055574899025750");
        return u;
    }
}
