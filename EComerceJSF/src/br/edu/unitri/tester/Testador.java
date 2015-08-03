/**
 * 
 */
package br.edu.unitri.tester;

import java.sql.SQLException;

import br.edu.unitri.controler.CategoriaControler;
import br.edu.unitri.controler.MarcaControler;
import br.edu.unitri.controler.UsuarioControler;
import br.edu.unitri.enumerators.TipoUsuario;
import br.edu.unitri.model.Categoria;
import br.edu.unitri.model.Marca;
import br.edu.unitri.model.Usuario;
import br.edu.unitri.util.JpaUtil;

/**
 * @author marcos.fernando
 *
 */
public class Testador {
	
	

	/**
	 * @param args
	 */
	

	public static void main(String[] args) {
		
		System.out.println("Verificando criação da base de dados");
		JpaUtil.getManager().clear();
		System.out.println("Finalização da criação da base de dados");
		
		System.out.println("Populando base de dados");
		System.out.println("Cadastro administrador do sistema");
		
		UsuarioControler usuarioControler = new UsuarioControler();
		Usuario usuario = new Usuario("marcos.fernando","marcos123","marcosfba.algar@gmail.com",TipoUsuario.ADMINISTRADOR);
		try {
			usuarioControler.save(usuario);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println("Cadastrando Categorias");
		CategoriaControler categoriaControler = new CategoriaControler();
		Categoria categoria  = new Categoria(null,"ELETRONICOS","ELETRO-PORTATEIS");
		Categoria categoria2 = new Categoria(null,"CELULARES","SMARTPHONE");
		Categoria categoria3 = new Categoria(null,"INFORMATICA","PERIFÉRICOS-COMPUTADORES");
		Categoria categoria4 = new Categoria(null,"ELETRODOMÉSTICOS","LAVADORAS-REFRIGERADORES");
		
		try {
			categoriaControler.save(categoria);
			categoriaControler.save(categoria2);
			categoriaControler.save(categoria3);
			categoriaControler.save(categoria4);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Cadastrando Marcas");
		MarcaControler marcaControler = new MarcaControler();
		Marca marca  = new Marca(null,"ELG","ELG ELETRONICS");
		Marca marca2 = new Marca(null,"PHILIPS","PHILIPS");
		Marca marca3 = new Marca(null,"SAMSUMG","SAUSUMG");
		Marca marca4 = new Marca(null,"SONY","SONY");
		
		try {
			marcaControler.save(marca);
			marcaControler.save(marca2);
			marcaControler.save(marca3);
			marcaControler.save(marca4);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Limpando objetos do ambiente de persitência");
		JpaUtil.getManager().clear();
		
		System.out.println("Operação realizada com sucesso");
		System.exit(0);
		
	}

}
