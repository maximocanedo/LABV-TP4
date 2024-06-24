package web.entity.input;

import org.hibernate.Query;
import org.hibernate.Session;

public interface Searchable {
	
	public String getQueryText();
	
	public int getPage();
	
	public int getSize();
	
	public Query toQuery(Session session);
	
}
