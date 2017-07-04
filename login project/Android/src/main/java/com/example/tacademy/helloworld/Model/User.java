package com.example.tacademy.helloworld.Model;

import static android.R.attr.password;

/**
 * Created by Tacademy on 2017-06-30.
 */

//"하나의 row"가 담기는 그릇이 됨.
public class User {
     int id;
     String email;
     String password;
     String date;
     String number;
     String os;
     String model;
     String uuid;
     String token;

    public User(){

    }

    @Override
    public String toString() {
        return "User{" +
                "date='" + date + '\'' +
                ", id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", number='" + number + '\'' +
                ", os='" + os + '\'' +
                ", model='" + model + '\'' +
                ", uuid='" + uuid + '\'' +
                ", token='" + token + '\'' +
                '}';
    }

    public User(String date, String email, int id, String model, String number, String os, String password, String token, String uuid) {
        this.date = date;
        this.email = email;
        this.id = id;
        this.model = model;
        this.number = number;
        this.os = os;
        this.password = password;
        this.token = token;
        this.uuid = uuid;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
