package kappafox.di.base.network;

import kappafox.di.transport.DiscreteTransport;
import kappafox.di.transport.blocks.SubBlockStorageRack;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class ServerSideBlockFunctions
{
	/*
	public static void onBlockActivated(int id, int x, int y, int z, int dimension, Player player, int side, float hitx, float hity, float hitz)
	{
		if(player instanceof EntityPlayerMP)
		{
			EntityPlayerMP p = (EntityPlayerMP)player;
			Block target = Block.blocksList[id];
			
			if(target != null)
			{
				if(id == DiscreteTransport.discreteTransportMetaBlockID)
				{
					SubBlockStorageRack sbsr = new SubBlockStorageRack(); 
					sbsr.onBlockActivated(p.worldObj, x, y, z, p, side, hitx, hity, hitz, true);
				}
				else
				{
					target.onBlockActivated(p.worldObj, x, y, z, p, side, hitx, hity, hitz);
				}
			}
		}
	}

	public static void onBlockClicked(int id, int x, int y, int z, int d, Player player, int side, float hitx, float hity, float hitz)
	{
		if(player instanceof EntityPlayerMP)
		{
			EntityPlayerMP p = (EntityPlayerMP)player;
			Block target = Block.blocksList[id];
			
			if(target != null)
			{
				if(id == DiscreteTransport.discreteTransportMetaBlockID)
				{
					System.out.println("Got a click!");
					SubBlockStorageRack sbsr = new SubBlockStorageRack(); 
					sbsr.onBlockClicked(p.worldObj, x, y, z, p, hitx, hity, hitz);
				}
				else
				{
					//tar.onBlockClicked(p.worldObj, x, y, z, p, hitx, hity, hitz);
				}
			}
		}
	}
	*/
}
