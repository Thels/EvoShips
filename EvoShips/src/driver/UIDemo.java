package driver;

import java.awt.Dimension;

import ui.ArenaFrame;
import arena.Arena;
import arena.objects.AbstractShip;
import arena.objects.ships.AsteroidHunter;
import arena.objects.ships.DerpShip;
import arena.objects.ships.KamakazieShip;
import arena.objects.ships.Turret;

public class UIDemo 
{
	public UIDemo() 
	{
		AbstractShip asteroidHunter = new AsteroidHunter();
		AbstractShip kamakazieShip = new KamakazieShip();
		AbstractShip derpShip = new DerpShip();
		AbstractShip turretShip = new Turret();
		
		Arena demoArena = new Arena(20,1,5);
		
		demoArena.addShipToArena(asteroidHunter);
		//demoArena.addShipToArena(kamakazieShip);
		//demoArena.addShipToArena(derpShip);
		//demoArena.addShipToArena(turretShip);
		
		ArenaFrame frame = new ArenaFrame(demoArena, new Dimension(500,500));
	}
	
	public static void main(String[] args)
	{
		new UIDemo();
	}

}
