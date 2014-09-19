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
	
	public BoundSet(float x1_, float x2_, float y1_, float y2_, float z1_, float z2_)
	{
		x1 = x1_;
		x2 = x2_;
		y1 = y1_;
		y2 = y2_;
		z1 = z1_;
		z2 = z2_;
	}
	
	public BoundSet(double x1_, double x2_, double y1_, double y2_, double z1_, double z2_)
	{
		this((float)x1_, (float)x2_, (float)y1_, (float)y2_, (float)z1_, (float)z2_);
	}
	
	public AxisAlignedBB toAABB( )
	{
		return AxisAlignedBB.getBoundingBox(x1, y1, z1, x2, y2, z2);
	}
}
