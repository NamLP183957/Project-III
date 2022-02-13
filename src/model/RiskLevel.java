package model;

public class RiskLevel {

	private int id;
	private String level;
	private int range_min;
	private int range_max;
	private String color;
	private int system_id;
	
	public RiskLevel() {
		// TODO Auto-generated constructor stub
	}

	

	public RiskLevel(int id, String level, int range_min, int range_max, String color, int system_id) {
		super();
		this.id = id;
		this.level = level;
		this.range_min = range_min;
		this.range_max = range_max;
		this.color = color;
		this.system_id = system_id;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public int getRange_min() {
		return range_min;
	}

	public void setRange_min(int range_min) {
		this.range_min = range_min;
	}

	public int getRange_max() {
		return range_max;
	}

	public void setRange_max(int range_max) {
		this.range_max = range_max;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}



	public int getSystem_id() {
		return system_id;
	}



	public void setSystem_id(int system_id) {
		this.system_id = system_id;
	}

	

	
	
	
}
