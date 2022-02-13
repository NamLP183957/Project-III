package dao;

import java.util.List;

import model.Asset;

public interface AssetDAO {
	void insert(Asset asset);
	
	void update(Asset asset);
	
	void delete(int id);
	
	Asset get(int id);
	
	List<Asset> getAllAssetsInSystem(int system_id);
	
	List<Asset> getAssetsOfRisk(int risk_id);
	
	List<Asset> getAssetsOfTrouble(int trouble_id);
}
