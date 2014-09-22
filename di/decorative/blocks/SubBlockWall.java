package kappafox.di.decorative.blocks;

import java.util.HashMap;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import kappafox.di.base.blocks.SubBlock;
import kappafox.di.base.tileentities.TileEntityDiscreteBlock;

public class SubBlockWall extends SubBlock
{
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
		return new TileEntityDiscreteBlock();
	}
	
	@Override
	public boolean hasTileEntity(int meta)
	{
		return true;
	}
	
	public static HashMap getConnectionList(int x, int y, int z)
	{
		HashMap<ForgeDirection, Boolean> cons = new HashMap<ForgeDirection, Boolean>();
		
		
		return cons;
	}
}
