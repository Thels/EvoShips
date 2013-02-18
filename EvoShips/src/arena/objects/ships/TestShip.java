package arena.objects.ships;

import arena.collisions.CollisionPolygon;
import arena.objects.AbstractShip;

public class TestShip extends AbstractShip 
{
	public TestShip(AbstractShip ship)
	{
		super(ship);
	}

	

	public TestShip(String shipName) {
		super(shipName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void determineAction() 
	{
		// TODO Auto-generated method stub

	}

	@Override
	public CollisionPolygon getObjectCollisionModel() {
		// TODO Auto-generated method stub
		return null;
	}

}
