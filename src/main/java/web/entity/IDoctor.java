package web.entity;

import java.util.Set;

/**
 * Interfaz de la entidad Doctor.
 * 
 * Esta interfaz existe para permitir devolver {@link Doctor} o {@link web.entity.view.DoctorMinimalView DoctorMinimalView}.
 * <br /><br />
 * <b>¿Por qué existen estas dos clases?</b><br />
 * Solucioné el tema de los permisos mediante la asignación de <u>permisos individuales</u> a cada usuario.<br />
 * Si un usuario solicita ver los datos de un doctor, en función de los permisos asignados, se le devolverá un {@link Doctor}, un {@link web.entity.view.DoctorMinimalView DoctorMinimalView}, o se lanzará un {@link web.exceptions.NotAllowedException NotAllowedException}.
 * 
 * @author Max
 * 
 * @see Doctor
 * @see view.DoctorMinimalView DoctorMinimalView
 *
 */
public interface IDoctor {

	int getId();
	int getFile();
	String getName();
	String getSurname();
	Specialty getSpecialty();
	String getFullName();
	String getUsername();
	String getSpecialtyName();
	Set<Schedule> getSchedules();
	boolean isActive();
	String listSchedules();
	
	void setId(int id);
	void setFile(int file);
	void setName(String name);
	void setSurname(String surname);
	void setSpecialty(Specialty specialty);
	void setSchedules(Set<Schedule> schedules);
	void setActive(boolean active);
	
}
