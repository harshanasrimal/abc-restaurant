package models;

public class Customer extends User {
    private String contactNumber;
    private String address;
    
    public Customer(int id, String name, String email, String password, String contactNumber, String address) {
    	super(id,name,email,password);
    	this.contactNumber = contactNumber;
    	this.address = address;
    }

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
    
    
}
