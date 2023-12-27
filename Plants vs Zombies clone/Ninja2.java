package a10;

import java.awt.geom.Point2D.Double;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Ninja2 extends Ninja {

	private static final int HEALTH = 200;
	private static final int COOLDOWN = 20;
	private static final int ATTACKDAMAGE = 15;

	public Ninja2(Double startingPosition, Double initHitbox, BufferedImage img) {
		super(startingPosition, initHitbox, img, HEALTH, COOLDOWN, ATTACKDAMAGE);

	}

	public void removeAction(ArrayList<Actor> others) {

		for (Actor allActors : others) {
			if (allActors instanceof Monster) {
				Monster s = (Monster) allActors;
				s.changeHealth(-1000);
			}
		}

	}
}
