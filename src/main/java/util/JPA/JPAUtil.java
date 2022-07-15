package util.JPA;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
	private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory("library");
	
	public static EntityManager getEntityManeger() {
		return factory.createEntityManager();
	}
}
