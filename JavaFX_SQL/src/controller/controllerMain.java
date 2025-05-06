package controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import dao.FuncionarioDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Funcionario;

public class controllerMain implements Initializable{

    @FXML
    private Button btApagar;

    @FXML
    private Button btEditar;

    @FXML
    private Button btSalvar;
    
    @FXML
    private TextField txtCPF;

    @FXML
    private TextField txtCargo;

    @FXML
    private TextField txtNivel;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtSenha;
    
    @FXML
    private TableColumn<Funcionario, String> columnCPF;

    @FXML
    private TableColumn<Funcionario, String> columnCargo;

    @FXML
    private TableColumn<Funcionario, String> columnID;

    @FXML
    private TableColumn<Funcionario, String> columnNivel;

    @FXML
    private TableColumn<Funcionario, String> columnNome;

    @FXML
    private TableColumn<Funcionario, String> columnSenha;

    @FXML
    private TableView<Funcionario> tableFuncionarios;
    
    
    @FXML
    void actionApagar(ActionEvent event) {
    	int i = tableFuncionarios.getSelectionModel().getSelectedIndex();
    	if(i == -1) {
    		Alert msg = new Alert(AlertType.ERROR);
    		msg.setHeaderText("Erro! ");
    		msg.setContentText("Erro! Selecione um funcionario antes de excluir!");
    		msg.show();
    	}else {
    		Funcionario func = new Funcionario();
    		FuncionarioDAO funcDAO = new FuncionarioDAO();
    		
    		func = tableFuncionarios.getItems().get(i);
    		Alert msg = new Alert(AlertType.CONFIRMATION);
    		msg.setContentText("Deseja realmente excluir o funcionario "+ func.getNome()+ "?");
    		
    		Optional<ButtonType> resultado = msg.showAndWait();
    		
    		if(resultado.isPresent() && resultado.get() == ButtonType.OK) {
    			funcDAO.delete(func.getCpf());
    			carregarTable();
    		}
    		
    	}
    	
    }

    @FXML
    void actionEditar(ActionEvent event) {

    }

    @FXML
    void actionSalvar(ActionEvent event) {
    	if(txtNome.getText().equals("") || txtCargo.getText().equals("") || txtCPF.getText().equals("")
    			|| txtNivel.getText().equals("") || txtSenha.getText().equals("")) {
    		Alert msg = new Alert(AlertType.ERROR);
    		msg.setHeaderText("Erro! informações incompletas!");
    		msg.setContentText("Falha ao cadastrar! Verifique as informações e tente de novo!");
    		msg.show();
    	}else {
    		Funcionario funcionario = new Funcionario();
    		FuncionarioDAO funcDAO = new FuncionarioDAO();
    		funcionario.setNome(txtNome.getText());
    		funcionario.setCargo(txtCargo.getText());
    		funcionario.setCpf(txtCPF.getText());
    		funcionario.setNivel(txtNivel.getText());
    		funcionario.setSenha(txtSenha.getText());
    		
    		funcDAO.create(funcionario);
    		carregarTable();
    		
    		txtNome.setText("");
    		txtCargo.setText("");
    		txtCPF.setText("");
    		txtNivel.setText("");
    		txtSenha.setText("");
    	}
    	
    }
    
    
    private ObservableList<Funcionario> funcionarios;
    private FuncionarioDAO funcDAO = new FuncionarioDAO();
    void carregarTable() {
    	funcionarios = FXCollections.observableArrayList(funcDAO.read());
    	
    	columnID.setCellValueFactory(new PropertyValueFactory<>("id"));
    	columnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
    	columnCargo.setCellValueFactory(new PropertyValueFactory<>("cargo"));
    	columnCPF.setCellValueFactory(new PropertyValueFactory<>("cpf"));
    	columnSenha.setCellValueFactory(new PropertyValueFactory<>("senha"));
    	columnNivel.setCellValueFactory(new PropertyValueFactory<>("nivel"));
    	
    	tableFuncionarios.setItems(funcionarios);
    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		carregarTable();
	}
    		
    
    

}
