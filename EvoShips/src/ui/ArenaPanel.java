package ui;

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
		setSize(panelSize);
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

	}





	@Override
	public void update(Observable arena, Object arg1) 
	{
		//When the arenapanel updates, it redraws.	
		arenaObjects = ((Arena)arena).getArenaObjects();
		repaint();
	}







}
