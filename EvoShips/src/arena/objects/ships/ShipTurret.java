package arena.objects.ships;

import arena.ArenaWatcher;
import arena.objects.AbstractShip;

public class ShipTurret extends AbstractShip 
{
	ArenaWatcher shipWatcher;
	public ShipTurret() 
	{
		super();
	}
	
	@Override
	public void determineAction() 
	{
		resetActionBooleans();
	
		shipWatcher = getShipsArenaWatcher();
		
		AbstractShip nearestShip = shipWatcher.getNearestShip(this);
		if(nearestShip != null)
		{
			double angleToShip = shipWatcher.angleToObject(this, nearestShip);
			if(angleToShip < 0)
				right = true;
			else
				left = true;
			
			fire = true;
		}

	}

	@Override
	public ShipTurret cloneShip() 
	{
		ShipTurret returnShip = new ShipTurret();
		returnShip.setCloneOfShip(this);
		return returnShip;
	}

	@Override
	public String getShipName() 
	{
		return "Turret";
	}


}
