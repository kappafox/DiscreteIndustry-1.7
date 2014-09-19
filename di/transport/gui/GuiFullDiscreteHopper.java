package kappafox.di.transport.gui;


import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.Iterator;

import kappafox.di.base.gui.BaseDiscreteGuiContainer;
import kappafox.di.base.gui.GuiModeButton;
import kappafox.di.base.network.PacketDiscreteSync;
import kappafox.di.transport.tileentities.TileEntityDiscreteHopper;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class GuiFullDiscreteHopper extends BaseDiscreteGuiContainer
{
	private ResourceLocation texture = new ResourceLocation("discreteindustry","textures/gui/FullDiscreteHopper.png");

	private TileEntityDiscreteHopper tile;
	
	public GuiFullDiscreteHopper(InventoryPlayer invp, TileEntityDiscreteHopper tile2)
	{
		super(new ContainerDiscreteHopper(invp, tile2));
		tile = tile2;
		
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
	protected void drawGuiContainerForegroundLayer(int p2, int p3)
	{
		fontRendererObj.drawString("Discrete Hopper", 8, 5, 4210752);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(texture);	
		
		//super.handleTooltips(p2, p3);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void initGui()
	{
		super.initGui();
		
		/*
		GuiModeButton btn = new GuiModeButton(this, 0, relativeX(10), relativeY(10), "Extract from inventory above", tile);
		
		if(tile != null)
		{
			btn.setMode(tile.getExtractFromAbove());
		}
		*/
		

		//this.buttonList.add(btn);
	
	}
	
	@Override
	public void actionPerformed(GuiButton button)
	{
		switch(button.id)
		{
			//extract button
			case 0:
			{
				this.toggleExtract();
				break;
			}
		}
	}
	
	private void toggleExtract( )
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream(42);
		DataOutputStream outputStream = new DataOutputStream(bos);
		
		int x = tile.xCoord;
		int y = tile.yCoord;
		int z = tile.zCoord;
		int d = tile.getWorldObj().provider.dimensionId;
		try 
		{
	        outputStream.writeInt(x);
	        outputStream.writeInt(y);
	        outputStream.writeInt(z);
	        outputStream.writeInt(d);
		}
		catch (Exception e) 
		{
	        e.printStackTrace();
		}

		/*
		PacketDiscreteSync packet = new PacketDiscreteSync();
		packet.module = 3;
		packet.type = 0;
		packet.channel = "DI_GENERIC_SYNC";
		packet.data = bos.toByteArray();
		packet.length = bos.size();	
		
		PacketDispatcher.sendPacketToServer(packet);
		*/
	}
	

}
