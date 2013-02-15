package arena.collisions;

import java.awt.Point;

public class CollisionPolygon
{


	private int count = 0;
	private int verticeCount = 0 ;
	private Vector vertices[];

	/**
	 * Create a new collision polygon. 
	 * @param objectCentre Centre of the object.
	 * @param verticeCount Number of vertices.
	 */
	public CollisionPolygon(Point.Double objectCentre, int verticeCount)
	{
		this.verticeCount = verticeCount;
		vertices = new Vector[this.verticeCount];
	}

	/**
	 * Get the number of vertices in this collision polygon.
	 * @return Number of vertices.
	 */
	public int getVerticeCount()
	{
		return verticeCount;
	}

	/**
	 * Add a new vertice to this collision polygon.
	 * @param x X Location of vertice.
	 * @param y Y Location of vertice.
	 */
	public void addVertice(double x, double y)
	{
		Vector temp = new Vector(x,y);
		vertices[this.count] = temp;
		this.count++;
	}

	/**
	 * Translate all of the vertices of this collision polygon.
	 * @param x Amount to translate in X axis.
	 * @param y Amount to translate in Y axis.
	 */
	public void translate(int x, int y)
	{
		for (int i = 0; i < verticeCount; i++) 
		{
			vertices[i].x += x;
			vertices[i].y += y;
		}
	}


	/**
	 * Determine if this collision polygon is colliding with another.
	 * @param poly Polygon to check collision with.
	 * @return True if they are colliding.
	 */
	public boolean collide(CollisionPolygon poly)
	{
		for(int j = verticeCount-1, i = 0; i < verticeCount; j = i, i++)
		{
			Vector v0 = vertices[j];
			Vector v1 = vertices[i];

			Vector edge = new Vector(0,0);
			edge.x = v1.x - v0.x;
			edge.y = v1.y - v0.y; 

			Vector axis = edge.perp(); 

			if(separatedByAxis(axis, poly))
				return false;
		}

		// test separation axes of other polygon
		for(int j = poly.verticeCount-1, i = 0; i < poly.verticeCount; j = i, i++)
		{
			Vector v0 = poly.vertices[j];
			Vector v1 = poly.vertices[i];

			Vector edge2 = new Vector(0,0);
			edge2.x = v1.x - v0.x; // edge
			edge2.y = v1.y - v0.y; // edge

			Vector axis = edge2.perp(); // Separate axis is perpendicular to the edge

			if(separatedByAxis(axis, poly))
				return false;
		}
		return true;
	}

	float min,max;

	/**
	 * Calculate the interval between this polygon to the given axis.
	 * @param axis Vector representing the axis.
	 */
	public void calculateInterval(Vector axis) 
	{
		this.min = this.max = (float) vertices[0].dot(axis);

		for (int i = 1; i < verticeCount; i++)
		{
			float d = (float) vertices[i].dot(axis);
			if (d < this.min)
				this.min = d;
			else if (d > this.max)
				this.max = d;
		}
	}

	/**
	 * Method to determine if the given intervals for two vectors are seperated.
	 * @param mina Min value of the first vector.
	 * @param maxa Max value of the first vector.
	 * @param minb Min value of the second vector.
	 * @param maxb Max value of the second vector.
	 * @return True if for any of the two vectors the min is greater than the max.
	 */
	public boolean intervalsSeparated(float mina, float maxa, float minb, float maxb) 
	{
		return (mina > maxb) || (minb > maxa);
	}

	float mina, maxa;
	float minb, maxb;

	/**
	 * Determine to see if a given axis and another collision polygon are seperated by an axis.
	 * @param axis The vector represnting the axis.
	 * @param poly CollisonPolygon of the other object.
	 * @return Whether the min/max values are seperated.
	 */
	public boolean separatedByAxis(Vector axis, CollisionPolygon poly) 
	{
		calculateInterval(axis);
		mina = min;
		maxa = max;
		poly.calculateInterval(axis);
		minb = poly.min;
		maxb = poly.max;
		return intervalsSeparated(mina, maxa, minb, maxb);
	}
}

