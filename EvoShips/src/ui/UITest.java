package ui;

import java.awt.Dimension;

import arena.Arena;
import arena.objects.AbstractShip;
import arena.objects.ships.AsteroidHunter;
import arena.objects.ships.AsteroidTurretShip;
import arena.objects.ships.PlayerTurretShip;

public class UITest 
{

	public UITest()
	{


		AbstractShip test1 = new AsteroidHunter("Asteroid Hunter");
		AbstractShip test2 = new AsteroidHunter("Yaaarp");

		Arena testGame = new Arena(20, 50, 1);
		testGame.addShipToArena(test1);
		testGame.addShipToArena(test2);
		
		ArenaFrame frame = new ArenaFrame(testGame, new Dimension(650,650));
		
	}


}
