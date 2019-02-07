package org.urturn.com.urturn;

public class UserModel {
    private String phoneNo;
    private  String name;
    private String picUrl;
    private String email;
    private String address;
    private String city;

    public UserModel(String phoneNo, String name, String picUrl, String email, String address, String city, String state) {
        this.phoneNo = phoneNo;
        this.name = name;
        this.picUrl = picUrl;
        this.email = email;
        this.address = address;
        this.city = city;
        this.state = state;
    }

    public UserModel() {

    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    private String state;
}
