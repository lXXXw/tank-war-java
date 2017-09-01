
import java.util.ArrayList;
import java.util.Random;

import com.sun.org.apache.xpath.internal.functions.FuncFalse;

import javafx.concurrent.Task;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class Tank extends Fighter {
	
	private static Random random = new Random();
	private double oldX, oldY;
//	public Circle tankBody;
	public Line tube;
//	public Image image = new Image("tankPNG//goodTank_U.png");
	public ArrayList<Image> images = new ArrayList<Image>();
	public ImageView iView = new ImageView();
	
	private static final int TUBELENGTH = 25;
	private int step = random.nextInt(12) + 3;
	
	private int BADFIREPOWER = 7;
	private int MODIFYDISTANCE = 25;
	
	
	public Tank(int x, int y, boolean good, TankWar tc) {
		circleX = x;
		circleY = y;
		this.XSPEED = 5;
		this.YSPEED = 5;
		this.good = good;
//		if(good) {
//			tankBody = new Circle(x,y,15, Color.rgb(12, 127, 224));
//		} else {
//			tankBody = new Circle(x,y,15, Color.rgb(12, 224, 100));
//		}
		
		if(good) {
			for(int i = 0; i<8;i++) {
				images.add(new Image("tankPNG//goodTank_"+Integer.toString(i)+".png"));
			}
		} else {
			for(int i = 0; i<8;i++) {
				images.add(new Image("tankPNG//badTank_"+Integer.toString(i)+".png"));
			}
		}
		
		tube = new Line(circleX, circleY, circleX, circleY+TUBELENGTH);
		tube.setStroke(Color.RED);
		//this.getChildren().add(tankBody);
		//this.getChildren().add(tube);
		iView.setImage(images.get(1));
		this.getChildren().add(iView);
		
		iView.setX(circleX - MODIFYDISTANCE);
		iView.setY(circleY - MODIFYDISTANCE);
		
		this.tc = tc;
		//this.tc.tanks.add(this);
		tc.warField.getChildren().add(this);
	}


	public void fire() {
		Missile missile = new Missile(tube.getEndX(), tube.getEndY(), tubeDir, good,tc);
//		tc.warField.getChildren().add(missile);
		//tc.missiles.add(this);
	}
	
	
	private void superFire() {
		Direction[] directions = Direction.values();
		for(int i = 0; i<8 ;i++) {
			Missile missile = new Missile(tube.getEndX(), tube.getEndY(), directions[i], good,tc);
		}
	}
	
	public void keyPressed(KeyEvent event) {
		
		KeyCode code = event.getCode();
		
		if(code == KeyCode.UP) {
			UP = true;
		} 
		if(code == KeyCode.DOWN) {
			DOWN = true;
		} //else DOWN = false;
		if(code == KeyCode.LEFT) {
			LEFT = true;
		} //else LEFT = false;
		if(code == KeyCode.RIGHT) {
			RIGHT = true;
		} //else RIGHT = false;
		
		getDirection();
		//move(direction, running);
	}
	
	public void keyReleased(KeyEvent event) {
		KeyCode code = event.getCode();
		if(code == KeyCode.SPACE) {
			fire();
		} else if(code == KeyCode.S) {
			superFire();
		}
		
		if(code == KeyCode.UP) {
			UP = false;
		}
		if(code == KeyCode.DOWN) {
			DOWN = false;
		}
		if(code == KeyCode.LEFT) {
			LEFT = false;
		}
		if(code == KeyCode.RIGHT) {
			RIGHT = false;
		}
		
		getDirection();
		
	
		
		
	}
	
	private void getDirection() {
		if(UP && !DOWN && !LEFT && !RIGHT) {
			direction = Direction.U;
//			iView.setImage(images.get(1));
		}
		else if(UP && !DOWN && LEFT && !RIGHT) {
			direction = Direction.UL;
//			iView.setImage(images.get(0));
		}
		else if(UP && !DOWN && !LEFT && RIGHT) {
			direction = Direction.UR;
//			iView.setImage(images.get(2));
		}
		else if(!UP && DOWN && !LEFT && !RIGHT) {
			direction = Direction.D;
//			iView.setImage(images.get(5));
		}
		else if(!UP && DOWN && LEFT && !RIGHT) {
			direction = Direction.DL;
//			iView.setImage(images.get(6));
		}
		else if(!UP && DOWN && !LEFT && RIGHT) {
			direction = Direction. DR;
//			iView.setImage(images.get(4));
		}
		else if(!UP && !DOWN && LEFT && !RIGHT) {
			direction = Direction.L;
//			iView.setImage(images.get(7));
		}
		else if(!UP && !DOWN && !LEFT && RIGHT) direction = Direction.R;
		else direction = Direction.STOP;
	}
	
	public void tubeMove() {
		tube.setStartX(circleX);
		tube.setStartY(circleY);
		if(direction != Direction.STOP) {
			tubeDir = direction;
		}
		switch(tubeDir) {
		case U: {
			tube.setEndX(circleX);
			tube.setEndY(circleY - TUBELENGTH);
//			iView.setImage(images.get(1));
			break;
		}
		
		case D: {
			tube.setEndX(circleX);
			tube.setEndY(circleY + TUBELENGTH);
//			iView.setImage(images.get(5));
			break;
		}
		
		case L: {
			tube.setEndX(circleX - TUBELENGTH);
			tube.setEndY(circleY);
//			iView.setImage(images.get(7));
			break;
		}
		
		case R: {
			tube.setEndX(circleX + TUBELENGTH);
			tube.setEndY(circleY);
//			iView.setImage(images.get(3));
			break;
		}
		
		case UL: {
			tube.setEndX(circleX - TUBELENGTH/1.4);
			tube.setEndY(circleY - TUBELENGTH/1.4);
//			iView.setImage(images.get(0));
			break;
		}
		
		case UR: {
			tube.setEndX(circleX + TUBELENGTH/1.4);
			tube.setEndY(circleY - TUBELENGTH/1.4);
//			iView.setImage(images.get(2));
			break;
		}
		
		case DL: {
			tube.setEndX(circleX - TUBELENGTH/1.4);
			tube.setEndY(circleY + TUBELENGTH/1.4);
//			iView.setImage(images.get(6));
			break;
		}
		
		case DR: {
			tube.setEndX(circleX + TUBELENGTH/1.4);
			tube.setEndY(circleY + TUBELENGTH/1.4);
//			iView.setImage(images.get(4));
			break;
		}
		}
	}
	
	public void move() {
		
		if(!good) {
			Direction[] directions = Direction.values();
			if(step == 0) {
				step = random.nextInt(12) + 3;
				int n = random.nextInt(directions.length);
				direction = directions[n];
			}
			step --;
			if(random.nextInt(40)<BADFIREPOWER ) {
				fire();
			}
		}
		
		
		
		tubeMove();
		
		switch(direction) {
			case U: {
				circleY = circleY - YSPEED;
				
//				tankBody.setCenterY(circleY);
//				iView.setX(circleX);
				iView.setY(circleY - MODIFYDISTANCE);
				iView.setImage(images.get(1));
				break;
			}
			
			case D: {
				circleY = circleY + YSPEED;
//				tankBody.setCenterY(circleY);
				
//				iView.setX(circleX);
				iView.setY(circleY - MODIFYDISTANCE);
				iView.setImage(images.get(5));
				break;
			}
			
			case L: {
				circleX = circleX - XSPEED;
//				tankBody.setCenterX(circleX);
				
				iView.setX(circleX - MODIFYDISTANCE);
				iView.setImage(images.get(7));
//				iView.setY(circleY);
				break;
			}
			
			case R: {
				circleX = circleX + XSPEED;
//				tankBody.setCenterX(circleX);
				
				iView.setX(circleX - MODIFYDISTANCE);
				iView.setImage(images.get(3));
//				iView.setY(circleY);
				break;
			}
			
			case UL: {
				circleX = circleX - XSPEED;
//				tankBody.setCenterX(circleX);
				
				circleY = circleY - YSPEED;
//				tankBody.setCenterY(circleY);
				
				iView.setX(circleX - MODIFYDISTANCE);
				iView.setY(circleY - MODIFYDISTANCE);
				iView.setImage(images.get(0));
				break;
			}
			
			case UR: {
				circleX = circleX + XSPEED;
//				tankBody.setCenterX(circleX);
				
				circleY = circleY - YSPEED;
//				tankBody.setCenterY(circleY);
				
				iView.setX(circleX - MODIFYDISTANCE);
				iView.setY(circleY - MODIFYDISTANCE);
				iView.setImage(images.get(2));
				break;
			}
			
			case DL: {
				circleX = circleX - XSPEED;
//				tankBody.setCenterX(circleX);
				
				circleY = circleY + YSPEED;
//				tankBody.setCenterY(circleY);
				
				iView.setX(circleX - MODIFYDISTANCE);
				iView.setY(circleY - MODIFYDISTANCE);
				iView.setImage(images.get(6));
				break;
			}
			
			case DR: {
				circleX = circleX + XSPEED;
//				tankBody.setCenterX(circleX);
				
				circleY = circleY + YSPEED;
//				tankBody.setCenterY(circleY);
				
				iView.setX(circleX - MODIFYDISTANCE);
				iView.setY(circleY - MODIFYDISTANCE);
				iView.setImage(images.get(4));
				break;
			}
			
			default: break;
		
		}
		if(circleX < 0) circleX = 0;
		if(circleX > 800) circleX = 800;
		if(circleY < 0) circleY = 0;
		if(circleY > 600) circleY = 600;
		
		if(collidesWithTanks(tc.goodTanks) || collidesWithWalls(tc.walls) ||
				collidesWithTanks(tc.badTanks)) {
			direction = Direction.STOP;
			stay();
		}
		
		oldX = this.circleX;
		oldY = this.circleY;
		
	}


	private void stay() {
		circleX = oldX;
		circleY = oldY;
	}
	
	private boolean collidesWithTank(Tank tank) {
		if(this.iView.intersects(tank.circleX-MODIFYDISTANCE, tank.circleY-MODIFYDISTANCE, 50, 50) && this!=tank) {
			return true;
		}
		return false;
	}
	
	private boolean collidesWithTanks(ArrayList<Tank> tanks) {
		for(Tank tank: tanks) {
			if(collidesWithTank(tank)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean collidesWithWalls(ArrayList<Wall> walls) {
		for(Wall wall: walls) {
			if(this.iView.intersects(wall.circleX, wall.circleY,
					wall.getWidth(), wall.getHeight())) {
//				System.out.println("collide With Wall A !");
				return true;
			}
		}
		return false;
	}
}
