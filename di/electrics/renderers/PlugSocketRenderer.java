package kappafox.di.electrics.renderers;

import kappafox.di.DiscreteIndustry;
import kappafox.di.base.DiscreteRenderHelper;
import kappafox.di.electrics.tileentities.TileEntityDiscreteCable;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class PlugSocketRenderer implements ISimpleBlockRenderingHandler
{
	
	private int renderID;
	private IIcon icon;
	private IIcon socket;
	private ResourceLocation[] socketTextures = new ResourceLocation[6];
	private static final ResourceLocation defaultTexture = new ResourceLocation("/terrain.png");

	
	public PlugSocketRenderer(int rID_)
	{
		renderID = rID_;
		socketTextures[0] = new ResourceLocation("discreteindustry", "textures/blocks/emptyPlugSocket.png");
		socketTextures[1] = new ResourceLocation("discreteindustry", "textures/blocks/tinPlugSocket.png");
		socketTextures[2] = new ResourceLocation("discreteindustry", "textures/blocks/copperPlugSocket.png");
		socketTextures[3] = new ResourceLocation("discreteindustry", "textures/blocks/goldPlugSocket.png");
		socketTextures[4] = new ResourceLocation("discreteindustry", "textures/blocks/glassPlugSocket.png");
		socketTextures[5] = new ResourceLocation("discreteindustry", "textures/blocks/ironPlugSocket.png");
	}
	

	@Override
	@SideOnly(Side.CLIENT)
	public void renderInventoryBlock(Block block, int metadata_, int modelID_, RenderBlocks renderblocks)
	{		
		Tessellator tessellator = Tessellator.instance;
		
		block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		renderblocks.setRenderBoundsFromBlock(block);
		
		//shifts the dimensions to show better in hand
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, -1.0F, 0.0F);
		renderblocks.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, metadata_));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		renderblocks.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, metadata_));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, -1.0F);
		renderblocks.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, metadata_));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		renderblocks.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, metadata_));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(-1.0F, 0.0F, 0.0F);
		renderblocks.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, metadata_));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		renderblocks.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, metadata_));
		tessellator.draw();
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		
		block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		renderblocks.setRenderBoundsFromBlock(block);
		
		
	}



	@Override
	@SideOnly(Side.CLIENT)
	public boolean renderWorldBlock(IBlockAccess world_, int x_, int y_, int z_, Block block_, int modelId_, RenderBlocks renderer_)
	{
		
		TileEntityDiscreteCable tile = (TileEntityDiscreteCable)world_.getTileEntity(x_, y_, z_);
	
		if(tile == null)
		{
			return true;
		}
		boolean[] connections = tile.getConnectionArray();
		
		DiscreteRenderHelper drh = new DiscreteRenderHelper();
		drh.renderDiscreteQuad(world_, renderer_, block_, x_, y_, z_);
        Tessellator t = Tessellator.instance;
               
        //if it was 0, it would be on the same plane as the face of the block. This is to prevent the graphical glitch
        double zOffset = 0.002;
        double extraOffset = 0.1;
       
        //flush the previous rendered blocks - otherwise they will use our new texture. We don't want that
        t.draw();
        t.startDrawingQuads();
        t.setColorOpaque_F(1.0F, 1.0F, 1.0F);
        //bind the new texture
        
        //GL11.glBindTexture(GL11.GL_TEXTURE_2D, Minecraft.getMinecraft().renderEngine.getTexture(socketTextures[tile.getCableType()]));
        this.bindTexture(socketTextures[tile.getCableType()]);

        //SOUTH Texture +Z
        if(connections[3] == true && block_.shouldSideBeRendered(world_, x_, y_, z_ + 1, 3) == true)
        {
		    t.addVertexWithUV(x_, y_, z_+1 + zOffset, 0, 1);
		    t.addVertexWithUV(x_+1, y_, z_+1 + zOffset, 1, 1);
		    t.addVertexWithUV(x_+1, y_+1, z_+1 + zOffset, 1, 0);
		    t.addVertexWithUV(x_, y_+1, z_+1 + zOffset, 0, 0);
		   
		    t.draw();
		    t.startDrawingQuads();
        }


        //NORTH -Z
        if(connections[2] == true && block_.shouldSideBeRendered(world_, x_, y_, z_ - 1, 2) == true)
        {        
	        t.addVertexWithUV(x_, y_ + 1, z_ - zOffset, 0, 0);
	        t.addVertexWithUV(x_ + 1, y_ + 1, z_ - zOffset, 1, 0);
	        t.addVertexWithUV(x_ + 1, y_, z_ - zOffset, 1, 1);
	        t.addVertexWithUV(x_, y_, z_ - zOffset, 0, 1);
	        
	        t.draw();
	        t.startDrawingQuads();
        }
        
        
        //EAST + X
        if(connections[5] == true  && block_.shouldSideBeRendered(world_, x_ + 1, y_, z_, 5) == true)
        { 
	        t.addVertexWithUV(x_ + 1 + zOffset, y_, z_ + 1, 1, 1);		//2
	        t.addVertexWithUV(x_ + 1 + zOffset, y_, z_, 0, 1);			//1              
	        t.addVertexWithUV(x_ + 1 + zOffset, y_ + 1, z_, 0, 0); 		//4
	         t.addVertexWithUV(x_ + 1 + zOffset, y_ + 1, z_ + 1, 1, 0);	//3
	        
	        t.draw();
	        t.startDrawingQuads();
        }
        
        //WEST
        if(connections[4] == true  && block_.shouldSideBeRendered(world_, x_ - 1, y_, z_, 4) == true)
        {
	        t.addVertexWithUV(x_ - zOffset, y_, z_, 0, 1);
	        t.addVertexWithUV(x_ - zOffset, y_, z_ + 1, 1, 1);
	        t.addVertexWithUV(x_ - zOffset, y_ + 1, z_ + 1, 1, 0);
	        t.addVertexWithUV(x_ - zOffset, y_ + 1, z_, 0, 0); 
	        
	        t.draw();
	        t.startDrawingQuads();
        }
        
        //TOP
        if(connections[1] == true && block_.shouldSideBeRendered(world_, x_, y_ + 1, z_, 1) == true)
        {
	        t.addVertexWithUV(x_ + 1, y_ + 1 + zOffset, z_, 1, 1);
	        t.addVertexWithUV(x_, y_ + 1 + zOffset, z_, 0, 1);
	        t.addVertexWithUV(x_, y_ + 1 + zOffset, z_ + 1, 0, 0);
	        t.addVertexWithUV(x_ + 1, y_ + 1 + zOffset, z_ + 1, 1, 0);
	             
	        t.draw();
	        t.startDrawingQuads();
        }
        
        //BOTTOM
        if(connections[0] == true && block_.shouldSideBeRendered(world_, x_, y_ - 1, z_, 0) == true)
        {
	        t.addVertexWithUV(x_, y_ - zOffset, z_, 0, 1);
	        t.addVertexWithUV(x_ + 1, y_ - zOffset, z_, 1, 1);
	        t.addVertexWithUV(x_ + 1, y_ - zOffset, z_ + 1, 1, 0);
	        t.addVertexWithUV(x_, y_ - zOffset, z_ + 1, 0, 0);
	        
	        t.draw();
	        t.startDrawingQuads();

        }
   
        
        renderer_.clearOverrideBlockTexture();
        
        //GL11.glBindTexture(GL11.GL_TEXTURE_2D, Minecraft.getMinecraft().renderEngine.getTexture("/terrain.png"));
        //this.bindTexture(terrainPng);
        this.bindTexture(TextureMap.locationBlocksTexture);
        return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelID)
	{
		return true;
	}
	

	@Override
	public int getRenderId()
	{
		return renderID;
	}
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ireg_)
	{	
		icon = ireg_.registerIcon(DiscreteIndustry.MODID + ":" + "textureBlock");
		socket = ireg_.registerIcon(DiscreteIndustry.MODID + ":" + "glassPlugSocket");
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
