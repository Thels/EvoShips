package arena.objects.ships;

import genetic.Chromosome;
import neuralnetwork.ENetworkInputs;
import neuralnetwork.ENetworkOutputs;
import neuralnetwork.NNetwork;
import arena.objects.AbstractShip;

public class NNShip extends AbstractShip 
{
	private NNetwork neuralNetwork;
	private Chromosome shipChromosome;
	private ENetworkOutputs[] outputs;

	//These are merely place holder values, if time permitted, a UI would be added that would allow the changing of these values.
	private int HIDDEN_LAYER_COUNT = 3;
	private int NEURONS_PER_HIDDEN = 3;

	/**
	 * Create a new Neural Network ship. It creates the neural network based on the final fields above for the time being, if 
	 * this project ended up having an extensive UI, then this ship would recieve the values for the NNetwork as additional parameters.
	 * @param chromosome - The chromosome this NNShip is to base its actions upon.
	 */
	public NNShip(int numberOfHiddenLayers, int neuronsPerHiddenLayer, Chromosome chromosome) 
	{
		super();
		HIDDEN_LAYER_COUNT = numberOfHiddenLayers;
		NEURONS_PER_HIDDEN = neuronsPerHiddenLayer;
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

		
		right = outputs[0].isActivatedPositiveBound(networkOutputs[0]);
		
		left = outputs[0].isActivatedNegativeBound(networkOutputs[0]);

		//Moving forward is based on if the first output is greater than its bounds.
		forward = outputs[1].isActivatedPositiveBound(networkOutputs[1]);
		//Moving backward is based of if the first output is less than the negative lower bound.
		backward = outputs[1].isActivatedNegativeBound(networkOutputs[1]);

		fire = outputs[2].isActivatedPositiveBound(networkOutputs[2]);
	}

	@Override
	public NNShip cloneShip() 
	{
		NNShip returnShip = new NNShip(HIDDEN_LAYER_COUNT,NEURONS_PER_HIDDEN,shipChromosome);
		returnShip.setCloneOfShip(this);
		return returnShip;
	}

	@Override
	public String getShipName() 
	{
		return "NNetwork Ship";
	}


}
