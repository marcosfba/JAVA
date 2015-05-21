package br.edu.unitri.testador;

import java.io.InputStream;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class FormFX<T> {
	
	private FXMLLoader fxmlLoader;
	private BorderPane root;
	private T controller;
	private Stage formulario;
	
	public FormFX() {
		super();
	}

	public FormFX(String fxml, Stage form, String titulo, boolean max) throws Exception  {
		
		FXMLLoader loader = new FXMLLoader();
        InputStream in = Relatorio_Aula2.class.getResourceAsStream(fxml);
        loader.setLocation(Relatorio_Aula2.class.getResource(fxml));
        BorderPane border;
        try {
            border = (BorderPane) loader.load(in);
        } finally {
            in.close();
        } 
        this.root = border;
		this.controller = loader.<T>getController();
		this.formulario = form;
		Scene scene = new Scene(this.root);	
		this.formulario.setResizable(max);

		if (max) {
			this.formulario.setOnCloseRequest(new EventHandler<WindowEvent>() {
				public void handle(WindowEvent we) {
					System.exit(0);
				}
			});
		}

		if (!max) {
		   this.formulario.initStyle(StageStyle.UTILITY);
		   this.formulario.initModality(Modality.APPLICATION_MODAL);
		}
		this.formulario.setScene(scene);
		this.formulario.setTitle(titulo);
		if (!max) {
			this.formulario.showAndWait();
		} else {
			this.formulario.show();
		}
	}

	public FXMLLoader getFxmlLoader() {
		return fxmlLoader;
	}

	public void setFxmlLoader(FXMLLoader fxmlLoader) {
		this.fxmlLoader = fxmlLoader;
	}

	public BorderPane getRoot() {
		return root;
	}

	public void setRoot(BorderPane root) {
		this.root = root;
	}

	public T getController() {
		return controller;
	}

	public void setController(T controller) {
		this.controller = controller;
	}

	public Stage getFormulario() {
		return formulario;
	}

	public void setFormulario(Stage formulario) {
		this.formulario = formulario;
	}

}
