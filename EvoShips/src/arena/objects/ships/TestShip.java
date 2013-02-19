package arena.objects.ships;

import arena.objects.AbstractShip;

public class TestShip extends AbstractShip 
{
	public TestShip(String shipName) 
	{
		super(shipName);
	}
	
	@Override
	public void determineAction() 
	{
		// TODO Auto-generated method stub

	}

	@Override
	public TestShip cloneShip() 
	{
		TestShip returnShip = new TestShip(this.getShipName());
		returnShip.setCloneOfShip(this);
		return returnShip;
	}


}
