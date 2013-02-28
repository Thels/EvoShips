package arena.objects.ships;

import arena.ArenaWatcher;
import arena.objects.AbstractShip;

public class KamakazieShip extends AbstractShip 
{
	ArenaWatcher shipWatcher;
	public KamakazieShip(String shipName) 
	{
		super(shipName);
	}
	
	@Override
	public void determineAction() 
	{
		resetActionBooleans();
	
		shipWatcher = getShipsArenaWatcher();
		forward = true;
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
	public KamakazieShip cloneShip() 
	{
		KamakazieShip returnShip = new KamakazieShip(this.getShipName());
		returnShip.setCloneOfShip(this);
		return returnShip;
	}


}
