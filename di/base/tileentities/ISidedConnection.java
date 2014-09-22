package kappafox.di.base.tileentities;

public interface ISidedConnection 
{
	public abstract boolean getConnection(short direction);
	public abstract void setConnection(short direction, boolean state);
	public abstract void toggleConnection(short direction);
	public abstract boolean[] getAllConnections();
	public abstract void setAllConnections(boolean state);
}
