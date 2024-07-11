package web.entity;

public interface IPatient {

	int getId();
	String getName();
	String getSurname();
	String getDni();
	
	void setId(int id);
	void setName(String name);
	void setSurname(String surname);
	void setDni(String dni);
	
		
}
