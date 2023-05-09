import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        currentGrocery temp = new currentGrocery();
        temp.setGrocery();

        pos instance = new pos();
        instance.setBill();
        GroceryItem item_2 = new GroceryItem(145, 500, 3, 5, 11, 30);

        instance.addToBill(2);

        instance.savePendingBill();

        Bill current = instance.getPendingBill();

        System.out.println(current.getTotalPrice());

    }
}

// define a class for the item code not found exception
class ItemCodeNotFoundException extends Exception {
    public ItemCodeNotFoundException(String message) {
        super(message);
    }
}

// Class to represent a grocery item
class GroceryItem implements Serializable{
    private int itemCode;
    private double price;
    private double weight;
    private int manufacturingDate;
    private int expiryDate;
    private double discount;


    // Constructor
    public GroceryItem(int itemCode, double price, double weight,
                       int manufacturingDate, int expiryDate, double discount) {
        this.itemCode = itemCode;
        this.price = price;
        this.weight = weight;
        this.manufacturingDate = manufacturingDate;
        this.expiryDate = expiryDate;
        this.discount = discount;
    }
    public double getDiscount() {
        return discount;
    }
    public double getPrice() {
        return price;
    }
}

// Class to represent a bill item
class BillItem implements Serializable {
    private GroceryItem groceryItem;
    private double quantity;

    // Constructor
    public BillItem(GroceryItem groceryItem, double quantity) {
        this.groceryItem = groceryItem;
        this.quantity = quantity;
    }

    // Getters
    public GroceryItem getGroceryItem() {
        return groceryItem;
    }

    public double getQuantity() {
        return quantity;
    }

    // Calculate the net price of the bill item
    public double getNetPrice() {
        double discountedPrice = groceryItem.getPrice() * (100 - groceryItem.getDiscount())/100;
        return discountedPrice * quantity;
    }

    public int getDiscount(){
        return (int) groceryItem.getDiscount();
    }
}

// Class to represent a bill
class Bill implements Serializable {
    private String cashierName;
    private String branch;
    private String customerName;
    private List<BillItem> billItems;
    private double totalDiscount;
    private double totalPrice;

    private Date date;
    // Constructor
    public Bill(String cashierName, String branch, String customerName) {
        this.cashierName = cashierName;
        this.branch = branch;
        this.customerName = customerName;
        this.date = new Date();
        billItems = new ArrayList<>();
    }

    // Add a bill item
    public void addItem(BillItem item) {
        billItems.add(item);
    }

    // Calculate the total price of the pending bill
    public double getTotalPrice() {
        double totalPrice = 0.0;
        for (BillItem item : billItems) {
            totalPrice += item.getNetPrice();
        }
        return totalPrice;
    }

    // Print the pending bill
    public void printBill() {
        System.out.println("---- Pending Bill ----");
        System.out.println("Cashier: " + cashierName);
        System.out.println("Branch: " + branch);
        System.out.println("Customer: " + customerName);
        System.out.println("Date: " + date);
        System.out.println("Items:");

        for (BillItem item : billItems) {
            System.out.println("processing a item");
            System.out.println("Quantity: " + item.getQuantity());
            System.out.println("Discount: " + item.getDiscount());
            System.out.println("Total Price: " + item.getNetPrice());
            System.out.println("----------------------");
        }
        System.out.println("Total Price: " + getTotalPrice());
        System.out.println("----------------------");
    }
}

// create a class to store the grocery items in the given instance
// this object will store the current grocery list after the set grocery method is called
class currentGrocery {
    public HashMap<Integer, GroceryItem> items;

    public void setGrocery() {
        items = new HashMap<>(); // Initialize the HashMap
        GroceryItem item_1 = new GroceryItem(123, 450, 2, 3, 10, 25);
        GroceryItem item_2 = new GroceryItem(145, 500, 3, 5, 11, 30);

        items.put(1,item_1);
        items.put(2,item_2);
    }

}

// create a class pos
// inside this we will do the serialization and deserialization parts
class pos{

    public Bill currentBill;

    public void setBill(){
        this.currentBill = new Bill("Disini","panadura","Savinu");
    }

    // we need to create separate bill item objects and add them to a running bill
    // create a method addToBill and create a bll item object each time
    public void addToBill(double amount){
        // take the grocery item and the amount of items as parameters
        System.out.println("Enter the item code");

        currentGrocery temp = new currentGrocery();
        temp.setGrocery();
        // after this method is called we need to get the item code from the user adn handle the exceptions from there

        // take the item code as a user input

        try {
            InputStreamReader r = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(r);
            String itemCode = br.readLine();
            int code = Integer.parseInt(itemCode);

            if (temp.items.containsKey(code)) {
                GroceryItem current = temp.items.get(code);
                BillItem curr_item = new BillItem(current,amount);
                currentBill.addItem(curr_item);
            } else {
                throw new ItemCodeNotFoundException("Item code not found. Please re-enter the item code.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid item code. Please enter a valid integer code.");
        } catch (ItemCodeNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    // create a method to save partially created bill object
    public void savePendingBill(){
        // serialize the current bill and save it in a file for later access
        try (FileOutputStream fileOut = new FileOutputStream("object.ser");
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
            objectOut.writeObject(currentBill);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Bill getPendingBill(){

        // deserialize the serialized bill and show it to the user again

        try (FileInputStream fileIn = new FileInputStream("object.ser");
             ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
            Bill deserializedObject = (Bill) objectIn.readObject();
            return deserializedObject;
            // Use the deserialized object
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }

    }


}

