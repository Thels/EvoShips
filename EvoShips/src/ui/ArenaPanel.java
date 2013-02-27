package ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import arena.Arena;
import arena.objects.AbstractObject;
import arena.objects.AbstractShip;
import arena.objects.objects.Asteroid;
import arena.objects.objects.Bullet;

/**
 * The arena panel is used whenever the arena has to be drawn for single game-purposes.
 * 
 * 
 * This class is an observer as it will recieve notifications of when to update from the arena, which is an observable.
 * @author Ross
 *
 */
public class ArenaPanel extends JPanel implements Observer
{
	private ArrayList<AbstractObject> arenaObjects;
	private int displayScale;
	/**
	 * Creates a new arena
	 * @param currentArena The Arena the game has to be based upon.
	 * @param panelSize The dimensions of the panel to be created.
	 */
	public ArenaPanel(Arena currentArena, Dimension panelSize)
	{
		currentArena.addObserver(this);
		setSize(panelSize);
		setPreferredSize(panelSize);
		setMinimumSize(panelSize);
		arenaObjects = new ArrayList<AbstractObject>();
		displayScale = panelSize.width;
	}

	@Override
	protected void paintComponent(Graphics g) 
	{
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

		//... Paint background.
		g2.setColor(Color.BLACK);
		g2.fillRect(0, 0, getWidth(), getHeight());

		for(AbstractObject obj : arenaObjects)
		{
			switch(obj.getObjectType())
			{
			case OBJ_SHIP: drawShip((AbstractShip) obj,g2); break;
			case OBJ_BULLET: drawBullet((Bullet) obj, g2); break;
			case OBJ_ASTEROID: drawAsteroid((Asteroid) obj, g2); break;
			default: continue;
			
			}
		}
	}

	
	private void drawBullet(Bullet bulllet, Graphics2D g2)
	{
		g2.setColor(Color.WHITE);
		g2.setStroke(new BasicStroke(1.5F));
		g2.drawOval((int)((bulllet.getObjectPosition().x-0.003125)*displayScale),
				(int)((bulllet.getObjectPosition().y-0.003125)*displayScale),
				(int)(0.00625*displayScale),
				(int)(0.00625*displayScale));
	}
	
	//TODO, lots ofjavadoc.
	
	private void drawAsteroid(Asteroid asteroid, Graphics2D g2)
	{
		System.out.println("{Drawing");
		int complexity = asteroid.getAsteroidComplexity();	
		int[] xPoints = new int[complexity];
		int[] yPoints = new int[complexity];
		
		double angle = 0;
		
		for(int i = 0 ; i < complexity; i++)
		{
			xPoints[i] = (int)(displayScale * (asteroid.getObjectPosition().x + (asteroid.ASTEROID_SIZE/2 * Math.cos(Math.toRadians(angle)))));
			yPoints[i] = (int)(displayScale * (asteroid.getObjectPosition().y + (asteroid.ASTEROID_SIZE/2 * Math.sin(Math.toRadians(angle)))));
			angle += asteroid.getTheta();
		}
		
		g2.setColor(Color.LIGHT_GRAY);
		g2.setStroke(new BasicStroke(2F));
		g2.drawPolygon(xPoints, yPoints, complexity);
		
	}




	private void drawShip(AbstractShip obj, Graphics2D g2) 
	{
		//First of all,work out the ships points.
		int[] xPoints = new int[3];
		int[] yPoints = new int[3];
		
		double x = obj.getObjectPosition().x;
		double y = obj.getObjectPosition().y;
		double dir = obj.getDirection();
		
		xPoints[0] = (int)((x +  0.025 * Math.sin(Math.toRadians(dir)))*displayScale);
		yPoints[0] = (int)((y +  0.025 * Math.cos(Math.toRadians(dir)))*displayScale);

		xPoints[1] = (int)((x + (Math.sqrt(Math.pow(0.025, 2)*2)* Math.sin(Math.toRadians(dir+180.0+45))))*displayScale);
		yPoints[1] = (int)((y + (Math.sqrt(Math.pow(0.025, 2)*2)* Math.cos(Math.toRadians(dir+180.0+45))))*displayScale);

		xPoints[2] = (int)((x + (Math.sqrt(Math.pow(0.025, 2)*2)* Math.sin(Math.toRadians(dir+180.0-45))))*displayScale);
		yPoints[2] = (int)((y + (Math.sqrt(Math.pow(0.025, 2)*2)* Math.cos(Math.toRadians(dir+180.0-45))))*displayScale);
		
		g2.setColor(Color.GREEN);
		g2.setStroke(new BasicStroke(1.5F));
		g2.drawPolygon(xPoints, yPoints, 3);
		g2.setColor(Color.WHITE);
	}

	@Override
	public void update(Observable arena, Object arg1) 
	{
		//When the arenapanel updates, it redraws.	
		arenaObjects = ((ArrayList<AbstractObject>)arg1);
		repaint();
	}







}
