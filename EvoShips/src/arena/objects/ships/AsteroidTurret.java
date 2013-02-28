package arena.objects.ships;

import arena.ArenaWatcher;
import arena.objects.AbstractShip;
import arena.objects.objects.Asteroid;

public class AsteroidTurret extends AbstractShip 
{
	ArenaWatcher shipWatcher;
	public AsteroidTurret(String shipName) 
	{
		super(shipName);
	}
	
	@Override
	public void determineAction() 
	{
		resetActionBooleans();
		fire = true;
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
	public AsteroidTurret cloneShip() 
	{
		AsteroidTurret returnShip = new AsteroidTurret(this.getShipName());
		returnShip.setCloneOfShip(this);
		return returnShip;
	}


}