package vn.iotstar.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;

@PersistenceContext
public class JpaConfig {

	private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory("jpa-hibernate-mysql");

	public static EntityManager getEntityManager() {
		return factory.createEntityManager();
	}
}
