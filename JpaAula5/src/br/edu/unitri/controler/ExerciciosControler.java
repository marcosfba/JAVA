/**
 * 
 */
package br.edu.unitri.controler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import br.edu.unitri.DTO.Consultas.ConsultaLetraA;
import br.edu.unitri.DTO.Consultas.ConsultaLetraB;
import br.edu.unitri.DTO.Consultas.ConsultaLetraC;
import br.edu.unitri.DTO.Consultas.ConsultaLetraD;
import br.edu.unitri.DTO.Consultas.ConsultaLetraE;
import br.edu.unitri.DTO.Consultas.ConsultaLetraF;
import br.edu.unitri.DTO.Consultas.ConsultaLetraG;
import br.edu.unitri.DTO.Consultas.ConsultaLetraH;
import br.edu.unitri.DTO.Consultas.ConsultaLetraI;
import br.edu.unitri.DTO.Consultas.ConsultaLetraK;
import br.edu.unitri.DTO.Consultas.ConsultaLetraL;
import br.edu.unitri.DTO.Consultas.ConsultaLetraM;
import br.edu.unitri.DTO.Consultas.ConsultaLetraN;
import br.edu.unitri.DTO.Consultas.ConsultaLetraO;
import br.edu.unitri.DTO.Consultas.ConsultaLetraP;
import br.edu.unitri.DTO.Consultas.ConsultaLetraQ;
import br.edu.unitri.DTO.Consultas.ConsultaLetraR;
import br.edu.unitri.DTO.Consultas.ConsultaLetraY;
import br.edu.unitri.DTO.Consultas.ConsultaLetraZ;
import br.edu.unitri.DTO.Consultas.LetraBDep;
import br.edu.unitri.DTO.Consultas.LetraBProj;
import br.edu.unitri.util.JpaUtil;

/**
 * @author marcos.fernando
 *
 */
@SuppressWarnings("unchecked")
public class ExerciciosControler {

	private EntityManager manager = JpaUtil.getManager();
	
	/*
	 * a) Quais os nomes dos departamentos existentes na empresa?
	 */
	public List<ConsultaLetraA> findLetraA() throws SQLException {
		return findLetraA("",null);
	}
	
	public List<ConsultaLetraA> findLetraA(String qry, String parametros) throws SQLException {
		String sql = "";
		if (qry.isEmpty()) {
			sql = "select numDepartamento, descLocal from tbDepartamento";
			
		} else {
			if (!parametros.isEmpty()) {
				qry = qry.concat(parametros);
			}
			sql = qry;
		}		 
		List<ConsultaLetraA> listaA = new ArrayList<ConsultaLetraA>();
		List<Object[]> lista =  manager.createNativeQuery(sql).getResultList();
		for (Object[] objects : lista) {	
			ConsultaLetraA letraA = new ConsultaLetraA(objects[0].toString(), objects[1].toString());
			listaA.add(letraA);
		}
		return listaA;
	}	
	
	/*
	 * b) Quais são as informações de todos os empregados da empresa? 
	 */
	public List<ConsultaLetraB> findLetraB() throws SQLException {
		return findLetraB("",null);
	}
	
	public List<ConsultaLetraB> findLetraB(String qry, String parametros) throws SQLException {

		String qryDependentes =" select d.nome, d.dtnascimento, d.idEmpregado,"
				+ " case when d.sexo = 0 then 'Masculino' when d.sexo = 1 then 'Feminino' when d.sexo = 2 then 'Indefinido' end as Sexo,"
				+ " case when d.tipodependente = 0 then 'Mãe' when d.tipodependente = 1 then 'Pai' when d.tipodependente = 2 then 'Filho'"
				+ "      when d.tipodependente = 3 then 'Filha' when d.tipodependente = 4 then 'Neto' when d.tipodependente = 5 then 'Neta'"
				+ "      when d.tipodependente = 6 then 'Irmão' when d.tipodependente = 7 then 'Irmã' when d.tipodependente = 8 then 'Conjuge'"
				+ "      when d.tipodependente = 9 then 'Avos' end as TipoDependente"
				+ " from tbDependente d where d.idEmpregado = ? ";
		
		String qryProjetos   ="select p.numprojeto, p.descprojeto, sum(pe.quanthoras) , pe.empregado_id"
				+ " from Projeto_Emp pe join tbProjeto p on p.idProjeto = pe.projeto_id"
				+ " where pe.empregado_id = ?"
				+ " group by p.numprojeto, p.descprojeto, pe.empregado_id";
		
		String qryEmpregados ="select e.codempregado, e.dtnasc, e.endEmpregado, e.nomeempregado, "
				+ " case when e.sexo = 0 then 'Masculino' when e.sexo = 1 then 'Feminino' when e.sexo = 2 then 'Indefinido' end as Sexo,"
				+ " (d.numdepartamento || '--'|| d.desclocal) as nomedepartamento,"
				+ " e2.nomeempregado as nomeGerente from tbempregado e"
				+ " join tbDepartamento d on d.idDepartamento = e.departamento_id"
				+ " join tbempregado e2 on e2.codEmpregado = e.gerente_id"
				+ " join tbGerencia g on g.gerente_id = e2.codEmpregado";
		
		String sql = "";
		if (qry.isEmpty()) {
			sql = qryEmpregados;
			
		} else {
			if (!parametros.isEmpty()) {
				qry = qry.concat(parametros);
			}
			sql = qry;
		}		 
		
		List<ConsultaLetraB> listaB = new ArrayList<ConsultaLetraB>();
		List<Object[]> lista =  manager.createNativeQuery(sql).getResultList();
		for (Object[] objects : lista) {	
			int codEmpregado = Integer.valueOf(objects[0].toString());			
			ConsultaLetraB letraB = new ConsultaLetraB(objects[0].toString(),(Date) objects[1], objects[2].toString(), 
					                                   objects[3].toString(), objects[4].toString(), objects[5].toString(), 
					                                   objects[6].toString());
			
			List<Object[]> listaProj =  manager.createNativeQuery(qryProjetos).setParameter(1,codEmpregado).getResultList();
			for (Object[] objects2 : listaProj) {
				LetraBProj proj = new LetraBProj(objects2[0].toString(),objects2[1].toString(),Integer.valueOf(objects2[2].toString()));
				letraB.getProjetos().add(proj);
			}
			
			List<Object[]> listaDepe =  manager.createNativeQuery(qryDependentes).setParameter(1,codEmpregado).getResultList();
			for (Object[] objects3 : listaDepe) {
				LetraBDep depend = new LetraBDep(objects3[0].toString(), objects3[3].toString(), objects3[4].toString(),(Date) objects3[1]);
				letraB.getDependentes().add(depend);
			}
			listaB.add(letraB);
		}
		return listaB;
	}

	/*
	 * c) Qual o nome do departamento de cada empregado?  
	 */
	public List<ConsultaLetraC> findLetraC() throws SQLException {
		return findLetraC("",null);
	}
	
	public List<ConsultaLetraC> findLetraC(String qry, String parametros) throws SQLException {
		String sql = "";
		if (qry.isEmpty()) {
			sql = "select (d.numDepartamento || '--'|| d.descLocal) as descricao, e.nomeEmpregado from tbDepartamento d"
					+ " join tbEmpregado e on e.departamento_id = d.idDepartamento";
			
		} else {
			if (!parametros.isEmpty()) {
				qry = qry.concat(parametros);
			}
			sql = qry;
		}		 
		List<ConsultaLetraC> listaC = new ArrayList<ConsultaLetraC>();
		List<Object[]> lista =  manager.createNativeQuery(sql).getResultList();
		for (Object[] objects : lista) {	
			ConsultaLetraC letraC = new ConsultaLetraC(objects[0].toString(), objects[1].toString());
			listaC.add(letraC);
		}
		return listaC;
	}

	/*
	 * d) Qual o nome dos dependentes de cada empregado?   
	 */
	public List<ConsultaLetraD> findLetraD() throws SQLException {
		return findLetraD("",null);
	}
	
	public List<ConsultaLetraD> findLetraD(String qry, String parametros) throws SQLException {

		String sql = "";
		if (qry.isEmpty()) {
			sql =" select d.nome, e.nomeempregado"
					+ " from tbDependente d join tbEmpregado e on e.codEmpregado = d.idEmpregado";
		} else {
			if (!parametros.isEmpty()) {
				qry = qry.concat(parametros);
			}
			sql = qry;
		}		 
		List<ConsultaLetraD> listaD = new ArrayList<ConsultaLetraD>();
		List<Object[]> lista =  manager.createNativeQuery(sql).getResultList();
		for (Object[] objects : lista) {	
			ConsultaLetraD letraD = new ConsultaLetraD(objects[0].toString(), objects[1].toString());
			listaD.add(letraD);
		}
		return listaD;
	}

	/*
	 * e) Qual o código dos locais de cada projeto em desenvolvimento?   
	 */
	public List<ConsultaLetraE> findLetraE() throws SQLException {
		return findLetraE("",null);
	}

	public List<ConsultaLetraE> findLetraE(String qry, String parametros) throws SQLException {
		String sql = "";
		if (qry.isEmpty()) {
			sql ="select p.numProjeto, l.nomLocal, l.descLocal from"
					+ " tbProjeto p join tbLocal l on l.idLocal = p.local_id";
		} else {
			if (!parametros.isEmpty()) {
				qry = qry.concat(parametros);
			}
			sql = qry;
		}		 
		List<ConsultaLetraE> listaE = new ArrayList<ConsultaLetraE>();
		List<Object[]> lista =  manager.createNativeQuery(sql).getResultList();
		for (Object[] objects : lista) {	
			ConsultaLetraE letraE = new ConsultaLetraE(objects[0].toString(), objects[1].toString(), objects[2].toString());
			listaE.add(letraE);
		}
		return listaE;
	}
	
	/*
	 * f) Qual o código do departamento responsável por cada projeto?    
	 */
	public List<ConsultaLetraF> findLetraF() throws SQLException {
		return findLetraF("",null);
	}

	public List<ConsultaLetraF> findLetraF(String qry, String parametros) throws SQLException {
		String sql = "";
		if (qry.isEmpty()) {
			sql ="select d.numDepartamento, d.descLocal, p.numProjeto"
					+ " from tbProjeto p join tbDepartamento d on d.idDepartamento = p.departamento_id";
		} else {
			if (!parametros.isEmpty()) {
				qry = qry.concat(parametros);
			}
			sql = qry;
		}		 
		List<ConsultaLetraF> listaF = new ArrayList<ConsultaLetraF>();
		List<Object[]> lista =  manager.createNativeQuery(sql).getResultList();
		for (Object[] objects : lista) {	
			ConsultaLetraF letraF = new ConsultaLetraF(objects[0].toString(), objects[1].toString(), objects[2].toString());
			listaF.add(letraF);
		}
		return listaF;
	}
	/*
	 * g) Qual o nome do departamento responsável por cada projeto?     
	 */
	public List<ConsultaLetraG> findLetraG() throws SQLException {
		return findLetraG("",null);
	}
	
	public List<ConsultaLetraG> findLetraG(String qry, String parametros) throws SQLException {
		String sql = "";
		if (qry.isEmpty()) {
			sql =" select d.descLocal, p.numProjeto from tbProjeto p join tbDepartamento d on d.idDepartamento = p.departamento_id";
		} else {
			if (!parametros.isEmpty()) {
				qry = qry.concat(parametros);
			}
			sql = qry;
		}		 
		List<ConsultaLetraG> listaG = new ArrayList<ConsultaLetraG>();
		List<Object[]> lista =  manager.createNativeQuery(sql).getResultList();
		for (Object[] objects : lista) {	
			ConsultaLetraG letraG = new ConsultaLetraG(objects[0].toString(), objects[1].toString());
			listaG.add(letraG);
		}
		return listaG;
	}
	/*
	 * h) Qual a data que cada empregado começou a gerenciar o departamento? Apresente o nome do empregado, o nome do departamento e a data    
	 */
	public List<ConsultaLetraH> findLetraH() throws SQLException {
		return findLetraH("",null);
	}
	
	public List<ConsultaLetraH> findLetraH(String qry, String parametros) throws SQLException {
		String sql = "";
		if (qry.isEmpty()) {
			sql = "select e.nomeempregado, d.desclocal, g.dtemp"
					+ " from tbGerencia g join tbEmpregado e on e.codEmpregado = g.gerente_id"
					+ " join tbDepartamento d on d.idDepartamento = g.departamento_id";
		} else {
			if (!parametros.isEmpty()) {
				qry = qry.concat(parametros);
			}
			sql = qry;
		}		 
		List<ConsultaLetraH> listaH = new ArrayList<ConsultaLetraH>();
		List<Object[]> lista =  manager.createNativeQuery(sql).getResultList();
		for (Object[] objects : lista) {	
			ConsultaLetraH letraH = new ConsultaLetraH(objects[0].toString(), objects[1].toString(), (Date) objects[2]);
			listaH.add(letraH);
		}
		return listaH;
	}
	/*
	 * i) Quais os códigos dos empregados que trabalham no projeto de código 30?     
	 */
	public List<ConsultaLetraI> findLetraI() throws SQLException {
		return findLetraI("",null);
	}
	
	public List<ConsultaLetraI> findLetraI(String qry, String parametros) throws SQLException {
		String sql = "";
		if (qry.isEmpty()) {
			sql ="select e.codempregado, e.nomeEmpregado, p.numProjeto"
					+ " from tbEmpregado e join Projeto_Emp pe on pe.empregado_id = e.codEmpregado"
					+ " join tbProjeto p on p.idProjeto = pe.projeto_id"
					+ " where p.numProjeto = '30'";
		} else {
			if (!parametros.isEmpty()) {
				qry = qry.concat(parametros);
			}
			sql = qry;
		}		 
		List<ConsultaLetraI> listaI = new ArrayList<ConsultaLetraI>();
		List<Object[]> lista =  manager.createNativeQuery(sql).getResultList();
		for (Object[] objects : lista) {	
			ConsultaLetraI letraI = new ConsultaLetraI(objects[0].toString(), objects[1].toString(), objects[2].toString());
			listaI.add(letraI);
		}
		return listaI;
	}
	/*
	 * j) Quais os nomes dos empregados que trabalham no projeto de código 20?    
	 */
	public List<ConsultaLetraI> findLetraJ() throws SQLException {
		return findLetraJ("",null);
	}
	
	public List<ConsultaLetraI> findLetraJ(String qry, String parametros) throws SQLException {
		String sql = "";
		if (qry.isEmpty()) {
			sql ="select e.codempregado, e.nomeEmpregado, p.numProjeto"
					+ " from tbEmpregado e join Projeto_Emp pe on pe.empregado_id = e.codEmpregado"
					+ " join tbProjeto p on p.idProjeto = pe.projeto_id"
					+ " where p.numProjeto = '20'";
		} else {
			if (!parametros.isEmpty()) {
				qry = qry.concat(parametros);
			}
			sql = qry;
		}		 
		List<ConsultaLetraI> listaJ = new ArrayList<ConsultaLetraI>();
		List<Object[]> lista =  manager.createNativeQuery(sql).getResultList();
		for (Object[] objects : lista) {	
			ConsultaLetraI letraI = new ConsultaLetraI(objects[0].toString(), objects[1].toString(), objects[2].toString());
			listaJ.add(letraI);
		}
		return listaJ;
	}
	/*
	 * k) Qual o nome do departamento do empregado de código 7?     
	 */
	public List<ConsultaLetraK> findLetraK() throws SQLException {
		return findLetraK("",null);
	}
	
	public List<ConsultaLetraK> findLetraK(String qry, String parametros) throws SQLException {
		String sql = "";
		if (qry.isEmpty()) {
			sql = " select d.numDepartamento, d.descLocal, e.nomeEmpregado from"
					+ " tbDepartamento d join tbEmpregado e on e.departamento_id = d.idDepartamento"
					+ " where e.codEmpregado ='7'";
		} else {
			if (!parametros.isEmpty()) {
				qry = qry.concat(parametros);
			}
			sql = qry;
		}		 
		List<ConsultaLetraK> listaK = new ArrayList<ConsultaLetraK>();
		List<Object[]> lista =  manager.createNativeQuery(sql).getResultList();
		for (Object[] objects : lista) {	
			ConsultaLetraK letraK = new ConsultaLetraK(objects[0].toString(), objects[1].toString(), objects[2].toString());
			listaK.add(letraK);
		}
		return listaK;
	}
	/*
	 * l) Qual o nome do departamento que controla o projeto onde o empregado de código 4 trabalha?     
	 */
	public List<ConsultaLetraL> findLetraL() throws SQLException {
		return findLetraL("",null);
	}
	
	public List<ConsultaLetraL> findLetraL(String qry, String parametros) throws SQLException {
		String sql = "";
		if (qry.isEmpty()) {
			sql = "select distinct d.numDepartamento, d.descLocal from tbDepartamento d"
					+ " join tbProjeto p on p.departamento_id = d.idDepartamento"
					+ " join Projeto_Emp pe on pe.projeto_id = p.idProjeto"
					+ " where pe.empregado_id = '4'";
		} else {
			if (!parametros.isEmpty()) {
				qry = qry.concat(parametros);
			}
			sql = qry;
		}		 
		List<ConsultaLetraL> listaL = new ArrayList<ConsultaLetraL>();
		List<Object[]> lista =  manager.createNativeQuery(sql).getResultList();
		for (Object[] objects : lista) {	
			ConsultaLetraL letraL = new ConsultaLetraL(objects[0].toString(), objects[1].toString());
			listaL.add(letraL);
		}
		return listaL;
	}
	/*
	 * m) Qual o nome dos empregados que trabalham em algum projeto ?     
	 */
	public List<ConsultaLetraM> findLetraM() throws SQLException {
		return findLetraM("",null);
	}

	public List<ConsultaLetraM> findLetraM(String qry, String parametros) throws SQLException {
		String sql = "";
		if (qry.isEmpty()) {
			sql = "select distinct e.nomeempregado, d.numdepartamento, d.desclocal, p.descprojeto"
					+ " from tbEmpregado e join tbDepartamento d on d.idDepartamento = e.departamento_id"
					+ " join Projeto_Emp pe on pe.empregado_id = e.codEmpregado"
					+ " join tbProjeto p on p.idProjeto = pe.projeto_id";
		} else {
			if (!parametros.isEmpty()) {
				qry = qry.concat(parametros);
			}
			sql = qry;
		}		 
		List<ConsultaLetraM> listaM = new ArrayList<ConsultaLetraM>();
		List<Object[]> lista =  manager.createNativeQuery(sql).getResultList();
		for (Object[] objects : lista) {	
			ConsultaLetraM letraM = new ConsultaLetraM(objects[0].toString(), objects[1].toString(),objects[2].toString(),objects[3].toString());
			listaM.add(letraM);
		}
		return listaM;
	}
	/*
	 * n) Qual o nome dos empregados que trabalham em algum projeto e que supervisionam outros empregados?     
	 */
	public List<ConsultaLetraN> findLetraN() throws SQLException {
		return findLetraN("",null);
	}
	
	public List<ConsultaLetraN> findLetraN(String qry, String parametros) throws SQLException {
		String sql = "";
		if (qry.isEmpty()) {
			sql = "select distinct e.nomeempregado, d.numdepartamento, d.desclocal, p.descprojeto,"
					+ " (select e1.nomeempregado from tbEmpregado e1 where e1.codEmpregado = e.gerente_id) as nomeGerente"
					+ " from tbEmpregado e join tbDepartamento d on d.idDepartamento = e.departamento_id"
					+ " join Projeto_Emp pe on pe.empregado_id = e.codEmpregado"
					+ " join tbProjeto p on p.idProjeto = pe.projeto_id"
					+ " join tbGerencia g on g.gerente_id = e.gerente_id";
		} else {
			if (!parametros.isEmpty()) {
				qry = qry.concat(parametros);
			}
			sql = qry;
		}		 
		List<ConsultaLetraN> listaN = new ArrayList<ConsultaLetraN>();
		List<Object[]> lista =  manager.createNativeQuery(sql).getResultList();
		for (Object[] objects : lista) {	
			ConsultaLetraN letraN = new ConsultaLetraN(objects[0].toString(), objects[1].toString(),
					objects[2].toString(),objects[3].toString(),objects[4].toString());
			listaN.add(letraN);
		}
		return listaN;
	}
	/*
	 * o) Qual o nome dos empregados que não estão trabalhando em projetos?    
	 */
	public List<ConsultaLetraO> findLetraO() throws SQLException {
		return findLetraO("",null);
	}
	
	public List<ConsultaLetraO> findLetraO(String qry, String parametros) throws SQLException {
		String sql = "";
		if (qry.isEmpty()) {
			sql = "select distinct e.nomeempregado, d.numdepartamento, d.desclocal"
					+ " from tbEmpregado e join tbDepartamento d on d.idDepartamento = e.departamento_id"
					+ " where e.codEmpregado not in (select empregado_id from Projeto_Emp)";
		} else {
			if (!parametros.isEmpty()) {
				qry = qry.concat(parametros);
			}
			sql = qry;
		}		 
		List<ConsultaLetraO> listaO = new ArrayList<ConsultaLetraO>();
		List<Object[]> lista =  manager.createNativeQuery(sql).getResultList();
		for (Object[] objects : lista) {	
			ConsultaLetraO letraO = new ConsultaLetraO(objects[0].toString(), objects[1].toString(),
					objects[2].toString());
			listaO.add(letraO);
		}
		return listaO;
	}
	/*
	 * p) Qual o nome dos empregados que não estão trabalhando em projetos, mas que gerenciam algum departamento?    
	 */
	public List<ConsultaLetraP> findLetraP() throws SQLException {
		return findLetraP("",null);
	}
	
	public List<ConsultaLetraP> findLetraP(String qry, String parametros) throws SQLException {
		String sql = "";
		if (qry.isEmpty()) {
			sql =" select distinct e.nomeEmpregado, d.numdepartamento, d.desclocal"
			   + " from"
			   + " tbGerencia g join tbDepartamento d on g.departamento_id = d.idDepartamento"
			   + " join tbEmpregado e on g.gerente_id = e.codEmpregado"
			   + " where"
			   + " not exists( select pe.empregado_id from Projeto_Emp pe where pe.empregado_id = g.gerente_id)";
			
		} else {
			if (!parametros.isEmpty()) {
				qry = qry.concat(parametros);
			}
			sql = qry;
		}		 
		List<ConsultaLetraP> listaP = new ArrayList<ConsultaLetraP>();
		List<Object[]> lista =  manager.createNativeQuery(sql).getResultList();
		for (Object[] objects : lista) {	
			ConsultaLetraP letraP = new ConsultaLetraP(objects[0].toString(), objects[1].toString(),
					objects[2].toString());
			listaP.add(letraP);
		}
		return listaP;
	}
	/*
	 * q) Qual a descrição da ou das localizações do departamento de código 222?     
	 */
	public List<ConsultaLetraQ> findLetraQ() throws SQLException {
		return findLetraQ("",null);
	}
	
	public List<ConsultaLetraQ> findLetraQ(String qry, String parametros) throws SQLException {
		String sql = "";
		if (qry.isEmpty()) {
			sql =" select l.nomLocal, l.descLocal, d.numDepartamento, d.descLocal as nomeDepartamento"
					+ " from tbLocal l join Local_Dept ld on ld.local_id = l.idLocal"
					+ " join tbDepartamento d on d.idDepartamento = ld.departamento_id"
					+ " where d.numDepartamento = '222'";
		} else {
			if (!parametros.isEmpty()) {
				qry = qry.concat(parametros);
			}
			sql = qry;
		}		 
		List<ConsultaLetraQ> listaQ = new ArrayList<ConsultaLetraQ>();
		List<Object[]> lista =  manager.createNativeQuery(sql).getResultList();
		for (Object[] objects : lista) {	
			ConsultaLetraQ letraQ = new ConsultaLetraQ(objects[0].toString(), objects[1].toString(),
					objects[2].toString(),objects[3].toString());
			listaQ.add(letraQ);
		}
		return listaQ;
	}
	/*
	 * r) Qual a quantidade de horas trabalhadas pelo empregado de código 2?     
	 */
	public List<ConsultaLetraR> findLetraR() throws SQLException {
		return findLetraR("",null);
	}
	
	public List<ConsultaLetraR> findLetraR(String qry, String parametros) throws SQLException {
		String sql = "";
		if (qry.isEmpty()) {
			sql =" select sum(pe.quantHoras) as qtdhoras, e.nomeEmpregado"
					+ " from Projeto_Emp pe join tbEmpregado e on e.codEmpregado = pe.empregado_id"
					+ " where pe.empregado_id = '2' group by pe.empregado_id";
		} else {
			if (!parametros.isEmpty()) {
				qry = qry.concat(parametros);
			}
			sql = qry;
		}		 
		List<ConsultaLetraR> listaR = new ArrayList<ConsultaLetraR>();
		List<Object[]> lista =  manager.createNativeQuery(sql).getResultList();
		for (Object[] objects : lista) {	
			ConsultaLetraR letraR = new ConsultaLetraR(objects[0].toString(), objects[1].toString());
			listaR.add(letraR);
		}
		return listaR;
		
	}
	/*
	 * s) Qual a quantidade de horas trabalhadas pelo empregado de código 4?     
	 */
	public List<ConsultaLetraR> findLetraS() throws SQLException {
		return findLetraS("",null);
	}
	
	public List<ConsultaLetraR> findLetraS(String qry, String parametros) throws SQLException {
		String sql = "";
		if (qry.isEmpty()) {
			sql =" select sum(pe.quantHoras) as qtdhoras, e.nomeEmpregado"
					+ " from Projeto_Emp pe join tbEmpregado e on e.codEmpregado = pe.empregado_id"
					+ " where pe.empregado_id = '4' group by pe.empregado_id";
		} else {
			if (!parametros.isEmpty()) {
				qry = qry.concat(parametros);
			}
			sql = qry;
		}		 
		List<ConsultaLetraR> listaR = new ArrayList<ConsultaLetraR>();
		List<Object[]> lista =  manager.createNativeQuery(sql).getResultList();
		for (Object[] objects : lista) {	
			ConsultaLetraR letraR = new ConsultaLetraR(objects[0].toString(), objects[1].toString());
			listaR.add(letraR);
		}
		return listaR;
	}
	/*
	 * t) Qual a quantidade de horas trabalhadas pela empregada de nome Emília?    
	 */
	public List<ConsultaLetraR> findLetraT() throws SQLException {
		return findLetraT("",null);
	}
	
	public List<ConsultaLetraR> findLetraT(String qry, String parametros) throws SQLException {
		String sql = "";
		if (qry.isEmpty()) {
			sql =" select sum(pe.quantHoras) as qtdhoras, e.nomeEmpregado"
					+ " from Projeto_Emp pe join tbEmpregado e on e.codEmpregado = pe.empregado_id"
					+ " where e.nomeEmpregado = 'EMILIA' group by pe.empregado_id";
		} else {
			if (!parametros.isEmpty()) {
				qry = qry.concat(parametros);
			}
			sql = qry;
		}		 
		List<ConsultaLetraR> listaR = new ArrayList<ConsultaLetraR>();
		List<Object[]> lista =  manager.createNativeQuery(sql).getResultList();
		for (Object[] objects : lista) {	
			ConsultaLetraR letraR = new ConsultaLetraR(objects[0].toString(), objects[1].toString());
			listaR.add(letraR);
		}
		return listaR;
	}
	/*
	 * u) Qual a média de horas trabalhadas pelos empregados desta empresa?     
	 */
	public Integer findLetraU() throws SQLException {
		String qry ="select avg(pe.quantHoras) as qtd from Projeto_Emp pe";
		return (Integer) manager.createNativeQuery(qry).getSingleResult();
	}
	/*
	 * v) Qual a quantidade de empregados existentes na empresa?     
	 */
	public Integer findLetraV() throws SQLException {
		String qry ="select e.* from tbEmpregado e";
		return manager.createNativeQuery(qry).getResultList().size();
	}
	
	/*
	 * x) Quais os  nomes dos dependentes de todas as empregadas da empresa (apenas empregadas)?    
	 */
	public List<LetraBDep> findLetraX() throws SQLException {
		return findLetraX("",null);
	}
	
	public List<LetraBDep> findLetraX(String qry, String parametros) throws SQLException {
		String sql = "";
		if (qry.isEmpty()) {
			sql =" select d.nome, d.dtnascimento, d.idEmpregado,"
					+ " case when d.sexo = 0 then 'Masculino' when d.sexo = 1 then 'Feminino' when d.sexo = 2 then 'Indefinido' end as Sexo,"
					+ " case when d.tipodependente = 0 then 'Mãe' when d.tipodependente = 1 then 'Pai' when d.tipodependente = 2 then 'Filho'"
					+ "      when d.tipodependente = 3 then 'Filha' when d.tipodependente = 4 then 'Neto' when d.tipodependente = 5 then 'Neta'"
					+ "      when d.tipodependente = 6 then 'Irmão' when d.tipodependente = 7 then 'Irmã' when d.tipodependente = 8 then 'Conjuge'"
					+ "      when d.tipodependente = 9 then 'Avos' end as TipoDependente"
					+ " from tbDependente d where d.sexo = 1";
		} else {
			if (!parametros.isEmpty()) {
				qry = qry.concat(parametros);
			}
			sql = qry;
		}		 
		List<LetraBDep> listaB = new ArrayList<LetraBDep>();
		List<Object[]> listaDepe =  manager.createNativeQuery(sql).getResultList();
		for (Object[] objects3 : listaDepe) {
			LetraBDep depend = new LetraBDep(objects3[0].toString(), objects3[3].toString(), objects3[4].toString(),(Date) objects3[1]);
			listaB.add(depend);
		}
		return listaB;
	}
	
	/*
	 * y) Qual o nome dos projetos que não são controlados por algum departamento?     
	 */
	public List<ConsultaLetraY> findLetraY() throws SQLException {
		return findLetraY("",null);
	}
	
	public List<ConsultaLetraY> findLetraY(String qry, String parametros) throws SQLException {
		String sql = "";
		if (qry.isEmpty()) {
			sql ="select p.descProjeto, p.numProjeto, l.descLocal, l.nomLocal"
					+ " from tbProjeto p join tbLocal l on l.idLocal = p.local_id"
					+ " where p.departamento_id is null";
		} else {
			if (!parametros.isEmpty()) {
				qry = qry.concat(parametros);
			}
			sql = qry;
		}		 
		List<ConsultaLetraY> listaY = new ArrayList<ConsultaLetraY>();
		List<Object[]> lista =  manager.createNativeQuery(sql).getResultList();
		for (Object[] objects : lista) {	
			ConsultaLetraY letraY = new ConsultaLetraY(objects[0].toString(), objects[1].toString(),objects[2].toString(), objects[3].toString());
			listaY.add(letraY);
		}
		return listaY;
	}
	/*
	 * z) Qual o nome dos departamentos que estão localizados em dois ou mais locais diferentes?    
	 */
	public List<ConsultaLetraZ> findLetraZ() throws SQLException {
		return findLetraZ("",null);
	}
	
	public List<ConsultaLetraZ> findLetraZ(String qry, String parametros) throws SQLException {
		String sql = "";
		if (qry.isEmpty()) {
			sql ="select d.numDepartamento, d.descLocal, count(ld.local_id)"
					+ " from tbDepartamento d"
					+ " join Local_Dept ld on ld.departamento_id = d.idDepartamento"
					+ " join tbLocal l on l.idLocal = ld.local_id"
					+ " group by d.idDepartamento having count(ld.local_id) > 1"; 
		} else {
			if (!parametros.isEmpty()) {
				qry = qry.concat(parametros);
			}
			sql = qry;
		}		 
		List<ConsultaLetraZ> listaZ = new ArrayList<ConsultaLetraZ>();
		List<Object[]> lista =  manager.createNativeQuery(sql).getResultList();
		for (Object[] objects : lista) {	
			ConsultaLetraZ letraZ = new ConsultaLetraZ(objects[0].toString(), objects[1].toString(),objects[2].toString());
			listaZ.add(letraZ);
		}
		return listaZ;
	}
}
