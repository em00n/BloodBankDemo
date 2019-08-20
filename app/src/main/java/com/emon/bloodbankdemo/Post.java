package com.emon.bloodbankdemo;

public class Post {
    String bloodgroup,date,location,number,details,uid;

    public Post() {
    }

    public Post(String bloodgroup, String date, String location, String number, String details,String uid) {
        this.bloodgroup = bloodgroup;
        this.date = date;
        this.location = location;
        this.number = number;
        this.details = details;
        this.uid=uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getBloodgroup() {
        return bloodgroup;
    }

    public void setBloodgroup(String bloodgroup) {
        this.bloodgroup = bloodgroup;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
