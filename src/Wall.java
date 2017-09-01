import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Wall extends Fighter{
	private Rectangle wallBody;
	private Color wallColor = Color.BROWN;
	private double height;
	private double width;
	
	public Wall(double x, double y, double width, double height, TankWar tc) {
		this.circleX = x;
		this.circleY = y;
		this.width = width;
		this.height = height;
		wallBody = new Rectangle(x,y, width, height);
		wallBody.setFill(wallColor);
		
//		wallBody.setStroke(wallColor);
		this.tc = tc;
		this.getChildren().add(wallBody);
		tc.warField.getChildren().add(this);
	}
	
	public double getWidth() {
		return width;
	}
	
	public double getHeight() {
		return height;
	}
	
}
