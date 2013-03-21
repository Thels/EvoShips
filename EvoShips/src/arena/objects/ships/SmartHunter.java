package arena.objects.ships;

import arena.ArenaWatcher;
import arena.objects.AbstractShip;
import arena.objects.objects.Asteroid;

public class SmartHunter extends AbstractShip 
{
	ArenaWatcher shipWatcher;
	private double astDist;
	private double shipDist;
	public SmartHunter() 
	{
		super();
	}

	@Override
	public void determineAction() 
	{
		resetActionBooleans();

		shipWatcher = getShipsArenaWatcher();

		Asteroid nearestAst = shipWatcher.getNearestAsteroid(this);
		AbstractShip nearestShip = shipWatcher.getNearestShip(this);

		if(nearestAst != null)
		{
			astDist = shipWatcher.distanceToNearestAsteroid(nearestAst);
		}
		else 
			astDist = Double.POSITIVE_INFINITY;
		
		if(nearestShip != null)
		{
			shipDist = shipWatcher.distanceToNearestAsteroid(nearestShip);
		}
		else 
			shipDist = Double.POSITIVE_INFINITY;

		if(astDist < shipDist)
		{
			if(astDist < 0.2)
				backward = true;
			else
				forward = true;
			
			fire = true;
			double angleToShip = shipWatcher.angleToObject(this, nearestAst);
			if(angleToShip < 0)
				right = true;
			else
				left = true;

		}
		else if(shipDist < astDist)
		{
			if(shipDist < 0.2)
				backward = true;
			else
				forward = true;
			fire = true;
			double angleToShip = shipWatcher.angleToObject(this, nearestShip);
			if(angleToShip < 0)
				right = true;
			else
				left = true;
		}
		else
			backward = true;
	}

	@Override
	public SmartHunter cloneShip() 
	{
		SmartHunter returnShip = new SmartHunter();
		returnShip.setCloneOfShip(this);
		return returnShip;
	}

	@Override
	public String getShipName() 
	{
		return "Smart Turret";
	}


}
