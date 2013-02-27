package ui;

import java.awt.Dimension;

import arena.Arena;
import arena.objects.AbstractShip;
import arena.objects.ships.AsteroidTurretShip;
import arena.objects.ships.PlayerTurretShip;

public class UITest 
{

	public UITest()
	{


		AbstractShip test1 = new AsteroidTurretShip("Yarrrr");
		AbstractShip test2 = new PlayerTurretShip("Yaaarp");

		Arena testGame = new Arena(20, 1, 10);
		testGame.addShipToArena(test1);
		testGame.addShipToArena(test2);
		
		ArenaFrame frame = new ArenaFrame(testGame, new Dimension(650,650));
		
	}


}
