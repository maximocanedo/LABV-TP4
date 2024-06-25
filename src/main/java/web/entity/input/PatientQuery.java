package web.entity.input;

import org.hibernate.Query;
import org.hibernate.Session;

public class PatientQuery implements Searchable {
	private String q;
	private FilterStatus status = FilterStatus.ONLY_ACTIVE;
	private int page;
	private int size;
	
	public PatientQuery(String q, FilterStatus status) {
		setQueryText(q);
		setStatus(status);
	}
	
	public PatientQuery(String q) {
		this(q, FilterStatus.ONLY_ACTIVE);
	}
	
	public PatientQuery paginate(int page, int size) {
		this.page = page;
		this.size = size;
		return this;
	}
	
	public PatientQuery paginate(String page, String size) {
		try {
			this.page = Integer.parseInt(page);
			this.size = Integer.parseInt(size);
		} catch(NumberFormatException expected) {
			
		}
		return this;
	}
	
	@Override
	public Query toQuery(Session session) {
		StringBuilder hql = new StringBuilder("SELECT p FROM Patient p ");
		if(getStatus() != FilterStatus.BOTH) {
			hql.append("WHERE p.active = :status");
		}
		Query query = session.createQuery(hql.toString());
		//query.setParameter("id", "%" + getQueryText() + "%");
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
	
}
