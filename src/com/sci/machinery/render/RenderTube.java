package com.sci.machinery.render;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import com.sci.machinery.block.TileTube;

public class RenderTube extends TileEntitySpecialRenderer
{
	public static final double OF = 0.4D;
	public static final double O = 0.2D;
	public static final double OA = 0.05D;
	public static final double OO = O * 2;

	private final EntityItem dummyEntityItem = new EntityItem(null);
	private final RenderItem customRenderItem;

	public RenderTube()
	{
		customRenderItem = new RenderItem()
		{
			@Override
			public boolean shouldBob()
			{
				return false;
			}

			@Override
			public boolean shouldSpreadItems()
			{
				return false;
			}
		};
		customRenderItem.setRenderManager(RenderManager.instance);
	}

	@Override
	public void renderTileEntityAt(TileEntity t, double x, double y, double z, float f)
	{
		if(!(t instanceof TileTube)) { throw new RuntimeException("Got a non-TileTube tile in RenderTube!"); }

		TileTube tube = (TileTube) t;

		x += OF;
		y += OF;
		z += OF;

		Tessellator tess = Tessellator.instance;
		tess.startDrawingQuads();

		this.bindTexture(new ResourceLocation("scimachinery", "blocks/tube.png"));

		tess.addVertex(x + 0, y + 0, z + 0);
		tess.addVertex(x + 0, y + OA, z + 0);
		tess.addVertex(x + O, y + OA, z + 0);
		tess.addVertex(x + O, y + 0, z + 0);

		tess.addVertex(x + 0, y + O - OA, z + 0);
		tess.addVertex(x + 0, y + O, z + 0);
		tess.addVertex(x + O, y + O, z + 0);
		tess.addVertex(x + O, y + O - OA, z + 0);

		tess.addVertex(x + 0, y + 0, z + 0);
		tess.addVertex(x + 0, y + O, z + 0);
		tess.addVertex(x + OA, y + O, z + 0);
		tess.addVertex(x + OA, y + 0, z + 0);

		tess.addVertex(x + O - OA, y + 0, z + 0);
		tess.addVertex(x + O - OA, y + O, z + 0);
		tess.addVertex(x + O, y + O, z + 0);
		tess.addVertex(x + O, y + 0, z + 0);

		//

		tess.addVertex(x + 0, y + 0, z + O);
		tess.addVertex(x + 0, y + OA, z + O);
		tess.addVertex(x + 0, y + OA, z + 0);
		tess.addVertex(x + 0, y + 0, z + 0);

		tess.addVertex(x + 0, y + O, z + O);
		tess.addVertex(x + 0, y + O, z + 0);
		tess.addVertex(x + 0, y + O - OA, z + 0);
		tess.addVertex(x + 0, y + O - OA, z + O);

		tess.addVertex(x + 0, y + O, z + OA);
		tess.addVertex(x + 0, y + O, z + 0);
		tess.addVertex(x + 0, y + 0, z + 0);
		tess.addVertex(x + 0, y + 0, z + OA);

		tess.addVertex(x + 0, y + O, z + O);
		tess.addVertex(x + 0, y + O, z + O - OA);
		tess.addVertex(x + 0, y + 0, z + O - OA);
		tess.addVertex(x + 0, y + 0, z + O);

		//

		tess.addVertex(x + O, y + OA, z + 0);
		tess.addVertex(x + O, y + OA, z + O);
		tess.addVertex(x + O, y + 0, z + O);
		tess.addVertex(x + O, y + 0, z + 0);

		tess.addVertex(x + O, y + O, z + 0);
		tess.addVertex(x + O, y + O, z + O);
		tess.addVertex(x + O, y + O - OA, z + O);
		tess.addVertex(x + O, y + O - OA, z + 0);

		tess.addVertex(x + O, y + O, z + 0);
		tess.addVertex(x + O, y + O, z + OA);
		tess.addVertex(x + O, y + 0, z + OA);
		tess.addVertex(x + O, y + 0, z + 0);

		tess.addVertex(x + O, y + O, z + O - OA);
		tess.addVertex(x + O, y + O, z + O);
		tess.addVertex(x + O, y + 0, z + O);
		tess.addVertex(x + O, y + 0, z + O - OA);

		//

		tess.addVertex(x + O, y + OA, z + O);
		tess.addVertex(x + 0, y + OA, z + O);
		tess.addVertex(x + 0, y + 0, z + O);
		tess.addVertex(x + O, y + 0, z + O);

		tess.addVertex(x + O, y + O, z + O);
		tess.addVertex(x + 0, y + O, z + O);
		tess.addVertex(x + 0, y + O - OA, z + O);
		tess.addVertex(x + O, y + O - OA, z + O);

		tess.addVertex(x + OA, y + O, z + O);
		tess.addVertex(x + 0, y + O, z + O);
		tess.addVertex(x + 0, y + 0, z + O);
		tess.addVertex(x + OA, y + 0, z + O);

		tess.addVertex(x + O, y + O, z + O);
		tess.addVertex(x + O - OA, y + O, z + O);
		tess.addVertex(x + O - OA, y + 0, z + O);
		tess.addVertex(x + O, y + 0, z + O);

		//

		tess.addVertex(x + OA, y + O, z + O);
		tess.addVertex(x + OA, y + O, z + 0);
		tess.addVertex(x + 0, y + O, z + 0);
		tess.addVertex(x + 0, y + O, z + O);

		tess.addVertex(x + 0, y + O, z + O);
		tess.addVertex(x + O, y + O, z + O);
		tess.addVertex(x + O, y + O, z + O - OA);
		tess.addVertex(x + 0, y + O, z + O - OA);

		tess.addVertex(x + 0, y + O, z + 0);
		tess.addVertex(x + 0, y + O, z + OA);
		tess.addVertex(x + O, y + O, z + OA);
		tess.addVertex(x + O, y + O, z + 0);

		tess.addVertex(x + O, y + O, z + O);
		tess.addVertex(x + O, y + O, z + 0);
		tess.addVertex(x + O - OA, y + O, z + 0);
		tess.addVertex(x + O - OA, y + O, z + O);

		//

		tess.addVertex(x + OA, y + 0, z + O);
		tess.addVertex(x + 0, y + 0, z + O);
		tess.addVertex(x + 0, y + 0, z + 0);
		tess.addVertex(x + OA, y + 0, z + 0);

		tess.addVertex(x + 0, y + 0, z + O);
		tess.addVertex(x + 0, y + 0, z + O - OA);
		tess.addVertex(x + O, y + 0, z + O - OA);
		tess.addVertex(x + O, y + 0, z + O);

		tess.addVertex(x + 0, y + 0, z + 0);
		tess.addVertex(x + O, y + 0, z + 0);
		tess.addVertex(x + O, y + 0, z + OA);
		tess.addVertex(x + 0, y + 0, z + OA);

		tess.addVertex(x + O - OA, y + 0, z + O);
		tess.addVertex(x + O - OA, y + 0, z + 0);
		tess.addVertex(x + O, y + 0, z + 0);
		tess.addVertex(x + O, y + 0, z + O);

		TileEntity[] tiles = tube.getAdjacentTiles(tube.worldObj, (int) tube.xCoord, (int) tube.yCoord, (int) tube.zCoord);
		for(int i = 0; i < 6; i++)
		{
			if(tiles[i] instanceof TileTube || tiles[i] instanceof IInventory)
			{
				renderSide(i, x, y, z);
			}
		}

		tess.draw();

		for(ItemStack item : tube.getItems())
		{
			renderItem(item, x, y, z);
		}
	}

	public void renderItem(ItemStack item, double x, double y, double z)
	{
		float renderScale = 0.3f;
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.1f, (float) y - 0.175f, (float) z + 0.1f);
		GL11.glTranslatef(0, 0.25F, 0);
		GL11.glScalef(renderScale, renderScale, renderScale);
		dummyEntityItem.setEntityItemStack(item);
		dummyEntityItem.rotationPitch += 0.1 % 360;
		customRenderItem.doRenderItem(dummyEntityItem, 0, 0, 0, 0, dummyEntityItem.rotationPitch);
		GL11.glPopMatrix();
	}

	public void renderSide(int side, double x, double y, double z)
	{
		Tessellator tess = Tessellator.instance;

		if(side == 5)
		{
			tess.addVertex(x + 0, y + 0, z + 0);
			tess.addVertex(x - OO, y + 0, z + 0);
			tess.addVertex(x - OO, y + OA, z + 0);
			tess.addVertex(x + 0, y + OA, z + 0);

			tess.addVertex(x + 0, y + O - OA, z + 0);
			tess.addVertex(x - OO, y + O - OA, z + 0);
			tess.addVertex(x - OO, y + O, z + 0);
			tess.addVertex(x + 0, y + O, z + 0);

			tess.addVertex(x - OO, y + OA, z + O);
			tess.addVertex(x - OO, y + 0, z + O);
			tess.addVertex(x + 0, y + 0, z + O);
			tess.addVertex(x + 0, y + OA, z + O);

			tess.addVertex(x - OO, y + O, z + O);
			tess.addVertex(x - OO, y + O - OA, z + O);
			tess.addVertex(x + 0, y + O - OA, z + O);
			tess.addVertex(x + 0, y + O, z + O);

			tess.addVertex(x + 0, y + O, z + O);
			tess.addVertex(x + 0, y + O, z + O - OA);
			tess.addVertex(x - OO, y + O, z + O - OA);
			tess.addVertex(x - OO, y + O, z + O);

			tess.addVertex(x + 0, y + O, z + 0);
			tess.addVertex(x - OO, y + O, z + 0);
			tess.addVertex(x - OO, y + O, z + OA);
			tess.addVertex(x + 0, y + O, z + OA);

			tess.addVertex(x + 0, y + 0, z + O);
			tess.addVertex(x - OO, y + 0, z + O);
			tess.addVertex(x - OO, y + 0, z + O - OA);
			tess.addVertex(x + 0, y + 0, z + O - OA);

			tess.addVertex(x + 0, y + 0, z + 0);
			tess.addVertex(x + 0, y + 0, z + OA);
			tess.addVertex(x - OO, y + 0, z + OA);
			tess.addVertex(x - OO, y + 0, z + 0);
		}

		//

		if(side == 4)
		{
			tess.addVertex(x + O, y + 0, z + 0);
			tess.addVertex(x + O, y + OA, z + 0);
			tess.addVertex(x + O + OO, y + OA, z + 0);
			tess.addVertex(x + O + OO, y + 0, z + 0);

			tess.addVertex(x + O, y + O - OA, z + 0);
			tess.addVertex(x + O, y + O, z + 0);
			tess.addVertex(x + O + OO, y + O, z + 0);
			tess.addVertex(x + O + OO, y + O - OA, z + 0);

			tess.addVertex(x + O + OO, y + OA, z + O);
			tess.addVertex(x + O, y + OA, z + O);
			tess.addVertex(x + O, y + 0, z + O);
			tess.addVertex(x + O + OO, y + 0, z + O);

			tess.addVertex(x + O + OO, y + O, z + O);
			tess.addVertex(x + O, y + O, z + O);
			tess.addVertex(x + O, y + O - OA, z + O);
			tess.addVertex(x + O + OO, y + O - OA, z + O);

			tess.addVertex(x + O, y + O, z + O);
			tess.addVertex(x + O + OO, y + O, z + O);
			tess.addVertex(x + O + OO, y + O, z + O - OA);
			tess.addVertex(x + O, y + O, z + O - OA);

			tess.addVertex(x + O, y + O, z + 0);
			tess.addVertex(x + O, y + O, z + OA);
			tess.addVertex(x + O + OO, y + O, z + OA);
			tess.addVertex(x + O + OO, y + O, z + 0);

			tess.addVertex(x + O, y + 0, z + O);
			tess.addVertex(x + O, y + 0, z + O - OA);
			tess.addVertex(x + O + OO, y + 0, z + O - OA);
			tess.addVertex(x + O + OO, y + 0, z + O);

			tess.addVertex(x + O, y + 0, z + 0);
			tess.addVertex(x + O + OO, y + 0, z + 0);
			tess.addVertex(x + O + OO, y + 0, z + OA);
			tess.addVertex(x + O, y + 0, z + OA);
		}

		//

		if(side == 3)
		{
			tess.addVertex(x + 0, y + 0, z - OO);
			tess.addVertex(x + 0, y + 0, z + 0);
			tess.addVertex(x + 0, y + OA, z + 0);
			tess.addVertex(x + 0, y + OA, z - OO);

			tess.addVertex(x + 0, y + O, z - OO);
			tess.addVertex(x + 0, y + O - OA, z - OO);
			tess.addVertex(x + 0, y + O - OA, z + 0);
			tess.addVertex(x + 0, y + O, z + 0);

			tess.addVertex(x + O, y + OA, z + 0);
			tess.addVertex(x + O, y + 0, z + 0);
			tess.addVertex(x + O, y + 0, z - OO);
			tess.addVertex(x + O, y + OA, z - OO);

			tess.addVertex(x + O, y + O, z + 0);
			tess.addVertex(x + O, y + O - OA, z + 0);
			tess.addVertex(x + O, y + O - OA, z - OO);
			tess.addVertex(x + O, y + O, z - OO);

			tess.addVertex(x + OA, y + O, z - OO);
			tess.addVertex(x + 0, y + O, z - OO);
			tess.addVertex(x + 0, y + O, z + 0);
			tess.addVertex(x + OA, y + O, z + 0);

			tess.addVertex(x + O, y + O, z - OO);
			tess.addVertex(x + O - OA, y + O, z - OO);
			tess.addVertex(x + O - OA, y + O, z + 0);
			tess.addVertex(x + O, y + O, z + 0);

			tess.addVertex(x + OA, y + 0, z - OO);
			tess.addVertex(x + OA, y + 0, z + 0);
			tess.addVertex(x + 0, y + 0, z + 0);
			tess.addVertex(x + 0, y + 0, z - OO);

			tess.addVertex(x + O - OA, y + 0, z - OO);
			tess.addVertex(x + O, y + 0, z - OO);
			tess.addVertex(x + O, y + 0, z + 0);
			tess.addVertex(x + O - OA, y + 0, z + 0);
		}

		//

		if(side == 2)
		{
			tess.addVertex(x + 0, y + 0, z + O + OO);
			tess.addVertex(x + 0, y + OA, z + O + OO);
			tess.addVertex(x + 0, y + OA, z + O);
			tess.addVertex(x + 0, y + 0, z + O);

			tess.addVertex(x + 0, y + O, z + O + OO);
			tess.addVertex(x + 0, y + O, z + O);
			tess.addVertex(x + 0, y + O - OA, z + O);
			tess.addVertex(x + 0, y + O - OA, z + O + OO);

			tess.addVertex(x + O, y + OA, z + O);
			tess.addVertex(x + O, y + OA, z + O + OO);
			tess.addVertex(x + O, y + 0, z + O + OO);
			tess.addVertex(x + O, y + 0, z + O);

			tess.addVertex(x + O, y + O, z + O);
			tess.addVertex(x + O, y + O, z + O + OO);
			tess.addVertex(x + O, y + O - OA, z + O + OO);
			tess.addVertex(x + O, y + O - OA, z + O);

			tess.addVertex(x + OA, y + O, z + O + OO);
			tess.addVertex(x + OA, y + O, z + O);
			tess.addVertex(x + 0, y + O, z + O);
			tess.addVertex(x + 0, y + O, z + O + OO);

			tess.addVertex(x + O, y + O, z + O + OO);
			tess.addVertex(x + O, y + O, z + O);
			tess.addVertex(x + O - OA, y + O, z + O);
			tess.addVertex(x + O - OA, y + O, z + O + OO);

			tess.addVertex(x + OA, y + 0, z + O + OO);
			tess.addVertex(x + 0, y + 0, z + O + OO);
			tess.addVertex(x + 0, y + 0, z + O);
			tess.addVertex(x + OA, y + 0, z + O);

			tess.addVertex(x + O - OA, y + 0, z + O + OO);
			tess.addVertex(x + O - OA, y + 0, z + O);
			tess.addVertex(x + O, y + 0, z + O);
			tess.addVertex(x + O, y + 0, z + O + OO);
		}

		//

		if(side == 1)
		{
			tess.addVertex(x + OA, y + O + OO, z + O);
			tess.addVertex(x + 0, y + O + OO, z + O);
			tess.addVertex(x + 0, y + O, z + O);
			tess.addVertex(x + OA, y + O, z + O);

			tess.addVertex(x + O, y + O + OO, z + O);
			tess.addVertex(x + O - OA, y + O + OO, z + O);
			tess.addVertex(x + O - OA, y + O, z + O);
			tess.addVertex(x + O, y + O, z + O);

			tess.addVertex(x + O, y + O + OO, z + 0);
			tess.addVertex(x + O, y + O + OO, z + OA);
			tess.addVertex(x + O, y + O, z + OA);
			tess.addVertex(x + O, y + O, z + 0);

			tess.addVertex(x + O, y + O + OO, z + O - OA);
			tess.addVertex(x + O, y + O + OO, z + O);
			tess.addVertex(x + O, y + O, z + O);
			tess.addVertex(x + O, y + O, z + O - OA);

			tess.addVertex(x + 0, y + O, z + 0);
			tess.addVertex(x + 0, y + O + OO, z + 0);
			tess.addVertex(x + OA, y + O + OO, z + 0);
			tess.addVertex(x + OA, y + O, z + 0);

			tess.addVertex(x + O - OA, y + O, z + 0);
			tess.addVertex(x + O - OA, y + O + OO, z + 0);
			tess.addVertex(x + O, y + O + OO, z + 0);
			tess.addVertex(x + O, y + O, z + 0);

			tess.addVertex(x + 0, y + O + OO, z + OA);
			tess.addVertex(x + 0, y + O + OO, z + 0);
			tess.addVertex(x + 0, y + O, z + 0);
			tess.addVertex(x + 0, y + O, z + OA);

			tess.addVertex(x + 0, y + O + OO, z + O);
			tess.addVertex(x + 0, y + O + OO, z + O - OA);
			tess.addVertex(x + 0, y + O, z + O - OA);
			tess.addVertex(x + 0, y + O, z + O);
		}

		//

		if(side == 0)
		{
			tess.addVertex(x + OA, y + 0, z + O);
			tess.addVertex(x + 0, y + 0, z + O);
			tess.addVertex(x + 0, y - OO, z + O);
			tess.addVertex(x + OA, y - OO, z + O);

			tess.addVertex(x + O, y + 0, z + O);
			tess.addVertex(x + O - OA, y + 0, z + O);
			tess.addVertex(x + O - OA, y - OO, z + O);
			tess.addVertex(x + O, y - OO, z + O);

			tess.addVertex(x + O, y + 0, z + 0);
			tess.addVertex(x + O, y + 0, z + OA);
			tess.addVertex(x + O, y - OO, z + OA);
			tess.addVertex(x + O, y - OO, z + 0);

			tess.addVertex(x + O, y + 0, z + O - OA);
			tess.addVertex(x + O, y + 0, z + O);
			tess.addVertex(x + O, y - OO, z + O);
			tess.addVertex(x + O, y - OO, z + O - OA);

			tess.addVertex(x + 0, y - OO, z + 0);
			tess.addVertex(x + 0, y + 0, z + 0);
			tess.addVertex(x + OA, y + 0, z + 0);
			tess.addVertex(x + OA, y - OO, z + 0);

			tess.addVertex(x + O - OA, y - OO, z + 0);
			tess.addVertex(x + O - OA, y + 0, z + 0);
			tess.addVertex(x + O, y + 0, z + 0);
			tess.addVertex(x + O, y - OO, z + 0);

			tess.addVertex(x + 0, y + 0, z + OA);
			tess.addVertex(x + 0, y + 0, z + 0);
			tess.addVertex(x + 0, y - OO, z + 0);
			tess.addVertex(x + 0, y - OO, z + OA);

			tess.addVertex(x + 0, y + 0, z + O);
			tess.addVertex(x + 0, y + 0, z + O - OA);
			tess.addVertex(x + 0, y - OO, z + O - OA);
			tess.addVertex(x + 0, y - OO, z + O);
		}
	}
}