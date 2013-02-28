package neuralnetwork;

import arena.objects.AbstractShip;

/**
 * An emum that stores a reference to all of the available inputs for a neural network.
 * @author Ross
 *
 */
public enum NetworkInputs 
{
	ENEMY_DISTANCE 
	{
		public double getInputValue(AbstractShip ship)
		{
			return ship.getShipsArenaWatcher().normalisedDistanceNearestEnemy(ship);
		}
	},

	ENEMY_ANGLE
	{
		public double getInputValue(AbstractShip ship)
		{
			return ship.getShipsArenaWatcher().normalisedAngleNearestEnemy(ship);
		}
	},

	ASTEROID_DISTANCE
	{
		public double getInputValue(AbstractShip ship)
		{
			return ship.getShipsArenaWatcher().normalisedDistanceNearestAsteroid(ship);
		}
	},

	ASTEROID_ANGLE
	{
		public double getInputValue(AbstractShip ship)
		{
			return ship.getShipsArenaWatcher().normalisedAngleNearestAsteroid(ship);
		}
	},

	BULLET_DISTANCE
	{
		public double getInputValue(AbstractShip ship)
		{
			return ship.getShipsArenaWatcher().normalisedDistanceNearestEnemyBullet(ship);
		}
	},
	
	BULLET_ANGLE
	{
		public double getInputValue(AbstractShip ship)
		{
			return ship.getShipsArenaWatcher().normalisedAngleNearestEnemyBullet(ship);
		}
	};
	
	public abstract double getInputValue(AbstractShip ship);
}
