package arena.objects;

import java.awt.Point;
import java.util.Random;

import arena.objects.objects.Asteroid;

/**
 * This class is used to generate random asteroids that move onto the game arena when they are spawned.
 * 
 * 
 * In order to do this, they are spawned off-screen with an on-screen target, they move towards and by this. 
 * Ships know where asteroids are when they spawn, but until they spawn, ships have no idea where they are.
 * @author Ross
 *
 */
public class AsteroidFactory 
{
	static Random r = new Random();

	/**
	 * Method to create an off-screen asteroid.
	 * 
	 * When an asteroid is created, it is created on a plane that exists just outside of the visible game area, 
	 * when it is created, it "targets" a point on the opposite plane and sets that as its target. These asteroids are random in where they spawn.
	 * 
	 * 
	 * Asteroids are locked in at size 0.08 which means that asteroids will spawn just outside of the game world by 0.09
	 * @return A random asteroid heading into the game.
	 */
	public static Asteroid createOffScreenAsteroid()
	{
		double asteroidDirection = 0;
		double xSpawnLoc, ySpawnLoc;
		if(r.nextDouble() < 0.5)
		{
			if(r.nextDouble() < 0.5)
				xSpawnLoc = -0.09;	
			else
				xSpawnLoc = 1.09;

			ySpawnLoc = r.nextDouble();

		}
		else
		{
			if(r.nextDouble() < 0.5)
				ySpawnLoc = -0.09;
			else
				ySpawnLoc = 1.09;

			xSpawnLoc = r.nextDouble();
		}

		if(xSpawnLoc < 0.5 && ySpawnLoc < 0.5)
		{
			asteroidDirection = 0 + (90 - 0) * r.nextDouble();
		}

		else if(xSpawnLoc < 0.5 && ySpawnLoc > 0.5)
		{
			asteroidDirection = 270 + (360 - 270) * r.nextDouble();
		}

		else if(xSpawnLoc > 0.5 && ySpawnLoc < 0.5)
		{
			asteroidDirection = 90 + (180 - 90) * r.nextDouble();
		}

		else if(xSpawnLoc > 0.5 && ySpawnLoc > 0.5)
		{
			asteroidDirection = 180 + (270 - 180) * r.nextDouble();
		}

		return new Asteroid(new Point.Double(xSpawnLoc,ySpawnLoc), asteroidDirection);
	}


}
