package arena.objects.objects;

import java.awt.Point;

import arena.collisions.CollisionPolygon;
import arena.objects.AbstractObject;
import arena.objects.EObjects;

public class Bullet extends AbstractObject 
{

	private AbstractObject bulletCreator;
		
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
	public void tickObject() 
	{

	}

	@Override
	public CollisionPolygon getObjectCollisionModel() 
	{
		return null;
	}
	
	public AbstractObject getBulletCreator()
	{
		return this.bulletCreator;
	}
	

}
