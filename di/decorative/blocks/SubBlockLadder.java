package kappafox.di.decorative.blocks;

import java.util.List;

import kappafox.di.DiscreteIndustry;
import kappafox.di.base.blocks.SubBlock;
import kappafox.di.base.tileentities.TileEntityDiscreteBlock;
import kappafox.di.base.tileentities.TileEntitySubtype;
import kappafox.di.base.util.BoundSet;
import kappafox.di.base.util.PointSet;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class SubBlockLadder extends SubBlock
{
	
	private static IIcon indLadderSide;
	private static IIcon indLadderTop;
	
	private static IIcon claLadderSide;
	private static IIcon claLadderTop;
	
	private static IIcon footLadderSide;
	
	private static IIcon poleLadderSide;
	
	private static IIcon ropeLadderSide;
	
	private static IIcon testIcon;

	@Override
	public void registerBlockIcons(IIconRegister ireg) 
	{
		super.registerBlockIcons(ireg);
		indLadderSide = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "blockIndustrialLadder");
		indLadderTop = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "blockIndustrialLadder_top");
		
		claLadderSide = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "blockClassicLadder");
		claLadderTop = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "blockDiscreteCable");
		
		footLadderSide = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "blockFootholdLadder");
		
		poleLadderSide = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "blockPoleLadder");
		
		ropeLadderSide = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "blockRope");
	}

	@Override
	public IIcon getIcon(int side, int meta) 
	{
		return DEFAULT_ICON;
	}
	
	@Override
	public IIcon getOverloadedIcon(int side, int meta)
	{
		if(meta == 800)
		{
			return footLadderSide;
		}
		
		if(meta == 801)
		{
			if(side == 1 || side == 0)
			{
				return footLadderSide;
			}
			return poleLadderSide;
		}
		
		if(meta == 802)
		{
			return poleLadderSide;
		}
		
		if(meta == 803)
		{
			return ropeLadderSide;
		}
		
		if(meta == 804)
		{
			return footLadderSide;
		}
		
		if(meta == 805)
		{
			return testIcon;
		}
		
		if(meta == 806)
		{
			if(side == 1 || side == 0)
			{
				return claLadderTop;
			}
			return claLadderSide;		
		}
		
		if(meta == 807)
		{
			if(side == 1 || side == 0)
			{
				return indLadderTop;
			}
			return indLadderSide;
		}
		
		return this.getIcon(1, 0);
	}

	@Override
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) 
	{
		TileEntitySubtype tile = (TileEntitySubtype)world.getTileEntity(x, y, z);
		int type = tile.getSubtype();
		
		return this.getOverloadedIcon(side, type);
	}
	
	@Override
	public boolean isLadder(IBlockAccess world, int x, int y, int z, EntityLivingBase entity)
	{
		return true;
	}
	
	@Override
	public void getCollisionBoxes(World world, int x, int y, int z, AxisAlignedBB mask, List boxlist, Entity entity)
    {
    	TileEntityDiscreteBlock tile = (TileEntityDiscreteBlock)world.getTileEntity(x, y, z);
    	
    	AxisAlignedBB box = null;
    	
    	if(tile != null)
    	{
    		int direction = tile.getDirection();
        	switch(direction)
        	{
            	case 2:
            		box = AxisAlignedBB.getBoundingBox(x + px.zero, y + px.zero, z + px.zero, x + px.sixteen, y + px.sixteen, z + px.two);	
            		break;
            	case 3:
            		box = AxisAlignedBB.getBoundingBox(x + px.zero, y + px.zero, z + px.fourteen, x + px.sixteen, y + px.sixteen, z + px.sixteen);
            		break;
            	case 4:
            		box = AxisAlignedBB.getBoundingBox(x + px.zero, y + px.zero, z + px.zero, x + px.two, y + px.sixteen, z + px.sixteen);
            		break;
            	case 5:
            		box = AxisAlignedBB.getBoundingBox(x + px.fourteen, y + px.zero, z + px.zero, x + px.sixteen, y + px.sixteen, z + px.sixteen);
            		break;
        	}
        	
        	
        	if(box != null)
        	{
	        	if(mask.intersectsWith(box) == true)
	        	{
	        		boxlist.add(box);
	        	}
        	}
    	}
    }
	
	@Override
	public AxisAlignedBB getWireframeBox(IBlockAccess world, int x, int y, int z) 
	{
    	TileEntityDiscreteBlock tile = (TileEntityDiscreteBlock)world.getTileEntity(x, y, z);
    	
    	AxisAlignedBB box = null;
    	
    	if(tile != null)
    	{
    		int direction = tile.getDirection();
    		
    		
        	switch(direction)
        	{
            	case 2:
            		return AxisAlignedBB.getBoundingBox(x + px.zero, y + px.zero, z + px.zero, x + px.sixteen, y + px.sixteen, z + px.two);	
            	case 3:
            		return AxisAlignedBB.getBoundingBox(x + px.zero, y + px.zero, z + px.fourteen, x + px.sixteen, y + px.sixteen, z + px.sixteen);
            	case 4:
            		return AxisAlignedBB.getBoundingBox(x + px.zero, y + px.zero, z + px.zero, x + px.two, y + px.sixteen, z + px.sixteen);
            	case 5:
            		return AxisAlignedBB.getBoundingBox(x + px.fourteen, y + px.zero, z + px.zero, x + px.sixteen, y + px.sixteen, z + px.sixteen);
        	}
        	
    	}
    	
    	 return AxisAlignedBB.getBoundingBox((double)x, (double)y, (double)z, (double)x + px.sixteen, (double)y + px.sixteen, z + px.sixteen);
	}
	
   
	@Override
    public BoundSet getHitBoxesBasedOnState(IBlockAccess world, int x, int y, int z)
    {
    	TileEntityDiscreteBlock tile = (TileEntityDiscreteBlock)world.getTileEntity(x, y, z);
    	
    	AxisAlignedBB box = null;
    	
    	if(tile != null)
    	{
    		int direction = tile.getDirection();
    		
    		
        	switch(direction)
        	{
            	case 2:
            		return new PointSet(px.zero, px.zero, px.zero, px.sixteen, px.sixteen, px.two);		
            	case 3:
            		return new PointSet(px.zero, px.zero, px.fourteen, px.sixteen, px.sixteen, + px.sixteen);
            	case 4:
            		return new PointSet(px.zero, px.zero, px.zero, px.two, px.sixteen, px.sixteen);
            	case 5:
            		return new PointSet(px.fourteen, px.zero, px.zero, px.sixteen, px.sixteen, px.sixteen);
        	}
        	
    	}
    	
    	return new PointSet(0,0,0,1,1,1);
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
	public boolean isDiscrete( )
	{
		return true;
	}
}
