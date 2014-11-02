package kappafox.di.base.util;

import kappafox.di.base.TranslationHelper;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class PointSet extends BoundSet
{
	private static TranslationHelper translator = new TranslationHelper();
	
	public PointSet(float x1, float y1, float z1, float x2, float y2, float z2)
	{
		super(x1, x2, y1, y2, z1, z2);
	}
	
	public PointSet(double x1, double y1, double z1, double x2, double y2, double z2)
	{
		super(x1, x2, y1, y2, z1, z2);
	}
	
	public PointSet(PointSet p)
	{
		this(p.x1, p.y1, p.z1, p.x2, p.y2, p.z2);
	}
	
	public void combine(BoundSet addition)
	{
		/*
		PointSet newBounds = new PointSet(Math.min(x1, addition.x1), 
				Math.min(y1, addition.y1), 
				Math.min(z1, addition.z1),
				Math.max(x2, addition.x2),
				Math.max(y2, addition.y2),
				Math.max(z2, addition.z2)		
				);
		
		return newBounds;
		*/
		
		x1 = Math.min(x1, addition.x1);
		y1 = Math.min(y1, addition.y1);
		z1 = Math.min(z1, addition.z1);
		x2 = Math.max(x2, addition.x2);
		y2 = Math.max(y2, addition.y2);
		z2 = Math.max(z2, addition.z2);	
	}
	
	public PointSet translateTo(ForgeDirection direction)
	{
		return translator.translate(ForgeDirection.NORTH, direction, new PointSet(this));
	}
}
