import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TankWarClient extends Application{
	public Scene gameScene;
	public Scene GUIScene;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
//		TankWar tWar = new TankWar(1, 5, this, primaryStage);
//		gameScene = tWar.getScene();
		
		FakeGUI fg = new FakeGUI(this, primaryStage);
		GUIScene = fg.getScene();
		
		primaryStage.setScene(GUIScene);
		primaryStage.show();
	}
	
}
