





package kappafox.di.decorative.blocks;

import java.util.List;

import kappafox.di.DiscreteIndustry;
import kappafox.di.base.blocks.BlockDiscreteBlock;
import kappafox.di.decorative.tileentities.TileEntityHazardBlock;
import kappafox.di.decorative.tileentities.TileEntityStripHazardBlock;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.IconFlipped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockHazard extends BlockDiscreteBlock
{
	
	@SideOnly(Side.CLIENT)
	public static IIcon[] diagonalIcons;
	
	@SideOnly(Side.CLIENT)
	public static IIcon[] arrowIcons;
	
	@SideOnly(Side.CLIENT)
	public static IIcon[] redDiagonalIcons;
	
	@SideOnly(Side.CLIENT)
	public static IIcon[] redArrowIcons;
	
	@SideOnly(Side.CLIENT)
	public static IIcon checkeredIcon;
	
	@SideOnly(Side.CLIENT)
	public static IIcon smallCheckeredIcon;
	
	@SideOnly(Side.CLIENT)
	public static IIcon discreteBlock;
	
	@SideOnly(Side.CLIENT)
	private int rid;
	
	@SideOnly(Side.CLIENT)
	private static IIcon hazardStripBlockIcon;
	
	@SideOnly(Side.CLIENT)
	private static IIcon YHS_HTOP;
	
	@SideOnly(Side.CLIENT)
	private static IIcon YHS_HMID;
	
	@SideOnly(Side.CLIENT)
	private static IIcon YHS_HBOT;
	
	@SideOnly(Side.CLIENT)
	private static IIcon YHS_VLEFT;
	
	@SideOnly(Side.CLIENT)
	private static IIcon YHS_VMID;
	
	@SideOnly(Side.CLIENT)
	private static IIcon YHS_VRIGHT;
	
	@SideOnly(Side.CLIENT)
	private static IIcon YHS_ANGLED_NW;
	
	@SideOnly(Side.CLIENT)
	private static IIcon YHS_ANGLED_NE;
	
	@SideOnly(Side.CLIENT)
	private static IIcon YHS_ANGLED_ES;
	
	@SideOnly(Side.CLIENT)
	private static IIcon YHS_ANGLED_SW;
	
	@SideOnly(Side.CLIENT)
	private static IIcon HAZARD_EDGE;
	
	public BlockHazard(Material mat, int renderID)
	{
		super(mat, renderID);
		this.setCreativeTab(CreativeTabs.tabAllSearch);
		this.setBlockName("hazardBlock");
		this.setHardness(3.0F);
		
		if(FMLCommonHandler.instance().getEffectiveSide().isClient() == true)
		{
			diagonalIcons = new IIcon[2];
			arrowIcons = new IIcon[4];
			redDiagonalIcons = new IIcon[2];
			redArrowIcons = new IIcon[4];
			rid = renderID;
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ireg_)
	{	
		super.registerBlockIcons(ireg_);
		diagonalIcons[0] = ireg_.registerIcon(DiscreteIndustry.MODID + ":" + "diagonalHazardBlock");
		diagonalIcons[1] = new IconFlipped(diagonalIcons[0], true, false);
		
		arrowIcons[0] = ireg_.registerIcon(DiscreteIndustry.MODID + ":" + "arrowHazardBlock0");
		arrowIcons[1] = ireg_.registerIcon(DiscreteIndustry.MODID + ":" + "arrowHazardBlock1");
		arrowIcons[2] = ireg_.registerIcon(DiscreteIndustry.MODID + ":" + "arrowHazardBlock2");
		arrowIcons[3] = ireg_.registerIcon(DiscreteIndustry.MODID + ":" + "arrowHazardBlock3");
		
		redDiagonalIcons[0] = ireg_.registerIcon(DiscreteIndustry.MODID + ":" + "diagonalRedHazardBlock");
		redDiagonalIcons[1] = new IconFlipped(redDiagonalIcons[0], true, false); 
		
		redArrowIcons[0] = ireg_.registerIcon(DiscreteIndustry.MODID + ":" + "arrowRedHazardBlock0");
		redArrowIcons[1] = ireg_.registerIcon(DiscreteIndustry.MODID + ":" + "arrowRedHazardBlock1");
		redArrowIcons[2] = ireg_.registerIcon(DiscreteIndustry.MODID + ":" + "arrowRedHazardBlock2");
		redArrowIcons[3] = ireg_.registerIcon(DiscreteIndustry.MODID + ":" + "arrowRedHazardBlock3");
		
		checkeredIcon = ireg_.registerIcon(DiscreteIndustry.MODID + ":" + "checkeredHazardBlock");
		
		smallCheckeredIcon = ireg_.registerIcon(DiscreteIndustry.MODID + ":" + "smallCheckeredHazardBlock");
		
		discreteBlock = ireg_.registerIcon(DiscreteIndustry.MODID + ":" + "blockDiscreteCable");
		
		hazardStripBlockIcon = ireg_.registerIcon(DiscreteIndustry.MODID + ":" + "hazardStripBlockIcon");
		
		YHS_HTOP = ireg_.registerIcon(DiscreteIndustry.MODID + ":" + "yhsHTop");
		YHS_HMID = ireg_.registerIcon(DiscreteIndustry.MODID + ":" + "yhsHMid");
		YHS_HBOT = ireg_.registerIcon(DiscreteIndustry.MODID + ":" + "yhsHBot");
		YHS_VLEFT = ireg_.registerIcon(DiscreteIndustry.MODID + ":" + "yhsVLeft");
		YHS_VMID = ireg_.registerIcon(DiscreteIndustry.MODID + ":" + "yhsVMid");
		YHS_VRIGHT = ireg_.registerIcon(DiscreteIndustry.MODID + ":" + "yhsVRight");

		YHS_ANGLED_NW = ireg_.registerIcon(DiscreteIndustry.MODID + ":" + "strip_angled_45_NW");	
		YHS_ANGLED_NE = ireg_.registerIcon(DiscreteIndustry.MODID + ":" + "strip_angled_45_NE");
		YHS_ANGLED_ES = ireg_.registerIcon(DiscreteIndustry.MODID + ":" + "strip_angled_45_ES");
		YHS_ANGLED_SW = ireg_.registerIcon(DiscreteIndustry.MODID + ":" + "strip_angled_45_SW");
		
		HAZARD_EDGE = ireg_.registerIcon(DiscreteIndustry.MODID + ":" + "hazardEdge");
	}

	
	@Override
	public int damageDropped(int meta_)
	{
		return meta_;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tabs, List list)
	{
		// block id, count, meta	
		
		for(int i = 0; i < 8; i++)
		{
			list.add(new ItemStack(item, 1, i));
		}		
	}
	
	
	@SideOnly(Side.CLIENT)
	public IIcon getCustomIcon(int side_, int meta_, int offset_)
	{
		return getIcon(side_, meta_ + offset_);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side_, int meta_)
	{
		
		switch(meta_)
		{
			case 0:
				return diagonalIcons[0];
			
			case 1:
				return arrowIcons[0];
				
			case 2:
				return redDiagonalIcons[0];
			
			case 3:
				return redArrowIcons[0];
			
			case 4:
				return checkeredIcon;
			
			case 5:
				return smallCheckeredIcon;
				
			case 6:
				return hazardStripBlockIcon;
				
			case 7:
				return hazardStripBlockIcon;
		}
		
		
		//custom strips and parts
		switch(meta_)
		{
			case 20:
				return YHS_HTOP;
				
			case 21:
				return YHS_HMID;
				
			case 22:
				return YHS_HBOT;
				
			case 23:
				return YHS_VLEFT;
				
			case 24:
				return YHS_VMID;
				
			case 25:
				return YHS_VRIGHT;
			
			case 30:
				return YHS_ANGLED_NW;
			
			case 31:
				return YHS_ANGLED_NE;
			
			case 32:
				return YHS_ANGLED_ES;
			
			case 33:
				return YHS_ANGLED_SW;
				
			case 80:
				return HAZARD_EDGE;
		
		}
		
		return null;
	}

	
	@Override
    public boolean onBlockActivated(World world_, int xcoord_, int ycoord_, int zcoord_, EntityPlayer player_, int side_, float par7, float par8, float par9)
    {	
		int meta = world_.getBlockMetadata(xcoord_, ycoord_, zcoord_);
		
		//if we are using one of the discrete blocks
		if(meta > 5)
		{
			return super.onBlockActivated(world_, xcoord_, ycoord_, zcoord_, player_, side_, par7, par8, par9);
		}
			
        return false;
        
    }

    

	
	@Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side)
    {
		
		int meta = world.getBlockMetadata(x, y, z);
		
		if(meta < 6)
		{
			TileEntityHazardBlock tile = (TileEntityHazardBlock)world.getTileEntity(x, y, z);
			
			if(tile != null)
			{
				switch(meta)
				{
					case 0:	//diagonal
						return diagonalIcons[tile.getFacing()];
					
					case 1:	//arrow
						return arrowIcons[tile.getFacing()];	
					
					case 2: //red diag
						return redDiagonalIcons[tile.getFacing()];
					
					case 3: //red arrow
						return redArrowIcons[tile.getFacing()];
					
				}
			}
			
			//non tile entity blocks
			switch(meta)
			{
				case 4:
					return checkeredIcon;
				
				case 5:
					return smallCheckeredIcon;
			}
		}
		
		//Hazard Strip
		if(meta == 6 || meta == 7)
		{
			//System.out.println(side_);
			return super.getIcon(world, x, y, z, side);
		}
		
		//fail safe
		return this.getIcon(side, world.getBlockMetadata(x, y, z));	
		
        
    }
	
	@Override
	public void onBlockPlacedBy(World world_, int x_, int y_, int z_, EntityLivingBase player_, ItemStack istack_)
	{
		//only run the placed code for blocks that can disguise
		if(world_.getBlockMetadata(x_, y_, z_) > 7)
		{
			super.onBlockPlacedBy(world_, x_, y_, z_, player_, istack_);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderType( )
	{
		return rid;	
	}
	
	@Override
	public TileEntity createTileEntity(World world_, int meta_)
	{
		if(this.hasTileEntity(meta_) == false)
		{
			return null;
		}
		
		if(meta_ == 6)
		{
			return new TileEntityStripHazardBlock(6);
		}
		
		if(meta_ == 7)
		{
			return new TileEntityStripHazardBlock(4);
		}
		
		return new TileEntityHazardBlock();
	}
	
	@Override
    public boolean hasTileEntity(int meta_)
    {
		//checkered has no tile
		if(meta_ == 4 || meta_ == 5)
		{
			return false;
		}
		
        return true;
    }
    


}
