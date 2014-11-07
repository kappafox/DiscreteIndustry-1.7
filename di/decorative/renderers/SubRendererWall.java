package kappafox.di.decorative.renderers;

import java.util.HashMap;
import java.util.Map.Entry;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import kappafox.di.base.SubBlockRenderingHandler;
import kappafox.di.base.tileentities.TileEntityDiscreteBlock;
import kappafox.di.base.tileentities.TileEntitySidedConnector;
import kappafox.di.base.util.PointSet;
import kappafox.di.base.util.TextureOffset;
import kappafox.di.decorative.blocks.BlockDecor;
import kappafox.di.decorative.blocks.SubBlockWall;

public class SubRendererWall extends SubBlockRenderingHandler
{
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelID, RenderBlocks renderer)
	{
		TileEntityDiscreteBlock tile = (TileEntityDiscreteBlock)world.getTileEntity(x, y, z);
		int subtype = tile.getSubtype();
		
		switch(subtype)
		{
			case BlockDecor.ID_WALL_DISCRETE:
				return this.renderWorldDiscreteWallBlock(world, x, y, z, block, tile, renderer, subtype);
				
			case BlockDecor.ID_WALL_RAILING_SIMPLE:
				return this.renderWorldDiscreteWallRailingSimple(world, x, y, z, block, tile, renderer, subtype);
				
			case BlockDecor.ID_WALL_RAILING_DOUBLE:
				return this.renderWorldDiscreteWallRailingDouble(world, x, y, z, block, tile, renderer, subtype);
				
			case BlockDecor.ID_WALL_RAILING_TRIPLE:
				return this.renderWorldDiscreteWallRailingTriple(world, x, y, z, block, tile, renderer, subtype);
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
			case BlockDecor.ID_WALL_DISCRETE:
			{
				//this.renderInventoryDiscreteStairs(block, subtype, modelID, renderer);
				break;
			}
		}
		
		drh.resetGL11Scale();		
		tessellator.draw();	
	}
	
	private boolean renderWorldDiscreteWallBlock(IBlockAccess world, int x, int y, int z, Block block, TileEntityDiscreteBlock t, RenderBlocks renderer, int subtype) 
	{
		HashMap<ForgeDirection, Boolean> adjMap = SubBlockWall.getAdjacencyMap(world, x, y, z);
		HashMap<ForgeDirection, Boolean> conMap = ((TileEntitySidedConnector)t).getConnectionMap();
		
		if(adjMap.size() == 0 || conMap.size() == 0) return false;
		
		boolean topBlock = SubBlockWall.shouldWallConnect(world.getBlock(x, y + 1, z)) && !SubBlockWall.isWall(world.getTileEntity(x, y + 1, z));
		
		SubBlockWall.mergeMaps(adjMap, conMap);
		int adjCount = SubBlockWall.getAdjacencyCount(adjMap);
		boolean verticalStretch = false;
		boolean bottomOfStack = adjMap.get(ForgeDirection.UP) && !adjMap.get(ForgeDirection.DOWN);

		if(adjMap.get(ForgeDirection.DOWN))
		{
			verticalStretch = true;
		}
		
		//with 0, 1 or 3+ we draw the 'post'
		if(adjCount != 2 || topBlock || SubBlockWall.isCorner(adjMap))
		{
			drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_WALL_POST);
		}
		
		
		TextureOffset off;
		
		if(adjMap.get(ForgeDirection.NORTH))
		{
			off = new TextureOffset();
			off.setOffsetU(5, 8);
			
			if(bottomOfStack || !verticalStretch)
			{
				off.setOffsetV(5, -3);
				off.setOffsetV(4, -3);
			}
			
			drh.setRenderBounds(renderer, (verticalStretch) ? SubBlockWall.PART_WALL_NORTH_EXT : SubBlockWall.PART_WALL_NORTH);
			drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off, false);
		}
		
		if(adjMap.get(ForgeDirection.SOUTH))
		{
			off = new TextureOffset();
			off.setOffsetU(5, -8);
			
			if(bottomOfStack || !verticalStretch)
			{
				off.setOffsetV(5, -3);
				off.setOffsetV(4, -3);
			}
			
			drh.setRenderBounds(renderer, (verticalStretch) ? SubBlockWall.PART_WALL_SOUTH_EXT : SubBlockWall.PART_WALL_SOUTH);
			drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off, false);
		}
		
		if(adjMap.get(ForgeDirection.EAST))
		{
			off = new TextureOffset();
			off.setOffsetU(2, -8);
			
			if(bottomOfStack || !verticalStretch)
			{
				off.setOffsetV(2, -3);
				off.setOffsetV(3, -3);
			}
			
			drh.setRenderBounds(renderer, (verticalStretch) ? SubBlockWall.PART_WALL_EAST_EXT : SubBlockWall.PART_WALL_EAST);
			drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off, false);

		}
		
		if(adjMap.get(ForgeDirection.WEST))
		{
			off = new TextureOffset();
			off.setOffsetU(2, 8);
			
			if(bottomOfStack || !verticalStretch)
			{
				off.setOffsetV(2, -3);
				off.setOffsetV(3, -3);
			}
			
			drh.setRenderBounds(renderer, (verticalStretch) ? SubBlockWall.PART_WALL_WEST_EXT : SubBlockWall.PART_WALL_WEST);
			drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off, false);
		}

		return false;
	}
	
	private boolean renderWorldDiscreteWallRailingSimple(IBlockAccess world, int x, int y, int z, Block block, TileEntityDiscreteBlock tile, RenderBlocks renderer, int subtype) 
	{
		HashMap<ForgeDirection, Boolean> adjMap = SubBlockWall.getAdjacencyMap(world, x, y, z);
		HashMap<ForgeDirection, Boolean> conMap = ((TileEntitySidedConnector)tile).getConnectionMap();
		
		if(adjMap.size() == 0 || conMap.size() == 0) return false;
		
		boolean topBlock = SubBlockWall.shouldWallConnect(world.getBlock(x, y + 1, z)) && !SubBlockWall.isWall(world.getTileEntity(x, y + 1, z));
		
		SubBlockWall.mergeMaps(adjMap, conMap);
		int adjCount = SubBlockWall.getAdjacencyCount(adjMap);
		boolean verticalStretch = false;
		boolean bottomOfStack = !adjMap.get(ForgeDirection.DOWN);

		if(adjMap.get(ForgeDirection.DOWN))
		{
			verticalStretch = true;
		}
		
		if(((TileEntitySidedConnector)tile).getConnection((short)0))
		{
			drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_WALL_RAILING_SIMPLE_POST);
			
			if(bottomOfStack)
			{
				drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_WALL_RAILING_SIMPLE_POST_BASE);
			}
		}
		else
		{
			drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, new PointSet(px.seven + px.half, px.fifteen, px.seven + px.half, px.eight + px.half, px.sixteen, px.eight + px.half));
		}
		
		TextureOffset off;
		
		if(adjMap.get(ForgeDirection.NORTH))
		{

			off = new TextureOffset();
			/*
			off.setOffsetU(5, 8);
			
			if(bottomOfStack || !verticalStretch)
			{
				off.setOffsetV(5, -3);
				off.setOffsetV(4, -3);
			}
			*/
			drh.setRenderBounds(renderer, SubBlockWall.PART_WALL_RAILING_SIMPLE_NORTH);
			drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off, false);
		}
		
		if(adjMap.get(ForgeDirection.SOUTH))
		{
			off = new TextureOffset();
			/*
			off.setOffsetU(5, -8);
			
			if(bottomOfStack || !verticalStretch)
			{
				off.setOffsetV(5, -3);
				off.setOffsetV(4, -3);
			}
			*/
			
			drh.setRenderBounds(renderer, drh.translate(ForgeDirection.NORTH, ForgeDirection.SOUTH, SubBlockWall.PART_WALL_RAILING_SIMPLE_NORTH));
			drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off, false);
		}
		
		if(adjMap.get(ForgeDirection.EAST))
		{
			off = new TextureOffset();
			/*
			off.setOffsetU(2, -8);
			
			if(bottomOfStack || !verticalStretch)
			{
				off.setOffsetV(2, -3);
				off.setOffsetV(3, -3);
			}
			*/
			drh.setRenderBounds(renderer, drh.translate(ForgeDirection.NORTH, ForgeDirection.EAST, SubBlockWall.PART_WALL_RAILING_SIMPLE_NORTH));
			drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off, false);

		}
		
		if(adjMap.get(ForgeDirection.WEST))
		{
			off = new TextureOffset();
			/*
			off.setOffsetU(2, 8);
			
			if(bottomOfStack || !verticalStretch)
			{
				off.setOffsetV(2, -3);
				off.setOffsetV(3, -3);
			}
			*/
			drh.setRenderBounds(renderer, drh.translate(ForgeDirection.NORTH, ForgeDirection.WEST, SubBlockWall.PART_WALL_RAILING_SIMPLE_NORTH));
			drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off, false);
		}

		return false;
	}
	
	private boolean renderWorldDiscreteWallRailingDouble(IBlockAccess world, int x, int y, int z, Block block, TileEntityDiscreteBlock tile, RenderBlocks renderer, int subtype) 
	{
		HashMap<ForgeDirection, Boolean> adjMap = SubBlockWall.getAdjacencyMap(world, x, y, z);
		HashMap<ForgeDirection, Boolean> conMap = ((TileEntitySidedConnector)tile).getConnectionMap();
		
		if(adjMap.size() == 0 || conMap.size() == 0) return false;
		
		boolean topBlock = SubBlockWall.shouldWallConnect(world.getBlock(x, y + 1, z)) && !SubBlockWall.isWall(world.getTileEntity(x, y + 1, z));
		
		SubBlockWall.mergeMaps(adjMap, conMap);
		int adjCount = SubBlockWall.getAdjacencyCount(adjMap);
		boolean verticalStretch = false;
		boolean bottomOfStack = !adjMap.get(ForgeDirection.DOWN);

		if(adjMap.get(ForgeDirection.DOWN))
		{
			verticalStretch = true;
		}
		
		if(((TileEntitySidedConnector)tile).getConnection((short)0))
		{
			drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_WALL_RAILING_SIMPLE_POST);
			
			if(bottomOfStack)
			{
				drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_WALL_RAILING_SIMPLE_POST_BASE);
			}
		}
		else
		{
			drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, new PointSet(px.seven + px.half, px.fifteen, px.seven + px.half, px.eight + px.half, px.sixteen, px.eight + px.half));
		}
		
		TextureOffset off;
		
		if(adjMap.get(ForgeDirection.NORTH))
		{
			off = new TextureOffset();

			drh.setRenderBounds(renderer, SubBlockWall.PART_WALL_RAILING_SIMPLE_NORTH);
			drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off, false);
			
			drh.setRenderBounds(renderer, SubBlockWall.PART_WALL_RAILING_DOUBLE_NORTH);
			drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off, false);
		}
		
		if(adjMap.get(ForgeDirection.SOUTH))
		{
			off = new TextureOffset();

			drh.setRenderBounds(renderer, drh.translate(ForgeDirection.NORTH, ForgeDirection.SOUTH, SubBlockWall.PART_WALL_RAILING_SIMPLE_NORTH));
			drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off, false);
			
			drh.setRenderBounds(renderer, drh.translate(ForgeDirection.NORTH, ForgeDirection.SOUTH, SubBlockWall.PART_WALL_RAILING_DOUBLE_NORTH));
			drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off, false);
		}
		
		if(adjMap.get(ForgeDirection.EAST))
		{
			off = new TextureOffset();

			drh.setRenderBounds(renderer, drh.translate(ForgeDirection.NORTH, ForgeDirection.EAST, SubBlockWall.PART_WALL_RAILING_SIMPLE_NORTH));
			drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off, false);
			
			drh.setRenderBounds(renderer, drh.translate(ForgeDirection.NORTH, ForgeDirection.EAST, SubBlockWall.PART_WALL_RAILING_DOUBLE_NORTH));
			drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off, false);

		}
		
		if(adjMap.get(ForgeDirection.WEST))
		{
			off = new TextureOffset();

			drh.setRenderBounds(renderer, drh.translate(ForgeDirection.NORTH, ForgeDirection.WEST, SubBlockWall.PART_WALL_RAILING_SIMPLE_NORTH));
			drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off, false);
			
			drh.setRenderBounds(renderer, drh.translate(ForgeDirection.NORTH, ForgeDirection.WEST, SubBlockWall.PART_WALL_RAILING_DOUBLE_NORTH));
			drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off, false);
		}

		return false;
	}
	
	private boolean renderWorldDiscreteWallRailingTriple(IBlockAccess world, int x, int y, int z, Block block, TileEntityDiscreteBlock tile, RenderBlocks renderer, int subtype) 
	{
		HashMap<ForgeDirection, Boolean> adjMap = SubBlockWall.getAdjacencyMap(world, x, y, z);
		HashMap<ForgeDirection, Boolean> conMap = ((TileEntitySidedConnector)tile).getConnectionMap();
		
		if(adjMap.size() == 0 || conMap.size() == 0) return false;
		
		boolean topBlock = SubBlockWall.shouldWallConnect(world.getBlock(x, y + 1, z)) && !SubBlockWall.isWall(world.getTileEntity(x, y + 1, z));
		
		SubBlockWall.mergeMaps(adjMap, conMap);
		int adjCount = SubBlockWall.getAdjacencyCount(adjMap);
		boolean verticalStretch = false;
		boolean bottomOfStack = !adjMap.get(ForgeDirection.DOWN);

		if(adjMap.get(ForgeDirection.DOWN))
		{
			verticalStretch = true;
		}
		
		if(((TileEntitySidedConnector)tile).getConnection((short)0))
		{
			drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_WALL_RAILING_SIMPLE_POST);
			
			if(bottomOfStack)
			{
				drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, SubBlockWall.PART_WALL_RAILING_SIMPLE_POST_BASE);
			}
		}
		else
		{
			drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, new PointSet(px.seven + px.half, px.fifteen, px.seven + px.half, px.eight + px.half, px.sixteen, px.eight + px.half));
		}
		
		TextureOffset off;
		
		if(adjMap.get(ForgeDirection.NORTH))
		{
			off = new TextureOffset();

			drh.setRenderBounds(renderer, SubBlockWall.PART_WALL_RAILING_SIMPLE_NORTH);
			drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off, false);
			
			drh.setRenderBounds(renderer, SubBlockWall.PART_WALL_RAILING_DOUBLE_NORTH);
			drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off, false);
			
			drh.setRenderBounds(renderer, SubBlockWall.PART_WALL_RAILING_TRIPLE_NORTH);
			drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off, false);
		}
		
		if(adjMap.get(ForgeDirection.SOUTH))
		{
			off = new TextureOffset();

			drh.setRenderBounds(renderer, drh.translate(ForgeDirection.NORTH, ForgeDirection.SOUTH, SubBlockWall.PART_WALL_RAILING_SIMPLE_NORTH));
			drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off, false);
			
			drh.setRenderBounds(renderer, drh.translate(ForgeDirection.NORTH, ForgeDirection.SOUTH, SubBlockWall.PART_WALL_RAILING_DOUBLE_NORTH));
			drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off, false);
			
			drh.setRenderBounds(renderer, drh.translate(ForgeDirection.NORTH, ForgeDirection.SOUTH, SubBlockWall.PART_WALL_RAILING_TRIPLE_NORTH));
			drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off, false);
		}
		
		if(adjMap.get(ForgeDirection.EAST))
		{
			off = new TextureOffset();

			drh.setRenderBounds(renderer, drh.translate(ForgeDirection.NORTH, ForgeDirection.EAST, SubBlockWall.PART_WALL_RAILING_SIMPLE_NORTH));
			drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off, false);
			
			drh.setRenderBounds(renderer, drh.translate(ForgeDirection.NORTH, ForgeDirection.EAST, SubBlockWall.PART_WALL_RAILING_DOUBLE_NORTH));
			drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off, false);
			
			drh.setRenderBounds(renderer, drh.translate(ForgeDirection.NORTH, ForgeDirection.EAST, SubBlockWall.PART_WALL_RAILING_TRIPLE_NORTH));
			drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off, false);

		}
		
		if(adjMap.get(ForgeDirection.WEST))
		{
			off = new TextureOffset();

			drh.setRenderBounds(renderer, drh.translate(ForgeDirection.NORTH, ForgeDirection.WEST, SubBlockWall.PART_WALL_RAILING_SIMPLE_NORTH));
			drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off, false);
			
			drh.setRenderBounds(renderer, drh.translate(ForgeDirection.NORTH, ForgeDirection.WEST, SubBlockWall.PART_WALL_RAILING_DOUBLE_NORTH));
			drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off, false);
			
			drh.setRenderBounds(renderer, drh.translate(ForgeDirection.NORTH, ForgeDirection.WEST, SubBlockWall.PART_WALL_RAILING_TRIPLE_NORTH));
			drh.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off, false);
		}

		return false;
	}
}
