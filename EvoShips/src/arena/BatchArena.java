package arena;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import arena.objects.AbstractShip;

/**
 * Creates a new batch arena, the aim of this class is to be able to manage a thread pool and run all of the arenas at the same time. In
 * order to do this , I will use an ExecutorService, which allows me to easily run a bounded thread pool.
 * 
 * The batch arena will take in a list of ships that will be competing across all of the games, along with how many games will be running.
 * 
 * This class will later hold methods to retrieve the score of each set of batch arenas.
 * @author Ross
 *
 */
public class BatchArena 
{

	private final int MAX_THREADS = 5;
	
	
	/**
	 * Creates a new batch arena.
	 * @param batchShips Shisp to be used in the arenas.
	 * @param numberOfGames Number of games to be played by each ship.
	 * @param maxAsteroids Maximum number of asteroids that can occur in each arena spawned.
	 * @param asteroidSpawnChance Chance of asteroids spawning in each arena ( 1 - 100% each tick ).
	 */
	public BatchArena(ArrayList<AbstractShip> batchShips, int numberOfGames, int maxAsteroids, int asteroidSpawnChance) 
	{
		ExecutorService threadPool = Executors.newFixedThreadPool(MAX_THREADS);
		
		Arena[] arenaThreads = new Arena[numberOfGames];
		
		for(int i = 0 ; i < numberOfGames ; i++)
		{
			arenaThreads[i] = new Arena(maxAsteroids, asteroidSpawnChance, 0);
			for(AbstractShip ship : batchShips)
			{
				arenaThreads[i].addShipToArena(ship);
			}
		}
		
	}

}
