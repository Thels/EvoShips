package arena;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import arena.objects.AbstractObject;
import arena.objects.AbstractShip;
import arena.objects.objects.Asteroid;
import arena.objects.objects.Bullet;

public class ArenaWatcher 
{
	private List<AbstractObject> ships;
	private List<AbstractObject> asteroids;
	private List<AbstractObject> bullets;


	//Max value used for normalised return values.
	private final double arenaMaxSize = Math.sqrt(2);

	/**
	 * Create a new arena watcher, just creates all of the lists that are needed.
	 */
	public ArenaWatcher()
	{
		ships = new ArrayList<AbstractObject>();
		asteroids = new ArrayList<AbstractObject>();
		bullets = new ArrayList<AbstractObject>();
	}

	/**
	 * Set the objects that the arena watcher should be using for this given game tick.
	 * @param gameObjects Game objects to be used by the methods.
	 */
	public void setObjects(List<AbstractObject> gameObjects) 
	{
		ships.clear();
		asteroids.clear();
		bullets.clear();
		for(AbstractObject obj : gameObjects)
		{
			switch(obj.getObjectType())
			{
			case OBJ_ASTEROID: asteroids.add(obj); break;
			case OBJ_BULLET: bullets.add(obj); break;
			case OBJ_SHIP: ships.add(obj); break;
			}
		}
	}

	/**
	 * Private method to calculate the value of distance between two given points.
	 * @param first First point to check.
	 * @param second Second point to check.
	 * @return Distance between the two given points.
	 */
	private double distanceBetweenPoints(Point.Double first, Point.Double second)
	{
		double sqdx = Math.pow((second.x - first.x),2);
		double sqdy = Math.pow((second.y - first.y),2);
		return Math.sqrt(sqdx + sqdy);
	}

	/**
	 * Gets the ship nearest to the given object.
	 * @param check Object to check.
	 * @return Nearest ship if it exists, null otherwise.
	 */
	public AbstractShip getNearestShip(AbstractObject check) 
	{
		AbstractShip nearestShip = null;
		double disToNearest = Double.POSITIVE_INFINITY;

		for(AbstractObject enemy: ships)
		{
			double newDistance = distanceBetweenPoints(check.getObjectPosition(), enemy.getObjectPosition());
			if((newDistance < disToNearest) && !enemy.equals(check) && enemy.isObjectAlive())
			{
				disToNearest = newDistance;
				nearestShip = (AbstractShip) enemy;
			}
		}

		return nearestShip;
	}

	/**
	 * Get the asteroid nearest to the given object.
	 * @param check Object to check.
	 * @return Nearest asteroid if it exists, null otherwise.
	 */
	public Asteroid getNearestAsteroid(AbstractObject check) 
	{
		Asteroid nearestAsteroid = null;
		double disToNearest = Double.POSITIVE_INFINITY;

		for(AbstractObject enemy: asteroids)
		{
			double newDistance = distanceBetweenPoints(check.getObjectPosition(), enemy.getObjectPosition());
			if((newDistance < disToNearest) && !enemy.equals(check) && enemy.isObjectAlive())
			{
				disToNearest = newDistance;
				nearestAsteroid = (Asteroid) enemy;
			}
		}

		return nearestAsteroid;
	}

	/**
	 * Get the bullet nearest to the given object, which wasn't created by the given object.
	 * @param check Object to check.
	 * @return Nearest bullet if it exists, null otherwise.
	 */
	public Bullet getNearestEnemyBullet(AbstractObject check) 
	{
		Bullet nearestBullet = null;
		double disToNearest = Double.POSITIVE_INFINITY;

		for(AbstractObject bullet: bullets)
		{
			if(((Bullet) bullet).getBulletCreator() == check)
				continue;

			double newDistance = distanceBetweenPoints(check.getObjectPosition(), bullet.getObjectPosition());
			if((newDistance < disToNearest) && bullet.isObjectAlive())
			{
				disToNearest = newDistance;
				nearestBullet = (Bullet) bullet;
			}
		}

		return nearestBullet;
	}

	/**
	 * Get the distance to the nearest enemy ship. If no enemy ship can be found, then it returns Double.POSITIVE_INFINITY.
	 * @param check Object to calculate nearest distance to.
	 * @return Distance to the nearest enemy ship, or INFINITY if no ship can be found.
	 */
	public double distanceToNearestEnemy(AbstractObject check) 
	{
		AbstractShip nearestEnemy = getNearestShip(check);
		if(nearestEnemy == null)
			return Double.POSITIVE_INFINITY;
		return distanceBetweenPoints(check.getObjectPosition(), nearestEnemy.getObjectPosition());
	}

	/**
	 * Get the distance to the nearest asteroid. If no asteroid can be found, then it returns Double.POSITIVE_INFINITY.
	 * @param check Object to calculate nearest distance to.
	 * @return Distance to the nearest asteroid, or 0 if no asteroid can be found.
	 */
	public double distanceToNearestAsteroid(AbstractObject check)
	{
		Asteroid nearestAsteroid = getNearestAsteroid(check);
		if(nearestAsteroid == null)
			return 0;
		return distanceBetweenPoints(check.getObjectPosition(),nearestAsteroid.getObjectPosition());
	}

	/**
	 * Get the distance to the nearest enemy bullet. If no bullet can be found, then it returns Double.POSITIVE_INFINITY.
	 * @param check Object to calculate nearest distance to.
	 * @return Distance to the nearest bullet, or 0 if no bullet can be found.
	 */
	public double distanceToNearestBullet(AbstractObject check)
	{
		Bullet nearestBullet = getNearestEnemyBullet(check);
		if(nearestBullet == null)
			return 0;
		return distanceBetweenPoints(check.getObjectPosition(),nearestBullet.getObjectPosition());
	}


	/**
	 * Calculate the distance to the nearest enemy and return it normalised.
	 * @param check Object to calculate distance from.
	 * @return Normalised value of the distance to nearest enemy, infinity if no ship nearby.
	 */
	public double normalisedDistanceNearestEnemy(AbstractObject check)
	{
		double distance = distanceToNearestEnemy(check);
		return distance / arenaMaxSize;
	}

	/**
	 * Calculate the distance to the nearest asteroid and return it normalised.
	 * @param check Object to calculate distance from.
	 * @return Normalised value of the distance to nearest asteroid, infinity if no asteroid nearby.
	 */
	public double normalisedDistanceNearestAsteroid(AbstractObject check)
	{
		double distance = distanceToNearestAsteroid(check);
		return distance / arenaMaxSize;
	}

	/**
	 * Calculate the distance to the nearest asteroid and return it normalised.
	 * @param check Object to calculate distance from.
	 * @return Normalised value of the distance to nearest asteroid, infinity if no asteroid nearby.
	 */
	public double normalisedDistanceNearestEnemyBullet(AbstractObject check)
	{
		double distance = distanceToNearestBullet(check);
		return distance / arenaMaxSize;
	}

	/**
	 * Calculate the angle to a given object inside the arena.
	 * @param check Object to calculate the angle from.
	 * @param object Object to calculate the angle to.
	 * @return -ve if objects need to turn RIGHT, and +ve if object needs to turn LEFT.
	 */
	public double angleToObject(AbstractObject check, AbstractObject object)
	{		
		double dx, dy;
		dx = object.getObjectPosition().x - check.getObjectPosition().x;
		dy = object.getObjectPosition().y - check.getObjectPosition().y;

		double angleInDegrees = ((Math.atan2(dx, dy)) * 180 / Math.PI);
		double targetDir = angleInDegrees;
		double objectDirection = check.getDirection();

		if(targetDir >= objectDirection)
		{
			if (targetDir - objectDirection < 180) 
			{
				angleInDegrees = -(targetDir - objectDirection);
			} 
			else 
			{ 
				angleInDegrees = (360 - targetDir) + objectDirection;
			}
		}

		if(objectDirection > targetDir)
		{
			if (objectDirection - targetDir < 180) 
			{
				angleInDegrees = objectDirection - targetDir;
			} 
			else 
			{ 
				angleInDegrees = -(360 - (objectDirection - targetDir));
			}
		}

		return angleInDegrees;
	}

	/**
	 * Calculates the angle from a given object to the nearest enemy.
	 * @param checkObject Object to calculate angle from.
	 * @return Angle to the nearest enemy, 0 if no ship exists.
	 */
	public double angleToNearestEnemy(AbstractObject checkObject)
	{
		AbstractShip nearestShip = getNearestShip(checkObject);
		if(nearestShip == null)
			return 0;
		return angleToObject(checkObject, nearestShip);
	}

	/**
	 * Calculates the angle from a given object to the nearest asteroid.
	 * @param checkObject Object to calculate angle from.
	 * @return Angle to the nearest enemy, 0 if no asteroid exists.
	 */
	public double angleToNearestAsteroid(AbstractObject checkObject)
	{
		Asteroid nearestAsteroid = getNearestAsteroid(checkObject);
		if(nearestAsteroid == null)
			return 0;
		return angleToObject(checkObject, nearestAsteroid);
	}

	/**
	 * Calculates the angle from a given object to the nearest enemy bullet.
	 * @param checkObject Object to calculate angle from.
	 * @return Angle to the nearest enemy bullet, 0 if no bullet exists.
	 */
	public double angleToNearestEnemyBullet(AbstractObject checkObject)
	{
		Bullet nearestBullet = getNearestEnemyBullet(checkObject);
		if(nearestBullet == null)
			return 0;
		return angleToObject(checkObject, nearestBullet);
	}

	/**
	 * Calculate the angle to the nearest ship and return it normalised.
	 * 
	 * The normalised angle has to be between -1 and 1.
	 * @param check Object to calculate angle from.
	 * @return Normalised value of the angle to nearest ship, infinity if no ship nearby.
	 */
	public double normalisedAngleNearestEnemy(AbstractObject checkObject)
	{
		double angle = angleToNearestEnemy(checkObject);
		return angle/=180;
	}

	/**
	 * Calculate the angle to the nearest asteroid and return it normalised.
	 * 
	 * The normalised angle has to be between -1 and 1.
	 * @param check Object to calculate angle from.
	 * @return Normalised value of the angle to nearest asteroid, infinity if no asteroid nearby.
	 */
	public double normalisedAngleNearestAsteroid(AbstractObject checkObject)
	{
		double angle = angleToNearestAsteroid(checkObject);
		return angle/=180;
	}
	
	/**
	 * Calculate the angle to the nearest enemy bullet and return it normalised.
	 * 
	 * The normalised angle has to be between -1 and 1.
	 * @param check Object to calculate angle from.
	 * @return Normalised value of the angle to nearest enemy bullet, infinity if no bullet nearby.
	 */
	public double normalisedAngleNearestEnemyBullet(AbstractObject checkObject)
	{
		double angle = angleToNearestEnemyBullet(checkObject);
		return angle/=180;
	}

	/**
	 * Get the number of bullets currently in the arnea that belong to a given ship.
	 * @param ship Ship to check.
	 * @return Number of bullets that are owned by that object.
	 */
	public synchronized int getShipBulletCount(AbstractShip ship) 
	{
		int count = 0;
		for (Iterator<AbstractObject> i = bullets.iterator(); i.hasNext();) 
		{
			AbstractObject next = i.next();
			if(((Bullet) next).getBulletCreator() == ship)
				count++;

		}
		return count;
	}

}
