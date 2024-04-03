package com.example.user;

public class Msg   {
    private String Name,UserEmail,OrgName,Msg,PostKey,Address,Email,Type,License,UserLocation,UserKey,PostName;

    public Msg() {
    }

    public Msg(String name, String userEmail, String orgName, String msg, String postKey, String address, String email, String type, String license, String userLocation, String userKey, String postName) {
        Name = name;
        UserEmail = userEmail;
        OrgName = orgName;
        Msg = msg;
        PostKey = postKey;
        Address = address;
        Email = email;
        Type = type;
        License = license;
        UserLocation = userLocation;
        UserKey = userKey;
        PostName = postName;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }

    public String getOrgName() {
        return OrgName;
    }

    public void setOrgName(String orgName) {
        OrgName = orgName;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }

    public String getPostKey() {
        return PostKey;
    }

    public void setPostKey(String postKey) {
        PostKey = postKey;
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

    public String getUserLocation() {
        return UserLocation;
    }

    public void setUserLocation(String userLocation) {
        UserLocation = userLocation;
    }

    public String getUserKey() {
        return UserKey;
    }

    public void setUserKey(String userKey) {
        UserKey = userKey;
    }

    public String getPostName() {
        return PostName;
    }

    public void setPostName(String postName) {
        PostName = postName;
    }
}
