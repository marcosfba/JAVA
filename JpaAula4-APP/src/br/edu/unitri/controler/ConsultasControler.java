/**
 * 
 */
package br.edu.unitri.controler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import br.edu.unitri.model.PessoaContatoDTO;
import br.edu.unitri.model.PessoaEnderecoDTO;
import br.edu.unitri.util.ConverterUtil;
import br.edu.unitri.util.JpaUtil;

/**
 * @author marcos.fernando
 *
 */
public class ConsultasControler {
	
	private EntityManager manager = JpaUtil.getManager();
	
	@SuppressWarnings("unchecked")
	public List<PessoaContatoDTO> findPessoaContatos(String param, boolean nome, boolean email, boolean tipoContato, boolean descTipo) throws SQLException{
		List<PessoaContatoDTO> pessoaContatoList = new ArrayList<PessoaContatoDTO>();
		
		String sql = "select p.nome as nomePessoa, p.email as emailPessoa, p.dtNascimento, c.descricao as descricao,"
				+ "case when c.tipoContato = 0 then 'Telefone Residencial'"
				+ "     when c.tipoContato = 1 then 'Telefone Celular'"
				+ "     when c.tipoContato = 2 then 'Email'"
				+ "     when c.tipoContato = 3 then 'Telefone Contato'"
				+ "     when c.tipoContato = 4 then 'Outros'"
				+ " end as tipocontato, c.complemento as complemento"
				+ " from tbpessoa p join pessoa_contato pc on pc.pessoa_id = p.id join tbcontato c on c.id = pc.contato_id";
		if (nome) {
			sql = sql.concat(" and p.nome like '%"+ param + "%'");	
		}
		if (email) {
			sql = sql.concat(" and p.email like '%"+ param + "%'");
		}
		if (tipoContato) {
			sql = sql.concat(" and c.tipoContato ='"+ param + "'");
		}
		if (descTipo) {
			sql = sql.concat(" and c.descricao like '%"+ param + "%'");
		}
		
		List<Object[]> lista =  manager.createNativeQuery(sql).getResultList();

		for (Object[] objects : lista) {				
			PessoaContatoDTO pessoaContatoDTO = new PessoaContatoDTO(objects[0].toString(), 
					objects[1].toString(), ConverterUtil.utilDateToLocalDate((Date) objects[2]), 
					objects[4].toString(), objects[3].toString(), objects[5].toString());
			pessoaContatoList.add(pessoaContatoDTO);
		}
		return pessoaContatoList;
	}
	
	@SuppressWarnings("unchecked")
	public List<PessoaEnderecoDTO> findPessoaEnderecos(String param, boolean nome, boolean email, boolean cidade, boolean uf) throws SQLException {
		List<PessoaEnderecoDTO> pessoaEnderecoList = new ArrayList<PessoaEnderecoDTO>();
		
		String sql = "select p.nome, p.email, p.dtNascimento, e.pais,"
				+ "case when e.tipoEndereco = 0 then 'Endereço Residencial'"
				+ "     when e.tipoEndereco = 1 then 'Endereço Comercial'"
				+ "     when e.tipoEndereco = 2 then 'Endereço de Referência'"
				+ "     when e.tipoEndereco = 3 then 'Endereço de Aluguel'"
				+ "     when e.tipoEndereco = 4 then 'Endereço da Casa da Mãe'"
				+ "     when e.tipoEndereco = 5 then 'Endereço da Casa do Pai'"
				+ "     when e.tipoEndereco = 6 then 'Outros'"
				+ " end as tipoendereco, e.estado, e.cidade, e.logradouro, e.numero, e.complemento, e.cep"
				+ " from tbpessoa p join pessoa_endereco pe on pe.pessoa_id = p.id"
				+ " join tbendereco e on e.id = pe.endereco_id";

		if (nome) {
			sql = sql.concat(" and p.nome like '%"+ param + "%'");	
		}
		if (email) {
			sql = sql.concat(" and p.email like '%"+ param + "%'");
		}
		if (cidade) {
			sql = sql.concat(" and e.cidade like '%"+ param + "%'");
		}
		if (uf) {
			sql = sql.concat(" and e.estado like '%"+ param + "%'");
		}
		List<Object[]> lista =  manager.createNativeQuery(sql).getResultList();
		
		for (Object[] objects : lista) {
			PessoaEnderecoDTO pessoaEnderecoDTO = new PessoaEnderecoDTO(objects[0].toString(), objects[1].toString(), 
					ConverterUtil.utilDateToLocalDate((Date) objects[2]), 
					objects[3].toString(), objects[4].toString(), objects[5].toString(), objects[6].toString(), 
					objects[7].toString(), objects[8].toString(), objects[9].toString(), objects[10].toString());
			pessoaEnderecoList.add(pessoaEnderecoDTO);
		}
		return pessoaEnderecoList;
	}

}
