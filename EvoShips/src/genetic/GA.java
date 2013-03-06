package genetic;

import io.GeneticIO;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import neuralnetwork.ENetworkInputs;
import neuralnetwork.ENetworkOutputs;
import arena.BatchArena;
import arena.objects.AbstractShip;
import arena.objects.ships.AsteroidHunter;
import arena.objects.ships.NNShip;

public class GA 
{

	private ArrayList<Chromosome> population;
	//These are merely place holder values, if time permitted, a UI would be added that would allow the changing of these values.
	private final int HIDDEN_LAYER_COUNT = 4;
	private final int NEURONS_PER_HIDDEN = 4;

	private int maxAsteroids, asteroidSpawnChance;

	//Should elitism be used in this GA. Elitism is the copying of one or a few of some of the best chromosomes in each generation.
	private final boolean ELITISM = true;
	private final int ELITISM_COPY_COUNT = 10;

	//The chance when breeding a new chromosome that crossover will occur.
	private final double CROSSOVER_CHANCE = 0.3;
	//The chance when breeding a ne chromosome that mutation will occur ( per allele ).
	private final double MUTATION_CHANCE = 0.1;

	private GeneticIO geneticIOHandler;

	public GA(ArrayList<AbstractShip> otherShips, int populationSize, int generations, int gamesPerGeneration, int maxAsteroids, int asteroidSpawnChance) 
	{
		this.maxAsteroids = maxAsteroids;
		this.asteroidSpawnChance = asteroidSpawnChance;

		population = new ArrayList<Chromosome>();

		//Generate initial population.
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
	 * @param otherShips
	 * @param population2
	 * @param gamesPerGeneration
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

		saveHighestScore(population);

	}

	private void saveHighestScore(ArrayList<Chromosome> population2) 
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

	private ArrayList<Chromosome> calculateNextPopulation(ArrayList<Chromosome> inputPopulation) 
	{
		ArrayList<Chromosome> nextPopulation = new ArrayList<Chromosome>();

		int populationCounter = 0;

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
				if(Math.random() > 0.5)
					child = dad;
				else
					child = mum;
			}

			if(Math.random() < MUTATION_CHANCE)
				GAHelper.mutateChromosone(child, MUTATION_CHANCE);

			nextPopulation.add(child);
			populationCounter++;
		}

		return nextPopulation;
	}


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

	private int sumPopulation(ArrayList<Chromosome> population) 
	{
		int total = 0;
		for(Chromosome c : population)
			total += c.getChromosomeScore();
		return total;
	}

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

	private void resetShipScores(ArrayList<AbstractShip> batchShips) 
	{
		for(AbstractShip s : batchShips)
			s.resetScore();

	}

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



	/*
	 * 
	 * Class to work on importing over some of the GA elements from the snake project.
	 * 
	 * Process.
	 * 
	 * At start, generate a base population. 
	 * 
	 * Some form of method will be required to run a generation with a given population.
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */

	public static void main(String[] args)
	{
		AsteroidHunter test = new AsteroidHunter();
		ArrayList<AbstractShip> ships = new ArrayList<AbstractShip>();
		ships.add(test);

		new GA(ships,100,200,80,20,1);
	}
}
