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
	private AppointmentStatus appointmentStatus = AppointmentStatus.PENDING;
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
	
	public boolean shouldFilterByAStatus() {
		return appointmentStatus != null && (appointmentStatus == AppointmentStatus.ABSENT || appointmentStatus == AppointmentStatus.PRESENT || appointmentStatus == AppointmentStatus.PENDING);
	}
	
	public AppointmentQuery(String q) {
		this(q, FilterStatus.ONLY_ACTIVE);
	}
	
	public boolean shouldFilterByPatient() {
		return patient != null && (patient.getDni().trim() != "" || patient.getId() > 0);
	}
	
	public boolean shouldFilterByDoctor() {
		return doctor != null && (doctor.getFile() > 0 || doctor.getId() > 0);
	}
	
	public AppointmentQuery filterByPatient(String dni, String idString) {
		if(dni.trim().length() == 0 && idString.trim().length() == 0) {
			this.setPatient(null);
			return this;
		}
		Patient p = new Patient();
		p.setDni(dni);
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
		if(filter == null || filter.trim().length() == 0) {
			setAppointmentStatus(null);
			return this;
		} else if(filter.trim().toUpperCase().equals("PENDING")) setAppointmentStatus(AppointmentStatus.PENDING);
		else if(filter.trim().toUpperCase().equals("ABSENT")) setAppointmentStatus(AppointmentStatus.ABSENT);
		else if(filter.trim().toUpperCase().equals("PRESENT")) setAppointmentStatus(AppointmentStatus.PRESENT);
		else setAppointmentStatus(null);
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
		StringBuilder hql = new StringBuilder("SELECT a FROM AppointmentMinimalView a JOIN a.patient p JOIN a.assignedDoctor d ");
		hql.append(" WHERE (");
		hql.append(" CONCAT(p.name, ' ', p.surname) LIKE :q OR CONCAT(p.surname, ' ', p.name) LIKE :q ");
		hql.append(" OR CONCAT(d.name, ' ', d.surname) LIKE :q OR CONCAT(d.surname, ' ', d.name) LIKE :q ");
		hql.append(") ");
		if(getDate() != null) {
			if(getLimitDate() != null) {
				hql.append(" AND ( a.date BETWEEN :date AND :limit ) ");
			} else {
				hql.append(" AND (a.date) = :date ");
			}
		}
		if(shouldFilterByAStatus()) hql.append(" AND a.status = :appointmentStatus ");
		if(shouldFilterByPatient()) hql.append(" AND ( p.dni LIKE :pdni OR p.id = :pId ) ");
		if(shouldFilterByDoctor()) hql.append(" AND ( d.file = :file OR d.id = :dId ) ");
		if(getStatus() != FilterStatus.BOTH) {
            hql.append("AND a.active = :status ");
        }
		hql.append(" ORDER BY a.date ");
		Query query = session.createQuery(hql.toString());
		query.setParameter("q", "%" + getQueryText() + "%");
		if(getDate() != null) {
			query.setDate("date", new java.sql.Date(getDate().getTime()));
			if(getLimitDate() != null) query.setDate("limit", new java.sql.Date(getLimitDate().getTime()));
		}
		if(shouldFilterByAStatus()) query.setParameter("appointmentStatus", getAppointmentStatus());
		if(shouldFilterByPatient()) {
			query.setParameter("pdni", getPatient().getDni());
			query.setParameter("pId", getPatient().getId());
		} 
		if(shouldFilterByDoctor()) {
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
        default:
            break;
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

	public AppointmentQuery filterByStatus(AppointmentStatus appointmentStatus2) {
		this.appointmentStatus = appointmentStatus2;
		return this;
	}
	
	

}
