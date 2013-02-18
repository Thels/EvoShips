package arena.objects;

import java.awt.Point;

import arena.AbstractArena;

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
	private final int FIRE_DELAY = 35;



	private String shipName;
	protected boolean forward, backward, left, right, fire;
	private int ticksSinceLastShot;
	private AbstractArena currentGame;

	/**
	 * Create a new ship, with a set given name.
	 * @param shipName
	 */
	public AbstractShip(String shipName)
	{
		super();
		this.shipName = shipName;
		forward = backward = left = right = fire = false;
		ticksSinceLastShot = 0;
	}

	/**
	 * Creates a new ship based upon another ship, so that it has the same position, name and direction.
	 * @param ship Ship to base new ship on.
	 */
	public AbstractShip(AbstractShip ship)
	{
		super(ship.getObjectPosition(),ship.getDirection());
		this.shipName = ship.getShipName();
	}

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
		if(currentGame.getCollisionManager().isMoveValid(this))
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
		if(currentGame.getCollisionManager().isMoveValid(this))
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
	public boolean setCurrentGame(AbstractArena arena)
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
		//TODO Add in fire method. Need arena watcher to be up and running for that though.
	}

}
