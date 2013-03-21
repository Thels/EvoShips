package driver;

import genetic.Chromosome;

import java.awt.Dimension;

import neuralnetwork.ENetworkInputs;
import neuralnetwork.ENetworkOutputs;
import ui.ArenaFrame;
import arena.Arena;
import arena.objects.AbstractShip;
import arena.objects.ships.NNShip;

public class NNShipDemo 
{
	private int hiddenCount = 5;
	private int hiddenDensity = 4;
	public NNShipDemo() 
	{
		//AbstractShip asteroidHunter = new DerpShip();
		
		int numberWeights = ( ENetworkInputs.values().length * hiddenDensity ) + 
		(hiddenCount * hiddenDensity * (hiddenCount-1)) + (hiddenDensity * ENetworkOutputs.values().length);

		AbstractShip nnShip = new NNShip(hiddenCount, hiddenDensity,new Chromosome(numberWeights));
		Arena demoArena = new Arena(20,1,5);
		
		//demoArena.addShipToArena(asteroidHunter);
		demoArena.addShipToArena(nnShip);
		
		ArenaFrame frame = new ArenaFrame(demoArena, new Dimension(500,500));
	}
	
	public static void main(String[] args)
	{
		new NNShipDemo();
	}

}
