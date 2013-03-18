package arena;

/**
 * An enum to help with the scoring conventions. Easily scalable/changeable.
 * @author Ross
 *
 */
public enum EScoring 
{

	ASTEROID_HIT(1),
	SHIP_HIT(5),
	SURVIVING_BONUS(1);
	
	
	private int score;
	/**
	 * Creates a new scoring rule.
	 * @param Score awarded.
	 */
	EScoring(int scoringAmount)
	{
		this.score = scoringAmount;
	}
	
	public int getScore()
	{
		return score;
	}
	
}
