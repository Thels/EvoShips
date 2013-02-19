package arena.exceptions;

/**
 * Exception thrown so that it's easy to tell if someone is trying to add another ship into the 
 * game when there isn't meant to be.
 * @author Ross
 *
 */
public class GameAlreadyRunningException extends Exception 
{

	public GameAlreadyRunningException(String method) 
	{
		super("Game is already running. METHOD : "+method);
	}



}
