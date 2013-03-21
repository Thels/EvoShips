package arena.objects.ships;

import arena.ArenaWatcher;
import arena.objects.AbstractShip;
import arena.objects.objects.Asteroid;

public class AsteroidTurret extends AbstractShip 
{
	ArenaWatcher shipWatcher;
	public AsteroidTurret() 
	{
		super();
	}
	
	@Override
	public void determineAction() 
	{
		resetActionBooleans();

		shipWatcher = getShipsArenaWatcher();
		
		Asteroid nearestAst = shipWatcher.getNearestAsteroid(this);
		if(nearestAst != null)
		{
			fire = true;
			double angleToShip = shipWatcher.angleToObject(this, nearestAst);
			if(angleToShip < 0)
				right = true;
			else
				left = true;
		}

	
	}

	@Override
	public AsteroidTurret cloneShip() 
	{
		AsteroidTurret returnShip = new AsteroidTurret();
		returnShip.setCloneOfShip(this);
		return returnShip;
	}

	@Override
	public String getShipName() 
	{
		return "Asteroid Turret";
	}


}
