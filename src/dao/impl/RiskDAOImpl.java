package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.RiskDAO;
import jdbc.ConnectionUtils;
import model.Risk;

public class RiskDAOImpl implements RiskDAO {

	@Override
	public void insert(Risk risk, List<Integer> assets) {
		try {
			Connection conn = ConnectionUtils.getConnection();
			String sql = "insert into risks(short_description, flaw, threat, threat_type, solution, system_id) values (?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, risk.getShort_description());
			preparedStatement.setString(2, risk.getFlaw());
			preparedStatement.setString(3, risk.getThreat());
			preparedStatement.setInt(4, risk.getThreat_type());
			preparedStatement.setString(5, risk.getSolution());
			preparedStatement.setInt(6, risk.getSystem_id());
			
			preparedStatement.executeUpdate();
			
			
			sql = "SELECT IDENT_CURRENT('risks') as id;";
			Statement st = conn.createStatement();
			ResultSet resultSet = st.executeQuery(sql);
			int id = -1;
			if(resultSet.next()) {
				id = resultSet.getInt("id");
			}
			
			if(risk.getLikelihood_level_id() != -1) {
				sql = "update risks set likelihood_level_id = ? where id = ?";
				preparedStatement = conn.prepareStatement(sql);
				preparedStatement.setInt(1, risk.getLikelihood_level_id());
				preparedStatement.setInt(2, id);
				preparedStatement.executeUpdate();
			}
			
			if(risk.getImpact_level_id() != -1) {
				sql = "update risks set impact_level_id = ?  where id = ?";
				preparedStatement = conn.prepareStatement(sql);
				preparedStatement.setInt(1, risk.getImpact_level_id());
				preparedStatement.setInt(2, id);
				preparedStatement.executeUpdate();
			}
			
			for(int asset_id: assets) {
				sql = "insert into risk_asset(risk_id, asset_id) values (?, ?)";
				preparedStatement = conn.prepareStatement(sql);
				preparedStatement.setInt(1, id);
				preparedStatement.setInt(2, asset_id);
				preparedStatement.executeUpdate();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateInfo(Risk risk, List<Integer> assets) {
		try {
			Connection conn = ConnectionUtils.getConnection();
			String sql = "update risks set short_description = ?, flaw = ?, threat = ?, threat_type = ?, solution = ? where id = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, risk.getShort_description());
			preparedStatement.setString(2, risk.getFlaw());
			preparedStatement.setString(3, risk.getThreat());
			preparedStatement.setInt(4, risk.getThreat_type());
			preparedStatement.setString(5, risk.getSolution());
			preparedStatement.setInt(6, risk.getId());
			
			preparedStatement.executeUpdate();
			
			
			sql = "delete from risk_asset where risk_id = ?";
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, risk.getId());
			preparedStatement.executeUpdate();
			
			for(int asset_id: assets) {
				sql = "insert into risk_asset(risk_id, asset_id) values (?, ?)";
				preparedStatement = conn.prepareStatement(sql);
				preparedStatement.setInt(1, risk.getId());
				preparedStatement.setInt(2, asset_id);
				preparedStatement.executeUpdate();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Override
	public void updateAssessment(int id, int likelihood, int impact) {
		try {
			Connection conn = ConnectionUtils.getConnection();
			String sql;
			PreparedStatement preparedStatement;
			
			if(likelihood != -1) {
				sql = "update risks set likelihood_level_id = ? where id = ?";
				preparedStatement = conn.prepareStatement(sql);
				preparedStatement.setInt(1, likelihood);
				preparedStatement.setInt(2, id);
				preparedStatement.executeUpdate();
			}
			
			if(impact != -1) {
				sql = "update risks set impact_level_id = ?  where id = ?";
				preparedStatement = conn.prepareStatement(sql);
				preparedStatement.setInt(1, impact);
				preparedStatement.setInt(2, id);
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
			String sql = "delete from risks where id = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public Risk get(int id) {
		try {
			Connection conn = ConnectionUtils.getConnection();
			String sql = "select * from risks where id = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				
				String short_description = resultSet.getString("short_description");
				String flaw = resultSet.getString("flaw");
				String threat = resultSet.getString("threat");
				int threat_type = resultSet.getInt("threat_type");
				String solution = resultSet.getString("solution");
				int system_id = resultSet.getInt("system_id");
				int likelihood_level_id = resultSet.getInt("likelihood_level_id");
				int impact_level_id = resultSet.getInt("impact_level_id");
				String created_time = resultSet.getString("created_time");
				String modified_time = resultSet.getString("modified_time");
				
				Risk risk = new Risk();
				risk.setId(id);
				risk.setShort_description(short_description);
				risk.setFlaw(flaw);
				risk.setThreat(threat);
				risk.setThreat_type(threat_type);
				risk.setSolution(solution);
				risk.setSystem_id(system_id);
				risk.setLikelihood_level_id(likelihood_level_id);
				risk.setImpact_level_id(impact_level_id);
				risk.setCreated_time(created_time);
				risk.setModified_time(modified_time);


				return risk;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Risk> getAllRisksInSystem(int system_id) {
		try {
			Connection conn = ConnectionUtils.getConnection();
			String sql = "select * from risks where system_id = ? order by modified_time desc";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, system_id);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			List<Risk> list = new ArrayList<Risk>();
			while(resultSet.next()) {
				int id = resultSet.getInt("id");
				String short_description = resultSet.getString("short_description");
				String flaw = resultSet.getString("flaw");
				String threat = resultSet.getString("threat");
				int threat_type = resultSet.getInt("threat_type");
				String solution = resultSet.getString("solution");
				
				int likelihood_level_id = resultSet.getInt("likelihood_level_id");
				int impact_level_id = resultSet.getInt("impact_level_id");
				String created_time = resultSet.getString("created_time");
				String modified_time = resultSet.getString("modified_time");
				
				Risk risk = new Risk();
				risk.setId(id);
				risk.setShort_description(short_description);
				risk.setFlaw(flaw);
				risk.setThreat(threat);
				risk.setThreat_type(threat_type);
				risk.setSolution(solution);
				risk.setSystem_id(system_id);
				risk.setLikelihood_level_id(likelihood_level_id);
				risk.setImpact_level_id(impact_level_id);
				risk.setCreated_time(created_time);
				risk.setModified_time(modified_time);
				list.add(risk);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Risk> getRisksOfTrouble(int trouble_id) {
		try {
			Connection conn = ConnectionUtils.getConnection();
			String sql = "select id, short_description from risks where id in (select risk_id from trouble_risk where trouble_id = ?)";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, trouble_id);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			List<Risk> list = new ArrayList<Risk>();
			while(resultSet.next()) {
				int id = resultSet.getInt("id");
				String short_description = resultSet.getString("short_description");
				
				Risk risk = new Risk();
				risk.setId(id);
				risk.setShort_description(short_description);

				list.add(risk);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int getNumRisksInSystem(int system_id) {
		try {
			Connection conn = ConnectionUtils.getConnection();
			String sql = "select COUNT(*) as num from risks where system_id = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			
			preparedStatement.setInt(1, system_id);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				int res = resultSet.getInt("num");
				return res;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	@Override
	public int getNumRisksInAssessment(int system_id) {
		try {
			Connection conn = ConnectionUtils.getConnection();
			String sql = "select COUNT(*) as num from assessment where system_id = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			
			preparedStatement.setInt(1, system_id);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				int res = resultSet.getInt("num");
				return res;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	@Override
	public Map<Integer, Integer> countByRiskLevel(int system_id) {
		try {
			Connection conn = ConnectionUtils.getConnection();
			String sql = "select id, COUNT(*) as num from assessment join risk_levels on risk_level_id = id"
					+ " where assessment.system_id = ?"
					+ " group by id";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			
			preparedStatement.setInt(1, system_id);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			Map<Integer, Integer> map = new HashMap<>();
			while(resultSet.next()) {
				int id = resultSet.getInt("id");
				int num = resultSet.getInt("num");
				map.put(id, num);
			}
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	

}
