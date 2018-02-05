package com.lookforpet.pet.data;

import android.util.Log;

/**
 * Created by Student on 2018/1/17.
 */

public class PetData {

    //照片

    //user 輸入只會有一筆
    public  String petName;
    public  String petKind;
    public  String petAge;
    public String petSex;
    public String petType;
    public String petCity;
    public String petArea;
    public  String petAddress;
    public String ownerName;
    public String ownerTel;
    public String ownerLine;
    public String ownerEmail;
    public String ownerRemarks;
//    public String year;
//    public String month;
//    public String day;
    public String date;

    public PetData()
    {

    }

//    public PetData(String petName, String petKind, String petAge)
//    {
//        this.petName = petName;
//        this.petKind = petKind;
//        this.petAge = petAge;
//        Log.d("PetData_petName", this.petName);
//        Log.d("PetData_petKind",  this.petKind);
//        Log.d("PetData_petAge",   this.petAge);
//
//    }


    public PetData(String petName, String petKind, String petAge,String petSex,String petType, String petCity,String petArea,String petAddress,
                   String ownerName, String ownerTel,   String ownerLine , String ownerEmail, String date)
    {
        this.petName = petName;
        this.petKind = petKind;
        this.petAge = petAge;
        this.petSex = petSex;
        this.petType=petType;
        this.petCity=petCity;
        this.petArea=petArea;
        this.petAddress=petAddress;
        this.ownerName=ownerName;
        this.ownerTel=ownerTel;
        this.ownerLine=ownerLine;
        this.ownerEmail=ownerEmail;
        this.date=date;
        Log.d("PetData_petName", this.petName);
        Log.d("PetData_petKind",  this.petKind);
        Log.d("PetData_petCity",   this.petCity);
        Log.d("PetData_petArea",   this.petArea);
    }


    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getPetKind() {
        return petKind;
    }

    public void setPetKind(String petKind) {
        this.petKind = petKind;
    }

    public String getPetAge() {
        return petAge;
    }

    public void setPetAge(String petAge) {
        this.petAge = petAge;
    }

    public String getPetSex() {
        return petSex;
    }

    public void setPetSex(String petSex) {
        this.petSex = petSex;
    }

    public String getPetType() {
        return petType;
    }

    public void setPetType(String petType) {
        this.petType = petType;
    }

    public String getPetCity() {
        return petCity;
    }

    public void setPetCity(String petCity) {
        this.petCity = petCity;
    }

    public String getPetArea() {
        return petArea;
    }

    public void setPetArea(String petArea) {
        this.petArea = petArea;
    }

    public String getPetAddress() {
        return petAddress;
    }

    public void setPetAddress(String petAddress) {
        this.petAddress = petAddress;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerTel() {
        return ownerTel;
    }

    public void setOwnerTel(String ownerTel) {
        this.ownerTel = ownerTel;
    }

    public String getOwnerLine() {
        return ownerLine;
    }

    public void setOwnerLine(String ownerLine) {
        this.ownerLine = ownerLine;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public String getOwnerRemarks() {
        return ownerRemarks;
    }

    public void setOwnerRemarks(String ownerRemarks) {
        this.ownerRemarks = ownerRemarks;
    }
//
//    public String getYear() {
//        return year;
//    }
//
//    public void setYear(String year) {
//        this.year = year;
//    }
//
//    public String getMonth() {
//        return month;
//    }
//
//    public void setMonth(String month) {
//        this.month = month;
//    }
//
//    public String getDay() {
//        return day;
//    }
//
//    public void setDay(String day) {
//        this.day = day;
//    }


}
