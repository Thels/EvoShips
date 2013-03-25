package genetic;

import genetic.selectionAlgorithms.RouletteSection;
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
public class GeneticAlgorithm 
{
	private ArrayList<Chromosome> population;

	//The chance when breeding a new chromosome that crossover will occur.
	public static double CROSSOVER_CHANCE = 0.3;
	//The chance when breeding a new chromosome that mutation will occur ( per allele ).
	public static double MUTATION_CHANCE = 0.4;
	//These are merely place holder values, if time permitted, a UI would be added that would allow the changing of these values.
	private final int HIDDEN_LAYER_COUNT = 3;
	private final int NEURONS_PER_HIDDEN = 9;

	private int maxAsteroids, asteroidSpawnChance;

	private GeneticIO geneticIOHandler;
	private int generationCounter;
	private ISelectionProcess selectionProcess;

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
	public GeneticAlgorithm(ArrayList<AbstractShip> otherShips, int populationSize, int generations, int gamesPerGeneration, int maxAsteroids, int asteroidSpawnChance) 
	{
		if(populationSize < 1 || generations < 1 || gamesPerGeneration < 1)
			throw new InvalidParameterException("Invalid parameters for GA, must have >1 for values.");

		this.maxAsteroids = maxAsteroids;
		this.asteroidSpawnChance = asteroidSpawnChance;
		
		generationCounter = 0;
		selectionProcess = new RouletteSection();
		//Generate initial population.
		population = new ArrayList<Chromosome>();
		population = generateInitialPopulation(populationSize);

		geneticIOHandler = new GeneticIO();
		geneticIOHandler.createChromosomeFolder();
		geneticIOHandler.writeParametersToFile(otherShips, populationSize, generations, gamesPerGeneration, maxAsteroids, asteroidSpawnChance,MUTATION_CHANCE , CROSSOVER_CHANCE);
		geneticIOHandler.createFitnessFile();
		
		for(int i = 0 ; i < generations; i++)
		{
			runGeneration(otherShips,population, gamesPerGeneration);
			population = selectionProcess.generateNewPopulation(population);
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
		generationCounter++;
		ArrayList<AbstractShip> batchShips = new ArrayList<AbstractShip>();
		BatchArena arena;
		for(Chromosome chromo : population)
		{
			batchShips.clear();
			batchShips.addAll(otherShips);
			NNShip nnShip = new NNShip(HIDDEN_LAYER_COUNT,NEURONS_PER_HIDDEN,chromo);
			batchShips.add(nnShip);
			arena = new BatchArena(batchShips, gamesPerGeneration, maxAsteroids, asteroidSpawnChance);
			arena.startBatch();

			//Not sure what else to do here, it's a busy wait.
			while(arena.isBatchRunning())
			{}

			//At the end of each chromosome, set the scores of each.
			chromo.setChromosomeScore(nnShip.getScore());

		}
		geneticIOHandler.addToFitnessFile(generationCounter, gamesPerGeneration, population);
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
