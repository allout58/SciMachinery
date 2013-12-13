package com.sci.machinery.block;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.world.IBlockAccess;
import com.sci.machinery.core.TileSci;
import com.sci.machinery.core.TravellingItem;

public class TileTube extends TileSci
{
	private List<TravellingItem> items;

	public TileTube()
	{
		items = new ArrayList<TravellingItem>();
	}

	public List<TravellingItem> getItems()
	{
		return items;
	}

	@Override
	public void updateEntity()
	{
		if(!items.isEmpty())
		{
			TileEntity[] t = getAdjacentTiles(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
			if(allNull(t))
			{
				if(!this.worldObj.isRemote)
					this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.xCoord, this.yCoord, this.zCoord, items.get(0).getStack()));
				items.remove(0);
			}
			else
			{
				for(int i = 0; i < t.length; i++)
				{
					if(!items.isEmpty())
					{
						if(t[i] instanceof IInventory)
						{
							ItemStack s = TileEntityHopper.insertStack((IInventory) t[i], items.remove(0).getStack(), 0);
							if(!this.worldObj.isRemote)
								this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.xCoord, this.yCoord, this.zCoord, s));
						}
						else if(t[i] instanceof TileTube)
						{
							((TileTube) t[i]).addItem(items.remove(0));
						}
					}
				}
			}
		}
	}

	private boolean allNull(TileEntity[] t)
	{
		boolean ret = true;
		for(TileEntity te : t)
			if(te != null)
				ret = false;
		return ret;
	}

	public TileEntity[] getAdjacentTiles(IBlockAccess world, int x, int y, int z)
	{
		TileEntity[] t = new TileEntity[6];
		t[4] = world.getBlockTileEntity(x + 1, y, z);
		t[5] = world.getBlockTileEntity(x - 1, y, z);
		t[1] = world.getBlockTileEntity(x, y + 1, z);
		t[0] = world.getBlockTileEntity(x, y - 1, z);
		t[2] = world.getBlockTileEntity(x, y, z + 1);
		t[3] = world.getBlockTileEntity(x, y, z - 1);
		return t;
	}

	public void addItem(TravellingItem travellingItem)
	{
		items.add(travellingItem);
	}
}