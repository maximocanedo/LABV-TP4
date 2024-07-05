package web.entity;

import java.util.Date;

public interface IAppointment {

	int getId();

	Date getDate();

	String getRemarks();

	AppointmentStatus getStatus();

	boolean isActive();

	String getStatusDescription();

	void setId(int id);

	void setDate(Date fecha);

	void setRemarks(String observacion);

	void setStatus(AppointmentStatus estado);

	void setActive(boolean active);

}