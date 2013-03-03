package arena.objects.ships;

import java.util.List;

import neuralnetwork.NNetwork;
import neuralnetwork.ENetworkInputs;
import neuralnetwork.ENetworkOutputs;
import genetic.Chromosome;
import arena.ArenaWatcher;
import arena.objects.AbstractShip;

public class NNShip extends AbstractShip 
{
	private ArenaWatcher shipWatcher;
	private NNetwork neuralNetwork;
	private Chromosome shipChromosome;
	private ENetworkOutputs[] outputs;
	
	public NNShip(Chromosome chromosome) 
	{
		super();
		shipChromosome = chromosome;
		neuralNetwork = new NNetwork(chromosome, ENetworkInputs.values().length, 4, 5, ENetworkOutputs.values().length);
		neuralNetwork.setupNeurons();
		outputs = ENetworkOutputs.values();
	}
	
	@Override
	public void determineAction() 
	{
		resetActionBooleans();
		neuralNetwork.updateInputNeurons(this);
		
		double[] networkOutputs = neuralNetwork.getNetworkOutputs();
		
		/*
		 * The order of the output neurons. For checking the values.
		 * 
		 * Copy/pasted from NetworkOutputs enum.
		 * 	FORWARD(0.05),
			MOVE_BACKWARD(0.05),
			TURN_LEFT(0.05),
			TURN_RIGHT(0.05),
			FIRE(0.01);
		 */
		
		forward = outputs[0].isActivated(networkOutputs[0]);
		backward = outputs[1].isActivated(networkOutputs[1]);
		left = outputs[2].isActivated(networkOutputs[2]);
		right = outputs[3].isActivated(networkOutputs[3]);
		fire = outputs[4].isActivated(networkOutputs[4]);
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
