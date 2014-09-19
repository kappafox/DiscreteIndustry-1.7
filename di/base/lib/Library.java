package kappafox.di.base.lib;

public class Library
{
	//public static final IC2Data ic2 = IC2Data.getInstance();
	public static final DiscreteID dibi = new DiscreteID();
	
	public Library( )
	{

	}
	
	public void preInit( )
	{
		
	}
	
	public void init( )
	{
		IC2Data.initialise();
	}
	
	public void postInit( )
	{
		
	}
	

	
}
