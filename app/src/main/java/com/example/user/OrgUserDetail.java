package com.example.user;

public class OrgUserDetail {
    public String Name,License,Contact,Address,Email,Password,Type;


    public OrgUserDetail() {
    }

    public OrgUserDetail(String name, String license,  String address,String contact, String email, String password, String type) {
        Name = name;
        License=license;
        Contact = contact;
        Address = address;
        Email = email;
        Password = password;
        Type = type;
    }
}
