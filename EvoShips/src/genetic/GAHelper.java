package genetic;

import java.util.Random;

public class GAHelper 
{

	public static Chromosome breedChromosones(Chromosome A, Chromosome B)
	{
		Random r = new Random();
		int point;

		double[] dad, mum;
		dad = A.getWeights();
		mum = B.getWeights();

		int chromoLength = dad.length;

		double[] child = new double[chromoLength];

		point = r.nextInt(chromoLength);	
		for(int i = 0 ; i < chromoLength ; i++)
		{
			if(i < point)
			{
				child[i] = dad[i];
			}
			else
			{
				child[i] = mum[i];
			}

		}

		Chromosome childChromosone = new Chromosome(child);

		return childChromosone;
	}

	public static void mutateChromosone(Chromosome chromo, double mutationChance) 
	{
		double[] chromoData = chromo.getWeights();
		for(int i = 0 ; i < chromoData.length ; i++)
		{
			double chance = Math.random();
			if(chance <= mutationChance)
			{
				chromoData[i] = -chromoData[i];
			}
		}
	}


}


