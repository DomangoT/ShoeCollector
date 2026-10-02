public class Sneaker {
    private String brand;
    private String model;
    private double size;
    private double retailPrice;
    private double resalePrice;
    private boolean isDeadstock;
    private int timesWorn;
    public Sneaker(String brand, String model, double size,
                   double retailPrice, double resalePrice,
                   boolean isDeadstock, int timesWorn) {
        if (brand == null || brand.trim().isEmpty()) {
            throw new IllegalArgumentException("Brand cannot be blank.");
        }
        if (model == null || model.trim().isEmpty()) {
            throw new IllegalArgumentException("Model cannot be blank.");
        }
        if (!Double.isFinite(size) || size <= 0) {
            throw new IllegalArgumentException(
                    "Size must be a finite number greater than zero.");
        }
        this.brand = brand.trim();
        this.model = model.trim();
        this.size = size;
        setRetailPrice(retailPrice);
        setResalePrice(resalePrice);
        setTimesWorn(timesWorn);
        setIsDeadStock(isDeadstock);
    }
 public String getBrand(){return brand;}
 public String getModel(){return model;}
 public double getSize(){return size;}
 public double getRetailPrice(){return retailPrice;}
    public void setRetailPrice(double retailPrice) {
        if (!Double.isFinite(retailPrice) || retailPrice < 0) {
            throw new IllegalArgumentException(
                    "Retail price must be a finite number of zero or more.");
        }
        this.retailPrice = retailPrice;
    }
 public double getResalePrice(){return resalePrice;}
    public void setResalePrice(double resalePrice) {
        if (!Double.isFinite(resalePrice) || resalePrice < 0) {
            throw new IllegalArgumentException(
                    "Resale price must be a finite number of zero or more.");
        }
        this.resalePrice = resalePrice;
    }
 public boolean getIsDeadStock(){return isDeadstock;}
    public void setIsDeadStock(boolean isDeadstock) {
        if (isDeadstock && timesWorn > 0) {
            throw new IllegalArgumentException(
                    "A worn sneaker cannot be deadstock.");
        }
        this.isDeadstock = isDeadstock;
    }
  public int getTimesWorn(){return timesWorn;}
    public void setTimesWorn(int timesWorn) {
        if (timesWorn < 0) {
            throw new IllegalArgumentException(
                    "Times worn cannot be negative.");
        }
        this.timesWorn = timesWorn;
        if (timesWorn > 0) {
            isDeadstock = false;
        }
    }
    public void buyShoe(boolean buyResaleShoe){
     if (buyResaleShoe) {
         System.out.println("The price of this shoe is $" + resalePrice);
     } else {
         System.out.println("The price of this shoe is $" + retailPrice);
     }
    }
    public void wear() {
        if (timesWorn == Integer.MAX_VALUE) {
            throw new IllegalStateException("Wear count cannot increase further.");
        }

        timesWorn++;
        isDeadstock = false;
    }
  public void sellShoe(double resale){
     setResalePrice(resale);
     }
    @Override
    public String toString() {
        return String.format(
                "%s %s | Size: %.1f | Retail: $%.2f | Resale: $%.2f"
                        + " | Deadstock: %s | Worn: %d times",
                brand, model, size, retailPrice, resalePrice,
                isDeadstock ? "Yes" : "No", timesWorn);
    }
  }


