package arena;

import java.util.ArrayList;
import java.util.Observer;
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

	private final int MAX_THREADS = 4;

	private ArrayList<AbstractShip> batchShips;
	private int gamesToRun, maxAsteroids, asteroidSpawnChance;

	private ExecutorService threadPool;


	/**
	 * Creates a new batch arena.
	 * @param batchShips Shisp to be used in the arenas.
	 * @param numberOfGames Number of games to be played by each ship.
	 * @param maxAsteroids Maximum number of asteroids that can occur in each arena spawned.
	 * @param asteroidSpawnChance Chance of asteroids spawning in each arena ( 1 - 100% each tick ).
	 */
	public BatchArena(ArrayList<AbstractShip> batchShips, int numberOfGames, int maxAsteroids, int asteroidSpawnChance) 
	{
		this.batchShips = batchShips;
		this.gamesToRun = numberOfGames;
		this.maxAsteroids = maxAsteroids;
		this.asteroidSpawnChance = asteroidSpawnChance;

		threadPool = Executors.newFixedThreadPool(MAX_THREADS);
	}

	/**
	 * Start this batch.
	 */
	public void startBatch()
	{
		Arena[] arenaThreads = new Arena[gamesToRun];

		for(int i = 0 ; i < gamesToRun ; i++)
		{
			arenaThreads[i] = new Arena(maxAsteroids, asteroidSpawnChance, 0);
			
			for(AbstractShip ship : batchShips)
			{
				arenaThreads[i].addShipToArena(ship);
			}
			threadPool.execute(arenaThreads[i]);
		}
		
		threadPool.shutdown();
		//Ask for all threads to be executed.
	}
	
	/**
	 * Get whether or not the thread pool is still executing.
	 * @return Whether the thread pool is terminated.
	 */
	public boolean isBatchRunning()
	{
		return !threadPool.isTerminated();
	}

}
