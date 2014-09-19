package kappafox.di.decorative.renderers;

import kappafox.di.base.DiscreteRenderHelper;
import kappafox.di.decorative.tileentities.TileEntitySwordRack;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;

import org.lwjgl.opengl.GL11;

public class TileEntitySwordRackRenderer extends TileEntitySpecialRenderer
{
	
	private Tessellator tessellator;
	private DiscreteRenderHelper drh;
	private float angle;
	private float height;
	private float increment;
	
	private static final float onePx = 0.0625F;
	private static final float halfPx = onePx / 2;
	
	public TileEntitySwordRackRenderer( )
	{
		tessellator = Tessellator.instance;
		drh = new DiscreteRenderHelper();
		angle = 0.0F;
		height = 0.2F;
		increment = 0.0005F;
	}
	
	
	@Override
	public void renderTileEntityAt(TileEntity tile_, double x_, double y_, double z_, float animationMultiplier_) 
	{
		TileEntitySwordRack tile = (TileEntitySwordRack)tile_; 

		if(tile != null)
		{
			int type = tile.getSubtype();
			//
			switch(type)
			{
				//Sword Rest
				case 821:
					this.renderSwordRest(tile, x_, y_, z_, animationMultiplier_);
					break;
				
				//6 Sword Rack
				case 822:
					this.renderSwordRack(tile, x_, y_, z_, animationMultiplier_);
					break;
			}
			
			//tile.worldObj.markBlockForRenderUpdate(tile.xCoord, tile.yCoord, tile.zCoord);
		}
		

	}
	
	
	private void renderSwordRest(TileEntitySwordRack tile, double x_, double y_, double z_, float animationMultiplier_) 
	{

		ItemStack istack = tile.getItem(0);
	
		if(istack != null)
		{       
	        Item item = istack.getItem();
			IIcon icon = item.getIcon(istack, 0);
	
			this.bindTexture(TextureMap.locationItemsTexture);
				
			int direction = tile.getDirection();
			float rotationOffset = 90.0F;
			float znudge = 0.025F;
			float xnudge = -0.025F;
			
			if(direction == 4 || direction == 5)
			{
				rotationOffset = 90.0F;
				znudge = 0;
			}
			else
			{
				rotationOffset = 0.0F;
				xnudge = 0;
			}
			
			
			GL11.glPushMatrix();
			GL11.glTranslated(x_ + 0.5 + xnudge , y_ + 1.29, z_ + 0.5 + znudge);	
			GL11.glScalef(0.8F, 0.8F, 0.8F);
			
			GL11.glRotatef(-135.0F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(rotationOffset, 1.0F, 1.0F, 0.0F);
			
			DiscreteRenderHelper.renderItemAsBlock(tessellator, istack, true, 0.0625F);
			GL11.glPopMatrix();
		}
	}


	private void renderSwordRack(TileEntitySwordRack tile_, double x_, double y_, double z_, float animationMultiplier_)
	{
		this.bindTexture(TextureMap.locationItemsTexture);

		Item item = null;
		ItemStack istack = null;
		IIcon icon = null;
		
		float offset = 1.0F;
		
		TileEntitySwordRack tile = tile_;
		
		int direction = tile.getDirection();
		
		float inc = 2.0F * onePx + 0.0020F;
		float translationOffset = inc + (onePx / 2.0F);
		float translationOffset2 = 3.0F * onePx + 0.020F;
		
		float heightOffset = 1.2F;
		float rotationOffset = -135.0F;
		float rotationOffset2;
		boolean rotated = false;
		
		if(direction == 2 || direction == 3)
		{
			rotationOffset2 = 90.0F;
		}
		else
		{
			rotationOffset2 = 0.0F;
			rotated = true;
		}

		

		
		for(int i = 0; i < 6; i++)
		{
			
			istack = tile.getItem(i);

			if(istack != null)
			{
				item = istack.getItem();
				icon = istack.getItem().getIcon(istack, 0);
				GL11.glPushMatrix();
				
				if(item instanceof ItemSword)
				{
					heightOffset = 1.2F;
					rotationOffset = -135.0F;
				}
				
				if(item instanceof ItemPickaxe || item instanceof ItemAxe || item instanceof ItemSpade || item instanceof ItemHoe)
				{
					rotationOffset = 45.0F;
					heightOffset = 0.0F;
					translationOffset = translationOffset + onePx - 0.015F;
				}

				
				if(rotated == true)
				{
					GL11.glTranslated(x_ + 0.5 , y_ + heightOffset, z_ + (translationOffset2 + (i * inc)));	
				}
				else
				{
					GL11.glTranslated(x_ + (translationOffset + (i * inc)), y_ + heightOffset, z_ + 0.5);					
				}
				GL11.glScalef(0.8F, 0.8F, 0.8F);
				
				GL11.glRotatef(rotationOffset, 0.0F, 0.0F, 1.0F);
				GL11.glRotatef(rotationOffset2, 1.0F, 1.0F, 0.0F);
			    
				//ItemRenderer.renderItemIn2D(tessellator, icon.getMinU(), icon.getMinV(), icon.getMaxU(), icon.getMaxV(), icon.getIconWidth(), icon.getIconHeight(), 0.0625F);
				DiscreteRenderHelper.renderItemAsBlock(tessellator, istack, true, 0.0625F);
				
				GL11.glPopMatrix();
				
				rotationOffset = -135.0F;
				heightOffset = 1.2F;
				translationOffset = inc + onePx / 2;
			}
		}
	}
	

}
