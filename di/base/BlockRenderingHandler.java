package kappafox.di.base;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

public abstract class BlockRenderingHandler 
{
	
	protected HashMap<Integer, SubBlockRenderingHandler> sub;
	
	public abstract boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelID, RenderBlocks renderer);
	public abstract void renderInventoryBlock(Block block, int subtype, int modelID, RenderBlocks renderer);
	
	public void registerHandler(int key, SubBlockRenderingHandler handler)
	{
		if(!sub.containsKey(key))
		{
			sub.put(key, handler);
		}
	}
	
	public void registerHandlerRange(int keyStart, int keyEnd, SubBlockRenderingHandler handler)
	{
		if(keyStart < keyEnd)
		{
			for(int i = keyStart; i <= keyEnd; i++)
			{
				this.registerHandler(i, handler);
			}
		}
	}
}
