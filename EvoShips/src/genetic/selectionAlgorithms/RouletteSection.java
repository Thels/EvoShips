package genetic.selectionAlgorithms;

import java.util.ArrayList;
import java.util.Random;

import genetic.Chromosome;
import genetic.GeneticAlgorithm;
import genetic.GAHelper;
import genetic.ISelectionProcess;

public class RouletteSection implements ISelectionProcess 
{
	//Should elitism be used in this GA. Elitism is the copying of one or a few of some of the best chromosomes in each generation.
	private final boolean ELITISM = true;
	private final int ELITISM_COPY_COUNT = 1;

	@Override
	public ArrayList<Chromosome> generateNewPopulation(ArrayList<Chromosome> inputPopulation) 
	{
		ArrayList<Chromosome> nextPopulation = new ArrayList<Chromosome>();

		int populationCounter = 0;

		/*
		 * If elitish has been set to true, then the system will copy the top ELITISM_COPY_COUNT scoring chromosomes into
		 * the new population, this is good to be able to retain the higher scoring ships in between each generation.
		 * 
		 * For elitism, I find the highest scoring chromosome, add it to the new population, and delete it from the input population.
		 * This means that the next getElitist call wont return the same chromosome!
		 */
		if(ELITISM)
		{
			for(int i = 0 ; i < ELITISM_COPY_COUNT ; i++)
			{
				Chromosome eliteChromo = getElitist(inputPopulation);
				nextPopulation.add(eliteChromo);
				inputPopulation.remove(eliteChromo);
				populationCounter++;
			}
		}

		while(populationCounter < inputPopulation.size())
		{
			Chromosome dad, mum, child;
			dad = rouletteChoice(inputPopulation);
			mum = rouletteChoice(inputPopulation);

			if(Math.random() < GeneticAlgorithm.CROSSOVER_CHANCE)
			{
				child = GAHelper.breedChromosones(dad, mum);
			}
			else
			{
				//If crossover isn't taking place, then choose the child based on the mother or father.
				if(Math.random() >= 0.5)
					child = dad;
				else
					child = mum;
			}

			//Mutate the chromosome, small chance for each allele to be changed.
			GAHelper.mutateChromosone(child, GeneticAlgorithm.MUTATION_CHANCE);

			nextPopulation.add(child);
			populationCounter++;
		}

		return nextPopulation;
	}

	/**
	 * Using roulette choice on a given population to select a given chromosome. Used to select a chromosome
	 * that will represent the mother and father chromosomes.
	 * 
	 * The way roulette selection works, is using each chromosomes score as its probability to be chosen. To begin with I sum
	 * the population, and choose a random number from 1-this sum. I then loop through the population, keeping track of a value which
	 * each score gets added top, as soon as the total is above the choice, this means that this chromosome has been chosen. Through 
	 * this method, chromosomes with a larger score have a larger chance to be selected.
	 * 
	 * @param inputPopulation Population of chromosomes to choose from.
	 * @return A chromosome, selected through a process of probability.
	 */
	private Chromosome rouletteChoice(ArrayList<Chromosome> inputPopulation)
	{
		int populationSum = sumPopulation(inputPopulation);
		Random r = new Random();
		int choice = r.nextInt(populationSum)+1;
		int totalSoFar = 0;

		for(Chromosome c : inputPopulation)
		{
			totalSoFar += c.getChromosomeScore();
			if(totalSoFar >= choice)
				return c;
		}

		return null;
	}

	/**
	 * Sum the scores of a given chromosome population.
	 * @param population Population to sum.
	 * @return Summation of all populations getScore().
	 */
	private int sumPopulation(ArrayList<Chromosome> population) 
	{
		int total = 0;
		for(Chromosome c : population)
			total += c.getChromosomeScore();
		return total;
	}

	/**
	 * Get the elitist chromosome of a given population. Which is the highest scoring of a given populace.
	 * @param inputPopulation Population to check.
	 * @return Highest scoring chromosome.
	 */
	private Chromosome getElitist(ArrayList<Chromosome> inputPopulation) 
	{
		Chromosome eliteChromosome = null;

		int highestScore = 0;

		for(Chromosome c : inputPopulation)
		{
			int cScore = c.getChromosomeScore();

			if(cScore > highestScore)
			{
				eliteChromosome = c;
				highestScore = cScore;
			}
		}


		return new Chromosome(eliteChromosome.getWeights());
	}

}
