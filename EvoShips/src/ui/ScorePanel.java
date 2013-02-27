package ui;

import java.util.ArrayList;

import javax.swing.JPanel;

import arena.objects.AbstractShip;

public class ScorePanel extends JPanel 
{

	public ScorePanel(ArrayList<AbstractShip> initialShips) 
	{
		for(AbstractShip ship : initialShips)
			System.out.println(ship.getScore());
	}


}
