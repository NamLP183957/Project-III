package model;

import java.util.List;

public class UserSystem {
	private int id;
	private ITSystem system;
	private User user;
	
	public UserSystem() {
		// TODO Auto-generated constructor stub
	}

	public UserSystem(int id, ITSystem system, User user) {
		super();
		this.id = id;
		this.system = system;
		this.user = user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ITSystem getSystem() {
		return system;
	}

	public void setSystem(ITSystem system) {
		this.system = system;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "UserSystem [id=" + id + ", system=" + system + ", user=" + user + "]";
	}
	
	
}
