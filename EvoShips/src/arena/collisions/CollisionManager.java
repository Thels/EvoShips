package arena.collisions;

import java.util.ArrayList;
import java.util.List;

import arena.AbstractArena;
import arena.objects.AbstractObject;
import arena.objects.EObjects;

/**
 * The collision watcher is a class which will be able to detect, and deal with collisions. It is created with a reference
 * to the arena that it is currently watching. This is to make collision watching be thread-safe.
 * @author Ross
 *
 */
public class CollisionManager
{
	private ArrayList<Collision> collisionList;
	private List<AbstractObject> gameObjects = null;

	/**
	 * Generate a new collision manager. With the given arena as a reference.
	 * @param arena Arena this class is managing collisions for.
	 */
	public CollisionManager(AbstractArena arena)
	{
		collisionList = new ArrayList<Collision>();
	}

	/**
	 * Add a new collision that has to be dealt with this tick, in order to add this, it makes sure the collision doesn't already exist.
	 * This shouldn't be an issue, but just incase it's there. 
	 * @param collision Collision to add to the system.
	 */
	public void addCollision(Collision collision)
	{
		if(!collisionList.contains(collision))
			collisionList.add(collision);
	}

	/**
	 * Checks to see whether or not a position is safe to move to. This is mainly for ships to use, and the value checking is determined by this.
	 * The "magic numbers" 0.025 is the width of a given ship. So first of all it checks that any of the given points of the ship are going to be outside of
	 * the bounds. It then checks to see if it collides with any other given object.
	 * 
	 * This method is useful as it allows ships to check if they need to reverse after moving. To avoid colliding or "warping" inside another object.
	 * @param objectToCheck Object to check for safe movement.
	 * @return Is the move safe.
	 */
	public boolean isPositionSafe(AbstractObject objectToCheck)
	{
		if(objectToCheck.getObjectPosition().x < 0+0.035 || objectToCheck.getObjectPosition().x > 1-0.035 || objectToCheck.getObjectPosition().y < 0+0.035 || objectToCheck.getObjectPosition().y > 1-0.035)
			return false;

		for(AbstractObject obj : gameObjects)
		{
			if(obj == objectToCheck || obj.getObjectType() == EObjects.OBJ_BULLET || obj.getObjectType() == EObjects.OBJ_ASTEROID)
				continue;

			if(objectToCheck.getObjectCollisionModel().collide(obj.getObjectCollisionModel()))
				return false;
		}

		return true;
	}

	/**
	 * Go through each object in the game and see if it collides with anything else.
	 */
	public void checkForCollisions() 
	{
		for(int i = 0 ; i < gameObjects.size(); i++)
		{
			for(int j = 0; j < gameObjects.size(); j++)
			{
				if(i == j)
					continue;
				else
				{
					if(gameObjects.get(i).getObjectType() == EObjects.OBJ_BULLET && gameObjects.get(j).getObjectType() != EObjects.OBJ_BULLET)
					{

						if(((Bullet) gameObjects.get(i)).getBulletCreator() != gameObjects.get(j))
						{
							CollisionPolygon bullet = gameObjects.get(i).getObjectCollisionModel();
							CollisionPolygon other = gameObjects.get(j).getObjectCollisionModel();

							if(bullet.collide(other))
								collisionList.add(new Collision(gameObjects.get(i), gameObjects.get(j)));
						}

					}

					if(gameObjects.get(i).getObjectType() == EObjects.ASTEROID && gameObjects.get(j).getObjectType() == EObjects.SHIP)
					{
						CollisionPolygon asteroid = gameObjects.get(i).getObjectCollisionModel();
						CollisionPolygon ship = gameObjects.get(j).getObjectCollisionModel();

						if(asteroid.collide(ship))
							collisionList.add(new Collision(gameObjects.get(i), gameObjects.get(j)));
					}


				}			
			}		
		}
		//Now we deal with the collisions, then clear the list so that the next ticks collison list is empty.
		dealWithCollisions();
		collisionList.clear();
	}

	/**
	 * Deal with the collisions that happened this tick.
	 * 
	 * BULLET -> ANYTHING  (apply one damage)
	 * ASTEROID -> ANYTHING (apply ten damage)
	 */
	private void dealWithCollisions() 
	{
		for(Collision c : collisionList)
		{
			if(c.getObject().getObjectType() == EObjects.BULLET)
			{
				c.getObject().applyDamage(1);
				c.getCollidingWithObject().applyDamage(1);
			}	

			else if(c.getObject().getObjectType() == EObjects.ASTEROID)
			{
				c.getCollidingWithObject().applyDamage(10);
			}	
		}

	}

}
