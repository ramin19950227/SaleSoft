/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Ramin
 */
public class User {

    private final IntegerProperty id;
    private final StringProperty userName;
    private final StringProperty userPassword;
    private final StringProperty fullName;
    private final IntegerProperty status;

    public User(Integer id, String userName, String userPassword, String fullName, Integer status) {
        this.id = new SimpleIntegerProperty(id);
        this.userName = new SimpleStringProperty(userName);
        this.userPassword = new SimpleStringProperty(userPassword);
        this.fullName = new SimpleStringProperty(fullName);
        this.status = new SimpleIntegerProperty(status);
    }

    public final void setId(Integer value) {
        id.set(value);
    }

    public final Integer getId() {
        return id.get();
    }

    public final IntegerProperty idProperty() {
        return id;
    }

    public final void setUserName(String value) {
        userName.set(value);
    }

    public final String getUserName() {
        return userName.get();
    }

    public final StringProperty userNameProperty() {
        return userName;
    }

    public final void setUserPassword(String value) {
        userPassword.set(value);
    }

    public final String getUserPassword() {
        return userPassword.get();
    }

    public final StringProperty userPasswordProperty() {
        return userPassword;
    }

    public final void setFullName(String value) {
        fullName.set(value);
    }

    public final String getFullName() {
        return fullName.get();
    }

    public final StringProperty fullNameProperty() {
        return fullName;
    }

    public final void setStatus(Integer value) {
        status.set(value);
    }

    public final Integer getStatus() {
        return status.get();
    }

    public final IntegerProperty statusProperty() {
        return status;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", userName=" + userName + ", userPassword=" + userPassword + ", fullName=" + fullName + ", status=" + status + '}';
    }

    public void println() {
        System.out.println(toString());
    }

}
