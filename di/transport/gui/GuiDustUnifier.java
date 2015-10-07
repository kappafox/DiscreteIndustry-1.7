package kappafox.di.transport.gui;

import kappafox.di.decorative.tileentities.TileEntitySwordRack;
import kappafox.di.transport.tileentities.TileEntityDustUnifier;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class GuiDustUnifier extends GuiContainer
{
	private ResourceLocation texture = new ResourceLocation("discreteindustry","textures/gui/DustUnifier.png");
	
	public GuiDustUnifier(InventoryPlayer invp, TileEntityDustUnifier tile)
	{
		super(new ContainerDustUnifier(invp, tile));
		this.xSize = 248;
		this.ySize = 242;
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
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	protected void drawGuiContainerForegroundLayer(int p2, int p3)
	{
		fontRendererObj.drawString("Dust Unifier", 8, 5, 4210752);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(texture);	
		
	}

}

