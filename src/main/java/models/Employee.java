package models;

public class Employee extends User {
	private int branch_id;
    private String role;
    
	public Employee(int id, String name, String email, String password,int branch_id, String role) {
		super(id,name,email,password);
		
		this.branch_id = branch_id;
		this.role = role;
	}

	public int getBranch_id() {
		return branch_id;
	}

	public void setBranch_id(int branch_id) {
		this.branch_id = branch_id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}  
	
	
}
