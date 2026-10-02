import java.util.Scanner;
import java.util.ArrayList;
public class Main {
    public static void main(String[] args){
        Collector myCollector = new Collector("Bob ", 300.00);
        Scanner input = new Scanner(System.in);
        Sneaker shoe1 = new Sneaker("Nike ","Air Jordan 1", 9,149.99,200, true, 0);
        Sneaker shoe2 = new Sneaker("New Balance ","530",10,99.99,125,false,3);
        Sneaker shoe3 = new Sneaker("Adidas ", "OG", 8.5, 100.00, 175, true, 0 );
        System.out.println(shoe1);
        System.out.println(shoe2);
        System.out.println(shoe3);
        myCollector.addShoe(shoe1);
        myCollector.addShoe(shoe2);
        myCollector.addShoe(shoe3);
        Store store = new Store();
        int actionsSinceRestock = 0;

        boolean running = true;

        while(running){ //hi hello
            System.out.println("\n--- Sneaker Collection Menu ---");
            System.out.printf("Money: $%.2f%n", myCollector.getMoney());
            System.out.println(
                    "Store restocks in " + (5 - actionsSinceRestock) + " actions.");
            System.out.println("1) Buy a Sneaker");
            System.out.println("2) View Collection");
            System.out.println("3) Find a Sneaker");
            System.out.println("4) Vieww Collection Report");
            System.out.println("5) Sell a Sneaker");
            System.out.println("6) Work (+$25)");
            System.out.println("7) View Store");
            System.out.println("8) Exit");

            int choice = readInt(input, "Choose an option: ", 1, 8);
            if (choice == 1) {
                buyFromStore(input, myCollector, store);
            }
            else if (choice == 2) {
                myCollector.viewCollection();
            }
            else if (choice == 3) {
                System.out.print("Enter the model you want to find: ");
                String targetModel = input.nextLine();
                ArrayList<Sneaker> matches =
                        myCollector.findAllByModel(targetModel);
                if (matches.isEmpty()) {
                    System.out.println("No matching sneakers found.");
                } else {
                    for (int i = 0; i < matches.size(); i++) {
                        System.out.println((i + 1) + ") " + matches.get(i));
                    }
                }
            }
            else if (choice == 4) {
                System.out.println("\n--- Collection Report ---");
                System.out.println(myCollector);
                System.out.printf("Total Resale Value: $%.2f%n",
                        myCollector.totalResaleValue());
                System.out.printf("Average Resale Price: $%.2f%n",
                        myCollector.averageResalePrice());
            }
            else if (choice == 5) {
                sellSneaker(input, myCollector);
            }
            else if (choice == 6) {
                myCollector.work();
                System.out.printf("You earned $25! Your money: $%.2f%n",
                        myCollector.getMoney());
            }
            else if (choice == 7) {
                store.viewStock();
            }
            else if (choice == 8) {
                running = false;
            }
            if (running) {
                actionsSinceRestock++;

                if (actionsSinceRestock == 5) {
                    store.restock();
                    actionsSinceRestock = 0;

                    System.out.println("\nThe store has restocked!");
                    store.viewStock();
                }
            }
        }
        System.out.println("Bye Bye");
   }
   public static String readText(Scanner input, String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = input.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("Please enter a value.");
        }
    }
    public static double readDouble(Scanner input, String prompt,
                                    boolean allowZero) {
        while (true) {
            String text = readText(input, prompt);
            try {
                double value = Double.parseDouble(text);
                if (Double.isFinite(value)
                        && (allowZero ? value >= 0 : value > 0)) {
                    return value;
                }
            } catch (NumberFormatException e) {
            }
            System.out.println(allowZero
                    ? "Enter a finite number of zero or more."
                    : "Enter a finite number greater than zero.");
        }
    }
    public static int readInt(Scanner input, String prompt,
                              int minimum, int maximum) {
        while (true) {
            String text = readText(input, prompt);
            try {
                int value = Integer.parseInt(text);

                if (value >= minimum && value <= maximum) {
                    return value;
                }
            } catch (NumberFormatException e) {
            }
            System.out.println(
                    "Enter a whole number from "
                            + minimum + " to " + maximum + ".");
        }
    }
    public static boolean readBoolean(Scanner input, String prompt) {
        while (true) {
            String text = readText(input, prompt);
            if (text.equalsIgnoreCase("true")) {
                return true;
            }
            if (text.equalsIgnoreCase("false")) {
                return false;
            }
            System.out.println("Please enter true or false.");
        }
    }
   public static void sellSneaker(Scanner input, Collector collector) {
        if (collector.getNumberOfShoes() == 0) {
            System.out.println("Your collection is empty.");
            return;
        }
        System.out.print("Enter the model to remove: ");
        String model = input.nextLine();
        ArrayList<Sneaker> matches = collector.findAllByModel(model);
        if (matches.isEmpty()) {
            System.out.println("No matching sneakers found.");
            return;
        }
        for (int i = 0; i < matches.size(); i++) {
            System.out.println((i + 1) + ") " + matches.get(i));
        }
       int selection = readInt(
               input,
               "Choose a sneaker number, or 0 to cancel: ",
               0,
               matches.size());

       if (selection == 0) {
           System.out.println("Removal canceled.");
           return;
       }
        while (true) {
            System.out.print("Choose a sneaker number, or 0 to cancel: ");
            try {
                selection = Integer.parseInt(input.nextLine().trim());
                if (selection == 0) {
                    System.out.println("Removal canceled.");
                    return;
                }
                if (selection >= 1 && selection <= matches.size()) {
                    break;
                }
                System.out.println(
                        "Enter a number from 0 to " + matches.size() + ".");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a whole number.");
            }
        }
        Sneaker selectedShoe = matches.get(selection - 1);
       System.out.println("Selected: " + selectedShoe);
       System.out.printf(
               "You will receive: $%.2f%n", selectedShoe.getResalePrice());
        while (true) {
            System.out.print("Sell this sneaker? (yes/no): ");
            String answer = input.nextLine().trim();

            if (answer.equalsIgnoreCase("yes") || answer.equalsIgnoreCase("y")) {
                if (collector.sellShoe(selectedShoe)) {
                    System.out.printf(
                            "Sneaker sold! Your money: $%.2f%n",
                            collector.getMoney());
                } else {
                    System.out.println("The sneaker could not be sold.");
                }
                return;
            }
            if (answer.equalsIgnoreCase("no")
                    || answer.equalsIgnoreCase("n")) {
                System.out.println("Sell canceled.");
                return;
            }
            System.out.println("Please enter yes or no.");
        }
    }
    public static void addSneaker(Scanner input, Collector collector) {
        String brand = readText(input, "Brand: ");
        String model = readText(input, "Model: ");
        double size = readDouble(input, "Size: ", false);
        double retailPrice = readDouble(input, "Retail Price: $", true);
        double resalePrice = readDouble(input, "Resale Price: $", true);
        int timesWorn = readInt(
                input, "Times worn: ", 0, Integer.MAX_VALUE);
        boolean isDeadstock = false;
        if (timesWorn == 0) {
            isDeadstock = readBoolean(
                    input, "Is it deadstock? (true/false): ");
        } else {
            System.out.println("Worn sneakers are marked as not deadstock.");
        }
        Sneaker shoe = new Sneaker(
                brand, model, size, retailPrice,
                resalePrice, isDeadstock, timesWorn);
        if (collector.addShoe(shoe)) {
            System.out.println("Sneaker added!");
        } else {
            System.out.println(
                    "That brand, model, and size already exist.");
        }
    }
    public static void buyFromStore(
            Scanner input, Collector collector, Store store) {
        store.viewStock();
        if (store.getStockCount() == 0) {
            return;
        }
        System.out.printf("Your money: $%.2f%n", collector.getMoney());
        int choice = readInt(
                input,
                "Choose a shoe to buy, or 0 to cancel: ",
                0,
                store.getStockCount());
        if (choice == 0) {
            System.out.println("Purchase canceled.");
            return;
        }
        if (store.purchase(choice - 1, collector)) {
            System.out.printf(
                    "Sneaker purchased! Remaining money: $%.2f%n",
                    collector.getMoney());
        } else {
            System.out.println(
                    "Purchase failed: insufficient money or "
                            + "you already own that brand, model, and size.");
        }
    }
}