package models;

public class Delivery {
    private int id;  // Unique ID for the delivery
    private int orderId;
    private String address;
    private String contactNumber;
    private String status;

    // Constructor for when the delivery is created (before the ID is known)
    public Delivery(int orderId, String address, String contactNumber, String status) {
        this.orderId = orderId;
        this.address = address;
        this.contactNumber = contactNumber;
        this.status = status;
    }

    // Constructor with delivery ID (for when the delivery has been created and ID is known)
    public Delivery(int id, int orderId, String address, String contactNumber, String status) {
        this.id = id;
        this.orderId = orderId;
        this.address = address;
        this.contactNumber = contactNumber;
        this.status = status;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
