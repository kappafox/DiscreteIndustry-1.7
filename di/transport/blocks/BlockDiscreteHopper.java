package kappafox.di.transport.blocks;

import java.util.List;
import java.util.Random;

import kappafox.di.DiscreteIndustry;
import kappafox.di.transport.tileentities.TileEntityDiscreteHopper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockDiscreteHopper extends BlockContainer
{
	private static final Random rnd = new Random();
	private int renderID;
	@SideOnly(Side.CLIENT)
	private IIcon top;
	
	@SideOnly(Side.CLIENT)
	private IIcon[] sides;
	
	
	public BlockDiscreteHopper(Material mat, int rid)
	{
		super(mat);
		this.setCreativeTab(CreativeTabs.tabAllSearch);
		this.setBlockName("discreteHopper");
		this.setHardness(0.5F);
		renderID = rid;
		
		if(FMLCommonHandler.instance().getEffectiveSide().isClient() == true)
		{
			sides = new IIcon[3];
		}
	}
	
	@Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
    {	
		int meta = world.getBlockMetadata(x, y, z);
		
		switch(meta)
		{
			//half block
			case 0:
				this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
				break;
			
			//1/4 block
			case 1:
				this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.3F, 1.0F);
				break;
				
			//1/8 block
			case 2:
				this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.1F, 1.0F);
				break;
			
				
			default:
				this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		}
    }
	
	@Override
    public boolean onBlockActivated(World world, int xcoord, int ycoord, int zcoord, EntityPlayer player, int side, float par7, float par8, float par9)
    {
		TileEntityDiscreteHopper tile = (TileEntityDiscreteHopper)world.getTileEntity(xcoord, ycoord, zcoord);
		
		if(tile != null)
		{
			player.openGui(DiscreteIndustry.instance, 0, world, xcoord, ycoord, zcoord);
		}
		return true;
    }
	
	@Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
    {
    	int meta = world.getBlockMetadata(x, y, z);
    	
    	switch(meta)
    	{
        	case 0:
        		return AxisAlignedBB.getBoundingBox((double)x + this.minX, (double)y + this.minY, (double)z + this.minZ, (double)x + 1.0, (double)y + 0.5, (double)z + 1.0);		
        	case 1:
        		return AxisAlignedBB.getBoundingBox((double)x + this.minX, (double)y + this.minY, (double)z + this.minZ, (double)x + 1.0, (double)y + 0.3, (double)z + 1.0);
        	case 2:
        		return AxisAlignedBB.getBoundingBox((double)x + this.minX, (double)y + this.minY, (double)z + this.minZ, (double)x + 1.0, (double)y + 0.1, (double)z + 1.0);
    	}
    	
        return AxisAlignedBB.getBoundingBox((double)x + this.minX, (double)y + this.minY, (double)z + this.minZ, (double)x + this.maxX, (double)y + this.maxY, (double)z + this.maxZ);
    }
    
	@Override
	@SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister ireg)
    {		
		sides[0] = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "hoppers/blockFullHopper_side");
		sides[1] = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "hoppers/blockHalfHopper_side");
		sides[2] = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "hoppers/blockQuarterHopper_side");	
		
        this.top = ireg.registerIcon(DiscreteIndustry.MODID + ":" + "hoppers/blockDiscreteHopperTop");     
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
		if(side == 1 || side == 0)
		{
			return top;
		}
		
        return sides[meta];
    }

	@Override
	public int getRenderType()
	{
		return renderID;
	}
	
	@Override
	public int damageDropped(int meta)
	{
		return meta;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tabs, List list)
	{
		// block id, count, meta	
		list.add(new ItemStack(item, 1, 0));
		list.add(new ItemStack(item, 1, 1));
		list.add(new ItemStack(item, 1, 2));
	}
	
	@Override
    public int onBlockPlaced(World world, int x, int y, int z, int side, float hitx, float hity, float hitz, int meta)
	{	
		return super.onBlockPlaced(world, x, y, z, side, hitz, hity, hitz, meta);
	}

	@Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
		switch(meta)
		{
			case 0:
				return new TileEntityDiscreteHopper(15);
				
			case 1:
				return new TileEntityDiscreteHopper(10);
				
			case 2:
				return new TileEntityDiscreteHopper(5);
				
			default:
				return new TileEntityDiscreteHopper(0);
		}
    }
	
	@Override
	public boolean isOpaqueCube( )
	{
		return false;
	}
	
	@Override
    public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side)
    {
    	return false;
    }
	
	@Override
	public boolean renderAsNormalBlock( )
	{
		return false;
	}
	
	@Override
    public boolean isNormalCube(IBlockAccess world, int x, int y, int z)
    {
        return false;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess block, int x, int y, int z, int side)
    {
        return true;
    }
	
	@Override
    public void breakBlock(World par1World, int par2, int par3, int par4, Block par5, int par6)
    {
        TileEntityDiscreteHopper tileentityhopper = (TileEntityDiscreteHopper)par1World.getTileEntity(par2, par3, par4);

        if (tileentityhopper != null)
        {
            for (int j1 = 0; j1 < tileentityhopper.getSizeInventory(); ++j1)
            {
                ItemStack itemstack = tileentityhopper.getStackInSlot(j1);

                if (itemstack != null)
                {
                    float f = this.rnd.nextFloat() * 0.8F + 0.1F;
                    float f1 = this.rnd.nextFloat() * 0.8F + 0.1F;
                    float f2 = this.rnd.nextFloat() * 0.8F + 0.1F;

                    while (itemstack.stackSize > 0)
                    {
                        int k1 = this.rnd.nextInt(21) + 10;

                        if (k1 > itemstack.stackSize)
                        {
                            k1 = itemstack.stackSize;
                        }

                        itemstack.stackSize -= k1;
                        EntityItem entityitem = new EntityItem(par1World, (double)((float)par2 + f), (double)((float)par3 + f1), (double)((float)par4 + f2), new ItemStack(itemstack.getItem(), k1, itemstack.getItemDamage()));

                        if (itemstack.hasTagCompound())
                        {
                            entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
                        }

                        float f3 = 0.05F;
                        entityitem.motionX = (double)((float)this.rnd.nextGaussian() * f3);
                        entityitem.motionY = (double)((float)this.rnd.nextGaussian() * f3 + 0.2F);
                        entityitem.motionZ = (double)((float)this.rnd.nextGaussian() * f3);
                        par1World.spawnEntityInWorld(entityitem);
                    }
                }
            }

            par1World.func_147453_f(par2, par3, par4, par5);
        }

        super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }
}
