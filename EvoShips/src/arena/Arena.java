package arena;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;
import java.util.Random;

import arena.collisions.CollisionManager;
import arena.objects.AbstractObject;
import arena.objects.AbstractShip;
import arena.objects.AsteroidFactory;
import arena.objects.EObjects;

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
	private final int ASTEROID_SPAWN_DELAY = 50;

	//This variable represents how many ticks have to occur before ships are given their 1 points for surviving.
	private final int TICKS_PER_ALIVE_SCORE = 100;
	
	private ArrayList<AbstractShip> arenaShips;

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
	private ArenaWatcher arenaWatcher;

	private boolean gameRunning;

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
		this.arenaWatcher = new ArenaWatcher();
		this.arenaObjects = new ArrayList<AbstractObject>();
		this.arenaShips = new ArrayList<AbstractShip>();
	}

	@Override
	public void run() 
	{
		Random r = new Random();
		gameRunning = true;
		int currentAsteroidCount;
		int ticksSinceLastAsteroidSpawn = 0;

		ArrayList<AbstractShip> currentShips = new ArrayList<AbstractShip>();
		addList = new ArrayList<AbstractObject>();
		removeList = new ArrayList<AbstractObject>();

		while(gameRunning)
		{
			/*
			 * Safely remove and add everything at the start of each tick.
			 */
			arenaObjects.addAll(addList);
			arenaObjects.removeAll(removeList);
			addList.clear();
			removeList.clear();
			currentShips.clear();

			//Here are variable clears.
			currentAsteroidCount = 0;
			
			//Ensure arena watcher is using most updated object list.
			arenaWatcher.setObjects(arenaObjects);


			//If we have an observer then update.
			if(this.countObservers()>0)
				notifyObservers();
			
			
			
			
			//In order for each tick to be fair, the list of object is shuffled here.
			Collections.shuffle(arenaObjects);
			
			collisionManager.checkForCollisions();

			//Go through each object, find all of the ships, useful to have a list of them.
			//If it's a ship, then find out what it wants to do for the next tick.
			//If it's dead, flag it for removal.
			for (AbstractObject obj : arenaObjects) 
			{
				if(obj.getObjectType() == EObjects.OBJ_SHIP)
					currentShips.add((AbstractShip) obj);

				if(obj.isObjectAlive())
				{
					if(obj.getObjectType() == EObjects.OBJ_SHIP)
					{
						((AbstractShip) obj).determineAction();
					}
					if(obj.getObjectType() == EObjects.OBJ_ASTEROID)
						currentAsteroidCount++;



					obj.tickObject();
				}
				else
					removeList.add(obj);
			}

			if(r.nextDouble() <= asteroidSpawnChanceNorm && currentAsteroidCount < maxAsteroids && ticksSinceLastAsteroidSpawn >= ASTEROID_SPAWN_DELAY)
			{	
				ticksSinceLastAsteroidSpawn = 0;
				addObjectToArena(AsteroidFactory.createOffScreenAsteroid());
			}
			else
				ticksSinceLastAsteroidSpawn++;

			this.arenaTickCount++;
			if(this.arenaTickCount % TICKS_PER_ALIVE_SCORE == 0)
			{
				for(AbstractShip ship : currentShips)
					ship.incrementScore(1);
			}

			gameRunning = updateGameStatus(arenaShips);

			try 
			{
				Thread.sleep(tickDelay);
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * Update the current status of the game given the list of the ships competing.
	 * @param gameShips Current ships in the arena.
	 * @return True if the game is still running, false otherwise.
	 */
	public boolean updateGameStatus(ArrayList<AbstractShip> gameShips)
	{
		if(this.arenaTickCount == MAX_GAME_TICKS)
			return false;
		
		int aliveCount = 0;
		AbstractShip aliveShip = null;
		
		for(AbstractShip ship : gameShips)
		{
			if(ship.isObjectAlive())
			{
				aliveCount++;
				aliveShip = ship;
			}
		}
		
		//Clause to see if two ships have managed to die on exactly the same tick ( unlikely ).
		if(aliveCount == 0)
			return false;
		
		//If one ships is alive at the end of the arena fight, then award that ship some points for winning. Hooray, points.
		else if(aliveCount == 1)
		{
			aliveShip.incrementScore(50);
			return false;
		}
		else return true;
			
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
	
	public ArenaWatcher getArenaWatcher()
	{
		return arenaWatcher;
	}

	/**
	 * This method will add a given ship to the arena, it will do this by copying it into a new ship, and adding it into the score map.
	 * @param shipToAdd Ship to add to the arena.
	 */
	public void addShipToArena(AbstractShip shipToAdd)
	{
		if(gameRunning)
			return;

		AbstractShip copy = shipToAdd.cloneShip();
		if(copy.setCurrentGame(this))
		{
			arenaShips.add(copy);
			arenaObjects.add(copy);
		}
	}

	/**
	 * Adds a new object to the arena. If the game is already running, then it will que it to be added. Otherwise it will add it.
	 * @param object Object to be added.
	 */
	public void addObjectToArena(AbstractObject object)
	{
		if(gameRunning)
			addList.add(object);
		else
			arenaObjects.add(object);
	}


}
