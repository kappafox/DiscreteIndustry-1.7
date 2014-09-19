package kappafox.di.decorative.renderers;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import kappafox.di.base.SubBlockRenderingHandler;
import kappafox.di.base.tileentities.TileEntityDiscreteBlock;
import kappafox.di.base.tileentities.TileEntitySubtype;
import kappafox.di.base.util.PointSet;
import kappafox.di.decorative.blocks.BlockDecor;
import kappafox.di.decorative.tileentities.TileEntitySwordRack;

public class SubRendererWeaponRack extends SubBlockRenderingHandler
{
	private static final PointSet PART_SWORDRACK_BASE = new PointSet(px.zero, px.zero, px.four, px.sixteen, px.one, px.twelve);
	private static final PointSet PART_SWORDRACK_LEFTSTRUT = new PointSet(px.one, px.one, px.six, px.two, px.thirteen, px.ten);
	private static final PointSet PART_SWORDRACK_RIGHTSTRUT = new PointSet(px.fourteen, px.one, px.six, px.fifteen, px.thirteen, px.ten);
	private static final PointSet PART_SWORDRACK_REARBARLOW = new PointSet(px.two, px.two + px.one, px.six, px.fourteen, px.three + px.half, px.seven - px.half);
	private static final PointSet PART_SWORDRACK_REARBARHIGH = new PointSet(px.two, px.ten + px.half, px.six, px.fourteen, px.eleven + px.half, px.seven - px.half);
	private static final PointSet PART_SWORDRACK_FRONTBAR = new PointSet(px.two, px.two + px.half, px.nine + px.half, px.fourteen, px.three + px.half, px.ten);
	
	private static final PointSet PART_SWORDREST_TIER1 = new PointSet(px.two, px.zero, px.four, px.fourteen, px.two, px.twelve);
	private static final PointSet PART_SWORDREST_TIER2 = new PointSet(px.four, px.two, px.five, px.twelve, px.four, px.eleven);
	private static final PointSet PART_SWORDREST_TIER3 = new PointSet(px.six, px.four, px.six, px.ten, px.five, px.ten);
	
	
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelID, RenderBlocks renderer)
	{
		
		TileEntitySubtype tile = (TileEntitySubtype)world.getTileEntity(x, y, z);
		
		if(tile == null)
		{
			return false;
		}
		
		int subtype = tile.getSubtype();
		switch(subtype)
		{
			case BlockDecor.ID_RACK_SWORDRACK:
			{
				return this.renderWorldSwordRack(world, x, y, z, block, tile, renderer, subtype);
			}
			
			case BlockDecor.ID_RACK_SWORDREST:
			{
				return this.renderWorldSwordRest(world, x, y, z, block, tile, renderer, subtype);
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
			case BlockDecor.ID_RACK_SWORDRACK:
			{
				this.renderInventorySwordRackBlock(block, subtype, modelID, renderer);
				break;
			}
			
			case BlockDecor.ID_RACK_SWORDREST:
			{
				this.renderInventorySwordRestBlock(block, subtype, modelID, renderer);
				break;
			}
		}
		
		
		drh.resetGL11Scale();		
		tessellator.draw();	
	}
	

	
	private boolean renderWorldSwordRack(IBlockAccess world, int x, int y, int z, Block block, TileEntitySubtype tile, RenderBlocks renderer, int type)
	{
		
		TileEntitySwordRack te = (TileEntitySwordRack)world.getTileEntity(x, y, z);
		
		if(tile == null)
		{
			return false;
		}
		
		//drh.recalculateTESRLighting(tile);
		ForgeDirection fdirection = ForgeDirection.getOrientation(te.getDirection());
		
		PointSet base = null;
		PointSet leftstrut = null;
		PointSet rightstrut = null;
		PointSet rearbarlow = null;
		PointSet rearbarhigh = null;
		PointSet frontbar = null;
		
		//No need to flip or reorientate the baseplate or struts for N/S as it's the same
		if(fdirection == ForgeDirection.EAST || fdirection == ForgeDirection.WEST)
		{
			base = drh.translate(ForgeDirection.NORTH, ForgeDirection.EAST, PART_SWORDRACK_BASE);
			leftstrut = drh.translate(ForgeDirection.NORTH, ForgeDirection.EAST, PART_SWORDRACK_LEFTSTRUT);
			rightstrut = drh.translate(ForgeDirection.NORTH, ForgeDirection.EAST, PART_SWORDRACK_RIGHTSTRUT);
			rearbarlow = drh.translate(ForgeDirection.NORTH, fdirection, PART_SWORDRACK_REARBARLOW);
			rearbarhigh = drh.translate(ForgeDirection.NORTH, fdirection, PART_SWORDRACK_REARBARHIGH);
			frontbar = drh.translate(ForgeDirection.NORTH, fdirection, PART_SWORDRACK_FRONTBAR);
		}
		else
		{
			base = new PointSet(PART_SWORDRACK_BASE);
			leftstrut = new PointSet(PART_SWORDRACK_LEFTSTRUT);
			rightstrut = new PointSet(PART_SWORDRACK_RIGHTSTRUT);
			rearbarlow = new PointSet(PART_SWORDRACK_REARBARLOW);
			rearbarhigh = new PointSet(PART_SWORDRACK_REARBARHIGH);
			frontbar = new PointSet(PART_SWORDRACK_FRONTBAR);
		}
		
		//due to the unique way in which only bars flip for opposite directions, we need to change SOUTH slightly
		if(fdirection == ForgeDirection.SOUTH)
		{
			float z1 = rearbarlow.z1;
			float z2 = rearbarlow.z2;
			
			rearbarlow.z1 = frontbar.z1;
			rearbarlow.z2 = frontbar.z2;
			rearbarhigh.z1 = frontbar.z1;
			rearbarhigh.z2 = frontbar.z2;
			
			frontbar.z1 = z1;
			frontbar.z2 = z2;
			
		}
		
		//baseplate
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, base);
			
		//side struts
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, leftstrut);	
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, rightstrut);
		
		//horizontal bars
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, rearbarlow);
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, rearbarhigh);
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, frontbar);
		

		return false;
	}

	

	
	private boolean renderWorldSwordRest(IBlockAccess world, int x, int y, int z, Block block, TileEntitySubtype tile, RenderBlocks renderer, int type)
	{

		TileEntityDiscreteBlock te = (TileEntityDiscreteBlock)world.getTileEntity(x, y, z);
		
		ForgeDirection fdirection = ForgeDirection.getOrientation(te.getDirection());

		PointSet tier1 = drh.translate(ForgeDirection.NORTH, fdirection, PART_SWORDREST_TIER1);
		PointSet tier2 = drh.translate(ForgeDirection.NORTH, fdirection, PART_SWORDREST_TIER2);
		PointSet tier3 = drh.translate(ForgeDirection.NORTH, fdirection, PART_SWORDREST_TIER3);
		
		
		drh.setRenderBounds(renderer, tier1);
		drh.renderDiscreteQuad(world, renderer, block, x, y, z);
		
		drh.setRenderBounds(renderer, tier2);
		drh.renderDiscreteQuad(world, renderer, block, x, y, z);
		
		drh.setRenderBounds(renderer, tier3);
		drh.renderDiscreteQuad(world, renderer, block, x, y, z);
		
		return false;
	}
	
	private void renderInventorySwordRestBlock(Block block, int subtype, int modelID, RenderBlocks renderer)
	{
		drh.setGL11Scale(1.2);
	
		PointSet tier = new PointSet(PART_SWORDREST_TIER1);
		
		for(int i = 0; i < 3; i++)
		{
			drh.tessellateInventoryBlock(renderer, block, subtype, tier);		
	
			tier.x1 += px.two;
			tier.y1 += px.two;
			tier.z1 += px.one;
			
			tier.x2 -= px.two;
			tier.y2 += px.two;
			tier.z2 -= px.one;
			
			if(i == 1)
			{
				tier.y2 -= px.one;
			}
		}
	}
	
	
	private void renderInventorySwordRackBlock(Block block, int subtype, int modelID, RenderBlocks renderer)
	{	
		drh.setGL11Scale(1.2);
		
		//baseplate
		drh.tessellateInventoryBlock(renderer, block, subtype, PART_SWORDRACK_BASE);
		
		//side struts
		drh.tessellateInventoryBlock(renderer, block, subtype, PART_SWORDRACK_LEFTSTRUT);
		drh.tessellateInventoryBlock(renderer, block, subtype, PART_SWORDRACK_RIGHTSTRUT);
		
		//rear support bars
		drh.tessellateInventoryBlock(renderer, block, subtype, PART_SWORDRACK_REARBARLOW);
		drh.tessellateInventoryBlock(renderer, block, subtype, PART_SWORDRACK_REARBARHIGH);
		drh.tessellateInventoryBlock(renderer, block, subtype, PART_SWORDRACK_FRONTBAR);
		
	}
	
}
