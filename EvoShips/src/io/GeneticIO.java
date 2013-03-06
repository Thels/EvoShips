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

	public boolean createChromosomeFolder()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		Date date = new Date();

		this.folderPath = "chromosomes/"+dateFormat.format(date);

		File newDir = new File(folderPath);
		newDir.mkdirs();

		return newDir.mkdirs();
	}

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

	public void writeParametersToFile(ArrayList<AbstractShip> ships,int popSize, int generationCount, 
			int gamesPerGeneration, int maxAsteroids, int asteroidSpawnChance, double mutationChance, double crossoverChance)
	{
		try 
		{
			FileWriter fstream = new FileWriter(folderPath+"\\"+"GA-PARMS.txt");
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
