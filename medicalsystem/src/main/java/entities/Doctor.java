package entities;

public class Doctor extends Entity{
	
	public String specialization;
	
	public Doctor() {
		super();
	}
	
	public Doctor(int id, String name, String specialization, String password) {
		super(id, name, specialization, password);
	}
	
}
