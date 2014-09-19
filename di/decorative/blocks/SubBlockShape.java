package kappafox.di.decorative.blocks;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import kappafox.di.DiscreteIndustry;
import kappafox.di.base.blocks.SubBlock;
import kappafox.di.base.tileentities.TileEntityDiscreteBlock;
import kappafox.di.base.util.BoundSet;
import kappafox.di.base.util.PointSet;

public class SubBlockShape extends SubBlock
{
	
	private static final PointSet SLAB_LOWER = new PointSet(px.zero, px.zero, px.zero, px.sixteen, px.eight, px.sixteen);
	private static final PointSet SLAB_UPPER = new PointSet(px.zero, px.eight, px.zero, px.sixteen, px.sixteen, px.sixteen);
	
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
	
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
    {
    	TileEntityDiscreteBlock tile = (TileEntityDiscreteBlock)world.getTileEntity(x, y, z);
    	
    	if(tile != null)
    	{
    		if(tile.getVariable() == 1)
    		{
    			return AxisAlignedBB.getBoundingBox(x + SLAB_UPPER.x1,  y + SLAB_UPPER.y1, z + SLAB_UPPER.z1, x + SLAB_UPPER.x2,  y + SLAB_UPPER.y2, z + SLAB_UPPER.z2);
    		}
    	}
    		
    	return AxisAlignedBB.getBoundingBox(x + SLAB_LOWER.x1,  y + SLAB_LOWER.y1, z + SLAB_LOWER.z1, x + SLAB_LOWER.x2,  y + SLAB_LOWER.y2, z + SLAB_LOWER.z2);
    }
    
    
    public void getCollisionBoxes(World world, int x, int y, int z, AxisAlignedBB mask, List boxlist, Entity entity)
    {
        AxisAlignedBB axisalignedbb1 = this.getCollisionBoundingBoxFromPool(world, x, y, z);

        if (axisalignedbb1 != null && mask.intersectsWith(axisalignedbb1))
        {
            boxlist.add(axisalignedbb1);
        }
    }
    
    
    
    public BoundSet getHitBoxesBasedOnState(IBlockAccess world, int x, int y, int z)
    {
    	TileEntityDiscreteBlock tile = (TileEntityDiscreteBlock)world.getTileEntity(x, y, z);
    	
    	if(tile != null)
    	{
    		if(tile.getVariable() == 1)
    		{
    			return SLAB_UPPER;
    		}
    	}
    	
    	return SLAB_LOWER;
    }
    
	public AxisAlignedBB getWireframeBox(World world, int x, int y, int z) 
	{
    	TileEntityDiscreteBlock tile = (TileEntityDiscreteBlock)world.getTileEntity(x, y, z);
    	
    	if(tile != null)
    	{
    		if(tile.getVariable() == 1)
    		{
    			return AxisAlignedBB.getBoundingBox(x + SLAB_UPPER.x1,  y + SLAB_UPPER.y1, z + SLAB_UPPER.z1, x + SLAB_UPPER.x2,  y + SLAB_UPPER.y2, z + SLAB_UPPER.z2);
    		}
    	}
    	
   	 	return AxisAlignedBB.getBoundingBox(x + SLAB_LOWER.x1,  y + SLAB_LOWER.y1, z + SLAB_LOWER.z1, x + SLAB_LOWER.x2,  y + SLAB_LOWER.y2, z + SLAB_LOWER.z2);
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

}
