package arena.objects.ships;

import neuralnetwork.NNetwork;
import neuralnetwork.NetworkInputs;
import neuralnetwork.NetworkOutputs;
import genetic.Chromosome;
import arena.ArenaWatcher;
import arena.objects.AbstractShip;

public class NNTank extends AbstractShip 
{
	private ArenaWatcher shipWatcher;
	private NNetwork neuralNetwork;
	private Chromosome shipChromosome;
	
	public NNTank(Chromosome chromosome) 
	{
		super();
		shipChromosome = chromosome;
		neuralNetwork = new NNetwork(chromosome, NetworkInputs.values().length, 4, 5, NetworkOutputs.values().length);
		neuralNetwork.setupNeurons();
		
	}
	
	@Override
	public void determineAction() 
	{
		resetActionBooleans();


	}

	@Override
	public NNTank cloneShip() 
	{
		NNTank returnShip = new NNTank(shipChromosome);
		returnShip.setCloneOfShip(this);
		return returnShip;
	}

	@Override
	public String getShipName() 
	{
		return "NNetwork Tank";
	}


}
