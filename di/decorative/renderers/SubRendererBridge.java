package kappafox.di.decorative.renderers;

import kappafox.di.base.SubBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

public class SubRendererBridge extends SubBlockRenderingHandler {

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelID, RenderBlocks renderer) 
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void renderInventoryBlock(Block block, int subtype, int modelID, RenderBlocks renderer) 
	{
		// TODO Auto-generated method stub
	}

}
