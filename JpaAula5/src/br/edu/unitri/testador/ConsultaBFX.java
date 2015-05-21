/**
 * 
 */
package br.edu.unitri.testador;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import br.edu.unitri.DTO.Consultas.ConsultaLetraB;
import br.edu.unitri.DTO.Consultas.LetraBDep;
import br.edu.unitri.DTO.Consultas.LetraBProj;
import br.edu.unitri.controler.ExerciciosControler;
import br.edu.unitri.testador.FXDialog.Type;
import br.edu.unitri.util.GenericTable;

/**
 * @author marcos.fernando
 *
 */
public class ConsultaBFX implements Initializable {

	@FXML private Tab tabConsulta;
	@FXML private RadioButton rbCodEmpregado;
	@FXML private RadioButton rbDepartamento;
	@FXML private TableView<LetraBProj> tbProjetos;
	@FXML private TableView<ConsultaLetraB> tbEmpregados;
	@FXML private ToggleGroup BuscarPor;
	@FXML private TableView<LetraBDep> tbDependentes;
	@FXML private RadioButton rbNomeEmpregado;
	@FXML private TextField txtBuscar;
 	@FXML private TabPane tabTela;
	@FXML private Button btnBuscar;
	
	private ObservableList<ConsultaLetraB> dados = FXCollections.observableArrayList();
	private ObservableList<LetraBProj> dadosProj = FXCollections.observableArrayList();
	private ObservableList<LetraBDep> dadosDep   = FXCollections.observableArrayList();
	private ExerciciosControler exerciciosCtr = new ExerciciosControler();
	
	@FXML
	void btnBuscarClick(ActionEvent event) throws SQLException {
		
	   	if (isValidConsulta()) {
			String qryEmpregados ="select e.codempregado, e.dtnasc, e.endEmpregado, e.nomeempregado, "
					+ " case when e.sexo = 0 then 'Masculino' when e.sexo = 1 then 'Feminino' when e.sexo = 2 then 'Indefinido' end as Sexo,"
					+ " (d.numdepartamento || '--'|| d.desclocal) as nomedepartamento,"
					+ " e2.nomeempregado as nomeGerente from tbempregado e"
					+ " join tbDepartamento d on d.idDepartamento = e.departamento_id"
					+ " join tbempregado e2 on e2.codEmpregado = e.gerente_id"
					+ " join tbGerencia g on g.gerente_id = e2.codEmpregado";
			String where ="";
			if (rbCodEmpregado.isSelected()) {
				where = " where e.codempregado = '" + txtBuscar.getText() + "'";
			}
			if (rbDepartamento.isSelected()) {
				where = " where (d.numdepartamento like  '%"+ txtBuscar.getText() + "%') or"
					 + " (d.desclocal like  '%"+ txtBuscar.getText() + "%')";
			}
			if (rbNomeEmpregado.isSelected()) {
				where = " where e.nomeempregado like  '%"+ txtBuscar.getText() + "%'";
			}
			
			LimpaTabelas();
			
			dados.addAll(exerciciosCtr.findLetraB(qryEmpregados,where));
			tbEmpregados.setItems(dados);
		} else {
			new FXDialog(Type.WARNING, "Escolha pelo menos uma das opções para consulta!").showDialog();
			popularDados();
			txtBuscar.requestFocus();
		}
	}

	private boolean isValidConsulta() {
		boolean ok = rbCodEmpregado.isSelected() || rbDepartamento.isSelected() || rbNomeEmpregado.isSelected();
		if (ok) {
			ok = !txtBuscar.getText().isEmpty();
		}
		return ok;
	}

	private void LimpaTabelas() {
		tbEmpregados.getItems().clear();
		tbProjetos.getItems().clear();
		tbDependentes.getItems().clear();
	}
	
	private void popularDados() {
		LimpaTabelas();
		dados.clear();
		try {
			dados.addAll(exerciciosCtr.findLetraB());
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
		tbEmpregados.setItems(dados);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tbEmpregados.getColumns().addAll(new GenericTable<ConsultaLetraB>().tableColunas(ConsultaLetraB.class));
		tbProjetos.getColumns().addAll(new GenericTable<LetraBProj>().tableColunas(LetraBProj.class));
		tbDependentes.getColumns().addAll(new GenericTable<LetraBDep>().tableColunas(LetraBDep.class));
		popularDados();
		tbEmpregados.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getClickCount() == 1) {
					dadosDep.clear();
					dadosProj.clear();
					tbProjetos.getItems().clear();
					tbDependentes.getItems().clear();
					dadosDep.addAll(dados.get(tbEmpregados.getSelectionModel().getSelectedIndex()).getDependentes());
					dadosProj.addAll(dados.get(tbEmpregados.getSelectionModel().getSelectedIndex()).getProjetos());
					tbProjetos.setItems(dadosProj);
					tbDependentes.setItems(dadosDep);
				}
			}
		});
	}
}
