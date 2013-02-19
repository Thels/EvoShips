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
	private List<AbstractObject> ships = new ArrayList<AbstractObject>();
	private List<AbstractObject> asteroids = new ArrayList<AbstractObject>();
	private List<AbstractObject> bullets = new ArrayList<AbstractObject>();

	public ArenaWatcher(List<AbstractObject> gameObjects)
	{
		setObjects(gameObjects);
	}


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

	private double distanceBetweenPoints(Point.Double first, Point.Double second)
	{
		double sqdx = Math.pow((second.x - first.x),2);
		double sqdy = Math.pow((second.y - first.y),2);
		return Math.sqrt(sqdx + sqdy);
	}

	public AbstractShip getNearestShip(AbstractObject check) 
	{
		AbstractShip nearestShip = null;
		double disToNearest = Double.POSITIVE_INFINITY;

		// now we have nearest tank work out direction from tank t
		// in polar coordinates

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

	public Asteroid getNearestAsteroid(AbstractObject check) 
	{
		Asteroid nearestAsteroid = null;
		double disToNearest = Double.POSITIVE_INFINITY;

		// now we have nearest tank work out direction from tank t
		// in polar coordinates

		for(AbstractObject enemy: asteroids)
		{
			if(enemy.isObjectAlive())
			{
				double newDistance = distanceBetweenPoints(check.getObjectPosition(), enemy.getObjectPosition());
				if((newDistance < disToNearest) && !enemy.equals(check) && enemy.isObjectAlive())
				{
					disToNearest = newDistance;
					nearestAsteroid = (Asteroid) enemy;
				}
			}
		}

		return nearestAsteroid;
	}

	public double distanceToLeftWall(AbstractObject check) 
	{
		// TODO Auto-generated method stub
		return check.getObjectPosition().x;
	}


	public double distanceToRightWall(AbstractObject check) 
	{
		// TODO Auto-generated method stub
		return 1 - check.getObjectPosition().x;
	}

	public double distanceToTopWall(AbstractObject check) 
	{
		// TODO Auto-generated method stub
		return check.getObjectPosition().y;
	}

	public double distanceToBottomWall(AbstractObject check) 
	{
		// TODO Auto-generated method stub
		return 1-check.getObjectPosition().y;
	}

	public double distanceToNearestAsteroid(AbstractObject check, Asteroid nearestAsteroid)
	{
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
