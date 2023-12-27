package a10;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Monster2 extends Monster {
	private static final int Health = 300;
	private static final int COOLDOWN = 200;
	private static final double SPEED = -0.7;
	private static final int ATTACKDAMAGE = 10;

	public Monster2(Point2D.Double position, BufferedImage image) {
		super(position, new Point2D.Double(image.getWidth(), image.getHeight()), image, Health, COOLDOWN, SPEED,
				ATTACKDAMAGE);
	}

	@Override
	public void attack(Actor other) {
		if (this != other && this.isCollidingOther(other)) {
			if (this.readyForAction()) {
				other.changeHealth(-attackDamage);
				this.changeHealth(40);
				this.resetCoolDown();
			}
		}
	}

}
