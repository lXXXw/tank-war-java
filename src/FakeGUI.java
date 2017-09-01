import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class FakeGUI {
	private Group root = new Group();
	private Scene guiScene = new Scene(root,300,400,Color.PURPLE);
	private Stage stage;
	
	public FakeGUI(TankWarClient twc,Stage stage) {
		this.stage = stage;
		guiScene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {				
				if(event.getCode() == KeyCode.A) {
					TankWar tWar = new TankWar(1, 5, twc, stage);
					Scene gameScene = tWar.getScene();
					stage.setScene(gameScene);
				}
				
				  
			}
			
		});
	}
	
	public Scene getScene() {
		return guiScene;
	}
	
}	
