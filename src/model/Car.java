/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author admin
 */
public class Car {
    private String id;
    private String brandID;
    private String color;
    private String frameID;
    private String engineID;

    public Car(String id, String brandID, String color, String frameID, String engineID) {
        this.id = id;
        this.brandID = brandID;
        this.color = color;
        this.frameID = frameID;
        this.engineID = engineID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrandID() {
        return brandID;
    }

    public void setBrandID(String brandID) {
        this.brandID = brandID;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getFrameID() {
        return frameID;
    }

    public void setFrameID(String frameID) {
        this.frameID = frameID;
    }

    public String getEngineID() {
        return engineID;
    }

    public void setEngineID(String engineID) {
        this.engineID = engineID;
    }
    
    @Override
    public String toString() {
        return String.format(String.format("%-10s | %-15s | %-10s | %-10s | %-10s", id, brandID, color,frameID,engineID));
    }
    
     public String toSave() {
        return id +"," + brandID  +"," + color  +"," + frameID  +"," +engineID;
    }
    
    
}
