/**
 * 
 */
package br.edu.unitri.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import br.edu.unitri.util.JpaUtilSession;

/**
 * @author marcos.fernando
 *
 */
public class JpaFilter implements Filter {
	
	public JpaFilter() {
		super();
	}

	@Override
	public void destroy() {
		JpaUtilSession.closeEM();
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
			throws IOException, ServletException {
		try {
			JpaUtilSession.currentEM().getTransaction().begin();
			chain.doFilter(request, response);
			JpaUtilSession.currentEM().getTransaction().commit();
		} catch (Throwable ex) {
			try {
				if (JpaUtilSession.currentEM().getTransaction().isActive()) {
					JpaUtilSession.currentEM().getTransaction().rollback();
				}
			} catch (Throwable t) {
				throw new ServletException(t);
			}
			throw new ServletException(ex);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		JpaUtilSession.currentEM().clear();
	}

}
