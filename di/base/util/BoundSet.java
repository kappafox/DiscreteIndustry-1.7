package kappafox.di.base.util;

import net.minecraft.util.AxisAlignedBB;

public class BoundSet 
{
	public float x1;
	public float x2;
	public float y1;
	public float y2;
	public float z1;
	public float z2;
	
	public BoundSet( )
	{
		this(0,0,0,0,0,0);
	}
	
	public BoundSet(float x1, float x2, float y1, float y2, float z1, float z2)
	{
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
		this.z1 = z1;
		this.z2 = z2;
	}
	
	public BoundSet(double x1, double x2, double y1, double y2, double z1, double z2)
	{
		this((float)x1, (float)x2, (float)y1, (float)y2, (float)z1, (float)z2);
	}
	
	public AxisAlignedBB toAABBRelative( )
	{
		return AxisAlignedBB.getBoundingBox(x1, y1, z1, x2, y2, z2);
	}
	
	public AxisAlignedBB toAABB(int x, int y, int z)
	{
		return AxisAlignedBB.getBoundingBox((double)x + x1, (double)y + y1, (double)z + z1, (double)x + x2, (double)y + y2, (double)z + z2);
	}
	
	public void combine(BoundSet addition)
	{
		x1 = Math.max(x1, addition.x1);
		x2 = Math.max(x2, addition.x2);
		y1 = Math.min(y1, addition.y1);
		y2 = Math.max(y2, addition.y2);
		z1 = Math.min(z1, addition.z1);
		z2 = Math.max(z2, addition.z2);		
	}
}
