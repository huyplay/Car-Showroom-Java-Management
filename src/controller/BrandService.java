/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.Brand;

/**
 *
 * @author admin
 */
public class BrandService {

    private static final String BRAND_PATH = "src/data/brands.txt";
    private boolean CHANGE = false;
    private final ArrayList<Brand> brands = new ArrayList<>();

    public void importDataBrand() {
        List<String[]> brandData = ImportData.from(BRAND_PATH);
        for (String[] columns : brandData) {
            if (columns.length >= 3) {
                boolean isSave = true;
                String id = columns[0].trim();
                String name = columns[1].trim();
                String soundBrand = "";
                String price;
                double valuePrice = 0.0;

                if (id == null || id.isEmpty()) {
                    isSave = false;
                }
                if (checkBrandtByID(id)) {
                    isSave = false;
                }
                if (name == null || name.isEmpty()) {
                    isSave = false;
                }

                if (columns[2] != null && columns[2].contains(":") && !columns[2].contains("error")) {
                    String[] text = columns[2].split(":");
                    if (text.length >= 1) {
                        soundBrand = text[0] != null ? text[0].trim() : "";
                        price = text[1] != null ? text[1].trim() : "";
                        valuePrice = InputValidator.getDoublePrice(price);
                    } else {
                        isSave = false;
                    }
                    if (soundBrand == null || soundBrand.isEmpty()) {
                        isSave = false;
                    }

                    if (valuePrice <= 0) {
                        isSave = false;
                    }
                } else {
                    isSave = false;
                }

                if (isSave) {
                    Brand brand = new Brand(id, name, soundBrand, valuePrice);
                    brands.add(brand);
                }
            }
        }
        //System.out.println("Update " + mountains.size() + " mountain from file csv successfuly!");
    }

    public void importDataBrand2() {
        List<String[]> brandData = ImportData.from(BRAND_PATH);
        for (String[] columns : brandData) {
            if (columns.length >= 1) {
                switch (columns.length) {
                    case 1: {
                        boolean isSave = true;
                        String id = columns[0].trim();

                        if (checkBrandtByID(id)) {
                            isSave = false;
                        }

                        if (isSave) {
                            Brand brand = new Brand(id, "", "", 0.0);
                            brands.add(brand);
                        }
                        break;
                    }
                    //System.out.println(id);
                    case 2: {
                        boolean isSave = true;
                        String id = columns[0].trim();
                        String name = columns[1].trim();
                        if (checkBrandtByID(id)) {
                            isSave = false;
                        }

                        if (isSave) {
                            Brand brand = new Brand(id, name, "", 0.0);
                            brands.add(brand);
                        }
                        break;
                    }
                    default:
                        boolean isSave = true;
                        String id = columns[0].trim();
                        String name = columns[1].trim();
                        String soundBrand = "";
                        String price;
                        double valuePrice = 0.0;

                        if (columns[2] != null && columns[2].contains(":") && !columns[2].contains("error")) {
                            String[] text = columns[2].split(":");
                            if (text.length >= 1) {
                                soundBrand = text[0] != null ? text[0].trim() : "";
                                price = text[1] != null ? text[1].trim() : "";
                                valuePrice = InputValidator.getDoublePrice(price);
                            } else {
                                soundBrand = "";
                                valuePrice = 0.0;
                            }

                            if (valuePrice <= 0) {
                                isSave = false;
                            }
                        } else {
                            isSave = false;
                        }

                        if (isSave) {
                            Brand brand = new Brand(id, name, soundBrand, valuePrice);
                            brands.add(brand);
                        }

                        break;
                }
            }
        }

    }

    public void displayAll() {
        System.out.println("");
        System.out.println("List all brands:");
        if (brands.isEmpty()) {
            System.err.println("No brand in data.");
            return;
        }
        System.out.println("---------------------------------------------------------------------------------------");
        System.out.println(String.format("%-10s | %-40s | %-15s | %-10s", "Brand ID", "Brand Name", "Sound Brand", "Price"));
        System.out.println("---------------------------------------------------------------------------------------");
        for (Brand b : brands) {
            System.out.println(b);
        }
        System.out.println("---------------------------------------------------------------------------------------");
    }

    public void addBrand(Scanner sc) {
        System.out.println("==Add New Brand ==");

        // Enter Brand ID
        String brandID;
        while (true) {
            System.out.print("Enter Brand ID (Enter CANCEL to return menu):");

            brandID = sc.nextLine().toUpperCase();

            if (brandID == null || brandID.isEmpty()) {
                System.err.println("Brand ID cannot be empty!");
                continue;
            }

            if (brandID.equalsIgnoreCase("CANCEL")) {
                return;
            }

            if (checkBrandtByID(brandID) == true) {
                System.err.println("Brand ID already exists!");
                continue;
            }
            break;
        }

        // Enter Brand Name
        String brandName;
        while (true) {
            System.out.print("Enter Brand name (Enter CANCEL to return menu):");

            brandName = sc.nextLine();

            if (brandName == null || brandName.isEmpty()) {
                System.err.println("Brand name cannot be empty!");
                continue;
            }

            if (brandName.equalsIgnoreCase("CANCEL")) {
                return;
            }

            break;
        }

        // Enter Brand Name
        String soundBrand;
        while (true) {
            System.out.print("Enter sound manufacturers (Enter CANCEL to return menu):");

            soundBrand = sc.nextLine();

            if (soundBrand == null || soundBrand.isEmpty()) {
                System.err.println("Sound manufactures cannot be empty!");
                continue;
            }

            if (soundBrand.equalsIgnoreCase("CANCEL")) {
                return;
            }

            break;
        }

        // Enter Brand Price
        String brandPrice;
        double valuePrice = 0;
        while (true) {
            System.out.println("You can enter CANCEL to return menu!");
            System.out.print("Enter price (Ex: 1.234 means that 1.234 billion(s)$):");

            brandPrice = sc.nextLine();

            if (brandPrice.equalsIgnoreCase("CANCEL")) {
                return;
            }

            if (!InputValidator.isPositiveDouble(brandPrice)) {
                System.err.println("Price must be a positive real number. Ex: 1.234 means that 1.234 billion(s)$:");
                continue;
            }

            valuePrice = Double.parseDouble(brandPrice);

            break;
        }

        brands.add(new Brand(brandID, brandName, soundBrand, valuePrice));
        CHANGE = true;
        System.out.println("Add a new brand: " + brandID + " success!");

    }

    public void searchBrandByID(Scanner sc) {

        System.out.println("== Search Brand By ID==");

        while (true) {
            System.out.print("Enter ID Brand to search: ");
            String keyword = sc.nextLine().toLowerCase();
            boolean found = false;

            ArrayList<Brand> brandSearch = new ArrayList<>();
            for (Brand b : brands) {
                if (b.getId().toLowerCase().equalsIgnoreCase(keyword)) {
                    brandSearch.add(b);
                    found = true;
                }
            }

            if (!found) {
                System.err.println("This brand does not exist!");
                break;

            } else {
                System.out.println("== Matching Brands ==");
                System.out.println("---------------------------------------------------------------------------------------");
                System.out.println(String.format("%-10s | %-40s | %-15s | %-10s", "Brand ID", "Brand Name", "Sound Brand", "Price"));
                System.out.println("---------------------------------------------------------------------------------------");
                for (Brand b : brandSearch) {

                    System.out.println(b.toString());

                }
                System.out.println("---------------------------------------------------------------------------------------");
            }
            break;
        }

    }

    public void updateBrandByID(Scanner sc) {
        boolean found = false;

        System.out.println("== Update Brand By ID==");

        // Enter Brand ID
        String brandID;
        while (!found) {
            System.out.print("Enter Brand ID (Enter CANCEL to return menu):");

            brandID = sc.nextLine().toUpperCase();

            if (brandID == null || brandID.isEmpty()) {
                break;
            }

            if (brandID.equalsIgnoreCase("CANCEL")) {
                return;
            }

            for (Brand b : brands) {
                if (b.getId().equalsIgnoreCase(brandID)) {
                    found = true;

                    displayBrandUpdate(b);

                    System.out.println("You can Enter to NOT update that information field!");

                    // Enter Brand Name
                    String brandName;
                    while (true) {
                        System.out.print("Enter Brand name:");

                        brandName = sc.nextLine();

                        if (brandName == null || brandName.isEmpty()) {
                            break;
                        }

                        b.setName(brandName);
                        break;
                    }

                    // Enter Brand Sound
                    String soundBrand;
                    while (true) {
                        System.out.print("Enter sound manufacturers:");

                        soundBrand = sc.nextLine();

                        if (soundBrand == null || soundBrand.isEmpty()) {
                            break;
                        }

                        b.setSoundBrand(soundBrand);
                        break;

                    }

                    // Enter Brand Price
                    String brandPrice;
                    double valuePrice;
                    while (true) {
                        System.out.print("Enter price (Ex: 1.234 means that 1.234 billion(s)$):");

                        brandPrice = sc.nextLine();

                        if (brandPrice == null || brandPrice.isEmpty()) {
                            break;
                        }

                        if (!InputValidator.isPositiveDouble(brandPrice)) {
                            System.err.println("Price must be a positive real number. Ex: 1.234 means that 1.234 billion(s)$:");
                            continue;
                        }

                        valuePrice = Double.parseDouble(brandPrice);

                        b.setPrice(valuePrice);
                        break;
                    }
                    CHANGE = true;
                    System.out.println("Update for brand ID: " + brandID + " successfuly!");
                    break;

                }
            }

            if (!found) {
                System.err.println("This brand does not exist!");
                break;
            }

            break;
        }

    }

    public void searchBrandByPrice(Scanner sc) {
        boolean found = false;
        System.out.println("== List all brands with prices less than or equal to an input value:==");
        while (true) {
            System.out.print("Enter price (Ex: 1.234 means that 1.234 billion(s)$):");

            String brandPrice = sc.nextLine();

            if (brandPrice == null || brandPrice.isEmpty()) {
                break;
            }

            if (!InputValidator.isPositiveDouble(brandPrice)) {
                System.err.println("Price must be a positive real number. Ex: 1.234 means that 1.234 billion(s)$:");
                continue;
            }
            double valuePrice = Double.parseDouble(brandPrice);

            ArrayList<Brand> brandSearch = new ArrayList<>();
            for (Brand b : brands) {
                if (b.getPrice() <= valuePrice) {
                    brandSearch.add(b);
                    found = true;
                }
            }

            if (!found) {
                System.err.println("Brand have price <= " + brandPrice + "B does not exist!");
                break;

            } else {
                System.out.println("== Matching Brands ==");
                System.out.println("----------------------------------------------------------------------------------");
                System.out.println(String.format("%-10s | %-40s | %-15s | %-10s", "Brand ID", "Brand Name", "Sound Brand", "Price"));
                System.out.println("----------------------------------------------------------------------------------");
                for (Brand b : brandSearch) {

                    System.out.println(b.toString());

                }
                System.out.println("----------------------------------------------------------------------------------");
            }
            break;
        }

    }

    public void saveData() {
        try ( BufferedWriter writer = new BufferedWriter(new FileWriter(BRAND_PATH))) {
            for (Brand b : brands) {
                CHANGE = false;
                writer.write(b.toSave());
                writer.newLine(); // xuống dòng mỗi brand
            }
            System.out.println("Saved Brand list to file: " + BRAND_PATH);
        } catch (IOException e) {
            System.err.println("Error for save file brand: " + e.getMessage());
        }
    }

    public boolean checkChange() {
        return CHANGE;
    }

    public void displayBrandUpdate(Brand b) {
        System.out.println("");
        System.out.println("Brand Details:");
        System.out.println("------------------------------------------------------");
        System.out.println(String.format("%-10s %-20s", "Brand ID", ": " + b.getId()));
        System.out.println(String.format("%-10s %-20s", "Brand Name", ": " + b.getName()));
        System.out.println(String.format("%-10s %-20s", "Sound Brand", ": " + b.getSoundBrand()));
        System.out.println(String.format("%-10s %-20s", "Price", ": " + b.getPrice2()));
        System.out.println("------------------------------------------------------");

    }

    public void displayBrandChoose() {
        if (brands.isEmpty()) {
            System.err.println("List brand null.");
            return;
        }
        System.out.println("\nList of brands:");
        System.out.println("------------------------------------------------------------------------");
        int i = 1;
        for (Brand b : brands) {
            System.out.println(i + ". " + b.toChoose());
            i++;
        }
        System.out.println("------------------------------------------------------------------------");
    }

    public int countBrands() {
        return brands.size();
    }

    public boolean isIDBrand(int id) {
        int count = brands.size();
        if (id >= 0 && id <= count) {
            return true;
        } else {
            return false;
        }
    }

    public String getCodeBrand(int id) {
        Brand b = brands.get(id);
        return b.getId();
    }

    public String getBrandName(String id) {
        for (Brand b : brands) {
            if (b.getId().equalsIgnoreCase(id)) {
                return b.getName();
            }
        }
        return "";
    }

    public Double getBrandPrice(String id) {
        for (Brand b : brands) {
            if (b.getId().equalsIgnoreCase(id)) {
                return b.getPrice();
            }
        }
        return 0.0;
    }

    public boolean checkBrandtByID(String id) {
        for (Brand b : brands) {
            if (b.getId().equalsIgnoreCase(id)) {
                return true;
            }
        }
        return false;
    }

}
