//Assignment: Homework 02
//File Name : GROUP15_HW02.zip
//Full Name : Manideep Reddy Nukala, Sai Charan Reddy Vallapureddy
package com.mad.group15_hw02;

import java.io.Serializable;

public class Password implements Serializable {
    String passwordType;
    String password;

    public String getPasswordType() {
        return passwordType;
    }

    public void setPasswordType(String passwordType) {
        this.passwordType = passwordType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Password(String passwordType, String password) {
        this.passwordType = passwordType;
        this.password = password;
    }
}
