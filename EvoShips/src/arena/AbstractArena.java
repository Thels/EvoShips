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
	
	public AbstractArena(int maxAsteroidCount, int asteroidSpawnChance, int tickDelay)
	{
		this.asteroidSpawnChanceNorm = (double)asteroidSpawnChance / 100;
		this.tickDelay = tickDelay;
		arenaTickCount = 0;
	}
	
	@Override
	public void run() 
	{
		// TODO Auto-generated method stub
		
	}

}
