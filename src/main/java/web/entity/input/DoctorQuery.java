package web.entity.input;

import org.hibernate.Query;
import org.hibernate.Session;

import web.entity.Day;

public class DoctorQuery implements Searchable {
	 private String q;
	    private FilterStatus status = FilterStatus.ONLY_ACTIVE;
	    private Day day = null;
	    private int specialty = -1;
	    private int page;
	    private int size;
		public boolean checkUnassigned = false; // ¿Filtrar doctores que no están asociados a un usuario?
	    
	    public DoctorQuery(String q, FilterStatus status) {
	        setQueryText(q);
	        setStatus(status);
	    }
	    
	    public DoctorQuery(String q) {
	        this(q, FilterStatus.ONLY_ACTIVE);
	    }
	    
	    public DoctorQuery filterByDay(String day) {
	    	if(day == null || day.trim() == "") {
				setDay(null);
				return this;
			}
			try {
				Day s = Day.valueOf(day);
				setDay(s);
			} catch(IllegalArgumentException expected) {
				
			}
			return this;
	    }
	    
	    public DoctorQuery filterBySpecialty(String specialty) {
	    	if(specialty == null || specialty.trim() == "") {
	    		setSpecialty(-1);
	    		return this;
	    	}
	    	try {
	    		setSpecialty(Integer.parseInt(specialty));
	    	} catch (NumberFormatException expected) {
	    		
	    	}
	    	return this;
	    }
	    
	    public DoctorQuery paginate(int page, int size) {
	        this.page = page;
	        this.size = size;
	        return this;
	    }
	    
	    public DoctorQuery paginate(String page, String size) {
	        try {
	            this.page = Integer.parseInt(page);
	            this.size = Integer.parseInt(size);
	        } catch(NumberFormatException expected) {
	            
	        }
	        return this;
	    }
	    
	    @Override
	    public Query toQuery(Session session) {
	        StringBuilder hql = new StringBuilder("SELECT d FROM DoctorMinimalView d ");
	        if(checkUnassigned) hql.append(" LEFT JOIN d.user u ");
	        hql.append("WHERE 1 = 1 "); // Condición fallback si se da el caso que ningún filtro se aplique.
	        if(getStatus() != FilterStatus.BOTH) {
	            hql.append("AND d.active = :status ");
	        }
	        if(checkUnassigned) hql.append(" AND u IS NULL ");
	        if (getQueryText() != null && !getQueryText().isEmpty()) {
	            hql.append("AND (d.name LIKE :queryText OR d.surname LIKE :queryText OR " +
	                       "CONCAT(d.name, ' ', d.surname) LIKE :queryText OR " +
	                       "CONCAT(d.surname, ', ', d.name) LIKE :queryText OR " +
	                       "CAST(d.file AS string) LIKE :queryText) ");
	        }

	        
	        if(getDay() != null) {
	        	hql.append("AND EXISTS (SELECT s FROM d.schedules s WHERE s.beginDay = :day OR s.finishDay = :day) ");
	        }
	        
	        if(getSpecialty() != -1) {
	        	hql.append("AND d.specialty.id = :specialty ");
	        }
	        
	        Query query = session.createQuery(hql.toString());
	        
	        if (getQueryText() != null && !getQueryText().isEmpty()) {
	            query.setParameter("queryText", "%" + getQueryText() + "%");
	        }
	        
	        if(getDay() != null) {
	        	query.setParameter("day", getDay());
	        }
	        
	        if(getSpecialty() != -1) {
	        	query.setParameter("specialty", getSpecialty());
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
	    
	    @Override
	    public String getQueryText() {
	        return q;
	    }
	    
	    private void setQueryText(String q) {
	        this.q = q;
	    }
	    
	    public FilterStatus getStatus() {
	        return status;
	    }
	    
	    private void setStatus(FilterStatus status) {
	        if(status == null) return;
	        this.status = status;
	    }
	    
	    @Override
	    public int getPage() {
	        return this.page;
	    }
	    
	    @Override
	    public int getSize() {
	        return this.size;
	    }

		public Day getDay() {
			return day;
		}

		public void setDay(Day day) {
			this.day = day;
		}

		public int getSpecialty() {
			return specialty;
		}

		public void setSpecialty(int specialty) {
			this.specialty = specialty;
		}

		public DoctorQuery filterByUnassigned(boolean checkUnassigned) {
			this.checkUnassigned = checkUnassigned;
			return this;
		}
	    
	    
	    
	    
}
