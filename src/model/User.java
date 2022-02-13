package model;

public class User {
	private int id;
	private String email;
	private String username;
	private String password;
	private String name;
	private String phone;
	private String organization;
	private String position;
	private String created_time;
	private String modified_time;
	
	public User() {
		
	}
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public User(int id, String email, String username, String password, String name) {
		this.id = id;
		this.email = email;
		this.username = username;
		this.password = password;
		this.name = name;
	}
	
	

	public User(int id, String email, String username, String password, String name, String phone, String organization,
			String position, String created_time, String modified_time) {
		super();
		this.id = id;
		this.email = email;
		this.username = username;
		this.password = password;
		this.name = name;
		this.phone = phone;
		this.organization = organization;
		this.position = position;
		this.created_time = created_time;
		this.modified_time = modified_time;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
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
		return "User [id=" + id + ", email=" + email + ", username=" + username + ", password=" + password + ", name="
				+ name + ", phone=" + phone + ", organization=" + organization + ", position=" + position
				+ ", created_time=" + created_time + ", modified_time=" + modified_time + "]";
	}
	
	
	
	
}
