package arena.objects.ships;

import java.util.List;

import neuralnetwork.NNetwork;
import neuralnetwork.NetworkInputs;
import neuralnetwork.NetworkOutputs;
import genetic.Chromosome;
import arena.ArenaWatcher;
import arena.objects.AbstractShip;

public class NNShip extends AbstractShip 
{
	private ArenaWatcher shipWatcher;
	private NNetwork neuralNetwork;
	private Chromosome shipChromosome;
	private NetworkOutputs[] outputs;
	
	public NNShip(Chromosome chromosome) 
	{
		super();
		shipChromosome = chromosome;
		neuralNetwork = new NNetwork(chromosome, NetworkInputs.values().length, 4, 5, NetworkOutputs.values().length);
		neuralNetwork.setupNeurons();
		outputs = NetworkOutputs.values();
	}
	
	@Override
	public void determineAction() 
	{
		resetActionBooleans();
		neuralNetwork.updateInputNeurons(this);
		
		double[] networkOutputs = neuralNetwork.getNetworkOutputs();
		
		for()


	}

	@Override
	public NNShip cloneShip() 
	{
		NNShip returnShip = new NNShip(shipChromosome);
		returnShip.setCloneOfShip(this);
		return returnShip;
	}

	@Override
	public String getShipName() 
	{
		return "NNetwork Tank";
	}


}
