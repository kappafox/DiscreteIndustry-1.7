package kappafox.di.base.util;

public class PointSet extends BoundSet
{
	public PointSet(float x1, float y1, float z1, float x2, float y2, float z2)
	{
		super(x1, x2, y1, y2, z1, z2);
	}
	
	public PointSet(PointSet p)
	{
		this(p.x1, p.y1, p.z1, p.x2, p.y2, p.z2);
	}
}
