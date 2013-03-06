package neuralnetwork;

/**
 * Enum that holds all of the possible output for the evoships neural network.
 * @author Ross
 *
 */
public enum ENetworkOutputs 
{
	MOVE(0),
	//MOVE_BACKWARD(0.25),
	TURN(0),
//	TURN_RIGHT(0.3),
	FIRE(0.05);
	
	private double activationThreshold;
	
	/**
	 * Creates a new network output, with a given activation threshold.
	 * @param activationThreshold Activation threshold for this output.
	 */
	ENetworkOutputs(double activationThreshold)
	{
		this.activationThreshold = activationThreshold;
	}
	
	/**
	 * Check to see if a given value activates the given output.
	 * @param value Value to check against the activation threshold.
	 * @return Whether the value is greater than the activation threshold.
	 */
	public boolean isActivated(double value)
	{
		return value > this.activationThreshold;
	}
}
