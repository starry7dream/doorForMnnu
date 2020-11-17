package com.cj.domain;

import java.io.Serializable;

public class User implements Serializable {
    private  Integer id;
    private String username;
    private String password;
    private String college;
    private String email;
    private String number;
    private String ip;//只取前三部分 xxx.xxx.xxx

    public User(String username, String password, String college, String email, String ip) {
        this.username = username;
        this.password = password;
        this.college = college;
        this.email = email;
        this.ip = ip;
    }

    public User(String username, String password, String college, String number) {
        this.username = username;
        this.password = password;
        this.college = college;
        this.number = number;
    }

    public User(String username, String password, String college, String email, String number, String ip) {
        this.username = username;
        this.password = password;
        this.college = college;
        this.email = email;
        this.number = number;
        this.ip = ip;
    }

    public User(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", college='" + college + '\'' +
                ", email='" + email + '\'' +
                ", number='" + number + '\'' +
                ", ip='" + ip + '\'' +
                '}';
    }
}
