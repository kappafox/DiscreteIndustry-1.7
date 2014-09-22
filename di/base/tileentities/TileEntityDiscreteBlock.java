package kappafox.di.base.tileentities;

import kappafox.di.base.tileentities.TileEntitySubtype;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityDiscreteBlock extends TileEntitySubtype
{
	private int facadeBlockID = 810;					//the current block we look like
	private int facadeMeta = 0;						//the damage value of the meta based facade
	private short facing;
	private int colour = 16777215;
	private int var1 = 0;
	//private int subType = 0;
	private boolean fullColour = false;
	private int textureOrienation = 0;
	private short direction = 0;
	
	private String[] blockNames = new String[6];
	private int[] blockMetas = new int[6];
	private int[] blockSides = new int[6];
	
	private String originalBlockName = "";
	private int originalMeta = 0;
	
	public TileEntityDiscreteBlock( )
	{
		this(Blocks.stone, 0);
	}
	
	
	public TileEntityDiscreteBlock(Block block, int meta)
	{
		for(int i = 0; i < 6; i++)
		{
			blockNames[i] = this.getBlockName(block);
			blockMetas[i] = meta;
			blockSides[i] = i;
		}
		
		originalBlockName = this.getBlockName(block);
		originalMeta = meta;
		setSubtype(0);
	}
	
	public TileEntityDiscreteBlock(Block block, int meta, int sub)
	{
		this(block, meta);
		super.setSubtype(sub);
	}
	
	private String getBlockName(Block block)
	{
		if(block != null)
		{
			return Block.blockRegistry.getNameForObject(block);
		}
		
		return null;
	}
	
	public void setAllTextureSources(Block block, int meta, int side)
	{
		for(int i = 0; i < 6; i++)
		{
			this.setTextureSource(block, meta, side, i);
		}
	}
	
	public void setAllTexturesFromSource(Block block, int meta)
	{
		for(int i = 0; i < 6; i++)
		{
			this.setTextureSource(block, meta, i, i);
		}
	}
		
	//target name, target Meta, target Side, Side actually clicked of this block
	public void setTextureSource(Block block, int meta, int side, int hitside)
	{
		if(side >= 0 || side < blockNames.length)
		{
			blockNames[hitside] = this.getBlockName(block);
			blockMetas[hitside] = meta;
			blockSides[hitside] = side;
		}
	}
	
	public String getTextureSource(int side)
	{
		if(side < 0 || side >= blockNames.length)
		{
			return "";
		}

		return blockNames[side];
	}

	
	public void setTextureSourceMeta(int meta, int side)
	{
		blockMetas[side] = meta;
	}
	
	public int getTextureSourceMeta(int side)
	{
		if(side < 0 || side >= blockMetas.length)
		{
			return 0;
		}
		

		return blockMetas[side];	


		//return facadeMeta;		
	}
	
	//side of this block to get from
	public int getTextureSourceSide(int side)
	{
		if(side < 0 || side >= blockSides.length)
		{
			return 0;
		}
		
		return blockSides[side];
	}

	public int getBlockColor( )
	{
		return colour;
	}
	
	public void setBlockColor(int col)
	{
		colour = col;
	}

	private boolean changeFace = true;


	public short getFace( )
	{
		return facing;
	}

	public void setFace(short face)
	{		
		if(changeFace == true )
		{
			facing = face;	
		}
		else
		{
			changeFace = true;
		}
			
		this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
		//this.worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
	}
	
	public int getVariable( )
	{
		return var1;
	}
	
	public void setVariable(int var)
	{
		var1 = var;
	}
	
	public int getTextureOrientation( )
	{
		return textureOrienation;
	}
	
	public void setTextureOrientation(int orient)
	{
		textureOrienation = orient;
	}
	
	public short getDirection( )
	{
		return direction;
	}
	
	public ForgeDirection getForgeDirection( )
	{
		return ForgeDirection.getOrientation(this.getDirection());
	}
	
	public void setDirection(short dir)
	{
		direction = dir;
	}
	
	public void setOriginalBlock(Block block)
	{
		originalBlockName = this.getBlockName(block);
	}
	
	public String getOriginalBlockName( )
	{
		return originalBlockName;
	}
	
	public int getOriginalMeta( )
	{
		return originalMeta;
	}
	
	public IIcon getBlockTexture(int side)
	{
		return this.getTextureSourceBlock(side).getIcon(blockSides[side], blockMetas[side]);
	}
	
	public Block getTextureSourceBlock(int side)
	{
		return (Block)Block.blockRegistry.getObject(blockNames[side]);
	}
	

		
	public void writeToNBT(NBTTagCompound nbt)
	{
		
		super.writeToNBT(nbt);
		
		nbt.setInteger("facadeBlockID", facadeBlockID);
		nbt.setInteger("facadeMeta", facadeMeta);
		nbt.setShort("facing", facing);
		nbt.setInteger("colour", colour);
		nbt.setInteger("var1", var1);
		//nbt.setInteger("subType", subType);
		nbt.setInteger("torient", textureOrienation);
		nbt.setShort("direction", direction);
		
		NBTTagList blockNameList = new NBTTagList();
		NBTTagCompound tag = null;
		
		for(int i = 0; i < blockNames.length; i++)
		{
			tag = new NBTTagCompound();

			tag.setString("Name" + i, blockNames[i]);
			
			blockNameList.appendTag(tag);
		}
		
		nbt.setTag("blockNames", blockNameList);
		//nbt.setIntArray("blockIDs", blockIDs);
		nbt.setIntArray("blockMetas", blockMetas);
		nbt.setIntArray("blockSides", blockSides);
		
		nbt.setBoolean("fullColour", fullColour);
		
		nbt.setString("originalBlockName", originalBlockName);
		nbt.setInteger("originalMeta", originalMeta);
		
	}
	
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		
		facadeBlockID = nbt.getInteger("facadeBlockID");
		facadeMeta = nbt.getInteger("facadeMeta");
		facing = nbt.getShort("facing");
		colour = nbt.getInteger("colour");
		var1 = nbt.getInteger("var1");
		//subType = nbt.getInteger("subType");
		textureOrienation = nbt.getInteger("torient");
		
		NBTTagList blockNameList = nbt.getTagList("blockNames", Constants.NBT.TAG_COMPOUND);
		for(int i = 0; i < blockNameList.tagCount(); i++)
		{
			NBTTagCompound tag = blockNameList.getCompoundTagAt(i);
			
			blockNames[i] = tag.getString("Name" + i);	
		}
		
		//blockIDs = nbt.getIntArray("blockIDs");
		blockMetas = nbt.getIntArray("blockMetas");
		blockSides = nbt.getIntArray("blockSides");
		
		fullColour = nbt.getBoolean("fullColour");
		direction = nbt.getShort("direction");
		
		originalBlockName = nbt.getString("originalBlockName");
		originalMeta = nbt.getInteger("originalMeta");
		
		//sometimes this happens before we have a valid world object
		if(worldObj != null)
		{
			this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
	}

	
	
	@Override
    public Packet getDescriptionPacket()
    {
		NBTTagCompound send = new NBTTagCompound();
		this.writeToNBT(send);
		
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, send);
    }
	
	@Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet)
    {
    	NBTTagCompound tag = packet.func_148857_g();
    	readFromNBT(tag);

    }
	

	public boolean isFullColour( )
	{
		return fullColour;
	}
	
	public void setFullColour(boolean b)
	{
		fullColour = b;
	}
	
	public boolean isSideOriginalTexture(int side)
	{
		if((this.getTextureSource(side).equals(this.getOriginalBlockName())) && (this.getTextureSourceMeta(side) == this.getSubtype()))
		{
			return true;
		}
		
		return false;
	}

	protected int directionToSide(ForgeDirection dir)
	{
		
		if(dir == ForgeDirection.DOWN)
		{
			return 0;
		}
		
		if(dir == ForgeDirection.UP)
		{
			return 1;
		}
		
		if(dir == ForgeDirection.NORTH)
		{
			return 2;
		}
		
		if(dir == ForgeDirection.SOUTH)
		{
			return 3;
		}
		
		if(dir == ForgeDirection.WEST)
		{
			return 4;
		}
		
		if(dir == ForgeDirection.EAST)
		{
			return 5;
		}
		
		return 0;
	}
}
