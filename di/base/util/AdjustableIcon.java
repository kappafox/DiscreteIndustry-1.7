package kappafox.di.base.util;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;

public class AdjustableIcon implements IIcon
{
	
	private int width;
	private int height;
	private float minu;
	private float maxu;
	private float minv;
	private float maxv;
	private String name;
	
	public AdjustableIcon(IIcon ico_)
	{
		this(ico_.getIconWidth(), ico_.getIconHeight(), ico_.getMinU(), ico_.getMaxU(), ico_.getMinV(), ico_.getMaxV(), ico_.getIconName());
	}
	
	public AdjustableIcon(int width_, int height_, float minu_, float maxu_, float minv_, float maxv_, String name_)
	{
		width = width_;
		height = height_;
		minu = minu_;
		minv = minv_;
		maxu = maxu_;
		maxv = maxv_;
		name = name_;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getIconWidth() 
	{
		return width;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getIconHeight() 
	{
		return height;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getMinU() 
	{
		return minu;
	}
	
	@SideOnly(Side.CLIENT)
	public void setMinU(float f_) 
	{
		minu = f_;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getMaxU() 
	{
		return maxu;
	}
	
	@SideOnly(Side.CLIENT)
	public void setMaxU(float f_) 
	{
		maxu = f_;
	}


	@Override
	@SideOnly(Side.CLIENT)
	public float getMinV() 
	{
		return minv;
	}
	
	@SideOnly(Side.CLIENT)
	public void setMinV(float f_) 
	{
		minv = f_;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getMaxV() 
	{
		return maxv;
	}
	
	@SideOnly(Side.CLIENT)
	public void setMaxV(float f_) 
	{
		maxv = f_;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public float getInterpolatedU(double par1)
    {
        float f = this.getMaxU() - this.getMinU();
        return this.getMinU() + f * ((float)par1 / 16.0F);
    }
	

	@Override
	@SideOnly(Side.CLIENT)
    public float getInterpolatedV(double par1)
    {
        float f = this.getMaxV() - this.getMinV();
        return this.getMinV() + f * ((float)par1 / 16.0F);
    }
	
	@SideOnly(Side.CLIENT)
    public void offsetU(int px_)
    {
	    float f = this.getMaxU() - this.getMinU();
	    float dist = (float)((1.0D / 16.0D) * px_);
	    
		this.setMinU(this.getInterpolatedU(px_));			
		this.setMaxU(this.getMaxU() + (f * (float)dist));
    }

	@Override
	@SideOnly(Side.CLIENT)
	public String getIconName() 
	{
		return name;
	}

	public void offsetV(int px_) 
	{
	    float f = this.getMaxV() - this.getMinV();
	    float dist = (float)((1.0D / 16.0D) * px_);
	    
		this.setMinV(this.getInterpolatedV(px_));			
		this.setMaxV(this.getMaxV() + (f * (float)dist));
	}

}
