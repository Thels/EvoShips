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


		AbstractShip test1 = new KamakazieShip();
		AbstractShip test2 = new AsteroidHunter();

		Arena testGame = new Arena(20, 50, 1);
		testGame.addShipToArena(test1);
		testGame.addShipToArena(test2);
		
		ArenaFrame frame = new ArenaFrame(testGame, new Dimension(650,650));
		
	}
	
	public static void main(String[] args)
	{
		new UITest();
	}


}
