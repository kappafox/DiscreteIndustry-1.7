package kappafox.di.decorative.blocks.items;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import kappafox.di.base.items.SubItem;
import kappafox.di.base.tileentities.TileEntityDiscreteBlock;
import kappafox.di.decorative.blocks.BlockDecor;
import kappafox.di.decorative.tileentities.TileEntityFixtureBlock;

public class SubItemShape implements SubItem
{

	@Override
	public TileEntity getTileEntity(int type, int dir, int var1, Block block, int blockMeta, int side, float hitx, float hity, float hitz)
	{
		TileEntityDiscreteBlock tile = new TileEntityDiscreteBlock();
		
		tile.setFullColour(false);
		tile.setSubtype(type);
		tile.setDirection((short)dir);
		
		tile.setAllTexturesFromSource(block, type);
		tile.setOriginalBlock(block);
		tile.setVariable(0);
		
		if(type == BlockDecor.ID_SHAPE_SLAB)
		{
			tile.setVariable(this.getSlabOrientation(side, hity));
		}
		return tile;
	}
	
	private int getSlabOrientation(int side, float hity)
	{
		if(side == 1)
		{
			return 0;
		}
		
		if(side == 0)
		{
			return 1;
		}
		
		if(hity > 0.5)
		{
			return 1;
		}
		
		return 0;
	}

}
