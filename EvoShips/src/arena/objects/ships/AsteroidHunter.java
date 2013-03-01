package arena.objects.ships;

import arena.ArenaWatcher;
import arena.objects.AbstractShip;
import arena.objects.objects.Asteroid;

public class AsteroidHunter extends AbstractShip 
{
	ArenaWatcher shipWatcher;
	public AsteroidHunter() 
	{
		super();
	}
	
	@Override
	public void determineAction() 
	{
		resetActionBooleans();
		
		fire = true;
		backward = true;
		shipWatcher = getShipsArenaWatcher();
		
		Asteroid nearestAst = shipWatcher.getNearestAsteroid(this);
		if(nearestAst != null)
		{
			double angleToShip = shipWatcher.angleToObject(this, nearestAst);
			if(angleToShip < 0)
				right = true;
			else
				left = true;
			
		}
	}

	@Override
	public AsteroidHunter cloneShip() 
	{
		AsteroidHunter returnShip = new AsteroidHunter();
		returnShip.setCloneOfShip(this);
		return returnShip;
	}

	@Override
	public String getShipName() 
	{
		return "Asteroid Hunter";
	}


}
