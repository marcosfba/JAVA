/**
 * 
 */
package br.edu.unitri.controler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import br.edu.unitri.DTO.Consultas.DepartamentoLocalDTO;
import br.edu.unitri.DTO.Consultas.GerenteEmpregadoDTO;
import br.edu.unitri.DTO.Consultas.ProjetoEmpregadoDTO;
import br.edu.unitri.util.JpaUtil;

/**
 * @author marcos.fernando
 *
 */
public class ConsultasControler {
	
	private EntityManager manager = JpaUtil.getManager();
	
	public List<DepartamentoLocalDTO> findDepartamentoLocal() throws SQLException {
		return findDepartamentoLocal("",null);
	}

	public List<ProjetoEmpregadoDTO> findEmpregadoProjeto() throws SQLException {
		return findEmpregadoProjeto("",null);
	}
	
	public List<GerenteEmpregadoDTO> findGerenteEmpregado() throws SQLException {
		return findGerenteEmpregado("", null);
	}


	@SuppressWarnings("unchecked")
	public List<DepartamentoLocalDTO> findDepartamentoLocal(String qry, String parametros) throws SQLException {
		String sql = "";
		if (qry.isEmpty()) {
			sql = "select d.numdepartamento || '-' || d.desclocal as nomedepartamento,"
					+ " l.nomlocal ||'-'|| l.descLocal as nomeLocal"
					+ " from local_dept ld join tbDepartamento d on d.idDepartamento = ld.departamento_id"
					+ " join tbLocal l on l.idLocal = ld.local_id";
			
		} else {
			if (!parametros.isEmpty()) {
				qry = qry.concat(parametros);
			}
			sql = qry;
		}
		
		List<DepartamentoLocalDTO> listaDepartamentoLocalDTO = new ArrayList<DepartamentoLocalDTO>();
		List<Object[]> lista =  manager.createNativeQuery(sql).getResultList();
		for (Object[] objects : lista) {	
			DepartamentoLocalDTO depLoc = new DepartamentoLocalDTO(objects[0].toString(),objects[1].toString());
			listaDepartamentoLocalDTO.add(depLoc);
		}
		return listaDepartamentoLocalDTO;
	}


	@SuppressWarnings("unchecked")
	public List<ProjetoEmpregadoDTO> findEmpregadoProjeto(String qry, String parametros) throws SQLException {
		String sql = "";
		if (qry.isEmpty()) {
			sql = "select e.nomeempregado as nomeEmpregado, (d2.numDepartamento ||'-'|| d2.descLocal) as nomeDepartamentoEmpregado,"
					+ " p.numProjeto as numProjeto, p.descProjeto as descProjeto, (l.nomLocal || '-'|| l.descLocal) as nomeLocal,"
					+ " (d.numDepartamento || '-' || d.descLocal) as nomeDepartamentoProjeto"
					+ " from Projeto_Emp pe"
					+ " join tbProjeto p on p.idProjeto = pe.projeto_id"
					+ " join tbDepartamento d on d.idDepartamento = p.departamento_id"
					+ " join tbLocal l on l.idLocal = p.local_id"
					+ " join tbEmpregado e on e.codEmpregado = pe.empregado_id"
					+ " join tbDepartamento d2 on d2.idDepartamento = e.departamento_id";
		} else {
			if (!parametros.isEmpty()) {
				qry = qry.concat(parametros);
			}
			sql = qry;
		}
		
		List<ProjetoEmpregadoDTO> listaProjetoEmpregadoDTO = new ArrayList<ProjetoEmpregadoDTO>();
		List<Object[]> lista =  manager.createNativeQuery(sql).getResultList();
		for (Object[] objects : lista) {	
			ProjetoEmpregadoDTO projEmp = new ProjetoEmpregadoDTO(objects[0].toString(),objects[1].toString(),
					objects[2].toString(), objects[3].toString(), objects[4].toString(),objects[5].toString());
			listaProjetoEmpregadoDTO.add(projEmp);
		}
		return listaProjetoEmpregadoDTO;
	}
	
	@SuppressWarnings("unchecked")
	public List<GerenteEmpregadoDTO> findGerenteEmpregado(String qry, String parametros) throws SQLException {
		String sql = "";
		if (qry.isEmpty()) {
			sql = "select e.nomeempregado as nomeEmpregado, e2.nomeempregado as nomeGerente"
					+ " from tbempregado e join tbempregado e2 on e2.codEmpregado = e.gerente_id"
					+ " join tbGerencia g on g.gerente_id = e2.codEmpregado";
		} else {
			if (!parametros.isEmpty()) {
				qry = qry.concat(parametros);
			}
			sql = qry;
		}
		List<GerenteEmpregadoDTO> listaGerenteEmpregado = new ArrayList<GerenteEmpregadoDTO>();
		List<Object[]> lista =  manager.createNativeQuery(sql).getResultList();
		for (Object[] objects : lista) {	
			GerenteEmpregadoDTO gerenteEmpregado = new GerenteEmpregadoDTO(objects[0].toString(),objects[1].toString());
			listaGerenteEmpregado.add(gerenteEmpregado);
		}
		return listaGerenteEmpregado;
	}

}
