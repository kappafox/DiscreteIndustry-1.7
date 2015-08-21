package kappafox.di.decorative.renderers;

import java.util.HashMap;
import java.util.Map.Entry;

import org.lwjgl.opengl.GL11;

import kappafox.di.base.SubBlockRenderingHandler;
import kappafox.di.base.tileentities.TileEntitySubtype;
import kappafox.di.base.util.PointSet;
import kappafox.di.decorative.blocks.BlockDecor;
import kappafox.di.decorative.blocks.SubBlockBridge;
import kappafox.di.decorative.blocks.SubBlockWall;
import kappafox.di.decorative.tileentities.TileEntityBridgeBlock;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

public class SubRendererBridge extends SubBlockRenderingHandler 
{
	private static final PointSet SIMPLE_CATWALK_RAIL_TOP_NORTH = new PointSet(px.zero, px.fifteen, px.zero, px.sixteen, px.sixteen, px.one);
	private static final PointSet SIMPLE_CATWALK_RAIL_MID_NORTH = new PointSet(px.half, px.eight, px.zero, px.fifteen + px.half, px.nine, px.half);
	private static final PointSet SIMPLE_CATWALK_RAIL_POST_NORTHWEST = new PointSet(px.zero, px.zero, px.zero, px.half, px.fifteen, px.half);
	private static final PointSet SIMPLE_CATWALK_RAIL_POST_NORTHEAST = new PointSet(px.fifteen + px.half, px.zero, px.zero, px.sixteen, px.fifteen, px.half);
	
	private static final PointSet SIMPLE_CATWALK_RAIL_CORNER_TOP_NORTHWEST = new PointSet(px.zero, px.zero, px.zero, px.one, px.sixteen, px.one);
	private static final PointSet SIMPLE_CATWALK_RAIL_CORNER_TOP_NORTHEAST = new PointSet(px.fifteen, px.zero, px.zero, px.sixteen, px.sixteen, px.one);
	
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelID, RenderBlocks renderer) 
	{
		TileEntitySubtype tile = (TileEntitySubtype)world.getTileEntity(x, y, z);
		int type = tile.getSubtype();
		
		switch(type)
		{
			case BlockDecor.ID_BRIDGE_CATWALK_SIMPLE:
			{
				return this.renderWorldBridgeCatwalkSimpleBlock(world, x, y, z, block, tile, renderer, type);
			}
		}
		
		return false;
	}

	@Override
	public void renderInventoryBlock(Block block, int subtype, int modelID, RenderBlocks renderer) 
	{
		tessellator.startDrawingQuads();
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		
		switch(subtype)
		{
			
			case BlockDecor.ID_BRIDGE_CATWALK_SIMPLE:
			{
				this.renderInventoryCatwalkSimple(block, subtype, modelID, renderer);
				break;
			}
		}
		
		drh.resetGL11Scale();		
		tessellator.draw();	
	}
	

	private void renderInventoryCatwalkSimple(Block block, int subtype, int modelID, RenderBlocks renderer) 
	{
		renderer.setRenderBounds(px.zero, px.zero, px.zero, px.sixteen, px.one, px.sixteen);
		drh.tessellateInventoryBlock(renderer, block, subtype);
		
		// Posts
		renderer.setRenderBounds(px.zero, px.one, px.zero, px.one, px.fifteen, px.one);
		drh.tessellateInventoryBlock(renderer, block, subtype);
		
		renderer.setRenderBounds(px.fifteen, px.one, px.zero, px.sixteen, px.fifteen, px.one);
		drh.tessellateInventoryBlock(renderer, block, subtype);
		
		renderer.setRenderBounds(px.zero, px.one, px.fifteen, px.one, px.fifteen, px.sixteen);
		drh.tessellateInventoryBlock(renderer, block, subtype);
		
		renderer.setRenderBounds(px.fifteen, px.one, px.fifteen, px.sixteen, px.fifteen, px.sixteen);
		drh.tessellateInventoryBlock(renderer, block, subtype);
		
		// Top rails
		renderer.setRenderBounds(px.zero, px.fifteen, px.zero, px.sixteen, px.sixteen, px.one);
		drh.tessellateInventoryBlock(renderer, block, subtype);
		
		renderer.setRenderBounds(px.zero, px.fifteen, px.fifteen, px.sixteen, px.sixteen, px.sixteen);
		drh.tessellateInventoryBlock(renderer, block, subtype);
		
		renderer.setRenderBounds(px.zero, px.fifteen, px.one, px.one, px.sixteen, px.fifteen);
		drh.tessellateInventoryBlock(renderer, block, subtype);
		
		renderer.setRenderBounds(px.fifteen, px.fifteen, px.one, px.sixteen, px.sixteen, px.fifteen);
		drh.tessellateInventoryBlock(renderer, block, subtype);
		
		// Mid rails
		renderer.setRenderBounds(px.zero, px.seven, px.zero, px.sixteen, px.eight, px.one);
		drh.tessellateInventoryBlock(renderer, block, subtype);
		
		renderer.setRenderBounds(px.zero, px.seven, px.fifteen, px.sixteen, px.eight, px.sixteen);
		drh.tessellateInventoryBlock(renderer, block, subtype);
		
		renderer.setRenderBounds(px.zero, px.seven, px.one, px.one, px.eight, px.fifteen);
		drh.tessellateInventoryBlock(renderer, block, subtype);
		
		renderer.setRenderBounds(px.fifteen, px.seven, px.one, px.sixteen, px.eight, px.fifteen);
		drh.tessellateInventoryBlock(renderer, block, subtype);
	}

	private boolean renderWorldBridgeCatwalkSimpleBlock(IBlockAccess world, int x, int y, int z, Block block, TileEntitySubtype tile, RenderBlocks renderer, int type) 
	{
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, new PointSet(px.zero, -px.one - 0.001, px.zero, px.sixteen, px.zero + 0.001, px.sixteen));
		
		TileEntityBridgeBlock bridge = (TileEntityBridgeBlock)tile;
		
		HashMap<ForgeDirection, Boolean> connectionMap = bridge.getConnectionMap();
		HashMap<ForgeDirection, Boolean> adjacencyMap = SubBlockBridge.getAdjacencyMap(world, x, y, z);
		
		SubBlockBridge.mergeMaps(adjacencyMap, connectionMap);

		if(adjacencyMap.get(ForgeDirection.SOUTH) && adjacencyMap.get(ForgeDirection.WEST) && !SubBlockBridge.isBridge(world.getTileEntity(x - 1, y, z + 1)))
		{
			drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SIMPLE_CATWALK_RAIL_CORNER_TOP_NORTHWEST, ForgeDirection.WEST);
		}
		
		if(adjacencyMap.get(ForgeDirection.NORTH) && adjacencyMap.get(ForgeDirection.WEST) && !SubBlockBridge.isBridge(world.getTileEntity(x - 1, y, z - 1)))
		{
			drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SIMPLE_CATWALK_RAIL_CORNER_TOP_NORTHWEST);
		}
		
		if(adjacencyMap.get(ForgeDirection.NORTH) && adjacencyMap.get(ForgeDirection.EAST) && !SubBlockBridge.isBridge(world.getTileEntity(x + 1, y, z - 1)))
		{
			drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SIMPLE_CATWALK_RAIL_CORNER_TOP_NORTHEAST);
		}
		
		if(adjacencyMap.get(ForgeDirection.SOUTH) && adjacencyMap.get(ForgeDirection.EAST) && !SubBlockBridge.isBridge(world.getTileEntity(x + 1, y, z + 1)))
		{
			drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SIMPLE_CATWALK_RAIL_CORNER_TOP_NORTHEAST, ForgeDirection.EAST);
		}
		
		if(!adjacencyMap.get(ForgeDirection.NORTH))
		{
			drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SIMPLE_CATWALK_RAIL_TOP_NORTH);
			drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SIMPLE_CATWALK_RAIL_MID_NORTH);		
			drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SIMPLE_CATWALK_RAIL_POST_NORTHEAST);		
			drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SIMPLE_CATWALK_RAIL_POST_NORTHWEST);		
		}
		
		if(!adjacencyMap.get(ForgeDirection.SOUTH))
		{
			drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SIMPLE_CATWALK_RAIL_TOP_NORTH, ForgeDirection.SOUTH);
			drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SIMPLE_CATWALK_RAIL_MID_NORTH, ForgeDirection.SOUTH);
			drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SIMPLE_CATWALK_RAIL_POST_NORTHEAST, ForgeDirection.SOUTH);		
			drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SIMPLE_CATWALK_RAIL_POST_NORTHWEST, ForgeDirection.SOUTH);	
		}
		
		if(!adjacencyMap.get(ForgeDirection.EAST))
		{
			drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SIMPLE_CATWALK_RAIL_TOP_NORTH, ForgeDirection.EAST);
			drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SIMPLE_CATWALK_RAIL_MID_NORTH, ForgeDirection.EAST);
			drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SIMPLE_CATWALK_RAIL_POST_NORTHEAST, ForgeDirection.EAST);		
			drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SIMPLE_CATWALK_RAIL_POST_NORTHWEST, ForgeDirection.EAST);	
		}

		if(!adjacencyMap.get(ForgeDirection.WEST))
		{
			drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SIMPLE_CATWALK_RAIL_TOP_NORTH, ForgeDirection.WEST);
			drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SIMPLE_CATWALK_RAIL_MID_NORTH, ForgeDirection.WEST);
			drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SIMPLE_CATWALK_RAIL_POST_NORTHEAST, ForgeDirection.WEST);		
			drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SIMPLE_CATWALK_RAIL_POST_NORTHWEST, ForgeDirection.WEST);	
		}
		
		return true;
	}

}
