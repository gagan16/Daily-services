package com.example.gagan.dailyserices.Beans;

/**
 * Created by gagan on 19/3/17.
 */

public class RetrieveUserBean {

    String NAME,EMAIL,CITY,MOBILE,PASSWORD;
    public RetrieveUserBean() {
        this.NAME = "";
        this.EMAIL = "";
        this.CITY = "";
        this.MOBILE = "";
        this.PASSWORD = "";
    }

    public RetrieveUserBean(String NAME, String EMAIL, String CITY, String MOBILE, String PASSWORD) {
        this.NAME = NAME;
        this.EMAIL = EMAIL;
        this.CITY = CITY;
        this.MOBILE = MOBILE;
        this.PASSWORD = PASSWORD;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getCITY() {
        return CITY;
    }

    public void setCITY(String CITY) {
        this.CITY = CITY;
    }

    public String getMOBILE() {
        return MOBILE;
    }

    public void setMOBILE(String MOBILE) {
        this.MOBILE = MOBILE;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }
}
