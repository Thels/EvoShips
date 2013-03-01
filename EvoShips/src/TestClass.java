import java.util.ArrayList;

import arena.BatchArena;
import arena.objects.AbstractShip;
import arena.objects.ships.AsteroidHunter;
import arena.objects.ships.DerpShip;


public class TestClass 
{

	public TestClass() 
	{
		int GAME_COUNT = 250;
		DerpShip test1 = new DerpShip();
		AsteroidHunter test2 = new AsteroidHunter();
		AsteroidHunter test3 = new AsteroidHunter();
		AsteroidHunter test4 = new AsteroidHunter();
		
		ArrayList<AbstractShip> ships = new ArrayList<AbstractShip>();
		ships.add(test1);
		ships.add(test2);
		ships.add(test3);
		ships.add(test4);
		
		BatchArena batchTest = new BatchArena(ships, GAME_COUNT, 15, 1);
		batchTest.startBatch();
		
		boolean f = false;
		while(!f)
		{
			f = batchTest.isBatchRunning();
		}
		
		for(AbstractShip ship : ships)
			System.out.println(ship.getShipName()+"    : "+ship.getScore());
		
		//UITest test = new UITest();
		
		
	}

	
	public static void main(String[] args)
	{
		new TestClass();
	}

}
