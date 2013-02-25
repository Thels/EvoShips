import java.util.ArrayList;

import arena.Arena;
import arena.BatchArena;
import arena.objects.AbstractShip;
import arena.objects.ships.DerpShip;
import arena.objects.ships.PlayerTurretShip;


public class TestClass 
{

	public TestClass() 
	{
		int GAME_COUNT = 250;
		DerpShip test1 = new DerpShip("DerpShip #1");
		PlayerTurretShip test2 = new PlayerTurretShip("TestShip #2");
		PlayerTurretShip test3 = new PlayerTurretShip("TestShip #3");
		PlayerTurretShip test4 = new PlayerTurretShip("TestShip #4");
		
		ArrayList<AbstractShip> ships = new ArrayList<AbstractShip>();
		ships.add(test1);
		ships.add(test2);
		ships.add(test3);
		ships.add(test4);
		
		BatchArena batchTest = new BatchArena(ships, GAME_COUNT, 15, 1);
		batchTest.startBatch();
		
		
	}

	
	public static void main(String[] args)
	{
		new TestClass();
	}

}
