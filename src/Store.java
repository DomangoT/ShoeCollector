import java.util.ArrayList;
import java.util.Collections;

public class Store {
    private final ArrayList<Sneaker> inventory = new ArrayList<>();
    public Store() {
        restock();
    }
    public void restock() {
        ArrayList<Sneaker> catalog = new ArrayList<>();
        catalog.add(new Sneaker(
                "Nike", "Air Jordan 1", 9, 150, 200, true, 0));
        catalog.add(new Sneaker(
                "Nike", "Dunk Low", 10, 110, 140, true, 0));
        catalog.add(new Sneaker(
                "Adidas", "Samba", 9.5, 100, 120, true, 0));
        catalog.add(new Sneaker(
                "New Balance", "550", 10, 120, 135, true, 0));
        catalog.add(new Sneaker(
                "Asics", "Gel-1130", 8.5, 95, 110, true, 0));
        catalog.add(new Sneaker(
                "Puma", "Suede", 11, 75, 85, true, 0));
        catalog.add(new Sneaker(
                "Converse", "Chuck Taylor", 9, 60, 65, true, 0));
        catalog.add(new Sneaker(
                "Vans", "Old Skool", 10.5, 70, 80, true, 0));
        Collections.shuffle(catalog);
        inventory.clear();
        for (int i = 0; i < 4; i++) {
            inventory.add(catalog.get(i));
        }
    }
    public int getStockCount() {
        return inventory.size();
    }
    public void viewStock() {
        System.out.println("\n--- Sneaker Store ---");
        if (inventory.isEmpty()) {
            System.out.println("Sold out! Wait for the next restock.");
            return;
        }
        for (int i = 0; i < inventory.size(); i++) {
            System.out.println((i + 1) + ") " + inventory.get(i));
        }
    }
    public boolean purchase(int index, Collector collector) {
        if (collector == null || index < 0 || index >= inventory.size()) {
            return false;
        }
        Sneaker shoe = inventory.get(index);
        if (!collector.buyShoe(shoe)) {
            return false;
        }
        inventory.remove(index);
        return true;
    }
}