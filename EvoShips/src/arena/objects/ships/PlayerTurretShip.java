package arena.objects.ships;

import arena.ArenaWatcher;
import arena.objects.AbstractShip;

public class PlayerTurretShip extends AbstractShip 
{
	ArenaWatcher shipWatcher;
	public PlayerTurretShip(String shipName) 
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
	public PlayerTurretShip cloneShip() 
	{
		PlayerTurretShip returnShip = new PlayerTurretShip(this.getShipName());
		returnShip.setCloneOfShip(this);
		return returnShip;
	}


}
