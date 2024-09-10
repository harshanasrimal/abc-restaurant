package models;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Product {
	private int id;
	private String name;
	private String description;
	private String image;
	private BigDecimal price;
	private int category_id;
	private String category;
	private boolean active;
	

	public Product(String name, String description, BigDecimal price, int category_id, String image, boolean active) {
		super();
		this.name = name;
		this.description = description;
		this.image = image;
		this.price = price;
		this.category_id = category_id;
		this.active = active;
	}
	
	public Product(int id, String name, String description, BigDecimal price, int category_id, String image, boolean active) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.image = image;
		this.price = price;
		this.category_id = category_id;
		this.active = active;
	}

	public Product(int id, String name, String description, BigDecimal price, int category_id, String image, boolean active, String category) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.image = image;
		this.price = price;
		this.category_id = category_id;
		this.category = category;
		this.active = active;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getCategory_id() {
		return category_id;
	}

	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getCategory() {
		return category;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	
}
