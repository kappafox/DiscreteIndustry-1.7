package kappafox.di.base.util;

public class PixelSet 
{
	private static PixelSet instance;
	

	
	public static final float zero = 0.0F;
	public static final float one = 0.0625F;
	public static final float two = 2.0F * one;
	public static final float three = 3.0F * one;
	public static final float four = 4.0F * one;
	public static final float five = 5.0F * one;
	public static final float six = 6.0F * one;
	public static final float seven = 7.0F * one;
	public static final float eight = 8.0F * one;
	public static final float nine = 9.0F * one;
	public static final float ten = 10.0F * one;
	public static final float eleven = 11.0F * one;
	public static final float twelve = 12.0F * one;
	public static final float thirteen = 13.0F * one;
	public static final float fourteen = 14.0F * one;
	public static final float fifteen = 15.0F * one;
	public static final float sixteen = 16.0F * one;
	
	public static final double dzero = 0.0;
	public static final double done = 0.0625;
	public static final double dtwo = 2.0 * done;
	public static final double dthree = 3.0 * done;
	public static final double dfour = 4.0 * done;
	public static final double dfive = 5.0 * done;
	public static final double dsix = 6.0 * done;
	public static final double dseven = 7.0 * done;
	public static final double deight = 8.0 * done;
	public static final double dnine = 9.0 * done;
	public static final double dten = 10.0 * done;
	public static final double deleven = 11.0 * done;
	public static final double dtwelve = 12.0 * done;
	public static final double dthirteen = 13.0 * done;
	public static final double dfourteen = 14.0 * done;
	public static final double dfifteen = 15.0 * done;
	public static final double dsixteen = 16.0 * done;
	
	public static final float half = one / 2.0F;
	public static final float quarter = one / 4.0F;
	
	private PixelSet( )
	{
		//Do Nothing
	}
	
	public static PixelSet getInstance( )
	{
		if(instance == null)
		{
			instance = new PixelSet();
		}
		
		return instance;
	}
	
	public static float getPx(float num_)
	{
		return one * num_;
	}
	
	public static double getdPx(double num_)
	{
		return done * num_;
	}

}
