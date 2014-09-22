package kappafox.di.decorative.blocks.items;

import kappafox.di.base.items.SubItem;
import kappafox.di.base.tileentities.TileEntitySidedConnector;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;

public class SubItemFixture implements SubItem 
{

	@Override
	public TileEntity getTileEntity(int type, int dir, int var1, Block block, int blockMeta, int side, float hitx, float hity, float hitz) 
	{
		TileEntitySidedConnector tile = new TileEntitySidedConnector();
		
		tile.setFullColour(true);
		tile.setSubtype(type);
		tile.setDirection((short)dir);
		
		tile.setAllTexturesFromSource(block, type);
		tile.setOriginalBlock(block);
		tile.setAllConnections(true);
		
		return tile;
	}
}
