package arena.objects.ships;

import arena.ArenaWatcher;
import arena.objects.AbstractShip;

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
		fire = true;
		shipWatcher = getShipsArenaWatcher();
		
		AbstractShip nearestShip = shipWatcher.getNearestShip(this);
		if(nearestShip != null)
		{
			double angleToShip = shipWatcher.angleToNearestObject(this, nearestShip);
			if(angleToShip < 0)
				left = true;
			else
				right = true;
			
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
