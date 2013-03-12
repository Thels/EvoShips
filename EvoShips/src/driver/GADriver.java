package driver;

import genetic.GA;

import java.util.ArrayList;

import arena.objects.AbstractShip;
import arena.objects.ships.AsteroidHunter;
import arena.objects.ships.Turret;

/**
 * Class used to easily run a genetic algorithm with a list of pre-set values.
 * @author Ross
 *
 */
public class GADriver 
{
	private int NUMBER_GENERATIONS = 200;
	private int MAX_ASTEROIDS = 20;
	private int ASTEROID_SPAWN_CHANCE = 1;
	private int POPULATION_SIZE = 150;
	private int GAMES_PER_GENERATION = 200;
	
	/**
	 * Creates a new driver that will run with a set of ships and pre-defined values.
	 * @param firstShip First ship to compete with the NNShip
	 * @param secondShip Second ship to compete with the NNShip
	 * @param thirdShip Third ship to compete with the NNShip
	 */
	public GADriver(AbstractShip firstShip, AbstractShip secondShip, AbstractShip thirdShip)
	{
		ArrayList<AbstractShip> otherShips = new ArrayList<AbstractShip>();
		
		if(firstShip != null)
			otherShips.add(firstShip);
		if(secondShip != null)
			otherShips.add(secondShip);
		if(thirdShip != null)
			otherShips.add(thirdShip);
		
		GA geneticAlgorithm = new GA(otherShips, POPULATION_SIZE, NUMBER_GENERATIONS, GAMES_PER_GENERATION, MAX_ASTEROIDS, ASTEROID_SPAWN_CHANCE);
	}
	
	public static void main(String[] args)
	{
		AbstractShip other1 = new AsteroidHunter();
		AbstractShip other2 = new Turret();
		AbstractShip other3 = null;
		
		new GADriver(other1, other2, other3);
			
	}

}
