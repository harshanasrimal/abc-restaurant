package services;

import java.sql.SQLException;
import java.util.List;

import dao.CategoryDAO;
import models.Category;

public class CategoryServices {
	private static CategoryServices instance;
	private CategoryDAO CategoryDAO;

	private CategoryServices() {
		CategoryDAO = new CategoryDAO();
	}

	public static CategoryServices getInstance() {
		if (instance == null) {
			synchronized (CategoryServices.class) {
				if (instance == null) {
					instance = new CategoryServices();
				}
			}
		}
		return instance;
	}

	public void addCategory(Category category) throws SQLException {
		CategoryDAO.addCategory(category);
	}
	
	public void updateCategory(Category category) throws SQLException {
		CategoryDAO.updateCategory(category);
	}
	
	public Category getCategory(int id) throws SQLException {
		return CategoryDAO.getCategory(id);
	}
	
	public void deleteCategory(int id) throws SQLException {
		CategoryDAO.deleteCategory(id);
	}

	public List<Category> getAllCategories() throws SQLException {
		return CategoryDAO.getAllCategories();
	}
}
