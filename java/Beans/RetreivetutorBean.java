package com.example.gagan.dailyserices.Beans;

/**
 * Created by gagan on 18/3/17.
 */

public class RetreivetutorBean {
    String Name;
    String Email;
    String Password;
    String Mobile;
    String Dob;
    String Charges;
    String Occupation;

    public RetreivetutorBean(String name, String email, String password, String mobile, String dob, String charges, String occupation, String qualification, String experience, String timing2, String timing1, String hometutor, String city, String address, String latitude, String longitude) {
        Name = name;
        Email = email;
        Password = password;
        Mobile = mobile;
        Dob = dob;
        Charges = charges;
        Occupation = occupation;
        Qualification = qualification;
        Experience = experience;
        Timing2 = timing2;
        Timing1 = timing1;
        Hometutor = hometutor;
        City = city;
        Address = address;
        Latitude = latitude;
        Longitude = longitude;
    }

    String Qualification;
    String Experience;
    String Timing2;
    String Timing1;
    String Hometutor;
    String City;
    String Address;
    String Latitude;
    String Longitude;


    public RetreivetutorBean() {
        Name = "";
        Email = "";
        Password="";
        Mobile = "";
        Dob = "";
        Charges = "";
        Qualification = "";
        Occupation="";
        Experience ="";
        Timing2 = "";
        Timing1 = "";
        Hometutor = "";
        City = "";
        Address = "";
        Latitude = "";
        Longitude = "";
    }

    public String getOccupation() {
        return Occupation;
    }

    public void setOccupation(String occupation) {
        Occupation = occupation;
    }

    public String getName() {

        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getDob() {
        return Dob;
    }

    public void setDob(String dob) {
        Dob = dob;
    }

    public String getCharges() {
        return Charges;
    }

    public void setCharges(String charges) {
        Charges = charges;
    }

    public String getQualification() {
        return Qualification;
    }

    public void setQualification(String qualification) {
        Qualification = qualification;
    }

    public String getExperience() {
        return Experience;
    }

    public void setExperience(String experience) {
        Experience = experience;
    }

    public String getTiming2() {
        return Timing2;
    }

    public void setTiming2(String timing2) {
        Timing2 = timing2;
    }

    public String getTiming1() {
        return Timing1;
    }

    public void setTiming1(String timing1) {
        Timing1 = timing1;
    }

    public String getHometutor() {
        return Hometutor;
    }

    public void setHometutor(String hometutor) {
        Hometutor = hometutor;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }
}
