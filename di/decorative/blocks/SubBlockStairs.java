package kappafox.di.decorative.blocks;

import java.util.List;

import kappafox.di.DiscreteIndustry;
import kappafox.di.base.blocks.SubBlock;
import kappafox.di.base.tileentities.TileEntityDiscreteBlock;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class SubBlockStairs extends SubBlock
{
	
	private static IIcon STAIR_TEXTURE;
	private static IIcon STAIR_SMALL_TEXTURE;
	private static IIcon STAIR_TEXTURE_TOP;
	private static IIcon STAIR_TEXTURE_SIDE;
	private static IIcon STAIR_TEXTURE_BACK;
	
	@Override
	public void registerBlockIcons(IIconRegister ireg) 
	{
		STAIR_TEXTURE = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "blockDiscreteStairs");
		STAIR_SMALL_TEXTURE = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "blockDiscreteSmallStairs");
		
		STAIR_TEXTURE_TOP = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "blockDiscreteStairstop");
		STAIR_TEXTURE_SIDE = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "blockDiscreteStairsside");
		STAIR_TEXTURE_BACK = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "blockDiscreteStairsback");
	}

	
	@Override
	public IIcon getIcon(int side, int meta) 
	{
		/*
		if(side == 0 || side == 1)
		{
			return STAIRTEXTURETOP;
		}
		
		if(side == 4 || side == 5)
		{
			return STAIRTEXTURESIDE;
		}
		
		if(side == 7)
		{
			return STAIRTEXTUREBACK;
		}
		*/
		
		if(meta == 862)
		{
			return STAIR_SMALL_TEXTURE;
		}
		return STAIR_TEXTURE;
	}
	
	@Override
	public IIcon getOverloadedIcon(int side, int meta)
	{
		return this.getIcon(side, meta);
	}

	@Override
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) 
	{
		TileEntityDiscreteBlock tile = (TileEntityDiscreteBlock)world.getTileEntity(x, y, z);
		
		if(tile != null)
		{
			return this.getIcon(0, tile.getSubtype());
			/*
			int dir = tile.getDirection();
			
			if(dir == 2)
			{
				switch(side)
				{
					case 0:
						return this.getIcon(0, 0);
					case 1:
						return this.getIcon(1, 0);
					case 2:
						return this.getIcon(7, 0);
					case 3:
						return this.getIcon(3, 0);
					case 4:
						return new IconFlipped(this.getIcon(4, 0), true, false);
					case 5:
						return this.getIcon(5, 0);
						
						
				}
			}
			
			if(dir == 3)
			{
				if(side == 1 || side == 0)
				{
					return this.getIcon(1, 0);
				}
				
				if(side == 4 || side == 5)
				{
					return new IconFlipped(this.getIcon(4, 0), true, false);
				}
			}
			*/
		}
		return STAIR_TEXTURE;
	}
	
	@Override
    public boolean isDiscrete( )
    {
    	return true;
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
	
	@Override
	public void getCollisionBoxes(World world, int x, int y, int z, AxisAlignedBB mask, List boxlist, Entity entity)
    {
    	TileEntityDiscreteBlock tile = (TileEntityDiscreteBlock)world.getTileEntity(x, y, z);
    	int subtype = tile.getSubtype();
    	
    	AxisAlignedBB box = null;
    	AxisAlignedBB box2 = null;
    	
    	
    	if(tile != null)
    	{
    		int direction = tile.getDirection();
    		
    		double y1 = px.dzero;
    		double y2 = px.deight;
    		double y3 = y2;
    		double y4 = px.dsixteen;
    		
    		//stairs are upside down
    		if(tile.getVariable() == 1)
    		{
    			y1 = px.deight;
    			y2 = px.dsixteen;
    			y3 = px.dzero;
    			y4 = px.deight;
    		}

    		//base box
            box = AxisAlignedBB.getBoundingBox((double)x + px.zero, (double)y + y1, (double)z + px.zero, (double)x + px.sixteen, (double)y + y2, (double)z + px.sixteen);	

        	
        	if(box != null)
        	{
	        	if(mask.intersectsWith(box) == true)
	        	{
	        		boxlist.add(box);
	        	}
        	}
        	
        	double d1 = px.dzero;
        	double d2 = px.dsixteen;
        	boolean con = false;
        	switch(direction)
        	{
        		//
	        	case 2:
	        	{

					int dir2 = SubBlockStairs.shouldStairsConnect(world, x, y, z - 1, tile);
					
					if(dir2 == 4)
					{
						d2 = px.eight;
					}
					
					if(dir2 == 5)
					{
						d1 = px.eight;
					}
					
					box = AxisAlignedBB.getBoundingBox((double)x + d1, (double)y + y3, (double)z + px.zero, (double)x + d2, (double)y + y4, (double)z + px.eight);
					
					
					int dir3 = SubBlockStairs.shouldStairsConnect(world, x, y, z + 1, tile);
					
					if(dir3 == 4)
					{
						box2 = AxisAlignedBB.getBoundingBox(x + px.dzero, y + y3, z + px.eight, x + px.eight, y + y4, z+ px.sixteen);
					}
					
					if(dir3 == 5)
					{
						box2 = AxisAlignedBB.getBoundingBox(x + px.eight, y + y3, z + px.eight, x + px.sixteen, y + y4, z+ px.sixteen);
					}

	        		break;
	        	}
	        	
	        	case 3:
	        	{

					int dir2 = SubBlockStairs.shouldStairsConnect(world, x, y, z + 1, tile);

					
					if(dir2 == 4)
					{
						d2 = px.eight;
					}
					
					if(dir2 == 5)
					{
						d1 = px.eight;
					}
					
	        		box = AxisAlignedBB.getBoundingBox((double)x + d1, (double)y + y3, (double)z + px.eight, (double)x + d2, (double)y + y4, (double)z + px.sixteen);
	        		
					int dir3 = SubBlockStairs.shouldStairsConnect(world, x, y, z - 1, tile);
					
					if(dir3 == 4)
					{
						box2 = AxisAlignedBB.getBoundingBox(x + px.zero, y + y3, z + px.zero, x + px.eight, y + y4, z+ px.eight);
					}
					
					if(dir3 == 5)
					{
						box2 = AxisAlignedBB.getBoundingBox(x + px.eight, y + y3, z + px.zero, x + px.sixteen, y + y4, z+ px.eight);
					}
					
					break;
	        	}
	        	
	        	case 4:
	        	{
					int dir2 = SubBlockStairs.shouldStairsConnect(world, x - 1, y, z, tile);
					
					if(dir2 == 2)
					{
						d2 = px.eight;
					}
					
					if(dir2 == 3)
					{
						d1 = px.eight;
					}
					
	        		box = AxisAlignedBB.getBoundingBox((double)x + px.zero, (double)y + y3, (double)z + d1, (double)x + px.eight, (double)y + y4, (double)z + d2);
	        		
					int dir3 = SubBlockStairs.shouldStairsConnect(world, x + 1, y, z, tile);
					
					if(dir3 == 2)
					{
						box2 = AxisAlignedBB.getBoundingBox(x + px.eight, y + y3, z + px.zero, x + px.sixteen, y + y4, z+ px.eight);
					}
					
					if(dir3 == 3)
					{
						box2 = AxisAlignedBB.getBoundingBox(x + px.eight, y + y3, z + px.eight, x + px.sixteen, y + y4, z+ px.sixteen);
					}
					
	        		break;
	        	}
	        	
	        	case 5:
	        	{
	        		int dir2 = SubBlockStairs.shouldStairsConnect(world, x + 1, y, z, tile);
	        		
					if(dir2 == 2)
					{
						d2 = px.eight;
					}
					
					if(dir2 == 3)
					{
						d1 = px.eight;
					}
					
	        		
	        		box = AxisAlignedBB.getBoundingBox((double)x + px.eight, (double)y + y3, (double)z + d1, (double)x + px.sixteen, (double)y + y4, (double)z + d2);
	        		
					int dir3 = SubBlockStairs.shouldStairsConnect(world, x - 1, y, z, tile);
					
					if(dir3 == 2)
					{
						box2 = AxisAlignedBB.getBoundingBox(x + px.zero, y + y3, z + px.zero, x + px.eight, y + y4, z+ px.eight);
					}
					
					if(dir3 == 3)
					{
						box2 = AxisAlignedBB.getBoundingBox(x + px.zero, y + y3, z + px.eight, x + px.eight, y + y4, z+ px.sixteen);
					}
					
	        		break;
	        	}
	        	
	        	default:
	        		box = AxisAlignedBB.getBoundingBox((double)x + px.zero, (double)y + px.eight, (double)z + px.zero, (double)x + px.sixteen, (double)y + px.sixteen, (double)z + px.sixteen);
        	}
            //box = AxisAlignedBB.getBoundingBox((double)x + zeroPx, (double)y + eightPx, (double)z + zeroPx, (double)x + sixteenPx, (double)y + sixteenPx, (double)z + eightPx);	

        	
        	if(box != null)
        	{
	        	if(mask.intersectsWith(box) == true)
	        	{
	        		boxlist.add(box);
	        	}
        	}
        	
        	if(box2 != null)
        	{
	        	if(mask.intersectsWith(box2) == true)
	        	{
	        		boxlist.add(box2);
	        	}
        	}
        	
    	}
    }
	
	
	public static int shouldStairsConnect(World world, int x, int y, int z, TileEntityDiscreteBlock stair)
	{
		TileEntity tile = world.getTileEntity(x, y, z);
		
		if(tile != null && tile instanceof TileEntityDiscreteBlock && stair != null)
		{
			TileEntityDiscreteBlock tile2 = (TileEntityDiscreteBlock)tile;
			
			if(tile2.getSubtype() == stair.getSubtype())
			{
				//int dir = stair.getDirection();
				return tile2.getDirection();
				
				
			}
		}
		return -1;
	}

}
