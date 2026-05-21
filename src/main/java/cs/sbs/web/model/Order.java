package cs.sbs.web.model;

public class Order {
    private int orderId;
    private String customerName;
    private String foodName;
    private int quantity;

    public Order(int orderId, String customerName, String foodName, int quantity) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.foodName = foodName;
        this.quantity = quantity;
    }

    // getter
    public int getOrderId() { return orderId; }
    public String getCustomerName() { return customerName; }
    public String getFoodName() { return foodName; }
    public int getQuantity() { return quantity; }
}