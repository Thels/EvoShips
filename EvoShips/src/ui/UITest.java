package ui;

import java.awt.Dimension;

import arena.Arena;
import arena.objects.AbstractShip;
import arena.objects.ships.AsteroidHunter;
import arena.objects.ships.AsteroidTurret;
import arena.objects.ships.KamakazieShip;
import arena.objects.ships.Turret;

public class UITest 
{

	public UITest()
	{


		AbstractShip test1 = new AsteroidHunter();
		AbstractShip test2 = new AsteroidHunter();
		AbstractShip test3 = new KamakazieShip();

		Arena testGame = new Arena(20, 50, 5);
		testGame.addShipToArena(test1);
		testGame.addShipToArena(test2);
		testGame.addShipToArena(test3);
		
		ArenaFrame frame = new ArenaFrame(testGame, new Dimension(650,650));
		
	}
	
	public static void main(String[] args)
	{
		new UITest();
	}


}
