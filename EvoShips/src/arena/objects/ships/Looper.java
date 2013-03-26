package arena.objects.ships;

import arena.objects.AbstractShip;

public class Looper extends AbstractShip 
{
	private double turnVal =  180/1.7;
	private int tickCounter = 0;
	@Override
	public AbstractShip cloneShip() 
	{
		Looper l = new Looper();
		l.setCloneOfShip(this);
		return l;
	}

	@Override
	public String getShipName() 
	{
		return "Looper";
	}

	@Override
	public void determineAction() 
	{
		tickCounter++;
		if(tickCounter > turnVal)
		{
			if(left)
				right = true;
			else if(right)
				left = true;
			else
				left = true;
			
			tickCounter = 0;	
		}
		forward = true;
		fire = true;
	}

}
