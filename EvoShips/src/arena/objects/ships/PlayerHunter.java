package arena.objects.ships;

import arena.ArenaWatcher;
import arena.objects.AbstractShip;
import arena.objects.objects.Asteroid;

public class PlayerHunter extends AbstractShip 
{
	private ArenaWatcher shipWatcher;
	
	@Override
	public AbstractShip cloneShip() 
	{
		PlayerHunter returnShip = new PlayerHunter();
		returnShip.setCloneOfShip(this);
		return returnShip;
	}

	@Override
	public String getShipName() 
	{
		return "PlayerHunter";
	}

	@Override
	public void determineAction() 
	{
		resetActionBooleans();

		shipWatcher = getShipsArenaWatcher();

		AbstractShip nearestShip = shipWatcher.getNearestShip(this);

		double distanceToNearestShip = shipWatcher.distanceToNearestEnemy(this);

		if(distanceToNearestShip > 0.3)
			forward = true;
		else
			backward = true;

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

}
