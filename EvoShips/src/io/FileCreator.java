package io;

import genetic.Chromosome;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileCreator 
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

}
