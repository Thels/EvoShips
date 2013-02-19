import arena.Arena;
import arena.exceptions.GameAlreadyRunningException;
import arena.objects.ships.TestShip;


public class TestClass 
{

	public TestClass() 
	{
		TestShip test1 = new TestShip("TestShip #1");
		TestShip test2 = new TestShip("TestShip #2");
		Arena[] arenaArray = new Arena[2500];
		for(int i = 0 ;  i < 2500 ; i++)
		{
			arenaArray[i] = new Arena(10, 10, 0);
			try 
			{
				arenaArray[i].addShipToArena(test1);
				arenaArray[i].addShipToArena(test2);
			} 
			catch (GameAlreadyRunningException e) 
			{
				e.printStackTrace();
			}
			
		}
		for(int i = 0 ;  i < 2500 ; i++)
		{
			arenaArray[i].run();
		}
		
		
		System.out.println(test1.getScore());
		System.out.println(test2.getScore());
		
	}
	
	public static void main(String[] args)
	{
		new TestClass();
	}

}
