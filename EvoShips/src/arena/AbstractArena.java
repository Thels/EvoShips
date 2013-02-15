package arena;

import java.util.ArrayList;
import java.util.Observable;

import arena.objects.AbstractObject;

/**
 * Class that will hold the shared behaviour between the various arena types. 
 * 
 * Since each arena can implement it's own rules and such, the only thing that will differ from
 * most arenas to one another is when to calculate the arena has ended.
 * 
 * This class is observeable, since if the arena is being displayed, then it will be required 
 * to update its observer every tick.
 * 
 * Each arena is threaded, so that we can run multiple arenas at once later on down the line concurrently.
 * @author Ross
 *
 */
public class AbstractArena extends Observable implements Runnable
{
	//Amount of game ticks that constitutes "too long".
	private final int MAX_GAME_TICKS = 30000;
	
	//Amount of ticks that must happen in between every asteroid spawning.
	private final int ASTEROID_SPAWN_DELAY = 20;
	
	
	/*
	 * GameObjects is a list holding all of the objects in the current arena
	 * AddList holds a list of objects to be added to the game at the start of the next tick.
	 * RemoveList holds the list of objects to be removed from the game at the start of the next tick.
	 * 
	 * The reason for these is to ensure that there are no concurrent modification errors.
	 */
	private ArrayList<AbstractObject> arenaObjects, addList, removeList;
	private int maxAsteroids, tickDelay, arenaTickCount;
	private double asteroidSpawnChanceNorm;
	
	/**
	 * Create a new AbstractArena with the following parameters.
	 * @param maxAsteroidCount Maximum asteroids that can spawn in the game.
	 * @param asteroidSpawnChance Percentage chance of asteroid spawning in this arena every tick.
	 * @param tickDelay
	 */
	public AbstractArena(int maxAsteroidCount, int asteroidSpawnChance, int tickDelay)
	{
		this.asteroidSpawnChanceNorm = (double)asteroidSpawnChance / 100;
		this.tickDelay = tickDelay;
		arenaTickCount = 0;
	}
	
	@Override
	public void run() 
	{
		boolean gameRunning = true;
		
		while(gameRunning)
		{
			/*
			 * Safely remove and add everything at the start of each tick.
			 */
			arenaObjects.addAll(addList);
			arenaObjects.removeAll(removeList);
			addList.clear();
			removeList.clear();
			
		}
	}

}
