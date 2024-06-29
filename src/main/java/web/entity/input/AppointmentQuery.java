package web.entity.input;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.Query;
import org.hibernate.Session;

import web.entity.AppointmentStatus;
import web.entity.Doctor;
import web.entity.Patient;

/**
 * 
 * @author Max
 *
 */
public class AppointmentQuery implements Searchable {

	private String q;
	private AppointmentStatus appointmentStatus = null;
	private FilterStatus status = FilterStatus.ONLY_ACTIVE;
	private Date date = null;
	private Date limit = null;
	private Patient patient = null;
	private Doctor doctor = null;
	
	private int page;
	private int size;
	
	public AppointmentQuery(String q, FilterStatus status) {
		setQueryText(q);
		setStatus(status);
	}
	
	public AppointmentQuery(String q) {
		this(q, FilterStatus.ONLY_ACTIVE);
	}
	
	public AppointmentQuery filterByPatient(String dniString, String idString) {
		Patient p = new Patient();
		try {
			if(dniString.trim() != "") {
				int dni = Integer.parseInt(dniString);
				p.setDni(dni);
			}
		} catch(NumberFormatException expected) { }
		try {
			if(idString.trim() != "") {
				int id = Integer.parseInt(idString);
				p.setId(id);
			}
		} catch(NumberFormatException expected) { }
		this.setPatient(p);
		return this;
	}
	
	public AppointmentQuery filterByDoctor(String fileString, String idString) {
		Doctor d = new Doctor();
		try {
			if(fileString.trim() != "") {
				int file = Integer.parseInt(fileString);
				d.setFile(file);
			}
		} catch(NumberFormatException expected) { }
		try {
			if(idString.trim() != "") {
				int id = Integer.parseInt(idString);
				d.setId(id);
			}
		} catch(NumberFormatException expected) { }
		this.setDoctor(d);
		return this;
	}
	
	public AppointmentQuery filterByStatus(String filter) {
		if(filter == null || filter.trim() == "") {
			setAppointmentStatus(null);
			return this;
		}
		try {
			AppointmentStatus s = AppointmentStatus.valueOf(filter);
			setAppointmentStatus(s);
		} catch(IllegalArgumentException expected) {
			
		}
		return this;
	}
	
	public AppointmentQuery filterByDate(String date, String limit) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            setDate(formatter.parse(date));
            setLimitDate(formatter.parse(limit));
        } catch (ParseException expected) {
        	
        }
        
        return this;
	}
	
	public AppointmentQuery paginate(String page, String size) {
		try {
			this.page = Integer.parseInt(page);
			this.size = Integer.parseInt(size);
		} catch(NumberFormatException expected) {
			
		}
		return this;
	}
	
	public Date getDate() {
		return this.date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public AppointmentStatus getAppointmentStatus() {
		return this.appointmentStatus;
	}
	
	public void setAppointmentStatus(AppointmentStatus appointmentStatus) {
		this.appointmentStatus = appointmentStatus;
	}
	
	public Date getLimitDate() {
		return this.limit;
	}
	
	public void setLimitDate(Date date) {
		this.limit = date;
	}
	
	
	@Override
	public String getQueryText() {
		return this.q;
	}

	@Override
	public int getPage() {
		return this.page;
	}

	public FilterStatus getStatus() {
		return this.status;
	}
	
	@Override
	public int getSize() {
		return this.size;
	}

	@Override
	public Query toQuery(Session session) {
		StringBuilder hql = new StringBuilder("SELECT a FROM Appointment a ");
		hql.append(" WHERE (");
		hql.append(" CONCAT(a.patient.name, ' ', a.patient.surname) LIKE :q OR CONCAT(a.patient.surname, ' ', a.patient.name) LIKE :q ");
		hql.append(" OR CONCAT(a.assignedDoctor.name, ' ', a.assignedDoctor.surname) LIKE :q OR CONCAT(a.assignedDoctor.surname, ' ', a.assignedDoctor.name) LIKE :q ");
		hql.append(") ");
		if(getDate() != null) {
			if(getLimitDate() != null) {
				hql.append(" AND ( a.date BETWEEN :date AND :limit ) ");
			} else {
				hql.append(" AND DATE(a.date) = :date ");
			}
		}
		if(getAppointmentStatus() != null) {
			hql.append(" AND a.status = :appointmentStatus ");
		}
		if(getPatient() != null) {
			hql.append(" AND ( a.patient.dni = :pdni OR a.patient.id = :pId ) ");
		}
		if(getDoctor() != null) {
			hql.append(" AND ( a.doctor.file = :file OR a.doctor.id = :dId ) ");
		}
		hql.append("");
		if(getStatus() != FilterStatus.BOTH) {
			hql.append(" AND p.active = :status ");
		}
		Query query = session.createQuery(hql.toString());
		query.setParameter("q", "%" + getQueryText() + "%");
		if(getDate() != null) {
			query.setDate("date", getDate());
			if(getLimitDate() != null) query.setDate("limit", getLimitDate());
		}
		if(getAppointmentStatus() != null) {
			query.setParameter("appointmentStatus", getAppointmentStatus());
		}
		if(getPatient() != null) {
			query.setParameter("pdni", getPatient().getDni());
			query.setParameter("pId", getPatient().getId());
		}
		if(getDoctor() != null) {
			query.setParameter("file", getDoctor().getFile());
			query.setParameter("dId", getDoctor().getId());
		}
		switch(getStatus()) {
		case ONLY_ACTIVE: 
			query.setParameter("status", true);
			break;
		case ONLY_INACTIVE:
			query.setParameter("status", false);
			break;
		default: break;
		}
		query.setFirstResult((page - 1) * size);
		query.setMaxResults(size);
		return query;
	}
	
	public void setQueryText(String q) {
		this.q = q;
	}
	
	public void setStatus(FilterStatus s) {
		this.status = s;
	}
	
	public void setPage(int page) {
		this.page = page;
	}
	
	public void setSize(int size) {
		this.size = size;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
	
	

}
