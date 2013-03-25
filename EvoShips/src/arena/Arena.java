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
import arena.objects.IDecisionMaker;
import arena.objects.IObjectFactory;

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
	private final int ASTEROID_SPAWN_DELAY = 200;

	//This variable represents how many ticks have to occur before ships are given their 1 points for surviving.
	private final int TICKS_PER_ALIVE_SCORE = 400;

	//initialShips holds a reference to the ships ( non cloned, for scoring purposes. )
	private ArrayList<AbstractShip> arenaShips, initialShips;


	/*
	 * GameObjects is a list holding all of the objects in the current arena
	 * AddList holds a list of objects to be added to the game at the start of the next tick.
	 * RemoveList holds the list of objects to be removed from the game at the start of the next tick.
	 * 
	 * The reason for these is to ensure that there are no concurrent modification errors.
	 */
	private ArrayList<AbstractObject> arenaObjects, addList, removeList;
	private int maxAsteroids, tickDelay, arenaTickCount;
	private float asteroidSpawnChanceNorm;
	private CollisionManager collisionManager;
	private ArenaWatcher arenaWatcher;
	private IObjectFactory asteroidFactory;

	private boolean gameRunning;

	/**
	 * Create a new AbstractArena with the following parameters.
	 * @param maxAsteroidCount Maximum asteroids that can spawn in the game.
	 * @param asteroidSpawnChance Percentage chance of asteroid spawning in this arena every tick. ( 1- 100 )
	 * @param tickDelay Amount of delay the thread should have in between every game-tick.
	 */
	public Arena(int maxAsteroidCount, int asteroidSpawnChance, int tickDelay)
	{
		this.asteroidSpawnChanceNorm = (float)((double)asteroidSpawnChance / (double)100);
		this.maxAsteroids = maxAsteroidCount;
		this.tickDelay = tickDelay;
		this.arenaTickCount = 0;
		this.collisionManager = new CollisionManager(this);
		this.arenaWatcher = new ArenaWatcher();
		this.arenaObjects = new ArrayList<AbstractObject>();
		this.arenaShips = new ArrayList<AbstractShip>();
		this.initialShips = new ArrayList<AbstractShip>();
		this.asteroidFactory = new AsteroidFactory();
	}

	@Override
	public void run() 
	{
		Random r = new Random();
		gameRunning = true;
		int currentAsteroidCount;

		//Set to max value to spawn one at start.
		int ticksSinceLastAsteroidSpawn = ASTEROID_SPAWN_DELAY;

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
			{
				setChanged();
				notifyObservers(arenaObjects.clone());
			}



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
					if(obj instanceof IDecisionMaker)
					{
						((IDecisionMaker) obj).determineAction();
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
				addObjectToArena(asteroidFactory.produceObject());
			}
			else
				ticksSinceLastAsteroidSpawn++;

			this.arenaTickCount++;
			if(this.arenaTickCount % TICKS_PER_ALIVE_SCORE == 0)
			{
				for(AbstractShip ship : currentShips)
					if(ship.isObjectAlive())
						ship.incrementScore(EScoring.SURVIVING_BONUS.getScore());
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

	/**
	 * Get the arena watcher currently tied to this arena.
	 * @return Arena watcher created at arena run time.
	 */
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

		initialShips.add(shipToAdd);

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

	/**
	 * Retrieve whether or not the game is currently running, useful for UI elements.
	 * @return Whether the game logic loops is still running.
	 */
	public boolean isGameRunning()
	{
		return this.gameRunning;
	}

	/**
	 * Get the initial ships that were used to create this arena, the non-cloned ones.
	 * @return List generated when adding ships to the arena.
	 */
	public ArrayList<AbstractShip> getInitialShips()
	{
		return this.initialShips;
	}


}
