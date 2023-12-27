package a10;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * 
 * @author saoud
 *
 */

public class Example extends JPanel implements ActionListener, MouseListener {

	private static final long serialVersionUID = 1L;
	private Timer timer;
	private ArrayList<Actor> actors; // Plants and zombies all go in here
	private BufferedImage ninjaImage; // Maybe these images should be in those classes, but easy to change here.
	private BufferedImage ninja2Image;
	private static Ninja ninja;
	private static Ninja2 ninja2;
	private BufferedImage monsterImage;
	private BufferedImage monster2Image;
	private BufferedImage monster3Image;
	private BufferedImage monster4Image;
	private Monster monster;
	private Monster2 monster2;
	private Monster3 monster3;
	private Monster4 monster4;
	private int numRows;
	private int numCols;
	private int cellSize;
	private int x;
	private int y;
	private long points;
	private int counter;
	private static int checkButton;
	private static JLabel label;
	private static JButton button1;
	private static JButton button2;
	private Random rand = new Random();

	/**
	 * Setup the basic info for the example
	 */
	public Example() {
		super();
		
		counter = 0;
		points = 0;
		checkButton = 0;

		// Define some quantities of the scene
		numRows = 5;
		numCols = 7;
		cellSize = 75;
		setPreferredSize(new Dimension(50 + numCols * cellSize, 50 + numRows * cellSize));

		// adding action listeners
		addMouseListener(this);
		button1.addActionListener(this);
		button2.addActionListener(this);

		// Store all the plants and zombies in here.
		actors = new ArrayList<Actor>();

		// Load images
		try {
			ninjaImage = ImageIO.read(new File("src/a10/Ninja.png"));
			ninja2Image = ImageIO.read(new File("src/a10/Ninja2.png"));
			monsterImage = ImageIO.read(new File("src/a10/Monster.png"));
			monster2Image = ImageIO.read(new File("src/a10/Monster2.png"));
			monster3Image = ImageIO.read(new File("src/a10/Monster3.png"));
			monster4Image = ImageIO.read(new File("src/a10/Monster4.png"));
		} catch (IOException e) {
			System.out.println("A file was not found");
			System.exit(0);
		}

		// The timer updates the game each time it goes.
		// Get the javax.swing Timer, not from util.
		timer = new Timer(40, this);
		timer.start();

	}

	/***
	 * Implement the paint method to draw the plants
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (Actor actor : actors) {
			actor.draw(g, 0);
			actor.drawHealthBar(g);
		}
	}

	/**
	 * 
	 * This is triggered by the timer. It is the game loop of this test.
	 * 
	 * @param e
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		// This method is getting a little long, but it is mostly loop code
		// Increment their cooldowns and reset collision status
		for (Actor actor : actors) {
			actor.update();
		}

		// Try to attack
		for (Actor actor : actors) {
			for (Actor other : actors) {
				actor.attack(other);
			}
		}

		// Remove plants and zombies with low health
		ArrayList<Actor> nextTurnActors = new ArrayList<>();
		for (Actor actor : actors) {
			if (actor.isAlive())
				nextTurnActors.add(actor);
			else
				actor.removeAction(actors); // any special effect or whatever on removal
		}
		actors = nextTurnActors;

		// Check for collisions between zombies and plants and set collision status
		for (Actor actor : actors) {
			for (Actor other : actors) {
				actor.setCollisionStatus(other);
			}
		}

		// Move the actors.
		for (Actor actor : actors) {
			actor.move(); // for Zombie, only moves if not colliding.
		}

		// choosing plant type
		if (e.getSource() == button1) {
			checkButton = 1;
		}
		if (e.getSource() == button2) {
			checkButton = 2;
		}

		// adding random zombies
		addMonster();
		addMonster2();

		// adding recourses and updating the label
		counter = counter + 1;
		if (counter % 40 == 0) {
			points = points + 5;
		}
		label.setText("Points = " + points);

		// making the game harded by adding stronger faster zombies after about 30
		// seconds
		if (counter / 900 >= 1) {
			addMonster3();
			addMonster4();
		}

		// Stopping the game
		for (Actor actor : actors) {
			if (actor.getPosition().getX() == 0) {
				timer.stop();
			}
		}
		// Redraw the new scene
		repaint();
	}

	/**
	 * this method resets the checkButton subtracts the number of points need if
	 * available then adds a Ninja at a specific location
	 */
	public void addNinja() {

		if (points >= 10) {
			checkButton = 0;
			points = points - 10;
			ninja = new Ninja(new Point2D.Double(((x / 75) * 75) + 1, ((y / 75) * 75) + 1),
					new Point2D.Double(ninjaImage.getWidth(), ninjaImage.getHeight()), ninjaImage, 100, 5, 5);
			actors.add(ninja);
		}

	}

	/**
	 * this method resets the checkButton subtracts the number of points need if
	 * available then adds a Ninja2 at a specific location
	 */
	public void addNinja2() {

		if (points >= 20) {
			checkButton = 0;
			points = points - 20;
			ninja2 = new Ninja2(new Point2D.Double(((x / 75) * 75) + 1, ((y / 75) * 75) + 1),
					new Point2D.Double(ninja2Image.getWidth(), ninja2Image.getHeight()), ninja2Image);
			actors.add(ninja2);
		}
	}

	/**
	 * This method adds a Monster at a random right column
	 */
	public void addMonster() {
		if (rand.nextInt(1000) > 990) {
			int row = rand.nextInt(6);
			int y = row * 50;
			monster = new Monster(new Point2D.Double(500.0, (y / 75) * 75),
					new Point2D.Double(monsterImage.getWidth(), monsterImage.getHeight()), monsterImage, 100, 50, -2,
					10);
			actors.add(monster);
		}

	}

	/**
	 * This method adds a Monster2 at a random right column
	 */
	public void addMonster2() {

		if (rand.nextInt(1000) > 995) {
			int row = rand.nextInt(6);
			int y = row * 50;
			monster2 = new Monster2(new Point2D.Double(500.0, (y / 75) * 75), monster2Image);
			actors.add(monster2);
		}
	}

	/**
	 * This method adds a Monster3 at a random right column
	 */
	public void addMonster3() {
		if (rand.nextInt(1000) > 990) {
			int row = rand.nextInt(6);
			int y = row * 50;
			monster3 = new Monster3(new Point2D.Double(500.0, (y / 75) * 75), monster3Image);
			actors.add(monster3);
		}

	}

	/**
	 * This method adds a Monster4 at a random right column
	 * 
	 */
	public void addMonster4() {
		if (rand.nextInt(1000) > 995) {
			int row = rand.nextInt(6);
			int y = row * 50;
			monster4 = new Monster4(new Point2D.Double(500.0, (y / 75) * 75), monster4Image);
			actors.add(monster4);
		}

	}

	/**
	 * Make the game and run it
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {

				JFrame app = new JFrame("Ninjas VS Monsters");

				button1 = new JButton();
				button1.setText("Ninja 1 cost : 10 points");

				button2 = new JButton();
				button2.setText("Ninja 2 cost : 20 points");

				label = new JLabel();

				app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				Example panel = new Example();

				panel.add(label);
				panel.add(button1);
				panel.add(button2);

				app.setContentPane(panel);
				app.pack();
				app.setVisible(true);

			}
		});
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		x = e.getX();
		y = e.getY();

		boolean canPlace = true;
		for (Actor actor : actors) {
			if (actor.isCollidingPoint(new Point2D.Double(((x / 75) * 75) + 1, ((y / 75) * 75) + 1))) {
				canPlace = false;
				break;
			}
		}
		if (canPlace) {
			if (checkButton == 1)
				addNinja();
			else if (checkButton == 2)
				addNinja2();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
