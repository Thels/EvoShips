package driver;

import java.awt.Dimension;

import ui.ArenaFrame;
import arena.Arena;
import arena.objects.AbstractShip;
import arena.objects.ships.KamakazieShip;
import arena.objects.ships.Looper;
import arena.objects.ships.SmartHunter;

public class UIDemo 
{
	public UIDemo() 
	{
		AbstractShip asteroidHunter = new SmartHunter();
		AbstractShip kamakazieShip = new KamakazieShip();
		AbstractShip turretShip = new Looper();
		
		Arena demoArena = new Arena(2,1,5);
		
//		demoArena.addShipToArena(asteroidHunter);
//		demoArena.addShipToArena(kamakazieShip);
		demoArena.addShipToArena(turretShip);
		
		ArenaFrame frame = new ArenaFrame(demoArena, new Dimension(500,500));
	}
	
	public static void main(String[] args)
	{
		new UIDemo();
	}

}
