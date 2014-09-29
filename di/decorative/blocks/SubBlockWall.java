package kappafox.di.decorative.blocks;

import java.util.HashMap;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import kappafox.di.base.blocks.SubBlock;
import kappafox.di.base.tileentities.TileEntityDiscreteBlock;
import kappafox.di.base.tileentities.TileEntitySidedConnector;
import kappafox.di.base.util.PointSet;

public class SubBlockWall extends SubBlock
{
	
	public static final PointSet PART_WALL_POST = new PointSet(px.four, px.zero, px.four, px.twelve, px.sixteen, px.twelve);
	public static final PointSet PART_WALL_NORTH = new PointSet(px.five, px.zero, px.zero, px.eleven, px.thirteen, px.eight);
	public static final PointSet PART_WALL_SOUTH = new PointSet(px.five, px.zero, px.eight, px.eleven, px.thirteen, px.sixteen);
	public static final PointSet PART_WALL_EAST = new PointSet(px.eight, px.zero, px.five, px.sixteen, px.thirteen, px.eleven);
	public static final PointSet PART_WALL_WEST = new PointSet(px.zero, px.zero, px.five, px.eight, px.thirteen, px.eleven);
	
	
	@Override
	public IIcon getIcon(int side, int meta) 
	{
		return DEFAULT_ICON;
	}

	@Override
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) 
	{
		return DEFAULT_ICON;
	}
	
	@Override
	public TileEntity createTileEntity(World world, int meta)
	{
		return new TileEntitySidedConnector();
	}
	
	@Override
	public boolean hasTileEntity(int meta)
	{
		return true;
	}
	
    public boolean isDiscrete( )
    {
    	return true;
    }
}
