package genetic;


import io.ISaveableChromosome;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * Class that represents the weights that any given neural network can work with.
 * @author Ross
 *
 */
public class Chromosome implements ISaveableChromosome
{

	private double[] weights;
	private int chromosomeScore;

	/**
	 * Creates a new chromosome with a given set of pre-defined weights.
	 * @param weights
	 */
	public Chromosome(double[] weights) 
	{
		this.weights = weights;
		this.chromosomeScore = 0;
	}

	/**
	 * Generate a new chromosome with random weights, truely random creation of a new chromosome.
	 * @param numberWeights Number of weights the chromosome is to have.
	 */
	public Chromosome(int numberWeights)
	{
		this.weights = new double[numberWeights];

		Random ran = new Random();

		for(int i = 0; i < numberWeights; i++)
		{
			double val = ran.nextDouble();
			weights[i] = val;
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

	/**
	 * Set the score of this given chromosome. 
	 * @param score Value to set the score.
	 */
	public void setChromosomeScore(int score)
	{
		this.chromosomeScore = score;
	}

	/**
	 * Get the score this chromosome achieved.
	 * 
	 * REQUIRES setChromosoScore to be called prior or will be 0.
	 * 
	 * @return Score of this chromosome.
	 */
	public int getChromosomeScore()
	{
		return this.chromosomeScore;
	}

	@Override
	public void saveToFile() throws IOException 
	{
		try 
		{
			FileWriter fstream = new FileWriter(this.getChromosomeScore()+"-"+this.toString());
			BufferedWriter out = new BufferedWriter(fstream);
			for(Double d : this.getWeights())
				out.write(String.valueOf(d));
			//Close the output stream
			out.close();

		}
		catch (IOException e)
		{
			System.err.println("Error when saving Chromosome : "+this.toString());
			System.err.println("Error message : " + e.getMessage());
		}


	}


}
