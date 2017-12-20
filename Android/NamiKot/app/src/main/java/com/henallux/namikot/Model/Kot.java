package com.henallux.namikot.Model;

/**
 * Created by Maurine on 17/12/2017.
 */

public class Kot {
    private int id;
    private double surface;
    private boolean isDesignForReducedMobility;
    private double monthlyPriceNoChargesIncluded;
    private double monthlyPriceWithAllCharges;
    private boolean hasPrivateBathroom;
    private boolean hasPrivateKitchen;
    private boolean hasEquippedKitchen;
    private Building building;

    public boolean isDesignForReducedMobility() {
        return isDesignForReducedMobility;
    }

    public boolean isHasEquippedKitchen() {
        return hasEquippedKitchen;
    }

    public boolean isHasPrivateBathroom() {
        return hasPrivateBathroom;
    }

    public boolean isHasPrivateKitchen() {
        return hasPrivateKitchen;
    }

    public Building getBuilding() {
        return building;
    }

    public double getMonthlyPriceNoChargesIncluded() {
        return monthlyPriceNoChargesIncluded;
    }

    public double getMonthlyPriceWithAllCharges() {
        return monthlyPriceWithAllCharges;
    }

    public double getSurface() {
        return surface;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public void setDesignForReducedMobility(boolean designForReducedMobility) {
        isDesignForReducedMobility = designForReducedMobility;
    }

    public void setHasEquippedKitchen(boolean hasEquippedKitchen) {
        this.hasEquippedKitchen = hasEquippedKitchen;
    }

    public void setHasPrivateBathroom(boolean hasPrivateBathroom) {
        this.hasPrivateBathroom = hasPrivateBathroom;
    }

    public void setHasPrivateKitchen(boolean hasPrivateKitchen) {
        this.hasPrivateKitchen = hasPrivateKitchen;
    }

    public void setMonthlyPriceNoChargesIncluded(double monthlyPriceNoChargesIncluded) {
        this.monthlyPriceNoChargesIncluded = monthlyPriceNoChargesIncluded;
    }

    public void setMonthlyPriceWithAllCharges(double monthlyPriceWithAllCharges) {
        this.monthlyPriceWithAllCharges = monthlyPriceWithAllCharges;
    }

    public void setSurface(double surface) {
        this.surface = surface;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Kot n°" + getId() +" : " + getBuilding();
    }
}
