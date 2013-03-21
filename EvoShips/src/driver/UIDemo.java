package driver;

import java.awt.Dimension;

import ui.ArenaFrame;
import arena.Arena;
import arena.objects.AbstractShip;
import arena.objects.ships.KamakazieShip;
import arena.objects.ships.ShipTurret;
import arena.objects.ships.SmartHunter;
import arena.objects.ships.SmartTurret;

public class UIDemo 
{
	public UIDemo() 
	{
		AbstractShip asteroidHunter = new SmartHunter();
		AbstractShip kamakazieShip = new KamakazieShip();
		AbstractShip turretShip = new ShipTurret();
		
		Arena demoArena = new Arena(20,1,5);
		
		demoArena.addShipToArena(asteroidHunter);
		//demoArena.addShipToArena(kamakazieShip);
		//demoArena.addShipToArena(turretShip);
		
		ArenaFrame frame = new ArenaFrame(demoArena, new Dimension(500,500));
	}
	
	public static void main(String[] args)
	{
		new UIDemo();
	}

}
