/**
 * 
 */
package br.edu.unitri.controler;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

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
import br.edu.unitri.model.Departamento;
import br.edu.unitri.model.Dependente;
import br.edu.unitri.model.Empregado;
import br.edu.unitri.model.Gerencia;
import br.edu.unitri.model.Local;
import br.edu.unitri.model.ProjEmp;
import br.edu.unitri.model.Projeto;
import br.edu.unitri.model.TipoSexo;
import br.edu.unitri.util.JpaUtil;

/**
 * @author marcos.fernando
 *
 */
public class ExerciciosControler {

	private EntityManager manager = JpaUtil.getManager();
	
	/*
	 * a) Quais os nomes dos departamentos existentes na empresa?
	 */
	public List<ConsultaLetraA> findLetraA() throws SQLException {
		CriteriaBuilder cb = manager.getCriteriaBuilder();
		CriteriaQuery<ConsultaLetraA> query = cb.createQuery(ConsultaLetraA.class);
		Root<Departamento> root = query.from(Departamento.class);
		query.multiselect(root.get("numDepartamento").alias("numDepartamento"),root.get("descLocal").alias("descLocal"));
		List<ConsultaLetraA> listaA = manager.createQuery(query).getResultList();
		return listaA;
	}	
	
	/*
	 * b) Quais são as informações de todos os empregados da empresa? 
	 */
	public List<ConsultaLetraB> findLetraB() throws SQLException {
		CriteriaBuilder cb = manager.getCriteriaBuilder();
		CriteriaQuery<ConsultaLetraB> query = cb.createQuery(ConsultaLetraB.class);
		Root<Empregado> root = query.from(Empregado.class);
		query.multiselect(root.get("codEmpregado").alias("codEmpregado"), root.get("nomeEmpregado").alias("nomeEmpregado"), 
				          root.get("endEmpregado").alias("endEmpregado"), root.get("dtNasc").alias("dtnasc"),root.get("sexo").alias("sexo"));
		List<ConsultaLetraB> listaB = manager.createQuery(query).getResultList();
		return listaB;
	}

	/*
	 * c) Qual o nome do departamento de cada empregado?  
	 */
	public List<ConsultaLetraC> findLetraC() throws SQLException {
		CriteriaBuilder cb = manager.getCriteriaBuilder();
		CriteriaQuery<ConsultaLetraC> query = cb.createQuery(ConsultaLetraC.class);
		Root<Empregado> root = query.from(Empregado.class);
		Join<Empregado,Departamento> join = root.join("departamento", JoinType.LEFT);
		query.multiselect(root.get("nomeEmpregado").alias("nomeEmpregado"), join.get("descLocal").alias("nomeDepartamento"));
		List<ConsultaLetraC> listaC = manager.createQuery(query).getResultList();
		return listaC;
	}

	/*
	 * d) Qual o nome dos dependentes de cada empregado?   
	 */
	public List<ConsultaLetraD> findLetraD() throws SQLException {
		CriteriaBuilder cb = manager.getCriteriaBuilder();
		CriteriaQuery<ConsultaLetraD> query = cb.createQuery(ConsultaLetraD.class);
		Root<Dependente> root = query.from(Dependente.class);
		Join<Dependente,Empregado> join = root.join("empregado", JoinType.LEFT);
		query.multiselect(root.get("nome").alias("nomeDependente"), join.get("nomeEmpregado").alias("nomeEmpregado"));
		List<ConsultaLetraD> listaD = manager.createQuery(query).getResultList();
		return listaD;
	}

	/*
	 * e) Qual o código dos locais de cada projeto em desenvolvimento?   

	 */
	public List<ConsultaLetraE> findLetraE() throws SQLException {
		CriteriaBuilder cb = manager.getCriteriaBuilder();
		CriteriaQuery<ConsultaLetraE> query = cb.createQuery(ConsultaLetraE.class);
		Root<Projeto> root = query.from(Projeto.class);
		Join<Projeto,Local> join = root.join("local", JoinType.LEFT);
		query.multiselect(root.get("numProjeto").alias("numProjeto"), join.get("nomLocal").alias("codLocal"), 
				join.get("descLocal").alias("nomeLocal"));
		List<ConsultaLetraE> listaE = manager.createQuery(query).getResultList();
		return listaE;
	}
	
	/*
	 * f) Qual o código do departamento responsável por cada projeto?    
	 */
	public List<ConsultaLetraF> findLetraF() throws SQLException {
		CriteriaBuilder cb = manager.getCriteriaBuilder();
		CriteriaQuery<ConsultaLetraF> query = cb.createQuery(ConsultaLetraF.class);
		Root<Projeto> root = query.from(Projeto.class);
		Join<Projeto,Departamento> join = root.join("departamento", JoinType.INNER);
		query.multiselect(join.get("numDepartamento").alias("numDepartamento"),join.get("descLocal").alias("nomeDepartamento"),
				root.get("numProjeto").alias("numProjeto"));
		List<ConsultaLetraF> listaF = manager.createQuery(query).getResultList();
		return listaF;
	}
	/*
	 * g) Qual o nome do departamento responsável por cada projeto?     
	 */
	
	public List<ConsultaLetraG> findLetraG() throws SQLException {
		CriteriaBuilder cb = manager.getCriteriaBuilder();
		CriteriaQuery<ConsultaLetraG> query = cb.createQuery(ConsultaLetraG.class);
		Root<Projeto> root = query.from(Projeto.class);
		Join<Projeto,Departamento> join = root.join("departamento", JoinType.INNER);
		query.multiselect(join.get("descLocal").alias("nomeDepartamento"),root.get("numProjeto").alias("numProjeto"));
		List<ConsultaLetraG> listaG = manager.createQuery(query).getResultList();
		return listaG;
	}
	/*
	 * h) Qual a data que cada empregado começou a gerenciar o departamento? 
	 * Apresente o nome do empregado, o nome do departamento e a data    
	 */
	public List<ConsultaLetraH> findLetraH() throws SQLException {
		CriteriaBuilder cb = manager.getCriteriaBuilder();
		CriteriaQuery<ConsultaLetraH> query = cb.createQuery(ConsultaLetraH.class);
		Root<Gerencia> root = query.from(Gerencia.class);
		Join<Gerencia,Empregado> joinEmp = root.join("empregado", JoinType.LEFT);
		Join<Gerencia,Departamento> joinDep = root.join("departamento", JoinType.LEFT);
		query.multiselect(joinEmp.get("nomeEmpregado").alias("nomeEmpregado"), joinDep.get("descLocal").alias("nomeDepartamento"),
				root.get("dtEmp").alias("dtInicio"));
		List<ConsultaLetraH> listaH = manager.createQuery(query).getResultList();
		return listaH;
	}
	/*
	 * i) Quais os códigos dos empregados que trabalham no projeto de código 30?     
	 */
	public List<ConsultaLetraI> findLetraI() throws SQLException {
		CriteriaBuilder cb = manager.getCriteriaBuilder();
		CriteriaQuery<ConsultaLetraI> query = cb.createQuery(ConsultaLetraI.class);
		Root<ProjEmp> root = query.from(ProjEmp.class);
		Join<ProjEmp,Empregado> joinEmp = root.join("empregado",JoinType.LEFT);
		Join<ProjEmp,Projeto> joinPro = root.join("projeto",JoinType.LEFT);
		query.multiselect(joinEmp.get("codEmpregado").alias("codEmpregado"),joinEmp.get("nomeEmpregado").alias("nomeEmpregado"),
				joinPro.get("numProjeto").alias("numProjeto"));
		Predicate where =  cb.equal(joinPro.get("numProjeto"),"30");
		query.where(where);
		List<ConsultaLetraI> listaI = manager.createQuery(query).getResultList();
		return listaI;
	}

	/*
	 * j) Quais os nomes dos empregados que trabalham no projeto de código 20?    
	 */
	public List<ConsultaLetraI> findLetraJ() throws SQLException {
		CriteriaBuilder cb = manager.getCriteriaBuilder();
		CriteriaQuery<ConsultaLetraI> query = cb.createQuery(ConsultaLetraI.class);
		Root<ProjEmp> root = query.from(ProjEmp.class);
		Join<ProjEmp,Empregado> joinEmp = root.join("empregado",JoinType.LEFT);
		Join<ProjEmp,Projeto> joinPro = root.join("projeto",JoinType.LEFT);
		query.multiselect(joinEmp.get("codEmpregado").alias("codEmpregado"),joinEmp.get("nomeEmpregado").alias("nomeEmpregado"),
				joinPro.get("numProjeto").alias("numProjeto"));
		Predicate where =  cb.equal(joinPro.get("numProjeto"),"20");
		query.where(where);
		List<ConsultaLetraI> listaI = manager.createQuery(query).getResultList();
		return listaI;
	}
	
	/*
	 * k) Qual o nome do departamento do empregado de código 7?     
	 */
	public List<ConsultaLetraK> findLetraK() throws SQLException {
		CriteriaBuilder cb = manager.getCriteriaBuilder();
		CriteriaQuery<ConsultaLetraK> query = cb.createQuery(ConsultaLetraK.class);
		Root<Empregado> root = query.from(Empregado.class);
		Join<Empregado,Departamento> joinDep = root.join("departamento",JoinType.LEFT);
		query.multiselect(joinDep.get("numDepartamento").alias("numDepartamento"),joinDep.get("descLocal").alias("descDepartamento"),
				root.get("nomeEmpregado").alias("nomeEmpregado"));
		Predicate where =  cb.equal(root.get("codEmpregado"),7);
		query.where(where);
		List<ConsultaLetraK> listaK = manager.createQuery(query).getResultList();
		return listaK;
	}
	
	/*
	 * l) Qual o nome do departamento que controla o projeto onde o empregado de código 4 trabalha?     
	 */
	public List<ConsultaLetraL> findLetraL() throws SQLException {
		CriteriaBuilder cb = manager.getCriteriaBuilder();
		CriteriaQuery<ConsultaLetraL> query = cb.createQuery(ConsultaLetraL.class);
		Root<ProjEmp> root = query.from(ProjEmp.class);
		Join<ProjEmp,Projeto> joinPro = root.join("projeto",JoinType.LEFT);
		Join<Projeto,Departamento> joinDep = joinPro.join("departamento", JoinType.LEFT);
		query.multiselect(joinDep.get("numDepartamento").alias("numDepartamento"),joinDep.get("descLocal").alias("descDepartamento"));
		Predicate where =  cb.equal(root.get("empregado"),4);
		query.where(where).distinct(true);
		List<ConsultaLetraL> listaL = manager.createQuery(query).getResultList();
		return listaL;
	}

	/*
	 * m) Qual o nome dos empregados que trabalham em algum projeto ?     
	 */
	public List<ConsultaLetraM> findLetraM() throws SQLException {
		CriteriaBuilder cb = manager.getCriteriaBuilder();
		CriteriaQuery<ConsultaLetraM> query = cb.createQuery(ConsultaLetraM.class);
		Root<ProjEmp> root = query.from(ProjEmp.class);
		Join<ProjEmp,Projeto> joinPro = root.join("projeto",JoinType.LEFT);
		Join<ProjEmp,Empregado> joinEmp = root.join("empregado", JoinType.LEFT);
		Join<Projeto,Departamento> joinDep = joinPro.join("departamento", JoinType.INNER);	
		query.multiselect(joinEmp.get("nomeEmpregado").alias("nomeEmpregado"), joinDep.get("numDepartamento").alias("numDepartamento"),
				joinDep.get("descLocal").alias("nomeDepartamento"), joinPro.get("descProjeto").alias("nomeProjeto")).distinct(true);
		List<ConsultaLetraM> listaM = manager.createQuery(query).getResultList();
		return listaM;
	}

	/*
	 * n) Qual o nome dos empregados que trabalham em algum projeto e que supervisionam outros empregados?     
	 */
	public List<ConsultaLetraN> findLetraN() throws SQLException {
		CriteriaBuilder cb = manager.getCriteriaBuilder();
		CriteriaQuery<ConsultaLetraN> query = cb.createQuery(ConsultaLetraN.class);
		Root<ProjEmp> root = query.from(ProjEmp.class);
		Join<ProjEmp,Empregado> joinEmp = root.join("empregado", JoinType.LEFT);
		Join<ProjEmp,Projeto> joinPro = root.join("projeto",JoinType.LEFT);
		Join<Projeto,Departamento> joinDep = joinPro.join("departamento", JoinType.INNER);
		Join<Empregado,Gerencia> joinGer = joinEmp.join("gerente", JoinType.LEFT);
		query.multiselect(joinEmp.get("nomeEmpregado").alias("nomeEmpregado"), joinDep.get("numDepartamento").alias("numDepartamento"),
				joinDep.get("descLocal").alias("nomeDepartamento"), joinPro.get("numProjeto").alias("nomeProjeto"), 
				joinGer.get("nomeEmpregado").alias("nomeGerente")).distinct(true);
		
		List<ConsultaLetraN> listaN = manager.createQuery(query).getResultList();
		return listaN;
	}

	/*
	 * o) Qual o nome dos empregados que não estão trabalhando em projetos?    
	 */
	public List<ConsultaLetraO> findLetraO() throws SQLException {
		CriteriaBuilder cb = manager.getCriteriaBuilder();
		CriteriaQuery<ConsultaLetraO> query = cb.createQuery(ConsultaLetraO.class);
		Root<Empregado> root = query.from(Empregado.class);
		Join<Empregado,Departamento> joinDep = root.join("departamento", JoinType.LEFT);
		query.multiselect(root.get("nomeEmpregado").alias("nomeEmpregado"), joinDep.get("numDepartamento").alias("numDepartamento"),
				joinDep.get("descLocal").alias("nomeDepartamento")).distinct(true);
		Subquery<ProjEmp> subQuery = query.subquery(ProjEmp.class);
		Root<ProjEmp> subRoot = subQuery.from(ProjEmp.class);
		subQuery.select(subRoot);
		Predicate where = cb.equal(subRoot.get("empregado"),root);
		subQuery.where(where);
		query.where(cb.not(cb.exists(subQuery)));
		List<ConsultaLetraO> listaO = manager.createQuery(query).getResultList();
		return listaO;
	}
	/*
	 * p) Qual o nome dos empregados que não estão trabalhando em projetos, mas que gerenciam algum departamento?    
	 */
	public List<ConsultaLetraP> findLetraP() throws SQLException {
		CriteriaBuilder cb = manager.getCriteriaBuilder();
		CriteriaQuery<ConsultaLetraP> query = cb.createQuery(ConsultaLetraP.class);
		Root<Gerencia> root = query.from(Gerencia.class);
		Join<Gerencia,Departamento> joinDep = root.join("departamento", JoinType.INNER);
		Join<Gerencia,Empregado> joinEmp = root.join("empregado", JoinType.INNER);
		query.multiselect(joinEmp.get("nomeEmpregado").alias("nomeGerente"), joinDep.get("numDepartamento").alias("numDepartamento"),
				joinDep.get("descLocal").alias("nomeDepartamento")).distinct(true);
		Subquery<ProjEmp> subQuery = query.subquery(ProjEmp.class);
		Root<ProjEmp> subRoot = subQuery.from(ProjEmp.class);
		subQuery.select(subRoot);
		Predicate where = cb.equal(subRoot.get("empregado"),root);
		subQuery.where(where);
		query.where(cb.not(cb.exists(subQuery)));
		List<ConsultaLetraP> listaP = manager.createQuery(query).getResultList();
		return listaP;
	}
	
	/*
	 * q) Qual a descrição da ou das localizações do departamento de código 222?     
	 */
	public List<ConsultaLetraQ> findLetraQ() throws SQLException {

		CriteriaBuilder cb = manager.getCriteriaBuilder();
		CriteriaQuery<ConsultaLetraQ> query = cb.createQuery(ConsultaLetraQ.class);
		Root<Local> root = query.from(Local.class);
		Join<Local,Departamento> joinDep  = root.join("departamentos",JoinType.LEFT);
		query.multiselect(root.get("nomLocal").alias("codLocal"), root.get("descLocal").alias("nomeLocal"),
				  		joinDep.get("numDepartamento").alias("codDepartamento"), joinDep.get("descLocal").alias("nomeDepartamento"));
		Predicate where =  cb.equal(joinDep.get("numDepartamento"),"222");
		query.where(where).distinct(true);
		List<ConsultaLetraQ> listaQ = manager.createQuery(query).getResultList();
		return listaQ;
	}
	/*
	 * r) Qual a quantidade de horas trabalhadas pelo empregado de código 2?     
	 */
	public List<ConsultaLetraR> findLetraR() throws SQLException {
		CriteriaBuilder cb = manager.getCriteriaBuilder();
		CriteriaQuery<ConsultaLetraR> query = cb.createQuery(ConsultaLetraR.class);
		Root<ProjEmp> root = query.from(ProjEmp.class);
		Join<ProjEmp,Empregado> joinEmp  = root.join("empregado",JoinType.LEFT);
		query.multiselect(cb.sum(root.get("quantHoras")).alias("qtdHoras"), joinEmp.get("nomeEmpregado").alias("nomeEmpregado"));
		Predicate where =  cb.equal(root.get("empregado"),2);
		query.where(where).distinct(true);
		List<ConsultaLetraR> listaR = manager.createQuery(query).getResultList();
		return listaR;
	}

	/*
	 * s) Qual a quantidade de horas trabalhadas pelo empregado de código 4?     
	 */
	public List<ConsultaLetraR> findLetraS() throws SQLException {
		CriteriaBuilder cb = manager.getCriteriaBuilder();
		CriteriaQuery<ConsultaLetraR> query = cb.createQuery(ConsultaLetraR.class);
		Root<ProjEmp> root = query.from(ProjEmp.class);
		Join<ProjEmp,Empregado> joinEmp  = root.join("empregado",JoinType.LEFT);
		query.multiselect(cb.sum(root.get("quantHoras")).alias("qtdHoras"), joinEmp.get("nomeEmpregado").alias("nomeEmpregado"));
		Predicate where =  cb.equal(root.get("empregado"),4);
		query.where(where).distinct(true);
		List<ConsultaLetraR> listaR = manager.createQuery(query).getResultList();
		return listaR;
	}

	/*
	 * t) Qual a quantidade de horas trabalhadas pela empregada de nome Emília?    
	 */
	public List<ConsultaLetraR> findLetraT() throws SQLException {
		CriteriaBuilder cb = manager.getCriteriaBuilder();
		CriteriaQuery<ConsultaLetraR> query = cb.createQuery(ConsultaLetraR.class);
		Root<ProjEmp> root = query.from(ProjEmp.class);
		Join<ProjEmp,Empregado> joinEmp  = root.join("empregado",JoinType.LEFT);
		query.multiselect(cb.sum(root.get("quantHoras")).alias("qtdHoras"), joinEmp.get("nomeEmpregado").alias("nomeEmpregado"));
		Predicate where =  cb.equal(joinEmp.get("nomeEmpregado"),"EMILIA");
		query.where(where).distinct(true);
		List<ConsultaLetraR> listaR = manager.createQuery(query).getResultList();
		return listaR;
	}

	/*
	 * u) Qual a média de horas trabalhadas pelos empregados desta empresa?     
	 */
	public Double findLetraU() throws SQLException {
		CriteriaBuilder cb = manager.getCriteriaBuilder();
		CriteriaQuery<Double> query = cb.createQuery(Double.class);
		Root<ProjEmp> root = query.from(ProjEmp.class);
		query.select(cb.avg(root.get("quantHoras")));
		return manager.createQuery(query).getSingleResult();
	}

	/*
	 * v) Qual a quantidade de empregados existentes na empresa?     
	 */
	public Long findLetraV() throws SQLException {
		TypedQuery<Long> query = manager.createQuery("select count(e) from Empregado e", Long.class);
		return query.getSingleResult();
	}

	/*
	 * x) Quais os  nomes dos dependentes de todas as empregadas da empresa (apenas empregadas)?    
	 */
	public List<LetraBDep> findLetraX() throws SQLException {
		CriteriaBuilder cb = manager.getCriteriaBuilder();
		CriteriaQuery<LetraBDep> query = cb.createQuery(LetraBDep.class);
		Root<Dependente> root = query.from(Dependente.class);
		Join<Dependente,Empregado> joinEmp = root.join("empregado", JoinType.LEFT);
		query.multiselect(root.get("nome").alias("nomeDependente"),root.get("sexo").alias("sexoDependente"),
				root.get("tipoDependente").alias("tipoDependente"),root.get("dtNascimento").alias("dtNascDependente"),
				joinEmp.get("nomeEmpregado").alias("nomeEmpregada"));
		Predicate where =  cb.equal(joinEmp.get("sexo"),TipoSexo.FEMININO);
		query.where(where);
		List<LetraBDep> listaB = manager.createQuery(query).getResultList();
		return listaB;
	}
	
	/*
	 * y) Qual o nome dos projetos que não são controlados por algum departamento?     
	 */
	public List<ConsultaLetraY> findLetraY() throws SQLException {
		CriteriaBuilder cb = manager.getCriteriaBuilder();
		CriteriaQuery<ConsultaLetraY> query = cb.createQuery(ConsultaLetraY.class);
		Root<Projeto> root = query.from(Projeto.class);
		Join<Projeto,Local> joinLoc  = root.join("local",JoinType.LEFT);
		query.multiselect(root.get("descProjeto").alias("descProjeto"), root.get("numProjeto").alias("numProjeto"),
				joinLoc.get("nomLocal").alias("descLocal"), joinLoc.get("descLocal").alias("nomeLocal"));
		Predicate where =  root.get("departamento").isNull();
		query.where(where).distinct(true);
		List<ConsultaLetraY> listaY = manager.createQuery(query).getResultList();
		return listaY;
	}
	/*
	 * z) Qual o nome dos departamentos que estão localizados em dois ou mais locais diferentes?    
	 */
	public List<ConsultaLetraZ> findLetraZ() throws SQLException {
		CriteriaBuilder cb = manager.getCriteriaBuilder();
		CriteriaQuery<ConsultaLetraZ> query = cb.createQuery(ConsultaLetraZ.class);
		Root<Local> root = query.from(Local.class);
		Join<Local,Departamento> joinDep  = root.join("departamentos",JoinType.INNER);		
		Expression<Integer> local = joinDep.get("idDepartamento");
		query.multiselect(joinDep.get("numDepartamento").alias("numDepartamento"), joinDep.get("descLocal").alias("nomeDepartamento"),
				cb.count(local));
		query.groupBy(joinDep.get("idDepartamento"));
		query.having(cb.gt(cb.count(local),1));
		List<ConsultaLetraZ> listaZ = manager.createQuery(query).getResultList();
		return listaZ;
	}
}
