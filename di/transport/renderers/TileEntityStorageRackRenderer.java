package kappafox.di.transport.renderers;

import org.lwjgl.opengl.GL11;

import kappafox.di.base.DiscreteRenderHelper;
import kappafox.di.base.TranslationHelper;
import kappafox.di.base.util.PixelSet;
import kappafox.di.base.util.Point;
import kappafox.di.base.util.PointSet;
import kappafox.di.transport.tileentities.TileEntityStorageRack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityStorageRackRenderer extends TileEntitySpecialRenderer
{
	
	private static final PixelSet px = PixelSet.getInstance();
	private static final FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
	private static final TranslationHelper translator = new TranslationHelper();
	private static final Tessellator tessellator = Tessellator.instance;
	
	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float animationMultiplier)
	{
		if(tile != null && tile instanceof TileEntityStorageRack)
		{
			TileEntityStorageRack tesr = (TileEntityStorageRack)tile;
			
			switch(tesr.getSize())
			{
				case 1:
				{
					this.renderStorageRackSingle(tesr, x, y, z, animationMultiplier);
					break;
				}
				
				case 2:
				{
					this.renderStorageRackDual(tesr, x, y, z, animationMultiplier);
					break;
				}
				
				case 4:
				{
					this.renderStorageRackQuad(tesr, x, y, z, animationMultiplier);
					break;
				}
			}	
		}
	}
	
	private void drawIcon(ForgeDirection direction, float scale, ItemStack content)
	{
		GL11.glDisable(GL11.GL_LIGHTING);
		
	    GL11.glScalef(1.0F, 1.0F, -1.0F);	//stops the item appearing inside out	    
	    GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);	//rotates the item so it's not upside down
	    
	    translator.rotateTessellator(ForgeDirection.NORTH, direction, true);
	    
	    GL11.glScalef(scale, scale, scale);		//shrinks the block down to the correct size
	    GL11.glScalef(1.0F, 1.0F, 0.01F);	//flattens the object by scaling Z to nothing

		DiscreteRenderHelper.renderItemFlatInWorld(content);
		GL11.glEnable(GL11.GL_LIGHTING);
	}
	
	private void drawText(ForgeDirection direction, float scale, ItemStack content, int number)
	{
		GL11.glDisable(GL11.GL_LIGHTING);
	    GL11.glScalef(1.0F, 1.0F, -1.0F);			//stops the item appearing inside out			    
	    GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);	//rotates the item so it's not upside down

	    translator.rotateTessellator(ForgeDirection.NORTH, direction, false);
	    
	    String count = this.itemCountToStacks(content, number);    
	    int length = fontRenderer.getStringWidth(count);
	    float middle = (length / 2 * scale) - (scale / 2);
	    
	    
	    switch(direction)
	    {
	    	case SOUTH:
	    	{	
	    		GL11.glTranslated(-middle - 0.5, 0.0, -0.5);
	    		break;
	    	}
	    	
	    	case EAST:
	    	{
	    		GL11.glTranslated(-middle, 0.0, -0.5);

	    		break;
	    	}
	    	
	    	case WEST:
	    	{
	    		GL11.glTranslated( -middle - 0.5, 0, 0);
	    		break;
	    	}
	    	
	    	default:
	    	{
	    	    GL11.glTranslated( -middle, 0, 0);
	    	} 		
	    }
	    
	    GL11.glScalef(scale, scale, scale);		//shrinks the text down to the correct size
	    DiscreteRenderHelper.renderTextInWorld(count, 0, 0, 16777215, false);
		GL11.glEnable(GL11.GL_LIGHTING);
	}
	
	private void renderStorageRackQuad(TileEntityStorageRack tile, double x, double y, double z, float animationMultiplier)
	{
		int type = tile.getSize();
		ForgeDirection direction = ForgeDirection.getOrientation(tile.getDirection());
		
		ItemStack content = tile.getContainerContent(0);
		
		if(content != null)
		{
			Point topBoxIcon = translator.translate(ForgeDirection.NORTH, direction, new Point(px.two + px.quarter, px.fifteen - px.quarter, px.fourteen + 0.01));
			Point topBoxText = translator.translate(ForgeDirection.NORTH, direction, new Point(px.four + px.quarter, px.ten + px.half, px.fourteen + 0.01)); 
			
			GL11.glPushMatrix();
			
		    GL11.glTranslated(x + topBoxIcon.x, y + topBoxIcon.y, z + topBoxIcon.z);

		    this.drawIcon(direction, px.one / 4.0F, content);	
			GL11.glPopMatrix();
		
			GL11.glPushMatrix();				
		    GL11.glTranslated(x + topBoxText.x, y + topBoxText.y, z + topBoxText.z);
		    this.drawText(direction, px.one / 10.0F, content, tile.getContainerContentCount(0));
			GL11.glPopMatrix();
			
			
			//box 2
		}
		
		content = tile.getContainerContent(1);
		if(content != null)
		{
			Point topBoxIcon = translator.translate(ForgeDirection.NORTH, direction, new Point(px.ten - px.quarter, px.fifteen - px.quarter, px.fourteen + 0.01));
			Point topBoxText = translator.translate(ForgeDirection.NORTH, direction, new Point(px.twelve - px.quarter, px.ten + px.half, px.fourteen + 0.01)); 
			
			GL11.glPushMatrix();
			
		    GL11.glTranslated(x + topBoxIcon.x, y + topBoxIcon.y, z + topBoxIcon.z);

		    this.drawIcon(direction, px.one / 4.0F, content);	
			GL11.glPopMatrix();
		
			GL11.glPushMatrix();				
		    GL11.glTranslated(x + topBoxText.x, y + topBoxText.y, z + topBoxText.z);
		    this.drawText(direction, px.one / 10.0F, content, tile.getContainerContentCount(1));
			GL11.glPopMatrix();
		}
		
		content = tile.getContainerContent(2);
		if(content != null)
		{
			Point topBoxIcon = translator.translate(ForgeDirection.NORTH, direction, new Point(px.two + px.quarter, px.seven, px.fourteen + 0.01));
			Point topBoxText = translator.translate(ForgeDirection.NORTH, direction, new Point(px.four + px.quarter, px.three - px.quarter, px.fourteen + 0.01)); 
			
			GL11.glPushMatrix();
			
		    GL11.glTranslated(x + topBoxIcon.x, y + topBoxIcon.y, z + topBoxIcon.z);

		    this.drawIcon(direction, px.one / 4.0F, content);	
			GL11.glPopMatrix();
		
			GL11.glPushMatrix();				
		    GL11.glTranslated(x + topBoxText.x, y + topBoxText.y, z + topBoxText.z);
		    this.drawText(direction, px.one / 10.0F, content, tile.getContainerContentCount(2));
			GL11.glPopMatrix();
		}
		
		content = tile.getContainerContent(3);
		if(content != null)
		{
			Point topBoxIcon = translator.translate(ForgeDirection.NORTH, direction, new Point(px.ten - px.quarter, px.seven, px.fourteen + 0.01));
			Point topBoxText = translator.translate(ForgeDirection.NORTH, direction, new Point(px.twelve - px.quarter, px.three - px.quarter, px.fourteen + 0.01)); 
			
			GL11.glPushMatrix();
			
		    GL11.glTranslated(x + topBoxIcon.x, y + topBoxIcon.y, z + topBoxIcon.z);

		    this.drawIcon(direction, px.one / 4.0F, content);	
			GL11.glPopMatrix();
		
			GL11.glPushMatrix();				
		    GL11.glTranslated(x + topBoxText.x, y + topBoxText.y, z + topBoxText.z);
		    this.drawText(direction, px.one / 10.0F, content, tile.getContainerContentCount(3));
			GL11.glPopMatrix();			
		}
	}
	
	private void renderStorageRackDual(TileEntityStorageRack tile, double x, double y, double z, float animationMultiplier)
	{
		int type = tile.getSize();
		ForgeDirection direction = ForgeDirection.getOrientation(tile.getDirection());
		
		ItemStack content = tile.getContainerContent(0);
		
		//fourteen
		Point topBoxIcon = translator.translate(ForgeDirection.NORTH, direction, new Point(px.six, px.fifteen - px.quarter, px.fourteen + 0.01));
		Point topBoxText = translator.translate(ForgeDirection.NORTH, direction, new Point(px.eight, px.ten + px.half, px.fourteen + 0.01)); 
		
		if(content != null)
		{
			GL11.glPushMatrix();
			
		    GL11.glTranslated(x + topBoxIcon.x, y + topBoxIcon.y, z + topBoxIcon.z);

		    this.drawIcon(direction, px.one / 4.0F, content);	
			GL11.glPopMatrix();
		
			GL11.glPushMatrix();				
		    GL11.glTranslated(x + topBoxText.x, y + topBoxText.y, z + topBoxText.z);
		    this.drawText(direction, px.one / 5.0F, content, tile.getContainerContentCount(0));
			GL11.glPopMatrix();
			
			//box 2
		}
		
		content = tile.getContainerContent(1);
		if(content != null)
		{
			topBoxIcon = translator.translate(ForgeDirection.NORTH, direction, new Point(px.six, px.seven, px.fourteen + 0.01));
			topBoxText = translator.translate(ForgeDirection.NORTH, direction, new Point(px.eight, px.three - px.quarter, px.fourteen + 0.01)); 
			
			GL11.glPushMatrix();
			
		    GL11.glTranslated(x + topBoxIcon.x, y + topBoxIcon.y, z + topBoxIcon.z);

		    this.drawIcon(direction, px.one / 4.0F, content);	
			GL11.glPopMatrix();
		
			GL11.glPushMatrix();				
		    GL11.glTranslated(x + topBoxText.x, y + topBoxText.y, z + topBoxText.z);
		    this.drawText(direction, px.one / 5.0F, content, tile.getContainerContentCount(1));
			GL11.glPopMatrix();
		}
	}
	
	
	private void renderStorageRackSingle(TileEntityStorageRack tile, double x, double y, double z, float animationMultiplier)
	{
		int type = tile.getSize();
		ForgeDirection direction = ForgeDirection.getOrientation(tile.getDirection());
		
		ItemStack content = tile.getContainerContent(0);
		
		if(content != null)
		{
			GL11.glPushMatrix();
			//translator.rotateTessellator(ForgeDirection.NORTH, ForgeDirection.getOrientation(tile.getDirection()));
			Point p = translator.translate(ForgeDirection.NORTH, ForgeDirection.getOrientation(tile.getDirection()), new Point(px.four, px.twelve + px.half, px.fourteen + 0.01));
			GL11.glTranslated(x + p.x, y + p.y, z + p.z);
			this.drawIcon(ForgeDirection.getOrientation(tile.getDirection()), px.one / 2.0F, content);
			//translator.rotateTessellator(ForgeDirection.NORTH, ForgeDirection.getOrientation(tile.getDirection()));
			/*
			GL11.glDisable(GL11.GL_LIGHTING);
			
		    GL11.glTranslated(x + px.twelve, y + px.four + px.half, z + px.fourteen + 0.01);
		    GL11.glScalef(1.0F, 1.0F, -1.0F);	//stops the item appearing inside out
		    
		    GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);	//rotates the item so it's not upside down
		    GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);    

		    //GL11.glTranslatef(-0.5F, -0.5F, 0.0F);	//rotates slightly up to match the inventory ones
		    float scale = px.one / 2.0F;
		    GL11.glScalef(scale, scale, scale);		//shrinks the block down to the correct size
		    GL11.glScalef(1.0F, 1.0F, 0.01F);	//flattens the object by scaling Z to nothing

			DiscreteRenderHelper.renderItemFlatInWorld(content);
			*/
			GL11.glPopMatrix();
			
			GL11.glPushMatrix();
			p = translator.translate(ForgeDirection.NORTH, ForgeDirection.getOrientation(tile.getDirection()), new Point(px.eight, px.three + px.half, px.fourteen + 0.01));
			
		    //GL11.glTranslated(x + p.x, y + p.y, z + p.z);
		    
			
		    GL11.glTranslated(x + p.x, y + p.y, z + p.z);
		    
		    this.drawText(ForgeDirection.getOrientation(tile.getDirection()), px.one / 5, content, tile.getContainerContentCount(0));
		    /*
		    GL11.glScalef(1.0F, 1.0F, -1.0F);			//stops the item appearing inside out			    
		    GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);	//rotates the item so it's not upside down
		    GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
	
		    float scale2 = px.one / 5;	//text scale is a division of a px, a whole block is * division
		    GL11.glScalef(scale2, scale2, scale2);		//shrinks the block down to the correct size
		    
		    String count = this.itemCountToStacks(content, tile.getContainerContentCount(0));
		    
		    int length = fontRenderer.getStringWidth(count);
		    int middle = length / 2;
		    GL11.glTranslated( -middle, 0, 0);
		    DiscreteRenderHelper.renderTextInWorld(count, 0, 0, 16777215, false);
		    //DiscreteRenderHelper.renderTextInWorldCentered(count, xpos, 0,  16777215, false, px.sixteen * 16);
		 
		     */
			GL11.glPopMatrix();
		}
	}
	
	private String itemCountToStacks(ItemStack istack, int count)
	{
		int leftover = count % istack.getMaxStackSize();
		int stacks = (count - leftover) / istack.getMaxStackSize();
		
		if(stacks == 0)
		{
			return "" + leftover;
		}
		else
		{
			if(istack.getMaxStackSize() != 1)
			{
				String s = stacks + "x" + istack.getMaxStackSize();
				
				if(leftover > 0)
				{
					s = s + "+" + leftover; 
				}
				return s;
			}
			else
			{
				return stacks + "";
			}
		}
	}
}
