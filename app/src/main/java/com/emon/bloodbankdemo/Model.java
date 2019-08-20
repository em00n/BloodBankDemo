package com.emon.bloodbankdemo;

public class Model {
    private String name,mobile,disc,blood,lastDonate,uid;

    public Model() {
    }

    public Model(String name, String mobile, String disc, String blood,String lastDonate,String uid) {
        this.name = name;
        this.mobile = mobile;
        this.disc = disc;
        this.blood = blood;
        this.lastDonate=lastDonate;
        this.uid=uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getLastDonate() {
        return lastDonate;
    }

    public void setLastDonate(String lastDonate) {
        this.lastDonate = lastDonate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }



    public String getDisc() {
        return disc;
    }

    public void setDisc(String disc) {
        this.disc = disc;
    }

    public String getBlood() {
        return blood;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }
}
