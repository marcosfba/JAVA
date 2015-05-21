package br.edu.unitri.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public final class JpaUtil {

	private static EntityManagerFactory emf = null;

	public static EntityManager getManager() {
		if (emf == null) {
			emf = Persistence.createEntityManagerFactory("JpaAula1");
		}
		EntityManager manager = emf.createEntityManager();
		return manager;
	}
	
/*	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map getConf(){  
		  
        Map prop = new HashMap(); 
        String rais = Path.workingDir + "\\BASE";        
        prop.put("hibernate.connection.url", "jdbc:h2:"+rais);  
  
        return prop;  
    }  
*/
	public static void closeManager(EntityManager manager) {
		try {
			manager.close();
		} catch (Exception ex) {
			System.err.println("Erro: " + ex.getMessage());
		}
	}

}
