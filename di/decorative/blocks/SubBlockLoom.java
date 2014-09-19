package kappafox.di.decorative.blocks;

import kappafox.di.DiscreteIndustry;
import kappafox.di.base.blocks.SubBlock;
import kappafox.di.decorative.tileentities.TileEntityLoomBlock;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class SubBlockLoom extends SubBlock
{

	@Override
	public void registerBlockIcons(IIconRegister ireg) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public IIcon getIcon(int side, int meta) 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) 
	{
		return null;
	}
	
	@Override
	public boolean onBlockActivated(World world, int xcoord, int ycoord, int zcoord, EntityPlayer player, int side, float par7, float par8, float par9)
	{
		player.openGui(DiscreteIndustry.instance, 1, world, xcoord, ycoord, zcoord);
		return true;
	}
	
    @Override
	public TileEntity createTileEntity(World world, int meta)
	{
		return new TileEntityLoomBlock();
	}
	
	
	@Override
	public boolean hasTileEntity(int meta)
	{
		return true;
	}
	
}
