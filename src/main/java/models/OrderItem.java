package models;

import java.math.BigDecimal;

public class OrderItem {
private int id;
 private int order_id;
 private int product_id;
 private int qty;
 private BigDecimal total;
 
 public OrderItem(int id, int order_id, int product_id, int qty) {
	 super();
	 this.id = id;
	 this.order_id = order_id;
	 this.product_id = product_id;
	 this.qty = qty;
 }
 public OrderItem(int product_id, int qty,BigDecimal total) {
	 super();
	 this.product_id = product_id;
	 this.qty = qty;
	 this.total = total;
 }
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public int getOrder_id() {
	return order_id;
}
public void setOrder_id(int order_id) {
	this.order_id = order_id;
}
public int getProduct_id() {
	return product_id;
}
public void setProduct_id(int product_id) {
	this.product_id = product_id;
}
public int getQty() {
	return qty;
}
public void setQty(int qty) {
	this.qty = qty;
}
public BigDecimal getTotal() {
	return total;
}
public void setTotal(BigDecimal total) {
	this.total = total;
}
 
}
