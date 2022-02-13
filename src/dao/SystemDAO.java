package dao;

import java.util.List;

import model.ITSystem;
import model.User;

public interface SystemDAO {
	void insert(User user, ITSystem system, List<String> imageLinks); 
	 
	void update(ITSystem system, List<String> imageLinks); 
	
	void changeOwner(int system_id, String username); 
	
	void delete(int id); 
 
	ITSystem getById(int id); 
	
	ITSystem getByUserId(int user_id); 
	
	List<String> getImages(int system_id);
	 	
}
