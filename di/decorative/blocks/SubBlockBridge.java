package kappafox.di.decorative.blocks;

import kappafox.di.base.blocks.SubBlock;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class SubBlockBridge extends SubBlock 
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

}
