package com.mp.privatefilm.bean;

/**
 * Created by Zhangzhe on 2016/5/5.
 */
public class Film {

    private String ID;
    private String filmName;

    public Film() {
    }

    public Film(String filmName, String ID) {
        this.filmName = filmName;
        this.ID = ID;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }
}
