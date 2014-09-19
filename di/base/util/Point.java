package kappafox.di.base.util;

public class Point
{
	public float x = 0;
	public float y = 0;
	public float z = 0;
	
	public Point(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Point(double x, double y, double z)
	{
		this.x = (float)x;
		this.y = (float)y;
		this.z = (float)z;
	}
}
