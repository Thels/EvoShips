package arena;

import java.util.ArrayList;

import arena.objects.AbstractShip;

/**
 * This class represents an arena with one ship just striving to stay alive for a given amount of time. 
 * 
 * In this arena, a higher score means surviving for longer, while killing lots of asteroids.
 * @author Ross
 *
 */
public class SinglePlayerArena extends AbstractArena 
{

	public SinglePlayerArena(int maxAsteroidCount, int asteroidSpawnChance, int tickDelay) 
	{
		super(maxAsteroidCount, asteroidSpawnChance, tickDelay);
	}

	@Override
	public boolean updateGameStatus(ArrayList<AbstractShip> gameShips)
	{
		for(AbstractShip ship :  gameShips)
			if(!ship.isObjectAlive())
				return false;
		return true;
	}

}
