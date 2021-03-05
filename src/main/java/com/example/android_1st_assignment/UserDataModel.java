package com.example.android_1st_assignment;

public class UserDataModel {

    private String id;
    private String firstName;
    private String lastName;
    private String phoneNr;
    private String dateOfBirth;
    private String email;
    private String address;
    private String latitude;
    private String longitude;

    public UserDataModel(String id, String firstName, String lastName, String phoneNr, String dateOfBirth, String email, String address, String latitude, String longitude) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNr = phoneNr;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNr() {
        return phoneNr;
    }

    public void setPhoneNr(String phoneNr) {
        this.phoneNr = phoneNr;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
