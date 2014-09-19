package kappafox.di.base.items;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;

public interface SubItem 
{	
	public abstract TileEntity getTileEntity(int type, int dir, int var1, Block block, int blockMeta, int side, float hitx, float hity, float hitz);
}
