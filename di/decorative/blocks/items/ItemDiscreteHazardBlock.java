package kappafox.di.decorative.blocks.items;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;






public class ItemDiscreteHazardBlock extends ItemBlock
{
	
	public ItemDiscreteHazardBlock(Block block)
	{
		super(block);
		this.setHasSubtypes(true);
	}
	
	public String getUnlocalizedName(ItemStack istack_)
	{
		String name = "Unset";
		
		switch(istack_.getItemDamage())
		{
			case 0:
			{
				name = "Diagonal Hazard Block";
				break;
			}
			
			case 1:
			{
				name = "Arrow Hazard Block";
				break;
			}
			case 2:
			{
				name = "Red Diagonal Hazard Block";
				break;
			}
			case 3:
			{
				name = "Red Arrow Hazard Block";
				break;
			}
			case 4:
			{
				name = "Checkered Hazard Block";
				break;
			}
			case 5:
			{
				name = "Small Checkered Hazard Block";
				break;
			}
			case 6:
			{
				name = "Hazard Strip Block";
				break;
			}
			case 7:
			{
				name = "Angled Hazard Strip Block";
				break;
			}
			case 8:
			{
				name = "HAZBLOCK_NAME_8";
				break;
			}
			case 9:
			{
				name = "HAZBLOCK_NAME_9";
				break;
			}
			case 10:
			{
				name = "HAZBLOCK_NAME_10";
				break;
			}
			case 11:
			{
				name = "HAZBLOCK_NAME_11";
				break;
			}
			case 12:
			{
				name = "HAZBLOCK_NAME_12";
				break;
			}
			case 13:
			{
				name = "HAZBLOCK_NAME_13";
				break;
			}
			case 14:
			{
				name = "HAZBLOCK_NAME_14";
				break;
			}
			case 15:
			{
				name = "HAZBLOCK_NAME_15";
				break;
			}
			
			default:
			{
				name = "how did you get this?";
				break;
			}
		}
		
		return getUnlocalizedName() + "." + name;
	}
	
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack item_, EntityPlayer player_, List tooltip_, boolean par4_)
    {
    	int meta = item_.getItemDamage();
    	tooltip_.add("Wrench to change direction");
    	
    	if(meta == 6 || meta == 7)
    	{	
    		tooltip_.add("Obscurator Compatible");
    	}
    }
    
	
	@Override
	public int getMetadata(int par1_)
	{
		return par1_;
	}
}
