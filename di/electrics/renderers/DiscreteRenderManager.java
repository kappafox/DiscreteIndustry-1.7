package kappafox.di.electrics.renderers;

import kappafox.di.electrics.DiscreteElectrics;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class DiscreteRenderManager implements ISimpleBlockRenderingHandler
{

	private int renderID;
	private ISimpleBlockRenderingHandler plugSocket;
	
	public DiscreteRenderManager(int rID_)
	{
		renderID = rID_;
		plugSocket = new PlugSocketRenderer(renderID);
	}
	
	@Override
	public void renderInventoryBlock(Block block_, int meta_, int modelID_, RenderBlocks renderer_)
	{
		if(modelID_ == DiscreteElectrics.discreteCableModelID)
		{
			plugSocket.renderInventoryBlock(block_, meta_, modelID_, renderer_);
		}
		
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world_, int x_, int y_, int z_, Block block_, int modelID_, RenderBlocks renderer_)
	{
		if(modelID_ == DiscreteElectrics.discreteCableModelID)
		{
			return plugSocket.renderWorldBlock(world_, x_, y_, z_, block_, modelID_, renderer_);
		}
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId)
	{
		return true;
	}

	@Override
	public int getRenderId()
	{
		return renderID;
	}

	
}
