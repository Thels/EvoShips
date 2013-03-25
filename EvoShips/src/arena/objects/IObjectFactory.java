package arena.objects;

public interface IObjectFactory 
{

	/**
	 * Produce the object that the factory requires.
	 * @return Object created.
	 */
	
	public AbstractObject produceObject();
}
