package arena.objects.ships;

import arena.objects.AbstractShip;

/**
 * Meet the derp ship, this little guy will just spin for days. In fact, he may even spin the other way around if he survives 500 ticks!
 * 
 * Derp.
 * 
 * @author Ross
 *
 */
public class DerpShip extends AbstractShip 
{
	private int tickCount;

	public DerpShip(String shipName) 
	{
		super(shipName);
		tickCount = 0;
	}

	@Override
	public DerpShip cloneShip() 
	{
		DerpShip returnShip = new DerpShip(this.getShipName());
		returnShip.setCloneOfShip(this);
		return returnShip;
	}

	@Override
	public void determineAction() 
	{
		tickCount++;
		if(tickCount > 500)
		{
			left = true;
		}
		else
			right = true;

	}

}