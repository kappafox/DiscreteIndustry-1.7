package kappafox.di.transport.renderers;
import kappafox.di.transport.tileentities.TileEntityDiscreteHopper;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class HopperRenderer implements ISimpleBlockRenderingHandler
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
	
	private ResourceLocation simpleDanger;
	

	public HopperRenderer(int rid_)
	{
		renderID = rid_;
		
		simpleDanger = new ResourceLocation("discreteindustry","textures/blocks/hoppers/simpleDanger.png");
	}
	
	@Override
	public void renderInventoryBlock(Block block_, int meta_, int modelID_, RenderBlocks renderer_)
	{
		Tessellator tessellator = Tessellator.instance;
		
		switch(meta_)
		{
			case 0:
				block_.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
				break;
			case 1:
				block_.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.3F, 1.0F);
				break;				
			case 2:
				block_.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.1F, 1.0F);

		}
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
		renderer_.renderFaceZNeg(block_, 0.0D, 0.0D, 0.0D, block_.getIcon(2, meta_));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		renderer_.renderFaceZPos(block_, 0.0D, 0.0D, 0.0D, block_.getIcon(2, meta_));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(-1.0F, 0.0F, 0.0F);
		renderer_.renderFaceXNeg(block_, 0.0D, 0.0D, 0.0D, block_.getIcon(2, meta_));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		renderer_.renderFaceXPos(block_, 0.0D, 0.0D, 0.0D, block_.getIcon(2, meta_));
		tessellator.draw();
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		
		//block_.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		//renderer_.setRenderBoundsFromBlock(block_);
		
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world_, int x_, int y_, int z_, Block block_, int modelID_, RenderBlocks renderer_)
	{		
        //Tessellator t = Tessellator.instance;
        int meta = world_.getBlockMetadata(x_, y_, z_);
        
        int onTopMeta = world_.getBlockMetadata(x_, y_ + 1, z_);
        //Block onTop = Block.blocksList[world_.getBlockId(x_, y_ + 1, z_)];
        Block onTop = world_.getBlock(x_, y_ + 1, z_);
        TileEntity above = world_.getTileEntity(x_, y_ + 1, z_);
        boolean stack = false;
        
        
        if(above instanceof TileEntityDiscreteHopper)
        {
        	stack = true;
        }
        else
        {
        	if(onTop != null && above instanceof IInventory)
        	{
        		stack = true;
        		
            	renderer_.setRenderBounds(onePx, fifteenPx, zeroPx, fifteenPx, sixteenPx, twoPx);
            	renderer_.renderStandardBlock(block_, x_, y_, z_);
            	
            	renderer_.setRenderBounds(onePx, fifteenPx, fourteenPx, fifteenPx, sixteenPx, sixteenPx);
            	renderer_.renderStandardBlock(block_, x_, y_, z_);
            	
            	renderer_.setRenderBounds(zeroPx, fifteenPx, onePx, twoPx, sixteenPx, fifteenPx);
            	renderer_.renderStandardBlock(block_, x_, y_, z_);
            	
            	renderer_.setRenderBounds(fourteenPx, fifteenPx, onePx, sixteenPx, sixteenPx, fifteenPx);
            	renderer_.renderStandardBlock(block_, x_, y_, z_);
            	
        	}
        }
        
        //full hopper
        if(meta == 0)
        {
        	
        	renderer_.setRenderBounds(zeroPx, zeroPx, zeroPx, sixteenPx, onePx, twoPx);
        	renderer_.renderStandardBlock(block_, x_, y_, z_);
        	
        	renderer_.setRenderBounds(zeroPx, zeroPx, fourteenPx, sixteenPx, onePx, sixteenPx);
        	renderer_.renderStandardBlock(block_, x_, y_, z_);
        	
        	renderer_.setRenderBounds(zeroPx, zeroPx, twoPx, twoPx, onePx, fourteenPx);
        	renderer_.renderStandardBlock(block_, x_, y_, z_);
        	
        	renderer_.setRenderBounds(fourteenPx, zeroPx, twoPx, sixteenPx, onePx, fourteenPx);
        	renderer_.renderStandardBlock(block_, x_, y_, z_);
        	
        	
        	
        	//top plate
        	
        	
        	//the four edges of the top plate
        	renderer_.setRenderBounds(fifteenPx, sixPx, onePx, onePx, eightPx, zeroPx);
        	renderer_.renderStandardBlock(block_, x_, y_, z_);
        	
        	renderer_.setRenderBounds(onePx, sixPx, fifteenPx, fifteenPx, eightPx, sixteenPx);
        	renderer_.renderStandardBlock(block_, x_, y_, z_);
        	
        	renderer_.setRenderBounds(zeroPx, sixPx, onePx, onePx, eightPx, fifteenPx);
        	renderer_.renderStandardBlock(block_, x_, y_, z_);
        	
        	renderer_.setRenderBounds(fifteenPx, sixPx, onePx, sixteenPx, eightPx, fifteenPx);
        	renderer_.renderStandardBlock(block_, x_, y_, z_);
        	
        	//the core parts of the top plate
        	renderer_.setRenderBounds(onePx, sixPx, onePx, fifteenPx, eightPx, fourPx);
        	renderer_.renderStandardBlock(block_, x_, y_, z_);        	
        	
        	renderer_.setRenderBounds(onePx, sixPx, twelvePx, fifteenPx, eightPx, fifteenPx);
        	renderer_.renderStandardBlock(block_, x_, y_, z_);
        	
        	renderer_.setRenderBounds(onePx, sixPx, fourPx, fourPx, eightPx, twelvePx);
        	renderer_.renderStandardBlock(block_, x_, y_, z_);
        	
        	renderer_.setRenderBounds(twelvePx, sixPx, fourPx, fifteenPx, eightPx, twelvePx);
        	renderer_.renderStandardBlock(block_, x_, y_, z_);
        	
        	//Columns       	
        	this.renderHopperColumns(renderer_, block_, x_, y_, z_, eightPx, stack);
        	
        	//chutes
        	renderer_.setRenderBounds(twoPx, fivePx, twoPx, fourteenPx, sevenPx, fourteenPx);
        	renderer_.renderStandardBlock(block_, x_, y_, z_);
        	
        	renderer_.setRenderBounds(threePx, threePx, threePx, thirteenPx, fivePx, thirteenPx);
        	renderer_.renderStandardBlock(block_, x_, y_, z_);
        	
        	renderer_.setRenderBounds(fourPx, onePx, fourPx, twelvePx, threePx, twelvePx);
        	renderer_.renderStandardBlock(block_, x_, y_, z_);
        	
        	renderer_.setRenderBounds(fivePx, zeroPx, fivePx, elevenPx, onePx, elevenPx);
        	renderer_.renderStandardBlock(block_, x_, y_, z_);
        	
        	if(stack == true)
        	{     		
        		this.renderHopperStackChute(renderer_, block_, x_, y_, z_);
        	}
        }
        
        //half hopper
        if(meta == 1)
        {
        	
        	//baseplate
        	renderer_.setRenderBounds(zeroPx, zeroPx, zeroPx, sixteenPx, onePx, twoPx);
        	renderer_.renderStandardBlock(block_, x_, y_, z_);
        	
        	renderer_.setRenderBounds(zeroPx, zeroPx, fourteenPx, sixteenPx, onePx, sixteenPx);
        	renderer_.renderStandardBlock(block_, x_, y_, z_);
        	
        	renderer_.setRenderBounds(zeroPx, zeroPx, twoPx, twoPx, onePx, fourteenPx);
        	renderer_.renderStandardBlock(block_, x_, y_, z_);
        	
        	renderer_.setRenderBounds(fourteenPx, zeroPx, twoPx, sixteenPx, onePx, fourteenPx);
        	renderer_.renderStandardBlock(block_, x_, y_, z_);
        	
        	//the four edges of the top plate
        	renderer_.setRenderBounds(fifteenPx, threePx, onePx, onePx, fivePx, zeroPx);
        	renderer_.renderStandardBlock(block_, x_, y_, z_);
        	
        	renderer_.setRenderBounds(onePx, threePx, fifteenPx, fifteenPx, fivePx, sixteenPx);
        	renderer_.renderStandardBlock(block_, x_, y_, z_);
        	
        	renderer_.setRenderBounds(zeroPx, threePx, onePx, onePx, fivePx, fifteenPx);
        	renderer_.renderStandardBlock(block_, x_, y_, z_);
        	
        	renderer_.setRenderBounds(fifteenPx, threePx, onePx, sixteenPx, fivePx, fifteenPx);
        	renderer_.renderStandardBlock(block_, x_, y_, z_);
        	
        	//the core parts of the top plate
        	renderer_.setRenderBounds(onePx, threePx, onePx, fifteenPx, fivePx, fourPx);
        	renderer_.renderStandardBlock(block_, x_, y_, z_);        	
        	
        	renderer_.setRenderBounds(onePx, threePx, twelvePx, fifteenPx, fivePx, fifteenPx);
        	renderer_.renderStandardBlock(block_, x_, y_, z_);
        	
        	renderer_.setRenderBounds(onePx, threePx, fourPx, fourPx, fivePx, twelvePx);
        	renderer_.renderStandardBlock(block_, x_, y_, z_);
        	
        	renderer_.setRenderBounds(twelvePx, threePx, fourPx, fifteenPx, fivePx, twelvePx);
        	renderer_.renderStandardBlock(block_, x_, y_, z_);
        	
        	//Columns
        	this.renderHopperColumns(renderer_, block_, x_, y_, z_, fivePx, stack);
        	
        	renderer_.setRenderBounds(zeroPx, onePx, zeroPx, onePx, fivePx, onePx);
        	renderer_.renderStandardBlock(block_, x_, y_, z_);
        	
        	renderer_.setRenderBounds(fifteenPx, onePx, zeroPx, sixteenPx, fivePx, onePx);
        	renderer_.renderStandardBlock(block_, x_, y_, z_);
        	
        	renderer_.setRenderBounds(fifteenPx, onePx, fifteenPx, sixteenPx, fivePx, sixteenPx);
        	renderer_.renderStandardBlock(block_, x_, y_, z_);
        	
        	renderer_.setRenderBounds(zeroPx, onePx, fifteenPx, onePx, fivePx, sixteenPx);
        	renderer_.renderStandardBlock(block_, x_, y_, z_);
        	
        	
        	//Chutes
        	renderer_.setRenderBounds(twoPx, onePx, twoPx, fourteenPx, threePx, fourteenPx);
        	renderer_.renderStandardBlock(block_, x_, y_, z_);
        	
        	renderer_.setRenderBounds(fivePx, zeroPx, fivePx, elevenPx, onePx, elevenPx);
        	renderer_.renderStandardBlock(block_, x_, y_, z_);
        	
        	if(stack == true)
        	{     		
        		this.renderHopperStackChute(renderer_, block_, x_, y_, z_);
        	}
        }
        
        //quarter hopper
        if(meta == 2)
        {
        	//base plate
        	renderer_.setRenderBounds(zeroPx, zeroPx, zeroPx, sixteenPx, onePx, sixteenPx);
        	renderer_.renderStandardBlock(block_, x_, y_, z_);
        	
        	//raised centre
        	
        	renderer_.setRenderBounds(threePx, onePx, threePx, thirteenPx, twoPx, fourPx);
        	renderer_.renderStandardBlock(block_, x_, y_, z_);
        	
        	renderer_.setRenderBounds(threePx, onePx, twelvePx, thirteenPx, twoPx, thirteenPx);
        	renderer_.renderStandardBlock(block_, x_, y_, z_);
        	
        	renderer_.setRenderBounds(threePx, onePx, threePx, fourPx, twoPx, thirteenPx);
        	renderer_.renderStandardBlock(block_, x_, y_, z_);
        	
        	renderer_.setRenderBounds(twelvePx, onePx, threePx, thirteenPx, twoPx, thirteenPx);
        	renderer_.renderStandardBlock(block_, x_, y_, z_);
        	
        	renderer_.setRenderBounds(fivePx, zeroPx, fivePx, elevenPx, onePx, elevenPx);
        	renderer_.renderStandardBlock(block_, x_, y_, z_);
        	
        	this.renderHopperColumns(renderer_, block_, x_, y_, z_, twoPx, stack);
        	
        	if(stack == true)
        	{     		
        		this.renderHopperStackChute(renderer_, block_, x_, y_, z_);
        	}
        }
        
        //bind the new texture
        
        //GL11.glBindTexture(GL11.GL_TEXTURE_2D, Minecraft.getMinecraft().renderEngine.getTexture("/mods/DiscreteIndustry/textures/blocks/ironPlugSocket.png"));
       
        /*
        //CORE Block
        renderer_.setRenderBounds(fourPx, fourPx, fourPx, twelvePx, twelvePx, twelvePx);
        renderer_.renderStandardBlock(block_, x_, y_, z_);
        

        renderer_.setRenderBounds(fourPx, fourPx, twelvePx, twelvePx, twelvePx, sixteenPx);
        renderer_.renderStandardBlock(block_, x_, y_, z_);
		*/

  
        
        //GL11.glBindTexture(GL11.GL_TEXTURE_2D, Minecraft.getMinecraft().renderEngine.getTexture("/terrain.png"));
        
        this.bindTexture(TextureMap.locationBlocksTexture);
        return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId)
	{
		return true;
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
    
    
    private void renderHopperColumns(RenderBlocks renderer_, Block block_, int x_, int y_, int z_, double height_, boolean stack_)
    {
    	
    	if(stack_ == true)
    	{
    		height_ = sixteenPx;
    	}
    	
    	renderer_.setRenderBounds(zeroPx, onePx, zeroPx, onePx, height_, onePx);
    	renderer_.renderStandardBlock(block_, x_, y_, z_);
    	
    	renderer_.setRenderBounds(fifteenPx, onePx, zeroPx, sixteenPx, height_, onePx);
    	renderer_.renderStandardBlock(block_, x_, y_, z_);
    	
    	renderer_.setRenderBounds(fifteenPx, onePx, fifteenPx, sixteenPx, height_, sixteenPx);
    	renderer_.renderStandardBlock(block_, x_, y_, z_);
    	
    	renderer_.setRenderBounds(zeroPx, onePx, fifteenPx, onePx, height_, sixteenPx);
    	renderer_.renderStandardBlock(block_, x_, y_, z_);
    	
    }
    
    private void renderHopperStackChute(RenderBlocks renderer_, Block block_, int x_, int y_, int z_)
    {
		//stack chute middle
		renderer_.setRenderBounds(sixPx, onePx, sixPx, tenPx, sixteenPx, tenPx);
		renderer_.renderStandardBlock(block_, x_, y_, z_);
		
		//the four columns around it
		renderer_.setRenderBounds(fivePx, onePx, fivePx, sixPx, sixteenPx, sixPx);
    	renderer_.renderStandardBlock(block_, x_, y_, z_);
    	
		renderer_.setRenderBounds(tenPx, onePx, tenPx, elevenPx, sixteenPx, elevenPx);
    	renderer_.renderStandardBlock(block_, x_, y_, z_);
    	
		renderer_.setRenderBounds(fivePx, onePx, tenPx, sixPx, sixteenPx, elevenPx);
    	renderer_.renderStandardBlock(block_, x_, y_, z_);
    	
		renderer_.setRenderBounds(tenPx, onePx, fivePx, elevenPx, sixteenPx, sixPx);
    	renderer_.renderStandardBlock(block_, x_, y_, z_);
    }

}
