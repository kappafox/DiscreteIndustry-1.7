package kappafox.di.decorative.gui;


import kappafox.di.decorative.tileentities.TileEntitySwordRack;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class GuiSwordRack extends GuiContainer
{
	
	private ResourceLocation texture = new ResourceLocation("discreteindustry","textures/gui/SwordRack.png");
	
	public GuiSwordRack(InventoryPlayer invp_, TileEntitySwordRack tile_)
	{
		super(new ContainerSwordRack(invp_, tile_));
	}

	@Override
	@SideOnly(Side.CLIENT)
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(texture);

        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
        
        //drawTexturedModalRect(x, y, u, v, width, height
       
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	protected void drawGuiContainerForegroundLayer(int p2_, int p3_)
	{
		fontRendererObj.drawString("Sword Rack", 8, 5, 4210752);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(texture);	
		
	}

}
