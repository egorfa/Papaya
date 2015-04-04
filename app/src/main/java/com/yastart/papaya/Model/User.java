package com.yastart.papaya.Model;

public class User {
    private static User currentUser;

    private String internal_id; // used in operations with user objects (delete, update) if any
    private String id; // Test user id 102363055574899025750
    private String username;
    private String email;
    private String contacts;
    private String city;

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

    /**
     * Should return current logged user
     * @return
     */
    public static User getCurrentUser() {
        if (currentUser != null) { return currentUser; }

        currentUser = new User();
        currentUser.setId("102363055574899025750");
        return currentUser;
    }
}
