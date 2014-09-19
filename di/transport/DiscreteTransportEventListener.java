package kappafox.di.transport;

import ic2.api.event.RetextureEvent;

import java.text.DecimalFormat;

import kappafox.di.base.tileentities.TileEntityDiscreteBlock;
import kappafox.di.electrics.DiscreteElectrics;
import kappafox.di.transport.blocks.BlockDiscreteTransport;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;




public class DiscreteTransportEventListener 
{

	@SubscribeEvent
	public void onPlayerInteractEvent(PlayerInteractEvent event)
	{
		if(FMLCommonHandler.instance().getEffectiveSide().isServer() == true)
		{
			//System.out.println(event.action);
			this.StorageRackOnClick(event);
		}
	}
	
	@SubscribeEvent	
	private void StorageRackOnClick(PlayerInteractEvent event)
	{
		//this.mc.objectMouseOver = this.mc.renderViewEntity.rayTrace(d0, par1);
		EntityPlayer player = event.entityPlayer;
		
		MovingObjectPosition clickedOn = this.getMovingObjectPositionFromPlayer(player.worldObj, player, true);
		
		if(clickedOn != null && clickedOn.hitVec != null)
		{
			//System.out.println((clickedOn.hitVec.xCoord - event.x) + " " + (clickedOn.hitVec.yCoord - event.y) + " " + (clickedOn.hitVec.zCoord - event.z));
			switch(event.action)
			{
				case RIGHT_CLICK_BLOCK:
				{
					break;
				}
				
				case LEFT_CLICK_BLOCK:
				{
					Block target = player.worldObj.getBlock(event.x, event.y, event.z);
					
					if(target != null && target instanceof BlockDiscreteTransport)
					{
						BlockDiscreteTransport bdt = (BlockDiscreteTransport)target;
						
						float hitx = (float)clickedOn.hitVec.xCoord - event.x;
						float hity = (float)clickedOn.hitVec.yCoord - event.y;
						float hitz = (float)clickedOn.hitVec.zCoord - event.z;
									
						bdt.onBlockClicked(player.worldObj, event.x, event.y, event.z, player, event.face, hitx, hity, hitz);
					}
					break;
				}
				
				default:
				{
					
				}
			}
		}
	}
	
	//from Item
    protected MovingObjectPosition getMovingObjectPositionFromPlayer(World p_77621_1_, EntityPlayer p_77621_2_, boolean p_77621_3_)
    {
        float f = 1.0F;
        float f1 = p_77621_2_.prevRotationPitch + (p_77621_2_.rotationPitch - p_77621_2_.prevRotationPitch) * f;
        float f2 = p_77621_2_.prevRotationYaw + (p_77621_2_.rotationYaw - p_77621_2_.prevRotationYaw) * f;
        double d0 = p_77621_2_.prevPosX + (p_77621_2_.posX - p_77621_2_.prevPosX) * (double)f;
        double d1 = p_77621_2_.prevPosY + (p_77621_2_.posY - p_77621_2_.prevPosY) * (double)f + (double)(p_77621_1_.isRemote ? p_77621_2_.getEyeHeight() - p_77621_2_.getDefaultEyeHeight() : p_77621_2_.getEyeHeight()); // isRemote check to revert changes to ray trace position due to adding the eye height clientside and player yOffset differences
        double d2 = p_77621_2_.prevPosZ + (p_77621_2_.posZ - p_77621_2_.prevPosZ) * (double)f;
        Vec3 vec3 = Vec3.createVectorHelper(d0, d1, d2);
        float f3 = MathHelper.cos(-f2 * 0.017453292F - (float)Math.PI);
        float f4 = MathHelper.sin(-f2 * 0.017453292F - (float)Math.PI);
        float f5 = -MathHelper.cos(-f1 * 0.017453292F);
        float f6 = MathHelper.sin(-f1 * 0.017453292F);
        float f7 = f4 * f5;
        float f8 = f3 * f5;
        double d3 = 5.0D;
        if (p_77621_2_ instanceof EntityPlayerMP)
        {
            d3 = ((EntityPlayerMP)p_77621_2_).theItemInWorldManager.getBlockReachDistance();
        }
        Vec3 vec31 = vec3.addVector((double)f7 * d3, (double)f6 * d3, (double)f8 * d3);
        return p_77621_1_.func_147447_a(vec3, vec31, p_77621_3_, !p_77621_3_, false);
    }
}


