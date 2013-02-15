package arena.collisions;

import arena.objects.AbstractObject;

/**
 * A helper class to easily set and determine who collided with who. Tye type of collision is used to easily determine
 * what's colliding with what when dealing with collisions.
 * @author Ross
 *
 */
public class Collision 
{
	private AbstractObject object, collidingWithObject;
	private ECollisionTypes colType;

	/**
	 * Create a new collision between two objects.
	 * @param object
	 * @param collidingWith
	 */
	public Collision(AbstractObject object, AbstractObject collidingWith, ECollisionTypes colType)
	{
		this.object = object;
		this.collidingWithObject = collidingWith;
		this.colType = colType;
	}

	/**
	 * Get the object that iniated the collision.
	 * @return AbstractObject that collided into the other object.
	 */
	public AbstractObject getObject() 
	{
		return object;
	}

	/**
	 * Get the object that was collided with.
	 * @return AbstractObject that was collided into.
	 */
	public AbstractObject getCollidingWithObject() 
	{
		return collidingWithObject;
	}
	
	/**
	 * Get the type of collision that this collision represents.
	 * @return Collision type was given by the enum ECollisionTypes
	 */
	public ECollisionTypes getCollisionType()
	{
		return this.colType;
	}
	
	
}
