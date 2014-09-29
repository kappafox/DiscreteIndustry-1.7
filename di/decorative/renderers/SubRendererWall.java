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
import kappafox.di.decorative.blocks.BlockDecor;
import kappafox.di.decorative.blocks.SubBlockWall;

public class SubRendererWall extends SubBlockRenderingHandler
{

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelID, RenderBlocks renderer)
	{
		TileEntityDiscreteBlock t = (TileEntityDiscreteBlock)world.getTileEntity(x, y, z);
		int subtype = t.getSubtype();
		
		switch(subtype)
		{
			case BlockDecor.ID_WALL_DISCRETE:
				return this.renderWorldDiscreteWallBlock(world, x, y, z, block, t, renderer, subtype);
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
		HashMap<ForgeDirection, Boolean> adjMap = this.getAdjacencyMap(world, x, y, z);
		HashMap<ForgeDirection, Boolean> conMap = ((TileEntitySidedConnector)t).getConnectionMap();
		
		if(adjMap.size() == 0 || conMap.size() == 0) return false;
		
		
		this.mergeMaps(adjMap, conMap);
		int adjCount = this.getAdjacencyCount(adjMap);
		
		//with 0, 1 or 3+ we draw the 'post'
		if(adjCount != 2)
		{
			drh.renderDiscreteQuad(world, renderer, block, x, y, z, SubBlockWall.PART_WALL_POST);
		}
		else
		{
			if(adjMap.get(ForgeDirection.NORTH) && adjMap.get(ForgeDirection.EAST))
			{
				drh.renderDiscreteQuad(world, renderer, block, x, y, z, SubBlockWall.PART_WALL_POST);
			}
			
			if(adjMap.get(ForgeDirection.NORTH) && adjMap.get(ForgeDirection.WEST))
			{
				drh.renderDiscreteQuad(world, renderer, block, x, y, z, SubBlockWall.PART_WALL_POST);
			}
			
			if(adjMap.get(ForgeDirection.SOUTH) && adjMap.get(ForgeDirection.EAST))
			{
				drh.renderDiscreteQuad(world, renderer, block, x, y, z, SubBlockWall.PART_WALL_POST);
			}
			
			if(adjMap.get(ForgeDirection.SOUTH) && adjMap.get(ForgeDirection.WEST))
			{
				drh.renderDiscreteQuad(world, renderer, block, x, y, z, SubBlockWall.PART_WALL_POST);
			}
		}
		
		
		if(adjMap.get(ForgeDirection.NORTH))
		{
			drh.renderDiscreteQuad(world, renderer, block, x, y, z, SubBlockWall.PART_WALL_NORTH);
		}
		
		if(adjMap.get(ForgeDirection.SOUTH))
		{
			drh.renderDiscreteQuad(world, renderer, block, x, y, z, SubBlockWall.PART_WALL_SOUTH);
		}
		
		if(adjMap.get(ForgeDirection.EAST))
		{
			drh.renderDiscreteQuad(world, renderer, block, x, y, z, SubBlockWall.PART_WALL_EAST);
		}
		
		if(adjMap.get(ForgeDirection.WEST))
		{
			drh.renderDiscreteQuad(world, renderer, block, x, y, z, SubBlockWall.PART_WALL_WEST);
		}

		return false;
	}
	
	private void mergeMaps(HashMap<ForgeDirection, Boolean> copyTo, HashMap<ForgeDirection, Boolean> copyFrom)
	{
		for(Entry<ForgeDirection, Boolean> entry : copyTo.entrySet())
		{
			copyTo.put(entry.getKey(), entry.getValue() && copyFrom.get(entry.getKey()));
		}
	}
	
	private int getAdjacencyCount(HashMap<ForgeDirection, Boolean> map)
	{
		int count = 0;
		for(Entry<ForgeDirection, Boolean> entry : map.entrySet())
		{
			if(entry.getValue())
			{
				count++;
			}
		}
		
		return count;
	}
	
	private HashMap<ForgeDirection, Boolean> getAdjacencyMap(IBlockAccess world, int x, int y, int z)
	{
		TileEntity north = world.getTileEntity(x, y, z - 1);
		TileEntity south = world.getTileEntity(x, y, z + 1);
		TileEntity east = world.getTileEntity(x + 1, y, z);
		TileEntity west = world.getTileEntity(x - 1, y, z);
		
		HashMap<ForgeDirection, Boolean> adj = new HashMap<ForgeDirection, Boolean>();
		
		if(this.isWall(north))
		{
			adj.put(ForgeDirection.NORTH, true);
		}
		else
		{
			adj.put(ForgeDirection.NORTH, false);			
		}
		
		if(this.isWall(south))
		{
			adj.put(ForgeDirection.SOUTH, true);
		}
		else
		{
			adj.put(ForgeDirection.SOUTH, false);			
		}
		
		if(this.isWall(east))
		{
			adj.put(ForgeDirection.EAST, true);
		}
		else
		{
			adj.put(ForgeDirection.EAST, false);			
		}
		
		if(this.isWall(west))
		{
			adj.put(ForgeDirection.WEST, true);
		}
		else
		{
			adj.put(ForgeDirection.WEST, false);			
		}
		
		adj.put(ForgeDirection.UP, false);
		adj.put(ForgeDirection.DOWN, false);

		return adj;
	}
	
	private boolean isWall(TileEntity tile)
	{
		if(tile instanceof TileEntitySidedConnector)
		{
			TileEntitySidedConnector te = (TileEntitySidedConnector)tile;
			
			if(BlockDecor.RANGE_WALL.contains(te.getSubtype()))
			{
				return true;
			}
		}
		
		return false;
	}
}
