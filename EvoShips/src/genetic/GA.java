package genetic;

import io.GeneticIO;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Random;

import neuralnetwork.ENetworkInputs;
import neuralnetwork.ENetworkOutputs;
import arena.BatchArena;
import arena.objects.AbstractShip;
import arena.objects.ships.NNShip;

/**
 * This class is an adapted version of the GA I used for a snake AI project in 3rd year.
 * @author Ross
 *
 */
public class GA 
{
	private ArrayList<Chromosome> population;

	//These are merely place holder values, if time permitted, a UI would be added that would allow the changing of these values.
	private final int HIDDEN_LAYER_COUNT = 4;
	private final int NEURONS_PER_HIDDEN = 5;

	private int maxAsteroids, asteroidSpawnChance;

	//Should elitism be used in this GA. Elitism is the copying of one or a few of some of the best chromosomes in each generation.
	private final boolean ELITISM = true;
	private final int ELITISM_COPY_COUNT = 5;

	//The chance when breeding a new chromosome that crossover will occur.
	private final double CROSSOVER_CHANCE = 0.3;
	//The chance when breeding a new chromosome that mutation will occur ( per allele ).
	private final double MUTATION_CHANCE = 0.1;

	private GeneticIO geneticIOHandler;

	/**
	 * Creates a new genetic algorithm that will run with the given parameters.
	 * 
	 * At the current time, it is only possible to run one NNShip with a set of given ships, although with further expansion, this 
	 * GA would be able to run with any number of NNShips against any number of enemy ships.
	 * 
	 * MUST have other ships and values for populationSize, generations and gamesPerGeneration must be >1.
	 * 
	 * @param otherShips Ships that the NNShips will compete with.
	 * @param populationSize How many chromosomes will be in each generation.
	 * @param generations How many generations will be ran by the GA.
	 * @param gamesPerGeneration How many games are played per generation.
	 * @param maxAsteroids Maximum number of asteroids in each of the arenas created by the GA.
	 * @param asteroidSpawnChance % chance to spawn an asteroid in each arena per tick. ( 1-100 ).
	 */
	public GA(ArrayList<AbstractShip> otherShips, int populationSize, int generations, int gamesPerGeneration, int maxAsteroids, int asteroidSpawnChance) 
	{
		if(otherShips.size() == 0)
			throw new InvalidParameterException("No other ships are taking part.");
		if(populationSize < 1 || generations < 1 || gamesPerGeneration < 1)
			throw new InvalidParameterException("Invalid parameters for GA, must have >1 for values.");

		this.maxAsteroids = maxAsteroids;
		this.asteroidSpawnChance = asteroidSpawnChance;

		//Generate initial population.
		population = new ArrayList<Chromosome>();
		population = generateInitialPopulation(populationSize);

		geneticIOHandler = new GeneticIO();
		geneticIOHandler.createChromosomeFolder();
		geneticIOHandler.writeParametersToFile(otherShips, populationSize, generations, gamesPerGeneration, maxAsteroids, asteroidSpawnChance,MUTATION_CHANCE , CROSSOVER_CHANCE);

		for(int i = 0 ; i < generations; i++)
		{
			runGeneration(otherShips,population, gamesPerGeneration);
			population = calculateNextPopulation(population);
		}

	}

	/**
	 * Run generation will run a batch for each chromosome, a batch of the given games per generation.
	 * @param otherShips Other ships taking part in the generation.
	 * @param population The chromosomes that make up this generation.
	 * @param gamesPerGeneration How many games have to be ran in this generation.
	 */
	private void runGeneration(ArrayList<AbstractShip> otherShips, ArrayList<Chromosome> population, int gamesPerGeneration) 
	{
		ArrayList<AbstractShip> batchShips = new ArrayList<AbstractShip>();
		BatchArena arena;
		for(Chromosome chromo : population)
		{
			batchShips.clear();
			batchShips.addAll(otherShips);
			NNShip nnShip = new NNShip(chromo);
			batchShips.add(nnShip);
			arena = new BatchArena(batchShips, gamesPerGeneration, maxAsteroids, asteroidSpawnChance);
			arena.startBatch();

			//Not sure what else to do here, it's a busy wait.
			while(arena.isBatchRunning())
			{}

			//At the end of each chromosome, set the scores of each.
			chromo.setChromosomeScore(nnShip.getScore());

		}

		resetShipScores(batchShips);
		saveHighestScore();

	}

	/**
	 * Search through the field population, find the highest scoring chromosome, and use the GeneticIO to save its details to a file.
	 */
	private void saveHighestScore() 
	{
		int highest = 0;
		Chromosome chromo = null;
		for(Chromosome c : population)
		{
			if(c.getChromosomeScore() > highest)
			{
				highest = c.getChromosomeScore();
				chromo = c;
			}
		}

		if(chromo != null)
		{
			geneticIOHandler.saveChromosomeToFolder(chromo);
		}

	}

	/**
	 * Calculate the next population given an input population.
	 * @param inputPopulation Population to choose next populous from.
	 * @return List of chromosomes that make up the next population.
	 */
	private ArrayList<Chromosome> calculateNextPopulation(ArrayList<Chromosome> inputPopulation) 
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

			if(Math.random() < CROSSOVER_CHANCE)
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
			GAHelper.mutateChromosone(child, MUTATION_CHANCE);

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
		int populationSum = sumPopulation(population);
		Random r = new Random();
		int choice = r.nextInt(populationSum)+1;
		int totalSoFar = 0;

		for(Chromosome c : population)
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

	/**
	 * Reset all the scores of a given set of ships.
	 * Just a handy little helper method, to avoid having another for loop in the above code.
	 * 
	 * @param batchShips Ships to reset the score of.
	 */
	private void resetShipScores(ArrayList<AbstractShip> batchShips) 
	{
		for(AbstractShip s : batchShips)
			s.resetScore();

	}

	/**
	 * Generate the initial population to be used by the GA.
	 * @param populationSize Number of chromosomes to generate.
	 * @return List of chromosomes generated.
	 */
	private ArrayList<Chromosome> generateInitialPopulation(int populationSize) 
	{
		ArrayList<Chromosome> populace = new ArrayList<Chromosome>();

		//Stuipidly long calculation here. Hopefully it's correct...
		int numberOfChromosomeConnections = ( ENetworkInputs.values().length * NEURONS_PER_HIDDEN ) + 
				(NEURONS_PER_HIDDEN * NEURONS_PER_HIDDEN * (HIDDEN_LAYER_COUNT-1)) + (NEURONS_PER_HIDDEN * ENetworkOutputs.values().length);

		for(int i = 0 ; i < populationSize; i++)
		{
			populace.add(new Chromosome(numberOfChromosomeConnections));
		}

		return populace;
	}

}
