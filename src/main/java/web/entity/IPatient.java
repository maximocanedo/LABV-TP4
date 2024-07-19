package web.entity;

public interface IPatient {

	int getId();
	String getName();
	String getSurname();
	String getDni();
	boolean isActive();
	
	void setId(int id);
	void setName(String name);
	void setSurname(String surname);
	void setDni(String dni);
	void setActive(boolean active);
		
}
