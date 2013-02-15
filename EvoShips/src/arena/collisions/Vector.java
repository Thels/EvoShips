package arena.collisions;

import java.util.Random;

/**
 * Class representing a vector , this class is merely a way of representing parts of the collision polygon.
 * @author Ross
 *
 */
public class Vector 
{

	public double x, y;

	public Vector(double Ix, double Iy) 
	{
		x = Ix;
		y = Iy;
	}

	/**
	 * Scale the vector, returning the new scaled vector.
	 * @param scale Amount to scale the vector by.
	 * @return New vector that is scaled.
	 */
	public Vector scale(double scale) 
	{
		return new Vector(x * scale, y * scale);
	}

	/**
	 * Multiple the vector, returning the new multiplied vector)
	 * @param other Other vector to multiply with.
	 * @return Vector of the multiplied vectors.
	 */
	public Vector multiply(Vector other) 
	{
		return new Vector(x * other.x, y * other.y);
	}

	/**
	 * Get the vector perpindicular to this vector.
	 * @return Vector which is perpidicular to this vector.
	 */
	public Vector perp() 
	{
		Vector vect = new Vector(-y, x);
		return vect;
	}

	/**
	 * Randomize this vector between two other vectors.
	 * @param max Vector representing max.
	 * @param min Vector representing min.
	 */
	public void randomize(Vector max, Vector min) 
	{ 
		Random r = new Random();
		x = (r.nextFloat() * (max.x - min.x) + min.x);
		y = (r.nextFloat() * (max.y - min.y) + min.y);
	}

	/**
	 * Get the dot product of this vector and an axis vector.
	 * @param axis Vector representing the axis.
	 * @return Dot product of the two vectors.
	 */
	public double dot(Vector axis) 
	{ 
		return x * axis.x + y * axis.y;
	}
	
}
