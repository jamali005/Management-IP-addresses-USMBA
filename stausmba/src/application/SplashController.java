package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class SplashController implements Initializable {
	
	@FXML
	private Label loadLabel;
	
	static Label sLabel;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		sLabel=loadLabel;
		
	}

}
