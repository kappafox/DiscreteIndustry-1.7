package kappafox.di.decorative.tileentities;

public interface ISidedConnection 
{
	public abstract boolean getConnection(short direction_);
	public abstract void setConnection(short direction_, boolean state_);
	public abstract void toggleConnection(short direction_);
	public abstract boolean[] getAllConnections();
	public abstract void setAllConnections(boolean state_);
}
