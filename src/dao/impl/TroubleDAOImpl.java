package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dao.TroubleDAO;
import jdbc.ConnectionUtils;
import model.Risk;
import model.Trouble;

public class TroubleDAOImpl implements TroubleDAO {

	@Override
	public void insert(Trouble trouble, List<Integer> assets, List<Integer> risks) {
		try {
			Connection conn = ConnectionUtils.getConnection();
			String sql = "insert into troubles(short_description, detail, status, system_id, time_happen, solution) values (?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, trouble.getShort_description());
			preparedStatement.setString(2, trouble.getDetail());
			preparedStatement.setInt(3, trouble.getStatus());
			preparedStatement.setInt(4, trouble.getSystem_id());
			preparedStatement.setTimestamp(5, Timestamp.valueOf(trouble.getTime_happen()));
			preparedStatement.setString(6, trouble.getSolution());
			preparedStatement.executeUpdate();
			
			
			sql = "SELECT IDENT_CURRENT('troubles') as id;";
			Statement st = conn.createStatement();
			ResultSet resultSet = st.executeQuery(sql);
			int id = -1;
			if(resultSet.next()) {
				id = resultSet.getInt("id");
			}
			
			
			for(int asset_id: assets) {
				sql = "insert into trouble_asset(trouble_id, asset_id) values (?, ?)";
				preparedStatement = conn.prepareStatement(sql);
				preparedStatement.setInt(1, id);
				preparedStatement.setInt(2, asset_id);
				preparedStatement.executeUpdate();
			}
			
			for(int risk_id: risks) {
				sql = "insert into trouble_risk(trouble_id, risk_id) values (?, ?)";
				preparedStatement = conn.prepareStatement(sql);
				preparedStatement.setInt(1, id);
				preparedStatement.setInt(2, risk_id);
				preparedStatement.executeUpdate();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void update(Trouble trouble, List<Integer> assets, List<Integer> risks) {
		try {
			Connection conn = ConnectionUtils.getConnection();
			String sql = "update troubles set short_description = ?, detail = ?, status = ?, time_happen = ?, solution = ? where id = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, trouble.getShort_description());
			preparedStatement.setString(2, trouble.getDetail());
			preparedStatement.setInt(3, trouble.getStatus());
			preparedStatement.setTimestamp(4, Timestamp.valueOf(trouble.getTime_happen()));
			preparedStatement.setString(5, trouble.getSolution());
			preparedStatement.setInt(6, trouble.getId());
						
			preparedStatement.executeUpdate();
			
			
			sql = "delete from trouble_asset where trouble_id = ?";
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, trouble.getId());
			preparedStatement.executeUpdate();
			
			sql = "delete from trouble_risk where trouble_id = ?";
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, trouble.getId());
			preparedStatement.executeUpdate();
			
			for(int asset_id: assets) {
				sql = "insert into trouble_asset(trouble_id, asset_id) values (?, ?)";
				preparedStatement = conn.prepareStatement(sql);
				preparedStatement.setInt(1, trouble.getId());
				preparedStatement.setInt(2, asset_id);
				preparedStatement.executeUpdate();
			}
			
			for(int risk_id: risks) {
				sql = "insert into trouble_risk(trouble_id, risk_id) values (?, ?)";
				preparedStatement = conn.prepareStatement(sql);
				preparedStatement.setInt(1, trouble.getId());
				preparedStatement.setInt(2, risk_id);
				preparedStatement.executeUpdate();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}


	}

	@Override
	public void delete(int id) {
		try {
			Connection conn = ConnectionUtils.getConnection();
			String sql = "delete from troubles where id = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public Trouble get(int id) {
		try {
			Connection conn = ConnectionUtils.getConnection();
			String sql = "select * from troubles where id = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				
				String short_description = resultSet.getString("short_description");
				String detail = resultSet.getString("detail");
				int status = resultSet.getInt("status");
				int system_id = resultSet.getInt("system_id");
				String solution = resultSet.getString("solution");
				String time_happen = resultSet.getString("time_happen");
				String created_time = resultSet.getString("created_time");
				String modified_time = resultSet.getString("modified_time");
				
				Trouble trouble = new Trouble(id, short_description, detail, status, solution, system_id, time_happen, created_time, modified_time);
				
				return trouble;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Trouble> getAllInSystem(int system_id) {
		try {
			Connection conn = ConnectionUtils.getConnection();
			String sql = "select * from troubles where system_id = ? order by time_happen desc";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, system_id);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			List<Trouble> list = new ArrayList<>();
			while(resultSet.next()) {
				int id = resultSet.getInt("id");
				String short_description = resultSet.getString("short_description");
				String detail = resultSet.getString("detail");
				int status = resultSet.getInt("status");
				String solution = resultSet.getString("solution");
				String time_happen = resultSet.getString("time_happen");
				String created_time = resultSet.getString("created_time");
				String modified_time = resultSet.getString("modified_time");
				
				Trouble trouble = new Trouble(id, short_description, detail, status, solution,system_id, time_happen, created_time, modified_time);
				
				list.add(trouble);
			}
			
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Integer> countNumGroupbyStatus(int system_id, int year) {
		try {
			Connection conn = ConnectionUtils.getConnection();
			String sql = "select status, COUNT(*) as num from troubles "
					+ "where system_id = ? and year(time_happen) = ? "
					+ "group by status order by status asc";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			
			preparedStatement.setInt(1, system_id);
			preparedStatement.setInt(2, year);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			List<Integer> list = new ArrayList<Integer>(Collections.nCopies(3, 0));
			
			while(resultSet.next()) {
				int status = resultSet.getInt("status");
				int num = resultSet.getInt("num");
				list.set(status, num);
			}
			
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Integer> countNumGroupbyMonth(int system_id, int year, int status) {
		try {
			Connection conn = ConnectionUtils.getConnection();
			String sql = "select MONTH(time_happen) as month, COUNT(*) as num from troubles"
					+ " where system_id = ? and YEAR(time_happen) = ? and status = ?"
					+ " group by MONTH(time_happen) order by MONTH(time_happen) asc";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			
			preparedStatement.setInt(1, system_id);
			preparedStatement.setInt(2, year);
			preparedStatement.setInt(3, status);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			List<Integer> list = new ArrayList<Integer>(Collections.nCopies(12, 0));
			
			while(resultSet.next()) {
				int month = resultSet.getInt("month");
				int num = resultSet.getInt("num");
				list.set(month - 1, num);
			}
			
//			System.out.println(list);
			
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
