package a10;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Monster4 extends Monster {
	private static final int Health = 50;
	private static final int COOLDOWN = 10;
	private static final double SPEED = -5;
	private static final int ATTACKDAMAGE = 30;
	
	public Monster4(Point2D.Double position, BufferedImage image) {
		super(position, new Point2D.Double(image.getWidth(), image.getHeight()), image, Health, COOLDOWN, SPEED, ATTACKDAMAGE);
	}

}
