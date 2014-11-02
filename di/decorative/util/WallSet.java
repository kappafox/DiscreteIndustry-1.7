package kappafox.di.decorative.util;

import java.util.HashMap;

import kappafox.di.base.util.PointSet;
import net.minecraftforge.common.util.ForgeDirection;

public class WallSet 
{
	private HashMap<ForgeDirection, PointSet> parts;
	
	
	public WallSet(PointSet north)
	{
		parts = new HashMap<ForgeDirection, PointSet>();
		
		parts.put(ForgeDirection.SOUTH, north.translateTo(ForgeDirection.SOUTH));
		parts.put(ForgeDirection.EAST, north.translateTo(ForgeDirection.EAST));
		parts.put(ForgeDirection.WEST, north.translateTo(ForgeDirection.WEST));
		parts.put(ForgeDirection.NORTH, north);
	}
	
	public PointSet Get(ForgeDirection direction)
	{
		return parts.get(direction);
	}
	
}
