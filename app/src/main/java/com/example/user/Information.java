package com.example.user;

public class Information {
    private String Name;
    private String Contact;
    private String Address;
    private String Email;
    private String Password;

    private String Dob;
    private String City;
    private String State;
    private String Occupation;
    private String Type;
    private String License;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
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

    public String getDob() {
        return Dob;
    }

    public void setDob(String dob) {
        Dob = dob;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getOccupation() {
        return Occupation;
    }

    public void setOccupation(String occupation) {
        Occupation = occupation;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getLicense() {
        return License;
    }

    public void setLicense(String license) {
        License = license;
    }

    public String getPostal() {
        return Postal;
    }

    public void setPostal(String postal) {
        Postal = postal;
    }

    public String getField() {
        return Field;
    }

    public void setField(String field) {
        Field = field;
    }

    public Information(String name, String contact, String address, String email, String password, String dob, String city, String state, String occupation, String type, String license, String postal, String field) {
        Name = name;
        Contact = contact;
        Address = address;
        Email = email;
        Password = password;
        Dob = dob;
        City = city;
        State = state;
        Occupation = occupation;
        Type = type;
        License = license;
        Postal = postal;
        Field = field;
    }

    private String Postal;
    private String Field;
    public Information() {
    }

}