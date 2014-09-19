package kappafox.di.transport.renderers;

import java.util.HashMap;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import kappafox.di.base.BlockRenderingHandler;
import kappafox.di.base.SubBlockRenderingHandler;
import kappafox.di.base.tileentities.TileEntitySubtype;
import kappafox.di.decorative.blocks.BlockDecor;
import kappafox.di.decorative.renderers.SubRendererLadder;
import kappafox.di.decorative.renderers.SubRendererShape;
import kappafox.di.decorative.renderers.SubRendererStairs;
import kappafox.di.decorative.renderers.SubRendererStrut;
import kappafox.di.decorative.renderers.SubRendererWeaponRack;
import kappafox.di.transport.blocks.BlockDiscreteTransport;

public class BlockDiscreteTransportRenderer extends BlockRenderingHandler
{
	private static SubRendererStorageRack SUB_RENDERER_STORAGE_RACK;

	
	public BlockDiscreteTransportRenderer( )
	{
		 sub = new HashMap<Integer, SubBlockRenderingHandler>();
		 
		 SUB_RENDERER_STORAGE_RACK = new SubRendererStorageRack();
		  
		 this.mapSubBlockRenderers();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelID, RenderBlocks renderer) 
	{
		TileEntity tile = world.getTileEntity(x, y, z);
		
		if(tile != null && tile instanceof TileEntitySubtype)
		{
			TileEntitySubtype te = (TileEntitySubtype)tile;
			if(sub.containsKey(te.getSubtype()))
			{
				return sub.get(te.getSubtype()).renderWorldBlock(world, x, y, z, block, modelID, renderer);
			}
		}
		
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void renderInventoryBlock(Block block, int subtype, int modelID, RenderBlocks renderer) 
	{
		if(sub.containsKey(subtype))
		{
			sub.get(subtype).renderInventoryBlock(block, subtype, modelID, renderer);
		}
	}
	
	private void mapSubBlockRenderers( )
	{	
		super.registerHandlerRange(BlockDiscreteTransport.RANGE_STORAGE_RACK.lowerEndpoint(), BlockDiscreteTransport.RANGE_STORAGE_RACK.upperEndpoint(), SUB_RENDERER_STORAGE_RACK);
	}

}
