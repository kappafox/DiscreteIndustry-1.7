





package kappafox.di.decorative.blocks;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Range;

import kappafox.di.base.blocks.BlockDiscreteBlock;
import kappafox.di.base.blocks.ISubItemRangeProvider;
import kappafox.di.base.blocks.SubBlock;
import kappafox.di.base.blocks.SubBlockDummy;
import kappafox.di.base.tileentities.TileEntityDiscreteBlock;
import kappafox.di.base.util.BoundSet;
import kappafox.di.decorative.tileentities.TileEntityLoomBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockDecor extends BlockDiscreteBlock implements ISubItemRangeProvider
{
	private static final short SUB_BLOCKS = 9;
	private static SubBlock[] blocks;
	
	public static final short META_LADDER = 2;
	public static final short META_SWORDRACK = 4;
	public static final short META_STAIRS = 5;
	public static final short META_STRUT = 6;
	public static final short META_SHAPE = 7;
	public static final short META_WALL = 8;
	
	public static final short ID_LADDER_FOOTHOLD = 800;
	public static final short ID_LADDER_POLE = 801;
	public static final short ID_LADDER_SIMPLE = 802;
	public static final short ID_LADDER_ROPE = 803;
	public static final short ID_LADDER_FIXED = 804;
	public static final short ID_LADDER_CLASSIC = 806;
	public static final short ID_LADDER_INDUSTRIAL = 807;
	
	public static final short ID_RACK_SWORDREST = 821;
	public static final short ID_RACK_SWORDRACK = 822;
	
	public static final short ID_STAIRS_NORMAL = 861;
	public static final short ID_STAIRS_SMALL = 862;
	
	public static final short ID_STRUT_2X2 = 871;
	public static final short ID_STRUT_4X4 = 872;
	public static final short ID_STRUT_6X6 = 873;
	
	public static final short ID_SHAPE_SLAB = 881;
	
	public static final short ID_WALL_DISCRETE = 900;

	public static final Range<Integer> RANGE_LADDER = Range.closed(800, 820);
	public static final Range<Integer> RANGE_RACK = Range.closed(821, 840);
	public static final Range<Integer> RANGE_STAIRS = Range.closed(861, 870);
	public static final Range<Integer> RANGE_STRUT = Range.closed(871, 880);
	public static final Range<Integer> RANGE_SHAPE = Range.closed(881, 890);
	public static final Range<Integer> RANGE_WALL = Range.closed(900, 920);
	
	private static HashMap<Short, Range> RANGE_SETS;
	
	@SideOnly(Side.CLIENT)
	private int rid;
	
	public BlockDecor(Material mat, int renderID)
	{
		super(mat, renderID);
		
		this.setCreativeTab(CreativeTabs.tabAllSearch);
		this.setBlockName("decorBlock");
		this.setHardness(3.0F);
		
		blocks = new SubBlock[SUB_BLOCKS];
		
		blocks[0] = new SubBlockDummy();
		blocks[1] = new SubBlockDummy();
		
		blocks[2] = new SubBlockLadder();
		blocks[3] = new SubBlockLoom();
		blocks[4] = new SubBlockSwordRack();
		blocks[5] = new SubBlockStairs();
		blocks[6] = new SubBlockStrut();
		blocks[7] = new SubBlockShape();
		blocks[8] = new SubBlockWall();
		
		if(FMLCommonHandler.instance().getEffectiveSide().isClient() == true)
		{
			rid = renderID;
		}
		
		RANGE_SETS = new HashMap<Short, Range>();
		RANGE_SETS.put(META_LADDER, RANGE_LADDER);
		RANGE_SETS.put(META_SWORDRACK, RANGE_RACK);
		RANGE_SETS.put(META_STAIRS, RANGE_STAIRS);
		RANGE_SETS.put(META_STRUT, RANGE_STRUT);
		RANGE_SETS.put(META_SHAPE, RANGE_SHAPE);
		RANGE_SETS.put(META_WALL, RANGE_WALL);	
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ireg)
	{
		//super.registerIcons(ireg_);
		for(SubBlock b: blocks)
		{
			b.registerBlockIcons(ireg);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tabs, List list)
	{
		// blockid, count, meta	
		
		for(int i = 0; i < SUB_BLOCKS; i++)
		{
			list.add(new ItemStack(item, 1, 3));
		}		
	}
	
	
	@SideOnly(Side.CLIENT)
	public IIcon getCustomIcon(int side_, int meta_, int offset_)
	{
		return getIcon(side_, meta_ + offset_);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		if(meta > 15)
		{
			if(RANGE_LADDER.contains(meta))
			{
				return blocks[2].getOverloadedIcon(side, meta);
			}
			
			if(RANGE_RACK.contains(meta))
			{
				return blocks[4].getOverloadedIcon(side, meta);
			}
			
			if(RANGE_STAIRS.contains(meta))
			{
				return blocks[5].getOverloadedIcon(side, meta);
			}
			
			if(RANGE_STRUT.contains(meta))
			{
				return blocks[6].getOverloadedIcon(side, meta);
			}
			
			if(RANGE_SHAPE.contains(meta))
			{
				return blocks[7].getOverloadedIcon(side, meta);
			}
			
			if(RANGE_WALL.contains(meta))
			{
				return blocks[8].getOverloadedIcon(side, meta);
			}
		}
		else
		{
			return blocks[meta].getIcon(side, meta);
		}
		
		return null;
	}

	
	@Override
    public boolean onBlockActivated(World world, int xcoord, int ycoord, int zcoord, EntityPlayer player, int side, float par7, float par8, float par9)
    {	
		int meta = world.getBlockMetadata(xcoord, ycoord, zcoord);
		return blocks[meta].onBlockActivated(world, xcoord, ycoord, zcoord, player, side, par7, par8, par9);	
    }

    

	
	@Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side)
    {	
		int meta = world.getBlockMetadata(x, y, z);
		
		if(blocks[meta].isDiscrete() == true)
		{
			TileEntityDiscreteBlock tile = (TileEntityDiscreteBlock)world.getTileEntity(x, y, z);
			
			if(tile != null)
			{
				if(tile.isSideOriginalTexture(side) == true)
				{
					return blocks[meta].getIcon(world, x, y, z, side);
				}
			}
			
			return super.getIcon(world, x, y, z, side);
		}
		
		return blocks[meta].getIcon(world, x, y, z, side);	
    }
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack istack)
	{
		
		int meta = world.getBlockMetadata(x, y, z);
		if(blocks[meta].isDiscrete() == true)
		{
			super.onBlockPlacedBy(world, x, y, z, player, istack);
		}
		
		blocks[meta].onBlockPlacedBy(world, x, y, z, player, istack);
	}
	
	public void onBlockAdded(World world, int x, int y, int z) 
	{
		int meta = world.getBlockMetadata(x, y, z);
		blocks[meta].onBlockAdded(world, x, y, z);
	}
	
	@Override
	public int onBlockPlaced(World world, int x, int y, int z, int side, float hitx, float hity, float hitz, int meta)
	{
		return blocks[meta].onBlockPlaced(world, x, y, z, side, hitx, hity, hitz, meta);
	}
	
	@Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta)
    {
		TileEntityDiscreteBlock tile = (TileEntityDiscreteBlock)world.getTileEntity(x, y, z);
		
		if(tile != null)
		{
			int type = tile.getSubtype();
			
			ItemStack item = new ItemStack(this, 1, type);
			super.dropBlockAsItem(world, x, y, z, item);
		}

		blocks[meta].breakBlock(world, x, y, z, block, meta);
		super.breakBlock(world, x, y, z, block, meta);
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
		return rid;	
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
		
		return blocks[meta].createTileEntity(world, meta);
	}
	
	@Override
    public boolean hasTileEntity(int meta)
    {
		return blocks[meta].hasTileEntity(meta);
    }
	
	@Override
    public boolean isLadder(IBlockAccess world, int x, int y, int z, EntityLivingBase entity)
    {
		int meta = world.getBlockMetadata(x, y, z);
		return blocks[meta].isLadder(world, x, y, z, entity);
    }
	
	
	//WIREFRAME BOX
	@Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z)
    {
		int meta = world.getBlockMetadata(x, y, z);
		
		return blocks[meta].getWireframeBox(world, x, y, z);
    }
    
    
	
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
    {
    	int meta = world.getBlockMetadata(x, y, z);
    	
    	BoundSet dim = blocks[meta].getHitBoxesBasedOnState(world, x, y, z);
    	
    	if(dim != null)
    	{
    		this.setBlockBounds(dim.x1, dim.y1, dim.z1, dim.x2, dim.y2, dim.z2);
    	}
    	else
    	{
    		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    	}
    }

    
    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z)
    {
    	
        //int id = idPicked(world, x, y, z);    
    	Item item = getItem(world, x, y, z);
        TileEntity t = world.getTileEntity(x, y, z);
        
        /*
        if (id == 0 || Item.itemsList[id] == null)
        {
            return null;
        }
        */
        
        if(item == null || Item.getIdFromItem(item) == 0)
        {
        	return null;
        }
        
        
        if(t instanceof TileEntityDiscreteBlock)
        {
        	TileEntityDiscreteBlock tile = (TileEntityDiscreteBlock)t;
        	int type = tile.getSubtype();
        	
        	if(type != 0)
        	{
        		return new ItemStack(item, 1, type);
        	}
        }
        
        if(t instanceof TileEntityLoomBlock)
        {
        	return new ItemStack(item, 3, 0);
        }

        return new ItemStack(item, 1, getDamageValue(world, x, y, z));      
    }
    
    
    @Override
    public boolean isNormalCube(IBlockAccess world, int x, int y, int z)
    {
    	return blocks[world.getBlockMetadata(x, y, z)].isNormalCube(world, x, y, z);
    }
    
    @Override
    public boolean isBlockSolid(IBlockAccess world, int x, int y, int z, int side)
    {
    	return blocks[world.getBlockMetadata(x, y, z)].isBlockSolid(world, x, y, z, side);
    }
    
    //Generates the collision boxes for a block
    @Override
    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB mask, List boxlist, Entity entity)
    {
    	blocks[world.getBlockMetadata(x, y, z)].getCollisionBoxes(world, x, y, z, mask, boxlist, entity);   
    }
    
    public HashMap<Short, Range> getRangeSet( )
    {
    	return RANGE_SETS;
    }
}
