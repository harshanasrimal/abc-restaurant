package services;

import java.sql.SQLException;
import java.util.List;

import dao.BranchDAO;
import models.Branch;

public class BranchServices {
	private static BranchServices instance;
	private BranchDAO BranchDAO;

	private BranchServices() {
		BranchDAO = new BranchDAO();
	}

	public static BranchServices getInstance() {
		if (instance == null) {
			synchronized (BranchServices.class) {
				if (instance == null) {
					instance = new BranchServices();
				}
			}
		}
		return instance;
	}

	public void addBranch(Branch branch) throws SQLException {
		BranchDAO.addBranch(branch);
	}
	
	public void updateBranch(Branch branch) throws SQLException {
		BranchDAO.updateBranch(branch);
	}
	
	public Branch getBranch(int id) throws SQLException {
		return BranchDAO.getBranch(id);
	}
	
	public void deleteBranch(int id) throws SQLException {
		BranchDAO.deleteBranch(id);
	}

	public List<Branch> getAllBranches() throws SQLException {
		return BranchDAO.getAllBranches();
	}
}
