package arena.objects.objects;

import java.awt.Point;

import arena.collisions.CollisionPolygon;
import arena.objects.AbstractObject;
import arena.objects.EObjects;

public class Bullet extends AbstractObject 
{
	private final double BULLET_SPEED = 0.004;
	private AbstractObject bulletCreator;
		
	/**
	 * Creates a new bullet with the given parameters.
	 * @param bulletCreator Object that created this bullet.
	 * @param spawnLocation Location the bullet is spawning at.
	 * @param bulletDirection Direction the bullet is facing.
	 */
	public Bullet(AbstractObject bulletCreator, Point.Double spawnLocation, double bulletDirection)
	{
		super(spawnLocation, bulletDirection);
		this.bulletCreator = bulletCreator;
		
	}
	
	@Override
	public EObjects getObjectType()
	{
		return EObjects.OBJ_BULLET;
	}

	@Override
	/**
	 * A bullets tick object method merely moves it along it's path. If the bullet has left the bounds of the arena, then it will remove itself from the game.
	 */
	public void tickObject() 
	{
		double x = getObjectPosition().x;
		double y = getObjectPosition().y;
		
		if(x < 0 || x > 1 || y < 0 || y > 1)
		{
			this.applyDamage(1);
			return;
		}
			
		double newPosX = x + (BULLET_SPEED * Math.sin(Math.toRadians(getDirection())));
		double newPosY = y + (BULLET_SPEED * Math.cos(Math.toRadians(getDirection())));
		this.setNewObjectPosition(new Point.Double(newPosX, newPosY));

	}

	@Override
	public CollisionPolygon getObjectCollisionModel() 
	{
		double x = getObjectPosition().x;
		double y = getObjectPosition().y;
		CollisionPolygon bulletPoly = new CollisionPolygon(new Point.Double(x,y), 4);
		bulletPoly.addVertice(x-0.003125, y-0.003125);
		bulletPoly.addVertice(x-0.003125, y+0.003125);
		bulletPoly.addVertice(x+0.003125, y-0.003125);
		bulletPoly.addVertice(x+0.003125, y+0.003125);
		return bulletPoly;

	}
	
	/**
	 * Gets the object that created this bullet. 
	 * @return Object that created this bullet.
	 */
	public AbstractObject getBulletCreator()
	{
		return this.bulletCreator;
	}
	

}
