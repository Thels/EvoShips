package genetic;

import java.util.Random;

/**
 * Class that represents the weights that any given neural network can work with.
 * @author Ross
 *
 */
public class Chromosome 
{
	
	private double[] weights;
	
	/**
	 * Creates a new chromosome with a given set of pre-defined weights.
	 * @param weights
	 */
	public Chromosome(double[] weights) 
	{
		this.weights = weights;
	}
	
	/**
	 * Generate a new chromosome with random weights, truely random creation of a new chromosome.
	 * @param numberWeights Number of weights the chromosome is to have.
	 */
	public Chromosome(int numberWeights)
	{
		weights = new double[numberWeights];
		
		Random ran = new Random();
		
		for(int i = 0; i < numberWeights; i++)
		{
			weights[i] = ran.nextDouble();
		}
	}
	
	/**
	 * Get the weights that this chromosome represents.
	 * @return Weights array.
	 */
	public double[] getWeights()
	{
		return this.weights;
	}

}
