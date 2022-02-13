package dao;

import model.User;

public interface UserDAO {
	void insert(User user); 
	 
	void updateInfo(User user);
	
	void updatePassword(User user); 
	
	void delete(int id); 
 
	User get(int id); 
	 
	User get(String username);
	
	User get(String username, String password);
	
	boolean checkUsername(String username);
	
	boolean checkEmail(String email);
	
	boolean isExistAccount(String username);
	
	boolean hasSystem(String username);
}
