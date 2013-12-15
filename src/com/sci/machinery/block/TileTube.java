package com.sci.machinery.block;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.ForgeDirection;
import com.sci.machinery.core.ITubeConnectable;
import com.sci.machinery.core.Speed;
import com.sci.machinery.core.TravellingItem;
import com.sci.machinery.network.PacketAddItem;
import com.sci.machinery.network.PacketTypeHandler;
import cpw.mods.fml.common.network.PacketDispatcher;

public class TileTube extends TileEntity implements ITubeConnectable
{
	protected List<TravellingItem> items;
	private int timer;
	protected Speed speed;

	public TileTube()
	{
		this.speed = Speed.MEDIUM;
		this.items = new ArrayList<TravellingItem>();
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
			timer++;
			if(timer == speed.delay)
			{
				timer = 0;
				if(!items.isEmpty())
				{
					TileEntity[] t = getAdjacentTiles(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
					if(allNull(t))
					{
						this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.xCoord, this.yCoord, this.zCoord, items.get(0).getStack()));
						items.remove(0);
					}
					else
					{
						for(int i = 0; i < t.length; i++)
						{
							if(!items.isEmpty())
							{
								if(t[i] instanceof ISidedInventory)
								{
									ISidedInventory inv = (ISidedInventory) t[i];
									int[] aint = inv.getAccessibleSlotsFromSide(ForgeDirection.OPPOSITES[i]);
									ItemStack stack = items.remove(0).getStack();
									for(int j = 0; j < aint.length; ++j)
									{
										if(stack != null && inv.canInsertItem(aint[j], stack, ForgeDirection.OPPOSITES[i]))
										{
											ItemStack s = TileEntityHopper.insertStack(inv, stack, ForgeDirection.OPPOSITES[i]);
											if(s != null)
												this.addItem(new TravellingItem(s));
										}
									}
								}
								else if(t[i] instanceof IInventory)
								{
									ItemStack s = TileEntityHopper.insertStack((IInventory) t[i], items.remove(0).getStack(), i);
									if(s != null)
										this.addItem(new TravellingItem(s));
								}
							}
						}

						boolean sent = true;
						for(int i = 0; i < t.length; i++)
						{
							if(!items.isEmpty())
							{
								if(t[i] instanceof ITubeConnectable && ((ITubeConnectable) t[i]).canAcceptItems())
								{
									if(i != items.get(0).getLastDir() || items.get(0).getLastDir() == -1)
									{
										items.get(0).setLastDir(ForgeDirection.OPPOSITES[i]);
										((ITubeConnectable) t[i]).addItem(items.remove(0));
										sent = false;
									}
								}
							}
						}
						if(!sent && !items.isEmpty())
						{
							this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.xCoord, this.yCoord, this.zCoord, items.remove(0).getStack()));
						}
					}
				}
			}
		}
		else
		{
			timer = 0;
		}
	}

	public Speed getSpeed()
	{
		return speed;
	}

	public void setSpeed(Speed speed)
	{
		this.speed = speed;
	}

	protected boolean allNull(TileEntity[] t)
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
		for(int i = 0; i < t.length; i++)
		{
			t[i] = world.getBlockTileEntity(x + ForgeDirection.getOrientation(i).offsetX, y + ForgeDirection.getOrientation(i).offsetY, z + ForgeDirection.getOrientation(i).offsetZ);
		}
		return t;
	}

	public void addItem(TravellingItem travellingItem)
	{
		items.add(travellingItem);
	}

	@Override
	public boolean canAcceptItems()
	{
		return true;
	}

	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readFromNBT(par1NBTTagCompound);
		NBTTagList nbttaglist = par1NBTTagCompound.getTagList("Items");
		items.clear();

		for(int i = 0; i < nbttaglist.tagCount(); ++i)
		{
			NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist.tagAt(i);
			int j = nbttagcompound1.getInteger("Slot");

			this.addItem(new TravellingItem(ItemStack.loadItemStackFromNBT(nbttagcompound1)));
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeToNBT(par1NBTTagCompound);
		NBTTagList nbttaglist = new NBTTagList();

		for(int i = 0; i < this.items.size(); ++i)
		{
			if(items.get(i) == null)
				continue;
			if(items.get(i).getStack() == null)
				continue;
			NBTTagCompound nbttagcompound1 = new NBTTagCompound();
			nbttagcompound1.setInteger("Slot", i);
			this.items.get(i).getStack().writeToNBT(nbttagcompound1);
			nbttaglist.appendTag(nbttagcompound1);
		}

		par1NBTTagCompound.setTag("Items", nbttaglist);
	}

	public void validate()
	{
		super.validate();
		for(TravellingItem item : items)
		{
			ItemStack stack = item.getStack();
			PacketDispatcher.sendPacketToAllAround(xCoord, yCoord, zCoord, 128, worldObj.provider.dimensionId, PacketTypeHandler.populatePacket(new PacketAddItem(xCoord, yCoord, zCoord, stack.itemID, stack.stackSize)));
		}
	}

	public void breakTube()
	{
		if(!worldObj.isRemote && !items.isEmpty())
		{
			while(!items.isEmpty())
			{
				this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.xCoord, this.yCoord, this.zCoord, items.remove(0).getStack()));
			}
		}
	}
}