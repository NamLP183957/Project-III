package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import dao.LikelihoodLevelDAO;
import jdbc.ConnectionUtils;
import model.LikelihoodLevel;

public class LikelihoodLevelDAOImpl implements LikelihoodLevelDAO {

	@Override
	public void insert(LikelihoodLevel likelihoodLevel) {
		try {
			Connection conn = ConnectionUtils.getConnection();
			String sql = "insert into likelihood_levels(level, score, color, system_id) values (?, ?, ?, ?)";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			
			preparedStatement.setString(1, likelihoodLevel.getLevel());
			preparedStatement.setInt(2, likelihoodLevel.getScore());
			preparedStatement.setString(3, likelihoodLevel.getColor());
			preparedStatement.setInt(4, likelihoodLevel.getSystem_id());
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void update(LikelihoodLevel likelihoodLevel) {
		try {
			Connection conn = ConnectionUtils.getConnection();
			String sql = "update likelihood_levels set level = ?,  score = ?, color = ? where id = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			
			preparedStatement.setString(1, likelihoodLevel.getLevel());
			preparedStatement.setInt(2, likelihoodLevel.getScore());
			preparedStatement.setString(3, likelihoodLevel.getColor());
			preparedStatement.setInt(4, likelihoodLevel.getId());
			
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}


	}

	@Override
	public void delete(int id) {
		try {
			Connection conn = ConnectionUtils.getConnection();
			String sql = "delete from likelihood_levels where id = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);

			preparedStatement.setInt(1, id);
			
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public LikelihoodLevel get(int id) {
		try {
			Connection conn = ConnectionUtils.getConnection();
			String sql = "select * from likelihood_levels where id = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);

			preparedStatement.setInt(1, id);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				String level = resultSet.getString("level");
				int score = resultSet.getInt("score");
				String color = resultSet.getString("color");
				int system_id = resultSet.getInt("system_id");
				LikelihoodLevel likelihoodLevel = new LikelihoodLevel(id, level, score, color, system_id);
				return likelihoodLevel;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public Map<Integer, LikelihoodLevel> getAllInSystem(int system_id) {
		try {
			Connection conn = ConnectionUtils.getConnection();
//			modify: order by score asc
			String sql = "select * from likelihood_levels where system_id = ? order by score asc";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, system_id);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			Map<Integer, LikelihoodLevel> map = new LinkedHashMap<Integer, LikelihoodLevel>();
			while(resultSet.next()) {
				int id = resultSet.getInt("id");
				String level = resultSet.getString("level");
				int score = resultSet.getInt("score");
				String color = resultSet.getString("color");
				
				LikelihoodLevel likelihoodLevel = new LikelihoodLevel(id, level, score, color, system_id);
				map.put(id, likelihoodLevel);
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
			String sql = "select id from risks where likelihood_level_id = ?";
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
			String sql = "select likelihood_level_id from risks where system_id = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);

			preparedStatement.setInt(1, sytem_id);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				int likelihood_level_id = resultSet.getInt("likelihood_level_id");
				list.add(likelihood_level_id);
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
			String sql = "select id from likelihood_levels where system_id = ?";
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
			LikelihoodLevel likelihoodLevel = new LikelihoodLevel(id.get(i), level[i], score.get(i), color[i], system_id);
			if(id.get(i) == -1) {
				insert(likelihoodLevel);
			} else {
				update(likelihoodLevel);
			}
		}
		
	}

}
