package neuralnetwork;

/**
 * Enum that holds all of the possible output for the evoships neural network.
 * 
 * Original version used to have 5 outputs , one for each movement and one for fire. But this was found to be too complicated for the simple NNetwork.
 * @author Ross
 *
 */
public enum ENetworkOutputs 
{
	MOVE(0.05),
	TURN(0.03),
	FIRE(0.005);
	
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
	 * Check to see if a given value activates the given output in the positive bound, is value > +activationThreshold.
	 * @param value Value to check against the activation threshold.
	 * @return Whether the value is greater than the POSITIVE activation threshold.
	 */
	public boolean isActivatedPositiveBound(double value)
	{
		return value > this.activationThreshold;
	}
	
	/**
	 * Check to see if a given value activates the given output in the negative bound, is value < -activationThreshold.
	 * @param value Value to check against the activation threshold.
	 * @return Whether the value is less than the negative activation threshold.
	 */
	public boolean isActivatedNegativeBound(double value)
	{
		return value < (this.activationThreshold*-1);
	}
}
