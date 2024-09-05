package models;

public class Branch {
private int id;
 private String location;
 private String description;
 
 public Branch(String location, String description) {
	 super();
	 this.location = location;
	 this.description = description;
 }
 
 public Branch(int id, String location, String description) {
	 super();
	 this.id = id;
	 this.location = location;
	 this.description = description;
 }

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public String getlocation() {
	return location;
}

public void setlocation(String location) {
	this.location = location;
}

public String getDescription() {
	return description;
}

public void setDescription(String description) {
	this.description = description;
}
 
}
