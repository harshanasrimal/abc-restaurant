package models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Order {
	private int id;
    private int customer_id;
    private int branch_id;
    private LocalDateTime date;
    private String status;
    private String type;
    private BigDecimal total;
    private BigDecimal service_charge;
    private BigDecimal delivery_charge;
    private List<OrderItem> items;
    private double totalAmount;

    public Order(int id, int customer_id, int branch_id, LocalDateTime date, String status, String type,
    		BigDecimal total, BigDecimal service_charge, BigDecimal delivery_charge, double totalAmount) {
    	super();
    	this.id = id;
    	this.customer_id = customer_id;
    	this.branch_id = branch_id;
    	this.date = date;
    	this.status = status;
    	this.type = type;
    	this.total = total;
    	this.service_charge = service_charge;
    	this.delivery_charge = delivery_charge;
    	this.totalAmount = totalAmount;
    }
    
    public Order(int customer_id, int branch_id, String type,
    		BigDecimal total) {
    	super();
    	this.customer_id = customer_id;
    	this.branch_id = branch_id;
    	this.type = type;
    	this.total = total;
    	this.service_charge = BigDecimal.ZERO;
    	this.delivery_charge = BigDecimal.ZERO;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}

	public int getBranch_id() {
		return branch_id;
	}

	public void setBranch_id(int branch_id) {
		this.branch_id = branch_id;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public BigDecimal getService_charge() {
		return service_charge;
	}

	public void setService_charge(BigDecimal service_charge) {
		this.service_charge = service_charge;
	}

	public BigDecimal getDelivery_charge() {
		return delivery_charge;
	}

	public void setDelivery_charge(BigDecimal delivery_charge) {
		this.delivery_charge = delivery_charge;
	}

	public List<OrderItem> getItems() {
		return items;
	}

	public void setItems(List<OrderItem> items) {
		this.items = items;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
    
}
