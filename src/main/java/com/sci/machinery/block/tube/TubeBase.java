package com.sci.machinery.block.tube;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import com.sci.machinery.block.TileTube;
import com.sci.machinery.block.tube.route.Router;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

/**
 * SciMachinery
 * 
 * @author sci4me
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public abstract class TubeBase implements ITubeConnectable
{
	public static final Class<? extends TubeBase> COBBLE = TubeCobble.class;
	public static final Class<? extends TubeBase> DETECTOR = TubeDetector.class;
	public static final Class<? extends TubeBase> PUMP = TubePump.class;
	public static final Class<? extends TubeBase> STONE = TubeStone.class;
	public static final Class<? extends TubeBase> VOID = TubeVoid.class;
	public static final Class<? extends TubeBase> VALVE = TubeValve.class;

	protected Router router;

	protected List<TravellingItem> items;
	protected Speed speed;
	protected TileTube tile;
	protected int timer;

	protected int r = 0;
	protected int g = 0;
	protected int b = 0;
	protected int a = 150;

	public TubeBase()
	{
		this.items = new ArrayList<TravellingItem>();
	}

	@Override
	public void addItem(TravellingItem item, TileEntity sender)
	{
		// TODO
		items.add(item);
	}

	public void breakTube()
	{
		while(!items.isEmpty())
		{
			getTile().worldObj.spawnEntityInWorld(new EntityItem(getTile().worldObj, getTile().xCoord, getTile().yCoord, getTile().zCoord, items.remove(0).getStack()));
		}
	}

	@Override
	public boolean canAcceptItems()
	{
		return true;
	}

	@Override
	public boolean canConnectTube(TileEntity e)
	{
		if(e instanceof TileTube)
		{
			if(((TileTube) e).getTube() instanceof TubeValve)
				return ((TubeValve) ((TileTube) e).getTube()).canConnectTube(e);
		}
		return e instanceof ITubeConnectable || e instanceof IInventory;
	}

	public int getR(int a)
	{
		return r;
	}

	public void setR(int r)
	{
		this.r = r;
	}

	public int getG(int a)
	{
		return g;
	}

	public void setG(int g)
	{
		this.g = g;
	}

	public int getB(int a)
	{
		return b;
	}

	public void setB(int b)
	{
		this.b = b;
	}

	public int getA(int a)
	{
		return this.a;
	}

	public void setA(int a)
	{
		this.a = a;
	}

	public List<TravellingItem> getItems()
	{
		return items;
	}

	public final TileTube getTile()
	{
		return tile;
	}

	public void invalidate()
	{

	}

	public boolean isPowering()
	{
		return false;
	}

	public final boolean isValid()
	{
		return tile != null;
	}

	@Override
	public void removeItem(int index)
	{
		if(FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT && !items.isEmpty())
		{
			items.remove(index);
		}
	}

	public void setSpeed(Speed fast)
	{
		this.speed = fast;
	}

	public final void setTile(TileTube tile)
	{
		this.tile = tile;
	}

	public void update()
	{
		if(!isValid())
			return;

		if(isValid() && !tile.isInvalid())
		{
			timer++;
			if(timer == speed.delay)
			{
				timer = 0;
				tick();
			}
		}
	}

	public abstract void tick();

	public void validate()
	{
		router = new Router(tile.worldObj, tile);
	}

	public void readFromNBT(NBTTagCompound tag)
	{
		this.speed = Speed.forDelay(tag.getInteger("delay"));
	}

	public void writeToNBT(NBTTagCompound tag)
	{
		tag.setInteger("delay", speed.delay);
	}

	public boolean isPowered()
	{
		return getTile().isPowered();
	}
}