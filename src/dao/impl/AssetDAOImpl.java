package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dao.AssetDAO;
import jdbc.ConnectionUtils;
import model.Asset;

public class AssetDAOImpl implements AssetDAO {

	@Override
	public void insert(Asset asset) {
		try {
			Connection conn = ConnectionUtils.getConnection();
			String sql = "insert into assets(name, description, system_id) values (?, ?, ?)";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, asset.getName());
			preparedStatement.setString(2, asset.getDescription());
			preparedStatement.setInt(3, asset.getSystem_id());
			
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void update(Asset asset) {
		try {
			Connection conn = ConnectionUtils.getConnection();
			String sql = "update assets set name = ?, description = ? where id = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, asset.getName());
			preparedStatement.setString(2, asset.getDescription());
			preparedStatement.setInt(3, asset.getId());
			
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void delete(int id) {
		try {
			Connection conn = ConnectionUtils.getConnection();
			String sql = "delete from assets where id = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public Asset get(int id) {
		try {
			Connection conn = ConnectionUtils.getConnection();
			String sql = "select * from assets where id = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				String name = resultSet.getString("name");
				String description = resultSet.getString("description");
				int system_id = resultSet.getInt("system_id");
				String created_time = resultSet.getString("created_time");
				String modified_time = resultSet.getString("modified_time");
				
				Asset asset = new Asset(id, name, description, system_id, created_time, modified_time);
				return asset;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Asset> getAllAssetsInSystem(int system_id) {
		try {
			Connection conn = ConnectionUtils.getConnection();
			String sql = "select * from assets where system_id = ? order by modified_time desc";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, system_id);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			List<Asset> list = new ArrayList<Asset>();
			while(resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				String description = resultSet.getString("description");
				String created_time = resultSet.getString("created_time");
				String modified_time = resultSet.getString("modified_time");
				
				Asset asset = new Asset(id, name, description, system_id, created_time, modified_time);
				list.add(asset);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Asset> getAssetsOfRisk(int risk_id) {
		try {
			Connection conn = ConnectionUtils.getConnection();
			String sql = "select id, name from assets where id in (select distinct asset_id from risk_asset where risk_id = ?)";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, risk_id);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			List<Asset> list = new ArrayList<Asset>();
			while(resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				
				Asset asset = new Asset();
				asset.setId(id);
				asset.setName(name);
				list.add(asset);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Asset> getAssetsOfTrouble(int trouble_id) {
		try {
			Connection conn = ConnectionUtils.getConnection();
			String sql = "select id, name from assets where id in (select distinct asset_id from trouble_asset where trouble_id = ?)";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, trouble_id);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			List<Asset> list = new ArrayList<Asset>();
			while(resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				
				Asset asset = new Asset();
				asset.setId(id);
				asset.setName(name);
				list.add(asset);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
