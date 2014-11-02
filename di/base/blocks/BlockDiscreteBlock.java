





package kappafox.di.base.blocks;

import static net.minecraftforge.common.util.ForgeDirection.UP;

import java.util.List;

import kappafox.di.DiscreteIndustry;
import kappafox.di.base.tileentities.TileEntityDiscreteBlock;
import kappafox.di.electrics.DiscreteElectrics;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockDiscreteBlock extends Block
{
	protected boolean isTileProvider = true;
	protected int renderType;
	protected boolean lockRefresh;
	
	protected IIcon[] textures = new IIcon[6];
	protected IIcon[] icons = new IIcon[6];
	
	protected IIcon defaultIcon;
	
	
	public BlockDiscreteBlock(Material mat, int renderID)
	{
		super(mat);
		this.setCreativeTab(CreativeTabs.tabAllSearch);
		this.setBlockName("discreteCable");
		this.setHardness(3.0F);
		this.setResistance(150.0F);
		this.renderType = renderID;
		this.isTileProvider = true;
	}
	
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ireg)
	{	
		
		defaultIcon = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "blockDiscreteCable");
	
		for(int i = 0; i < 6; i++)
		{
			textures[i] = defaultIcon;
			icons[i] = defaultIcon;
		}
		
		this.blockIcon = icons[0];
		
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tabs, List list)
	{
		list.add(new ItemStack(item, 1, 0));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		return icons[meta]; 
	}

	@Override
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side)
    {
    	TileEntityDiscreteBlock tile = (TileEntityDiscreteBlock)world.getTileEntity(x, y, z);
    	
    	if(tile != null)
    	{
    		Block target = tile.getTextureSourceBlock(side);
    		
    		if(target != null && tile.getBlockTexture(side) != null)
    		{
    			return tile.getBlockTexture(side);
    		}
    	}
    	return textures[0];
    }
	
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack istack)
    {
		//thankyou mojang <3
		int magic = MathHelper.floor_double((double)(player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		
		TileEntityDiscreteBlock tile = (TileEntityDiscreteBlock)world.getTileEntity(x, y, z);
		
		short dir = 3;
	
		if(tile != null)
		{
			switch(magic)
			{
				case 0:
				{
					dir = 2;
					break;
				}
				
				case 2:
				{
					dir = 3;
					break;
				}
				
				case 3:
				{
					dir = 4;
					break;
				}
				
				case 1:
				{
					dir = 5;
					break;
				}
			}
		
			tile.setFace(dir);
			tile.setTextureOrientation(dir);
		}
    }
    
	@Override
    public boolean hasTileEntity(int metadata)
    {
        return isTileProvider;
    }
    
    @Override
    public TileEntity createTileEntity(World world, int metadata)
    {
    	
        if(isTileProvider)
        {
        	TileEntityDiscreteBlock tile = new TileEntityDiscreteBlock(this, metadata);
            return tile;
        }
        return null;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public boolean renderAsNormalBlock( )
    {
    	return true;
    }
    
    @Override
    public boolean isOpaqueCube( )
    {
    	return true;
    }

    
    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderType( )
    {
    	return renderType;
    }
	
	@Override
	public int damageDropped(int meta)
	{
		return meta;
	}
	
    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z)
    {
    	return new ItemStack(Items.apple);
    }
}
