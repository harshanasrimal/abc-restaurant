package model;

public class Employee extends User {
	private int branch_id;
    private String role;
    
	public Employee(int id, String name, String email, String password,int branch_id, String role) {
		super(id,name,email,password);
		
		this.branch_id = branch_id;
		this.role = role;
	}  
	
	
}
