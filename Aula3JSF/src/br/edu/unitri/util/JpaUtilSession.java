package br.edu.unitri.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public final class JpaUtilSession {

	private static EntityManagerFactory emf = null;

	private static EntityManagerFactory getEMF() {
		if (emf == null) {
			emf = Persistence.createEntityManagerFactory("JpaJSF");
		}
		return emf;
	}
	
	private static EntityManager getEM() { 
		return getEMF().createEntityManager() ;
	}
	
	public static final ThreadLocal<EntityManager> session = new ThreadLocal<EntityManager>();
	
	public static EntityManager currentEM() {
		EntityManager s = (EntityManager) session.get();
		if (s == null) {
			s = getEM();
			session.set(s);
		}
		return s;
	}
	
	public static void closeEM() {
		EntityManager s = (EntityManager) session.get();
		if (s != null) {
			s.close();
		}
		session.set(null);
	}

}
