package arena.objects.ships;

import arena.objects.AbstractShip;

public class TestShip extends AbstractShip 
{
	public TestShip(AbstractShip ship)
	{
		super(ship);
	}

	public TestShip(String shipName) 
	{
		super(shipName);
	}

	@Override
	public void determineAction() 
	{
		// TODO Auto-generated method stub

	}
}
