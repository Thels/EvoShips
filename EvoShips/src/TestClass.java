import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import arena.Arena;
import arena.exceptions.GameAlreadyRunningException;
import arena.objects.ships.TestShip;


public class TestClass 
{

	public TestClass() 
	{
		int GAME_COUNT = 10000;
		TestShip test1 = new TestShip("TestShip #1");
		TestShip test2 = new TestShip("TestShip #2");
		TestShip test3 = new TestShip("TestShip #3");
		TestShip test4 = new TestShip("TestShip #4");
		
		Arena[] arenaArray = new Arena[GAME_COUNT];
		for(int i = 0 ;  i < GAME_COUNT ; i++)
		{
			arenaArray[i] = new Arena(10, 10, 0);
			try 
			{
				arenaArray[i].addShipToArena(test1);
				arenaArray[i].addShipToArena(test2);
				arenaArray[i].addShipToArena(test3);
				arenaArray[i].addShipToArena(test4);
			} 
			catch (GameAlreadyRunningException e) 
			{
				e.printStackTrace();
			}
			
			executorService.execute(arenaArray[i]);
			//linkedBlockingDeque.add(arenaArray[i]);
			
		}
		
	
		System.out.println(test1.getScore()/GAME_COUNT);
		System.out.println(test2.getScore()/GAME_COUNT);
		System.out.println(test3.getScore()/GAME_COUNT);
		System.out.println(test4.getScore()/GAME_COUNT);
		
	}
	
	public static void main(String[] args)
	{
		new TestClass();
	}

}
