package utils;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/***
 * Clase que inicializa una SessionFactory
 * @author Francesco De Amicis
 *
 */
public class HibernateUtil {

	private static SessionFactory sessionFactory = buildSessionFactory();

	private static SessionFactory buildSessionFactory() {
		try {
			Configuration cfg = new Configuration().configure();

			SessionFactory sessionFactory = cfg
					.buildSessionFactory(new StandardServiceRegistryBuilder().configure().build());

			return sessionFactory;
		} catch (Throwable e) {
			// Make sure you log the exception, as it might be swallowed
			Log.i("Initial SessionFactory creation failed." + e);
			throw new ExceptionInInitializerError(e);
			
		}

	}
	
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
