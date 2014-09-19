package kappafox.di.base;

import org.lwjgl.opengl.GL11;

import net.minecraft.util.Vec3;
import net.minecraftforge.common.util.ForgeDirection;
//import net.minecraftforge.common.ForgeDirection;
import kappafox.di.base.util.Point;
import kappafox.di.base.util.PointSet;

public class TranslationHelper
{
	
	public Point translate(ForgeDirection from, ForgeDirection to, Point point)
	{
		PointSet p = new PointSet(point.x, point.y, point.z, point.x + 0.5F, point.y + 0.5F, point.z + 0.5F);
		
		PointSet t = this.translate(from, to, p);
		
		point.x = t.x1;
		point.y = t.y1;
		point.z = t.z1;
		
		return point;
	}
	
	//you must call your own supporting methods to use this! no pop/push or translation here
	//this method WILL correct offsets that come from rotation, you must account for this
	public void rotateTessellator(ForgeDirection from, ForgeDirection to, boolean correct)
	{
		switch(to)
		{
			
			case NORTH:
			{
				GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
				break;
			}
			case SOUTH:
			{
				if(correct == true)
				{
					GL11.glTranslated(-0.5, 0, -0.5);
				}

				break;
			}
			
			case EAST:
			{
				GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
				if(correct == true)
				{
					GL11.glTranslated(0, 0, -0.5);
				}
				break;
			}
			
			case WEST:
			{
				GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
				if(correct == true)
				{
					GL11.glTranslated(-0.5, 0, 0);
				}
				break;
			}
			
			default:
			{
				break;
			}
		}
	}
	
	public PointSet translate(ForgeDirection from, ForgeDirection to, PointSet points)
	{
		switch(from)
		{
			case NORTH:
			{
				return translateNorthTo(to, points);
			}
			
			default:
			{
				return points;
			}
		}
	}
	
	private PointSet translateNorthTo(ForgeDirection to, PointSet points)
	{
		switch(to)
		{
			
			case UNKNOWN:
			{
				break;
			}
			
			case UP:
			{
				break;
			}
			
			case DOWN:
			{
				break;
			}
			
			case NORTH:
			{
				break;
			}
			

			
			case SOUTH:
			{
				float myz1 = points.z1;
				float myx1 = points.x1;
				float myx2 = points.x2;
				
				points.z1 = 1 - points.z2;
				points.z2 = 1 - myz1;
				
				points.x1 = 1 - myx2;
				points.x2 = 1 - myx1;
				
				break;
			}
			
			case EAST:
			{
				float myx1 = points.x1;
				float myx2 = points.x2;
				
				points.x1 = 1 - points.z2;
				points.x2 = 1 - points.z1;
				points.z1 = myx1;
				points.z2 = myx2;
				break;
			}
			
			case WEST:
			{
				float myx1 = points.x1;
				float myx2 = points.x2;
				
				points.x1 = points.z1;
				points.x2 = points.z2;
				points.z1 = 1 - myx2;
				points.z2 = 1 - myx1;
				break;
			}
		}
		
		return points;
	}
	
	public static float[] normaliseHitVector(int facing, Vec3 vec)
	{
		return normaliseHitVector(facing, (float)vec.xCoord, (float)vec.yCoord, (float)vec.zCoord);
	}
	
	public static float[] normaliseHitVector(int facing, float hitx, float hity, float hitz)
	{
		float[] normalised = {hitx, hity, hitz};
		
		switch(facing)
		{
			//South
			case 2:
			{
				normalised[0] = 1 - hitx;
				normalised[2] = 1;
				break;
			}
			
			//West
			case 5:
			{
				normalised[0] = 1 - hitz;
				normalised[2] = 1;
				break;
			}
			
			//East
			case 4:
			{
				normalised[0] = hitz;
				normalised[2] = 1;
				break;
			}
			
			
		}
		
		return normalised;
	}
	
	public static int getHitQuadrant(int facing, Vec3 vec)
	{
		return getHitQuadrant(facing, (float)vec.xCoord, (float)vec.yCoord, (float)vec.zCoord);
	}
	
	public static int getHitQuadrant(int facing, float hitx, float hity, float hitz)
	{
		float[] normalised = normaliseHitVector(facing, hitx, hity, hitz);
		
		if(hitx < 0.5)
		{
			if(hity > 0.5)
			{
				return 0;
			}
			
			return 2;
		}
		else
		{
			if(hity > 0.5)
			{
				return 1;
			}
			
			return 3;
		}
	}
}
