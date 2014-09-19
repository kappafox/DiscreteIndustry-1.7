package kappafox.di.transport.blocks.items;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import kappafox.di.base.items.SubItem;
import kappafox.di.base.tileentities.TileEntityDiscreteBlock;
import kappafox.di.transport.blocks.BlockDiscreteTransport;
import kappafox.di.transport.tileentities.TileEntityStorageRack;

public class SubItemStorageRack implements SubItem
{

	@Override
	public TileEntity getTileEntity(int type, int dir, int var1, Block block, int blockMeta, int side, float hitx, float hity, float hitz) 
	{
		TileEntityDiscreteBlock tile = new TileEntityStorageRack(1);
		
		if(type == BlockDiscreteTransport.ID_STORAGERACK_SINGLE)
		{
			tile = new TileEntityStorageRack(1);
		}
		
		if(type == BlockDiscreteTransport.ID_STORAGERACK_DUAL)
		{
			tile = new TileEntityStorageRack(2);
		}
		
		if(type == BlockDiscreteTransport.ID_STORAGERACK_QUAD)
		{
			tile = new TileEntityStorageRack(4);
		}
		
		tile.setOriginalBlock(block);
		tile.setSubtype(type);
		tile.setDirection((short)dir);
		tile.setFullColour(true);
		tile.setAllTexturesFromSource(block, type);

		
		return tile;
		
	}

}
