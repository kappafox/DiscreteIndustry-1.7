package kappafox.di.transport.renderers;

import kappafox.di.base.BlockRenderingHandler;
import kappafox.di.transport.DiscreteTransport;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class DiscreteTransportRenderManager implements ISimpleBlockRenderingHandler
{

	private int renderID;
	private ISimpleBlockRenderingHandler ductRenderer;
	private ISimpleBlockRenderingHandler hopperRenderer;
	
	private static BlockRenderingHandler HANDLER_TRANSPORT_BLOCK;
	
	public DiscreteTransportRenderManager(int rID)
	{
		//renderID = rID;
		hopperRenderer = new HopperRenderer(rID);
		
		HANDLER_TRANSPORT_BLOCK = new BlockDiscreteTransportRenderer();
	}
	
	@Override
	public void renderInventoryBlock(Block block, int meta, int modelID, RenderBlocks renderer)
	{
		
		if(modelID == DiscreteTransport.hopperRenderID)
		{
			hopperRenderer.renderInventoryBlock(block, meta, modelID, renderer);
		}
		
		if(modelID == DiscreteTransport.transportBlockRenderID)
		{
			HANDLER_TRANSPORT_BLOCK.renderInventoryBlock(block, meta, modelID, renderer);
		}
		
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelID, RenderBlocks renderer)
	{
		
		if(modelID == DiscreteTransport.hopperRenderID)
		{
			return hopperRenderer.renderWorldBlock(world, x, y, z, block, modelID, renderer);
		}
		
		if(modelID == DiscreteTransport.transportBlockRenderID)
		{
			return HANDLER_TRANSPORT_BLOCK.renderWorldBlock(world, x, y, z, block, modelID, renderer);
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
