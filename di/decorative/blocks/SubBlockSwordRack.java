package kappafox.di.decorative.blocks;


import ic2.api.item.IC2Items;

import java.util.List;
import java.util.Random;

import kappafox.di.DiscreteIndustry;
import kappafox.di.base.blocks.SubBlock;
import kappafox.di.base.tileentities.TileEntityDiscreteBlock;
import kappafox.di.decorative.tileentities.TileEntitySwordRack;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class SubBlockSwordRack extends SubBlock 
{
	
	private static IIcon side;
	private static IIcon top;
	
	@Override
	public void registerBlockIcons(IIconRegister ireg) 
	{
		side = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "blockSwordRack_side");
		top = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "blockSwordRack_top");

	}

	@Override
	public IIcon getIcon(int side, int meta) 
	{
		if(side == 0 || side == 1)
		{
			return top;	
		}
		
		return this.side;
	}
	
	@Override
	public IIcon getOverloadedIcon(int side, int meta)
	{
		return this.getIcon(side, meta);
	}

	@Override
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) 
	{
		return this.getIcon(side, 0);
	}
	
	
	@Override	
	public TileEntity createTileEntity(World world, int meta)
	{
		return new TileEntitySwordRack(6);
	}
	
	@Override	
	public boolean hasTileEntity(int meta)
	{
		return true;
	}
	
	@Override
	public boolean onBlockActivated(World world, int xcoord, int ycoord, int zcoord, EntityPlayer player, int side, float par7, float par8, float par9)
	{
		ItemStack held = player.inventory.getCurrentItem();
		
		if(held != null)
		{
			if(held == IC2Items.getItem("obscurator"))
			{
				return true;
			}
		}
		player.openGui(DiscreteIndustry.instance, 2, world, xcoord, ycoord, zcoord);	
		return true;
	}
	
	@Override
	public boolean isDiscrete( )
	{
		return true;
	}
	
	@Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta)
    {
        Random rand = new Random();

        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (!(tileEntity instanceof IInventory)) 
        {
        	
        }
        else
        {
	        IInventory inventory = (IInventory)tileEntity;
	
	        for (int i = 0; i < inventory.getSizeInventory(); i++) 
	        {
                ItemStack item = inventory.getStackInSlot(i);

                if (item != null && item.stackSize > 0) 
                {
                    float rx = rand.nextFloat() * 0.8F + 0.1F;
                    float ry = rand.nextFloat() * 0.8F + 0.1F;
                    float rz = rand.nextFloat() * 0.8F + 0.1F;

                    EntityItem entityItem = new EntityItem(world, x + rx, y + ry, z + rz, new ItemStack(item.getItem(), item.stackSize, item.getItemDamage()));

                    if (item.hasTagCompound()) 
                    {
                    	entityItem.getEntityItem().setTagCompound((NBTTagCompound) item.getTagCompound().copy());
                    }

                    float factor = 0.05F;
                    entityItem.motionX = rand.nextGaussian() * factor;
                    entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
                    entityItem.motionZ = rand.nextGaussian() * factor;
                    world.spawnEntityInWorld(entityItem);
                    item.stackSize = 0;
                }
	        }  
        }
    }
	
	
	@Override
	public AxisAlignedBB getWireframeBox(IBlockAccess world, int x, int y, int z) 
	{
    	TileEntityDiscreteBlock tile = (TileEntityDiscreteBlock)world.getTileEntity(x, y, z);
    	
    	if(tile != null)
    	{
    		int direction = tile.getDirection();
    		
    		if(direction == 2 || direction == 3)
    		{
    			return AxisAlignedBB.getBoundingBox(x + px.zero, y + px.zero, z + px.four, x + px.sixteen, y + px.sixteen, z + px.twelve);		
    		}
    		else
    		{
    			return AxisAlignedBB.getBoundingBox(x + px.four, y + px.zero, z + px.zero, x + px.twelve, y + px.sixteen, z + px.sixteen);    			
    		}
    		
    	}
        
    	return null;
	}
	
	@Override
    public void getCollisionBoxes(World world, int x, int y, int z, AxisAlignedBB mask, List boxlist, Entity entity)
    {
		
    	TileEntityDiscreteBlock tile = (TileEntityDiscreteBlock)world.getTileEntity(x, y, z);
    	AxisAlignedBB box = null;
    	
    	if(tile != null)
    	{
    		
    		int direction = tile.getDirection();
    		
    		if(direction == 2 || direction == 3)
    		{
    			box = AxisAlignedBB.getBoundingBox(x + px.zero, y + px.zero, z + px.four, x + px.sixteen, y + px.sixteen, z + px.twelve);			
    		}
    		else
    		{
    			box = AxisAlignedBB.getBoundingBox(x + px.four, y + px.zero, z + px.zero, x + px.twelve, y + px.sixteen, z + px.sixteen);  			
    		}
    		
    	}
    	
        if (box != null && mask.intersectsWith(box))
        {
            boxlist.add(box);
        }
    }


}
