package com.sci.machinery.block.computer;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

/**
 * SciMachinery
 * 
 * @author sci4me
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

public class TileEntityComputer extends TileEntity
{
	private Computer computer;

	public void updateEntity()
	{
		super.updateEntity();

		if(!this.worldObj.isRemote)
		{
			System.out.println(this.computer);

			if(computer == null)
			{
				computer = new Computer(this.worldObj);
			}
		}
	}

	public void validate()
	{
		super.validate();
	}

	public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readFromNBT(par1NBTTagCompound);

		if(!this.worldObj.isRemote)
		{
			this.computer = Computer.fromNBT(this.worldObj, par1NBTTagCompound);
		}
	}

	public void writeToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeToNBT(par1NBTTagCompound);

		if(!this.worldObj.isRemote)
		{
			this.computer.writeToNBT(par1NBTTagCompound);
		}
	}
}