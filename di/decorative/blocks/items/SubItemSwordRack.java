package kappafox.di.decorative.blocks.items;

import kappafox.di.base.items.SubItem;
import kappafox.di.decorative.tileentities.TileEntitySwordRack;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;

public class SubItemSwordRack implements SubItem
{

	@Override
	public TileEntity getTileEntity(int type, int dir, int var1, Block block, int blockMeta, int side, float hitx, float hity, float hitz) 
	{
		TileEntitySwordRack tile = new TileEntitySwordRack(this.getInventorySlots(type));
		
		tile.setFullColour(true);
		tile.setSubtype(type);
		tile.setDirection((short)dir);
		
		tile.setAllTexturesFromSource(block, type);
		tile.setOriginalBlock(block);
		
		return tile;
	}
	
	private int getInventorySlots(int type)
	{
    	switch(type)
    	{
    		case 821:	//Sword Rest
    			return 1;
    		
    		case 822:	//Sword Rack
    			return 6;
    			
    		default:
    			return 0;
    			
    	}
	}

}
