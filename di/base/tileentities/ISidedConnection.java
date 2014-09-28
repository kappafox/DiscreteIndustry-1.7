package kappafox.di.base.tileentities;

import java.util.HashMap;

import net.minecraftforge.common.util.ForgeDirection;

public interface ISidedConnection 
{
	public abstract boolean getConnection(short direction);
	public abstract void setConnection(short direction, boolean state);
	public abstract void toggleConnection(short direction);
	public abstract boolean[] getAllConnections();
	public abstract HashMap<ForgeDirection, Boolean> getConnectionMap();
	public abstract void setAllConnections(boolean state);
}
