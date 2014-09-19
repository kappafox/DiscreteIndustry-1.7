/*===================================================================================
 *	Class: IC2Data.java
 *		Purpose: Supplies Industrial craft information to objects that require it. 
 *					Information provided by this class should not duplicate the
 *					functionality of the IC2 API, but give additional information that
 *					can be accessed via reflection or other means.
 *		
 *		Created:	-/-/-
 *		Modified:	15/4/14
 *		Modified By:	Kappafox
 ===================================================================================*/

package kappafox.di.base.lib;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import net.minecraft.item.ItemStack;
import ic2.api.energy.tile.IEnergyConductor;

public class IC2Data
{
	
	private static IC2Data instance;
	
	//cable array index are not related to the IC2 cable types!
	public static double[] cableLoss;
	public static int[] cableInsulationMeltEnergy;
	public static int[] cableInsulationAbsorbEnergy;
	public static int[] cableMaximumCapacity;
	public static String[] cableName;
	
	//these cable types are those used internally by IC2
	private static final short IC2_CABLE_TIN = 13;
	private static final short IC2_CABLE_COPPER = 0;
	private static final short IC2_CABLE_GOLD	= 3;
	private static final short IC2_CABLE_GLASS = 9;
	private static final short IC2_CABLE_HV = 6;
	
	private static final String IC2_TILEENTITYCABLE_NAME = "ic2.core.block.wiring.TileEntityCable";
	
	private IC2Data( )
	{

	}
	
	public static void initialise( )
	{
		
		cableMaximumCapacity = new int[6];
		cableLoss = new double[6];
		cableInsulationMeltEnergy = new int[6];
		cableName = new String[6];
		cableInsulationAbsorbEnergy = new int[6];
		
		Class<?> c = null;
		Object o = null;
		
		Method getConductionLoss = null;
		Method getMaxCapacity = null;
		Method changeType = null;
		
		Field ctype = null;
		
		try
		{
			c = Class.forName(IC2_TILEENTITYCABLE_NAME);
			o = c.newInstance();
			
			getConductionLoss = c.getDeclaredMethod("getConductionLoss");
			getMaxCapacity = c.getDeclaredMethod("getMaxCapacity", int.class);
			ctype = c.getDeclaredField("cableType");
		}
		catch(Exception e_)
		{
			System.out.println(e_);
		}
		
		if(c != null)
		{		
			try
			{
				cableMaximumCapacity[0] = Integer.MAX_VALUE;
				cableMaximumCapacity[1] = (Integer)getMaxCapacity.invoke(o, IC2_CABLE_TIN);
				cableMaximumCapacity[2] = (Integer)getMaxCapacity.invoke(o, IC2_CABLE_COPPER);
				cableMaximumCapacity[3] = (Integer)getMaxCapacity.invoke(o, IC2_CABLE_GOLD);
				cableMaximumCapacity[4] = (Integer)getMaxCapacity.invoke(o, IC2_CABLE_GLASS);
				cableMaximumCapacity[5] = (Integer)getMaxCapacity.invoke(o, IC2_CABLE_HV);
				
				
				cableLoss[0] = Double.MAX_VALUE;		//no cable
				ctype.set(o, IC2_CABLE_TIN);
				cableLoss[1] = (Double)getConductionLoss.invoke(o);
				ctype.set(o, IC2_CABLE_COPPER);
				cableLoss[2] = (Double)getConductionLoss.invoke(o);					//Ins copper
				ctype.set(o, IC2_CABLE_GOLD);
				cableLoss[3] = (Double)getConductionLoss.invoke(o);					//2xIns gold
				ctype.set(o, IC2_CABLE_GLASS);
				cableLoss[4] = (Double)getConductionLoss.invoke(o);					//glass
				ctype.set(o, IC2_CABLE_HV);
				cableLoss[5] = (Double)getConductionLoss.invoke(o);					//4xIns HV

				for(int i = 0; i < 6; i++)
				{
					cableInsulationMeltEnergy[i] = 9001;
				}
				

				cableInsulationAbsorbEnergy[0] = Integer.MAX_VALUE;
				cableInsulationAbsorbEnergy[1] = cableMaximumCapacity[1];
				cableInsulationAbsorbEnergy[2] = cableMaximumCapacity[2];
				cableInsulationAbsorbEnergy[3] = cableMaximumCapacity[3];
				cableInsulationAbsorbEnergy[4] = cableMaximumCapacity[4];
				cableInsulationAbsorbEnergy[5] = cableMaximumCapacity[5];
				
				
				//For some reason the API does not respond well in the load phase, so hardset for now.
				cableName[0] = "Block";
				cableName[1] = "Tin Cable Block";//Items.getItem("insulatedtinCableBlock").getDisplayName();
				cableName[2] = "Copper Cable Block";//Items.getItem("insulatedCopperCableBlock").getDisplayName();
				cableName[3] = "Gold Cable Block";//Items.getItem("insulatedGoldCableBlock").getDisplayName();
				cableName[4] = "Glass Fibre Cable Block";//Items.getItem("glassFiberCableBlock").getDisplayName();
				cableName[5] = "HV Cable Block";//Items.getItem("insulatedIronCableItem").getDisplayName();
			}
			catch(Exception e)
			{
				System.out.println(e);
			}		
		}		
	}
	
	public static IC2Data getInstance( )
	{
		if(instance == null)
		{
			instance = new IC2Data();
		}
		
		return instance;
	}
}




