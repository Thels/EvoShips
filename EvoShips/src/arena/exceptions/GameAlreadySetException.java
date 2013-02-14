package arena.exceptions;

public class GameAlreadySetException extends Exception 
{

	public GameAlreadySetException()
	{
		super("Ship already has an arena set!");
	}
}
