package arena.objects.objects;

import arena.collisions.CollisionPolygon;
import arena.objects.AbstractObject;
import arena.objects.EObjects;

public class Asteroid extends AbstractObject 
{

	@Override
	public EObjects getObjectType() 
	{
		return null;
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

}
