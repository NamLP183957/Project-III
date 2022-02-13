package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import dao.RiskLevelDAO;
import jdbc.ConnectionUtils;
import model.RiskLevel;

public class RiskLevelDAOImpl implements RiskLevelDAO {

	@Override
	public void insert(RiskLevel riskLevel) {
		try {
			Connection conn = ConnectionUtils.getConnection();
			String sql = "insert into risk_levels(level, range_min, range_max, color, system_id) values (?, ?, ?, ?, ?)";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			
			preparedStatement.setString(1, riskLevel.getLevel());
			preparedStatement.setInt(2, riskLevel.getRange_min());
			preparedStatement.setInt(3, riskLevel.getRange_max());
			preparedStatement.setString(4, riskLevel.getColor());
			preparedStatement.setInt(5, riskLevel.getSystem_id());
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void update(RiskLevel riskLevel) {
		try {
			Connection conn = ConnectionUtils.getConnection();
			String sql = "update risk_levels set level = ?,  range_min = ?, range_max = ?, color = ? where id = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			
			preparedStatement.setString(1, riskLevel.getLevel());
			preparedStatement.setInt(2, riskLevel.getRange_min());
			preparedStatement.setInt(3, riskLevel.getRange_max());
			preparedStatement.setString(4, riskLevel.getColor());
			preparedStatement.setInt(5, riskLevel.getId());
			
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void delete(int id) {
		try {
			Connection conn = ConnectionUtils.getConnection();
			String sql = "delete from risk_levels where id = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);

			preparedStatement.setInt(1, id);
			
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public RiskLevel get(int id) {
		try {
			Connection conn = ConnectionUtils.getConnection();
			String sql = "select * from risk_levels where id = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);

			preparedStatement.setInt(1, id);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				String level = resultSet.getString("level");
				int range_min = resultSet.getInt("range_min");
				int range_max = resultSet.getInt("range_max");
				String color = resultSet.getString("color");
				int system_id = resultSet.getInt("system_id");
				RiskLevel riskLevel = new RiskLevel(id, level, range_min, range_max, color, system_id);
				return riskLevel;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public Map<Integer, RiskLevel> getAllInSystem(int system_id) {
		try {
			Connection conn = ConnectionUtils.getConnection();
//			modify: order by range_min asc
			String sql = "select * from risk_levels where system_id = ?  order by range_min asc";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, system_id);
			ResultSet resultSet = preparedStatement.executeQuery();
			Map<Integer, RiskLevel> map = new LinkedHashMap<Integer, RiskLevel>();
			while(resultSet.next()) {
				int id = resultSet.getInt("id");
				String level = resultSet.getString("level");
				int range_min = resultSet.getInt("range_min");
				int range_max = resultSet.getInt("range_max");
				String color = resultSet.getString("color");
				
				RiskLevel riskLevel = new RiskLevel(id, level, range_min, range_max, color, system_id);
				map.put(id, riskLevel);
			}
			
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public List<Integer> getAllIdInSystem(int system_id) {
		try {
			Connection conn = ConnectionUtils.getConnection();
			String sql = "select id from risk_levels where system_id = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, system_id);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			List<Integer> list = new ArrayList<>();
			while(resultSet.next()) {
				int id = resultSet.getInt("id");				
				list.add(id);
			}
			
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void doUpdateAll(List<Integer> id, List<Integer> id_delete, String[] level, List<Integer> range_min,
			List<Integer> range_max, String[] color, int system_id) {
		
		for(int i = 0; i < id_delete.size(); ++i) {
			delete(id_delete.get(i));
		}
		
		int size = id.size();
		for(int i = 0; i < size; ++i) {
			RiskLevel riskLevel = new RiskLevel(id.get(i), level[i], range_min.get(i), range_max.get(i), color[i], system_id);
			if(id.get(i) == -1) {
				insert(riskLevel);
			} else {
				update(riskLevel);
			}
		}
		
	}

	
}
