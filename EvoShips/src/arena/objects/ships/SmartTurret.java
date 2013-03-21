package arena.objects.ships;

import arena.ArenaWatcher;
import arena.objects.AbstractShip;
import arena.objects.objects.Asteroid;

public class SmartTurret extends AbstractShip 
{
	ArenaWatcher shipWatcher;
	private double astDist;
	public SmartTurret() 
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

		if(astDist != Double.POSITIVE_INFINITY && astDist < 0.3)
		{

			fire = true;
			double angleToShip = shipWatcher.angleToObject(this, nearestAst);
			if(angleToShip < 0)
				right = true;
			else
				left = true;

		}
		else if(nearestShip != null)
		{
			fire = true;
			double angleToShip = shipWatcher.angleToObject(this, nearestShip);
			if(angleToShip < 0)
				right = true;
			else
				left = true;
		}
		else if(nearestAst != null)
		{
			fire = true;
			double angleToShip = shipWatcher.angleToObject(this, nearestAst);
			if(angleToShip < 0)
				right = true;
			else
				left = true;
		}

	}

	@Override
	public SmartTurret cloneShip() 
	{
		SmartTurret returnShip = new SmartTurret();
		returnShip.setCloneOfShip(this);
		return returnShip;
	}

	@Override
	public String getShipName() 
	{
		return "Smart Turret";
	}


}
