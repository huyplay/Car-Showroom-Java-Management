package view;

import controller.BrandService;
import controller.CarService;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        BrandService brandService = new BrandService();

        brandService.importDataBrand();

        CarService carService = new CarService();

        carService.importDataCar(brandService);
        
        //carService.displayAll();

        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.println("\n===== CAR SHOWROOM MANAGEMENT =====");
            System.out.println("1. List all brands");
            System.out.println("2. Add a new brand");
            System.out.println("3. Search for a brand by ID");
            System.out.println("4. Update a brand by ID");
            System.out.println("5. List all brands with prices less than or equal to an input value");
            System.out.println("6. List all cars in ascending order of brand names");
            System.out.println("7. Search cars by partial brand name match");
            System.out.println("8. Add a new car");
            System.out.println("9. Remove a car by ID");
            System.out.println("10. Update a car by ID");
            System.out.println("11. List all cars by a specific color");
            System.out.println("12. Save data to files");
            System.out.println("13. Quit program");
            while (true) {
                  // carService.displayAll();
                        
                System.out.print("Enter your choose: ");
                String choice = sc.nextLine();

                switch (choice) {
                    case "1":
                        brandService.displayAll();
                        break;
                    case "2":
                        brandService.addBrand(sc);
                        break;
                    case "3":
                        brandService.searchBrandByID(sc);
                        break;
                    case "4":
                        brandService.updateBrandByID(sc);
                        break;
                    case "5":
                        brandService.searchBrandByPrice(sc);
                        break;
                    case "6":
                        carService.ascendingBrand(brandService);
                        break;
                    case "7":
                        carService.searchCarByBrand(sc, brandService);
                        break;
                    case "8":
                        carService.addCar(sc, brandService);
                        break;
                    case "9":
                        carService.removeCar(sc);
                        break;
                    case "10":
                        carService.updateCarByID(sc, brandService);
                        break;
                    case "11":
                        carService.searchCarByColor(sc);
                        break;
                    case "12":
                        brandService.saveData();
                        carService.saveData();
                        break;
                    case "13":
                        if (brandService.checkChange() || carService.checkChange()) {
                            while (true) {
                                System.out.print("You have unsaved changes. Do you want to save the changes before exiting? (Y/N):");
                                String input = sc.nextLine().toLowerCase();
                                if (input.equals("y")) {
                                    brandService.saveData();
                                    carService.saveData();
                                    System.out.println("Exited program!");
                                    System.exit(0);
                                } else if (input.equals("n")) {
                                    System.out.println("Exited program without saving!");
                                    System.exit(0);
                                } else {
                                    System.err.println("Invalid choice. Enter Y or N to choice!");
                                    continue;
                                }
                                break;
                            }
                        }
                        System.out.println("Exited program!");
                        System.exit(0);
                        break;

                    default:

                        System.err.println("Invalid choose. Enter 1->13 to use program!");
                        continue;
                }

                break;
            }

        }
    }
}
