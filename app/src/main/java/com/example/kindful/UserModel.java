package com.example.kindful;

public class UserModel {
    private String fullname;
    private String username;
    private String email;
    private Boolean isDon;
    public UserModel(){

    }
    public UserModel(String fullname,String username,String email,Boolean isDon){
        this.fullname=fullname;
        this.username=username;
        this.email=email;
        this.isDon=isDon;
    }
}
