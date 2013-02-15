package arena.objects;

import java.awt.Point;
import java.util.Random;

import arena.collisions.CollisionPolygon;

/**
 * Abstract class representing the core behaviour and methods that belong to any given object
 * in the arena.
 * @author Ross
 *
 */
public abstract class AbstractObject 
{
	private Point.Double objectPos;
	private double objectDir;
	private int objectHealth;

	/**
	 * Constructor that most objects will use, specifying the object type when creating
	 * @param spawnLocation - Location the object is spawning at.
	 * @param direction - Direction the object is facing on creation.
	 */
	public AbstractObject(Point.Double spawnLocation, double direction)
	{
		this.objectPos = spawnLocation;
		this.objectDir = direction;
		this.objectHealth = getObjectType().getBaseHealth();
	}

	/**
	 * This constructor is only used when the object being created is to have a random spawn
	 * location and direction. This is useful for randomly placing ships at the start of each
	 * round.
	 */
	public AbstractObject()
	{
		Random r = new Random();

		double x = 0.1 + ( 0.9 - 0.1 ) * r.nextDouble();
		double y = 0.1 + ( 0.9 - 0.1 ) * r.nextDouble();

		this.objectPos = new Point.Double(x,y);
		this.objectDir = 360 * r.nextDouble();
		this.objectHealth = getObjectType().getBaseHealth();

		r = null;
	}

	/**
	 * Gets the current type of this object. Useful for differentiating between different
	 * objects in different parts of the program.
	 * @return Type of object defined by EObjects.
	 */
	public abstract EObjects getObjectType();

	/**
	 * This method determines the objects behavior each time the game ticks.
	 */
	public abstract void tickObject();

	/**
	 * Modify the direction the object is facing by a given amount.
	 * 
	 * If the direction ends up being a negative value, normalise it to be above 0 again.
	 * @param amount Amount to change the direction by, positive OR negative.
	 */
	public void modifyDirection(double amount)
	{
		this.objectDir += amount;
		if(this.objectDir < 0)
			this.objectDir += 360;
	}


	/**
	 * Method to return the current health of the given object.
	 * @return Current health of the object.
	 */
	public int getObjectHealth()
	{
		return this.objectHealth;
	}

	/**
	 * Method to check if a given object is alive or not. For an object to be alive, it needs to 
	 * have at least one health remaining. If it doesn't, then it is dead.
	 * @return True If health is >1 , otherwise false.
	 */
	public boolean isObjectAlive()
	{
		return this.objectHealth >= 1;
	}

	/**
	 * Get the objects current position.
	 * @return Current position of object ( x , y )
	 */
	public Point.Double getObjectPosition()
	{
		return this.objectPos;
	}

	/**
	 * Get the current direction of this object.
	 * @return this.objectDirection % 360.
	 */
	public double getDirection()
	{
		return this.objectDir % 360;
	}

	/**
	 * Apply damage to the object by directly removing value from its health.
	 * @param amount Amount of damage to apply.
	 */
	public void applyDamage(int amount)
	{
		this.objectHealth -= amount;
	}
	
	public void setNewObjectPosition(Point.Double newPos)
	{
		this.objectPos = newPos;
	}

	
	public abstract CollisionPolygon getObjectCollisionModel();



}
