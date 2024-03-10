package com.example.user;

public class OrgUserDetail {
    public String Name,License,Contact,Address,Email,Password,Type;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLicense() {
        return License;
    }

    public void setLicense(String license) {
        License = license;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

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
