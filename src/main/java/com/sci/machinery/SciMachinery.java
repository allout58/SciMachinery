package com.sci.machinery;

import java.io.File;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.client.event.sound.PlayStreamingEvent;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.event.ForgeSubscribe;
import com.sci.machinery.block.BlockTube;
import com.sci.machinery.block.TileTube;
import com.sci.machinery.block.tube.TubeBase;
import com.sci.machinery.block.tube.TubeModifier;
import com.sci.machinery.core.CreativeTabSM;
import com.sci.machinery.core.IProxy;
import com.sci.machinery.item.ItemEasterEgg;
import com.sci.machinery.lib.Reference;
import com.sci.machinery.network.PacketHandler;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * SciMachinery
 * 
 * @author sci4me
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME)
@NetworkMod(channels =
{ Reference.CHANNEL_NAME }, clientSideRequired = true, serverSideRequired = false, packetHandler = PacketHandler.class)
public class SciMachinery
{
	@Instance(Reference.MOD_ID)
	public static SciMachinery instance;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.SERVER_PROXY)
	public static IProxy proxy;

	public static CreativeTabs tab = new CreativeTabSM(CreativeTabs.getNextID(), Reference.MOD_ID);

	public ItemEasterEgg easterEgg;
	public int easterEggId;

	public BlockTube cobbleTube;
	public int cobbleTubeId;

	public BlockTube detectorTube;
	public int detectorTubeId;

	public int fastCobbleId;
	public BlockTube fastCobbleTube;

	public int fastStoneId;
	public BlockTube fastStoneTube;

	public BlockTube pumpTube;
	public int pumpTubeId;

	public BlockTube stoneTube;
	public int stoneTubeId;

	public BlockTube voidTube;
	public int voidTubeId;

	public BlockTube tubeValve;
	public int valveTubeId;

	@EventHandler
	public void init(FMLInitializationEvent e)
	{
		proxy.init(e);

		easterEgg = new ItemEasterEgg(easterEggId);

		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(easterEgg), 1, 1, 1));

		GameRegistry.registerTileEntity(TileTube.class, "SciMachinery_TileTube");

		stoneTube = new BlockTube(stoneTubeId, TubeBase.STONE);
		stoneTube.setUnlocalizedName("stoneTube");
		GameRegistry.registerBlock(stoneTube, "SciMachinery_TileStoneTube");

		cobbleTube = new BlockTube(cobbleTubeId, TubeBase.COBBLE);
		cobbleTube.setUnlocalizedName("cobbleTube");
		GameRegistry.registerBlock(cobbleTube, "SciMachinery_TileCobbleTube");

		fastStoneTube = new BlockTube(fastStoneId, TubeBase.STONE, TubeModifier.SPEED);
		fastStoneTube.setUnlocalizedName("fastStoneTube");
		GameRegistry.registerBlock(fastStoneTube, "SciMachinery_TileFastStoneTube");

		fastCobbleTube = new BlockTube(fastCobbleId, TubeBase.COBBLE, TubeModifier.SPEED);
		fastCobbleTube.setUnlocalizedName("fastCobbleTube");
		GameRegistry.registerBlock(fastCobbleTube, "SciMachinery_TileFastCobbleTube");

		pumpTube = new BlockTube(pumpTubeId, TubeBase.PUMP);
		pumpTube.setUnlocalizedName("pumpTube");
		GameRegistry.registerBlock(pumpTube, "SciMachinery_TilePumpTube");

		detectorTube = new BlockTube(detectorTubeId, TubeBase.DETECTOR);
		detectorTube.setUnlocalizedName("detectorTube");
		GameRegistry.registerBlock(detectorTube, "SciMachinery_TileDetectorTube");

		voidTube = new BlockTube(voidTubeId, TubeBase.VOID);
		voidTube.setUnlocalizedName("voidTube");
		GameRegistry.registerBlock(voidTube, "SciMachinery_TileVoidTube");

		tubeValve = new BlockTube(valveTubeId, TubeBase.VALVE);
		tubeValve.setUnlocalizedName("valveTube");
		GameRegistry.registerBlock(tubeValve, "SciMachinery_TileValveTube");

		GameRegistry.addRecipe(new ItemStack(stoneTube, 16), new Object[]
		{ "sss", "gpg", "sss", 's', Block.stone, 'g', Block.glass, 'p', Block.pistonBase });
		GameRegistry.addRecipe(new ItemStack(cobbleTube, 16), new Object[]
		{ "sss", "gpg", "sss", 's', Block.cobblestone, 'g', Block.glass, 'p', Block.pistonBase });
		GameRegistry.addRecipe(new ItemStack(pumpTube, 4), new Object[]
		{ "rhr", "hth", "rhr", 'r', Item.redstone, 'h', Block.hopperBlock, 't', stoneTube });
		GameRegistry.addRecipe(new ItemStack(detectorTube, 3), new Object[]
		{ "rgr", "ttt", "rgr", 'r', Item.redstone, 'g', Item.ingotGold, 't', stoneTube });
		GameRegistry.addRecipe(new ItemStack(voidTube, 1), new Object[]
		{ "lgl", "epe", "lgl", 'l', new ItemStack(Item.dyePowder, 4), 'g', Item.glowstone, 'e', Item.enderPearl, 'p', stoneTube });

		GameRegistry.addShapelessRecipe(new ItemStack(fastStoneTube), new Object[]
		{ new ItemStack(Item.ingotGold, 1), new ItemStack(fastStoneTube, 1) });
		GameRegistry.addShapelessRecipe(new ItemStack(fastCobbleTube), new Object[]
		{ new ItemStack(Item.ingotGold, 1), new ItemStack(fastStoneTube, 1) });

		GameRegistry.addShapelessRecipe(new ItemStack(tubeValve), new Object[]
		{ new ItemStack(Item.redstone, 1), new ItemStack(stoneTube, 1) });
		GameRegistry.addShapelessRecipe(new ItemStack(tubeValve), new Object[]
		{ new ItemStack(Item.redstone, 1), new ItemStack(cobbleTube, 1) });
	}

	@ForgeSubscribe
	public void loadSounds(SoundLoadEvent e)
	{
		e.manager.soundPoolStreaming.addSound("scimachinery:chicken.ogg");
	}

	@ForgeSubscribe
	public void onPlayStreaming(PlayStreamingEvent event)
	{
		if(event.name.equals("chicken"))
		{
			FMLClientHandler.instance().getClient().sndManager.playStreaming("scimachinery:chicken", event.x + 0.5F, event.y + 0.5F, event.z + 0.5F);
		}
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent e)
	{
		proxy.postInit(e);
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent e)
	{
		e.getModMetadata().version = Reference.MOD_VERSION;
		
		proxy.preInit(e);

		File folder = new File(e.getModConfigurationDirectory(), "sci4me");
		if(!folder.exists())
			folder.mkdirs();
		Configuration cfg = new Configuration(new File(folder, "machinery.cfg"));
		try
		{
			cfg.load();
			easterEggId = cfg.getItem("easterEgg", 419).getInt();

			stoneTubeId = cfg.getBlock("stoneTube", 420).getInt();
			cobbleTubeId = cfg.getBlock("cobbleTube", 424).getInt();
			pumpTubeId = cfg.getBlock("pumpTube", 421).getInt();
			detectorTubeId = cfg.getBlock("detectorTube", 422).getInt();
			voidTubeId = cfg.getBlock("voidTube", 423).getInt();

			fastStoneId = cfg.getBlock("fastStoneTube", 425).getInt();
			fastCobbleId = cfg.getBlock("fastCobbleTube", 426).getInt();

			valveTubeId = cfg.getBlock("valveTube", 427).getInt();
		}
		finally
		{
			cfg.save();
		}
	}
}