package driver;

import genetic.Chromosome;

import java.awt.Dimension;

import neuralnetwork.ENetworkInputs;
import neuralnetwork.ENetworkOutputs;
import ui.ArenaFrame;
import arena.Arena;
import arena.objects.AbstractShip;
import arena.objects.ships.AsteroidHunter;
import arena.objects.ships.NNShip;

public class NNShipDemo 
{

	public NNShipDemo() 
	{
		AbstractShip asteroidHunter = new AsteroidHunter();
		
		int numberWeights = ( ENetworkInputs.values().length * 5 ) + 
		(5 * 5 * (5-1)) + (5 * ENetworkOutputs.values().length);

		AbstractShip nnShip = new NNShip(new Chromosome(numberWeights));
		Arena demoArena = new Arena(20,1,5);
		
		demoArena.addShipToArena(asteroidHunter);
		demoArena.addShipToArena(nnShip);
		
		ArenaFrame frame = new ArenaFrame(demoArena, new Dimension(500,500));
	}
	
	public static void main(String[] args)
	{
		new NNShipDemo();
	}

}
