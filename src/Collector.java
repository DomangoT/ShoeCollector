import java.util.ArrayList;


public class Collector {
    private ArrayList<Sneaker> collection = new ArrayList<>();
    private final String name;
    private double money;
    public Collector(String name, double money) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be blank.");
        }
        this.name = name.trim();
        setMoney(money);
    }
    public boolean buyShoe(Sneaker shoe) {
        if (shoe == null) {
            return false;
        }
        double price = shoe.getRetailPrice();
        if (!Double.isFinite(price) || price < 0 || price > money) {
            return false;
        }
        if (!addShoe(shoe)) {
            return false;
        }
        setMoney(money - price);
        return true;
    }
    public void sortByResalePrice() {
        collection.sort((a, b) ->
                Double.compare(a.getResalePrice(), b.getResalePrice())
        );
        System.out.println("Sneakers sorted by resale price!");
    }
    public boolean sellShoe(Sneaker shoe) {
        if (shoe == null || !collection.contains(shoe)) {
            return false;
        }
        double price = shoe.getResalePrice();
        double newBalance = money + price;
        if (!Double.isFinite(price) || price < 0
                || !Double.isFinite(newBalance)) {
            return false;
        }
        collection.remove(shoe);
        setMoney(newBalance);
        return true;
    }
    public void work() {
        setMoney(money + 25.00);
    }
    public ArrayList<Sneaker> findAllByModel(String model) {
        ArrayList<Sneaker> matches = new ArrayList<>();
        if (model == null || model.trim().isEmpty()) {
            return matches;
        }
        for (Sneaker shoe : collection) {
            if (shoe.getModel().equalsIgnoreCase(model.trim())) {
                matches.add(shoe);
            }
        }
        return matches;
    }
    public String getName() {
        return name;
    }
    public double getMoney() {
        return money;
    }
    public void setMoney(double money) {
        if (!Double.isFinite(money) || money < 0) {
            throw new IllegalArgumentException(
                    "Money must be a finite, nonnegative amount.");
        }
        this.money = money;
    }
    public int getNumberOfShoes(){
        return collection.size();
    }
    @Override
    public String toString() {
        return String.format(
                "Name: %s | Money: $%.2f | Number of Shoes: %d",
                name, money, collection.size());
    }
    public boolean addShoe(Sneaker shoe) {
        if (shoe == null) {
            return false;
        }
        for (Sneaker existing : collection){
            if (existing.getBrand().equalsIgnoreCase(shoe.getBrand())
                && existing.getModel().equalsIgnoreCase(shoe.getModel())
                && existing.getSize() == shoe.getSize()) {
                return false;
            }
        }
        collection.add(shoe);
        return true;
    }
    public boolean removeShoe(Sneaker shoe){
        return collection.remove(shoe);
    }
    public Sneaker findByModel(String model) {
        for (Sneaker shoe : collection) {
            if (shoe.getModel().equalsIgnoreCase(model)) {
                return shoe;
            }
        }
        return null;
    }
    public double averageResalePrice() {
        if (collection.isEmpty()) {
            return 0;
        }

        return totalResaleValue() / collection.size();
    }
    public double totalResaleValue(){
        if (collection.size() == 0) {
            return 0;
        } double totalResaleValue = 0;
        for (Sneaker shoe : collection) {
            totalResaleValue +=shoe.getResalePrice();
        }
        return totalResaleValue;
    }
    public void viewCollection() {
        if (collection.isEmpty()) {
            System.out.println("Your Collection is Empty");
        }  else {
            System.out.println("\n--- Your Collection---");
        }
            for (Sneaker shoe : collection) {
                System.out.println(shoe);
            }
    }
}