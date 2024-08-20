package rg.jwt.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

	private static SessionFactory sessionFactory;

	private static SessionFactory buildSessionFactory() {
		
		try {
			Configuration configuration = new Configuration();
			configuration.configure("hibernate.cfg.xml");
			
			return configuration.buildSessionFactory();
			
		} catch (Throwable ex) {
			ex.printStackTrace();
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		
		if (sessionFactory == null) {
			sessionFactory = buildSessionFactory();
		}
		
		return sessionFactory;
	}
}