package arena.objects.ships;

import arena.ArenaWatcher;
import arena.objects.AbstractShip;

public class TestShip extends AbstractShip 
{
	ArenaWatcher shipWatcher;
	public TestShip(String shipName) 
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
	public TestShip cloneShip() 
	{
		TestShip returnShip = new TestShip(this.getShipName());
		returnShip.setCloneOfShip(this);
		return returnShip;
	}


}
