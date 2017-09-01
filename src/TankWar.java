import java.util.ArrayList;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class TankWar {
	private static final int MYBORN_X = 400;
	private static final int MYBORN_Y = 400;
	private static final int BORN_X = 100;
	private static final int BORN_Y = 100;
	private static int ENEMYNUMBER;
	private static int FRIENDNUMBER;
	
	public ArrayList<Tank> badTanks = new ArrayList<Tank>();
	public ArrayList<Tank> goodTanks = new ArrayList<Tank>();
	public ArrayList<Missile> missiles = new ArrayList<Missile>();
	public ArrayList<Explode> explodes = new ArrayList<Explode>();
	public ArrayList<Wall> walls = new ArrayList<Wall>();
	
	private TankWarClient twc;
	private Stage stage;
	private Tank myTank;
	public Group warField = new Group();
	private Scene gameScene;
	
	private boolean running;
	
	MyTask myTask = new MyTask();
	Thread thread = new Thread(myTask);
	
	
	public TankWar(int friendNumber ,int enemyNumber, TankWarClient twc ,Stage primaryStage) {
		this.FRIENDNUMBER = friendNumber;
		this.ENEMYNUMBER = enemyNumber;
		this.twc = twc;
		this.stage = primaryStage;
		//warField.getChildren().clear();
		buildWorld();
		this.gameScene = new Scene(warField, 800, 600, Color.BLACK);
		
		thread.setDaemon(true);
	}
	
	private void cleanWorld() {
		// Clean World
		badTanks.clear();
		goodTanks.clear();
		missiles.clear();
		explodes.clear();
		walls.clear();
		warField.getChildren().clear();
	}
	
	private void buildWorld() {
		cleanWorld();
		
		// Build walls
		Wall wall = new Wall(300, 300, 200, 40, this);
		walls.add(wall);
		createFriends();
		createEnemy();
	}
	
	public void restartGame() {
		buildWorld();
		
		thread.start();
	}
	
	private void createFriends() {
		// My Tank Born
		myTank = new Tank(MYBORN_X, MYBORN_Y, true, this);
		goodTanks.add(myTank);
	}

	private void createEnemy() {
		// Enemy Tank Born
		for(int i = 0; i < ENEMYNUMBER ; i++) {
			badTanks.add(new Tank(BORN_X+(i+1)*60, BORN_Y, false, this));
		}
	}
	
	public Scene getScene() {
//		MyTask myTask = new MyTask();
//		Thread thread = new Thread(myTask);
//		thread.setDaemon(true);
		running = true;
		thread.start();
		
		
		gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {				
				if(event.getCode() == KeyCode.Q) {
					running = false;
					cleanWorld();
					
					stage.setScene(twc.GUIScene);
				}
				
				if(event.getCode() == KeyCode.P) {
					thread.suspend();
				}
				
				if(event.getCode() == KeyCode.C) {
					thread.resume();
				}
				
				
				myTank.keyPressed(event);  
			}
			
		});
		
		gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				myTank.keyReleased(event);
			}
			
		});
		
		return gameScene;
	}
	
	private class MyTask extends Task {
		private int countDown = 0;
		@Override
		protected Object call() throws Exception {
			// TODO Auto-generated method stub
			while(countDown != 7 && running && badTanks.size()!=0) {
				//System.out.println("It's running?");
				//myTank.move();
				Platform.runLater(new Runnable() {
	                 @Override public void run() {
	                	 showFighters();
	                 }
	             });
				if(!myTank.isLive()) {
					countDown ++;
				}
				try {
					Thread.sleep(40);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			if(!running) {
				System.out.println("Player Exit.");
			} else if(myTank.live) {
				System.out.println("You win!");
			} else {
				System.out.println("Game Over!");
			}
			
			return null;
		}
	}
	
	private void showFighters() {
		warField.getChildren().clear();
		for(Wall wall: walls) {
			warField.getChildren().add(wall);
		}
		
		for(int i = 0; i< badTanks.size(); i++) {
			Tank tank = badTanks.get(i);
			if(tank.isLive()) {
				tank.move();
				warField.getChildren().add(tank);
			} else {
				badTanks.remove(i--);
			}
		}
		
		for(int i = 0; i< missiles.size(); i++) {
			Missile missile = missiles.get(i);
			if(missile.isLive()) {
				missile.move();
				warField.getChildren().add(missile);
			}
			else {
				missiles.remove(i--);
			}
			
		}
		
		for(int i = 0; i< explodes.size(); i++) {
			Explode explode = explodes.get(i);
			if(explode.isLive()) {
				explode.move();
				warField.getChildren().add(explode);
			}
			else {
				explodes.remove(i--);
			}
			
		}
		
		if(myTank.isLive()) {
			myTank.move();
			warField.getChildren().add(myTank);
		} else {
			return;
		}
	}
	
}
