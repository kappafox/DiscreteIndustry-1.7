package kappafox.di.decorative.renderers;
import kappafox.di.base.DiscreteRenderHelper;
import kappafox.di.decorative.blocks.BlockHazard;
import kappafox.di.decorative.tileentities.TileEntityStripHazardBlock;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class StripRenderer implements ISimpleBlockRenderingHandler
{
	
	private int renderID;
	private final double zeroPx = 0.0;
	private final double onePx = 0.0625;
	private final double twoPx = onePx * 2;
	private final double threePx = onePx * 3;
	private final double fourPx = onePx * 4;
	private final double fivePx = onePx * 5;
	private final double sixPx = onePx * 6;
	private final double sevenPx = onePx * 7;
	private final double eightPx = onePx * 8;
	private final double ninePx = onePx * 9;
	private final double tenPx = onePx * 10;
	private final double elevenPx = onePx * 11;
	private final double twelvePx = onePx * 12;
	private final double thirteenPx = onePx * 13;
	private final double fourteenPx = onePx * 14;
	private final double fifteenPx = onePx * 15;
	private final double sixteenPx = onePx * 16;
	
	@SideOnly(Side.CLIENT)
	private static Tessellator tessellator;
	
	@SideOnly(Side.CLIENT)
	private static final int YELLOW_STRAIGHT_OFFSET = 20;
	
	@SideOnly(Side.CLIENT)
	private static final int YELLOW_ANGLED_OFFSET = 30;
	
	public StripRenderer(int rid_)
	{
		renderID = rid_;
		
		if(FMLCommonHandler.instance().getEffectiveSide().isClient() == true)
		{
			tessellator = Tessellator.instance;
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void renderInventoryBlock(Block block_, int meta_, int modelID_, RenderBlocks renderer_)
	{
		renderer_.setRenderBoundsFromBlock(block_);
		
		//shifts the dimensions to show better in hand
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, -1.0F, 0.0F);
		renderer_.renderFaceYNeg(block_, 0.0D, 0.0D, 0.0D, block_.getIcon(0, meta_));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		renderer_.renderFaceYPos(block_, 0.0D, 0.0D, 0.0D, block_.getIcon(0, meta_));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, -1.0F);
		renderer_.renderFaceZNeg(block_, 0.0D, 0.0D, 0.0D, block_.getIcon(0, meta_));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		renderer_.renderFaceZPos(block_, 0.0D, 0.0D, 0.0D, block_.getIcon(0, meta_));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(-1.0F, 0.0F, 0.0F);
		renderer_.renderFaceXNeg(block_, 0.0D, 0.0D, 0.0D, block_.getIcon(0, meta_));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		renderer_.renderFaceXPos(block_, 0.0D, 0.0D, 0.0D, block_.getIcon(0, meta_));
		tessellator.draw();
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		
		block_.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		renderer_.setRenderBoundsFromBlock(block_);
		
		
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean renderWorldBlock(IBlockAccess world_, int x_, int y_, int z_, Block block_, int modelID_, RenderBlocks renderer_)
	{		
		
		int meta = world_.getBlockMetadata(x_, y_, z_);
		int iconOffset = this.getIconOffset(meta);
		
    	BlockHazard haz = (BlockHazard)block_;
		

		
		if(meta < 6)
		{		
			return true;
		}
		
		TileEntityStripHazardBlock tile = (TileEntityStripHazardBlock)world_.getTileEntity(x_, y_, z_);
		
		if(tile == null)
		{
			return true;
		}
		
		DiscreteRenderHelper drh = new DiscreteRenderHelper();
		drh.renderDiscreteQuad(world_, renderer_, block_, x_, y_, z_);
		
		//GL11.glDisable(GL11.GL_BLEND);
		//int stripPosition = tile.getSideStripPosition();
		
        //if it was 0, it would be on the same plane as the face of the block. This is to prevent the graphical glitch
        double zOffset = 0.0;//0.0005;
        
        //SOUTH Texture +Z 3
        if(block_.shouldSideBeRendered(world_, x_, y_, z_ + 1, 3) == true && tile.isStripHidden(3) == false)
        {       	
        	renderer_.setOverrideBlockTexture(haz.getCustomIcon(0, tile.getStripPosition(3), iconOffset));
            renderer_.setRenderBounds(zeroPx, zeroPx, sixteenPx - zOffset, sixteenPx, sixteenPx, sixteenPx + zOffset);
            renderer_.renderStandardBlock(block_, x_, y_, z_);
            
            renderer_.clearOverrideBlockTexture();
        }


        //NORTH -Z 2
        if(block_.shouldSideBeRendered(world_, x_, y_, z_ - 1, 2) == true && tile.isStripHidden(2) == false)
        {   
        	renderer_.setOverrideBlockTexture(haz.getCustomIcon(0, tile.getStripPosition(2), iconOffset));
            renderer_.setRenderBounds(zeroPx, zeroPx, zeroPx - zOffset, sixteenPx, sixteenPx, zeroPx + zOffset);
            renderer_.renderStandardBlock(block_, x_, y_, z_);
            
            renderer_.clearOverrideBlockTexture();
        }
        
        
        //EAST + X 5
        if(block_.shouldSideBeRendered(world_, x_ + 1, y_, z_, 5) == true && tile.isStripHidden(5) == false)
        { 
        	renderer_.setOverrideBlockTexture(haz.getCustomIcon(0, tile.getStripPosition(5), iconOffset));
            renderer_.setRenderBounds(sixteenPx - zOffset, zeroPx, zeroPx, sixteenPx + zOffset, sixteenPx, sixteenPx);
            renderer_.renderStandardBlock(block_, x_, y_, z_);
            
            renderer_.clearOverrideBlockTexture();
        }
        
        //WEST 4
        if(block_.shouldSideBeRendered(world_, x_ - 1, y_, z_, 4) == true && tile.isStripHidden(4) == false)
        {
        	renderer_.setOverrideBlockTexture(haz.getCustomIcon(0, tile.getStripPosition(4), iconOffset));
            renderer_.setRenderBounds(zeroPx - zOffset, zeroPx, zeroPx + zOffset, zeroPx, sixteenPx, sixteenPx);
            renderer_.renderStandardBlock(block_, x_, y_, z_);
            
            renderer_.clearOverrideBlockTexture();
        }
        
        //TOP 1
        if(block_.shouldSideBeRendered(world_, x_, y_ + 1, z_, 1) == true && tile.isStripHidden(1) == false)
        {

        	renderer_.setOverrideBlockTexture(haz.getCustomIcon(0, tile.getStripPosition(1), iconOffset));
            renderer_.setRenderBounds(zeroPx, sixteenPx  - zOffset, zeroPx, sixteenPx, sixteenPx + zOffset, sixteenPx);
            renderer_.renderStandardBlock(block_, x_, y_, z_);
            
            renderer_.clearOverrideBlockTexture();
        }
        
        //BOTTOM 0
        if(block_.shouldSideBeRendered(world_, x_, y_ - 1, z_, 0) == true && tile.isStripHidden(0) == false)
        {
        	renderer_.setOverrideBlockTexture(haz.getCustomIcon(0, tile.getStripPosition(0), iconOffset));
            renderer_.setRenderBounds(zeroPx, zeroPx - zOffset, zeroPx, sixteenPx, zeroPx + zOffset, sixteenPx);
            renderer_.renderStandardBlock(block_, x_, y_, z_);
            
            renderer_.clearOverrideBlockTexture();
        }
   
        //GL11.glEnable(GL11.GL_BLEND);
        renderer_.clearOverrideBlockTexture();
        this.bindTexture(TextureMap.locationBlocksTexture);
        return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int param_1)
	{
		return false;
	}

	@Override
	public int getRenderId( )
	{
		return renderID;
	}
	
    protected void bindTexture(ResourceLocation par1ResourceLocation)
    {
        TextureManager texturemanager = Minecraft.getMinecraft().renderEngine;

        if (texturemanager != null)
        {
            texturemanager.bindTexture(par1ResourceLocation);
        }
    }

    
    private int getIconOffset(int meta_)
    {
    	switch(meta_)
    	{
	    	case 6:
	    	{
	    		return YELLOW_STRAIGHT_OFFSET;
	    	}
	    	case 7:
	    	{
	    		return YELLOW_ANGLED_OFFSET;
	    	}
    	}
    	
    	return 0;
    }
}
