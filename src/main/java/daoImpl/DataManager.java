package daoImpl;

import java.util.function.Consumer;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class DataManager {
	private SessionFactory sessionFactory;
	private Session session;
	
	public static class ContainerFor<T> {
		T object;
		public ContainerFor(T object) {
			this.object = object;
		}
	}
	
	public static void run(Consumer<Session> function, Consumer<Exception> onError) {
		try {
			DataManager dm = new DataManager();
			Session session = dm.open();
			function.accept(session);
			session.close();
		} catch(Exception e) {
			onError.accept(e);
		}
	}
	
	
	public static void run(Consumer<Session> function) {
		DataManager dm = new DataManager();
		Session session = dm.open();
		function.accept(session);
		session.close();
	}
	
	public static void transact(Consumer<Session> function, Consumer<Exception> onError) {
		DataManager dm = new DataManager();
		Session session = dm.open();
		Transaction transaction = session.beginTransaction();
		try {
			function.accept(session);
			transaction.commit();
		} catch(Exception e) {
			/*if(transaction != null) {
				transaction.rollback();
			}*/
			onError.accept(e);
		} finally {
			if(session != null && session.isOpen()) 
				session.close();
		}
	}
	
	public static void transact(Consumer<Session> function) {
		DataManager.transact(function, exception -> {
			exception.printStackTrace();
		});
	}
	
	public DataManager() {
		Configuration configuration = new Configuration();
		configuration.configure();
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	}
	
	public Session open() {
		session = sessionFactory.openSession();
		return session;
	}
	
	public void close() {
		session.close();
		sessionFactory.close();
	}
	
	
}
