package arena.objects.objects;

import java.awt.Point;
import java.util.Random;

import arena.collisions.CollisionPolygon;
import arena.objects.AbstractObject;
import arena.objects.EObjects;

public class Asteroid extends AbstractObject 
{
	private final double ASTEROID_SPEED = 0.001;
	private final double ASTEROID_SIZE = 0.08;
	private final int MAX_TICKS = 1400;
	private double theta;
	private int asteroidComplexity, tickCount;
	
	public Asteroid(Point.Double spawnLocation, double direction)
	{
		super(spawnLocation,direction);
		this.tickCount = 0;
		
		Random r = new Random();
		this.asteroidComplexity = r.nextInt(2)+5;
		r = null;
		
		this.theta = 360 / asteroidComplexity;
	}
	
	@Override
	public EObjects getObjectType() 
	{
		return EObjects.OBJ_ASTEROID;
	}

	@Override
	public void tickObject() 
	{
		this.tickCount++;
		
		if(this.tickCount >= MAX_TICKS)
			this.applyDamage(10000);
		
		double newPosX = getObjectPosition().x + (ASTEROID_SPEED * Math.cos(Math.toRadians(getDirection())));
		double newPosY = getObjectPosition().y + (ASTEROID_SPEED * Math.sin(Math.toRadians(getDirection())));
		
		this.setNewObjectPosition(new Point.Double(newPosX, newPosY));
	}

	@Override
	public CollisionPolygon getObjectCollisionModel() 
	{
		double startingAngle = 0;
		CollisionPolygon colPol = new CollisionPolygon(this.getObjectPosition(), asteroidComplexity);
		for(int i = 0 ; i < asteroidComplexity; i++)
		{
			double posX = getObjectPosition().x + (ASTEROID_SIZE/2 * Math.cos(Math.toRadians(startingAngle)));
			double posY = getObjectPosition().y + (ASTEROID_SIZE/2 * Math.sin(Math.toRadians(startingAngle)));
			startingAngle += theta;
			colPol.addVertice(posX, posY);
		}
		return colPol;
	}
	
	public int getAsteroidComplexity()
	{
		return this.asteroidComplexity;
	}

}