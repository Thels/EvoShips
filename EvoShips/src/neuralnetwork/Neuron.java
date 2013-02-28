package neuralnetwork;

import java.util.HashMap;

public class Neuron 
{
	/*
	 * For the current moment , neurons can have unlimited connections.
	 */
	private ENeuronTypes neuronType;
	private double inputValue, neuronValue;
	private HashMap<Neuron,Double> connectedWeightMap;
	
	/**
	 * Create a new neuron, with a given type.
	 * @param neuronType Type of neuron to create, INPUT/HIDDEN/OUTPUT.
	 */
	public Neuron(ENeuronTypes neuronType) 
	{
		this.neuronType = neuronType;
		this.connectedWeightMap = new HashMap<Neuron,Double>();
	}
	
	/**
	 * Add a connection to this neuron. It places it in a hashmap, so that I can easily check what neurons it's connected to
	 * and the respective weights of each of those connections.
	 * @param otherNeuron Other neuron to connect to.
	 * @param weight Weight of the connection.
	 */
	public void connectToNeuron(Neuron otherNeuron , double weight)
	{
		//Input neurons cannot be connected to anything. This shouldn't be called.
		if(this.neuronType == ENeuronTypes.INPUT)
			return;
		connectedWeightMap.put(otherNeuron, weight);
	}
	
	/**
	 * Get the type of neuron this is as governed by ENeuronTypes.
	 * @return Type of neuron ( INPUT / HIDDEN / OUTPUT ).
	 */
	public ENeuronTypes getNeuronType()
	{
		return this.neuronType;
	}
	
	/**
	 * Set the value of this neuron if it happens to be an input value.
	 * 
	 * The reason for this is because in order to start firing the neurons, the input values need to be set.
	 * @param inputValue
	 */
	public void setInputValue(double inputValue)
	{
		if(this.neuronType == ENeuronTypes.INPUT)
		{
			this.inputValue = inputValue;
		}
	}
	
	/**
	 * Get the value contained at this neuron.
	 * 
	 * Requires fire() to be called before value is set.
	 * @return Value of the neuron.
	 */
	public double getNeuronValue()
	{
		return this.neuronValue;
	}
	
	/**
	 * Update this neuron by calculating it's value from the weight of it's given connections.
	 */
	public void updateNeuron()
	{
		if(getNeuronType() == ENeuronTypes.INPUT)
		{
			this.neuronValue = this.inputValue;
			return;
		}
		
		this.neuronValue = 0;
		
		for(Neuron n : this.connectedWeightMap.keySet())
		{
			neuronValue += connectedWeightMap.get(n)*n.getNeuronValue();
		}
		
		neuronValue = Math.tanh(neuronValue);
	}
	
	
	

}
