package arena.objects;

import java.awt.Point;

import arena.Arena;
import arena.ArenaWatcher;
import arena.collisions.CollisionPolygon;
import arena.objects.objects.Bullet;

public abstract class AbstractShip extends AbstractObject
{
	//Speed at which any ship can move forward every given tick.
	private final double SHIP_SPEED = 0.002;
	//Speed at which any ship can move backward every given tick.
	private final double SHIP_SPEED_REVERSE = SHIP_SPEED * 0.9;
	//How many degrees the ship can turn through each tick.
	private final double SHIP_TURNSPEED = 1.7;

	//How many bullets any ship can have on screen at any given time.
	private final int MAX_BULLETS = 8;
	//How many ticks a ship has to wait in between firing each shot.
	private final int FIRE_DELAY = 50;



	private String shipName;
	protected boolean forward, backward, left, right, fire;
	private int ticksSinceLastShot, shipScore;
	private Arena currentGame;
	private AbstractShip cloneOfShip;

	/**
	 * Create a new ship, with a set given name.
	 * @param shipName
	 */
	public AbstractShip(String shipName)
	{
		super();
		this.shipName = shipName;
		resetActionBooleans();
		ticksSinceLastShot = shipScore = 0;
	}

	/**
	 * Returns a deep copy of this abstract ship, useful for running one ship across many threads while maintaining scoring.
	 * @return AbstractShip copy of the current ship.
	 */
	public abstract AbstractShip cloneShip();

	protected void resetActionBooleans()
	{
		forward = backward = left = right = fire = false;
	}

	/**
	 * Get the name of this ship.
	 * @return Name of the ship as given in the constructor.
	 */
	public String getShipName()
	{
		return this.shipName;
	}

	@Override
	public EObjects getObjectType() 
	{
		return EObjects.OBJ_SHIP;
	}

	/**
	 * When a ship ticks, it checks all of its boolean flags and determines movement from them, that's all it does.
	 */
	@Override
	public void tickObject() 
	{
		if(fire)
			fire();
		if(left)
			turnLeft();
		else if(right)
			turnRight();
	
		if(forward)
			moveForward();
		else if(backward)
			moveBackward();
	}

	/**
	 * Turn the ship left by a set amount of degrees.
	 */
	private void turnLeft()
	{
		modifyDirection(-SHIP_TURNSPEED);
		if(!currentGame.getCollisionManager().isMoveValid(this))
			modifyDirection(SHIP_TURNSPEED);
	}

	/**
	 * Turn the ship right by a set amount of degrees.
	 */
	private void turnRight()
	{
		modifyDirection(SHIP_TURNSPEED);
		if(!currentGame.getCollisionManager().isMoveValid(this))
			modifyDirection(-SHIP_TURNSPEED);
	}

	/**
	 * Move the ship forward by a given distance on it's current facing.
	 */
	private void moveForward()
	{
		double oldPosX = getObjectPosition().x;
		double oldPosY = getObjectPosition().y;
		double newPosX = getObjectPosition().x + (SHIP_SPEED * Math.sin(Math.toRadians(getDirection())));
		double newPosY = getObjectPosition().y + (SHIP_SPEED * Math.cos(Math.toRadians(getDirection())));
		this.setNewObjectPosition(new Point.Double(newPosX, newPosY));
		if(!currentGame.getCollisionManager().isMoveValid(this))
			this.setNewObjectPosition(new Point.Double(oldPosX, oldPosY));
		
		
	}

	/**
	 * Move the ship backward by a given distance on it's current facing.
	 */
	private void moveBackward()
	{
		double oldPosX = getObjectPosition().x;
		double oldPosY = getObjectPosition().y;
		double newPosX = getObjectPosition().x - (SHIP_SPEED_REVERSE * Math.sin(Math.toRadians(getDirection())));
		double newPosY = getObjectPosition().y - (SHIP_SPEED_REVERSE * Math.cos(Math.toRadians(getDirection())));
		this.setNewObjectPosition(new Point.Double(newPosX, newPosY));
		if(!currentGame.getCollisionManager().isMoveValid(this))
			this.setNewObjectPosition(new Point.Double(oldPosX, oldPosY));
	}


	/**
	 * Determine the action that this ship has to take this tick, setting boolean values such as fire, left, right, forward 
	 * and backward to true and false figure this out.
	 */
	public abstract void determineAction();


	/**
	 * Attempts to set the current game this ships is competing in.
	 * @param arena Arena in which the ship is competing.
	 * @return True if set correctly, false if otherwise.
	 */
	public boolean setCurrentGame(Arena arena)
	{
		if(this.currentGame != null)
			return false;
		else 
		{
			this.currentGame = arena;
			return true;
		}
	}

	/**
	 * Method that is called whenever the ship is to fire.
	 */
	private void fire()
	{
		int currentBulletCount = currentGame.getArenaWatcher().getShipBulletCount(this);
		if(currentBulletCount < MAX_BULLETS && ticksSinceLastShot >= FIRE_DELAY)
		{
			ticksSinceLastShot = 0;
			
			double frontX = getObjectPosition().x +  0.025 * Math.sin(Math.toRadians(getDirection()));
			double frontY = getObjectPosition().y +  0.025 * Math.cos(Math.toRadians(getDirection()));
			Bullet b = new Bullet(this,new Point.Double(frontX,frontY), getDirection());
			currentGame.addObjectToArena(b);
		}
		else
			ticksSinceLastShot++;
	}

	/**
	 * Gets the ship that this ship was copied from. This is used for when scoring has to be calculated when I changed to threading the arenas.
	 * @return
	 */
	public AbstractShip getCloneOfShip()
	{
		if(this.cloneOfShip != null)
			return this.cloneOfShip;
		else
			return null;
	}

	@Override
	public CollisionPolygon getObjectCollisionModel() 
	{
		CollisionPolygon colPol = new CollisionPolygon(this.getObjectPosition(), 3);
		double x = getObjectPosition().x;
		double y = getObjectPosition().y;

		double frontX = x +  0.025 * Math.sin(Math.toRadians(getDirection()));
		double frontY = y +  0.025 * Math.cos(Math.toRadians(getDirection()));
		colPol.addVertice(frontX, frontY);

		double backLeftX = x + (Math.sqrt(Math.pow(0.025, 2)*2)* Math.sin(Math.toRadians(getDirection()+180.0+45)));
		double backLeftY = y + (Math.sqrt(Math.pow(0.025, 2)*2)* Math.cos(Math.toRadians(getDirection()+180.0+45)));
		colPol.addVertice(backLeftX, backLeftY);

		double backRightX = x + (Math.sqrt(Math.pow(0.025, 2)*2)* Math.sin(Math.toRadians(getDirection()+180.0-45)));
		double backRightY = y + (Math.sqrt(Math.pow(0.025, 2)*2)* Math.cos(Math.toRadians(getDirection()+180.0-45)));
		colPol.addVertice(backRightX, backRightY);

		return colPol;

	}

	/**
	 * Get the current score of the ship
	 * @return Score of ship in arena.
	 */
	public int getScore()
	{
		if(this.cloneOfShip == null)
			return this.shipScore;
		else
			return this.cloneOfShip.getScore();
	}

	/**
	 * Increment the score of this ship in the given arena. It is synchronised since incrementing the score will happen across multiple threads.
	 * 
	 * If the ship has a "cloneOfShip". Then it will increment that ships score instead. This is to track scores across multiple games easily.
	 * @param amount Amount to increase score by.
	 */
	public synchronized void incrementScore(int amount)
	{
		if(this.cloneOfShip == null)
			this.shipScore += amount;
		else
			this.cloneOfShip.incrementScore(amount);
	}
	
	/**
	 * Set the ships cloneOf ship.
	 * @param motherShip Ship to set as clone of ship.
	 */
	public void setCloneOfShip(AbstractShip motherShip)
	{
		this.cloneOfShip = motherShip;
	}
	
	/**
	 * Get the arena watcher that this ship is to use. 
	 * Could forsee some errors if this was called in a ships constructor.
	 * 
	 * @return arenawatcher to get information from.
	 */
	public ArenaWatcher getShipsArenaWatcher()
	{
		return this.currentGame.getArenaWatcher();
	}

}
