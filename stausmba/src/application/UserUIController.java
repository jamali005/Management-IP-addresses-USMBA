package application;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.sun.javafx.css.Rule;

import dao.DAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import metier.Adresse;
import metier.MetierImpl;
import metier.User;

public class UserUIController implements Initializable {

	@FXML
	private TextField username;

	@FXML
	private TextField password;

	@FXML
	private ComboBox<String> role;

	@FXML
	private Button addBtn;

	@FXML
	private Button editBtn;

	@FXML
	private Button deleteBtn;

	@FXML
	private TextField search;

	@FXML
	private ComboBox<String> rechercherole;

	@FXML
	private Button RechBtn;

	@FXML
	private TableView<User> saisieUser;
	@FXML
	private TableColumn<User, String> id1;
	@FXML
	private TableColumn<User, String> username1;
	@FXML
	private TableColumn<User, String> password1;
	@FXML
	private TableColumn<User, String> role1;

	private MetierImpl impl;

	private User selectedUser;

	@FXML
	public void loadUser() {
		impl = new MetierImpl();

		// for etablissement cobobox
		ObservableList<String> oblist = FXCollections.observableArrayList("admin", "user");
		role.setItems(oblist);
		role.getSelectionModel().selectFirst();
		// add column in tableview
		saisieUser.getItems().addAll(impl.getAllUser());
		id1.setCellValueFactory(new PropertyValueFactory<User, String>("Id"));
		username1.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
		password1.setCellValueFactory(new PropertyValueFactory<User, String>("password"));
		role1.setCellValueFactory(new PropertyValueFactory<User, String>("Rule"));
		
		// for search
		ObservableList<String> choice = FXCollections.observableArrayList("mot clé", "username", "password", "role");
		rechercherole.setItems(choice);
		rechercherole.getSelectionModel().selectFirst();
	}

	@FXML
	public void addUser() {
		impl = new MetierImpl();
		User e = new User(username.getText(), password.getText(), role.getValue());
		impl.addUser(e);
		saisieUser.getItems().clear();
		this.loadUser();
	}

	@FXML
	public void editUser() {
		Alert alert = new Alert(AlertType.CONFIRMATION, " voulez vous modifier ces informations ?", ButtonType.YES,
				ButtonType.NO);
		alert.setTitle("Question");
		Optional<ButtonType> result = alert.showAndWait();
		impl = new MetierImpl();
		if (result.get() == ButtonType.YES) {
			selectedUser.setUsername(username.getText());
			selectedUser.setPassword(password.getText());
			selectedUser.setRule(role.getSelectionModel().getSelectedItem());
			impl.UpdateUser(selectedUser);
		}
		if (result.get() == ButtonType.NO) {
			return;
		}
		saisieUser.getItems().clear();
		this.loadUser();
	}

	@FXML
	public void DeleteUser() {
		impl = new MetierImpl();
		selectedUser.setUsername(username.getText());
		selectedUser.setPassword(password.getText());
		selectedUser.setRule(role.getSelectionModel().getSelectedItem());
		impl.DeleteUser(selectedUser);
		saisieUser.getItems().clear();
		this.loadUser();

	}

	@FXML
	public void recherche() {

//		Recherchetab.selectedProperty();
		impl = new MetierImpl();
			if (rechercherole.getValue().equals("Rule")) {
				saisieUser.getItems().clear();
				saisieUser.getItems().addAll(impl.RechercheParRole(search.getText()));
			} else {
				if (rechercherole.getValue().equals("username")) {
					saisieUser.getItems().clear();
					saisieUser.getItems().addAll(impl.RechercheParUsername(search.getText()));
				} else {
							saisieUser.getItems().clear();
							saisieUser.getItems().addAll(impl.RechercheParMotCle(search.getText()));
						}
					}
	}
	@FXML
	private void close_app_saisie(MouseEvent event) {
		System.exit(0);
	}
	@FXML
	private void deconnexion(MouseEvent event) throws IOException {
		((Node)event.getSource()).getScene().getWindow().hide();
		 DAO.getConn();
		 Stage stage=new Stage();
		AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("loginUI.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.initStyle(StageStyle.UNDECORATED);
		stage.show();
	}
	@FXML
	public void selectionIP11() {
		selectedUser = saisieUser.getSelectionModel().getSelectedItem();
		username.setText(selectedUser.getUsername());
		password.setText(selectedUser.getPassword());
		role.setValue(selectedUser.getRule());
		

	}
	@FXML 
	private void versIP(MouseEvent a) throws IOException {
		((Node)a.getSource()).getScene().getWindow().hide();
		 DAO.getConn();
		Stage stage=new Stage();
		AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("IPUI.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.initStyle(StageStyle.UNDECORATED);
		stage.show();

	}
	@FXML 
	private void users(MouseEvent a) throws IOException {
		((Node) a.getSource()).getScene().getWindow().hide();
		DAO.getConn();
		Stage stage = new Stage();
		AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("UserUI.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.initStyle(StageStyle.UNDECORATED);
		stage.show();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		loadUser();

	}

}
