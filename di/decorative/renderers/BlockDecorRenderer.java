package kappafox.di.decorative.renderers;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import kappafox.di.base.BlockRenderingHandler;
import kappafox.di.base.SubBlockRenderingHandler;
import kappafox.di.base.tileentities.TileEntityDiscreteBlock;
import kappafox.di.base.tileentities.TileEntitySubtype;
import kappafox.di.decorative.blocks.BlockDecor;

public class BlockDecorRenderer extends BlockRenderingHandler
{
	private static SubRendererLadder SUB_RENDERER_LADDER;
	private static SubRendererWeaponRack SUB_RENDERER_WEAPON_RACK;
	private static SubRendererStrut SUB_RENDERER_STRUT;
	private static SubRendererStairs SUB_RENDERER_STAIRS;
	private static SubRendererShape SUB_RENDERER_SHAPE;
	private static SubRendererWall SUB_RENDERER_WALL;
	private static SubRendererBridge SUB_RENDERER_BRIDGE;
	
	public BlockDecorRenderer( )
	{
		 sub = new HashMap<Integer, SubBlockRenderingHandler>();
		 
		 SUB_RENDERER_LADDER = new SubRendererLadder();
		 SUB_RENDERER_WEAPON_RACK = new SubRendererWeaponRack();
		 SUB_RENDERER_STRUT = new SubRendererStrut();
		 SUB_RENDERER_STAIRS = new SubRendererStairs();
		 SUB_RENDERER_SHAPE = new SubRendererShape();
		 SUB_RENDERER_WALL = new SubRendererWall();
		 SUB_RENDERER_BRIDGE = new SubRendererBridge();
		 
		 
		 this.mapSubBlockRenderers();
	}
	
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelID, RenderBlocks renderer) 
	{
		TileEntity tile = world.getTileEntity(x, y, z);
		
		//All Decor Block are required to be based on TileEntitySubtype
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
	public void renderInventoryBlock(Block block, int subtype, int modelID, RenderBlocks renderer) 
	{
		if(sub.containsKey(subtype))
		{
			sub.get(subtype).renderInventoryBlock(block, subtype, modelID, renderer);
		}
	}
	
	private void mapSubBlockRenderers( )
	{	
		super.registerHandlerRange(BlockDecor.RANGE_LADDER, SUB_RENDERER_LADDER);
		super.registerHandlerRange(BlockDecor.RANGE_RACK, SUB_RENDERER_WEAPON_RACK);
		super.registerHandlerRange(BlockDecor.RANGE_STRUT, SUB_RENDERER_STRUT);
		super.registerHandlerRange(BlockDecor.RANGE_STAIRS, SUB_RENDERER_STAIRS);
		super.registerHandlerRange(BlockDecor.RANGE_SHAPE, SUB_RENDERER_SHAPE);
		super.registerHandlerRange(BlockDecor.RANGE_WALL, SUB_RENDERER_WALL);
		super.registerHandlerRange(BlockDecor.RANGE_BRIDGE, SUB_RENDERER_BRIDGE);
	}
}
