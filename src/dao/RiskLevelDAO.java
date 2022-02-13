package dao;

import java.util.List;
import java.util.Map;

import model.RiskLevel;

public interface RiskLevelDAO {
	void insert(RiskLevel riskLevel);
	
	void update(RiskLevel riskLevel);
	
	void delete(int id);
	
	RiskLevel  get(int id);
	
	Map<Integer, RiskLevel > getAllInSystem(int system_id);
	
	List<Integer> getAllIdInSystem(int system_id);
	
	void doUpdateAll(List<Integer> id, List<Integer> id_delete, String[] level, List<Integer> range_min, List<Integer> range_max, String[] color, int system_id);
}
