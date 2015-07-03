/**
 * 
 */
package br.edu.unitri.listener;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import br.edu.unitri.util.JpaUtilSession;

/**
 * @author marcos.fernando
 *
 */
public class FaseListener implements PhaseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void afterPhase(PhaseEvent arg0) {
		try {
			if (JpaUtilSession.currentEM().getTransaction().isActive()) {
				JpaUtilSession.currentEM().getTransaction().commit();
				System.out.println("Fechou transação");
			}
		} catch (Throwable ex) {
			try {
				if (JpaUtilSession.currentEM().getTransaction().isActive()) {
					JpaUtilSession.currentEM().getTransaction().rollback();
					System.out.println("Roolback transação");
				}
			} catch (Throwable t) {
				throw new RuntimeException(t);
			}
			throw new RuntimeException(ex);
		}
	}

	@Override
	public void beforePhase(PhaseEvent arg0) {
		try {
			if (JpaUtilSession.currentEM().getTransaction().isActive()) {
				JpaUtilSession.currentEM().getTransaction().rollback();
			}
			JpaUtilSession.currentEM().getTransaction().begin();
			System.out.println("Iniciou transação");
		} catch (Throwable ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.INVOKE_APPLICATION;
	}

}
