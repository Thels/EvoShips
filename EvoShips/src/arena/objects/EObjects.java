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
	
	/**
	 * Create a new EObject in the enum with the given objecthealth.
	 * @param objectHealth Base health of the object.
	 */
	EObjects(int objectHealth)
	{
		this.defaultObjectHealth = objectHealth;
	}
	
	/**
	 * Get the base health of the given object.
	 * @return Health as given in the enum.
	 */
	public int getBaseHealth()
	{
		return defaultObjectHealth;
	}
	
	
	
}
