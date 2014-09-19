package kappafox.di.base.compat;

import java.util.List;

import kappafox.di.base.TranslationHelper;
import kappafox.di.base.util.SideHelper;
import kappafox.di.transport.DiscreteTransport;
import kappafox.di.transport.blocks.BlockDiscreteTransport;
import kappafox.di.transport.tileentities.TileEntityStorageRack;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;


public class DiscreteWailaProvider implements IWailaDataProvider
{

	@Override
	public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		return null;
	}

	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		return currenttip;
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		if(SideHelper.onClient())
		{
			if(accessor.getTileEntity() instanceof TileEntityStorageRack)
			{
				TileEntityStorageRack tile = (TileEntityStorageRack)accessor.getTileEntity();
				
				
				switch(tile.getSize())
				{
					case 1:
					{
						int slot = 0;
						if(tile.hasContainer(slot))
						{
							
							if(tile.isContainerEmpty(slot) == false)
							{
								currenttip.add("Slot " + 0 + " : "  + (EnumChatFormatting.GOLD + tile.getContainerContent(0).getDisplayName()));
								currenttip.add("" + tile.getContainerContentCount(0) + "/" + tile.getMax(0));
							}
							else
							{
								currenttip.add("Empty");
							}
						}
						else
						{
							currenttip.add("Empty");
						}
						break;
					}
					
					case 2:
					{
						
						MovingObjectPosition mop = accessor.getPosition();
						float hity = (float)mop.hitVec.yCoord - mop.blockY;
						
						int half = 0;
						
						if(hity < 0.5)
						{
							half = 1;
						}

						if(tile.hasContainer(half))
						{
							
							if(tile.isContainerEmpty(half) == false)
							{
								currenttip.add("Slot " + half + " : "  + (EnumChatFormatting.GOLD + tile.getContainerContent(half).getDisplayName()));
								currenttip.add("" + tile.getContainerContentCount(half) + "/" + tile.getMax(half));
							}
							else
							{
								currenttip.add("Empty");
							}
						}
						else
						{
							currenttip.add("Empty");
						}
						break;
					}
					
					case 4:
					{
						
						MovingObjectPosition mop = accessor.getPosition();
						
						float hitx = (float)mop.hitVec.xCoord - mop.blockX;
						float hity = (float)mop.hitVec.yCoord - mop.blockY;
						float hitz = (float)mop.hitVec.zCoord - mop.blockZ;
						//int quadrant = TranslationHelper.getHitQuadrant(mop.sideHit, hitx, hity, hitz);
						
						float[] t = TranslationHelper.normaliseHitVector(mop.sideHit, hitx, hity, hitz);
						int quadrant = TranslationHelper.getHitQuadrant(3, t[0], t[1], t[2]);
						
						if(tile.hasContainer(quadrant))
						{
							
							if(tile.isContainerEmpty(quadrant) == false)
							{
								currenttip.add("Slot " + quadrant + " : "  + (EnumChatFormatting.GOLD + tile.getContainerContent(quadrant).getDisplayName()));
								currenttip.add("" + tile.getContainerContentCount(quadrant) + "/" + tile.getMax(quadrant));
							}
							else
							{
								currenttip.add("Empty");
							}
						}
						else
						{
							currenttip.add("Empty");
						}
						break;
					}
				}
			}
			
		}
		return currenttip;
	}

	@Override
	public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		if(SideHelper.onClient())
		{
			if(accessor.getTileEntity() instanceof TileEntityStorageRack)
			{
				TileEntityStorageRack tile = (TileEntityStorageRack)accessor.getTileEntity();
				
				if(tile.getSticky())
				{
					currenttip.add(EnumChatFormatting.DARK_GREEN + "Sticky Enabled");
				}
			}
		}
		return currenttip;
	}
	
	public static void callbackRegister(IWailaRegistrar registrar)
	{
		registrar.addConfig("WailaDemo", "wailademo.showbody", "Show Body");
		//registrar.registerHeadProvider(new DiscreteWailaProvider(), BlockDiscreteTransport.class);
		registrar.registerBodyProvider(new DiscreteWailaProvider(), BlockDiscreteTransport.class);
		registrar.registerTailProvider(new DiscreteWailaProvider(), BlockDiscreteTransport.class);
	}

}
