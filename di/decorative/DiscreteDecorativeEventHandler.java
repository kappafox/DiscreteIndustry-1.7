package kappafox.di.decorative;

import ic2.api.event.RetextureEvent;

import java.text.DecimalFormat;

import kappafox.di.base.tileentities.TileEntityDiscreteBlock;
import kappafox.di.electrics.DiscreteElectrics;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;




public class DiscreteDecorativeEventHandler 
{

	@SubscribeEvent
	public void onRetextureEvent(RetextureEvent event)
	{
		World world = event.world;
		TileEntity tile = world.getTileEntity(event.x, event.y, event.z);

		if(tile != null && tile instanceof TileEntityDiscreteBlock)
		{
			TileEntityDiscreteBlock disc = (TileEntityDiscreteBlock)tile;

			//Ladders, Fixtures/Struts, Weapon Racks, anything that uses a single texture for all sides
			if(disc.isFullColour() == true)
			{
				disc.setAllTextureSources(event.referencedBlock, event.referencedMeta, event.referencedSide);
			}
			else
			{
				disc.setTextureSource(event.referencedBlock, event.referencedMeta, event.referencedSide, event.side);
			}
			
			world.markBlockForUpdate(event.x, event.y, event.z);		
		}
	}
	
	@SubscribeEvent
	public void onEntityInteractEvent(EntityInteractEvent event)
	{
		Entity h = event.target;
		
		EntityPlayer p = event.entityPlayer;
		
		if(p != null)
		{
			ItemStack e = p.getCurrentEquippedItem();
			
			if(e != null)
			{
				if(e.getItem() == DiscreteElectrics.horseInspector)
				{
					if(h != null && h instanceof EntityHorse)
					{
						EntityHorse horse = (EntityHorse)h;

						IAttributeInstance ai = horse.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.movementSpeed);
						IAttributeInstance hp = horse.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.maxHealth);
						
						double jumpRange = 0.6;
						double speedRange = 0.225;
						
						double j = ((horse.getHorseJumpStrength() - 0.4) / jumpRange) * 100;
						double s = ((ai.getAttributeValue() - 0.1125) / speedRange) * 100;
						
						//System.out.println("J:" + horse.getHorseJumpStrength());
						//System.out.println("S:" + ai.getAttributeValue());
						DecimalFormat df = new DecimalFormat("##.#");
						String jump = df.format(j) + "%";
						String speed = df.format(s) + "%";
						
						//double jump = Math.round(horse.getHorseJumpStrength() * 1000.0) / 1000.0;
						//double speed = Math.round(ai.getAttributeValue() * 1000.0) / 1000.0;
						
						
						if(FMLCommonHandler.instance().getEffectiveSide().isServer() == true)
						{
							//p.sendChatToPlayer(ChatMessageComponent.createFromText("Hearts:" + df.format(hp.getAttributeValue()) + "   Jump:" + jump + "   Speed:" + speed));

						}
						
						event.setCanceled(true);
					}
				}
			}
		}
		

	}
}


