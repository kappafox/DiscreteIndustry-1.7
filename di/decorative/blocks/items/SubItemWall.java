package kappafox.di.decorative.blocks.items;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import kappafox.di.base.items.SubItem;
import kappafox.di.base.tileentities.TileEntityDiscreteBlock;
import kappafox.di.base.tileentities.TileEntitySidedConnector;
import kappafox.di.decorative.blocks.BlockDecor;

public class SubItemWall implements SubItem
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
		
		return tile;
	}
}
