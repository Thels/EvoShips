package ui;

import genetic.Chromosome;

import java.awt.Dimension;

import neuralnetwork.ENetworkInputs;
import neuralnetwork.ENetworkOutputs;

import arena.Arena;
import arena.objects.AbstractShip;
import arena.objects.ships.AsteroidHunter;
import arena.objects.ships.AsteroidTurret;
import arena.objects.ships.DerpShip;
import arena.objects.ships.KamakazieShip;
import arena.objects.ships.NNShip;
import arena.objects.ships.Turret;

public class UITest 
{

	public UITest()
	{
		
		int numberWeights = ( ENetworkInputs.values().length * 4 ) + 
				(4 * 4 * (4-1)) + (4 * ENetworkOutputs.values().length);

		Chromosome temp = new Chromosome(numberWeights);	
		
		NNShip test0 = new NNShip(temp);
		AbstractShip test1 = new AsteroidHunter();
		AbstractShip test2 = new AsteroidHunter();
		AbstractShip test3 = new KamakazieShip();

		Arena testGame = new Arena(20, 50, 5);
		testGame.addShipToArena(test0);
		testGame.addShipToArena(test1);
		
		ArenaFrame frame = new ArenaFrame(testGame, new Dimension(650,650));
		
	}
	
	public static void main(String[] args)
	{
		new UITest();
	}


}
