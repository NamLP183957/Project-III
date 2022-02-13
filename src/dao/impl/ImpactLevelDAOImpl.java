package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import dao.ImpactLevelDAO;
import jdbc.ConnectionUtils;
import model.ImpactLevel;

public class ImpactLevelDAOImpl implements ImpactLevelDAO {

	@Override
	public void insert(ImpactLevel impactLevel) {
		try {
			Connection conn = ConnectionUtils.getConnection();
			String sql = "insert into impact_levels(level, score, color, system_id) values (?, ?, ?, ?)";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			
			preparedStatement.setString(1, impactLevel.getLevel());
			preparedStatement.setInt(2, impactLevel.getScore());
			preparedStatement.setString(3, impactLevel.getColor());
			preparedStatement.setInt(4, impactLevel.getSystem_id());
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void update(ImpactLevel impactLevel) {
		try {
			Connection conn = ConnectionUtils.getConnection();
			String sql = "update impact_levels set level = ?,  score = ?, color = ? where id = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			
			preparedStatement.setString(1,impactLevel.getLevel());
			preparedStatement.setInt(2, impactLevel.getScore());
			preparedStatement.setString(3, impactLevel.getColor());
			preparedStatement.setInt(4, impactLevel.getId());
			
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void delete(int id) {
		try {
			Connection conn = ConnectionUtils.getConnection();
			String sql = "delete from impact_levels where id = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);

			preparedStatement.setInt(1, id);
			
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	

	@Override
	public ImpactLevel get(int id) {
		try {
			Connection conn = ConnectionUtils.getConnection();
			String sql = "select * from impact_levels where id = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);

			preparedStatement.setInt(1, id);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				String level = resultSet.getString("level");
				int score = resultSet.getInt("score");
				String color = resultSet.getString("color");
				int system_id = resultSet.getInt("system_id");
				
				ImpactLevel impactLevel = new ImpactLevel(id, level, score, color, system_id);
				return impactLevel;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public Map<Integer, ImpactLevel> getAllInSystem(int system_id) {
		try {
			Connection conn = ConnectionUtils.getConnection();
//			modify: order by score asc
			String sql = "select * from impact_levels where system_id = ? order by score asc";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, system_id);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			Map<Integer, ImpactLevel> map = new LinkedHashMap<Integer, ImpactLevel>();
			while(resultSet.next()) {
				int id = resultSet.getInt("id");
				String level = resultSet.getString("level");
				int score = resultSet.getInt("score");
				String color = resultSet.getString("color");
				
				ImpactLevel impactLevel = new ImpactLevel(id, level, score, color, system_id);
				map.put(id, impactLevel);
			}
			
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public boolean isUsed(int id) {
		try {
			Connection conn = ConnectionUtils.getConnection();
			String sql = "select id from risks where impact_level_id = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);

			preparedStatement.setInt(1, id);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<Integer> usedInSystem(int sytem_id) {
		List<Integer> list = new ArrayList<>();
		
		try {
			Connection conn = ConnectionUtils.getConnection();
//			String sql = "select id from impact_levels where system_id = ? and exists( select id from risks where impact_level_id = id)";
			String sql = "select impact_level_id from risks where system_id = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);

			preparedStatement.setInt(1, sytem_id);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				int impact_level_id = resultSet.getInt("impact_level_id");
				list.add(impact_level_id);
			}
			
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Integer> getAllIdInSystem(int system_id) {
		try {
			Connection conn = ConnectionUtils.getConnection();
			String sql = "select id from impact_levels where system_id = ?";
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
	public void doUpdateAll(List<Integer> id, List<Integer> id_delete, String[] level, List<Integer> score,
			String[] color, int system_id) {
		
		for(int i = 0; i < id_delete.size(); ++i) {
			delete(id_delete.get(i));
		}
		
		int size = id.size();
		for(int i = 0; i < size; ++i) {
			ImpactLevel impactLevel = new ImpactLevel(id.get(i), level[i], score.get(i), color[i], system_id);
			if(id.get(i) == -1) {
				insert(impactLevel);
			} else {
				update(impactLevel);
			}
		}
		
	}

}
