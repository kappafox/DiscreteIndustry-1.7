package kappafox.di.base;

import kappafox.di.base.tileentities.TileEntityDiscreteBlock;
import kappafox.di.base.tileentities.TileEntitySubtype;
import kappafox.di.base.util.AdjustableIcon;
import kappafox.di.base.util.BoundSet;
import kappafox.di.base.util.PointSet;
import kappafox.di.base.util.TextureOffset;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.opengl.GL11;

public class DiscreteRenderHelper 
{
	private static Tessellator tessellator = Tessellator.instance;
	private static TextureManager textureManager = Minecraft.getMinecraft().getTextureManager();
	
	private static final TranslationHelper translator = new TranslationHelper();	
    private static final ResourceLocation RES_ITEM_GLINT = new ResourceLocation("textures/misc/enchanteditemglint.png");
    
    private static final RenderItem renderItem = new RenderItem();
    private static final RenderBlocks renderer = new RenderBlocks();
    private static final FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
    
    
    
    //Rendering, setting and translating
    public void renderDiscreteQuadWithColourMultiplier(IBlockAccess world, RenderBlocks renderer, Block block, int x, int y, int z, PointSet bounds, ForgeDirection fdirection)
    {
    	if(bounds == null)
    	{
    		this.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z);
    	}
    	
    	this.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z, this.translate(ForgeDirection.NORTH, fdirection, bounds));
    }
    
    //Combination method for setting bounds and rendering simple
    public void renderDiscreteQuadWithColourMultiplier(IBlockAccess world, RenderBlocks renderer, Block block, int x, int y, int z, PointSet bounds)
    {
    	if(bounds != null)
    	{
    		this.setRenderBounds(renderer, bounds);
    	}
    	
    	this.renderDiscreteQuadWithColourMultiplier(world, renderer, block, x, y, z);
    }
    
	public void renderDiscreteQuadWithColourMultiplier(IBlockAccess world, RenderBlocks renderer, Block block, int x, int y, int z)
	{
		TileEntityDiscreteBlock tile = (TileEntityDiscreteBlock)world.getTileEntity(x, y, z);

		
		if(tile != null)
		{
			Block bl = tile.getTextureSourceBlock(0);
			
	        float r = 1.0F;
	        float g = 1.0F;
	        float b = 1.0F;
	        
			if(bl != null)
			{
		        int l = bl.colorMultiplier(world, x, y, z);
		        r = (float)(l >> 16 & 255) / 255.0F;
		        g = (float)(l >> 8 & 255) / 255.0F;
		        b = (float)(l & 255) / 255.0F;
		
		        if (EntityRenderer.anaglyphEnable)
		        {
		            float f3 = (r * 30.0F + g * 59.0F + b * 11.0F) / 100.0F;
		            float f4 = (r * 30.0F + g * 70.0F) / 100.0F;
		            float f5 = (r * 30.0F + b * 70.0F) / 100.0F;
		            r = f3;
		            g = f4;
		            b = f5;
		        }
		        
		        if(tile.getTextureOrientation() == 3)
		        {
                    renderer.uvRotateTop = 3;
		        }
		        
		        if(tile.getTextureOrientation() == 4)
		        {
                    renderer.uvRotateTop = 2;
		        }
		        
		        if(tile.getTextureOrientation() == 5)
		        {
                    renderer.uvRotateTop = 1;
		        }
		        
		        renderer.renderStandardBlockWithColorMultiplier(block, x, y, z, r, g, b);
		        
                renderer.uvRotateEast = 0;
                renderer.uvRotateWest = 0;
                renderer.uvRotateTop = 0;
                renderer.uvRotateBottom = 0;
                renderer.uvRotateNorth = 0;
                renderer.uvRotateSouth = 0;
			}
		}
		

	}
	
	public void renderDiscreteQuadWithFlip(IBlockAccess world, RenderBlocks renderer, Block block, int x, int y, int z, boolean[] flip)
	{
			tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
			

			renderer.enableAO = true;
			if(flip[0])
			{
				renderer.flipTexture = true;
			}
	    	this.renderFaceAOXNeg(world, renderer, block, x, y, z);
	    	renderer.flipTexture = false;
	    	
			if(flip[1])
			{
				renderer.flipTexture = true;
			}
	    	this.renderFaceAOXPos(world, renderer, block, x, y, z);		
	    	renderer.flipTexture = false;
			
			if(flip[2])
			{
				renderer.flipTexture = true;
			}
			this.renderFaceAOYNeg(world, renderer, block, x, y, z);
			renderer.flipTexture = false;
			
			if(flip[3])
			{
				renderer.flipTexture = true;
			}
	    	this.renderFaceAOYPos(world, renderer, block, x, y, z);
	    	renderer.flipTexture = false;
	    	
			if(flip[4])
			{
				renderer.flipTexture = true;
			}
	    	this.renderFaceAOZNeg(world, renderer, block, x, y, z);
	    	renderer.flipTexture = false;
	    	
			if(flip[5])
			{
				renderer.flipTexture = true;
			}
	    	this.renderFaceAOZPos(world, renderer, block, x, y, z);
	    	renderer.flipTexture = false;
	    	
			renderer.enableAO = false;
	}
	
    //Rendering, setting and translating
    public void renderDiscreteQuad(IBlockAccess world, RenderBlocks renderer, Block block, int x, int y, int z, PointSet bounds, ForgeDirection fdirection)
    {
    	if(bounds == null)
    	{
    		this.renderDiscreteQuad(world, renderer, block, x, y, z);
    	}
    	
    	this.renderDiscreteQuad(world, renderer, block, x, y, z, this.translate(ForgeDirection.NORTH, fdirection, bounds));
    }
	
	public void renderDiscreteQuad(IBlockAccess world, RenderBlocks renderer, Block block, int x, int y, int z, PointSet bounds)
	{
    	if(bounds != null)
    	{
    		this.setRenderBounds(renderer, bounds);
    	}
    	
    	this.renderDiscreteQuad(world, renderer, block, x, y, z);	
	}
	
	public void renderDiscreteQuad(IBlockAccess world, RenderBlocks renderer, Block block, int x, int y, int z)
	{				
			renderer.enableAO = true;

	       
			this.renderFaceAOYPos(world, renderer, block, x, y, z);
	    	this.renderFaceAOZPos(world, renderer, block, x, y, z);
	    	this.renderFaceAOZNeg(world, renderer, block, x, y, z);
	    	this.renderFaceAOYNeg(world, renderer, block, x, y, z);
	    	this.renderFaceAOXPos(world, renderer, block, x, y, z);
	    	this.renderFaceAOXNeg(world, renderer, block, x, y, z);
	    	
		
	    	 /*
			int colourMultiplier = this.setupColour(world, renderer, block, x, y, z, 0);
	        float r = (float)(colourMultiplier >> 16 & 255) / 255.0F;
	        float g = (float)(colourMultiplier >> 8 & 255) / 255.0F;
	        float b = (float)(colourMultiplier & 255) / 255.0F;
	        
	        renderer.renderStandardBlockWithAmbientOcclusion(block, x, y, z, r, g, b);
	        */
	    	renderer.enableAO = false;
	}
	
	public void renderDiscreteQuadWithTextureOffsets(IBlockAccess world, RenderBlocks renderer, Block block, int x, int y, int z, TextureOffset off)
	{
		this.renderDiscreteQuadWithTextureOffsets(world, renderer, block, x, y, z, off, true);
	}
	
	public void renderDiscreteQuadWithTextureOffsets(IBlockAccess world, RenderBlocks renderer, Block block, int x, int y, int z, TextureOffset off, boolean AO)
	{		
		TileEntityDiscreteBlock tile = (TileEntityDiscreteBlock)world.getTileEntity(x, y, z);

		
		if(tile != null)
		{
			renderer.enableAO = AO;
			
			AdjustableIcon aico;
	    	
			//block = tile.getTextureSourceBlock(0);
			
	    	if(off.hasOffset(0))
	    	{
	    		
	    		aico = new AdjustableIcon(renderer.getBlockIcon(block, world, x, y, z, 0));
	    		aico.offsetU(off.getOffsetU(0));
	    		aico.offsetV(off.getOffsetV(0));
	    		
	    		if(AO)
	    		{
	    			this.renderFaceAOYNeg(world, renderer, block, x, y, z, aico);
	    		}
	    		else
	    		{
	    			this.renderFaceYNeg(world, renderer, block, x, y, z, aico);
	    		}
	    	}	
	    	else
	    	{
	    		if(AO)
	    		{
	    			this.renderFaceAOYNeg(world, renderer, block, x, y, z);
	    		}
	    		else
	    		{
	    			this.renderFaceYNeg(world, renderer, block, x, y, z, null);
	    		}
	    	}
	    	
			//block = tile.getTextureSourceBlock(1);
	    	if(off.hasOffset(1))
	    	{
	    		aico = new AdjustableIcon(renderer.getBlockIcon(block, world, x, y, z, 1));
	    		aico.offsetU(off.getOffsetU(1));
	    		aico.offsetV(off.getOffsetV(1));
	    		
	    		
	    		if(AO)
	    		{
	    			this.renderFaceAOYPos(world, renderer, block, x, y, z, aico);
	    		}
	    		else
	    		{
	    			this.renderFaceYPos(world, renderer, block, x, y, z, aico);
	    		}
	    	}	
	    	else
	    	{
	    		if(AO)
	    		{
	    			this.renderFaceAOYPos(world, renderer, block, x, y, z);
	    		}
	    		else
	    		{
	    			this.renderFaceYPos(world, renderer, block, x, y, z, null);
	    		}   		
	    	}
	    	
			//block = tile.getTextureSourceBlock(2);
	    	if(off.hasOffset(2))
	    	{
	    		aico = new AdjustableIcon(renderer.getBlockIcon(block, world, x, y, z, 2));
	    		aico.offsetU(off.getOffsetU(2));
	    		aico.offsetV(off.getOffsetV(2));
	    		
	    		if(AO)
	    		{
	    			this.renderFaceAOZNeg(world, renderer, block, x, y, z, aico);
	    		}
	    		else
	    		{
	    			this.renderFaceZNeg(world, renderer, block, x, y, z, aico);
	    		}
	    	}	
	    	else
	    	{
	    		if(AO)
	    		{
	    			this.renderFaceAOZNeg(world, renderer, block, x, y, z);
	    		}
	    		else
	    		{
	    			this.renderFaceZNeg(world, renderer, block, x, y, z, null);
	    		}
	    	}
	    	
			//block = tile.getTextureSourceBlock(3);
	    	if(off.hasOffset(3))
	    	{
	    		aico = new AdjustableIcon(renderer.getBlockIcon(block, world, x, y, z, 3));
	    		aico.offsetU(off.getOffsetU(3));
	    		aico.offsetV(off.getOffsetV(3));
	    		
	    		if(AO)
	    		{
	    			this.renderFaceAOZPos(world, renderer, block, x, y, z, aico);
	    		}
	    		else
	    		{
	    			this.renderFaceZPos(world, renderer, block, x, y, z, aico);
	    		}
	    	}	
	    	else
	    	{
	    		if(AO)
	    		{
	    			this.renderFaceAOZPos(world, renderer, block, x, y, z);
	    		}
	    		else
	    		{
	    			this.renderFaceZPos(world, renderer, block, x, y, z, null);
	    		}
	    	}
	    	
			//block = tile.getTextureSourceBlock(4);
	    	if(off.hasOffset(4))
	    	{
	    		aico = new AdjustableIcon(renderer.getBlockIcon(block, world, x, y, z, 4));
	    		aico.offsetU(off.getOffsetU(4));
	    		aico.offsetV(off.getOffsetV(4));
	    		
	    		if(AO)
	    		{
	    			this.renderFaceAOXNeg(world, renderer, block, x, y, z, aico);
	    		}
	    		else
	    		{
	    			this.renderFaceXNeg(world, renderer, block, x, y, z, aico);
	    		}
	    	}	
	    	else
	    	{
	    		if(AO)
	    		{
	    			this.renderFaceAOXNeg(world, renderer, block, x, y, z);
	    		}
	    		else
	    		{
	    			this.renderFaceXNeg(world, renderer, block, x, y, z, null);
	    		}
	    	}
	    	
			//block = tile.getTextureSourceBlock(5);
	    	if(off.hasOffset(5))
	    	{
	    		aico = new AdjustableIcon(renderer.getBlockIcon(block, world, x, y, z, 5));
	    		aico.offsetU(off.getOffsetU(5));
	    		aico.offsetV(off.getOffsetV(5));
	    		
	    		if(AO)
	    		{
	    			this.renderFaceAOXPos(world, renderer, block, x, y, z, aico);
	    		}
	    		else
	    		{
	    			this.renderFaceXPos(world, renderer, block, x, y, z, aico);
	    		}
	    	}	
	    	else
	    	{
	    		if(AO)
	    		{
	    			this.renderFaceAOXPos(world, renderer, block, x, y, z);
	    		}
	    		else
	    		{
	    			this.renderFaceXPos(world, renderer, block, x, y, z, null);
	    		}
	    	}

		}
	    	renderer.enableAO = false;
	}
	
	
	
	public int setupColour(IBlockAccess world, RenderBlocks renderer, Block block, int x, int y, int z, int side)
	{
		TileEntityDiscreteBlock tile = (TileEntityDiscreteBlock)world.getTileEntity(x, y, z);
		
		/*
		if(tile.getTextureSource(side) == 0)
		{
			return block.colorMultiplier(world, x, y, z);
		}
		else
		{
			return Blocks[tile.getTextureSource(side)].colorMultiplier(world, x, y, z);
		}
		*/
		
		return tile.getTextureSourceBlock(side).colorMultiplier(world, x, y, z);
	}
	
	public int getColour(IBlockAccess world, RenderBlocks renderer, Block block, int x, int y, int z, int side)
	{
		return block.colorMultiplier(world, x, y, z);
	}
	
	public void renderFaceAOXNeg(IBlockAccess world, RenderBlocks renderer, Block block, int x, int y, int z)
	{
		this.renderFaceAOXNeg(world, renderer, block, x, y, z, null);
	}

	public void renderFaceAOXNeg(IBlockAccess world, RenderBlocks renderer, Block block, int x, int y, int z, IIcon ico)
	{
		int colourMultiplier = this.setupColour(world, renderer, block, x, y, z, 4);
        float par5 = (float)(colourMultiplier >> 16 & 255) / 255.0F;
        float par6 = (float)(colourMultiplier >> 8 & 255) / 255.0F;
        float par7 = (float)(colourMultiplier & 255) / 255.0F;
        
        boolean flag = false;
        float f3 = 0.0F;
        float f4 = 0.0F;
        float f5 = 0.0F;
        float f6 = 0.0F;
        boolean flag1 = true;
        int l = block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z);
        Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(983055);
        
        
        if (renderer.getBlockIcon(block).getIconName().equals("grasstop"))
        {
            flag1 = false;
        }
        else if (renderer.hasOverrideBlockTexture())
        {
            flag1 = false;
        }
        

        boolean flag2;
        boolean flag3;
        boolean flag4;
        boolean flag5;
        float f7;
        int i1;
        float f8;
        float f9;
        float f10;
        float f11;
        int j1;
        int k1;
        int l1;
        int i2;
        IIcon icon;
        
        if (renderer.renderAllFaces || block.shouldSideBeRendered(world, x - 1, y, z, 4))
        {
            if (renderer.renderMinX <= 0.0D)
            {
                --x;
            }

            renderer.aoLightValueScratchXYNN = renderer.blockAccess.getBlock( x, y - 1, z).getAmbientOcclusionLightValue();
            renderer.aoLightValueScratchXZNN = renderer.blockAccess.getBlock( x, y, z - 1).getAmbientOcclusionLightValue();
            renderer.aoLightValueScratchXZNP = renderer.blockAccess.getBlock( x, y, z + 1).getAmbientOcclusionLightValue();
            renderer.aoLightValueScratchXYNP = renderer.blockAccess.getBlock( x, y + 1, z).getAmbientOcclusionLightValue();
            renderer.aoBrightnessXYNN = block.getMixedBrightnessForBlock(world, x, y - 1, z);
            renderer.aoBrightnessXZNN = block.getMixedBrightnessForBlock(world, x, y, z - 1);
            renderer.aoBrightnessXZNP = block.getMixedBrightnessForBlock(world, x, y, z + 1);
            renderer.aoBrightnessXYNP = block.getMixedBrightnessForBlock(world, x, y + 1, z);
            flag3 = renderer.blockAccess.getBlock(x - 1, y + 1, z).getCanBlockGrass();
            flag2 = renderer.blockAccess.getBlock(x - 1, y - 1, z).getCanBlockGrass();
            flag5 = renderer.blockAccess.getBlock(x - 1, y, z - 1).getCanBlockGrass();
            flag4 = renderer.blockAccess.getBlock(x - 1, y, z + 1).getCanBlockGrass();

            if (!flag5 && !flag2)
            {
                renderer.aoLightValueScratchXYZNNN = renderer.aoLightValueScratchXZNN;
                renderer.aoBrightnessXYZNNN = renderer.aoBrightnessXZNN;
            }
            else
            {
                renderer.aoLightValueScratchXYZNNN = renderer.blockAccess.getBlock( x, y - 1, z - 1).getAmbientOcclusionLightValue();
                renderer.aoBrightnessXYZNNN = block.getMixedBrightnessForBlock(world, x, y - 1, z - 1);
            }

            if (!flag4 && !flag2)
            {
                renderer.aoLightValueScratchXYZNNP = renderer.aoLightValueScratchXZNP;
                renderer.aoBrightnessXYZNNP = renderer.aoBrightnessXZNP;
            }
            else
            {
                renderer.aoLightValueScratchXYZNNP = renderer.blockAccess.getBlock( x, y - 1, z + 1).getAmbientOcclusionLightValue();
                renderer.aoBrightnessXYZNNP = block.getMixedBrightnessForBlock(world, x, y - 1, z + 1);
            }

            if (!flag5 && !flag3)
            {
                renderer.aoLightValueScratchXYZNPN = renderer.aoLightValueScratchXZNN;
                renderer.aoBrightnessXYZNPN = renderer.aoBrightnessXZNN;
            }
            else
            {
                renderer.aoLightValueScratchXYZNPN = renderer.blockAccess.getBlock( x, y + 1, z - 1).getAmbientOcclusionLightValue();
                renderer.aoBrightnessXYZNPN = block.getMixedBrightnessForBlock(world, x, y + 1, z - 1);
            }

            if (!flag4 && !flag3)
            {
                renderer.aoLightValueScratchXYZNPP = renderer.aoLightValueScratchXZNP;
                renderer.aoBrightnessXYZNPP = renderer.aoBrightnessXZNP;
            }
            else
            {
                renderer.aoLightValueScratchXYZNPP = renderer.blockAccess.getBlock( x, y + 1, z + 1).getAmbientOcclusionLightValue();
                renderer.aoBrightnessXYZNPP = block.getMixedBrightnessForBlock(world, x, y + 1, z + 1);
            }

            if (renderer.renderMinX <= 0.0D)
            {
                ++x;
            }

            i1 = l;

            if (renderer.renderMinX <= 0.0D || !renderer.blockAccess.getBlock(x - 1, y, z).isOpaqueCube())
            {
                i1 = block.getMixedBrightnessForBlock(world, x - 1, y, z);
            }

            f7 = renderer.blockAccess.getBlock( x - 1, y, z).getAmbientOcclusionLightValue();
            f9 = (renderer.aoLightValueScratchXYNN + renderer.aoLightValueScratchXYZNNP + f7 + renderer.aoLightValueScratchXZNP) / 4.0F;
            f8 = (f7 + renderer.aoLightValueScratchXZNP + renderer.aoLightValueScratchXYNP + renderer.aoLightValueScratchXYZNPP) / 4.0F;
            f11 = (renderer.aoLightValueScratchXZNN + f7 + renderer.aoLightValueScratchXYZNPN + renderer.aoLightValueScratchXYNP) / 4.0F;
            f10 = (renderer.aoLightValueScratchXYZNNN + renderer.aoLightValueScratchXYNN + renderer.aoLightValueScratchXZNN + f7) / 4.0F;
            f3 = (float)((double)f8 * renderer.renderMaxY * renderer.renderMaxZ + (double)f11 * renderer.renderMaxY * (1.0D - renderer.renderMaxZ) + (double)f10 * (1.0D - renderer.renderMaxY) * (1.0D - renderer.renderMaxZ) + (double)f9 * (1.0D - renderer.renderMaxY) * renderer.renderMaxZ);
            f4 = (float)((double)f8 * renderer.renderMaxY * renderer.renderMinZ + (double)f11 * renderer.renderMaxY * (1.0D - renderer.renderMinZ) + (double)f10 * (1.0D - renderer.renderMaxY) * (1.0D - renderer.renderMinZ) + (double)f9 * (1.0D - renderer.renderMaxY) * renderer.renderMinZ);
            f5 = (float)((double)f8 * renderer.renderMinY * renderer.renderMinZ + (double)f11 * renderer.renderMinY * (1.0D - renderer.renderMinZ) + (double)f10 * (1.0D - renderer.renderMinY) * (1.0D - renderer.renderMinZ) + (double)f9 * (1.0D - renderer.renderMinY) * renderer.renderMinZ);
            f6 = (float)((double)f8 * renderer.renderMinY * renderer.renderMaxZ + (double)f11 * renderer.renderMinY * (1.0D - renderer.renderMaxZ) + (double)f10 * (1.0D - renderer.renderMinY) * (1.0D - renderer.renderMaxZ) + (double)f9 * (1.0D - renderer.renderMinY) * renderer.renderMaxZ);
            k1 = renderer.getAoBrightness(renderer.aoBrightnessXYNN, renderer.aoBrightnessXYZNNP, renderer.aoBrightnessXZNP, i1);
            j1 = renderer.getAoBrightness(renderer.aoBrightnessXZNP, renderer.aoBrightnessXYNP, renderer.aoBrightnessXYZNPP, i1);
            i2 = renderer.getAoBrightness(renderer.aoBrightnessXZNN, renderer.aoBrightnessXYZNPN, renderer.aoBrightnessXYNP, i1);
            l1 = renderer.getAoBrightness(renderer.aoBrightnessXYZNNN, renderer.aoBrightnessXYNN, renderer.aoBrightnessXZNN, i1);
            renderer.brightnessTopLeft = renderer.mixAoBrightness(j1, i2, l1, k1, renderer.renderMaxY * renderer.renderMaxZ, renderer.renderMaxY * (1.0D - renderer.renderMaxZ), (1.0D - renderer.renderMaxY) * (1.0D - renderer.renderMaxZ), (1.0D - renderer.renderMaxY) * renderer.renderMaxZ);
            renderer.brightnessBottomLeft = renderer.mixAoBrightness(j1, i2, l1, k1, renderer.renderMaxY * renderer.renderMinZ, renderer.renderMaxY * (1.0D - renderer.renderMinZ), (1.0D - renderer.renderMaxY) * (1.0D - renderer.renderMinZ), (1.0D - renderer.renderMaxY) * renderer.renderMinZ);
            renderer.brightnessBottomRight = renderer.mixAoBrightness(j1, i2, l1, k1, renderer.renderMinY * renderer.renderMinZ, renderer.renderMinY * (1.0D - renderer.renderMinZ), (1.0D - renderer.renderMinY) * (1.0D - renderer.renderMinZ), (1.0D - renderer.renderMinY) * renderer.renderMinZ);
            renderer.brightnessTopRight = renderer.mixAoBrightness(j1, i2, l1, k1, renderer.renderMinY * renderer.renderMaxZ, renderer.renderMinY * (1.0D - renderer.renderMaxZ), (1.0D - renderer.renderMinY) * (1.0D - renderer.renderMaxZ), (1.0D - renderer.renderMinY) * renderer.renderMaxZ);

            if (flag1)
            {
                renderer.colorRedTopLeft = renderer.colorRedBottomLeft = renderer.colorRedBottomRight = renderer.colorRedTopRight = par5 * 0.6F;
                renderer.colorGreenTopLeft = renderer.colorGreenBottomLeft = renderer.colorGreenBottomRight = renderer.colorGreenTopRight = par6 * 0.6F;
                renderer.colorBlueTopLeft = renderer.colorBlueBottomLeft = renderer.colorBlueBottomRight = renderer.colorBlueTopRight = par7 * 0.6F;
            }
            else
            {
                renderer.colorRedTopLeft = renderer.colorRedBottomLeft = renderer.colorRedBottomRight = renderer.colorRedTopRight = 0.6F;
                renderer.colorGreenTopLeft = renderer.colorGreenBottomLeft = renderer.colorGreenBottomRight = renderer.colorGreenTopRight = 0.6F;
                renderer.colorBlueTopLeft = renderer.colorBlueBottomLeft = renderer.colorBlueBottomRight = renderer.colorBlueTopRight = 0.6F;
            }

            renderer.colorRedTopLeft *= f3;
            renderer.colorGreenTopLeft *= f3;
            renderer.colorBlueTopLeft *= f3;
            renderer.colorRedBottomLeft *= f4;
            renderer.colorGreenBottomLeft *= f4;
            renderer.colorBlueBottomLeft *= f4;
            renderer.colorRedBottomRight *= f5;
            renderer.colorGreenBottomRight *= f5;
            renderer.colorBlueBottomRight *= f5;
            renderer.colorRedTopRight *= f6;
            renderer.colorGreenTopRight *= f6;
            renderer.colorBlueTopRight *= f6;
            
            if(ico != null)
            {
            	icon = ico;
            }
            else
            {
            	icon = renderer.getBlockIcon(block, world, x, y, z, 4);
            }
            
            renderer.renderFaceXNeg(block, (double)x, (double)y, (double)z, icon);

            if (renderer.fancyGrass && icon.getIconName().equals("grassside") && !renderer.hasOverrideBlockTexture())
            {
                renderer.colorRedTopLeft *= par5;
                renderer.colorRedBottomLeft *= par5;
                renderer.colorRedBottomRight *= par5;
                renderer.colorRedTopRight *= par5;
                renderer.colorGreenTopLeft *= par6;
                renderer.colorGreenBottomLeft *= par6;
                renderer.colorGreenBottomRight *= par6;
                renderer.colorGreenTopRight *= par6;
                renderer.colorBlueTopLeft *= par7;
                renderer.colorBlueBottomLeft *= par7;
                renderer.colorBlueBottomRight *= par7;
                renderer.colorBlueTopRight *= par7;
                renderer.renderFaceXNeg(block, (double)x, (double)y, (double)z, BlockGrass.getIconSideOverlay());
            }
        }
        
	}
	
	public void renderFaceAOXPos(IBlockAccess world, RenderBlocks renderer, Block block, int x, int y, int z)
	{
		this.renderFaceAOXPos(world, renderer, block, x, y, z, null);
	}
	
	public void renderFaceAOXPos(IBlockAccess world, RenderBlocks renderer, Block block, int x, int y, int z, IIcon ico)
	{
        int l2 = this.setupColour(world, renderer, block, x, y, z, 5);
        float par5 = (float)(l2 >> 16 & 255) / 255.0F;
        float par6 = (float)(l2 >> 8 & 255) / 255.0F;
        float par7 = (float)(l2 & 255) / 255.0F;
        

        boolean flag = false;
        float f3 = 0.0F;
        float f4 = 0.0F;
        float f5 = 0.0F;
        float f6 = 0.0F;
        boolean flag1 = true;
        int l = block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z);
        Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(983055);
        
        
        if (renderer.getBlockIcon(block).getIconName().equals("grasstop"))
        {
            flag1 = false;
        }
        else if (renderer.hasOverrideBlockTexture())
        {
            flag1 = false;
        }
        

        boolean flag2;
        boolean flag3;
        boolean flag4;
        boolean flag5;
        float f7;
        int i1;
        float f8;
        float f9;
        float f10;
        float f11;
        int j1;
        int k1;
        int l1;
        int i2;
        
        IIcon icon;
        
        if (renderer.renderAllFaces || block.shouldSideBeRendered(world, x + 1, y, z, 5))
        {
            if (renderer.renderMaxX >= 1.0D)
            {
                ++x;
            }

            renderer.aoLightValueScratchXYPN = renderer.blockAccess.getBlock( x, y - 1, z).getAmbientOcclusionLightValue();
            renderer.aoLightValueScratchXZPN = renderer.blockAccess.getBlock( x, y, z - 1).getAmbientOcclusionLightValue();
            renderer.aoLightValueScratchXZPP = renderer.blockAccess.getBlock( x, y, z + 1).getAmbientOcclusionLightValue();
            renderer.aoLightValueScratchXYPP = renderer.blockAccess.getBlock( x, y + 1, z).getAmbientOcclusionLightValue();
            renderer.aoBrightnessXYPN = block.getMixedBrightnessForBlock(world, x, y - 1, z);
            renderer.aoBrightnessXZPN = block.getMixedBrightnessForBlock(world, x, y, z - 1);
            renderer.aoBrightnessXZPP = block.getMixedBrightnessForBlock(world, x, y, z + 1);
            renderer.aoBrightnessXYPP = block.getMixedBrightnessForBlock(world, x, y + 1, z);
            flag3 = renderer.blockAccess.getBlock(x + 1, y + 1, z).getCanBlockGrass();
            flag2 = renderer.blockAccess.getBlock(x + 1, y - 1, z).getCanBlockGrass();
            flag5 = renderer.blockAccess.getBlock(x + 1, y, z + 1).getCanBlockGrass();
            flag4 = renderer.blockAccess.getBlock(x + 1, y, z - 1).getCanBlockGrass();

            if (!flag2 && !flag4)
            {
                renderer.aoLightValueScratchXYZPNN = renderer.aoLightValueScratchXZPN;
                renderer.aoBrightnessXYZPNN = renderer.aoBrightnessXZPN;
            }
            else
            {
                renderer.aoLightValueScratchXYZPNN = renderer.blockAccess.getBlock( x, y - 1, z - 1).getAmbientOcclusionLightValue();
                renderer.aoBrightnessXYZPNN = block.getMixedBrightnessForBlock(world, x, y - 1, z - 1);
            }

            if (!flag2 && !flag5)
            {
                renderer.aoLightValueScratchXYZPNP = renderer.aoLightValueScratchXZPP;
                renderer.aoBrightnessXYZPNP = renderer.aoBrightnessXZPP;
            }
            else
            {
                renderer.aoLightValueScratchXYZPNP = renderer.blockAccess.getBlock( x, y - 1, z + 1).getAmbientOcclusionLightValue();
                renderer.aoBrightnessXYZPNP = block.getMixedBrightnessForBlock(world, x, y - 1, z + 1);
            }

            if (!flag3 && !flag4)
            {
                renderer.aoLightValueScratchXYZPPN = renderer.aoLightValueScratchXZPN;
                renderer.aoBrightnessXYZPPN = renderer.aoBrightnessXZPN;
            }
            else
            {
                renderer.aoLightValueScratchXYZPPN = renderer.blockAccess.getBlock( x, y + 1, z - 1).getAmbientOcclusionLightValue();
                renderer.aoBrightnessXYZPPN = block.getMixedBrightnessForBlock(world, x, y + 1, z - 1);
            }

            if (!flag3 && !flag5)
            {
                renderer.aoLightValueScratchXYZPPP = renderer.aoLightValueScratchXZPP;
                renderer.aoBrightnessXYZPPP = renderer.aoBrightnessXZPP;
            }
            else
            {
                renderer.aoLightValueScratchXYZPPP = renderer.blockAccess.getBlock( x, y + 1, z + 1).getAmbientOcclusionLightValue();
                renderer.aoBrightnessXYZPPP = block.getMixedBrightnessForBlock(world, x, y + 1, z + 1);
            }

            if (renderer.renderMaxX >= 1.0D)
            {
                --x;
            }

            i1 = l;

            if (renderer.renderMaxX >= 1.0D || !renderer.blockAccess.getBlock(x + 1, y, z).isOpaqueCube())
            {
                i1 = block.getMixedBrightnessForBlock(world, x + 1, y, z);
            }

            f7 = renderer.blockAccess.getBlock( x + 1, y, z).getAmbientOcclusionLightValue();
            f9 = (renderer.aoLightValueScratchXYPN + renderer.aoLightValueScratchXYZPNP + f7 + renderer.aoLightValueScratchXZPP) / 4.0F;
            f8 = (renderer.aoLightValueScratchXYZPNN + renderer.aoLightValueScratchXYPN + renderer.aoLightValueScratchXZPN + f7) / 4.0F;
            f11 = (renderer.aoLightValueScratchXZPN + f7 + renderer.aoLightValueScratchXYZPPN + renderer.aoLightValueScratchXYPP) / 4.0F;
            f10 = (f7 + renderer.aoLightValueScratchXZPP + renderer.aoLightValueScratchXYPP + renderer.aoLightValueScratchXYZPPP) / 4.0F;
            f3 = (float)((double)f9 * (1.0D - renderer.renderMinY) * renderer.renderMaxZ + (double)f8 * (1.0D - renderer.renderMinY) * (1.0D - renderer.renderMaxZ) + (double)f11 * renderer.renderMinY * (1.0D - renderer.renderMaxZ) + (double)f10 * renderer.renderMinY * renderer.renderMaxZ);
            f4 = (float)((double)f9 * (1.0D - renderer.renderMinY) * renderer.renderMinZ + (double)f8 * (1.0D - renderer.renderMinY) * (1.0D - renderer.renderMinZ) + (double)f11 * renderer.renderMinY * (1.0D - renderer.renderMinZ) + (double)f10 * renderer.renderMinY * renderer.renderMinZ);
            f5 = (float)((double)f9 * (1.0D - renderer.renderMaxY) * renderer.renderMinZ + (double)f8 * (1.0D - renderer.renderMaxY) * (1.0D - renderer.renderMinZ) + (double)f11 * renderer.renderMaxY * (1.0D - renderer.renderMinZ) + (double)f10 * renderer.renderMaxY * renderer.renderMinZ);
            f6 = (float)((double)f9 * (1.0D - renderer.renderMaxY) * renderer.renderMaxZ + (double)f8 * (1.0D - renderer.renderMaxY) * (1.0D - renderer.renderMaxZ) + (double)f11 * renderer.renderMaxY * (1.0D - renderer.renderMaxZ) + (double)f10 * renderer.renderMaxY * renderer.renderMaxZ);
            k1 = renderer.getAoBrightness(renderer.aoBrightnessXYPN, renderer.aoBrightnessXYZPNP, renderer.aoBrightnessXZPP, i1);
            j1 = renderer.getAoBrightness(renderer.aoBrightnessXZPP, renderer.aoBrightnessXYPP, renderer.aoBrightnessXYZPPP, i1);
            i2 = renderer.getAoBrightness(renderer.aoBrightnessXZPN, renderer.aoBrightnessXYZPPN, renderer.aoBrightnessXYPP, i1);
            l1 = renderer.getAoBrightness(renderer.aoBrightnessXYZPNN, renderer.aoBrightnessXYPN, renderer.aoBrightnessXZPN, i1);
            renderer.brightnessTopLeft = renderer.mixAoBrightness(k1, l1, i2, j1, (1.0D - renderer.renderMinY) * renderer.renderMaxZ, (1.0D - renderer.renderMinY) * (1.0D - renderer.renderMaxZ), renderer.renderMinY * (1.0D - renderer.renderMaxZ), renderer.renderMinY * renderer.renderMaxZ);
            renderer.brightnessBottomLeft = renderer.mixAoBrightness(k1, l1, i2, j1, (1.0D - renderer.renderMinY) * renderer.renderMinZ, (1.0D - renderer.renderMinY) * (1.0D - renderer.renderMinZ), renderer.renderMinY * (1.0D - renderer.renderMinZ), renderer.renderMinY * renderer.renderMinZ);
            renderer.brightnessBottomRight = renderer.mixAoBrightness(k1, l1, i2, j1, (1.0D - renderer.renderMaxY) * renderer.renderMinZ, (1.0D - renderer.renderMaxY) * (1.0D - renderer.renderMinZ), renderer.renderMaxY * (1.0D - renderer.renderMinZ), renderer.renderMaxY * renderer.renderMinZ);
            renderer.brightnessTopRight = renderer.mixAoBrightness(k1, l1, i2, j1, (1.0D - renderer.renderMaxY) * renderer.renderMaxZ, (1.0D - renderer.renderMaxY) * (1.0D - renderer.renderMaxZ), renderer.renderMaxY * (1.0D - renderer.renderMaxZ), renderer.renderMaxY * renderer.renderMaxZ);

            if (flag1)
            {
                renderer.colorRedTopLeft = renderer.colorRedBottomLeft = renderer.colorRedBottomRight = renderer.colorRedTopRight = par5 * 0.6F;
                renderer.colorGreenTopLeft = renderer.colorGreenBottomLeft = renderer.colorGreenBottomRight = renderer.colorGreenTopRight = par6 * 0.6F;
                renderer.colorBlueTopLeft = renderer.colorBlueBottomLeft = renderer.colorBlueBottomRight = renderer.colorBlueTopRight = par7 * 0.6F;
            }
            else
            {
                renderer.colorRedTopLeft = renderer.colorRedBottomLeft = renderer.colorRedBottomRight = renderer.colorRedTopRight = 0.6F;
                renderer.colorGreenTopLeft = renderer.colorGreenBottomLeft = renderer.colorGreenBottomRight = renderer.colorGreenTopRight = 0.6F;
                renderer.colorBlueTopLeft = renderer.colorBlueBottomLeft = renderer.colorBlueBottomRight = renderer.colorBlueTopRight = 0.6F;
            }

            renderer.colorRedTopLeft *= f3;
            renderer.colorGreenTopLeft *= f3;
            renderer.colorBlueTopLeft *= f3;
            renderer.colorRedBottomLeft *= f4;
            renderer.colorGreenBottomLeft *= f4;
            renderer.colorBlueBottomLeft *= f4;
            renderer.colorRedBottomRight *= f5;
            renderer.colorGreenBottomRight *= f5;
            renderer.colorBlueBottomRight *= f5;
            renderer.colorRedTopRight *= f6;
            renderer.colorGreenTopRight *= f6;
            renderer.colorBlueTopRight *= f6;
            
            if(ico != null)
            {
            	icon = ico;
            }
            else
            {
                icon = renderer.getBlockIcon(block, world, x, y, z, 5);	
            }
            

            renderer.renderFaceXPos(block, (double)x, (double)y, (double)z, icon);

            if (renderer.fancyGrass && icon.getIconName().equals("grassside") && !renderer.hasOverrideBlockTexture())
            {
                renderer.colorRedTopLeft *= par5;
                renderer.colorRedBottomLeft *= par5;
                renderer.colorRedBottomRight *= par5;
                renderer.colorRedTopRight *= par5;
                renderer.colorGreenTopLeft *= par6;
                renderer.colorGreenBottomLeft *= par6;
                renderer.colorGreenBottomRight *= par6;
                renderer.colorGreenTopRight *= par6;
                renderer.colorBlueTopLeft *= par7;
                renderer.colorBlueBottomLeft *= par7;
                renderer.colorBlueBottomRight *= par7;
                renderer.colorBlueTopRight *= par7;
                renderer.renderFaceXPos(block, (double)x, (double)y, (double)z, BlockGrass.getIconSideOverlay());
            }

            flag = true;
        }
        

	}
	
	public void renderFaceAOZNeg(IBlockAccess world, RenderBlocks renderer, Block block, int x, int y, int z)
	{
		this.renderFaceAOZNeg(world, renderer, block, x, y, z, null);
	}
	
	public void renderFaceAOZNeg(IBlockAccess world, RenderBlocks renderer, Block block, int x, int y, int z, IIcon ico)
	{
        int l2 = this.setupColour(world, renderer, block, x, y, z, 2);
        float par5 = (float)(l2 >> 16 & 255) / 255.0F;
        float par6 = (float)(l2 >> 8 & 255) / 255.0F;
        float par7 = (float)(l2 & 255) / 255.0F;
        

        boolean flag = false;
        float f3 = 0.0F;
        float f4 = 0.0F;
        float f5 = 0.0F;
        float f6 = 0.0F;
        boolean flag1 = true;
        int l = block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z);
        Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(983055);
        
        
        if (renderer.getBlockIcon(block).getIconName().equals("grasstop"))
        {
            flag1 = false;
        }
        else if (renderer.hasOverrideBlockTexture())
        {
            flag1 = false;
        }
        

        boolean flag2;
        boolean flag3;
        boolean flag4;
        boolean flag5;
        float f7;
        int i1;
        float f8;
        float f9;
        float f10;
        float f11;
        int j1;
        int k1;
        int l1;
        int i2;
        IIcon icon;
        
        if (renderer.renderMinZ <= 0.0D)
        {
            --z;
        }

        renderer.aoLightValueScratchXZNN = renderer.blockAccess.getBlock( x - 1, y, z).getAmbientOcclusionLightValue();
        renderer.aoLightValueScratchYZNN = renderer.blockAccess.getBlock( x, y - 1, z).getAmbientOcclusionLightValue();
        renderer.aoLightValueScratchYZPN = renderer.blockAccess.getBlock( x, y + 1, z).getAmbientOcclusionLightValue();
        renderer.aoLightValueScratchXZPN = renderer.blockAccess.getBlock( x + 1, y, z).getAmbientOcclusionLightValue();
        renderer.aoBrightnessXZNN = block.getMixedBrightnessForBlock(world, x - 1, y, z);
        renderer.aoBrightnessYZNN = block.getMixedBrightnessForBlock(world, x, y - 1, z);
        renderer.aoBrightnessYZPN = block.getMixedBrightnessForBlock(world, x, y + 1, z);
        renderer.aoBrightnessXZPN = block.getMixedBrightnessForBlock(world, x + 1, y, z);
        flag3 = renderer.blockAccess.getBlock(x + 1, y, z - 1).getCanBlockGrass();
        flag2 = renderer.blockAccess.getBlock(x - 1, y, z - 1).getCanBlockGrass();
        flag5 = renderer.blockAccess.getBlock(x, y + 1, z - 1).getCanBlockGrass();
        flag4 = renderer.blockAccess.getBlock(x, y - 1, z - 1).getCanBlockGrass();

        if (!flag2 && !flag4)
        {
            renderer.aoLightValueScratchXYZNNN = renderer.aoLightValueScratchXZNN;
            renderer.aoBrightnessXYZNNN = renderer.aoBrightnessXZNN;
        }
        else
        {
            renderer.aoLightValueScratchXYZNNN = renderer.blockAccess.getBlock( x - 1, y - 1, z).getAmbientOcclusionLightValue();
            renderer.aoBrightnessXYZNNN = block.getMixedBrightnessForBlock(world, x - 1, y - 1, z);
        }

        if (!flag2 && !flag5)
        {
            renderer.aoLightValueScratchXYZNPN = renderer.aoLightValueScratchXZNN;
            renderer.aoBrightnessXYZNPN = renderer.aoBrightnessXZNN;
        }
        else
        {
            renderer.aoLightValueScratchXYZNPN = renderer.blockAccess.getBlock( x - 1, y + 1, z).getAmbientOcclusionLightValue();
            renderer.aoBrightnessXYZNPN = block.getMixedBrightnessForBlock(world, x - 1, y + 1, z);
        }

        if (!flag3 && !flag4)
        {
            renderer.aoLightValueScratchXYZPNN = renderer.aoLightValueScratchXZPN;
            renderer.aoBrightnessXYZPNN = renderer.aoBrightnessXZPN;
        }
        else
        {
            renderer.aoLightValueScratchXYZPNN = renderer.blockAccess.getBlock( x + 1, y - 1, z).getAmbientOcclusionLightValue();
            renderer.aoBrightnessXYZPNN = block.getMixedBrightnessForBlock(world, x + 1, y - 1, z);
        }

        if (!flag3 && !flag5)
        {
            renderer.aoLightValueScratchXYZPPN = renderer.aoLightValueScratchXZPN;
            renderer.aoBrightnessXYZPPN = renderer.aoBrightnessXZPN;
        }
        else
        {
            renderer.aoLightValueScratchXYZPPN = renderer.blockAccess.getBlock( x + 1, y + 1, z).getAmbientOcclusionLightValue();
            renderer.aoBrightnessXYZPPN = block.getMixedBrightnessForBlock(world, x + 1, y + 1, z);
        }

        if (renderer.renderMinZ <= 0.0D)
        {
            ++z;
        }

        i1 = l;

        if (renderer.renderMinZ <= 0.0D || !renderer.blockAccess.getBlock(x, y, z - 1).isOpaqueCube())
        {
            i1 = block.getMixedBrightnessForBlock(world, x, y, z - 1);
        }

        f7 = renderer.blockAccess.getBlock( x, y, z - 1).getAmbientOcclusionLightValue();
        f9 = (renderer.aoLightValueScratchXZNN + renderer.aoLightValueScratchXYZNPN + f7 + renderer.aoLightValueScratchYZPN) / 4.0F;
        f8 = (f7 + renderer.aoLightValueScratchYZPN + renderer.aoLightValueScratchXZPN + renderer.aoLightValueScratchXYZPPN) / 4.0F;
        f11 = (renderer.aoLightValueScratchYZNN + f7 + renderer.aoLightValueScratchXYZPNN + renderer.aoLightValueScratchXZPN) / 4.0F;
        f10 = (renderer.aoLightValueScratchXYZNNN + renderer.aoLightValueScratchXZNN + renderer.aoLightValueScratchYZNN + f7) / 4.0F;
        f3 = (float)((double)f9 * renderer.renderMaxY * (1.0D - renderer.renderMinX) + (double)f8 * renderer.renderMinY * renderer.renderMinX + (double)f11 * (1.0D - renderer.renderMaxY) * renderer.renderMinX + (double)f10 * (1.0D - renderer.renderMaxY) * (1.0D - renderer.renderMinX));
        f4 = (float)((double)f9 * renderer.renderMaxY * (1.0D - renderer.renderMaxX) + (double)f8 * renderer.renderMaxY * renderer.renderMaxX + (double)f11 * (1.0D - renderer.renderMaxY) * renderer.renderMaxX + (double)f10 * (1.0D - renderer.renderMaxY) * (1.0D - renderer.renderMaxX));
        f5 = (float)((double)f9 * renderer.renderMinY * (1.0D - renderer.renderMaxX) + (double)f8 * renderer.renderMinY * renderer.renderMaxX + (double)f11 * (1.0D - renderer.renderMinY) * renderer.renderMaxX + (double)f10 * (1.0D - renderer.renderMinY) * (1.0D - renderer.renderMaxX));
        f6 = (float)((double)f9 * renderer.renderMinY * (1.0D - renderer.renderMinX) + (double)f8 * renderer.renderMinY * renderer.renderMinX + (double)f11 * (1.0D - renderer.renderMinY) * renderer.renderMinX + (double)f10 * (1.0D - renderer.renderMinY) * (1.0D - renderer.renderMinX));
        k1 = renderer.getAoBrightness(renderer.aoBrightnessXZNN, renderer.aoBrightnessXYZNPN, renderer.aoBrightnessYZPN, i1);
        j1 = renderer.getAoBrightness(renderer.aoBrightnessYZPN, renderer.aoBrightnessXZPN, renderer.aoBrightnessXYZPPN, i1);
        i2 = renderer.getAoBrightness(renderer.aoBrightnessYZNN, renderer.aoBrightnessXYZPNN, renderer.aoBrightnessXZPN, i1);
        l1 = renderer.getAoBrightness(renderer.aoBrightnessXYZNNN, renderer.aoBrightnessXZNN, renderer.aoBrightnessYZNN, i1);
        renderer.brightnessTopLeft = renderer.mixAoBrightness(k1, j1, i2, l1, renderer.renderMaxY * (1.0D - renderer.renderMinX), renderer.renderMaxY * renderer.renderMinX, (1.0D - renderer.renderMaxY) * renderer.renderMinX, (1.0D - renderer.renderMaxY) * (1.0D - renderer.renderMinX));
        renderer.brightnessBottomLeft = renderer.mixAoBrightness(k1, j1, i2, l1, renderer.renderMaxY * (1.0D - renderer.renderMaxX), renderer.renderMaxY * renderer.renderMaxX, (1.0D - renderer.renderMaxY) * renderer.renderMaxX, (1.0D - renderer.renderMaxY) * (1.0D - renderer.renderMaxX));
        renderer.brightnessBottomRight = renderer.mixAoBrightness(k1, j1, i2, l1, renderer.renderMinY * (1.0D - renderer.renderMaxX), renderer.renderMinY * renderer.renderMaxX, (1.0D - renderer.renderMinY) * renderer.renderMaxX, (1.0D - renderer.renderMinY) * (1.0D - renderer.renderMaxX));
        renderer.brightnessTopRight = renderer.mixAoBrightness(k1, j1, i2, l1, renderer.renderMinY * (1.0D - renderer.renderMinX), renderer.renderMinY * renderer.renderMinX, (1.0D - renderer.renderMinY) * renderer.renderMinX, (1.0D - renderer.renderMinY) * (1.0D - renderer.renderMinX));

        if (flag1)
        {
            renderer.colorRedTopLeft = renderer.colorRedBottomLeft = renderer.colorRedBottomRight = renderer.colorRedTopRight = par5 * 0.8F;
            renderer.colorGreenTopLeft = renderer.colorGreenBottomLeft = renderer.colorGreenBottomRight = renderer.colorGreenTopRight = par6 * 0.8F;
            renderer.colorBlueTopLeft = renderer.colorBlueBottomLeft = renderer.colorBlueBottomRight = renderer.colorBlueTopRight = par7 * 0.8F;
        }
        else
        {
            renderer.colorRedTopLeft = renderer.colorRedBottomLeft = renderer.colorRedBottomRight = renderer.colorRedTopRight = 0.8F;
            renderer.colorGreenTopLeft = renderer.colorGreenBottomLeft = renderer.colorGreenBottomRight = renderer.colorGreenTopRight = 0.8F;
            renderer.colorBlueTopLeft = renderer.colorBlueBottomLeft = renderer.colorBlueBottomRight = renderer.colorBlueTopRight = 0.8F;
        }

        renderer.colorRedTopLeft *= f3;
        renderer.colorGreenTopLeft *= f3;
        renderer.colorBlueTopLeft *= f3;
        renderer.colorRedBottomLeft *= f4;
        renderer.colorGreenBottomLeft *= f4;
        renderer.colorBlueBottomLeft *= f4;
        renderer.colorRedBottomRight *= f5;
        renderer.colorGreenBottomRight *= f5;
        renderer.colorBlueBottomRight *= f5;
        renderer.colorRedTopRight *= f6;
        renderer.colorGreenTopRight *= f6;
        renderer.colorBlueTopRight *= f6;

        if(ico != null)
        {
        	icon = ico;
        }
        else
        {
        	icon = renderer.getBlockIcon(block, world, x, y, z, 2);
        }
        renderer.renderFaceZNeg(block, (double)x, (double)y, (double)z, icon);

        if (renderer.fancyGrass && icon.getIconName().equals("grassside") && !renderer.hasOverrideBlockTexture())
        {
            renderer.colorRedTopLeft *= par5;
            renderer.colorRedBottomLeft *= par5;
            renderer.colorRedBottomRight *= par5;
            renderer.colorRedTopRight *= par5;
            renderer.colorGreenTopLeft *= par6;
            renderer.colorGreenBottomLeft *= par6;
            renderer.colorGreenBottomRight *= par6;
            renderer.colorGreenTopRight *= par6;
            renderer.colorBlueTopLeft *= par7;
            renderer.colorBlueBottomLeft *= par7;
            renderer.colorBlueBottomRight *= par7;
            renderer.colorBlueTopRight *= par7;
            renderer.renderFaceZNeg(block, (double)x, (double)y, (double)z, BlockGrass.getIconSideOverlay());
        }
        

	}
	
	public void renderFaceAOZPos(IBlockAccess world, RenderBlocks renderer, Block block, int x, int y, int z)
	{
		this.renderFaceAOZPos(world, renderer, block, x, y, z, null);
	}
	
	public void renderFaceAOZPos(IBlockAccess world, RenderBlocks renderer, Block block, int x, int y, int z, IIcon ico)
	{
        int l2 = this.setupColour(world, renderer, block, x, y, z, 3);
        float par5 = (float)(l2 >> 16 & 255) / 255.0F;
        float par6 = (float)(l2 >> 8 & 255) / 255.0F;
        float par7 = (float)(l2 & 255) / 255.0F;

        boolean flag = false;
        float f3 = 0.0F;
        float f4 = 0.0F;
        float f5 = 0.0F;
        float f6 = 0.0F;
        boolean flag1 = true;
        int l = block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z);
        Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(983055);
        
        
        if (renderer.getBlockIcon(block).getIconName().equals("grasstop"))
        {
            flag1 = false;
        }
        else if (renderer.hasOverrideBlockTexture())
        {
            flag1 = false;
        }
        

        boolean flag2;
        boolean flag3;
        boolean flag4;
        boolean flag5;
        float f7;
        int i1;
        float f8;
        float f9;
        float f10;
        float f11;
        int j1;
        int k1;
        int l1;
        int i2;
        IIcon icon;
        
        if (renderer.renderAllFaces || block.shouldSideBeRendered(world, x, y, z + 1, 3))
        {
            if (renderer.renderMaxZ >= 1.0D)
            {
                ++z;
            }

            renderer.aoLightValueScratchXZNP = renderer.blockAccess.getBlock( x - 1, y, z).getAmbientOcclusionLightValue();
            renderer.aoLightValueScratchXZPP = renderer.blockAccess.getBlock( x + 1, y, z).getAmbientOcclusionLightValue();
            renderer.aoLightValueScratchYZNP = renderer.blockAccess.getBlock( x, y - 1, z).getAmbientOcclusionLightValue();
            renderer.aoLightValueScratchYZPP = renderer.blockAccess.getBlock( x, y + 1, z).getAmbientOcclusionLightValue();
            renderer.aoBrightnessXZNP = block.getMixedBrightnessForBlock(world, x - 1, y, z);
            renderer.aoBrightnessXZPP = block.getMixedBrightnessForBlock(world, x + 1, y, z);
            renderer.aoBrightnessYZNP = block.getMixedBrightnessForBlock(world, x, y - 1, z);
            renderer.aoBrightnessYZPP = block.getMixedBrightnessForBlock(world, x, y + 1, z);
            flag3 = renderer.blockAccess.getBlock(x + 1, y, z + 1).getCanBlockGrass();
            flag2 = renderer.blockAccess.getBlock(x - 1, y, z + 1).getCanBlockGrass();
            flag5 = renderer.blockAccess.getBlock(x, y + 1, z + 1).getCanBlockGrass();
            flag4 = renderer.blockAccess.getBlock(x, y - 1, z + 1).getCanBlockGrass();

            if (!flag2 && !flag4)
            {
                renderer.aoLightValueScratchXYZNNP = renderer.aoLightValueScratchXZNP;
                renderer.aoBrightnessXYZNNP = renderer.aoBrightnessXZNP;
            }
            else
            {
                renderer.aoLightValueScratchXYZNNP = renderer.blockAccess.getBlock( x - 1, y - 1, z).getAmbientOcclusionLightValue();
                renderer.aoBrightnessXYZNNP = block.getMixedBrightnessForBlock(world, x - 1, y - 1, z);
            }

            if (!flag2 && !flag5)
            {
                renderer.aoLightValueScratchXYZNPP = renderer.aoLightValueScratchXZNP;
                renderer.aoBrightnessXYZNPP = renderer.aoBrightnessXZNP;
            }
            else
            {
                renderer.aoLightValueScratchXYZNPP = renderer.blockAccess.getBlock( x - 1, y + 1, z).getAmbientOcclusionLightValue();
                renderer.aoBrightnessXYZNPP = block.getMixedBrightnessForBlock(world, x - 1, y + 1, z);
            }

            if (!flag3 && !flag4)
            {
                renderer.aoLightValueScratchXYZPNP = renderer.aoLightValueScratchXZPP;
                renderer.aoBrightnessXYZPNP = renderer.aoBrightnessXZPP;
            }
            else
            {
                renderer.aoLightValueScratchXYZPNP = renderer.blockAccess.getBlock( x + 1, y - 1, z).getAmbientOcclusionLightValue();
                renderer.aoBrightnessXYZPNP = block.getMixedBrightnessForBlock(world, x + 1, y - 1, z);
            }

            if (!flag3 && !flag5)
            {
                renderer.aoLightValueScratchXYZPPP = renderer.aoLightValueScratchXZPP;
                renderer.aoBrightnessXYZPPP = renderer.aoBrightnessXZPP;
            }
            else
            {
                renderer.aoLightValueScratchXYZPPP = renderer.blockAccess.getBlock( x + 1, y + 1, z).getAmbientOcclusionLightValue();
                renderer.aoBrightnessXYZPPP = block.getMixedBrightnessForBlock(world, x + 1, y + 1, z);
            }

            if (renderer.renderMaxZ >= 1.0D)
            {
                --z;
            }

            i1 = l;

            if (renderer.renderMaxZ >= 1.0D || !renderer.blockAccess.getBlock(x, y, z + 1).isOpaqueCube())
            {
                i1 = block.getMixedBrightnessForBlock(world, x, y, z + 1);
            }

            f7 = renderer.blockAccess.getBlock( x, y, z + 1).getAmbientOcclusionLightValue();
            f9 = (renderer.aoLightValueScratchXZNP + renderer.aoLightValueScratchXYZNPP + f7 + renderer.aoLightValueScratchYZPP) / 4.0F;
            f8 = (f7 + renderer.aoLightValueScratchYZPP + renderer.aoLightValueScratchXZPP + renderer.aoLightValueScratchXYZPPP) / 4.0F;
            f11 = (renderer.aoLightValueScratchYZNP + f7 + renderer.aoLightValueScratchXYZPNP + renderer.aoLightValueScratchXZPP) / 4.0F;
            f10 = (renderer.aoLightValueScratchXYZNNP + renderer.aoLightValueScratchXZNP + renderer.aoLightValueScratchYZNP + f7) / 4.0F;
            f3 = (float)((double)f9 * renderer.renderMaxY * (1.0D - renderer.renderMinX) + (double)f8 * renderer.renderMaxY * renderer.renderMinX + (double)f11 * (1.0D - renderer.renderMaxY) * renderer.renderMinX + (double)f10 * (1.0D - renderer.renderMaxY) * (1.0D - renderer.renderMinX));
            f4 = (float)((double)f9 * renderer.renderMinY * (1.0D - renderer.renderMinX) + (double)f8 * renderer.renderMinY * renderer.renderMinX + (double)f11 * (1.0D - renderer.renderMinY) * renderer.renderMinX + (double)f10 * (1.0D - renderer.renderMinY) * (1.0D - renderer.renderMinX));
            f5 = (float)((double)f9 * renderer.renderMinY * (1.0D - renderer.renderMaxX) + (double)f8 * renderer.renderMinY * renderer.renderMaxX + (double)f11 * (1.0D - renderer.renderMinY) * renderer.renderMaxX + (double)f10 * (1.0D - renderer.renderMinY) * (1.0D - renderer.renderMaxX));
            f6 = (float)((double)f9 * renderer.renderMaxY * (1.0D - renderer.renderMaxX) + (double)f8 * renderer.renderMaxY * renderer.renderMaxX + (double)f11 * (1.0D - renderer.renderMaxY) * renderer.renderMaxX + (double)f10 * (1.0D - renderer.renderMaxY) * (1.0D - renderer.renderMaxX));
            k1 = renderer.getAoBrightness(renderer.aoBrightnessXZNP, renderer.aoBrightnessXYZNPP, renderer.aoBrightnessYZPP, i1);
            j1 = renderer.getAoBrightness(renderer.aoBrightnessYZPP, renderer.aoBrightnessXZPP, renderer.aoBrightnessXYZPPP, i1);
            i2 = renderer.getAoBrightness(renderer.aoBrightnessYZNP, renderer.aoBrightnessXYZPNP, renderer.aoBrightnessXZPP, i1);
            l1 = renderer.getAoBrightness(renderer.aoBrightnessXYZNNP, renderer.aoBrightnessXZNP, renderer.aoBrightnessYZNP, i1);
            renderer.brightnessTopLeft = renderer.mixAoBrightness(k1, l1, i2, j1, renderer.renderMaxY * (1.0D - renderer.renderMinX), (1.0D - renderer.renderMaxY) * (1.0D - renderer.renderMinX), (1.0D - renderer.renderMaxY) * renderer.renderMinX, renderer.renderMaxY * renderer.renderMinX);
            renderer.brightnessBottomLeft = renderer.mixAoBrightness(k1, l1, i2, j1, renderer.renderMinY * (1.0D - renderer.renderMinX), (1.0D - renderer.renderMinY) * (1.0D - renderer.renderMinX), (1.0D - renderer.renderMinY) * renderer.renderMinX, renderer.renderMinY * renderer.renderMinX);
            renderer.brightnessBottomRight = renderer.mixAoBrightness(k1, l1, i2, j1, renderer.renderMinY * (1.0D - renderer.renderMaxX), (1.0D - renderer.renderMinY) * (1.0D - renderer.renderMaxX), (1.0D - renderer.renderMinY) * renderer.renderMaxX, renderer.renderMinY * renderer.renderMaxX);
            renderer.brightnessTopRight = renderer.mixAoBrightness(k1, l1, i2, j1, renderer.renderMaxY * (1.0D - renderer.renderMaxX), (1.0D - renderer.renderMaxY) * (1.0D - renderer.renderMaxX), (1.0D - renderer.renderMaxY) * renderer.renderMaxX, renderer.renderMaxY * renderer.renderMaxX);

            if (flag1)
            {
                renderer.colorRedTopLeft = renderer.colorRedBottomLeft = renderer.colorRedBottomRight = renderer.colorRedTopRight = par5 * 0.8F;
                renderer.colorGreenTopLeft = renderer.colorGreenBottomLeft = renderer.colorGreenBottomRight = renderer.colorGreenTopRight = par6 * 0.8F;
                renderer.colorBlueTopLeft = renderer.colorBlueBottomLeft = renderer.colorBlueBottomRight = renderer.colorBlueTopRight = par7 * 0.8F;
            }
            else
            {
                renderer.colorRedTopLeft = renderer.colorRedBottomLeft = renderer.colorRedBottomRight = renderer.colorRedTopRight = 0.8F;
                renderer.colorGreenTopLeft = renderer.colorGreenBottomLeft = renderer.colorGreenBottomRight = renderer.colorGreenTopRight = 0.8F;
                renderer.colorBlueTopLeft = renderer.colorBlueBottomLeft = renderer.colorBlueBottomRight = renderer.colorBlueTopRight = 0.8F;
            }

            renderer.colorRedTopLeft *= f3;
            renderer.colorGreenTopLeft *= f3;
            renderer.colorBlueTopLeft *= f3;
            renderer.colorRedBottomLeft *= f4;
            renderer.colorGreenBottomLeft *= f4;
            renderer.colorBlueBottomLeft *= f4;
            renderer.colorRedBottomRight *= f5;
            renderer.colorGreenBottomRight *= f5;
            renderer.colorBlueBottomRight *= f5;
            renderer.colorRedTopRight *= f6;
            renderer.colorGreenTopRight *= f6;
            renderer.colorBlueTopRight *= f6;

            if(ico != null)
            {
            	icon = ico;
            }
            else
            {
            	icon = renderer.getBlockIcon(block, world, x, y, z, 3);
            }
            
            renderer.renderFaceZPos(block, (double)x, (double)y, (double)z, renderer.getBlockIcon(block, world, x, y, z, 3));

            if (renderer.fancyGrass && icon.getIconName().equals("grassside") && !renderer.hasOverrideBlockTexture())
            {
                renderer.colorRedTopLeft *= par5;
                renderer.colorRedBottomLeft *= par5;
                renderer.colorRedBottomRight *= par5;
                renderer.colorRedTopRight *= par5;
                renderer.colorGreenTopLeft *= par6;
                renderer.colorGreenBottomLeft *= par6;
                renderer.colorGreenBottomRight *= par6;
                renderer.colorGreenTopRight *= par6;
                renderer.colorBlueTopLeft *= par7;
                renderer.colorBlueBottomLeft *= par7;
                renderer.colorBlueBottomRight *= par7;
                renderer.colorBlueTopRight *= par7;
                renderer.renderFaceZPos(block, (double)x, (double)y, (double)z, BlockGrass.getIconSideOverlay());
            }
        }
        
	}
	
	
	public void renderFaceAOYPos(IBlockAccess world, RenderBlocks renderer, Block block, int x, int y, int z)
	{
		this.renderFaceAOYPos(world, renderer, block, x, y, z, null);
	}
	
	public void renderFaceAOYPos(IBlockAccess world, RenderBlocks renderer, Block block, int x, int y, int z, IIcon ico)
	{
        int l2 = this.setupColour(world, renderer, block, x, y, z, 1);
        float par5 = (float)(l2 >> 16 & 255) / 255.0F;
        float par6 = (float)(l2 >> 8 & 255) / 255.0F;
        float par7 = (float)(l2 & 255) / 255.0F;
        
        boolean flag = false;
        float f3 = 0.0F;
        float f4 = 0.0F;
        float f5 = 0.0F;
        float f6 = 0.0F;
        boolean flag1 = true;
        int l = block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z);
        Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(983055);
        
        
        if (renderer.getBlockIcon(block).getIconName().equals("grasstop"))
        {
            flag1 = false;
        }
        else if (renderer.hasOverrideBlockTexture())
        {
            flag1 = false;
        }
        

        boolean flag2;
        boolean flag3;
        boolean flag4;
        boolean flag5;
        float f7;
        int i1;
        
        if (renderer.renderMaxY >= 1.0D)
        {
            ++y;
        }

        renderer.aoBrightnessXYNP = block.getMixedBrightnessForBlock(world, x - 1, y, z);
        renderer.aoBrightnessXYPP = block.getMixedBrightnessForBlock(world, x + 1, y, z);
        renderer.aoBrightnessYZPN = block.getMixedBrightnessForBlock(world, x, y, z - 1);
        renderer.aoBrightnessYZPP = block.getMixedBrightnessForBlock(world, x, y, z + 1);
        renderer.aoLightValueScratchXYNP = renderer.blockAccess.getBlock( x - 1, y, z).getAmbientOcclusionLightValue();
        renderer.aoLightValueScratchXYPP = renderer.blockAccess.getBlock( x + 1, y, z).getAmbientOcclusionLightValue();
        renderer.aoLightValueScratchYZPN = renderer.blockAccess.getBlock( x, y, z - 1).getAmbientOcclusionLightValue();
        renderer.aoLightValueScratchYZPP = renderer.blockAccess.getBlock( x, y, z + 1).getAmbientOcclusionLightValue();
        flag3 = renderer.blockAccess.getBlock(x + 1, y + 1, z).getCanBlockGrass();
        flag2 = renderer.blockAccess.getBlock(x - 1, y + 1, z).getCanBlockGrass();
        flag5 = renderer.blockAccess.getBlock(x, y + 1, z + 1).getCanBlockGrass();
        flag4 = renderer.blockAccess.getBlock(x, y + 1, z - 1).getCanBlockGrass();

        if (!flag4 && !flag2)
        {
            renderer.aoLightValueScratchXYZNPN = renderer.aoLightValueScratchXYNP;
            renderer.aoBrightnessXYZNPN = renderer.aoBrightnessXYNP;
        }
        else
        {
            renderer.aoLightValueScratchXYZNPN = renderer.blockAccess.getBlock( x - 1, y, z - 1).getAmbientOcclusionLightValue();
            renderer.aoBrightnessXYZNPN = block.getMixedBrightnessForBlock(world, x - 1, y, z - 1);
        }

        if (!flag4 && !flag3)
        {
            renderer.aoLightValueScratchXYZPPN = renderer.aoLightValueScratchXYPP;
            renderer.aoBrightnessXYZPPN = renderer.aoBrightnessXYPP;
        }
        else
        {
            renderer.aoLightValueScratchXYZPPN = renderer.blockAccess.getBlock( x + 1, y, z - 1).getAmbientOcclusionLightValue();
            renderer.aoBrightnessXYZPPN = block.getMixedBrightnessForBlock(world, x + 1, y, z - 1);
        }

        if (!flag5 && !flag2)
        {
            renderer.aoLightValueScratchXYZNPP = renderer.aoLightValueScratchXYNP;
            renderer.aoBrightnessXYZNPP = renderer.aoBrightnessXYNP;
        }
        else
        {
            renderer.aoLightValueScratchXYZNPP = renderer.blockAccess.getBlock( x - 1, y, z + 1).getAmbientOcclusionLightValue();
            renderer.aoBrightnessXYZNPP = block.getMixedBrightnessForBlock(world, x - 1, y, z + 1);
        }

        if (!flag5 && !flag3)
        {
            renderer.aoLightValueScratchXYZPPP = renderer.aoLightValueScratchXYPP;
            renderer.aoBrightnessXYZPPP = renderer.aoBrightnessXYPP;
        }
        else
        {
            renderer.aoLightValueScratchXYZPPP = renderer.blockAccess.getBlock( x + 1, y, z + 1).getAmbientOcclusionLightValue();
            renderer.aoBrightnessXYZPPP = block.getMixedBrightnessForBlock(world, x + 1, y, z + 1);
        }

        if (renderer.renderMaxY >= 1.0D)
        {
            --y;
        }

        i1 = l;

        if (renderer.renderMaxY >= 1.0D || !renderer.blockAccess.getBlock(x, y + 1, z).isOpaqueCube())
        {
            i1 = block.getMixedBrightnessForBlock(world, x, y + 1, z);
        }

        f7 = renderer.blockAccess.getBlock( x, y + 1, z).getAmbientOcclusionLightValue();
        f6 = (renderer.aoLightValueScratchXYZNPP + renderer.aoLightValueScratchXYNP + renderer.aoLightValueScratchYZPP + f7) / 4.0F;
        f3 = (renderer.aoLightValueScratchYZPP + f7 + renderer.aoLightValueScratchXYZPPP + renderer.aoLightValueScratchXYPP) / 4.0F;
        f4 = (f7 + renderer.aoLightValueScratchYZPN + renderer.aoLightValueScratchXYPP + renderer.aoLightValueScratchXYZPPN) / 4.0F;
        f5 = (renderer.aoLightValueScratchXYNP + renderer.aoLightValueScratchXYZNPN + f7 + renderer.aoLightValueScratchYZPN) / 4.0F;
        renderer.brightnessTopRight = renderer.getAoBrightness(renderer.aoBrightnessXYZNPP, renderer.aoBrightnessXYNP, renderer.aoBrightnessYZPP, i1);
        renderer.brightnessTopLeft = renderer.getAoBrightness(renderer.aoBrightnessYZPP, renderer.aoBrightnessXYZPPP, renderer.aoBrightnessXYPP, i1);
        renderer.brightnessBottomLeft = renderer.getAoBrightness(renderer.aoBrightnessYZPN, renderer.aoBrightnessXYPP, renderer.aoBrightnessXYZPPN, i1);
        renderer.brightnessBottomRight = renderer.getAoBrightness(renderer.aoBrightnessXYNP, renderer.aoBrightnessXYZNPN, renderer.aoBrightnessYZPN, i1);
        renderer.colorRedTopLeft = renderer.colorRedBottomLeft = renderer.colorRedBottomRight = renderer.colorRedTopRight = par5;
        renderer.colorGreenTopLeft = renderer.colorGreenBottomLeft = renderer.colorGreenBottomRight = renderer.colorGreenTopRight = par6;
        renderer.colorBlueTopLeft = renderer.colorBlueBottomLeft = renderer.colorBlueBottomRight = renderer.colorBlueTopRight = par7;
        renderer.colorRedTopLeft *= f3;
        renderer.colorGreenTopLeft *= f3;
        renderer.colorBlueTopLeft *= f3;
        renderer.colorRedBottomLeft *= f4;
        renderer.colorGreenBottomLeft *= f4;
        renderer.colorBlueBottomLeft *= f4;
        renderer.colorRedBottomRight *= f5;
        renderer.colorGreenBottomRight *= f5;
        renderer.colorBlueBottomRight *= f5;
        renderer.colorRedTopRight *= f6;
        renderer.colorGreenTopRight *= f6;
        renderer.colorBlueTopRight *= f6;
        
        IIcon icon;
        
        if(ico != null)
        {
        	icon = ico;
        }
        else
        {
        	icon = renderer.getBlockIcon(block, world, x, y, z, 1);
        }
        
        renderer.renderFaceYPos(block, (double)x, (double)y, (double)z, icon);
        
	}
	
	
	public void renderFaceAOYNeg(IBlockAccess world, RenderBlocks renderer, Block block, int x, int y, int z)
	{
		this.renderFaceAOYNeg(world, renderer, block, x, y, z, null);
	}
	
	public void renderFaceAOYNeg(IBlockAccess world, RenderBlocks renderer, Block block, int x, int y, int z, IIcon ico)
	{
        int l2 = this.setupColour(world, renderer, block, x, y, z, 0);
        float r = (float)(l2 >> 16 & 255) / 255.0F;
        float g = (float)(l2 >> 8 & 255) / 255.0F;
        float b = (float)(l2 & 255) / 255.0F;
        
        boolean flag = false;
        float f3 = 0.0F;
        float f4 = 0.0F;
        float f5 = 0.0F;
        float f6 = 0.0F;
        boolean flag1 = true;
        int l = block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z);
        Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(983055);
        
        
        if (renderer.getBlockIcon(block).getIconName().equals("grasstop"))
        {
            flag1 = false;
        }
        else if (renderer.hasOverrideBlockTexture())
        {
            flag1 = false;
        }
        

        boolean flag2;
        boolean flag3;
        boolean flag4;
        boolean flag5;
        float f7;
        int i1;

        if (renderer.renderAllFaces || block.shouldSideBeRendered(renderer.blockAccess, x, y - 1, z, 0))
        {
            if (renderer.renderMinY <= 0.0D)
            {
                --y;
            }

            renderer.aoBrightnessXYNN = block.getMixedBrightnessForBlock(renderer.blockAccess, x - 1, y, z);
            renderer.aoBrightnessYZNN = block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z - 1);
            renderer.aoBrightnessYZNP = block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z + 1);
            renderer.aoBrightnessXYPN = block.getMixedBrightnessForBlock(renderer.blockAccess, x + 1, y, z);
            renderer.aoLightValueScratchXYNN = renderer.blockAccess.getBlock( x - 1, y, z).getAmbientOcclusionLightValue();
            renderer.aoLightValueScratchYZNN = renderer.blockAccess.getBlock( x, y, z - 1).getAmbientOcclusionLightValue();
            renderer.aoLightValueScratchYZNP = renderer.blockAccess.getBlock( x, y, z + 1).getAmbientOcclusionLightValue();
            renderer.aoLightValueScratchXYPN = renderer.blockAccess.getBlock( x + 1, y, z).getAmbientOcclusionLightValue();
            flag3 = renderer.blockAccess.getBlock(x + 1, y - 1, z).getCanBlockGrass();
            flag2 = renderer.blockAccess.getBlock(x - 1, y - 1, z).getCanBlockGrass();
            flag5 = renderer.blockAccess.getBlock(x, y - 1, z + 1).getCanBlockGrass();
            flag4 = renderer.blockAccess.getBlock(x, y - 1, z - 1).getCanBlockGrass();

            if (!flag4 && !flag2)
            {
                renderer.aoLightValueScratchXYZNNN = renderer.aoLightValueScratchXYNN;
                renderer.aoBrightnessXYZNNN = renderer.aoBrightnessXYNN;
            }
            else
            {
                renderer.aoLightValueScratchXYZNNN = renderer.blockAccess.getBlock( x - 1, y, z - 1).getAmbientOcclusionLightValue();
                renderer.aoBrightnessXYZNNN = block.getMixedBrightnessForBlock(renderer.blockAccess, x - 1, y, z - 1);
            }

            if (!flag5 && !flag2)
            {
                renderer.aoLightValueScratchXYZNNP = renderer.aoLightValueScratchXYNN;
                renderer.aoBrightnessXYZNNP = renderer.aoBrightnessXYNN;
            }
            else
            {
                renderer.aoLightValueScratchXYZNNP = renderer.blockAccess.getBlock( x - 1, y, z + 1).getAmbientOcclusionLightValue();
                renderer.aoBrightnessXYZNNP = block.getMixedBrightnessForBlock(renderer.blockAccess, x - 1, y, z + 1);
            }

            if (!flag4 && !flag3)
            {
                renderer.aoLightValueScratchXYZPNN = renderer.aoLightValueScratchXYPN;
                renderer.aoBrightnessXYZPNN = renderer.aoBrightnessXYPN;
            }
            else
            {
                renderer.aoLightValueScratchXYZPNN = renderer.blockAccess.getBlock( x + 1, y, z - 1).getAmbientOcclusionLightValue();
                renderer.aoBrightnessXYZPNN = block.getMixedBrightnessForBlock(renderer.blockAccess, x + 1, y, z - 1);
            }

            if (!flag5 && !flag3)
            {
                renderer.aoLightValueScratchXYZPNP = renderer.aoLightValueScratchXYPN;
                renderer.aoBrightnessXYZPNP = renderer.aoBrightnessXYPN;
            }
            else
            {
                renderer.aoLightValueScratchXYZPNP = renderer.blockAccess.getBlock( x + 1, y, z + 1).getAmbientOcclusionLightValue();
                renderer.aoBrightnessXYZPNP = block.getMixedBrightnessForBlock(renderer.blockAccess, x + 1, y, z + 1);
            }

            if (renderer.renderMinY <= 0.0D)
            {
                ++y;
            }

            i1 = l;

            if (renderer.renderMinY <= 0.0D || !renderer.blockAccess.getBlock(x, y - 1, z).isOpaqueCube())
            {
                i1 = block.getMixedBrightnessForBlock(renderer.blockAccess, x, y - 1, z);
            }

            f7 = renderer.blockAccess.getBlock( x, y - 1, z).getAmbientOcclusionLightValue();
            f3 = (renderer.aoLightValueScratchXYZNNP + renderer.aoLightValueScratchXYNN + renderer.aoLightValueScratchYZNP + f7) / 4.0F;
            f6 = (renderer.aoLightValueScratchYZNP + f7 + renderer.aoLightValueScratchXYZPNP + renderer.aoLightValueScratchXYPN) / 4.0F;
            f5 = (f7 + renderer.aoLightValueScratchYZNN + renderer.aoLightValueScratchXYPN + renderer.aoLightValueScratchXYZPNN) / 4.0F;
            f4 = (renderer.aoLightValueScratchXYNN + renderer.aoLightValueScratchXYZNNN + f7 + renderer.aoLightValueScratchYZNN) / 4.0F;
            renderer.brightnessTopLeft = renderer.getAoBrightness(renderer.aoBrightnessXYZNNP, renderer.aoBrightnessXYNN, renderer.aoBrightnessYZNP, i1);
            renderer.brightnessTopRight = renderer.getAoBrightness(renderer.aoBrightnessYZNP, renderer.aoBrightnessXYZPNP, renderer.aoBrightnessXYPN, i1);
            renderer.brightnessBottomRight = renderer.getAoBrightness(renderer.aoBrightnessYZNN, renderer.aoBrightnessXYPN, renderer.aoBrightnessXYZPNN, i1);
            renderer.brightnessBottomLeft = renderer.getAoBrightness(renderer.aoBrightnessXYNN, renderer.aoBrightnessXYZNNN, renderer.aoBrightnessYZNN, i1);

            if (flag1)
            {
                renderer.colorRedTopLeft = renderer.colorRedBottomLeft = renderer.colorRedBottomRight = renderer.colorRedTopRight = r * 0.5F;
                renderer.colorGreenTopLeft = renderer.colorGreenBottomLeft = renderer.colorGreenBottomRight = renderer.colorGreenTopRight = g * 0.5F;
                renderer.colorBlueTopLeft = renderer.colorBlueBottomLeft = renderer.colorBlueBottomRight = renderer.colorBlueTopRight = b * 0.5F;
            }
            else
            {
                renderer.colorRedTopLeft = renderer.colorRedBottomLeft = renderer.colorRedBottomRight = renderer.colorRedTopRight = 0.5F;
                renderer.colorGreenTopLeft = renderer.colorGreenBottomLeft = renderer.colorGreenBottomRight = renderer.colorGreenTopRight = 0.5F;
                renderer.colorBlueTopLeft = renderer.colorBlueBottomLeft = renderer.colorBlueBottomRight = renderer.colorBlueTopRight = 0.5F;
            }

            renderer.colorRedTopLeft *= f3;
            renderer.colorGreenTopLeft *= f3;
            renderer.colorBlueTopLeft *= f3;
            renderer.colorRedBottomLeft *= f4;
            renderer.colorGreenBottomLeft *= f4;
            renderer.colorBlueBottomLeft *= f4;
            renderer.colorRedBottomRight *= f5;
            renderer.colorGreenBottomRight *= f5;
            renderer.colorBlueBottomRight *= f5;
            renderer.colorRedTopRight *= f6;
            renderer.colorGreenTopRight *= f6;
            renderer.colorBlueTopRight *= f6;
            
            IIcon icon;
            
            if(ico != null)
            {
            	icon = ico;
            }
            else
            {
            	icon = renderer.getBlockIcon(block, world, x, y, z, 0);
            }
            
	        
            renderer.renderFaceYNeg(block, (double)x, (double)y, (double)z, icon);
        }
        
	}
	
	public void tessellateInventoryBlock(RenderBlocks renderer, Block block, int meta, int side, PointSet bounds)
	{
		if(bounds != null)
		{
			this.setRenderBounds(renderer, bounds);
		}
		
		this.tessellateInventoryBlock(renderer, block, side, meta);
	}
	
	public void tessellateInventoryBlock(RenderBlocks renderer, Block block, int side, int meta)
	{
		tessellator.setNormal(0.0F, -1.0F, 0.0F);
		renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(side, meta));
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(side, meta));
		tessellator.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(side, meta));
		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(side, meta));
		tessellator.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(side, meta));
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(side, meta));
	}
	
	public void tessellateInventoryBlock(RenderBlocks renderer, Block block, int meta, PointSet bounds)
	{
		if(bounds != null)
		{
			this.setRenderBounds(renderer, bounds);
		}
		
		this.tessellateInventoryBlock(renderer, block, meta);
	}
	
	public void tessellateInventoryBlock(RenderBlocks renderer, Block block, int meta)
	{
		tessellator.setNormal(0.0F, -1.0F, 0.0F);
		renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, meta));
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(1, meta));
		tessellator.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(2, meta));
		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(3, meta));
		tessellator.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(4, meta));
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(5, meta));
	}
	
	public void setGL11Scale(double scale)
	{
		GL11.glScaled(scale, scale, scale);
	}
	
	public void resetGL11Scale( )
	{
		GL11.glScaled(1.0, 1.0, 1.0);
	}
	
    public static void renderItemAsBlock(Tessellator tess, ItemStack istack, boolean effects, float thickness)
    {

    	Item item = istack.getItem();
    	IIcon icon = item.getIcon(istack, 0);
    	
    	//pass 0
    	renderItemAsBlockSimple(tess, icon, thickness);
    	
		if(item.requiresMultipleRenderPasses())
		{
			IIcon icon2 = item.getIcon(istack, 0);
            for(int x = 1; x < istack.getItem().getRenderPasses(istack.getItemDamage()); x++)
            {
            	icon2 = item.getIcon(istack, x);
            	renderItemAsBlockSimple(tess, icon2, thickness);
            }		
		}
		
		
		
        for(int x = 0; x < istack.getItem().getRenderPasses(istack.getItemDamage()); x++)
        {
			if(istack.hasEffect(x))
			{
				renderItemEffectRelative(tess);
			}
        }
    }
    
    public static void renderItemAsBlockSimple(Tessellator tess, IIcon ico, float thickness)
    {
    	if(ico != null)
    	{
    		ItemRenderer.renderItemIn2D(tess, ico.getMinU(), ico.getMinV(), ico.getMaxU(), ico.getMaxV(), ico.getIconWidth(), ico.getIconHeight(), thickness);
    	}
    }
    
    public static void renderItemIn2D(Tessellator tess, float minu, float minv, float maxu, float maxv, int iconWidth, int iconHeight, float thickness)
    {
    	//Z
        tess.startDrawingQuads();
        tess.setColorOpaque_F(0.8F, 0.8F, 0.8F);
        tess.setNormal(0.0F, 0.0F, 1.0F);
        tess.addVertexWithUV(0.0D, 0.0D, 0.0D, (double)minu, (double)maxv);
        tess.addVertexWithUV(1.0D, 0.0D, 0.0D, (double)maxu, (double)maxv);
        tess.addVertexWithUV(1.0D, 1.0D, 0.0D, (double)maxu, (double)minv);
        tess.addVertexWithUV(0.0D, 1.0D, 0.0D, (double)minu, (double)minv);
        tess.draw();
        
        
        tess.startDrawingQuads();
        tess.setColorOpaque_F(0.8F, 0.8F, 0.8F);
        tess.setNormal(0.0F, 0.0F, -1.0F);
        tess.addVertexWithUV(0.0D, 1.0D, (double)(0.0F - thickness), (double)minu, (double)minv);
        tess.addVertexWithUV(1.0D, 1.0D, (double)(0.0F - thickness), (double)maxu, (double)minv);
        tess.addVertexWithUV(1.0D, 0.0D, (double)(0.0F - thickness), (double)maxu, (double)maxv);
        tess.addVertexWithUV(0.0D, 0.0D, (double)(0.0F - thickness), (double)minu, (double)maxv);
        tess.draw();
        float f5 = 0.5F * (minu - maxu) / (float)iconWidth;
        float f6 = 0.5F * (maxv - minv) / (float)iconHeight;
        tess.startDrawingQuads();
        tess.setNormal(-1.0F, 0.0F, 0.0F);
        int k;
        float f7;
        float f8;
        
        //WEST -X
        tess.setColorOpaque_F(0.6F, 0.6F, 0.6F);
        for (k = 0; k < iconWidth; ++k)
        {
            f7 = (float)k / (float)iconWidth;
            f8 = minu + (maxu - minu) * f7 - f5;
            tess.addVertexWithUV((double)f7, 0.0D, (double)(0.0F - thickness), (double)f8, (double)maxv);
            tess.addVertexWithUV((double)f7, 0.0D, 0.0D, (double)f8, (double)maxv);
            tess.addVertexWithUV((double)f7, 1.0D, 0.0D, (double)f8, (double)minv);
            tess.addVertexWithUV((double)f7, 1.0D, (double)(0.0F - thickness), (double)f8, (double)minv);
        }

        tess.draw();
        tess.startDrawingQuads();
        
        //EAST +X
        tess.setNormal(1.0F, 0.0F, 0.0F);
        tess.setColorOpaque_F(0.6F, 0.6F, 0.6F);
        float f9;

        for (k = 0; k < iconWidth; ++k)
        {
            f7 = (float)k / (float)iconWidth;
            f8 = minu + (maxu - minu) * f7 - f5;
            f9 = f7 + 1.0F / (float)iconWidth;
            tess.addVertexWithUV((double)f9, 1.0D, (double)(0.0F - thickness), (double)f8, (double)minv);
            tess.addVertexWithUV((double)f9, 1.0D, 0.0D, (double)f8, (double)minv);
            tess.addVertexWithUV((double)f9, 0.0D, 0.0D, (double)f8, (double)maxv);
            tess.addVertexWithUV((double)f9, 0.0D, (double)(0.0F - thickness), (double)f8, (double)maxv);
        }

        tess.draw();
        
        //UP? +Y?
        tess.startDrawingQuads();
        tess.setColorOpaque_F(1.0F, 1.0F, 1.0F);
        tess.setNormal(0.0F, 1.0F, 0.0F);

        for (k = 0; k < iconHeight; ++k)
        {
            f7 = (float)k / (float)iconHeight;
            f8 = maxv + (minv - maxv) * f7 - f6;
            f9 = f7 + 1.0F / (float)iconHeight;
            tess.addVertexWithUV(0.0D, (double)f9, 0.0D, (double)minu, (double)f8);
            tess.addVertexWithUV(1.0D, (double)f9, 0.0D, (double)maxu, (double)f8);
            tess.addVertexWithUV(1.0D, (double)f9, (double)(0.0F - thickness), (double)maxu, (double)f8);
            tess.addVertexWithUV(0.0D, (double)f9, (double)(0.0F - thickness), (double)minu, (double)f8);
        }

        tess.draw();
        
        //DOWN? -Y?
        tess.startDrawingQuads();
        tess.setColorOpaque_F(0.5F, 0.5F, 0.5F);
        tess.setNormal(0.0F, -1.0F, 0.0F);

        for (k = 0; k < iconHeight; ++k)
        {
            f7 = (float)k / (float)iconHeight;
            f8 = maxv + (minv - maxv) * f7 - f6;
            tess.addVertexWithUV(1.0D, (double)f7, 0.0D, (double)maxu, (double)f8);
            tess.addVertexWithUV(0.0D, (double)f7, 0.0D, (double)minu, (double)f8);
            tess.addVertexWithUV(0.0D, (double)f7, (double)(0.0F - thickness), (double)minu, (double)f8);
            tess.addVertexWithUV(1.0D, (double)f7, (double)(0.0F - thickness), (double)maxu, (double)f8);
        }

        tess.draw();
    }
	
	public static void renderItemEffectRelative(Tessellator tessellator)
	{
		tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
		tessellator.setBrightness(1000);
        GL11.glDepthFunc(GL11.GL_EQUAL);
        GL11.glDisable(GL11.GL_LIGHTING);
        textureManager.bindTexture(RES_ITEM_GLINT);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
        float f7 = 0.76F;
        GL11.glColor4f(0.5F * f7, 0.25F * f7, 0.8F * f7, 1.0F);
        GL11.glMatrixMode(GL11.GL_TEXTURE);
        GL11.glPushMatrix();
        float f8 = 0.125F;
        GL11.glScalef(f8, f8, f8);
        float f9 = (float)(Minecraft.getSystemTime() % 3000L) / 3000.0F * 8.0F;
        GL11.glTranslatef(f9, 0.0F, 0.0F);
        GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
        ItemRenderer.renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glScalef(f8, f8, f8);
        f9 = (float)(Minecraft.getSystemTime() % 4873L) / 4873.0F * 8.0F;
        GL11.glTranslatef(-f9, 0.0F, 0.0F);
        GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
        ItemRenderer.renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
        GL11.glPopMatrix();
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDepthFunc(GL11.GL_LEQUAL);
        
        textureManager.bindTexture(TextureMap.locationItemsTexture);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}
	
	public void translationNorth(double[] coords)
	{
		double t = coords[0];
		coords[0] = coords[2];
		coords[2] = t;
		
		t = coords[3];
		coords[3] = coords[5];
		coords[5] = t;
		
 	}
	
	public void recalculateTESRLighting(TileEntity t)
	{
        int l = t.getWorldObj().getLightBrightnessForSkyBlocks(t.xCoord, t.yCoord, t.zCoord, 0);
        int i1 = l % 65536;
        int j1 = l / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)i1, (float)j1);
	}
	
    public void setRenderBounds(RenderBlocks renderer, BoundSet points)
    {
    	renderer.setRenderBounds(points.x1, points.y1, points.z1, points.x2, points.y2, points.z2);
    }
    
    public PointSet translate(ForgeDirection from, ForgeDirection to, final PointSet points)
    {
    	PointSet ps = new PointSet(points);
    	return translator.translate(from, to, ps);
    }
    
    //This assumes a natural direction of NORTH
    public PointSet centerForInventoryRender(PointSet ps, float shift)
    {
    	ps.z1 = ps.z1 + shift;
    	ps.z2 = ps.z2 + shift;
    	
    	return ps;
    }
    
    //This is just a wrapper method.
    //All tessellator calls, translations, rotations and scale are up to you to implement!
    public static void renderItemFlatInWorld(ItemStack istack)
    {
    	renderItem.renderItemAndEffectIntoGUI(istack.getItem().getFontRenderer(istack), textureManager, istack, 0, 0);
    }
    
    //This is just a wrapper method.
    //All tessellator calls, translations, rotations and scale are up to you to implement!
    public static void renderTextInWorld(String text, int xoffset, int yoffset, int colour, boolean dropShadow)
    {
    	fontRenderer.drawString(text, xoffset, yoffset, colour, dropShadow);
    }
    
    //This is just a wrapper method.
    //All tessellator calls, translations, rotations and scale are up to you to implement!
    public static void renderTextInWorldCentered(String text, int xoffset, int yoffset, int colour, boolean dropShadow, float parentWidth)
    {
    	int length = -(fontRenderer.getStringWidth(text) / 2);
    	length = length + (int)((parentWidth / 2.0F));
    	
    	fontRenderer.drawString(text, length + xoffset, yoffset, colour, dropShadow);
    }
    
    //these functions are pass throughs to the renderer functions, but setup the lighting and colour for you without needing a whole block render
    public void renderFaceYNeg(IBlockAccess world, RenderBlocks renderer, Block block, int x, int y, int z, IIcon icon)
    {
        TileEntityDiscreteBlock tile = (TileEntityDiscreteBlock)world.getTileEntity(x, y, z);
        Block sourceBlock = tile.getTextureSourceBlock(0);
        
        int blockColour = sourceBlock.colorMultiplier(world, x, y, z);    
        int brightness = block.getMixedBrightnessForBlock(world, x, y, z);
        float[] rgb = this.colourMultiplerToRGBWithLight(blockColour, 0);
        
        if(icon == null)
        {
        	icon = renderer.getBlockIcon(block, world, x, y, z, 0);
        }

        if (renderer.renderAllFaces || block.shouldSideBeRendered(world, x, y - 1, z, 0))
        {
            tessellator.setBrightness(renderer.renderMinY > 0.0D ? brightness : block.getMixedBrightnessForBlock(world, x, y - 1, z));
            tessellator.setColorOpaque_F(rgb[0], rgb[1], rgb[2]);
            renderer.renderFaceYNeg(block, (double)x, (double)y, (double)z, icon);
        }
    }
    
    public void renderFaceYPos(IBlockAccess world, RenderBlocks renderer, Block block, int x, int y, int z, IIcon icon)
    {
        TileEntityDiscreteBlock tile = (TileEntityDiscreteBlock)world.getTileEntity(x, y, z);
        Block sourceBlock = tile.getTextureSourceBlock(1);
        
        int blockColour = sourceBlock.colorMultiplier(world, x, y, z);    
        int brightness = block.getMixedBrightnessForBlock(world, x, y, z);
        float[] rgb = this.colourMultiplerToRGBWithLight(blockColour, 1);
        
        if(icon == null)
        {
        	icon = renderer.getBlockIcon(block, world, x, y, z, 1);
        }
        
        if (renderer.renderAllFaces || block.shouldSideBeRendered(world, x, y + 1, z, 1))
        {
            tessellator.setBrightness(renderer.renderMaxY > 0.0D ? brightness : block.getMixedBrightnessForBlock(world, x, y + 1, z));
            tessellator.setColorOpaque_F(rgb[0], rgb[1], rgb[2]);
            renderer.renderFaceYPos(block, (double)x, (double)y, (double)z, icon);
        }
    }
    
    public void renderFaceZNeg(IBlockAccess world, RenderBlocks renderer, Block block, int x, int y, int z, IIcon icon)
    {
        TileEntityDiscreteBlock tile = (TileEntityDiscreteBlock)world.getTileEntity(x, y, z);
        Block sourceBlock = tile.getTextureSourceBlock(2);
        
        int blockColour = sourceBlock.colorMultiplier(world, x, y, z);    
        int brightness = block.getMixedBrightnessForBlock(world, x, y, z);
        
        float[] rgb = this.colourMultiplerToRGBWithLight(blockColour, 2);

        if(icon == null)
        {
        	icon = renderer.getBlockIcon(block, world, x, y, z, 2);
        }

        if (renderer.renderAllFaces || block.shouldSideBeRendered(renderer.blockAccess, x, y, z - 1, 2))
        {
            tessellator.setBrightness(renderer.renderMinZ > 0.0D ? brightness : block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z - 1));
            tessellator.setColorOpaque_F(rgb[0], rgb[1], rgb[2]);
            renderer.renderFaceZNeg(block, (double)x, (double)y, (double)z, icon);
        }
    }
    
    public void renderFaceZPos(IBlockAccess world, RenderBlocks renderer, Block block, int x, int y, int z, IIcon icon)
    {
        TileEntityDiscreteBlock tile = (TileEntityDiscreteBlock)world.getTileEntity(x, y, z);
        Block sourceBlock = tile.getTextureSourceBlock(3);
        
        int blockColour = sourceBlock.colorMultiplier(world, x, y, z);    
        int brightness = block.getMixedBrightnessForBlock(world, x, y, z);
        
        float[] rgb = this.colourMultiplerToRGBWithLight(blockColour, 3);

        if(icon == null)
        {
        	icon = renderer.getBlockIcon(block, world, x, y, z, 3);
        }

        if (renderer.renderAllFaces || block.shouldSideBeRendered(renderer.blockAccess, x, y, z + 1, 3))
        {
            tessellator.setBrightness(renderer.renderMaxZ > 0.0D ? brightness : block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z + 1));
            tessellator.setColorOpaque_F(rgb[0], rgb[1], rgb[2]);
            renderer.renderFaceZPos(block, (double)x, (double)y, (double)z, icon);
        }	
    }
    
    public void renderFaceXNeg(IBlockAccess world, RenderBlocks renderer, Block block, int x, int y, int z, IIcon icon)
    {
        TileEntityDiscreteBlock tile = (TileEntityDiscreteBlock)world.getTileEntity(x, y, z);
        Block sourceBlock = tile.getTextureSourceBlock(4);
        
        int blockColour = sourceBlock.colorMultiplier(world, x, y, z);    
        int brightness = block.getMixedBrightnessForBlock(world, x, y, z);
        
        float[] rgb = this.colourMultiplerToRGBWithLight(blockColour, 4);

        if(icon == null)
        {
        	icon = renderer.getBlockIcon(block, world, x, y, z, 4);
        }

        if (renderer.renderAllFaces || block.shouldSideBeRendered(renderer.blockAccess, x - 1, y, z, 4))
        {
            tessellator.setBrightness(renderer.renderMinX > 0.0D ? brightness : block.getMixedBrightnessForBlock(renderer.blockAccess, x - 1, y, z));
            tessellator.setColorOpaque_F(rgb[0], rgb[1], rgb[2]);
            renderer.renderFaceXNeg(block, (double)x, (double)y, (double)z, icon);
        }	
    }
    
    public void renderFaceXPos(IBlockAccess world, RenderBlocks renderer, Block block, int x, int y, int z, IIcon icon)
    {
        TileEntityDiscreteBlock tile = (TileEntityDiscreteBlock)world.getTileEntity(x, y, z);
        Block sourceBlock = tile.getTextureSourceBlock(5);
        
        int blockColour = sourceBlock.colorMultiplier(world, x, y, z);      
        int brightness = block.getMixedBrightnessForBlock(world, x, y, z);
        
        float[] rgb = this.colourMultiplerToRGBWithLight(blockColour, 5);
        
        if(icon == null)
        {
        	icon = renderer.getBlockIcon(block, world, x, y, z, 5);
        }

        if (renderer.renderAllFaces || block.shouldSideBeRendered(renderer.blockAccess, x + 1, y, z, 5))
        {
            tessellator.setBrightness(renderer.renderMaxX > 0.0D ? brightness : block.getMixedBrightnessForBlock(renderer.blockAccess, x + 1, y, z));
            tessellator.setColorOpaque_F(rgb[0], rgb[1], rgb[2]);
            renderer.renderFaceXPos(block, (double)x, (double)y, (double)z, icon);
        }	
    }
    
    private float[] colourMultiplerToRGB(int colour)
    {
        float r = (float)(colour >> 16 & 255) / 255.0F;
        float g = (float)(colour >> 8 & 255) / 255.0F;
        float b = (float)(colour & 255) / 255.0F;
        
        return new float[]{r,g,b};
    }
    
    private float[] colourMultiplerToRGBWithLight(int colour, int side)
    {
    	float[] a = this.colourMultiplerToRGB(colour);
    	
    	for(int i = 0; i < a.length; i++)
    	{
    		a[i] = a[i] * this.getSideLightMultiplier(side);
    	}
    	
    	return a;
    }
    
    
    //returns the simple lighting light multiplier for a given side
    private float getSideLightMultiplier(int side)
    {
    	
    	switch(side)
    	{
    		case 0:
    			return 0.5F;
    		
    		case 1:
    			return 1.0F;
    			
    		case 2:
    			return 0.8F;
    		
    		case 3:
    			return 0.8F;
    		
    		case 4:
    			return 0.6F;
    		
    		case 5:
    			return 0.6F;
    	}
    	
    	return 1.0F;
    }

}
