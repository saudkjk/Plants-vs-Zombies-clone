package a10;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Monster3 extends Monster {
	private static final int Health = 300;
	private static final int COOLDOWN = 50;
	private static final double SPEED = -4;
	private static final int ATTACKDAMAGE = 20;
	
	public Monster3(Point2D.Double position, BufferedImage image) {
		super(position, new Point2D.Double(image.getWidth(), image.getHeight()), image, Health, COOLDOWN, SPEED, ATTACKDAMAGE);
	}

}
