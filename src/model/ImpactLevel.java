package model;

public class ImpactLevel {

	private int id;
	private String level;
	private int score;
	private String color;
	private int system_id;
	
	public ImpactLevel() {
		// TODO Auto-generated constructor stub
	}
	
	

	public ImpactLevel(int id, String level, int score, String color, int system_id) {
		super();
		this.id = id;
		this.level = level;
		this.score = score;
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

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
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
