package neuralnetwork;

/**
 * Class that will hold neural network details used by specific ships.
 * @author Ross
 *
 */
public class NNetwork 
{

	//Arrays the represent the neurons inside the neural network.
	private Neuron inputNeurons[], outputNeurons[], hiddenNeurons[][];
	
	/**
	 * Create a new neural network, with the given amount of input / hidden / output layers.
	 * @param inputCount Number of input layers.
	 * @param hiddenCount Number of hidden neurons in each layer.
	 * @param numberHiddenLayers Number of hidden layers that exist in the NN.
	 * @param outputCount Number of output layers.
	 */
	public NNetwork(int inputCount, int hiddenCount, int numberHiddenLayers, int outputCount) 
	{
		inputNeurons = new Neuron[inputCount];
		outputNeurons = new Neuron[outputCount];
		hiddenNeurons = new Neuron[numberHiddenLayers][hiddenCount];
	}

}
