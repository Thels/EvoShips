import java.util.ArrayList;

import arena.BatchArena;
import arena.objects.AbstractShip;
import arena.objects.ships.TestShip;


public class TestClass 
{

	public TestClass() 
	{
		int GAME_COUNT = 100000;
		TestShip test1 = new TestShip("TestShip #1");
		TestShip test2 = new TestShip("TestShip #2");
		TestShip test3 = new TestShip("TestShip #3");
		TestShip test4 = new TestShip("TestShip #4");
		
		
		
	
		
		ArrayList<AbstractShip> ships = new ArrayList<AbstractShip>();
		ships.add(test1);
		ships.add(test2);
		ships.add(test3);
		ships.add(test4);
		
		BatchArena batchTest = new BatchArena(ships, GAME_COUNT, 15, 5);
		batchTest.startBatch();
		
		
	}
	
	public static void main(String[] args)
	{
		new TestClass();
	}

}
