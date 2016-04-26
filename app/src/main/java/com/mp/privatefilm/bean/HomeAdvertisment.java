package com.mp.privatefilm.bean;

/**
 * Created by eE on 2016/4/26.
 */
public class HomeAdvertisment {

    private String ADAddress;
    private String toPath;

    public HomeAdvertisment() {
    }

    public HomeAdvertisment(String ADAddress, String toPath) {
        this.ADAddress = ADAddress;
        this.toPath = toPath;
    }

    public String getADAddress() {
        return ADAddress;
    }

    public void setADAddress(String ADAddress) {
        this.ADAddress = ADAddress;
    }

    public String getToPath() {
        return toPath;
    }

    public void setToPath(String toPath) {
        this.toPath = toPath;
    }
}
