package com.example.gagan.dailyserices.Beans;

/**
 * Created by gagan on 22/2/17.
 */

public class Tutorbean {
    public static String Name="";
    public static String Email="";
    public static String Password="";
    public static String Dob="";
    public static String Mobile="";
    public static String Occupation="";
    public static String Timing1="",Timing2="";
    public static String Address="";
    public static String Latitude="30.9010",Longitide="75.8573";
    public static String Charges="";
    public static String Qualification="";
    public static String Description="";
    public static String City="";

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public static  String Experience;


    public String getTiming1() {
        return Timing1;
    }

    public void setTiming1(String timing1) {
        Timing1 = timing1;
    }

    public String getTiming2() {
        return Timing2;
    }

    public void setTiming2(String timing2) {
        Timing2 = timing2;
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

    public String getLongitide() {
        return Longitide;
    }

    public void setLongitide(String longitide) {
        Longitide = longitide;
    }

    public String getHometutor() {

        return Hometutor;
    }

    public void setHometutor(String hometutor) {
        Hometutor = hometutor;
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

    public static String Hometutor;

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

    public String getDob() {
        return Dob;
    }

    public void setDob(String dob) {
        Dob = dob;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getOccupation() {
        return Occupation;
    }

    public void setOccupation(String occupation) {
        Occupation = occupation;
    }



    public String getCharges() {
        return Charges;
    }

    public void setCharges(String charges) {
        Charges = charges;
    }
    public String validatePerson(){
        if(Name.isEmpty()){
            //    Toast.makeText(RegisterActivity.this,name,Toast.LENGTH_LONG).show();
            return "Please enter name";

        }
        if(Email.isEmpty()){

            return "Please enter email id ";
        }
        if(Mobile.isEmpty()){

            return "Please enter mobile";
        }

        if(City.isEmpty()){
            return "Please enter dob";
        }
        if(Password.isEmpty()){

            return "Please enter password";
        }
        if(Mobile.length()<10){
            return " Please check the mobile number";
        }
        if(Password.length()<6){
            return "password should be more than 6 letter";
        }
        return "abc";
    }
    public String validatePerson2(){
        if(Experience.isEmpty()){
            //    Toast.makeText(RegisterActivity.this,name,Toast.LENGTH_LONG).show();
            return "Please Choose Experience";

        }
        if(Qualification.isEmpty()){

            return "Please fill qualification";
        }
        if(Occupation.isEmpty()){

            return "Please choose occupation";
        }

        if(Timing1.isEmpty()){
            return "Please choose start time";
        }
        if(Timing1.isEmpty()){
            return "Please choose end time";
        }
        if(Charges.isEmpty()){

            return "Please enter charges";
        }
        if(Hometutor.isEmpty()){

            return "Please choose hometutor";
        }

        return "abc";
    }
}
