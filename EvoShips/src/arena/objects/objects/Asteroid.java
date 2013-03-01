package arena.objects.objects;

import java.awt.Point;
import java.util.Random;

import arena.collisions.CollisionPolygon;
import arena.objects.AbstractObject;
import arena.objects.EObjects;

public class Asteroid extends AbstractObject 
{
	private final double ASTEROID_SPEED = 0.0015;
	public final double ASTEROID_SIZE = 0.08;
	private final int MAX_TICKS = 1400;
	private double theta;
	private int asteroidComplexity, tickCount;
	
	/**
	 * Creates a new asteroid with the following parameters.
	 * @param spawnLocation Location the asteroid is spawning.
	 * @param direction Direction the asteroid is facing.
	 */
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
	/**
	 * When an asteroid ticks, it just follows its path until it has lived for a "Max Tick". At which point the asteroid kills itself.
	 */
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
	
	/**
	 * Gets the complexity of this asteroid, this is mainly used for the drawing code to be able to draw the correct shape of each asteroid later on.
	 * @return The complexity of the asteroid.
	 */
	public int getAsteroidComplexity()
	{
		return this.asteroidComplexity;
	}
	
	/**
	 * Retrieve the theta value for this asteroid, which is the angle in between every point being drawn from the centre point.
	 * @return Theta, being 360 / asteroidComplexity
	 */
	public double getTheta()
	{
		return theta;
	}
	
	
	

}
