package arena.objects.ships;

import arena.ArenaWatcher;
import arena.objects.AbstractShip;
import arena.objects.objects.Asteroid;

public class AsteroidTurretShip extends AbstractShip 
{
	ArenaWatcher shipWatcher;
	public AsteroidTurretShip(String shipName) 
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
			double angleToShip = shipWatcher.angleToNearestObject(this, nearestAst);
			if(angleToShip < 0)
				right = true;
			else
				left = true;
			
		}

		
	
	}

	@Override
	public AsteroidTurretShip cloneShip() 
	{
		AsteroidTurretShip returnShip = new AsteroidTurretShip(this.getShipName());
		returnShip.setCloneOfShip(this);
		return returnShip;
	}


}
