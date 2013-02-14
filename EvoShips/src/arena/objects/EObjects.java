package arena.objects;

/**
 * Enum to represent the basic objects and hold some of the default values for them, such 
 * as the default health for any given object. Health isn't modifiable inside the game or 
 * simulation, so storing it along with the type of object seemed like a sensible option.
 * @author Ross
 *
 */
public enum EObjects 
{
	OBJ_SHIP(10),
	OBJ_ASTEROID(3),
	OBJ_BULLET(1);
	
	private int defaultObjectHealth;
	
	EObjects(int objectHealth)
	{
		this.defaultObjectHealth = objectHealth;
	}
	
	public int getBaseHealth()
	{
		return defaultObjectHealth;
	}
	
	
	
}
