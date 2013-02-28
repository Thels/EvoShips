package arena.objects.ships;

import arena.ArenaWatcher;
import arena.objects.AbstractShip;

public class Turret extends AbstractShip 
{
	ArenaWatcher shipWatcher;
	public Turret(String shipName) 
	{
		super(shipName);
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
	public Turret cloneShip() 
	{
		Turret returnShip = new Turret(this.getShipName());
		returnShip.setCloneOfShip(this);
		return returnShip;
	}


}
