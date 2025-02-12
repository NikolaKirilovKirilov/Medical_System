package entities;

public class Entity {
	
		public int id;
		public String name;
		public String description;
		private String password;
	
	public Entity() {}
	public Entity(int id, String name, String description, String Password) {
		this.id = id;
		this.name = name;
		this.description = description;
	}
	
	
}
