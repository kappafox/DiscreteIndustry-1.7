package kappafox.di.decorative.blocks;

import ic2.api.item.IC2Items;
import kappafox.di.DiscreteIndustry;
import kappafox.di.base.blocks.SubBlock;
import kappafox.di.base.compat.ToolHelper;
import kappafox.di.base.tileentities.TileEntitySidedConnector;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SubBlockStrut extends SubBlock
{
	private static IIcon FIXTURE_TEXTURE;
	private static float tolerance = 0.3F;

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ireg_)
	{	
		FIXTURE_TEXTURE = ireg_.registerIcon(DiscreteIndustry.MODID + ":" + "blockDiscreteCable");
	}

	@Override
	public IIcon getIcon(int side_, int meta_) 
	{
		return FIXTURE_TEXTURE;
	}

	@Override
	public boolean onBlockActivated(World world_, int xcoord_, int ycoord_, int zcoord_, EntityPlayer player_, int side_, float hitx_, float hity_, float hitz_) 
	{
		
		//System.out.println(side_ + "," + hitx_ + "," + hity_ + "," + hitz_);
		
		TileEntitySidedConnector tile = (TileEntitySidedConnector)world_.getTileEntity(xcoord_, ycoord_, zcoord_);

		
		if(tile == null)
		{
			return false;
		}
		
		ItemStack inhand = player_.inventory.getCurrentItem();
		ItemStack wrench = IC2Items.getItem("wrench");
		ItemStack ewrench = IC2Items.getItem("electricWrench");
		

		
		boolean ignore = false;
		if(inhand != null)
		{
			
			if(FMLCommonHandler.instance().getEffectiveSide().isServer() == true)
			{

				if(ToolHelper.isWrench(inhand))
				{			
					
					if(side_ != 0 && side_ != 1)
					{
						if(hity_ >= 1.0F - tolerance)
						{
							tile.toggleConnection((short)1);
							ignore = true;
						}
						
						if(hity_ <= tolerance)
						{
							tile.toggleConnection((short)0);
							ignore = true;
						}
					}
					else
					{
						if(hitz_ <= tolerance)
						{
							tile.toggleConnection((short)2);
						}
						
						if(hitz_ >= 1.0F - tolerance)
						{
							tile.toggleConnection((short)3);
						}
						
						if(hitx_ <= tolerance)
						{
							tile.toggleConnection((short)4);
						}
						
						if(hitx_ >= 1.0F - tolerance)
						{
							tile.toggleConnection((short)5);
						}
					}
					
					if((side_ == 4 || side_ == 5) && ignore == false)
					{
						if(hitz_ <= tolerance)
						{
							tile.toggleConnection((short)2);	
						}
						
						if(hitz_ >= 1.0F - tolerance)
						{
							tile.toggleConnection((short)3);	
						}
					}
					
					if((side_ == 2 || side_ == 3) && ignore == false)
					{
						if(hitx_ <= tolerance)
						{
							tile.toggleConnection((short)4);	
						}
						
						if(hitx_ >= 1.0F - tolerance)
						{
							tile.toggleConnection((short)5);	
						}
					}
					
					
					world_.markBlockForUpdate(xcoord_, ycoord_, zcoord_);
					return false;
				}
			}
		}
		
		return false;
	}

	@Override
	public IIcon getIcon(IBlockAccess world_, int x_, int y_, int z_, int side_) 
	{
		return this.getIcon(side_, 0);
	}
	
	@Override
	public IIcon getOverloadedIcon(int side_, int meta_)
	{
		return this.getIcon(side_, meta_);
	}
	


	@Override
	public TileEntity createTileEntity(World world_, int meta_) 
	{
		return new TileEntitySidedConnector();
	}

	@Override
	public boolean hasTileEntity(int meta_) 
	{
		return true;
	}
	
	@Override
	public boolean isDiscrete() 
	{
		return true;
	}

	@Override
	public boolean shouldSideBeRendered(IBlockAccess block_, int x_, int y_, int z_, int side_) 
	{
		return true;
	}
}
