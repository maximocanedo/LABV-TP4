package web.daoImpl;

import java.util.function.Consumer;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import web.dao.IDataManager;

public class DataManager implements IDataManager {
    
    private static SessionFactory sessionFactory;
    
    static {
        initializeSessionFactory();
    }

    private static void initializeSessionFactory() {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();
            ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run(Consumer<Session> function, Consumer<Exception> onError) {
        Session session = null;
        try {
            session = getSessionWithRetries();
            function.accept(session);
        } catch (org.hibernate.exception.JDBCConnectionException expected) {
        	System.out.println("Se perdió la sesión. Reintentando... ");
        	DataManager.initializeSessionFactory();
        	session = getSessionWithRetries();
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
            session = getSessionWithRetries();
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

    private Session getSessionWithRetries() {
        int retries = 3;
        while (retries > 0) {
            try {
                return getSession();
            } catch (Exception e) {
                if (retries <= 1) {
                    throw new IllegalStateException("Could not obtain session after retries.", e);
                }
                retries--;
                // Wait a bit before retrying
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
                // Reinitialize sessionFactory if needed
                if (sessionFactory == null || sessionFactory.isClosed()) {
                    initializeSessionFactory();
                }
            }
        }
        throw new IllegalStateException("Failed to obtain a session after retries.");
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
