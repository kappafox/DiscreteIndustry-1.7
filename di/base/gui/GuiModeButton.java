package kappafox.di.base.gui;

import kappafox.di.transport.tileentities.TileEntityDiscreteHopper;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

public class GuiModeButton extends GuiButton
{

	public GuiModeButton(int par1, int par2, int par3, int par4, int par5,
			String par6Str) {
		super(par1, par2, par3, par4, par5, par6Str);
		// TODO Auto-generated constructor stub
	}
	/*
	private BaseDiscreteGuiContainer gContainer;
	private String toolTip = "";
	
	protected ResourceLocation buttonTextures = new ResourceLocation("textures/gui/widgets.png");
	private static final ResourceLocation buttonStateOn = new ResourceLocation("discreteindustry", "textures/gui/GuiModeButton_On.png");
	private static final ResourceLocation buttonStateOff = new ResourceLocation("discreteindustry", "textures/gui/GuiModeButton_Off.png");
	private static final int WIDTH = 16;
	private static final int HEIGHT = 16;
	
	protected boolean mode = false;
	private TileEntityDiscreteHopper tile;
	
	
	public GuiModeButton(BaseDiscreteGuiContainer gcon, int id, int xpos, int ypos, String tooltip, TileEntityDiscreteHopper t)
	{
		super(id, xpos, ypos, WIDTH, HEIGHT, "");
		gContainer = gcon;
		toolTip = tooltip;
		tile = t;
	}
	
	public GuiModeButton(int id, int xpos, int ypos, int width, int height, String text)
	{
		super(id, xpos, ypos, width, height, text);
	}
	
	@Override
    public void func_82251_b(int par1, int par2)
    {
        this.gContainer.tooltipAccessHelper(toolTip, par1, par2);
    }
	
	@Override
    public void drawButton(Minecraft minecraft, int par2, int par3)
    {
		ResourceLocation t = buttonStateOff;
    	if(tile != null)
    	{
            if(tile.getExtractFromAbove() == false)
            {
                t = buttonStateOff;
            }
            else
            {
            	t = buttonStateOn;
            }
    	}
    	
        if (this.drawButton)
        {
            FontRenderer fontrenderer = minecraft.fontRenderer;
            minecraft.getTextureManager().bindTexture(t);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.field_82253_i = par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height;
            int k = this.getHoverState(this.field_82253_i);
            this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 46 + k * 20, this.width / 2, this.height);
            this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, 200 - this.width / 2, 46 + k * 20, this.width / 2, this.height);
            this.mouseDragged(minecraft, par2, par3);
            int l = 14737632;

            if (!this.enabled)
            {
                l = -6250336;
            }
            else if (this.field_82253_i)
            {
                l = 16777120;
            }

            this.drawCenteredString(fontrenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, l);
        }
    }
	
	public void setMode(boolean m)
	{
		mode = m;
	}
	*/

}
