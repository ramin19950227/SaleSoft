/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesoft.model;

/**
 *
 * @author Ramin
 */
public class DBProperties {

    /**
     * Məlumat Bazasının istifadəçi adı Məsələn: (User)
     */
    private final String user;
    /**
     * Məlumat Bazasının Şifrəsi Məsələn: (Password)
     */
    private final String password;

    /**
     * Məlumat Bazasının Ünvanı Məsələn: (localhost) və ya Serverin İP- ünvanı
     * (192.168.1.1)
     */
    private final String host;
    /**
     * Məlumat Bazasına qoşulma Portu Məsələn: (3306)
     */
    private final Integer port;

    /**
     * Məlumat Bazasında Olan Bazamızın adı (Schema) - deyilir deyəsən
     */
    private final String dbName;

    /**
     * Məlumat Bazasına, Baza adini Qeyd edərək, Qoşulmaq üçün istifadə olunan
     * (URL). Tərkibi = host+port+dbName+user+password
     */
    private final String dbUrl;

    /**
     * Məlumat Bazasına, Baza adini Qeyd ETMƏDƏN, Qoşulmaq üçün istifadə olunan
     * (URL). Tərkibi = host+port+user+password
     */
    private final String directUrl;

    public DBProperties(String user, String password, String host, Integer port, String dbName) {
        System.out.println("com.salesoft.model.DBProperties.<init>() - Constructor");
        this.user = user;
        this.password = password;
        this.host = host;
        this.port = port;
        this.dbName = dbName;
        this.dbUrl = "jdbc:mysql://" + host + ":" + port + "/" + dbName + "?useSSL=false&user=" + user + "&password=" + password;
        this.directUrl = "jdbc:mysql://" + host + ":" + port + "/?useSSL=false&user=" + user + "&password=" + password;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getHost() {
        return host;
    }

    public Integer getPort() {
        return port;
    }

    public String getDbName() {
        return dbName;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public String getDirectUrl() {
        return directUrl;
    }

}
