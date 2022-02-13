package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dao.UserDAO;
import jdbc.ConnectionUtils;
import model.User;

public class UserDAOImpl implements UserDAO {

	@Override
	public void insert(User user) {
		try {
			Connection conn = ConnectionUtils.getConnection();
			String sql = "insert into users(username, email, password) values (?, ?, ?)";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			
			preparedStatement.setString(1, user.getUsername());
			preparedStatement.setString(2, user.getEmail());
			preparedStatement.setString(3, user.getPassword());
			preparedStatement.executeUpdate();	
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateInfo(User user) {
		try {
			Connection conn = ConnectionUtils.getConnection();
			String sql = "update users set name = ?, phone = ?, organization = ?, position = ? where id = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getPhone());
			preparedStatement.setString(3, user.getOrganization());
			preparedStatement.setString(4, user.getPosition());
			preparedStatement.setInt(5, user.getId());
			preparedStatement.executeUpdate();	
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void updatePassword(User user) {
		try {
			Connection conn = ConnectionUtils.getConnection();
			String sql = "update users set password = ? where id = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			
			preparedStatement.setString(1, user.getPassword());
			preparedStatement.setInt(2, user.getId());
			preparedStatement.executeUpdate();	
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void delete(int id) {
		try {
			Connection conn = ConnectionUtils.getConnection();
			String sql = "delete from users where id = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();	
		
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public User get(int id) {
		try {
			Connection conn = ConnectionUtils.getConnection();
			String sql = "select * from users where id = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
		
			if(resultSet.next()) {
				String username = resultSet.getString("username");
				String password = resultSet.getString("password");
				String email = resultSet.getString("email");
				String name = resultSet.getString("name");
				String phone = resultSet.getString("phone");
				String organization = resultSet.getString("organization");
				String position = resultSet.getString("position");
				String created_time = resultSet.getString("created_time");
				String modified_time = resultSet.getString("modified_time");
				
				User user = new User(id, email, username, password, name, phone, organization, position, created_time, modified_time);
				return user;
			}
		
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	@Override
	public User get(String username) {
		
		try {
			Connection conn = ConnectionUtils.getConnection();
			String sql = "select * from users where username = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			
			preparedStatement.setString(1, username);
			ResultSet resultSet = preparedStatement.executeQuery();
		
			if(resultSet.next()) {
				int id = resultSet.getInt("id");
				String password = resultSet.getString("password");
				String email = resultSet.getString("email");
				String name = resultSet.getString("name");
				String phone = resultSet.getString("phone");
				String organization = resultSet.getString("organization");
				String position = resultSet.getString("position");
				String created_time = resultSet.getString("created_time");
				String modified_time = resultSet.getString("modified_time");
				
				User user = new User(id, email, username, password, name, phone, organization, position, created_time, modified_time);
				return user;
			}
		
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	@Override
	public User get(String username, String password) {
		return null;
	}

	@Override
	public boolean checkUsername(String username) {
		try {
			Connection conn = ConnectionUtils.getConnection();
			String sql = "select id from users where username = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			
			preparedStatement.setString(1, username);

			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				return false;
			}
			
			return true;
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean checkEmail(String email) {
		try {
			Connection conn = ConnectionUtils.getConnection();
			String sql = "select id from users where email = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			
			preparedStatement.setString(1, email);

			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				return false;
			}
			
			return true;
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean isExistAccount(String username) {
		return !checkUsername(username);
	}

	@Override
	public boolean hasSystem(String username) {
		try {
			Connection conn = ConnectionUtils.getConnection();
			String sql = "select system_id from user_system where user_id = (select id from users where username = ?)";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			
			preparedStatement.setString(1, username);

			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				return true;
			}
			
			return false;
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	
	

}
