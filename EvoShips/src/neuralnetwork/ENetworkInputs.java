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
	};
	
	public abstract double getInputValue(AbstractShip ship);
}
