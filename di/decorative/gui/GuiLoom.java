package kappafox.di.decorative.gui;


import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import kappafox.di.base.network.PacketDiscreteSync;
import kappafox.di.decorative.tileentities.TileEntityLoomBlock;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class GuiLoom extends GuiContainer
{
	
	private static Tessellator tessellator = Tessellator.instance;
	
	private static ResourceLocation texture = new ResourceLocation("discreteindustry","textures/gui/Loom.png");
	private static ResourceLocation flagSquare = new ResourceLocation("discreteindustry","textures/blocks/flagPartSingle.png");
	
	private static int colourXSize = 2;
	private static int colourYSize = 2;
	private static float colInc = (float)0.0625;
	private static float brightInc = (float)(0.0625 * 1.5);
	
	private TileEntityLoomBlock tile = null;
	
	public GuiLoom(InventoryPlayer invp_, TileEntityLoomBlock tile_)
	{	
		super(new ContainerLoom(invp_, tile_));
		
		tile = tile_;
		this.xSize = 200;
		this.ySize = 227;

		
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	protected void mouseClicked(int hitx_, int hity_, int id_)
	{
		super.mouseClicked(hitx_, hity_, id_);
		
		System.out.println("W:" + this.width + " |H:" + this.height);
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        
        x = x - hitx_;// + 4;
        y = y - hity_;// + 105;
		System.out.println(hitx_ + "," + hity_);
		System.out.println("O:" + x + "," + y);
		
		if(y > -128 && y < -81)
		{
			if(x > -196 && x < -3)
			{
				this.colorClicked(x, y, id_);
			}
		}
		

		
	}
	
	@SideOnly(Side.CLIENT)
	private void colorClicked(int hitx_, int hity_, int btn_) 
	{
		hitx_ += 4;
		hity_ += 105;
		
		hitx_ = Math.abs(hitx_);
		hity_ = hity_ * -1;
		
		System.out.println("THR:" + hitx_ + "," + hity_);
		//hitx_ -= 119;
		//hity_ -= 124;
		
		//odd numbers rounded down
		if(hitx_ % 2 != 0)
		{
			hitx_ -= 1;
		}
		
		if(hity_ % 2 != 0)
		{
			hity_ -= 1;
		}
		
		hity_ /= 2;
		hitx_ /= 2;
		
		System.out.println("CLICKED:" + hitx_ + "," + hity_);
		double[] xshift = this.shiftColoursX(hitx_, hity_);
		double[] yshift = this.shiftColoursY(hity_, xshift);

		
		double r = yshift[0];
		double g = yshift[1];
		double b = yshift[2];
		//System.out.println("YSHIFT\tR:" + r + "\tG:" + g + "\tB:" + b);
			
		if(tile != null)
		{
			tile.setPrimaryPaintColour(this.compressColourFloats((float)r, (float)g, (float)b));
		}
		
		this.sendSyncPacket((byte)0, this.compressColourFloats(r, g, b));
	}

	
	private double[] shiftColoursY(int hity_, double[] colours_) 
	{
		double r = colours_[0];
		double g = colours_[1];
		double b = colours_[2];
		
		double additive = (hity_ * brightInc);
		
		r = r + additive;
		g = g + additive;
		b = b + additive;
		
		if(r > 1.0)
		{
			r = 1.0;
		}
		
		if(g > 1.0)
		{
			g = 1.0;
		}
		
		if(b > 1.0)
		{
			b = 1.0;
		}
		
		
		if(r < 0.0)
		{
			r = 0.0;
		}
		
		if(g < 0.0)
		{
			g = 0.0;
		}
		
		if(b < 0.0)
		{
			b = 0.0;
		}
		
		return (new double[]{r, g, b});
	}

	@SideOnly(Side.CLIENT)
	private double[] shiftColoursX(int hitx_, int hity_)
	{
		double r = 0.0;
		double g = 0.0;
		double b = 0.0;
		
		double shiftedX;
		
		//Red Plus Green
		if(hitx_ >= 0 && hitx_ <= 16)
		{
			r = 1.0;
			g = 0.0;
			b = 0.0;
			
			g = g + (hitx_ * colInc);
		}
		
		//Green Minus Red
		if(hitx_ > 16 && hitx_ <= 32)
		{
			r = 1.0;
			g = 1.0;
			b = 0.0;
			
			shiftedX = hitx_ - 16;
			r = r - (shiftedX * colInc);
		}
		
		//Green + Blue
		if(hitx_ > 32 && hitx_ <= 48)
		{
			r = 0.0;
			g = 1.0;
			b = 0.0;
			
			shiftedX = hitx_ - 32;
			b = b + (shiftedX * colInc);
		}
		
		//Blue - Green
		if(hitx_ > 48 && hitx_ <= 64)
		{
			r = 0.0;
			g = 1.0;
			b = 1.0;
			
			shiftedX = hitx_ - 48;
			g = g - (shiftedX * colInc);
		}
		
		//Blue + Red
		if(hitx_ > 64 && hitx_ <= 80)
		{
			r = 0.0;
			g = 0.0;
			b = 1.0;		
			
			shiftedX = hitx_ - 64;
			r = r + (shiftedX * colInc);
		}
		
		//Red - Blue
		if(hitx_ > 80 && hitx_ <= 96)
		{
			r = 1.0;
			g = 0.0;
			b = 1.0;	
			
			shiftedX = hitx_ - 80;
			b = b - (shiftedX * colInc);
		}
		
		double[] result = new double[3];
		result[0] = r;
		result[1] = g;
		result[2] = b;
		
		return result;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void initGui( )
	{
		super.initGui();
		
        //id, x, y, width, height, text
		//controlList.add(new GuiButton(1, 10, 52, 20, 20, "+"));
		
    	//id, xpos, ypos, width, height, test
		//buttonList.add(new GuiButton(0, 100, 50, 100, 20, "Test"));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	protected void drawGuiContainerBackgroundLayer(float f_, int i_, int j_)
	{
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(texture);

        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        
        
        
     
        
        //int x = 115;
        //int y = 20;
        this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
		
        this.mc.renderEngine.bindTexture(flagSquare);
        
        if(tile != null)
        {
        	int col = tile.getPrimaryPaintColour();
        	double[] colourFloats = this.decompressColourFloats(col);
        	this.setColour((float)colourFloats[0], (float)colourFloats[1], (float)colourFloats[2]);
        	
        	this.drawTexturedModalRect(x + 4, y + 50, 0, 0, 20, 20);
        }
		
		
		float r = 0.0F;
		float b = 0.0F;
		float g = 0.0F;
		
		int xinc = 2;
		int yinc = 2;
		
		int xpos = x + 4;
		int ypos = y + 104;
	

		int shades = 16;
		int brights = 12;
		int darks = 12;
				
		//Draws a textured rectangle at the stored z-value. Args: x, y, u, v, width, height
		
			//RED ADDING GREEN
			r = 1.0F;
			g = 0.0F;
			b = 0.0F;

			for(int i = 0; i < shades; i ++)
			{
				this.setColour(r, g, b);
				this.drawTexturedModalRect(xpos, ypos, 0, 0, colourXSize, colourYSize);	
			
				this.drawColourBrights(xpos, ypos, brights, r, g, b);
				this.drawColourDarks(xpos, ypos, darks, r, g, b);
				
				g += colInc;
				xpos += xinc;
			}
			

			g = 1.0F;
			r = 1.0F;
			b = 0.0F;
			//GREEN SUBTRACTING RED
			for(int i = 0; i < shades; i ++)
			{
				this.setColour(r, g, b);
				this.drawTexturedModalRect(xpos, ypos, 0, 0, colourXSize, colourYSize);	
			
				this.drawColourBrights(xpos, ypos, brights, r, g, b);
				this.drawColourDarks(xpos, ypos, darks, r, g, b);
				
				r -= colInc;
				xpos += xinc;
			}
			
			g = 1.0F;
			r = 0.0F;
			b = 0.0F;
			//GREEN ADDING BLUE
			for(int i = 0; i < shades; i ++)
			{
				this.setColour(r, g, b);
				this.drawTexturedModalRect(xpos, ypos, 0, 0, colourXSize, colourYSize);	
			
				this.drawColourBrights(xpos, ypos, brights, r, g, b);
				this.drawColourDarks(xpos, ypos, darks, r, g, b);
				
				b += colInc;
				xpos += xinc;
			}
			
			g = 1.0F;
			b = 1.0F;
			r = 0.0F;
			//BLUE SUBTRACTING GREEN
			for(int i = 0; i < shades; i ++)
			{
				this.setColour(r, g, b);
				this.drawTexturedModalRect(xpos, ypos, 0, 0, colourXSize, colourYSize);	
			
				this.drawColourBrights(xpos, ypos, brights, r, g, b);
				this.drawColourDarks(xpos, ypos, darks, r, g, b);
				
				g -= colInc;
				xpos += xinc;
			}
			
			b = 1.0F;
			r = 0.0F;
			g = 0.0F;
			//BLUE ADDING RED
			for(int i = 0; i < shades; i ++)
			{
				this.setColour(r, g, b);
				this.drawTexturedModalRect(xpos, ypos, 0, 0, colourXSize, colourYSize);	
			
				this.drawColourBrights(xpos, ypos, brights, r, g, b);
				this.drawColourDarks(xpos, ypos, darks, r, g, b);
				
				r += colInc;
				xpos += xinc;
			}
			
			r = 1.0f;
			b = 1.0f;
			g = 0.0F;
			//RED SUBTRACTING BLUE
			for(int i = 0; i < shades; i ++)
			{
				this.setColour(r, g, b);
				this.drawTexturedModalRect(xpos, ypos, 0, 0, colourXSize, colourYSize);	
			
				this.drawColourBrights(xpos, ypos, brights, r, g, b);
				this.drawColourDarks(xpos, ypos, darks, r, g, b);
				
				b -= colInc;
				xpos += xinc;
			}

			
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	protected void drawGuiContainerForegroundLayer(int p2_, int p3_)
	{
		/*
		fontRenderer.drawString("Discrete Hopper", 8, 5, 4210752);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(texture);
		*/
		
	}

	private void drawColourBrights(int x_, int y_, int count_, float r_, float g_, float b_)
	{
		float r2;
		float g2;
		float b2;
		
		for(int j = 1; j < count_; j++)
		{
			y_ += colourYSize;
			
			r2 = r_ + (j * brightInc);
			g2 = g_ + (j * brightInc);
			b2 = b_ + (j * brightInc);
			
			this.setColour(r2, g2, b2);
			this.drawTexturedModalRect(x_, y_, 0, 0, colourXSize, colourYSize);	
		}
	}
	
	private void drawColourDarks(int x_, int y_, int count_, float r_, float g_, float b_)
	{
		float r2;
		float g2;
		float b2;
		
		for(int j = 1; j < count_; j++)
		{
			y_ -= colourYSize;
			
			r2 = r_ - (j * brightInc);
			g2 = g_ - (j * brightInc);
			b2 = b_ - (j * brightInc);
			
			this.setColour(r2, g2, b2);
			this.drawTexturedModalRect(x_, y_, 0, 0, colourXSize, colourYSize);	
		}
	}
	
	@SideOnly(Side.CLIENT)
	private int compressColourFloats(float r_, float g_, float b_) 
	{
		//(B + 256 * G + 65536 * R
		
		r_ = Math.round(r_ * 255.0F);
		g_ = Math.round(g_ * 255.0F);
		b_ = Math.round(b_ * 255.0F);

		float r2 = (65536 * r_);
		float g2 = (256 * g_);
		float b2 = b_;

		
		int result = (int)(b2 + g2 + r2);
		return result;
	}
	
	@SideOnly(Side.CLIENT)
	private int compressColourFloats(double r_, double g_, double b_) 
	{
		return this.compressColourFloats((float)r_, (float)g_, (float)b_);
	}
	
	@SideOnly(Side.CLIENT)
	private double[] decompressColourFloats(int colour_) 
	{     
		float r = ((float)(colour_ >> 16 & 255) / 255.0F);
		float g = (float)(colour_ >> 8 & 255) / 255.0F;
		float b = (float)(colour_ & 255) / 255.0F;

		return (new double[]{r, g, b});
	}
	
	@SideOnly(Side.CLIENT)
	private void setColour(float r_, float g_, float b_)
	{
		GL11.glColor4f(r_, g_, b_, 1.0F);	
	}

	@SideOnly(Side.CLIENT)
	private void sendSyncPacket(byte type_, int value_)
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream(42);
		DataOutputStream outputStream = new DataOutputStream(bos);
		
		int x = tile.xCoord;
		int y = tile.yCoord;
		int z = tile.zCoord;
		int d = tile.getWorldObj().provider.dimensionId;
		
		System.out.println("Sending Colour:" + value_);
		
		try 
		{
	        outputStream.writeByte(type_);
	        outputStream.writeInt(value_);
	        outputStream.writeInt(x);
	        outputStream.writeInt(y);
	        outputStream.writeInt(z);
	        outputStream.writeInt(d);
		}
		catch (Exception e_) 
		{
	        e_.printStackTrace();
		}

		//Packet250CustomPayload packet = new Packet250CustomPayload();
		//packet.channel = "DD_FLAG_SYNC";
		//packet.data = bos.toByteArray();
		//packet.length = bos.size();	
		
		//PacketDispatcher.sendPacketToServer(packet);
	}

}
