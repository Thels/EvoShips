package io;

import java.io.IOException;

/**
 * Interface that allows the saving of certain objects.
 * @author Ross
 *
 */
public interface ISaveableChromosome 
{

	/**
	 * Save elements of a chromosome so that it can be loaded/edited later easily.
	 * @param folderName Name of the folder the chromo has to be saved to.
	 * @throws IOException Exception is thrown if there are any issues with file opening/creation/editing.
	 */
	public void saveToFile(String folderName) throws IOException;
	
	
}
