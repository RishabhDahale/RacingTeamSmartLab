package com.example.fourofour.racingteamsmartlab;

public class LoginStatus {
    private String name;
    private String status;

    public LoginStatus() {

    }

    public LoginStatus(String name, String status){
        this.name = name;
        this.status = status;
    }

    public String getName() { return name; }

    public String getStatus() { return status; }

    public void setName(String name) {
        this.name = name;
    }


}
