package dao;

import org.hibernate.Session;

public interface IDataManager {
	
	Session open();
	
	void close();

}