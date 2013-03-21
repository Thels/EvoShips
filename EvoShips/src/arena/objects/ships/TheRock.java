package arena.objects.ships;

import arena.objects.AbstractShip;

public class TheRock extends AbstractShip
{

	@Override
	public AbstractShip cloneShip() 
	{
		TheRock returnShip = new TheRock();
		returnShip.setCloneOfShip(this);
		return returnShip;
	}

	@Override
	public String getShipName() 
	{
		return "TheRock";
	}

	@Override
	public void determineAction() 
	{

	}

}
