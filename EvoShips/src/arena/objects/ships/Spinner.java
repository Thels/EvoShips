package arena.objects.ships;

import arena.objects.AbstractShip;

public class Spinner extends AbstractShip {

	

	@Override
	public AbstractShip cloneShip() 
	{
		Spinner s = new Spinner();
		s.setCloneOfShip(this);
		return s;
	}

	@Override
	public String getShipName() 
	{
		// TODO Auto-generated method stub
		return "Spinner";
	}

	@Override
	public void determineAction() 
	{
		left = true;
		fire = true;

	}

}
