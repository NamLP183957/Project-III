package dao;

import java.util.List;
import java.util.Map;

import model.LikelihoodLevel;

public interface LikelihoodLevelDAO {
	void insert(LikelihoodLevel likelihoodLevel);
	
	void update(LikelihoodLevel likelihoodLevel);
	
	void delete(int id);
	
	LikelihoodLevel get(int id);
	
	Map<Integer, LikelihoodLevel> getAllInSystem(int system_id);
	
	List<Integer> getAllIdInSystem(int system_id);
	
	boolean isUsed(int id);
	
	List<Integer> usedInSystem(int sytem_id);
	
	void doUpdateAll(List<Integer> id, List<Integer> id_delete, String[] level, List<Integer> score, String[] color, int system_id);
}
