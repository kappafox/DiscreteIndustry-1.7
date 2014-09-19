package kappafox.di.base.gui;

import java.util.Arrays;
import java.util.Iterator;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.inventory.Container;

public abstract class BaseDiscreteGuiContainer extends GuiContainer 
{

	public BaseDiscreteGuiContainer(Container par1Container) {
		super(par1Container);
		// TODO Auto-generated constructor stub
	}
	
	/*
	public BaseDiscreteGuiContainer(Container container)
	{
		super(container);
	}

	
	@Override
    protected void drawCreativeTabHoveringText(String par1Str, int par2, int par3)
    {
        this.func_102021_a(Arrays.asList(new String[] {par1Str}), par2, par3);
    }
	
	public void tooltipAccessHelper(String msg, int par2, int par3)
	{
		this.drawCreativeTabHoveringText(msg, par2, par3);
	}
    
	@Override
	protected abstract void drawGuiContainerBackgroundLayer(float f, int i, int j);
	
	protected void handleTooltips(int x, int y)
	{
        RenderHelper.disableStandardItemLighting();
        Iterator iterator = this.buttonList.iterator();

        while (iterator.hasNext())
        {
            GuiButton guibutton = (GuiButton)iterator.next();

            if (guibutton.func_82252_a())
            {
                guibutton.func_82251_b(x - this.guiLeft, y - this.guiTop);
                break;
            }
        }

        RenderHelper.enableGUIStandardItemLighting();
	}
	
	protected int relativeX(int offsetX)
	{
		return ((this.width - this.xSize) / 2) + offsetX;
	}
	
	protected int relativeY(int offsetY)
	{
		return ((this.height - this.ySize) / 2) + offsetY;
	}
	*/

}
