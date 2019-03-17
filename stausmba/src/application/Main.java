package application;
	

import com.sun.javafx.application.LauncherImpl;

import javafx.application.Application;
import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Main extends Application {
	final static int COUNT_LIMIT=40000;
	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("loginUI.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void init() throws Exception {
		for (int i = 0; i < COUNT_LIMIT; i++) {
			double progress=(100*i)/COUNT_LIMIT;
			//LauncherImpl.notifyPreloader(this, new Preloader.PreloaderNotification(progress));
			LauncherImpl.notifyPreloader(this, new Preloader.ProgressNotification(progress));
			
		}
	}
	
	public static void main(String[] args) {
		LauncherImpl.launchApplication(Main.class, MyPreloader.class, args);
	}

}
