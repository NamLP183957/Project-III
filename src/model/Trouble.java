package model;

public class Trouble {

	private int id;
	private String short_description;
	private String detail;
	private int status;
	private String solution;
	private int system_id;
	private String time_happen;
	private String created_time;
	private String modified_time;
	
	public Trouble() {
		// TODO Auto-generated constructor stub
	}

	

	public Trouble(int id, String short_description, String detail, int status, String solution, int system_id, String time_happen,
			String created_time, String modified_time) {
		super();
		this.id = id;
		this.short_description = short_description;
		this.detail = detail;
		this.status = status;
		this.solution = solution;
		this.system_id = system_id;
		this.time_happen = time_happen;
		this.created_time = created_time;
		this.modified_time = modified_time;
	}
	
	

	public Trouble(int id, String short_description, String detail, int status, String solution, String time_happen) {
		super();
		this.id = id;
		this.short_description = short_description;
		this.detail = detail;
		this.status = status;
		this.solution = solution;
		this.time_happen = time_happen;
	}

	public Trouble(String short_description, String detail, int status, String solution, int system_id,
			String time_happen) {
		super();
		this.short_description = short_description;
		this.detail = detail;
		this.status = status;
		this.solution = solution;
		this.system_id = system_id;
		this.time_happen = time_happen;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getShort_description() {
		return short_description;
	}

	public void setShort_description(String short_description) {
		this.short_description = short_description;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getTime_happen() {
		return time_happen;
	}

	public void setTime_happen(String time_happen) {
		this.time_happen = time_happen;
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



	public int getSystem_id() {
		return system_id;
	}



	public void setSystem_id(int system_id) {
		this.system_id = system_id;
	}



	public String getSolution() {
		return solution;
	}



	public void setSolution(String solution) {
		this.solution = solution;
	}

	
	
}
