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
	}

	@Override
	protected void paintComponent(Graphics g) 
	{
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

		//... Paint background.
		g2.setColor(Color.BLACK);
		g2.fillRect(0, 0, getWidth(), getHeight());
		//TODO Draw gameobjects.
		
		for(AbstractObject obj : arenaObjects)
		{
			switch(obj.getObjectType())
			{
			case OBJ_SHIP: drawShip((AbstractShip) obj,g2); break;
			default: continue;
			
			}
		}

		System.out.println("REFRESHING");
	}





	private void drawShip(AbstractShip obj, Graphics2D g2) 
	{
		//First of all,work out the ships points.
		int[] xPoints = new int[3];
		int[] yPoints = new int[3];
		
		double x = obj.getObjectPosition().x;
		double y = obj.getObjectPosition().y;
		double dir = obj.getDirection();
		
		xPoints[0] = (int)(x +  0.025 * Math.sin(Math.toRadians(dir)));
		yPoints[0] = (int)(y +  0.025 * Math.cos(Math.toRadians(dir)));

		xPoints[1] = (int)(x + (Math.sqrt(Math.pow(0.025, 2)*2)* Math.sin(Math.toRadians(dir+180.0+45))));
		yPoints[1] = (int)(y + (Math.sqrt(Math.pow(0.025, 2)*2)* Math.cos(Math.toRadians(dir+180.0+45))));

		xPoints[2] = (int)(x + (Math.sqrt(Math.pow(0.025, 2)*2)* Math.sin(Math.toRadians(dir+180.0-45))));
		yPoints[2] = (int)(y + (Math.sqrt(Math.pow(0.025, 2)*2)* Math.cos(Math.toRadians(dir+180.0-45))));
		
		
		g2.setColor(Color.GREEN);
		g2.setStroke(new BasicStroke(1.5F));
		g2.drawPolygon(xPoints, yPoints, 3);
		
	}

	@Override
	public void update(Observable arena, Object arg1) 
	{
		//When the arenapanel updates, it redraws.	
		arenaObjects = ((Arena)arena).getArenaObjects();
		repaint();
	}







}
