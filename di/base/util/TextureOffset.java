package kappafox.di.base.util;

public class TextureOffset 
{
	private int[] offsets;
	
	public TextureOffset( )
	{
		offsets = new int[12];
	}
	
	public void setOffsetU(int dir_, int off_)
	{
		offsets[dir_] = off_;
	}
	
	public void setOffsetV(int dir_, int off_)
	{
		offsets[dir_ + 6] = off_;
	}
	
	public int getOffsetU(int dir_)
	{
		return offsets[dir_];
	}
	
	public int getOffsetV(int dir_)
	{
		return offsets[dir_ + 6];
	}
	
	public boolean hasOffset(int dir_)
	{
		if((this.getOffsetU(dir_) + this.getOffsetV(dir_)) != 0)
		{
			return true;
		}
		
		return false;
	}
}
