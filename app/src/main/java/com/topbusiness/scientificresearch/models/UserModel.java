package com.topbusiness.scientificresearch.models;

import java.io.Serializable;

public class UserModel implements Serializable {
    private String user_id;
    private String user_name;
    private String user_username;
    private String user_pass;
    private String user_email;
    private String user_phone;
    private String user_country;
    private String degree;
    private String company;
    private String specialization;
    private String user_type;
    private int message;

    public UserModel()
    {

    }

    public UserModel(String user_id, String user_name, String user_username, String user_pass, String user_email, String user_phone, String user_country, String degree, String company, String specialization, String user_type) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_username = user_username;
        this.user_pass = user_pass;
        this.user_email = user_email;
        this.user_phone = user_phone;
        this.user_country = user_country;
        this.degree = degree;
        this.company = company;
        this.specialization = specialization;
        this.user_type = user_type;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_username() {
        return user_username;
    }

    public void setUser_username(String user_username) {
        this.user_username = user_username;
    }

    public String getUser_pass() {
        return user_pass;
    }

    public void setUser_pass(String user_pass) {
        this.user_pass = user_pass;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getUser_country() {
        return user_country;
    }

    public void setUser_country(String user_country) {
        this.user_country = user_country;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public int getMessage() {
        return message;
    }

    public void setMessage(int message) {
        this.message = message;
    }
}
