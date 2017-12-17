package com.henallux.namikot.Model;

/**
 * Created by Maurine on 16/12/2017.
 */

public class Building {
    private String cityName;
    private int floorRoom;
    private String numberOfTheHouse;
    private int postCode;
    private String street;
    private double longitude;
    private double latitude;

    public Building(){

    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setFloorRoom(int floorRoom) {
        this.floorRoom = floorRoom;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setNumberOfTheHouse(String numberOfTheHouse) {
        this.numberOfTheHouse = numberOfTheHouse;
    }

    public void setPostCode(int postCode) {
        this.postCode = postCode;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getFloorRoom() {
        return floorRoom;
    }

    public int getPostCode() {
        return postCode;
    }

    public String getCityName() {
        return cityName;
    }

    public String getStreet() {
        return street;
    }

    public String getNumberOfTheHouse() {
        return numberOfTheHouse;
    }

    @Override
    public String toString() {
        return getNumberOfTheHouse() + ", " + getStreet() + " - " + getPostCode() + " " + getCityName();
    }
}
