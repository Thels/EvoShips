package io;

import genetic.Chromosome;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import arena.objects.AbstractShip;

public class GeneticIO 
{
	private String folderPath;

	/**
	 * Creates the folder that all chromosomes will be saved in.
	 * @return True if folder created successfully.
	 */
	public boolean createChromosomeFolder()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		Date date = new Date();

		this.folderPath = "chromosomes/"+dateFormat.format(date);

		File newDir = new File(folderPath);
		newDir.mkdirs();

		return newDir.mkdirs();
	}

	/**
	 * Saves a given chromosome to the folder that this GeneticIO represents.
	 * @param chromoToSave Chromosome to save.
	 */
	public void saveChromosomeToFolder(Chromosome chromoToSave)
	{
		try 
		{
			chromoToSave.saveToFile(folderPath);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	/**
	 * Write the paramaters of this GA to a file in the folder, so that you can easily check the variables later on.
	 * @param ships Other ships taking part with the NNShip.
	 * @param popSize Size of the population.
	 * @param generationCount Number of generations being ran.
	 * @param gamesPerGeneration Number of games per generation.
	 * @param maxAsteroids Max number of asteroids per arena created.
	 * @param asteroidSpawnChance Asteroid spawn chance ( 0-100 ).
	 * @param mutationChance Chance that an allele will be mutated.
	 * @param crossoverChance Chance that a chromosome will incur crossover.
	 */
	public void writeParametersToFile(ArrayList<AbstractShip> ships,int popSize, int generationCount, 
			int gamesPerGeneration, int maxAsteroids, int asteroidSpawnChance, double mutationChance, double crossoverChance)
	{
		try 
		{
			FileWriter fstream = new FileWriter(folderPath+"\\"+"GA-PARAMS.txt");
			BufferedWriter out = new BufferedWriter(fstream);

			out.write("Other Ships Taking Part");
			out.newLine();
			out.newLine();
			
			for(AbstractShip s : ships)
			{
				out.write(s.getShipName());
				out.newLine();
			}
			out.newLine();
			
			out.write("Population Size : "+popSize);
			out.newLine();
			
			out.write("Number of Generations : "+generationCount);
			out.newLine();
			
			out.write("Games per Generation : "+generationCount);
			out.newLine();
			
			out.write("Max Asteroids / Spawn Chance : "+maxAsteroids+" / "+asteroidSpawnChance);
			out.newLine();
			
			out.write("Mutation Rate : "+mutationChance);
			out.newLine();
			
			out.write("Crossover Rate : "+crossoverChance);
			out.newLine();
			
			
			
			out.close();
		}
		catch (IOException e)
		{
			System.err.println("Error when saving Chromosome : "+this.toString());
			System.err.println("Error message : " + e.getMessage());
		}
	}

}
