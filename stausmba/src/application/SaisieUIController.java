package application;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import dao.DAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import metier.Adresse;
import metier.MetierImpl;
import metier.User;

public class SaisieUIController implements Initializable {

	@FXML
	private ComboBox<String> etablissement;

	@FXML
	private TextField service;

	@FXML
	private TextField MAP;

	@FXML
	private ComboBox<String> IP;

	@FXML
	private DatePicker dtR;

	@FXML
	private TextField search;

	@FXML
	private Button addBtn;

	@FXML
	private Button reserveBtn;

	@FXML
	private Button editBtn;

	@FXML
	private Button deleteBtn;
	@FXML
	private ComboBox<String> recherchechoice;
	@FXML
	private Button RechBtn;

	@FXML
	private TabPane tab;

	@FXML
	TableView<Adresse> saisieTV1;

	@FXML
	TableColumn<Adresse, String> IdIP1;

	@FXML
	TableColumn<Adresse, String> IP1;

	@FXML
	TableView<Adresse> saisieTV11;
	@FXML
	TableColumn<Adresse, String> IdIP2;
	@FXML
	TableColumn<Adresse, String> etablissement2;
	@FXML
	TableColumn<Adresse, String> service2;
	@FXML
	TableColumn<Adresse, String> IP2;
	@FXML
	TableColumn<Adresse, String> MAP2;
	@FXML
	TableColumn<Adresse, String> dtr2;

	MetierImpl impl;

	Adresse selectedPerson;

	MetierImpl obj;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		loadEmp();

		
	}

	public void loadEmp() {
		
		impl = new MetierImpl();

		obj = new MetierImpl();
		// for etablissement cobobox
		ObservableList<String> oblist = FXCollections.observableArrayList("Présidence", "FSDM", "FLDM", "FSJES", "FST",
				"FLS", "EST", "FMP", "ENSA", "ENCG", "ENS", "CHARIAA");
		etablissement.setItems(oblist);
		etablissement.getSelectionModel().selectFirst();
		ObservableList<String> IPlist = FXCollections.observableArrayList(obj.getAllIPNotreserved());
		IP.setEditable(true);
		IP.setItems(IPlist);
		// add column in tableview

		IdIP2.setCellValueFactory(new PropertyValueFactory<Adresse, String>("id"));
		etablissement2.setCellValueFactory(new PropertyValueFactory<Adresse, String>("etablissement"));
		service2.setCellValueFactory(new PropertyValueFactory<Adresse, String>("service"));
		IP2.setCellValueFactory(new PropertyValueFactory<Adresse, String>("IP"));
		MAP2.setCellValueFactory(new PropertyValueFactory<Adresse, String>("MAP"));
		dtr2.setCellValueFactory(new PropertyValueFactory<Adresse, String>("dtr"));
		IdIP1.setCellValueFactory(new PropertyValueFactory<Adresse, String>("id"));

		IP1.setCellValueFactory(new PropertyValueFactory<Adresse, String>("IP"));

//		add all data in 2 table view reserved or not
		saisieTV11.getItems().addAll(impl.getAdresseesParReservation(true));System.out.println("toto");
		saisieTV1.getItems().addAll(impl.getAdresseesParReservation(false));
		// for search
		ObservableList<String> choice = FXCollections.observableArrayList("mot clé", "id", "etablissement", "service",
				"IP", "MAP");
		recherchechoice.setItems(choice);
		recherchechoice.getSelectionModel().selectFirst();
		dtR.setValue(LocalDate.now());

	}

	@FXML
	public void addEmp() {
		impl = new MetierImpl();
		int reserve = 0;
		Adresse e;
		if (!impl.isValidIP(IP.getValue()) || !impl.isValidIP(MAP.getText())) {
			Alert alert3 = new Alert(AlertType.ERROR, "l addresse IP n'est pas compatible", ButtonType.OK);
			alert3.showAndWait();
			return;
		}
		for (int i = 1; i < impl.getAllIP().size(); i++) {
			if (impl.getAllIP().get(i).getIP().equals(IP.getValue()) && impl.getAllIP().get(i).getMAP().equals(MAP.getText())) {
				Alert alert1 = new Alert(AlertType.ERROR, "l addresse IP est deja existe", ButtonType.OK);
				alert1.showAndWait();
				return;
			}

		}

		Alert alert = new Alert(AlertType.INFORMATION, " l'addresse est reserver ?", ButtonType.YES, ButtonType.NO);
		alert.setTitle("Question");
		Optional<ButtonType> result = alert.showAndWait();

		if (result.get() == ButtonType.YES) {
			reserve = 1;
		}
		if (result.get() == ButtonType.NO) {
			reserve = 0;
		}

		if (dtR.getValue() == null) {

			e = new Adresse(etablissement.getValue(), service.getText(), IP.getValue(), MAP.getText(), null, reserve);
		} else {
			e = new Adresse(etablissement.getValue(), service.getText(), IP.getValue(), MAP.getText(),
					dtR.getValue().toString(), reserve);
		}

		impl = new MetierImpl();
		impl.addEmp(e);
		saisieTV1.getItems().clear();
		saisieTV11.getItems().clear();

		this.loadEmp();

	}

	@FXML
	public void selectionIP11() {

		saisieTV1.getSelectionModel().clearSelection();
		selectedPerson = saisieTV11.getSelectionModel().getSelectedItem();
		etablissement.setValue(selectedPerson.getEtablissement());
		service.setText(selectedPerson.getService());
		IP.setValue(selectedPerson.getIP());
		MAP.setText(selectedPerson.getMAP());
		dtR.setValue(LocalDate.parse(selectedPerson.getDtr()));    

	}

	@FXML
	public void selectionIP1() {

		saisieTV11.getSelectionModel().clearSelection();

		etablissement.getSelectionModel().selectFirst();
		selectedPerson = saisieTV1.getSelectionModel().getSelectedItem();
		service.setText(null);
		IP.setValue(selectedPerson.getIP());
		MAP.setText(null);
		dtR.setValue(LocalDate.now());
	}

	@FXML
	public void editIP() {
		if (!impl.isValidIP(MAP.getText())) {
			Alert alert3 = new Alert(AlertType.ERROR, "l addresse IP n'est pas compatible", ButtonType.OK);
			alert3.showAndWait();
			return;
		}
		Alert alert = new Alert(AlertType.CONFIRMATION, " voulez vous modifier ces informations ?", ButtonType.YES,
				ButtonType.NO);
		alert.setTitle("Question");
		Optional<ButtonType> result = alert.showAndWait();
		impl = new MetierImpl();
		if (result.get() == ButtonType.YES) {
			selectedPerson.setEtablissement(etablissement.getSelectionModel().getSelectedItem());
			selectedPerson.setService(service.getText());
			selectedPerson.setIP(IP.getSelectionModel().getSelectedItem());
			selectedPerson.setMAP(MAP.getText());
			selectedPerson.setDtr(dtR.getValue() + "");
			dtR.setValue(LocalDate.now());
			impl.UpdateEmp(selectedPerson);
		}
		if (result.get() == ButtonType.NO) {
			return;
		}

		saisieTV1.getItems().clear();
		saisieTV11.getItems().clear();

		this.loadEmp();

	}

	@FXML
	public void DeleteIP() {
		impl = new MetierImpl();
		selectedPerson.setEtablissement(etablissement.getSelectionModel().getSelectedItem());
		selectedPerson.setService(service.getText());
		selectedPerson.setIP(IP.getSelectionModel().getSelectedItem());
		selectedPerson.setMAP(MAP.getText());
		selectedPerson.setDtr(dtR.getValue() + "");
		impl.DeleteEmp(selectedPerson);
		saisieTV1.getItems().clear();
		saisieTV11.getItems().clear();

		this.loadEmp();

	}

	@FXML
	public void reserve() {
		impl = new MetierImpl();
		if (!impl.isValidIP(MAP.getText())) {
			Alert alert3 = new Alert(AlertType.ERROR, "l addresse IP n'est pas compatible", ButtonType.OK);
			alert3.showAndWait();
			return;
		}
		if (tab.getSelectionModel().getSelectedItem().getId().equals("Libre")) {
			Alert alert = new Alert(AlertType.CONFIRMATION, " vous voulez reservé ?", ButtonType.YES, ButtonType.NO);
			alert.setTitle("Question");
			Optional<ButtonType> result = alert.showAndWait();

			if (result.get() == ButtonType.YES) {
				
				selectedPerson.setEtablissement(etablissement.getSelectionModel().getSelectedItem());
				selectedPerson.setService(service.getText());
				selectedPerson.setIP(IP.getSelectionModel().getSelectedItem());
				selectedPerson.setMAP(MAP.getText());
				selectedPerson.setDtr(dtR.getValue() + "");
				impl.UpdateEmp(selectedPerson);
				impl.Reserve(selectedPerson, 1);

			}
			if (result.get() == ButtonType.NO) {
				return;
			}
		} else {

			Alert alert1 = new Alert(AlertType.CONFIRMATION, " vous voulez libere ?", ButtonType.YES, ButtonType.NO);
			alert1.setTitle("Question");
			Optional<ButtonType> result1 = alert1.showAndWait();

			if (result1.get() == ButtonType.YES) {
				selectedPerson.setEtablissement(etablissement.getSelectionModel().getSelectedItem());
				selectedPerson.setService(service.getText());
				selectedPerson.setIP(IP.getSelectionModel().getSelectedItem());
				selectedPerson.setMAP(MAP.getText());
				selectedPerson.setDtr(dtR.getValue() + "");
				impl.UpdateEmp(selectedPerson);
				impl.Reserve(selectedPerson, 0);

			}
			if (result1.get() == ButtonType.NO) {
				return;
			}
		}
		saisieTV1.getItems().clear();
		saisieTV11.getItems().clear();
		this.loadEmp();

	}

	@FXML
	public void recherche() {

//		Recherchetab.selectedProperty();
		impl = new MetierImpl();
		if (tab.getSelectionModel().getSelectedItem().getId().equals("assigné")) {
			if (recherchechoice.getValue().equals("id")) {
				saisieTV11.getItems().clear();
				saisieTV11.getItems().addAll(impl.RechercheParId(Integer.parseInt(search.getText()), 1));
			} else {
				if (recherchechoice.getValue().equals("etablissement")) {
					saisieTV11.getItems().clear();
					saisieTV11.getItems().addAll(impl.getIPByEtablissement(search.getText(), 1));
				} else {
					if (recherchechoice.getValue().equals("service")) {
						saisieTV11.getItems().clear();
						saisieTV11.getItems().addAll(impl.getIPByService(search.getText(), 1));
					} else {
						if (recherchechoice.getValue().equals("IP")) {
							saisieTV11.getItems().clear();
							saisieTV11.getItems().addAll(impl.getIPByIP(search.getText(), 1));
						} else {
							if (recherchechoice.getValue().equals("MAP")) {
								saisieTV11.getItems().clear();
								saisieTV11.getItems().addAll(impl.getIPByMAP(search.getText(), 1));
							} else {
								saisieTV11.getItems().clear();
								saisieTV11.getItems().addAll(impl.getIP(search.getText(), 1));
							}
						}
					}
				}
			}
		} else {
			if (recherchechoice.getValue().equals("id")) {
				saisieTV1.getItems().clear();
				saisieTV1.getItems().addAll(impl.RechercheParId(Integer.parseInt(search.getText()), 0));
			} else {
				if (recherchechoice.getValue().equals("etablissement")) {
					saisieTV1.getItems().clear();
					saisieTV1.getItems().addAll(impl.getIPByEtablissement(search.getText(), 0));
				} else {
					if (recherchechoice.getValue().equals("service")) {
						saisieTV1.getItems().clear();
						saisieTV1.getItems().addAll(impl.getIPByService(search.getText(), 0));
					} else {
						if (recherchechoice.getValue().equals("IP")) {
							saisieTV1.getItems().clear();
							saisieTV1.getItems().addAll(impl.getIPByIP(search.getText(), 0));
						} else {
							if (recherchechoice.getValue().equals("MAP")) {
								saisieTV1.getItems().clear();
								saisieTV1.getItems().addAll(impl.getIPByMAP(search.getText(), 0));
							} else {
								saisieTV1.getItems().clear();
								saisieTV1.getItems().addAll(impl.getIP(search.getText(), 0));
							}
						}
					}
				}

			}
		}
	}

	@FXML
	private void close_app_saisie(MouseEvent event) {
		System.exit(0);
	}

	@FXML
	private void deconnexion(MouseEvent event) throws IOException {
		((Node) event.getSource()).getScene().getWindow().hide();
		DAO.getConn();
		Stage stage = new Stage();
		AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("loginUI.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.initStyle(StageStyle.UNDECORATED);
		stage.show();
	}

	@FXML
	private void selection() {
		System.out.println(tab.getSelectionModel().getSelectedItem().getId());
	}
	@FXML
	private void users(MouseEvent a) throws IOException, SQLException {
		if (LoginController.sai.equals("admin")) {
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
		else

		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("");
			alert.initStyle(StageStyle.UTILITY);
			alert.setHeaderText(null);
			alert.setContentText("access denied ");
			ButtonType loginButtonType1 = new ButtonType("Retour ", ButtonData.OK_DONE);
			alert.getDialogPane().getButtonTypes().clear();
			alert.getDialogPane().getButtonTypes().addAll(loginButtonType1);
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == loginButtonType1) {
				return;
			}

		}
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
}
