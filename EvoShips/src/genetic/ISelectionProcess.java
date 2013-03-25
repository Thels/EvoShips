package genetic;

import java.util.ArrayList;

public interface ISelectionProcess 
{

	
	/**
	 * Calculate the next population given an input population.
	 * @param inputPopulation Population to choose next populous from.
	 * @return List of chromosomes that make up the next population.
	 */
	public ArrayList<Chromosome> generateNewPopulation(ArrayList<Chromosome> currentPopulation);
	
}
