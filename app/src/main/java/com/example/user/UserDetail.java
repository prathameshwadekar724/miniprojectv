package com.example.user;

public class UserDetail {
    public String Name,Contact,Address,Email,Password,City,State,Postal,Dob,Occupation,Field;

    public UserDetail(){}

    public UserDetail(String name, String contact, String address, String email, String password, String city, String state, String postal, String dob, String occupation, String field) {
        this.Name = name;
        this.Contact = contact;
        this.Address = address;
        this.Email = email;
        this.Password = password;
        this.City = city;
        this.State = state;
        this.Postal = postal;
        this.Dob = dob;
        this.Occupation = occupation;
        this.Field = field;
    }
}
