/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author admin
 */
public class Brand {
    private String id;
    private String name;
    private String soundBrand;
    private Double price;

    public Brand(String id, String name, String soundBrand, Double price) {
        this.id = id;
        this.name = name;
        this.soundBrand = soundBrand;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSoundBrand() {
        return soundBrand;
    }

    public void setSoundBrand(String soundBrand) {
        this.soundBrand = soundBrand;
    }

    public Double getPrice() {
        return price;
    }
    
    public String getPrice2(){
        return price + "B";
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("%-10s | %-40s | %-15s | %-10s", id, name, soundBrand,price+"B");
    }
    
     public String toChoose() {
        return id +" - " + name+" - " + soundBrand +" - " +price+"B";
    }
     
     public String toSave() {
        return id +" , " + name+" ," + soundBrand +" : " +price+"B";
    }
    
    
}




