package com.example.google_authentication.Model;

public class User {
//    private String first_name;
//    private String last_name;
//    private String email;
    private String username;


    private int imgId;

    public User(String username ,int imgId) {
        this.username = username;
        this.imgId = imgId;
    }

//    public String getFirst_name() {
//        return first_name;
//    }
//
//    public void setFirst_name(String first_name) {
//        this.first_name = first_name;
//    }
//
//    public String getLast_name() {
//        return last_name;
//    }
//
//    public void setLast_name(String last_name) {
//        this.last_name = last_name;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }



}
