import java.util.ArrayList;
import java.util.Iterator;

import com.sun.corba.se.impl.orbutil.graph.Node;
import com.sun.jmx.snmp.tasks.ThreadService;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Missile extends Fighter {
	
	//Circle missileBody;
	ImageView iView = new ImageView();
	private double MODIFYDISTANCE = 12;
	
	
	public Missile (double d, double e, Tank.Direction direction, boolean good, TankWar tc){
		circleX = d;
		circleY = e;
		this.XSPEED = 15;
		this.YSPEED = 15;
		this.good = good;
		this.tc = tc;
//		if(good) {
//			missileBody = new Circle(circleX, circleY, 3, Color.RED);
//		} else {
//			missileBody = new Circle(circleX, circleY, 3, Color.YELLOW);
//		}
		Image image;
		if(good) {
			image = new Image("missilePNG//goodMissile_"+direction.toString()+".png");
			
		} else {
			image = new Image("missilePNG//badMissile_"+direction.toString()+".png");
		}
		iView.setImage(image);
		iView.setX(circleX - MODIFYDISTANCE);
		iView.setY(circleY - MODIFYDISTANCE);
		this.direction = direction;
		//this.getChildren().add(missileBody);
		this.getChildren().add(iView);
		tc.missiles.add(this);
		//tc.warField.getChildren().add(this);
	}
	
	public boolean hitWalls(ArrayList<Wall> walls) {
		for(Wall wall: walls) {
			if(this.iView.intersects(wall.circleX, wall.circleY,
					wall.getWidth(), wall.getHeight())) {
				return true;
			}
		}
		return false;
	}
	
	public boolean hitTank(Tank tank) {
		if(tank.iView.intersects(this.circleX - 7, this.circleY-7,14,14)
				&& (this.good)!= tank.good) {
			
			return true;
		}
		
		return false;
	}
	
	public boolean hitTanks(ArrayList<Tank> t) {
		for(Tank tank:t) {
			if (hitTank(tank)) {
				tank.setLive(false);
				return true;
			}
		}
		return false;
	}
	
	public void move() {
		if(hitTanks(tc.goodTanks) || hitTanks(tc.badTanks)) {
			this.live = false;
			Explode explode = new Explode(this.circleX, this.circleY);
			tc.explodes.add(explode);
		}
		
		if(hitWalls(tc.walls)) {
			this.live = false;
		}
		
		if(circleX > 810 || circleX < -10
				|| circleY > 610 || circleY < -10) {
			this.live = false;
		} 
		
		switch(direction) {
				case U: {
					circleY = circleY - YSPEED;
//					missileBody.setCenterY(circleY);
					break;
				}
				
				case D: {
					circleY = circleY + YSPEED;
//					missileBody.setCenterY(circleY);
					break;
				}
				
				case L: {
					circleX = circleX - XSPEED;
//					missileBody.setCenterX(circleX);
					break;
				}
				
				case R: {
					circleX = circleX + XSPEED;
//					missileBody.setCenterX(circleX);
					break;
				}
				
				case UL: {
					circleX = circleX - XSPEED;
//					missileBody.setCenterX(circleX);
					
					circleY = circleY - YSPEED;
//					missileBody.setCenterY(circleY);
					break;
				}
				
				case UR: {
					circleX = circleX + XSPEED;
//					missileBody.setCenterX(circleX);
					
					circleY = circleY - YSPEED;
//					missileBody.setCenterY(circleY);
					break;
				}
				
				case DL: {
					circleX = circleX - XSPEED;
//					missileBody.setCenterX(circleX);
					
					circleY = circleY + YSPEED;
//					missileBody.setCenterY(circleY);
					break;
				}
				
				case DR: {
					circleX = circleX + XSPEED;
//					missileBody.setCenterX(circleX);
					
					circleY = circleY + YSPEED;
//					missileBody.setCenterY(circleY);
					break;
				}
		
		}
		iView.setX(circleX - MODIFYDISTANCE);
		iView.setY(circleY - MODIFYDISTANCE);
	}
	
}
	