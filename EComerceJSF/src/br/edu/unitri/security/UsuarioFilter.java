/**
 * 
 */
package br.edu.unitri.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.edu.unitri.model.Usuario;

/**
 * @author Marcos
 *
 */
public class UsuarioFilter implements Filter {

	@Override
	public void destroy() {
		//do not implement
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain arg2) throws IOException, ServletException {
		
		Usuario usuario = null;
		HttpSession sessao = ((HttpServletRequest)request).getSession(false);
		
		if (sessao != null) {
			usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		}
		
		if (usuario == null) {
			String contexto = ((HttpServletRequest)request).getContextPath();
			((HttpServletResponse) response).sendRedirect(contexto + "/index.xhtml");
		} else {
			arg2.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		//do not implement
	}

}
