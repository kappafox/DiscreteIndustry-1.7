package kappafox.di.transport.renderers;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import kappafox.di.base.DiscreteRenderHelper;
import kappafox.di.base.SubBlockRenderingHandler;
import kappafox.di.base.blocks.BaseBlockDiscreteSubtype;
import kappafox.di.base.tileentities.TileEntitySubtype;
import kappafox.di.base.util.PointSet;
import kappafox.di.transport.blocks.BlockDiscreteTransport;
import kappafox.di.transport.tileentities.TileEntityStorageRack;

public class SubRendererStorageRack extends SubBlockRenderingHandler
{
	private static final PointSet PART_STORAGERACK_BACK_BOTTOM = new PointSet(px.one, px.one, px.zero, px.fifteen, px.two, px.one);
	private static final PointSet PART_STORAGERACK_BACK_TOP = new PointSet(px.one, px.fourteen, px.zero, px.fifteen, px.fifteen, px.one);
	private static final PointSet PART_STORAGERACK_LEFT = new PointSet(px.one - 0.001F, px.two, px.one, px.one, px.fourteen, px.fifteen);
	private static final PointSet PART_STORAGERACK_RIGHT = new PointSet(px.fifteen, px.two, px.one, px.fifteen + 0.001F, px.fourteen, px.fifteen);
	private static final PointSet PART_STORAGERACK_TOP = new PointSet(px.one, px.fifteen, px.one, px.fifteen, px.fifteen, px.fifteen );
	private static final PointSet PART_STORAGERACK_BOTTOM = new PointSet(px.one, px.one, px.one, px.fifteen, px.one, px.fifteen );
	
	private static final PointSet PART_STORAGERACK_TOP_PLATE = new PointSet(px.zero, px.fifteen, px.zero, px.sixteen, px.sixteen, px.sixteen);
	private static final PointSet PART_STORAGERACK_TOP_PLATE_SHORT = new PointSet(px.zero, px.fifteen, px.zero, px.sixteen, px.sixteen, px.fifteen);
	private static final PointSet PART_STORAGERACK_BOTTOM_PLATE = new PointSet(px.zero, px.zero, px.zero, px.sixteen, px.one, px.sixteen);
	private static final PointSet PART_STORAGERACK_BOTTOM_PLATE_SHORT = new PointSet(px.zero, px.zero, px.zero, px.sixteen, px.one, px.fifteen);
	private static final PointSet PART_STORAGERACK_NW_COLUMN = new PointSet(px.zero, px.one, px.zero, px.one, px.fifteen, px.one);
	private static final PointSet PART_STORAGERACK_NE_COLUMN = new PointSet(px.fifteen, px.one, px.zero, px.sixteen, px.fifteen, px.one);
	private static final PointSet PART_STORAGERACK_SE_COLUMN = new PointSet(px.fifteen, px.one, px.fifteen, px.sixteen, px.fifteen, px.sixteen);
	private static final PointSet PART_STORAGERACK_SW_COLUMN = new PointSet(px.zero, px.one, px.fifteen, px.one, px.fifteen, px.sixteen);
	
	private static final PointSet PART_STORAGERACK_FRONTBAR_BOTTOM = new PointSet(px.one, px.zero, px.fifteen, px.fifteen, px.one, px.sixteen);
	private static final PointSet PART_STORAGERACK_FRONTBAR_TOP = new PointSet(px.one, px.fifteen, px.fifteen, px.fifteen, px.sixteen, px.sixteen);
	
	private static final PointSet PART_STORAGERACK_LEFT_BOTTOM_COLUMN = new PointSet(px.zero, px.one, px.one, px.one, px.two, px.fifteen);
	private static final PointSet PART_STORAGERACK_LEFT_TOP_COLUMN = new PointSet(px.zero, px.fourteen, px.one, px.one, px.fifteen, px.fifteen);
	private static final PointSet PART_STORAGERACK_RIGHT_BOTTOM_COLUMN = new PointSet(px.fifteen, px.one, px.one, px.sixteen, px.two, px.fifteen);
	private static final PointSet PART_STORAGERACK_RIGHT_TOP_COLUMN = new PointSet(px.fifteen, px.fourteen, px.one, px.sixteen, px.fifteen, px.fifteen);
	
	private static final PointSet PART_STORAGERACK_BOX_SINGLE_FRONT = new PointSet(px.one, px.one, px.fourteen, px.fifteen, px.fifteen, px.fourteen);
	private static final PointSet PART_STORAGERACK_BOX_SINGLE_BACK = new PointSet(px.one, px.one, px.one, px.fifteen, px.fifteen, px.one);
	//private static final PointSet PART_STORAGERACK_BOX_SINGLE_HANDLE_BAR = new PointSet(px.one, px.one, px.one, px.fifteen, px.fifteen, px.fixteen);
	
	private static final PointSet PART_STORAGERACK_BOX_SINGLE_LEFTHANDLE = new PointSet(px.two, px.five, px.fifteen, px.three, px.eleven, px.sixteen);
	private static final PointSet PART_STORAGERACK_BOX_SINGLE_LEFTHANDLE_PEGTOP = new PointSet(px.two, px.ten, px.fourteen, px.three, px.eleven, px.fifteen);
	private static final PointSet PART_STORAGERACK_BOX_SINGLE_LEFTHANDLE_PEGBOTTOM = new PointSet(px.two, px.five, px.fourteen, px.three, px.six, px.fifteen);
	
	private static final PointSet PART_STORAGERACK_BOX_SINGLE_RIGHTHANDLE = new PointSet(px.thirteen, px.five, px.fifteen, px.fourteen, px.eleven, px.sixteen);
	private static final PointSet PART_STORAGERACK_BOX_SINGLE_RIGHTHANDLE_PEFTOP = new PointSet(px.thirteen, px.ten, px.fourteen, px.fourteen, px.eleven, px.fifteen);
	private static final PointSet PART_STORAGERACK_BOX_SINGLE_RIGHTHANDLE_PEGBOTTOM = new PointSet(px.thirteen, px.five, px.fourteen, px.fourteen, px.six, px.fifteen);
	
	
	private static final PointSet PART_STORAGERACK_DUAL_CROSSBAR1 = new PointSet(px.one, px.seven + px.half, px.one, px.fifteen, px.eight + px.half, px.fifteen);
	private static final PointSet PART_STORAGERACK_DUAL_BOX_TOP_FRONT = new PointSet(px.one, px.eight + px.half, px.fourteen, px.fifteen, px.fifteen, px.fourteen);
	private static final PointSet PART_STORAGERACK_DUAL_BOX_BOTTOM_FRONT = new PointSet(px.one, px.one, px.fourteen, px.fifteen, px.seven + px.half, px.fourteen);	
	private static final PointSet PART_STORAGERACK_DUAL_BOX_TOP_BACK = new PointSet(px.one, px.eight + px.half, px.one, px.fifteen, px.fifteen, px.one);
	private static final PointSet PART_STORAGERACK_DUAL_BOX_BOTTOM_BACK = new PointSet(px.one, px.one, px.one, px.fifteen, px.seven + px.half, px.one);
	
	private static final PointSet PART_STORAGERACK_QUAD_CROSSBAR1 = new PointSet(px.seven + px.half, px.one, px.one + 0.001F, px.eight + px.half, px.fifteen, px.fifteen);
	private static final PointSet PART_STORAGERACK_QUAD_BOX_TOPLEFT_FRONT = new PointSet(px.one, px.eight + px.half, px.fourteen, px.eight + px.half, px.fifteen, px.fourteen);
	private static final PointSet PART_STORAGERACK_QUAD_BOX_TOPLEFT_BACK = new PointSet(px.one, px.eight + px.half, px.one, px.eight + px.half, px.fifteen, px.one);
	private static final PointSet PART_STORAGERACK_QUAD_BOX_TOPRIGHT_FRONT = new PointSet(px.eight + px.half, px.eight + px.half, px.fourteen, px.fifteen, px.fifteen, px.fourteen);
	private static final PointSet PART_STORAGERACK_QUAD_BOX_TOPRIGHT_BACK = new PointSet(px.eight + px.half, px.eight + px.half, px.one, px.fifteen, px.fifteen, px.one);
	private static final PointSet PART_STORAGERACK_QUAD_BOX_BOTTOMLEFT_FRONT = new PointSet(px.one, px.one, px.fourteen, px.eight + px.half, px.eight + px.half, px.fourteen);	
	private static final PointSet PART_STORAGERACK_QUAD_BOX_BOTTOMLEFT_BACK = new PointSet(px.one, px.one, px.one, px.eight + px.half, px.eight + px.half, px.one);	
	private static final PointSet PART_STORAGERACK_QUAD_BOX_BOTTOMRIGHT_FRONT = new PointSet(px.eight + px.half, px.one, px.fourteen, px.fifteen, px.eight + px.half, px.fourteen);
	private static final PointSet PART_STORAGERACK_QUAD_BOX_BOTTOMRIGHT_BACK = new PointSet(px.eight + px.half, px.one, px.one, px.fifteen, px.eight + px.half, px.one);
	
	private static final PointSet PART_STORAGERACK_STICKY_CENTER = new PointSet(px.seven + px.half, px.seven + px.half, px.fifteen, px.eight + px.half, px.eight + px.half, px.fifteen + 0.005F);
	private static final PointSet PART_STORAGERACK_STICKY_TOPLEFT = new PointSet(px.one, px.fourteen, px.fourteen, px.two, px.fifteen, px.fourteen + 0.005F);
	private static final PointSet PART_STORAGERACK_STICKY_TOPRIGHT = new PointSet(px.fourteen, px.fourteen, px.fourteen, px.fifteen, px.fifteen, px.fourteen + 0.005F);
	private static final PointSet PART_STORAGERACK_STICKY_BOTTOMLEFT = new PointSet(px.one, px.one, px.fourteen, px.two, px.two, px.fourteen + 0.005F);
	private static final PointSet PART_STORAGERACK_STICKY_BOTTOMRIGHT = new PointSet(px.fourteen, px.one, px.fourteen, px.fifteen, px.two, px.fourteen + 0.005F);
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelID, RenderBlocks renderer)
	{
		
		TileEntitySubtype tile = (TileEntitySubtype)world.getTileEntity(x, y, z);
		
		if(tile != null)
		{
			int type = tile.getSubtype();
			switch(type)
			{
				case BlockDiscreteTransport.ID_STORAGERACK_SINGLE:
				{
					return this.renderWorldStorageRackSingle(world, x, y, z, block, tile, renderer, type);
				}
				
				case BlockDiscreteTransport.ID_STORAGERACK_DUAL:
				{
					return this.renderWorldStorageRackDual(world, x, y, z, block, tile, renderer, type);
				}
				
				case BlockDiscreteTransport.ID_STORAGERACK_QUAD:
				{
					return this.renderWorldStorageRackQuad(world, x, y, z, block, tile, renderer, type);
				}
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
			case BlockDiscreteTransport.ID_STORAGERACK_SINGLE:
			{
				this.renderInventoryStorageRackSingle(block, subtype, modelID, renderer);
				break;
			}
			
			case BlockDiscreteTransport.ID_STORAGERACK_DUAL:
			{
				this.renderInventoryStorageRackDual(block, subtype, modelID, renderer);
				break;
			}
			
			case BlockDiscreteTransport.ID_STORAGERACK_QUAD:
			{
				this.renderInventoryStorageRackQuad(block, subtype, modelID, renderer);
				break;
			}
		}
		
		drh.resetGL11Scale();
		tessellator.draw();
	}
	
	private void renderInventoryStorageRackSingle(Block block, int subtype, int modelID, RenderBlocks renderer)
	{
		//drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, PART_STORAGERACK_BACK);
		BlockDiscreteTransport b = (BlockDiscreteTransport)block;
		
		renderer.setOverrideBlockTexture(b.getIcon(3, 0));
		drh.tessellateInventoryBlock(renderer, block, subtype, PART_STORAGERACK_LEFT);
		drh.tessellateInventoryBlock(renderer, block, subtype, PART_STORAGERACK_RIGHT);
		drh.tessellateInventoryBlock(renderer, block, subtype, PART_STORAGERACK_TOP);
		drh.tessellateInventoryBlock(renderer, block, subtype, PART_STORAGERACK_BOTTOM);
		
		drh.tessellateInventoryBlock(renderer, block, subtype, PART_STORAGERACK_TOP_PLATE);
		drh.tessellateInventoryBlock(renderer, block, subtype, PART_STORAGERACK_BOTTOM_PLATE);


		
		renderer.setOverrideBlockTexture(b.getSpecialIcon(0,0));
		
		drh.tessellateInventoryBlock(renderer, block, subtype, PART_STORAGERACK_NW_COLUMN);
		drh.tessellateInventoryBlock(renderer, block, subtype, PART_STORAGERACK_NE_COLUMN);
		drh.tessellateInventoryBlock(renderer, block, subtype, PART_STORAGERACK_SE_COLUMN);
		drh.tessellateInventoryBlock(renderer, block, subtype, PART_STORAGERACK_SW_COLUMN);
		
		drh.tessellateInventoryBlock(renderer, block, subtype, PART_STORAGERACK_BACK_BOTTOM);
		drh.tessellateInventoryBlock(renderer, block, subtype, PART_STORAGERACK_BACK_TOP);
		
		drh.tessellateInventoryBlock(renderer, block, subtype, PART_STORAGERACK_LEFT_BOTTOM_COLUMN);
		drh.tessellateInventoryBlock(renderer, block, subtype, PART_STORAGERACK_LEFT_TOP_COLUMN);
		drh.tessellateInventoryBlock(renderer, block, subtype, PART_STORAGERACK_RIGHT_BOTTOM_COLUMN);
		drh.tessellateInventoryBlock(renderer, block, subtype, PART_STORAGERACK_RIGHT_TOP_COLUMN);
		
		renderer.clearOverrideBlockTexture();
	}
	
	private void renderInventoryStorageRackDual(Block block, int subtype, int modelID, RenderBlocks renderer)
	{
		//drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, PART_STORAGERACK_BACK);
		this.renderInventoryStorageRackSingle(block, subtype, modelID, renderer);
		
		BlockDiscreteTransport b = (BlockDiscreteTransport)block;
		
		renderer.setOverrideBlockTexture(b.getSpecialIcon(0,0));
		drh.tessellateInventoryBlock(renderer, block, subtype, PART_STORAGERACK_DUAL_CROSSBAR1);
		
		renderer.clearOverrideBlockTexture();
	}
	
	private void renderInventoryStorageRackQuad(Block block, int subtype, int modelID, RenderBlocks renderer)
	{
		//drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, PART_STORAGERACK_BACK);
		this.renderInventoryStorageRackSingle(block, subtype, modelID, renderer);
		
		BlockDiscreteTransport b = (BlockDiscreteTransport)block;
		
		renderer.setOverrideBlockTexture(b.getSpecialIcon(0,0));
		drh.tessellateInventoryBlock(renderer, block, subtype, PART_STORAGERACK_DUAL_CROSSBAR1);
		drh.tessellateInventoryBlock(renderer, block, subtype, PART_STORAGERACK_QUAD_CROSSBAR1);
		
		renderer.clearOverrideBlockTexture();
	}
	
	private void renderStorageRackShell(IBlockAccess world, int x, int y, int z, Block block, RenderBlocks renderer)
	{
		TileEntityStorageRack t = (TileEntityStorageRack)world.getTileEntity(x, y, z);
		
		ForgeDirection N = ForgeDirection.NORTH;
		ForgeDirection T = ForgeDirection.getOrientation(t.getDirection());
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, drh.translate(N, T, PART_STORAGERACK_LEFT));
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, drh.translate(N, T, PART_STORAGERACK_RIGHT));
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, drh.translate(N, T, PART_STORAGERACK_TOP));
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, drh.translate(N, T, PART_STORAGERACK_BOTTOM));
		
		TileEntity below = world.getTileEntity(x, y - 1, z);
		TileEntity above = world.getTileEntity(x, y + 1, z);
		BlockDiscreteTransport b = (BlockDiscreteTransport)block;
		
		if(below instanceof TileEntityStorageRack)
		{
			drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, drh.translate(N, T, PART_STORAGERACK_BOTTOM_PLATE_SHORT));
			
			renderer.setOverrideBlockTexture(b.getSpecialIcon(0,0));
			drh.renderDiscreteQuad(world, renderer, block, x, y, z, new PointSet(px.zero, px.zero, px.fifteen, px.one, px.one, px.sixteen), T);
			drh.renderDiscreteQuad(world, renderer, block, x, y, z, new PointSet(px.fifteen, px.zero, px.fifteen, px.sixteen, px.one, px.sixteen), T);
			drh.renderDiscreteQuad(world, renderer, block, x, y, z, PART_STORAGERACK_FRONTBAR_BOTTOM, T);
			renderer.clearOverrideBlockTexture();
			
		}
		else
		{
			drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, drh.translate(N, T, PART_STORAGERACK_BOTTOM_PLATE));
		}
		
		if(above instanceof TileEntityStorageRack)
		{
			drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, drh.translate(N, T, PART_STORAGERACK_TOP_PLATE_SHORT));
			
			renderer.setOverrideBlockTexture(b.getSpecialIcon(0,0));
			drh.renderDiscreteQuad(world, renderer, block, x, y, z, new PointSet(px.zero, px.fifteen, px.fifteen, px.one, px.sixteen, px.sixteen), T);
			drh.renderDiscreteQuad(world, renderer, block, x, y, z, new PointSet(px.fifteen, px.fifteen, px.fifteen, px.sixteen, px.sixteen, px.sixteen), T);
			drh.renderDiscreteQuad(world, renderer, block, x, y, z, PART_STORAGERACK_FRONTBAR_TOP, T);
			
			renderer.clearOverrideBlockTexture();
			
		}
		else
		{
			drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, drh.translate(N, T, PART_STORAGERACK_TOP_PLATE));
		}
		


		
		renderer.setOverrideBlockTexture(b.getSpecialIcon(0,0));
		drh.setRenderBounds(renderer, drh.translate(N, T, PART_STORAGERACK_NW_COLUMN));
		renderer.renderStandardBlockWithColorMultiplier(block, x, y, z, 1, 1, 1);
		drh.setRenderBounds(renderer, drh.translate(N, T, PART_STORAGERACK_NE_COLUMN));
		renderer.renderStandardBlockWithColorMultiplier(block, x, y, z, 1, 1, 1);
		drh.setRenderBounds(renderer, drh.translate(N, T, PART_STORAGERACK_SE_COLUMN));
		renderer.renderStandardBlockWithColorMultiplier(block, x, y, z, 1, 1, 1);
		drh.setRenderBounds(renderer, drh.translate(N, T, PART_STORAGERACK_SW_COLUMN));
		renderer.renderStandardBlockWithColorMultiplier(block, x, y, z, 1, 1, 1);
		
		drh.setRenderBounds(renderer, drh.translate(N, T, PART_STORAGERACK_BACK_BOTTOM));
		renderer.renderStandardBlockWithColorMultiplier(block, x, y, z, 1, 1, 1);
		
		drh.setRenderBounds(renderer, drh.translate(N, T, PART_STORAGERACK_BACK_TOP));
		renderer.renderStandardBlockWithColorMultiplier(block, x, y, z, 1, 1, 1);

		
		drh.setRenderBounds(renderer, drh.translate(N, T, PART_STORAGERACK_LEFT_BOTTOM_COLUMN));
		renderer.renderStandardBlockWithColorMultiplier(block, x, y, z, 1, 1, 1);
		drh.setRenderBounds(renderer, drh.translate(N, T, PART_STORAGERACK_LEFT_TOP_COLUMN));
		renderer.renderStandardBlockWithColorMultiplier(block, x, y, z, 1, 1, 1);
		drh.setRenderBounds(renderer, drh.translate(N, T, PART_STORAGERACK_RIGHT_BOTTOM_COLUMN));
		renderer.renderStandardBlockWithColorMultiplier(block, x, y, z, 1, 1, 1);
		drh.setRenderBounds(renderer, drh.translate(N, T, PART_STORAGERACK_RIGHT_TOP_COLUMN));
		renderer.renderStandardBlockWithColorMultiplier(block, x, y, z, 1, 1, 1);
		
		renderer.clearOverrideBlockTexture();
		
		boolean sticky = t.getSticky();
		
		if(t.getSize() > 1)
		{
			if(sticky)
			{
				renderer.setOverrideBlockTexture(b.getSpecialIcon(1, 0));
				drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, PART_STORAGERACK_STICKY_CENTER, T);
				renderer.clearOverrideBlockTexture();
			}
		}
		else
		{
			if(sticky)
			{
				renderer.setOverrideBlockTexture(b.getSpecialIcon(1, 0));
				drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, PART_STORAGERACK_STICKY_TOPLEFT, T);
				drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, PART_STORAGERACK_STICKY_TOPRIGHT, T);
				drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, PART_STORAGERACK_STICKY_BOTTOMLEFT, T);
				drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, PART_STORAGERACK_STICKY_BOTTOMRIGHT, T);
				renderer.clearOverrideBlockTexture();
			}
		}
	}
	
	private boolean renderWorldStorageRackDual(IBlockAccess world, int x, int y, int z, Block block, TileEntitySubtype tile, RenderBlocks renderer, int type)
	{
		this.renderStorageRackShell(world, x, y, z, block, renderer);

		

		TileEntityStorageRack tesr = (TileEntityStorageRack)tile;
		
		BlockDiscreteTransport b = (BlockDiscreteTransport)block;		
		renderer.setOverrideBlockTexture(b.getSpecialIcon(0,0));
		
		ForgeDirection T = ForgeDirection.getOrientation(tesr.getDirection());
		drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, PART_STORAGERACK_DUAL_CROSSBAR1, T);
		
		renderer.clearOverrideBlockTexture();
		
		if(tesr != null)
		{
			if(tesr.hasContainer(0))
			{
				drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, PART_STORAGERACK_DUAL_BOX_TOP_FRONT, T);
				drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, PART_STORAGERACK_DUAL_BOX_TOP_BACK, T);				
			}
			
			if(tesr.hasContainer(1))
			{
				drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, PART_STORAGERACK_DUAL_BOX_BOTTOM_FRONT, T);
				drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, PART_STORAGERACK_DUAL_BOX_BOTTOM_BACK, T);
			}
		}
		
		return true;
	}
	
	
	private boolean renderWorldStorageRackSingle(IBlockAccess world, int x, int y, int z, Block block, TileEntitySubtype tile, RenderBlocks renderer, int type)
	{

		TileEntityStorageRack tesr = (TileEntityStorageRack)tile;
		
		this.renderStorageRackShell(world, x, y, z, block, renderer);

		if(tesr != null)
		{
			if(tesr.hasContainer(0))
			{
				ForgeDirection T = ForgeDirection.getOrientation(tesr.getDirection());

				BlockDiscreteTransport b = (BlockDiscreteTransport)block;
				
				renderer.setOverrideBlockTexture(b.getSpecialIcon(0,0));
				
				drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, PART_STORAGERACK_BOX_SINGLE_LEFTHANDLE, T);
				drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, PART_STORAGERACK_BOX_SINGLE_LEFTHANDLE_PEGTOP, T);
				drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, PART_STORAGERACK_BOX_SINGLE_LEFTHANDLE_PEGBOTTOM, T);
				
				drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, PART_STORAGERACK_BOX_SINGLE_RIGHTHANDLE, T);
				drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, PART_STORAGERACK_BOX_SINGLE_RIGHTHANDLE_PEFTOP, T);
				drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, PART_STORAGERACK_BOX_SINGLE_RIGHTHANDLE_PEGBOTTOM, T);
				
				renderer.clearOverrideBlockTexture();
				
				drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, PART_STORAGERACK_BOX_SINGLE_FRONT, T);
				drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, PART_STORAGERACK_BOX_SINGLE_BACK, T);
			}
		}
		
		renderer.clearOverrideBlockTexture();
		

		return true;
	}
	
	private boolean renderWorldStorageRackQuad(IBlockAccess world, int x, int y, int z, Block block, TileEntitySubtype tile, RenderBlocks renderer, int type)
	{
		this.renderStorageRackShell(world, x, y, z, block, renderer);
		TileEntityStorageRack tesr = (TileEntityStorageRack)tile;
		
		if(tesr != null)
		{
			BlockDiscreteTransport b = (BlockDiscreteTransport)block;		
			renderer.setOverrideBlockTexture(b.getSpecialIcon(0,0));
			
			ForgeDirection T = ForgeDirection.getOrientation(tesr.getDirection());
			drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, PART_STORAGERACK_DUAL_CROSSBAR1, T);
			drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, PART_STORAGERACK_QUAD_CROSSBAR1, T);
			renderer.clearOverrideBlockTexture();
			
			
			if(tesr.hasContainer(0))
			{
				drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, PART_STORAGERACK_QUAD_BOX_TOPLEFT_FRONT, T);
				drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, PART_STORAGERACK_QUAD_BOX_TOPLEFT_BACK, T);
			}
				
			if(tesr.hasContainer(1))
			{
				drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, PART_STORAGERACK_QUAD_BOX_TOPRIGHT_FRONT, T);
				drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, PART_STORAGERACK_QUAD_BOX_TOPRIGHT_BACK, T);
			}
				
			if(tesr.hasContainer(2))
			{
				drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, PART_STORAGERACK_QUAD_BOX_BOTTOMLEFT_FRONT, T);
				drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, PART_STORAGERACK_QUAD_BOX_BOTTOMLEFT_BACK, T);
			}
				
			if(tesr.hasContainer(3))
			{
				drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, PART_STORAGERACK_QUAD_BOX_BOTTOMRIGHT_FRONT, T);
				drh.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, PART_STORAGERACK_QUAD_BOX_BOTTOMRIGHT_BACK, T);
			}
		}
			
		renderer.clearOverrideBlockTexture();
		

		return true;
	}

}
