package neuralnetwork;

import java.util.List;

import arena.objects.AbstractShip;

/**
 * Class that will hold neural network details used by specific ships.
 * @author Ross
 *
 */
public class NNetwork 
{

	//Arrays the represent the neurons inside the neural network.
	private Neuron inputNeurons[], outputNeurons[], hiddenNeurons[][];
	private NetworkInputs[] inputs;
	private NetworkOutputs[] outputs;
	
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
		inputs = NetworkInputs.values();
		outputs = NetworkOutputs.values();
	}
	
	/**
	 * Set up the neurons inside the neural network, in given time this will be given a
	 * chromosone representing the random choices for a given chromosome.
	 * 
	 * 
	 * At the current time, all neurons are currently being made with a threshold of 0.5, not 
	 * too sure if I'll remain with thresholds or go with a more basic NN due to time constraints.
	 */
	public void setupNeurons()
	{
		for(int i = 0 ; i < outputNeurons.length; i++)
		{
			outputNeurons[i] = new Neuron(ENeuronTypes.OUTPUT);
		}
		
		/*
		 * In order to add in the hidden neurons, I add them from right to left.
		 */
		int hiddenLength = hiddenNeurons[0].length;
		
		for(int i = hiddenNeurons.length - 1; i >= 0; i--)
		{
			for(int j = 0; j < hiddenLength; j++)
			{
				hiddenNeurons[i][j] = new Neuron(ENeuronTypes.HIDDEN);
			}
		}
		
		for(int i = 0; i < inputNeurons.length; i++)
		{
			inputNeurons[i] = new Neuron(ENeuronTypes.INPUT);
		}
		
		//Since all of the neurons have been initialised, now I set up the connections between them.
		setupNeuronConnections(hiddenLength,hiddenNeurons.length);
	}
	
	/**
	 * Set up all of the connections inside the neural network. Moved to another method to avoid
	 * messy code.
	 * 
	 * For connecting the neurons, we work from the far right to the left, as to ensure that the
	 * connections are working properly.
	 * @param hiddenCount Number of neurons in each hidden layer.
	 * @param hiddenLayerCount Number of hidden layers.
	 */
	private void setupNeuronConnections(int hiddenCount, int hiddenLayerCount)
	{
		for(int i = 0 ; i < outputNeurons.length; i++)
		{
			for(int j = 0 ; j < hiddenCount; j++)
			{
				//TODO Get weight from a given chromosome.
				outputNeurons[i].connectToNeuron(hiddenNeurons[hiddenLayerCount-1][j], 0.5);
			}
		}
		
		for(int i = hiddenLayerCount-1; i > 0 ; i--)
		{
			for(int j = 0; j < hiddenCount; j++)
			{
				for(int k = 0; k < hiddenCount; k++)
				{
					//TODO Get Weight from a given chromosome.
					hiddenNeurons[i][j].connectToNeuron(hiddenNeurons[i-1][k], 0.5);
				}
				
			}
		}
		
		for(int i = 0; i < hiddenCount; i++)
		{
			for(int j = 0; j < inputNeurons.length; j++)
			{
				//TODO Get weight from a given chromosome.
				hiddenNeurons[0][i].connectToNeuron(inputNeurons[j], 0.5);
			}
		}
	}
	
	/**
	 * This method will force an update of each neuron of the network in turn.
	 * 
	 * INPUT -> HIDDEN -> OUTPUTS.
	 */
	public void updateNetwork()
	{
		for(int i = 0; i < inputNeurons.length; i++)
		{
			inputNeurons[i].updateNeuron();
		}
		
		for(int i = 0; i < hiddenNeurons.length;i++)
		{
			for(int j = 0; j < hiddenNeurons[i].length; j++)
			{
				hiddenNeurons[i][j].updateNeuron();
			}
		}
		
		for(int i = 0; i < outputNeurons.length;i++)
		{
			outputNeurons[i].updateNeuron();
		}
	}
	
	public void updateInputNeurons(AbstractShip ship)
	{
		for(int i = 0 ; i < inputNeurons.length; i++)
		{
			inputNeurons[i].setInputValue(inputs[i].getInputValue(ship));
		}
	}

}
