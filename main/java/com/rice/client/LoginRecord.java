package com.rice.client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class LoginRecord {
    private String ip = "127.0.0.1";
    private int port  = 65525;
    private String username;
    private String password;

    public LoginRecord(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public LoginRecord(String ip, int port, String username, String password) {
        this.ip = ip;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
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

    @Override
    public String toString() {
        return "LoginRecord{" +
                "ip='" + ip + '\'' +
                ", port=" + port +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String toJson() {
        List<LoginRecord> list = new ArrayList<>();
        list.add(this);
        Gson gson = new Gson();
        Type type = new TypeToken<List<LoginRecord>>() {}.getType();
        return gson.toJson(list, type);
    }
}
