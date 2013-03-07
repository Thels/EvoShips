package arena.objects.ships;

import genetic.Chromosome;
import neuralnetwork.ENetworkInputs;
import neuralnetwork.ENetworkOutputs;
import neuralnetwork.NNetwork;
import arena.ArenaWatcher;
import arena.objects.AbstractShip;

public class NNShip extends AbstractShip 
{
	private ArenaWatcher shipWatcher;
	private NNetwork neuralNetwork;
	private Chromosome shipChromosome;
	private ENetworkOutputs[] outputs;

	//These are merely place holder values, if time permitted, a UI would be added that would allow the changing of these values.
	private final int HIDDEN_LAYER_COUNT = 4;
	private final int NEURONS_PER_HIDDEN = 4;

	public NNShip(Chromosome chromosome) 
	{
		super();
		shipChromosome = chromosome;
		neuralNetwork = new NNetwork(chromosome, ENetworkInputs.values().length, NEURONS_PER_HIDDEN, HIDDEN_LAYER_COUNT, ENetworkOutputs.values().length);
		neuralNetwork.setupNeurons();
		outputs = ENetworkOutputs.values();
	}

	@Override
	public void determineAction() 
	{
		//Reset all of the booleans.
		resetActionBooleans();

		//Update the input neurons.
		neuralNetwork.updateInputNeurons(this);

		//Get the output from the updated neural network.
		double[] networkOutputs = neuralNetwork.getNetworkOutputs();

		forward = outputs[0].isActivatedPositiveBound(networkOutputs[0]);
		backward = outputs[0].isActivatedNegativeBound(networkOutputs[0]);

		
		left = outputs[1].isActivatedPositiveBound(networkOutputs[1]);
		right = outputs[1].isActivatedNegativeBound(networkOutputs[1]);

		fire = outputs[2].isActivatedPositiveBound(networkOutputs[2]);
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
		return "NNetwork Ship";
	}


}
