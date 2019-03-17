package application;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import dao.DAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;
import metier.MetierImpl;
import metier.User;

public class LoginController implements Initializable {
	
	
	@FXML
	private Button loginBtn;
	@FXML
	private ComboBox<String> Rule;
	@FXML
	private TextField usernameTF;
	@FXML
    private PasswordField mdpPF;
	@FXML
	private Label etatBD;
	
	MetierImpl obj;
	
    static String sai;
	
	//ObservableList<User> etabli;
	
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		try {
			etatBD();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 obj = new MetierImpl();
		//etabli = FXCollections.observableArrayList(obj.getAllEtabli());
		ObservableList<String> oblist=FXCollections.observableArrayList("admin","user");
		
		Rule.setItems(oblist);
		Rule.getSelectionModel().selectFirst();
		//		for(User etb:obj.getAllEtabli()) {
//			etabliCB.getItems().add(etb);
//			System.out.println(etb.getNomEtabli()+"    ");
//		}
		
//		StringConverter<User> conv = new StringConverter<User>() {
//
//			@Override
//			public User fromString(String string) {
//				// TODO Auto-generated method stub
//				return null;
//			}

//			@Override
//			public String toString(User object) {
//				// TODO Auto-generated method stub
//				return object.getRule();
//			}
//
//		};
//		
//		Rule.setConverter(conv);
		
		}
		
		
//		for(User tab:obj.getAllEtabli()) {
//			etabliCB.setv(tab.getNomEtabli());
//			System.out.println(tab.getNomEtabli()+"	");
//		}
		
		
		
		

	@FXML
	private void close_app(MouseEvent event) {
		System.exit(0);
	}
	
	public void etatBD() throws IOException,SQLException {
		if(!(DAO.getConn().isClosed())) {
			etatBD.setText("Connectée");
			etatBD.setTextFill(Color.web("#2ecc71"));
		}else {
			etatBD.setText("Déconnectée");
			etatBD.setTextFill(javafx.scene.paint.Color.web("#e74c3c"));
		}
	}
	
	public void EnterCP(ActionEvent a) throws IOException,SQLException {
		 obj = new MetierImpl();
		List<User> list = obj.RechercheParRole(Rule.getSelectionModel().getSelectedItem());
		System.out.println(Rule.getSelectionModel().getSelectedItem());
		Map<String, String> map= new HashMap<String,String>();
		for(User etb:list) {
			map.put(etb.getUsername(), etb.getPassword());
		}
		
		if (map.containsKey(usernameTF.getText())) {
			String val = map.get(usernameTF.getText());
	
			if (val.equals(mdpPF.getText())) {
				sai=Rule.getSelectionModel().getSelectedItem();
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
		}else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("");
			alert.initStyle(StageStyle.UTILITY);
			alert.setHeaderText (null);
			alert.setContentText ("Mot de passe ou nom d'utilisateur incorrecte");
			ButtonType loginButtonType1 =new ButtonType("Retour ",ButtonData.OK_DONE);
			alert.getDialogPane ().getButtonTypes ().clear();
			alert.getDialogPane ().getButtonTypes ().addAll(loginButtonType1 );
			Optional<ButtonType> result = alert.showAndWait ();
			if (result.get() == loginButtonType1 ){
			return;
			}
		}
			
	}



}
