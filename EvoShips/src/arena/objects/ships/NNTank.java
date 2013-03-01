package arena.objects.ships;

import neuralnetwork.NNetwork;
import neuralnetwork.NetworkInputs;
import neuralnetwork.NetworkOutputs;
import genetic.Chromosome;
import arena.ArenaWatcher;
import arena.objects.AbstractShip;

public class NNTank extends AbstractShip 
{
	private ArenaWatcher shipWatcher;
	private NNetwork neuralNetwork;
	private Chromosome shipChromosome;
	
	public NNTank(Chromosome chromosome) 
	{
		super();
		shipChromosome = chromosome;
		neuralNetwork = new NNetwork(chromosome, NetworkInputs.values().length, 4, 5, NetworkOutputs.values().length);
		
	}
	
	@Override
	public void determineAction() 
	{
		resetActionBooleans();
	
		shipWatcher = getShipsArenaWatcher();
		
		AbstractShip nearestShip = shipWatcher.getNearestShip(this);
		if(nearestShip != null)
		{
			double angleToShip = shipWatcher.angleToObject(this, nearestShip);
			if(angleToShip < 0)
				right = true;
			else
				left = true;
			
			fire = true;
		}

	}

	@Override
	public NNTank cloneShip() 
	{
		NNTank returnShip = new NNTank(shipChromosome);
		returnShip.setCloneOfShip(this);
		return returnShip;
	}

	@Override
	public String getShipName() 
	{
		return "NNetwork Tank";
	}


}
