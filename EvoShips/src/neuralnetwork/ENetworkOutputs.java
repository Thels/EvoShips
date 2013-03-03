package neuralnetwork;

/**
 * Enum that holds all of the possible output for the evoships neural network.
 * @author Ross
 *
 */
public enum ENetworkOutputs 
{
	MOVE_FORWARD(0.05),
	MOVE_BACKWARD(0.05),
	TURN_LEFT(0.05),
	TURN_RIGHT(0.05),
	FIRE(0.01);
	
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
