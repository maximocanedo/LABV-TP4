package web.dao;

import java.util.function.Consumer;

import org.hibernate.Session;

public interface IDataManager {
	
	void run(Consumer<Session> function, Consumer<Exception> onError);
	
	void run(Consumer<Session> function);
	
	void transact(Consumer<Session> function, Consumer<Exception> onError);
	
	void transact(Consumer<Session> function);
	
	void shutdown();
	
}