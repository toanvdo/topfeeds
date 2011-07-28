package au.edu.unsw.cse.topfeeds.model;

public class Friend {
	private int id;
	private String name;
	
	public Friend(){}
	
	public Friend(int id, String name) {
		super();
		this.id = id;
		this.name = name;
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
	
	
	
}
