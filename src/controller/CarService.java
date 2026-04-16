/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import model.Car;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author admin
 */
public class CarService {

    private static final String CAR_PATH = "src/data/cars.txt";
    private boolean CHANGE = false;
    private final ArrayList<Car> cars = new ArrayList<>();

    public void importDataCar(BrandService brandService) {
        List<String[]> carData = ImportData.from(CAR_PATH);
        for (String[] columns : carData) {
            if (columns.length >= 5) {
                boolean isSave = true;

                String id = columns[0].trim().toUpperCase();

                String brandID = columns[1].trim().toUpperCase();

                String color = columns[2].trim();

                String frameID = columns[3].trim();

                String engineID = columns[4].trim();

                if (id == null || id.isEmpty() || checkCarByID(id)) {
                    isSave = false;
                }
                if (brandID == null || brandID.isEmpty() || !brandService.checkBrandtByID(brandID)) {
                    isSave = false;
                }
                if (color == null || color.isEmpty() || !InputValidator.isValidText(color)) {
                    isSave = false;
                }

                if (frameID == null || frameID.isEmpty() || checkCarByFrame(frameID) || !InputValidator.isFrameID(frameID)) {
                    isSave = false;
                }

                if (engineID == null || engineID.isEmpty() || checkCarByEngine(engineID) || !InputValidator.isEngineID(engineID)) {
                    isSave = false;
                }

                if (isSave) {
                    Car car = new Car(id, brandID, color, frameID, engineID);
                    cars.add(car);
                }
            }
            //System.out.println(id);

        }
    }

    public void importDataCar2(BrandService brandService) {
        List<String[]> carData = ImportData.from(CAR_PATH);
        for (String[] columns : carData) {
            if (columns.length >= 2) {
                boolean isSave = true;

                String id = columns[0].trim();

                String brandID = columns[1].trim();

                String color = (columns.length >= 3 && columns[2] != null) ? columns[2].trim() : "";

                String frameID =(columns.length >= 4 && columns[3] != null) ? columns[3].trim() : "";

                String engineID = (columns.length >= 5 && columns[4] != null) ? columns[4].trim() : "";

                if (id == null || id.isEmpty() || checkCarByID(id)) {
                    isSave = false;
                }
                if (brandID == null || brandID.isEmpty() || !brandService.checkBrandtByID(brandID)) {
                    isSave = false;
                }
                if (!color.isEmpty() && !InputValidator.isValidText(color)) {
                    isSave = false;
                }

                if (!frameID.isEmpty() && ( checkCarByFrame(frameID) || !InputValidator.isFrameID(frameID) )) {
                    isSave = false;
                }

                if (!engineID.isEmpty() && ( checkCarByEngine(engineID) || !InputValidator.isEngineID(engineID)) ) {
                    isSave = false;
                }

                  if (isSave) {
                    Car car = new Car(id, brandID, color, frameID, engineID);
                    cars.add(car);
                }
            }
            //System.out.println(id);

        }
    }

    public void ascendingBrand(BrandService brandService) {
        ArrayList<Car> carSort = new ArrayList<>(cars);

        Comparator<Car> nameComparator = new Comparator<Car>() {
            @Override
public int compare(Car c1, Car c2) {
                int result = brandService.getBrandName(c1.getBrandID()).compareTo(brandService.getBrandName(c2.getBrandID()));
                if (result == 0) {
                    return brandService.getBrandPrice(c2.getBrandID()).compareTo(brandService.getBrandPrice(c1.getBrandID()));
                }
                return result;
            }
        };
        Collections.sort(carSort, nameComparator);

        System.out.println("");
        System.out.println("List all brands:");
        if (cars.isEmpty()) {
            System.err.println("No brand in data.");
            return;
        }
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println(String.format("%-10s | %-15s | %-40s | %-10s | %-10s | %-10s | %-10s", "Car ID", "Brand ID", "Brand Name", "Price", "Color", "FrameID", "EngineID"));
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------");
        for (Car c : carSort) {
            System.out.println(String.format("%-10s | %-15s | %-40s | %-10s | %-10s | %-10s | %-10s", c.getId(), c.getBrandID(), brandService.getBrandName(c.getBrandID()), brandService.getBrandPrice(c.getBrandID()) + "B", c.getColor(), c.getFrameID(), c.getEngineID()));
        }
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------");

    }

    public void searchCarByBrand(Scanner sc, BrandService brandService) {
        System.out.println("== Search cars by partial brand name match==");

        while (true) {
            System.out.print("Enter Name Brand to search car: ");
            String keyword = sc.nextLine().toLowerCase();
            boolean found = false;

            ArrayList<Car> carSearch = new ArrayList<>();
            for (Car c : cars) {
                if (brandService.getBrandName(c.getBrandID()).toLowerCase().contains(keyword)) {
                    carSearch.add(c);
                    found = true;
                }
            }

            if (!found) {
                System.err.println("Not found car!");
                break;

            } else {
                System.out.println("== Matching Cars ==");
                System.out.println("---------------------------------------------------------------------------------------------------------------------");
                System.out.println(String.format("%-10s | %-15s | %-40s  | %-10s | %-10s | %-10s", "Car ID", "Brand ID", "Brand Name", "Color", "FrameID", "EngineID"));
                System.out.println("---------------------------------------------------------------------------------------------------------------------");
                for (Car c : carSearch) {

                    System.out.println(String.format("%-10s | %-15s | %-40s | %-10s | %-10s | %-10s", c.getId(), c.getBrandID(), brandService.getBrandName(c.getBrandID()), c.getColor(), c.getFrameID(), c.getEngineID()));

                }
                System.out.println("---------------------------------------------------------------------------------------------------------------------");
            }
            break;
        }

    }

    public void addCar(Scanner sc, BrandService brandService) {
        System.out.println("== Add New Car ==");

        // Enter Brand ID
        String carID;
        while (true) {
            System.out.print("Enter Car ID (Enter CANCEL to return menu):");

            carID = sc.nextLine().toUpperCase();

            if (carID == null || carID.isEmpty()) {
                System.err.println("Car ID cannot be empty!");
                continue;
            }

            if (carID.equalsIgnoreCase("CANCEL")) {
                return;
            }

            if (checkCarByID(carID) == true) {
                System.err.println("Car ID already exists!");
                continue;
            }
            break;
        }

        // Enter Brand id
        String brandID;
        int brandIDSTT;
        while (true) {
            brandService.displayBrandChoose();
            while (true) {
                // Enter Mountain Code
                System.out.print("Enter Brand ID (Enter CANCEL to return menu):");
                brandID = sc.nextLine().toUpperCase();

                if (brandID == null || brandID.isEmpty()) {
                    System.err.println("Brand ID cannot empty!");
                    continue;
                }

                if (brandID.equalsIgnoreCase("CANCEL")) {
                    return;
                }

                if (!InputValidator.isPositiveInteger(brandID)) {
                    System.err.println("Brand ID is invalid: Must be number!");
                    continue;
                }
                brandIDSTT = Integer.parseInt(brandID);

                //MountainService mountainService = new MountainService();
                if (brandService.isIDBrand(brandIDSTT - 1) == false) {
                    System.err.println("Brand ID is invalid. Enter 1->" + brandService.countBrands() + " to choose!");
                    continue;
                }
                brandID = brandService.getCodeBrand(brandIDSTT - 1);
                break;
            }

            // Enter Brand Name
            String colorCar;
            while (true) {
                System.out.print("Enter color (Enter CANCEL to return menu):");

                colorCar = sc.nextLine();

                if (colorCar == null || colorCar.isEmpty()) {
                    System.err.println("Color Car cannot be empty!");
                    continue;
                }

                if (colorCar.equalsIgnoreCase("CANCEL")) {
                    return;
                }

                if (!InputValidator.isValidText(colorCar)) {
                    System.err.println("Color Car not invalid!");
                    continue;
                }

                break;
            }

            // Enter Brand Price
            String FrameID;

            while (true) {
                System.out.println("You can enter CANCEL to return menu!");
                System.out.print("Enter FrameID (Ex: F00000):");

                FrameID = sc.nextLine();

                if (FrameID == null || FrameID.isEmpty()) {
                    System.err.println("FrameID cannot be empty!");
                    continue;
                }

                if (FrameID.equalsIgnoreCase("CANCEL")) {
                    return;
                }

                if (!InputValidator.isFrameID(FrameID)) {
                    System.err.println("FrameID must follow the format “F00000”,");
                    continue;
                }

                if (checkCarByFrame(FrameID)) {
                    System.err.println("Frame ID already exists!");
                    continue;
                }

                break;
            }

            String EngineID;

            while (true) {
                System.out.println("You can enter CANCEL to return menu!");
                System.out.print("Enter EngineID (Ex: E00000):");

                EngineID = sc.nextLine();

                if (EngineID == null || EngineID.isEmpty()) {
                    System.err.println("EngineID cannot be empty!");
                    continue;
                }

                if (EngineID.equalsIgnoreCase("CANCEL")) {
                    return;
                }

                if (!InputValidator.isEngineID(EngineID)) {
                    System.err.println("EngineID must follow the format “E00000”,");
                    continue;
                }

                if (checkCarByEngine(EngineID)) {
                    System.err.println("EngineID ID already exists!");
                    continue;
                }

                break;
            }

            cars.add(new Car(carID, brandID, colorCar, FrameID, EngineID));
            CHANGE = true;
            System.out.println("Add a new car: " + carID + " success!");
            break;

        }
    }

    public void removeCar(Scanner sc) {
        System.out.println("== Remove A Car ==");

        // Enter Brand ID
        String carID;
        while (true) {
            System.out.print("Enter Car ID (Enter CANCEL to return menu):");

            carID = sc.nextLine();

            if (carID == null || carID.isEmpty()) {
                System.err.println("Car ID cannot be empty!");
                continue;
            }

            if (carID.equalsIgnoreCase("CANCEL")) {
                return;
            }

            Car c = findCarByID(carID);
            if (c == null) {
                System.err.println("This car does not exist!");
                break;
            }
            displayCar(c);

            while (true) {
                System.out.print("Are you sure you want to remove this car? (Y/N):");
                String choose = sc.nextLine().toLowerCase();
                if (choose.equalsIgnoreCase("y")) {
                    cars.remove(c);
                    CHANGE = true;
                    System.out.println("This car has been successfully removed.");
                    break;
                } else if (choose.equalsIgnoreCase("n")) {
                    break;
                }

                System.err.println("Invalid choice!. Only enter N or Y to remove!");
                continue;
            }
            break;
        }
        //break;
    }

    public void updateCarByID(Scanner sc, BrandService brandService) {
        boolean found = false;

        System.out.println("== Update Car By ID==");

        // Enter Brand ID
        String carID;
        while (!found) {
            System.out.print("Enter Car ID (Enter CANCEL to return menu):");

            carID = sc.nextLine();

            if (carID == null || carID.isEmpty()) {
                System.err.println("Car ID cannot be empty!");
                continue;
            }

            if (carID.equalsIgnoreCase("CANCEL")) {
                return;
            }

            for (Car c : cars) {
                if (c.getId().equalsIgnoreCase(carID)) {
                    found = true;

                    displayCar(c);

                    System.out.println("You can Enter to NOT update that information field!");

                    // Enter Brand Sound
                    String colorCar;
                    while (true) {
                        System.out.print("Enter color of car:");

                        colorCar = sc.nextLine();

                        if (colorCar == null || colorCar.isEmpty()) {
                            break;
                        }
                        if (!InputValidator.isValidText(colorCar)) {
                            System.err.println("Color Car not invalid!");
                        }

                        c.setColor(colorCar);
                        break;

                    }

                    // Enter Brand Price
                    String FrameID;

                    while (true) {
                        System.out.print("Enter FrameID (Ex: F00000):");

                        FrameID = sc.nextLine();

                        if (FrameID == null || FrameID.isEmpty()) {
                            break;
                        }

                        if (!InputValidator.isFrameID(FrameID)) {
                            System.err.println("FrameID must follow the format “F00000”,");
                            continue;
                        }

                        if (checkCarByFrame(FrameID)) {
                            System.err.println("Frame ID already exists!");
                            continue;
                        }
                        c.setFrameID(FrameID);
                        break;
                    }

                    // Enter Brand Price
                    String EngineID;

                    while (true) {
                        System.out.print("Enter EngineID (Ex: E00000):");

                        EngineID = sc.nextLine();

                        if (EngineID == null || EngineID.isEmpty()) {

                            break;
                        }

                        if (!InputValidator.isEngineID(EngineID)) {
                            System.err.println("Engine ID must follow the format “E00000”,");
                            continue;
                        }

                        if (checkCarByEngine(EngineID)) {
                            System.err.println("Engine ID already exists!");
                            continue;
                        }
                        c.setEngineID(EngineID);
                        break;
                    }
                    CHANGE = true;
                    System.out.println("Update for Car ID: " + carID + " successfuly!");
                    break;

                }
            }

            if (!found) {
                System.err.println("This car does not exist!");
                break;
            }

            break;
        }

    }

    public void searchCarByColor(Scanner sc) {
        boolean found = false;
        System.out.println("== List car by color:==");
        while (true) {
            System.out.print("Enter a color:");

            String carColor = sc.nextLine();

            if (carColor == null || carColor.isEmpty()) {
                break;
            }

            ArrayList<Car> carSearch = new ArrayList<>();
            for (Car c : cars) {
                if (c.getColor().equalsIgnoreCase(carColor)) {
                    carSearch.add(c);
                    found = true;
                }
            }

            if (!found) {
                System.err.println("Not found car have color: " + carColor + "!");
                break;

            } else {
                System.out.println("== Matching Cars ==");
                System.out.println("------------------------------------------------------------------------");
                System.out.println(String.format("%-10s | %-15s | %-10s | %-10s | %-10s", "Car ID", "Brand ID", "Color", "FrameID", "EngineID"));
                System.out.println("------------------------------------------------------------------------");
                for (Car c : carSearch) {

                    System.out.println(c.toString());

                }
                System.out.println("------------------------------------------------------------------------");
            }
            break;
        }

    }

    public void saveData() {
        try ( BufferedWriter writer = new BufferedWriter(new FileWriter(CAR_PATH))) {
            for (Car b : cars) {
                CHANGE = false;
                writer.write(b.toSave());
                writer.newLine(); // xuống dòng mỗi brand
            }
            System.out.println("Saved Car list to file: " + CAR_PATH);
        } catch (IOException e) {
            System.err.println("Error for save file cars: " + e.getMessage());
        }
    }

    public void displayCar(Car c) {
        System.out.println("");
        System.out.println("Car Details:");
        System.out.println("------------------------------------------------------");
        System.out.println(String.format("%-10s %-20s", "Car ID", ": " + c.getId()));
        System.out.println(String.format("%-10s %-20s", "Brand ID", ": " + c.getBrandID()));
        System.out.println(String.format("%-10s %-20s", "Color", ": " + c.getColor()));
        System.out.println(String.format("%-10s %-20s", "FrameID", ": " + c.getFrameID()));
        System.out.println(String.format("%-10s %-20s", "EngineID", ": " + c.getEngineID()));
        System.out.println("------------------------------------------------------");

    }

    public void displayAll() {
        System.out.println("");
        System.out.println("List all cars:");
        if (cars.isEmpty()) {
            System.err.println("No car in data.");
            return;
        }
        System.out.println("---------------------------------------------------------------------------------------");
        System.out.println(String.format("%-10s | %-15s | %-10s | %-10s| %-10s", "Car ID", "Brand ID", "Color", "FrameID", "EngineID"));
        System.out.println("---------------------------------------------------------------------------------------");
        for (Car c : cars) {
            System.out.println(c);
        }
        System.out.println("---------------------------------------------------------------------------------------");
    }

    public boolean checkCarByID(String id) {
        for (Car c : cars) {
            if (c.getId().equalsIgnoreCase(id)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkCarByFrame(String id) {
        for (Car c : cars) {
            if (c.getFrameID().equalsIgnoreCase(id)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkCarByEngine(String id) {
        for (Car c : cars) {
            if (c.getEngineID().equalsIgnoreCase(id)) {
                return true;
            }
        }
        return false;
    }

    private Car findCarByID(String carID) {
        for (Car c : cars) {
            if (c.getId().equalsIgnoreCase(carID)) {
                return c;
            }
        }
        return null;
    }

    public boolean checkChange() {
        return CHANGE;
    }

}
