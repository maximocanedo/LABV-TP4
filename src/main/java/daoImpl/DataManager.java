package daoImpl;

import java.util.function.Consumer;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import dao.IDataManager;

public class DataManager implements IDataManager {
	
	private static SessionFactory sessionFactory;
	
	static {
		try {
			Configuration configuration = new Configuration();
			configuration.configure();
			ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);
			
		} catch(Exception e) {
			
		}
	}

    public void run(Consumer<Session> function, Consumer<Exception> onError) {
        Session session = null;
        try {
            session = getSession();
            function.accept(session);
        } catch (Exception e) {
            onError.accept(e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public void run(Consumer<Session> function) {
        run(function, Exception::printStackTrace);
    }

    public void transact(Consumer<Session> function, Consumer<Exception> onError) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = getSession();
            transaction = session.beginTransaction();
            function.accept(session);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            onError.accept(e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public void transact(Consumer<Session> function) {
        transact(function, Exception::printStackTrace);
    }

    private Session getSession() {
        if (sessionFactory == null) {
            throw new IllegalStateException("SessionFactory is not initialized.");
        }
        return sessionFactory.openSession();
    }

    public void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
	
}