package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.SystemDAO;
import dao.UserDAO;
import jdbc.ConnectionUtils;
import model.User;
import model.ITSystem;

public class SystemDAOImpl implements SystemDAO {

	@Override
	public void insert(User user, ITSystem system, List<String> imageLinks) {
		try {

			Connection conn = ConnectionUtils.getConnection();
			String sql = "insert into systems(name, description) values (?, ?)";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);

			preparedStatement.setString(1, system.getName());
			preparedStatement.setString(2, system.getDescription());
			preparedStatement.executeUpdate();
			

			sql = "SELECT IDENT_CURRENT('systems') as id;";
			Statement st = conn.createStatement();
			ResultSet resultSet = st.executeQuery(sql);
			int id = -1;
			if(resultSet.next()) {
				id = resultSet.getInt("id");
			}
//			System.out.println(id);

			sql = "insert into user_system(user_id, system_id) values (?, ?)";
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, user.getId());
			preparedStatement.setInt(2, id);
			preparedStatement.executeUpdate();
			
			for(String link: imageLinks) {
				sql = "insert into system_image(system_id, image_link) values (?, ?)";
				preparedStatement = conn.prepareStatement(sql);
				preparedStatement.setInt(1, id);
				preparedStatement.setString(2, link);
				preparedStatement.executeUpdate();
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void update(ITSystem system, List<String> imageLinks) {
		try {

			Connection conn = ConnectionUtils.getConnection();
			String sql = "update systems set name = ?, description = ? where id = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);

			preparedStatement.setString(1, system.getName());
			preparedStatement.setString(2, system.getDescription());
			preparedStatement.setInt(3, system.getId());
			preparedStatement.executeUpdate();
			
			
			sql = "delete from system_image where system_id = ?";
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, system.getId());
			preparedStatement.executeUpdate();
			
			for(String link: imageLinks) {
				sql = "insert into system_image(system_id, image_link) values (?, ?)";
				preparedStatement = conn.prepareStatement(sql);
				preparedStatement.setInt(1, system.getId());
				preparedStatement.setString(2, link);
				preparedStatement.executeUpdate();
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

	}

	@Override
	public void delete(int id) {
		try {

			Connection conn = ConnectionUtils.getConnection();
			String sql = "delete from systems where id = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);

			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();

		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

	}

	@Override
	public ITSystem getById(int id) {
		try {

			Connection conn = ConnectionUtils.getConnection();
			String sql = "select * from systems where id = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);

			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();

			if(resultSet.next()) {
				String name = resultSet.getString("name");
				String description = resultSet.getString("description");
				String created_time = resultSet.getString("created_time");
				String modified_time = resultSet.getString("modified_time");

				ITSystem system = new ITSystem(id, name, description, created_time, modified_time);
				return system;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ITSystem getByUserId(int user_id) {
		try {

			Connection conn = ConnectionUtils.getConnection();
			String sql = "select * from systems "
					+ "where id = (select system_id from user_system where user_id = ?)";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);

			preparedStatement.setInt(1, user_id);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				String description = resultSet.getString("description");
				String created_time = resultSet.getString("created_time");
				String modified_time = resultSet.getString("modified_time");

				ITSystem system = new ITSystem(id, name, description, created_time, modified_time);
				return system;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<String> getImages(int system_id) {
		try {

			Connection conn = ConnectionUtils.getConnection();
			String sql = "select image_link from system_image where system_id = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);

			preparedStatement.setInt(1, system_id);
			ResultSet resultSet = preparedStatement.executeQuery();

			List<String> images = new ArrayList<String>();
			while(resultSet.next()) {
				String link = resultSet.getString("image_link");
				images.add(link);
			}
			
			return images;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void changeOwner(int system_id, String username) {
		try {

			Connection conn = ConnectionUtils.getConnection();
			
			UserDAO userDAO = new UserDAOImpl();
			User user = userDAO.get(username);
			
			String sql = "update user_system set user_id = ? where system_id = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);

			preparedStatement.setInt(1, user.getId());
			preparedStatement.setInt(2, system_id);
			preparedStatement.executeUpdate();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	

}
