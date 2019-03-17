package application;

import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MyPreloader extends Preloader {
	private Stage preloaderStage;
	private Scene scene;
	
	@Override
	public void init() throws Exception {
		// TODO Auto-generated method stub
		super.init();
		
		Parent root1 = FXMLLoader.load(getClass().getResource("Splash.fxml"));
		 scene = new Scene(root1);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		
		this.preloaderStage=stage;
		preloaderStage.setScene(scene);
		preloaderStage.initStyle(StageStyle.UNDECORATED);
		preloaderStage.show();
		
		
	}
	
	@Override
	public void handleApplicationNotification(PreloaderNotification info) {
		// TODO Auto-generated method stub
		
		if (info instanceof ProgressNotification) {
			SplashController.sLabel.setText("Chargement... "+((ProgressNotification) info).getProgress()+"%");
		}
	}
	
	@Override
	public void handleStateChangeNotification(StateChangeNotification info) {
		StateChangeNotification.Type type=info.getType();
		switch (type) {
		case BEFORE_START:
			System.out.println("BEFORE_START");
			preloaderStage.hide();
			break;
		default:
			break;
		}
	}

}
