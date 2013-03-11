package neuralnetwork;

import arena.objects.AbstractShip;

/**
 * An emum that stores a reference to all of the available inputs for a neural network.
 * @author Ross
 *
 */
public enum ENetworkInputs 
{
	ENEMY_DISTANCE 
	{
		public double getInputValue(AbstractShip ship)
		{
			double temp = ship.getShipsArenaWatcher().normalisedDistanceNearestEnemy(ship);
			return temp;
		}
	},

	ENEMY_ANGLE
	{
		public double getInputValue(AbstractShip ship)
		{
			double temp = ship.getShipsArenaWatcher().normalisedAngleNearestEnemy(ship);
			return temp;
		}
	},

	ASTEROID_DISTANCE
	{
		public double getInputValue(AbstractShip ship)
		{
			double temp = ship.getShipsArenaWatcher().normalisedDistanceNearestAsteroid(ship);
			return temp;
		}
	},

	ASTEROID_ANGLE
	{
		public double getInputValue(AbstractShip ship)
		{
			double temp =  ship.getShipsArenaWatcher().normalisedAngleNearestAsteroid(ship);
			return temp;
		}
	},

	BULLET_DISTANCE
	{
		public double getInputValue(AbstractShip ship)
		{
			double temp = ship.getShipsArenaWatcher().normalisedDistanceNearestEnemyBullet(ship);
			return temp;
		}
	},
	
	BULLET_ANGLE
	{
		public double getInputValue(AbstractShip ship)
		{
			double temp = ship.getShipsArenaWatcher().normalisedAngleNearestEnemyBullet(ship);
			return temp;
		}
	},
	
	LEFT_WALL_DISTANCE
	{
		public double getInputValue(AbstractShip ship)
		{
			double temp = ship.getObjectPosition().x;
			return temp;
		}
	},
	
	RIGHT_WALL_DISTANCE
	{
		public double getInputValue(AbstractShip ship)
		{
			double temp = 1 - ship.getObjectPosition().x;
			return temp;
		}
	},
	
	TOP_WALL_DISTANCE
	{
		public double getInputValue(AbstractShip ship)
		{
			double temp = ship.getObjectPosition().y;
			return temp;
		}
	},
	
	BOTTOM_WALL_DISTANCE
	{
		public double getInputValue(AbstractShip ship)
		{
			double temp = 1 - ship.getObjectPosition().y;
			return temp;
		}
	};
	
	
	public abstract double getInputValue(AbstractShip ship);
}
