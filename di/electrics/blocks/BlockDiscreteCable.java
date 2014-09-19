





package kappafox.di.electrics.blocks;

import java.util.List;

import kappafox.di.DiscreteIndustry;
import kappafox.di.base.blocks.BlockDiscreteBlock;
import kappafox.di.electrics.DiscreteElectrics;
import kappafox.di.electrics.tileentities.TileEntityDiscreteCable;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockDiscreteCable extends BlockDiscreteBlock
{
	//private Icon faceTexture;
	
	// 0 = no cable
	// 1 = tin
	// 2 = copper
	// 3 = gold
	// 4 = glass
	// 5 = HV
	


	public BlockDiscreteCable(Material mat, int renderID)
	{
		super(mat, renderID);
		this.setBlockName("discreteCable");
		this.setResistance(150);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ireg)
	{	
		IIcon defaultIcon = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "blockDiscreteCable");
		this.blockIcon = defaultIcon;
		
		icons[0] = defaultIcon;
		icons[1] = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "icon_blockDiscreteCableTin");
		icons[2] = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "icon_blockDiscreteCableCopper");
		icons[3] = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "icon_blockDiscreteCableGold");
		icons[4] = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "icon_blockDiscreteCableGlass");
		icons[5] = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "icon_blockDiscreteCableIron");
	
		for(int i = 0; i < 6; i++)
		{
			textures[i] = defaultIcon;
		}
		

		
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tabs, List list)
	{
		// block id, count, meta	
		
		for(int i = 0; i < 6; i++)
		{
			list.add(new ItemStack(item, 1, i));
		}		
	}
	   
    @Override
    public TileEntity createTileEntity(World world, int meta)
    {
    	
        if(this.isTileProvider == true)
        {
        	TileEntityDiscreteCable tile = new TileEntityDiscreteCable(meta);
        	tile.setAllTexturesFromSource(DiscreteElectrics.discreteCableBlock, meta);
        	tile.setOriginalBlock(DiscreteElectrics.discreteCableBlock);
        	tile.setMeta(meta);
            return tile;
        }
        return null;
    }

}
