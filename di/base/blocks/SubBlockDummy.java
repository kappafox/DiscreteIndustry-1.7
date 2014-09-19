package kappafox.di.base.blocks;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import kappafox.di.DiscreteIndustry;

public class SubBlockDummy extends SubBlock
{

	private static IIcon DEFAULT_ICON;
	
	@Override
	public void registerBlockIcons(IIconRegister ireg)
	{
		DEFAULT_ICON = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "blockDiscreteCable");
	}

	@Override
	public IIcon getIcon(int side, int meta)
	{
		return DEFAULT_ICON;
	}
	
	public IIcon getOverloadedIcon(int side, int meta)
	{
		return this.getIcon(0, 0);
	}
	@Override
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side)
	{
		return this.getIcon(0, 0);
	}

}
