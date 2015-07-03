package br.edu.unitri.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public final class JpaUtil {

	private static EntityManagerFactory emf = null;

	public static EntityManager getManager() {
		if (emf == null) {
			emf = Persistence.createEntityManagerFactory("JpaJSF");
		}
		EntityManager manager = emf.createEntityManager();
		return manager;
	}

	public static void closeManager(EntityManager manager) {
		try {
			manager.close();
		} catch (Exception ex) {
			System.err.println("Erro: " + ex.getMessage());
		}
	}

}
