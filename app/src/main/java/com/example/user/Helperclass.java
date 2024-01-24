package com.example.user;

public class Helperclass {
    String editName,editContact,editTextUsername,editTextPassword;

    public String getEditName() {
        return editName;
    }

    public void setEditName(String editName) {
        this.editName = editName;
    }

    public String getEditContact() {
        return editContact;
    }

    public void setEditContact(String editContact) {
        this.editContact = editContact;
    }

    public String getEditTextUsername() {
        return editTextUsername;
    }

    public void setEditTextUsername(String editTextUsername) {
        this.editTextUsername = editTextUsername;
    }

    public String getEditTextPassword() {
        return editTextPassword;
    }

    public void setEditTextPassword(String editTextPassword) {
        this.editTextPassword = editTextPassword;
    }

    public Helperclass(String editName, String editContact, String editTextUsername, String editTextPassword) {
        this.editName = editName;
        this.editContact = editContact;
        this.editTextUsername = editTextUsername;
        this.editTextPassword = editTextPassword;
    }
}
