package arena;

import java.util.ArrayList;
import java.util.Observable;

import arena.collisions.CollisionManager;
import arena.objects.AbstractObject;
import arena.objects.AbstractShip;

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
public class Arena extends Observable implements Runnable
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
	private CollisionManager collisionManager;
	
	/**
	 * Create a new AbstractArena with the following parameters.
	 * @param maxAsteroidCount Maximum asteroids that can spawn in the game.
	 * @param asteroidSpawnChance Percentage chance of asteroid spawning in this arena every tick. ( 1- 100 )
	 * @param tickDelay Amount of delay the thread should have in between every game-tick.
	 */
	public Arena(int maxAsteroidCount, int asteroidSpawnChance, int tickDelay)
	{
		this.asteroidSpawnChanceNorm = (double)asteroidSpawnChance / 100;
		this.tickDelay = tickDelay;
		this.arenaTickCount = 0;
		this.collisionManager = new CollisionManager(this);
		
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
	
	/**
	 * Update the current status of the game given the list of the ships competing.
	 * @param gameShips Current ships in the arena.
	 * @return True if the game is still running, false otherwise.
	 */
	public boolean updateGameStatus(ArrayList<AbstractShip> gameShips)
	{
		for(AbstractShip ship : gameShips)
			if(ship.isObjectAlive())
				return true;
		return false;
	}
	
	
	
	/**
	 * Get the list of objects that currently exist in the arena.
	 * @return List of all objects.
	 */
	public ArrayList<AbstractObject> getArenaObjects()
	{
		return this.arenaObjects;
	}
	
	/**
	 * Get the current collision manager of this arena.
	 * @return Collision manager currently being used by this arena.
	 */
	public CollisionManager getCollisionManager()
	{
		return collisionManager;
	}

}
