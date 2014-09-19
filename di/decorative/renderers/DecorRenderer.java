package kappafox.di.decorative.renderers;

import kappafox.di.base.DiscreteRenderHelper;
import kappafox.di.base.tileentities.TileEntityDiscreteBlock;
import kappafox.di.base.util.PixelSet;
import kappafox.di.decorative.tileentities.TileEntityLoomBlock;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;



@Deprecated
public class DecorRenderer implements ISimpleBlockRenderingHandler
{
	
	private static final PixelSet px = PixelSet.getInstance();	
	private static final DiscreteRenderHelper drh = new DiscreteRenderHelper();
	
	private int renderID;
	
	//included purely as a legacy helper while converting to the new px method, Always use px.<num> from now on!
	private static final double zeroPx = px.dzero;
	private static final double onePx = px.done;
	private static final double twoPx = px.dtwo;
	private static final double threePx = px.dthree;
	private static final double fourPx = px.dfour;
	private static final double fivePx = px.dfive;
	private static final double sixPx = px.dsix;
	private static final double sevenPx = px.dseven;
	private static final double eightPx = px.deight;
	private static final double ninePx = px.dnine;
	private static final double tenPx = px.dten;
	private static final double elevenPx = px.deleven;
	private static final double twelvePx = px.dtwelve;
	private static final double thirteenPx = px.dthirteen;
	private static final double fourteenPx = px.dfourteen;
	private static final double fifteenPx = px.dfifteen;
	private static final double sixteenPx = px.dsixteen;

	
	@SideOnly(Side.CLIENT)
	private static Tessellator tessellator;
	
	@SideOnly(Side.CLIENT)
	private static ResourceLocation flagSheet;
	
	@SideOnly(Side.CLIENT)
	private static TextureManager textureManager;
	
	
	public DecorRenderer(int rid_)
	{
		renderID = rid_;
		
		if(FMLCommonHandler.instance().getEffectiveSide().isClient() == true)
		{
			tessellator = Tessellator.instance;
			flagSheet = new ResourceLocation("discreteindustry", "textures/blocks/flagPart.png");
			
			textureManager = Minecraft.getMinecraft().getTextureManager();
		}
	}

	
	@Override
	@SideOnly(Side.CLIENT)
	public void renderInventoryBlock(Block block_, int meta_, int modelID_, RenderBlocks renderer_)
	{

	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean renderWorldBlock(IBlockAccess world_, int x_, int y_, int z_, Block block_, int modelID_, RenderBlocks renderer_)
	{		
		
		int meta = world_.getBlockMetadata(x_, y_, z_);
		TileEntity t = world_.getTileEntity(x_, y_, z_);
		
		
		if(t instanceof TileEntityLoomBlock)
		{
			return renderWorldLoomBlock(world_, x_, y_, z_,  block_,  modelID_,  renderer_, meta);
		}
		
		if(t instanceof TileEntityDiscreteBlock)
		{
			TileEntityDiscreteBlock tile = (TileEntityDiscreteBlock)t;
			int type = tile.getSubtype();
			
			switch(meta)
			{
				case 2:
				{
					if(tile != null)
					{
				
						if(type == 888)
						{
							return renderWorldTestBlock2(world_, x_, y_, z_,  block_,  modelID_,  renderer_, meta);
						}
					}
				}
				
				case 5:
				{

				}
				
				case 6:
				{
					//return renderWorldFixtureBlock(world_, x_, y_, z_,  block_,  modelID_,  renderer_, meta);
				}
			}
		}
		

        return true;
	}

	private boolean renderWorldLoomBlock(IBlockAccess world_, int x_, int y_, int z_, Block block_, int modelID_, RenderBlocks renderer_, int meta) 
	{
		renderer_.renderStandardBlock(block_, x_, y_, z_);
		return true;
	}

	private boolean renderWorldTestBlock2(IBlockAccess world_, int x_, int y_, int z_, Block block_, int modelID_, RenderBlocks renderer_, int meta) 
	{
		/*
		renderer_.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		renderer_.renderStandardBlock(block_, x_, y_, z_);
		
		if(true == true)
		{
			return true;
		}
		
		tessellator.draw();

		this.bindTexture(TextureMap.locationItemsTexture);

		Item item = Item.swordDiamond;
		ItemStack istack = new ItemStack(item);
		Icon icon = item.getIcon(istack, 0);
		
		float offset = 1.0F;
		
		GL11.glPushMatrix();
		GL11.glTranslatef(0.0F, 0.0F, 0.0F);
		GL11.glRotatef(0F, 0.0F, 1.0F, 0.0F);
		
		//tessellator.startDrawingQuads();

    	GL11.glPopMatrix();
    	renderer_.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    	
		float angle = 5.0F;
	    for(int i = 5; i < 360; i += 10)
	    {    	
			GL11.glPushMatrix();
			GL11.glRotatef(i, 0.0F, 1.0F, 0.0F);
			//GL11.glTranslatef(2.0F, 0.0F, 9.0F);
			//Tessellator
			
			//drh.renderItemIn2D(tessellator, x_, y_, z_, icon.getMinU(), icon.getMinV(), icon.getMaxU(), icon.getMaxV(), icon.getIconWidth(), icon.getIconHeight(), 0.0625F);
		    GL11.glPopMatrix();
	    }

		return true;
	}

	private boolean renderWorldTestBlock(IBlockAccess world_, int x_, int y_, int z_, Block block_, int modelID_, RenderBlocks renderer_, int meta) 
	{
		
		tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
		
		double res = Math.sin(System.currentTimeMillis());
		
		
		float r = 0.0F;
		float b = 0.0F;
		float g = 0.0F;
		
		int rows = 32;
		int cols = 60;
		
		double pixelWidth = onePx / 2.0;
		double pixelHeight = onePx / 2.0;
		
		if(block_ != null)
		{
			for(int i = 0; i <= rows; i++)
			{
				for(int j = 0; j <= cols; j++)
				{
					renderer_.setRenderBounds(j * pixelWidth, (i * pixelHeight), sevenPx, ((j + 1) * pixelWidth), ((i + 1) * pixelHeight), eightPx);
					//renderer_.setRenderBounds(j * onePx, (i * onePx), sevenPx, ((j + 1) * onePx), ((i + 1) * onePx), eightPx);
					renderer_.renderStandardBlockWithColorMultiplier(block_, x_, y_, z_, r, g, b);
					//renderer_.renderFaceZPos(block_, x_, y_, z_, block_.getIcon(0, 805));
					
					if(r == 0.0)
					{
						r = 1.0F;
						g = 1.0F;
						b = 1.0F;
					}
					else
					{
						r = 0.0F;
						g = 0.0F;
						b = 0.0F;
					}
					
				}
			}
			

		}
		*/
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
}

