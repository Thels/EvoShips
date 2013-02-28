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
	 * Get the distance to the nearest asteroid. If no asteroid can be found, then it returns Double.POSITIVE_INFINITY.
	 * @param check Object to calculate nearest distance to.
	 * @return Distance to the nearest asteroid, or INFINITY if no asteroid can be found.
	 */
	public double distanceToNearestAsteroid(AbstractObject check)
	{
		Asteroid nearestAsteroid = getNearestAsteroid(check);
		if(nearestAsteroid == null)
			return Double.POSITIVE_INFINITY;
		return distanceBetweenPoints(check.getObjectPosition(),nearestAsteroid.getObjectPosition());
	}
	
	public double angleToPoint(AbstractObject check, Point.Double point)
	{
		double dx, dy;
		dx = point.x - check.getObjectPosition().x;
		dy = point.y - check.getObjectPosition().y;

		double angleInDegrees = ((Math.atan2(dx, dy)) * 180 / Math.PI);
		final double targetDir = angleInDegrees;

		if(targetDir >= check.getDirection())
		{
			if (targetDir - check.getDirection() < 180) 
			{
				angleInDegrees = -(targetDir - check.getDirection());
			} 
			else 
			{ 
				angleInDegrees = (360 - targetDir) + check.getDirection();
			}
		}

		if(check.getDirection() > targetDir)
		{
			if (check.getDirection() - targetDir < 180) 
			{
				angleInDegrees = check.getDirection() - targetDir;
			} 
			else 
			{ 
				angleInDegrees = -(360 - (check.getDirection() - targetDir));
			}
		}

		return (angleInDegrees);
	}

	public double angleToNearestObject(AbstractObject check, AbstractObject nearestEnemy)
	{		
		double dx, dy;
		dx = nearestEnemy.getObjectPosition().x - check.getObjectPosition().x;
		dy = nearestEnemy.getObjectPosition().y - check.getObjectPosition().y;

		double angleInDegrees = ((Math.atan2(dx, dy)) * 180 / Math.PI);
		double targetDir = angleInDegrees;

		if(targetDir >= check.getDirection())
		{
			if (targetDir - check.getDirection() < 180) 
			{
				angleInDegrees = -(targetDir - check.getDirection());
			} 
			else 
			{ 
				angleInDegrees = (360 - targetDir) + check.getDirection();
			}
		}

		if(check.getDirection() > targetDir)
		{
			if (check.getDirection() - targetDir < 180) 
			{
				angleInDegrees = check.getDirection() - targetDir;
			} 
			else 
			{ 
				angleInDegrees = -(360 - (check.getDirection() - targetDir));
			}
		}

		return (angleInDegrees);
	}

	public double distanceToNearestEnemy(AbstractObject check, AbstractObject nearestEnemy) {
		if(nearestEnemy == null)
			return Double.POSITIVE_INFINITY;
		return distanceBetweenPoints(check.getObjectPosition(), nearestEnemy.getObjectPosition());
	}

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
	public double distanceToNearestEnemyBullet(AbstractObject check, AbstractObject nearestEnemy) {
		// TODO Auto-generated method stub
		return 0;
	}

}
