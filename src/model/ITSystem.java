package model;

public class ITSystem {
	private int id;
	private String name;
	private String description;
	private String created_time;
	private String modified_time;
	
	public ITSystem() {
		// TODO Auto-generated constructor stub
	}
		
	public ITSystem(int id, String name, String description) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
	}


	public ITSystem(int id, String name, String description, String created_time, String modified_time) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.created_time = created_time;
		this.modified_time = modified_time;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreated_time() {
		return created_time;
	}
	public void setCreated_time(String created_time) {
		this.created_time = created_time;
	}
	public String getModified_time() {
		return modified_time;
	}
	public void setModified_time(String modified_time) {
		this.modified_time = modified_time;
	}
	@Override
	public String toString() {
		return "System [id=" + id + ", name=" + name + ", description=" + description
				+ ", created_time=" + created_time + ", modified_time=" + modified_time + "]";
	}
	
	
	
	
}
