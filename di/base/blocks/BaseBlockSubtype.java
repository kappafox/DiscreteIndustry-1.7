package kappafox.di.base.blocks;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import kappafox.di.base.tileentities.TileEntityDiscreteBlock;
import kappafox.di.base.tileentities.TileEntitySubtype;
import kappafox.di.base.util.BoundSet;
import kappafox.di.decorative.tileentities.TileEntityLoomBlock;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class BaseBlockSubtype extends Block
{
	
	protected HashMap<Integer, SubBlock> blocks;	
	protected int renderID;
	
	public BaseBlockSubtype(int id, Material mat)
	{
		super(mat);
		
		blocks = new HashMap<Integer, SubBlock>();
	}
	
	public BaseBlockSubtype(int id, Material mat, int rID)
	{
		this(id, mat);
		renderID = rID;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ireg)
	{
		for(SubBlock b: blocks.values())
		{
			b.registerBlockIcons(ireg);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tabs, List list)
	{

	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		return Blocks.stone.getIcon(0, 0);
	}
	
	@Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitx, float hity, float hitz)
    {	
		int meta = world.getBlockMetadata(x, y, z);
		
		if(blocks.containsKey(meta))
		{
			return blocks.get(meta).onBlockActivated(world, x, y, z, player, side, hitx, hity, hitz);
		}
		
		return false;
    }
	
	@Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side)
    {	

		int meta = world.getBlockMetadata(x, y, z);
		
		if(blocks.containsKey(meta))
		{
			if(blocks.get(meta).isDiscrete() == true)
			{
				TileEntityDiscreteBlock tile = (TileEntityDiscreteBlock)world.getTileEntity(x, y, z);
				
				if(tile != null)
				{
					if((tile.getTextureSource(side) == tile.getOriginalBlockName()) && (tile.getTextureSourceMeta(side) == tile.getSubtype()))
					{
						return blocks.get(meta).getIcon(world, x, y, z, side);
					}
				}
				
				return super.getIcon(world, x, y, z, side);
			}
			else
			{
				return blocks.get(meta).getIcon(world, x, y, z, side);		
			}		
		}
		
		return this.getIcon(side, meta);
    }
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack istack)
	{
		
		int meta = world.getBlockMetadata(x, y, z);
		if(blocks.containsKey(meta))
		{
			if(blocks.get(meta).isDiscrete() == true)
			{
				super.onBlockPlacedBy(world, x, y, z, player, istack);
			}
			
			blocks.get(meta).onBlockPlacedBy(world, x, y, z, player, istack);
		}
	}
	
	public void onBlockAdded(World world, int x, int y, int z) 
	{
		int meta = world.getBlockMetadata(x, y, z);
		if(blocks.containsKey(meta))
		{
			blocks.get(meta).onBlockAdded(world, x, y, z);
		}
	}
	
	@Override
	public int onBlockPlaced(World world, int x, int y, int z, int side, float hitx, float hity, float hitz, int meta)
	{
		if(blocks.containsKey(meta))
		{
			return blocks.get(meta).onBlockPlaced(world, x, y, z, side, hitx, hity, hitz, meta);
		}
		
		return super.onBlockPlaced(world, x, y, z, side, hitx, hity, hitz, meta);
	}
	
	@Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta)
    {
		TileEntitySubtype tile = (TileEntitySubtype)world.getTileEntity(x, y, z);
		
		if(tile != null)
		{
			int type = tile.getSubtype();
			
			ItemStack item = new ItemStack(this, 1, type);
			super.dropBlockAsItem(world, x, y, z, item);
		}
		
		if(blocks.containsKey(meta))
		{
			blocks.get(meta).breakBlock(world, x, y, z, block, meta);
		}
		
		world.removeTileEntity(x, y, z);
    }
	
	@Override
    public int quantityDropped(int meta, int fortune, Random rng)
    {
		//we will drop our own items because of overloading
		return 0;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderType( )
	{
		return renderID;	
	}
	
	//do NOT set this to client side only, it is required on the serverside too!
	public boolean isOpaqueCube( )
	{
		return false;
	}
	
    @Override
    @SideOnly(Side.CLIENT)
    public boolean renderAsNormalBlock( )
    {
    	return false;
    }
    
	@Override
	@SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side)
    {	
		return true;
    }
	
	@Override
	public TileEntity createTileEntity(World world, int meta)
	{
		if(this.hasTileEntity(meta) == false)
		{
			return null;
		}
		
		if(blocks.containsKey(meta))
		{
			return blocks.get(meta).createTileEntity(world, meta);
		}
		
		return null;
	}
	
	@Override
    public boolean hasTileEntity(int meta)
    {
		if(blocks.containsKey(meta))
		{
			return blocks.get(meta).hasTileEntity(meta);
		}
		
		return false;
    }
	
	@Override
    public boolean isLadder(IBlockAccess world, int x, int y, int z, EntityLivingBase entity)
    {
		int meta = world.getBlockMetadata(x, y, z);
		
		if(blocks.containsKey(meta))
		{
			return blocks.get(meta).isLadder(world, x, y, z, entity);
		}
		
		return false;
    }
	
	@Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z)
    {
		int meta = world.getBlockMetadata(x, y, z);
		
		if(blocks.containsKey(meta))
		{
			return blocks.get(meta).getWireframeBox(world, x, y, z);
		}
		
		return AxisAlignedBB.getBoundingBox(x + 0, y + 0, z + 0, x + 1, y + 1, z + 1);
    }
	
	@Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
    {
    	int meta = world.getBlockMetadata(x, y, z);
    	BoundSet dim = null;
    	
		if(blocks.containsKey(meta))
		{
			dim = blocks.get(meta).getHitBoxesBasedOnState(world, x, y, z);
		}
    	
    	if(dim != null)
    	{
    		this.setBlockBounds(dim.x1, dim.y1, dim.z1, dim.x2, dim.y2, dim.z2);
    	}
    	else
    	{
    		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    	}
    }
	
    //Generates the collision boxes for a block
    @Override
    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB mask, List boxlist, Entity entity)
    {
    	int meta = world.getBlockMetadata(x, y, z);
		if(blocks.containsKey(meta))
		{
			blocks.get(meta).getCollisionBoxes(world, x, y, z, mask, boxlist, entity);
		}
    }
    
    @Override
    public boolean isNormalCube(IBlockAccess world, int x, int y, int z)
    {
    	int meta = world.getBlockMetadata(x, y, z);
		if(blocks.containsKey(meta))
		{
			return blocks.get(meta).isNormalCube(world, x, y, z);
		}
		
		return true;
    }
    
    @Override
    public boolean isBlockSolid(IBlockAccess world, int x, int y, int z, int side)
    {
    	int meta = world.getBlockMetadata(x, y, z);
		if(blocks.containsKey(meta))
		{
			return blocks.get(meta).isBlockSolid(world, x, y, z, side);
		}
		
		return true;
    }
    
    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z)
    {
        Block block = world.getBlock(x, y, z);        
        TileEntity t = world.getTileEntity(x, y, z);
        
        if (block == null) return null;
             
        if(t instanceof TileEntitySubtype)
        {
        	TileEntitySubtype tile = (TileEntitySubtype)t;
        	int type = tile.getSubtype();
        	
        	if(type != 0)
        		return new ItemStack(block, 1, type);
        }
        
        return new ItemStack(block, 1, getDamageValue(world, x, y, z));
    }

}
