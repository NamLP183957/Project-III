package dao;

import java.util.List;
import java.util.Map;

import model.ImpactLevel;

public interface ImpactLevelDAO {
	void insert(ImpactLevel impactLevel);
	
	void update(ImpactLevel impactLevel);
	
	void delete(int id);
	
	ImpactLevel get(int id);
	
	Map<Integer, ImpactLevel> getAllInSystem(int system_id);
	
	List<Integer> getAllIdInSystem(int system_id);
	
	boolean isUsed(int id);
	
	List<Integer> usedInSystem(int sytem_id);
	
	void doUpdateAll(List<Integer> id, List<Integer> id_delete, String[] level, List<Integer> score, String[] color, int system_id);
}
