package org.example.backend.Entity;

public class User {
    private
    Integer id;
    String name = "default";
    String email;
    String password;
    char[] biography = new char[100];

    public char[] getBiography() {
        return biography;
    }

    public void setBiography(char[] biography) {
        this.biography = biography;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}