package mod.yuzukotomod;

import mod.yuzukotomod.block.YKBlockChest;
import mod.yuzukotomod.block.YKLightGlassBlock;
import mod.yuzukotomod.block.YKMoonEnchantmentTable;
import mod.yuzukotomod.block.YKNCBlock;
import mod.yuzukotomod.block.YKTileEntityBlock;
import mod.yuzukotomod.block.YuzuKotoBlock;
import mod.yuzukotomod.client.tesr.YKTESRMoonEnchantmentTable;
import mod.yuzukotomod.enchantment.YKEnchantmentChickenPower;
import mod.yuzukotomod.enchantment.YKEnchantmentGreatElectricFire;
import mod.yuzukotomod.entity.YKEntityChicken;
import mod.yuzukotomod.entity.YKEntityRabbit;
import mod.yuzukotomod.entity.YKMineCart;
import mod.yuzukotomod.entity.YKRenderRabbit;
import mod.yuzukotomod.entity.bath.YKMineCartBath;
import mod.yuzukotomod.entity.bath.YKRenderMineCartBath;
import mod.yuzukotomod.entity.kettle.YKEntityKettle;
import mod.yuzukotomod.entity.kettle.YKRenderKettle;
import mod.yuzukotomod.entity.model.YKMineCartRenderer;
import mod.yuzukotomod.entity.ykminecart.YKCMinecart;
import mod.yuzukotomod.entity.ykminecart.YKCRenderMinecart;
import mod.yuzukotomod.event.YKEventBlockBreak;
import mod.yuzukotomod.event.YKEventEnchantmentChickenPower;
import mod.yuzukotomod.event.YKEventOreGen;
import mod.yuzukotomod.event.YKEventRenderer;
import mod.yuzukotomod.event.YKEventShieldSword;
import mod.yuzukotomod.event.YKEventTest;
import mod.yuzukotomod.event.YKEvents;
import mod.yuzukotomod.event.YKLootTableLoadEvent;
import mod.yuzukotomod.gui.YKGuiHandler;
import mod.yuzukotomod.item.YKItemAxe;
import mod.yuzukotomod.item.YKItemHoe;
import mod.yuzukotomod.item.YKItemLuckBox;
import mod.yuzukotomod.item.YKItemMushroom;
import mod.yuzukotomod.item.YKItemPickaxe;
import mod.yuzukotomod.item.YKItemSword;
import mod.yuzukotomod.item.YKItemTest;
import mod.yuzukotomod.item.YKItemVanillaSword;
import mod.yuzukotomod.tileentity.YKTEMoonEnchantmentTable;
import mod.yuzukotomod.tileentity.YKTileEntity;
import mod.yuzukotomod.tileentity.YKTileEntityChest;
import mod.yuzukotomod.tileentity.YKTileEntitySpRenderer;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.IFuelHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(
		modid = YuzuKotoMod.MODID, 
		version = YuzuKotoMod.VERSION,
		name = YuzuKotoMod.MODNAME,
		dependencies = YuzuKotoMod.MOD_DEPENDENCIES,
		acceptedMinecraftVersions = YuzuKotoMod.MOD_ACCEPTED_MINECRAFT_VERSIONS
	)
@EventBusSubscriber
public class YuzuKotoMod {
	
	//MODのID
	public static final String MODID = "yuzukotomod";
	//MODのversion
    public static final String VERSION = "0.1";
    //MODの名前
    public static final String MODNAME = "YuzuKoto Mod";
    //前提MODの設定
	public static final String MOD_DEPENDENCIES = "required-after:forge@[1.11.2-13.20.1.2386,)";
	//MODが対応しているマインクラフトのバージョン
	public static final String MOD_ACCEPTED_MINECRAFT_VERSIONS = "[1.11.2]";
    
	/**
     * **************************************************
     * Modのインスタンス設定
     * **************************************************
     */
    @Instance(YuzuKotoMod.MODID)
    public static YuzuKotoMod INSTANCE;
    
    
	
	public Item testItem = null;

	@EventHandler
    public void preInit(FMLPreInitializationEvent event) {

		//カスタムうさぎさん登録
        ResourceLocation rlRabbit = new ResourceLocation(
        		YuzuKotoMod.MODID, "ykrabbit");
        EntityRegistry.registerModEntity(rlRabbit, YKEntityRabbit.class, 
        		"ykrabbit", 1, this, 128, 1, false);
        
        //草原
        Biome biome = Biome.REGISTRY.getObject(new ResourceLocation("plains"));
        //草原バイオームにスポーン設定
        EntityRegistry.addSpawn(YKEntityRabbit.class, 1, 1, 4,
        		EnumCreatureType.CREATURE, biome);
                
        //エンチャント登録
        //グレートエレキファイヤー
        YKEnchantmentGreatElectricFire encGef = new YKEnchantmentGreatElectricFire();
        GameRegistry.register(encGef, new ResourceLocation(MODID,"great_electric_fire"));

        //にわとりの加護
        YKEnchantmentChickenPower encCP = new YKEnchantmentChickenPower();
        GameRegistry.register(encCP, new ResourceLocation(MODID,"chicken_power"));
        
    	//TileEntityを登録する
    	GameRegistry.registerTileEntity(YKTileEntity.class, "yktile_entity");
    	
    	
    	//TileEntityを登録する
    	GameRegistry.registerTileEntity(YKTileEntityChest.class, "ykte_chest");
    	
    	
    	GameRegistry.registerTileEntity(YKTEMoonEnchantmentTable.class, "ykte_moon_enchantment_table");
    	
    	//マインカートの設定
        //モブの登録だけどうまくできない
        ResourceLocation rl3 = new ResourceLocation(YuzuKotoMod.MODID,
        		"ykminecart");
        EntityRegistry.registerModEntity(rl3, YKMineCart.class, 
        		"ykminecart", 2, YuzuKotoMod.INSTANCE, 128, 1, true);
        
        int mobid = 3;
        
        //やかんを登録
        EntityRegistry.registerModEntity(
        		new ResourceLocation(YuzuKotoMod.MODID, "ykyakan"),
        		YKEntityKettle.class, 
        		"ykyakan",
        		mobid,
        		YuzuKotoMod.INSTANCE,
        		128,
        		1,
        		true
        );
        
        //浴槽マインカート
        mobid = mobid + 1;
        EntityRegistry.registerModEntity(
        		new ResourceLocation(YuzuKotoMod.MODID, "ykbath"),
        		YKMineCartBath.class, 
        		"ykbath",
        		mobid,
        		YuzuKotoMod.INSTANCE,
        		128,
        		1,
        		true
        );
        
        //やかんつき
        mobid = mobid + 1;
        EntityRegistry.registerModEntity(
        		new ResourceLocation(YuzuKotoMod.MODID, "ykfcart"),
        		YKCMinecart.class, 
        		"ykfcart",
        		mobid,
        		YuzuKotoMod.INSTANCE,
        		128,
        		1,
        		true
        );
          
    }
	
	
	/**
	 * 無効化している
	 * @param event
	 */
    public void preInit1(FMLPreInitializationEvent event) {
		//ログだけ出力
        System.out.println("preInit");
        
        //Entityを設定する
        //コメントにpreinitで呼べってかいてた
        //@here
        ResourceLocation rl = new ResourceLocation(YuzuKotoMod.MODID,
        		"ykchicken");
        //Entityの登録
        EntityRegistry.registerModEntity(rl, YKEntityChicken.class, 
        		"ykchicken", 0, this, 128, 1, false);
        
        
        //Entityの登録
        //250は可視範囲かな？
        ResourceLocation rl2 = new ResourceLocation(YuzuKotoMod.MODID,
        		"ykrabbit");
        EntityRegistry.registerModEntity(rl2, YKEntityRabbit.class, 
        		"ykrabbit", 1, this, 128, 1, false);
        
        /*
        //ワールドへのスポーン設定
        EntityRegistry.addSpawn(YKEntityChicken.class, 4, 1, 1,
        		EnumCreatureType.MONSTER, Biome.getBiomeForId(1));
        */
        //ワールドへのスポーン設定
        EntityRegistry.addSpawn(YKEntityRabbit.class, 4, 1, 1,
        		EnumCreatureType.CREATURE, Biome.getBiomeForId(1));        
        
        //レンダー
        if(FMLCommonHandler.instance().getSide() == Side.CLIENT) {
        	
        	//Class<T> entityClass, IRenderFactory<? super T> renderFactory
        	
        	
        	
        	/*
        	//うさぎのrenderer
        	RenderingRegistry.registerEntityRenderingHandler(
        			YKEntityRabbit.class, new IRenderFactory<YKEntityRabbit>() {
					@Override
					public Render<? super YKEntityRabbit> createRenderFor(RenderManager manager) {
						// TODO 自動生成されたメソッド・スタブ
						return new YKRenderRabbit(manager);
					}
        	});
        	*/
        	
        }
        
    }

	@EventHandler
    public void init(FMLInitializationEvent event) {
        System.out.println("init");
        
        //YuzuKotoBlockのレシピ
        GameRegistry.addRecipe(new ItemStack(YuzuKotoBlocks.YUZUKOTO_BLOCK),
                "YYY",
                "YXY",
                "YYY",
                'X', YuzuKotoItems.PURPLE_DIAMOND,
                'Y', Blocks.IRON_BLOCK
        );
        
        //PurpleDiamondのレシピ
        GameRegistry.addShapelessRecipe(new ItemStack(YuzuKotoItems.PURPLE_DIAMOND),
                Blocks.DIAMOND_BLOCK,
                Blocks.REDSTONE_BLOCK,
                Blocks.LAPIS_BLOCK);
        //ダイヤ＋染料に変更
        GameRegistry.addShapelessRecipe(new ItemStack(YuzuKotoItems.PURPLE_DIAMOND),
                Items.DIAMOND,
                new ItemStack(Items.DYE, 1, 5));
        
        //紫ダイヤ剣
        GameRegistry.addRecipe(new ItemStack(YuzuKotoItems.PURPLEDIAMOND_SWORD),
                "X",
                "X",
                "Y",
                'X', YuzuKotoItems.PURPLE_DIAMOND,
                'Y', Items.STICK
        );
        
        //紫ダイヤピッケル
        GameRegistry.addRecipe(new ItemStack(YuzuKotoItems.PURPLEDIAMOND_PICKAXE),
                "XXX",
                " Y ",
                " Y ",
                'X', YuzuKotoItems.PURPLE_DIAMOND,
                'Y', Items.STICK
        );
        //紫ダイヤ斧
        GameRegistry.addRecipe(new ItemStack(YuzuKotoItems.PURPLEDIAMOND_AXE),
                "XX",
                "XY",
                " Y",
                'X', YuzuKotoItems.PURPLE_DIAMOND,
                'Y', Items.STICK
        );
        //紫ダイヤショベル
        GameRegistry.addRecipe(new ItemStack(YuzuKotoItems.PURPLEDIAMOND_SHOVEL),
                "X",
                "Y",
                "Y",
                'X', YuzuKotoItems.PURPLE_DIAMOND,
                'Y', Items.STICK
        );
        //紫ダイヤクワ
        GameRegistry.addRecipe(new ItemStack(YuzuKotoItems.PURPLEDIAMOND_HOE),
                "XX",
                " Y",
                " Y",
                'X', YuzuKotoItems.PURPLE_DIAMOND,
                'Y', Items.STICK
        );
        //紫ダイヤヘルメット
        GameRegistry.addRecipe(new ItemStack(YuzuKotoItems.PURPLEDIAMOND_HELMET),
                "XXX",
                "X X",
                'X', YuzuKotoItems.PURPLE_DIAMOND
        );
        //紫ダイヤチェストプレート
        GameRegistry.addRecipe(new ItemStack(YuzuKotoItems.PURPLEDIAMOND_CHESTPLATE),
                "X X",
                "XXX",
                "XXX",
                'X', YuzuKotoItems.PURPLE_DIAMOND
        );
        //紫ダイヤレギンス
        GameRegistry.addRecipe(new ItemStack(YuzuKotoItems.PURPLEDIAMOND_LEGGINGS),
                "XXX",
                "X X",
                "X X",
                'X', YuzuKotoItems.PURPLE_DIAMOND
        );
        //紫ダイヤブーツ
        GameRegistry.addRecipe(new ItemStack(YuzuKotoItems.PURPLEDIAMOND_BOOTS),
                "X X",
                "X X",
                'X', YuzuKotoItems.PURPLE_DIAMOND
        );
        
        //YuzuKotoBlock から PurpleDiamond
        GameRegistry.addSmelting(new ItemStack(YuzuKotoBlocks.YUZUKOTO_BLOCK),
				new ItemStack(YuzuKotoItems.PURPLE_DIAMOND),
				0.1f);
        
        //燃料化を無効化
        //YuzuKotoBlockは燃料
        GameRegistry.registerFuelHandler(new IFuelHandler(){
			@Override
			public int getBurnTime(ItemStack fuel) {
				if (fuel.getItem().equals(Item.getItemFromBlock(YuzuKotoBlocks.YUZUKOTO_BLOCK))) {
					//石炭の半分
					return 800;
				}
				return 0;
			}
		});
        
        //琴葉ツルハシと紲星クワの修理素材設定
        //修理素材を設定する
        tmPurpleDiamond.setRepairItem(new ItemStack(YuzuKotoItems.PURPLE_DIAMOND));
        amPurpleDiamond.setRepairItem(new ItemStack(YuzuKotoItems.PURPLE_DIAMOND));
        
        
        //びっくり箱のレシピ
        GameRegistry.addShapelessRecipe(new ItemStack(YuzuKotoItems.LUCKBOX_PURPLE),
                Blocks.EMERALD_BLOCK,
                new ItemStack(Items.DYE, 1, 5));
        GameRegistry.addShapelessRecipe(new ItemStack(YuzuKotoItems.LUCKBOX_RED),
                Blocks.EMERALD_BLOCK,
                new ItemStack(Items.DYE, 1, 1));
        GameRegistry.addShapelessRecipe(new ItemStack(YuzuKotoItems.LUCKBOX_BLUE),
                Blocks.EMERALD_BLOCK,
                new ItemStack(Items.DYE, 1, 4));
        GameRegistry.addShapelessRecipe(new ItemStack(YuzuKotoItems.LUCKBOX_YELLOW),
                Blocks.EMERALD_BLOCK,
                new ItemStack(Items.DYE, 1, 14));
        
        
        //GUIの登録
        NetworkRegistry.INSTANCE.registerGuiHandler(YuzuKotoMod.INSTANCE, new YKGuiHandler());
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    	//ログだけ出力
        System.out.println("postInit");
        
        //マグマと炎上無効化イベントを登録
        MinecraftForge.EVENT_BUS.register(new YKEvents());
        
        //鉱石生成イベントを登録
        MinecraftForge.ORE_GEN_BUS.register(new YKEventOreGen());
        
        
        //エンチャントイベントの登録
        MinecraftForge.EVENT_BUS.register(new YKEventEnchantmentChickenPower());

        
        //テスト用
        //旧バニラ剣用
        MinecraftForge.EVENT_BUS.register(new YKEventShieldSword());
        
        //くわの一括破壊イベント
        MinecraftForge.EVENT_BUS.register(new YKEventBlockBreak());
        
        
        
        
        //描画のイベント
        MinecraftForge.EVENT_BUS.register(new YKEventRenderer());
        
        

    }
    
    @EventHandler
    public void postInit1(FMLPostInitializationEvent event) {
    	
        Blocks.RED_MUSHROOM_BLOCK.setCreativeTab(YuzuKotoCreativeTab);
        Item a = Item.getItemFromBlock(Blocks.RED_MUSHROOM_BLOCK);
        
        MinecraftForge.EVENT_BUS.register(new YKLootTableLoadEvent());
        
        MinecraftForge.ORE_GEN_BUS.register(new YKEventOreGen());
        
        
        //こっちは新規roottableを追加するためのもの
        //既存の切り替えはやっぱりeventsのほうでやらないといけない
        //LootTableList.register(new ResourceLocation(YuzuKotoMod.MODID, "yuzukotomod:chests/spawn_bonus_chest"));
        
        
        
        Blocks.MYCELIUM.setCreativeTab(YuzuKotoCreativeTab);
		//ログだけ出力
        System.out.println("postInit");
        
        //イベントを登録する
        MinecraftForge.EVENT_BUS.register(new YKEventTest());
        
    }
    
    /**
     * YuzuKotoMod用クリエイティブタブ
     */
    public static final CreativeTabs YuzuKotoCreativeTab = new CreativeTabs("tabYuzuKoto") {
    	@SideOnly(Side.CLIENT)
    	@Override
        public ItemStack getTabIconItem()
        {
            return new ItemStack(YuzuKotoItems.PURPLE_DIAMOND);
        }
    };
    
    /**
     * アイテムインスタンス保持用
     */
    @ObjectHolder("yuzukotomod")
    public static class YuzuKotoItems{
    	public final static Item PURPLE_DIAMOND = null;
    	public final static Item YUZUKOTO_BLOCK = null;
    	public final static Item PURPLEDIAMOND_SWORD = null;
    	public final static Item PURPLEDIAMOND_PICKAXE = null;
    	public final static Item PURPLEDIAMOND_AXE = null;
    	public final static Item PURPLEDIAMOND_SHOVEL = null;
    	public final static Item PURPLEDIAMOND_HOE = null;
    	
    	public final static Item PURPLEDIAMOND_HELMET = null;
    	public final static Item PURPLEDIAMOND_CHESTPLATE = null;
    	public final static Item PURPLEDIAMOND_LEGGINGS = null;
    	public final static Item PURPLEDIAMOND_BOOTS = null;
    	
    	public final static Item LUCKBOX_PURPLE = null;
    	public final static Item LUCKBOX_RED = null;
    	public final static Item LUCKBOX_BLUE = null;
    	public final static Item LUCKBOX_YELLOW = null;
    	
    	
    	public final static Item VANILLA_WOOD_SWORD = null;
    	public final static Item VANILLA_STONE_SWORD = null;
    	public final static Item VANILLA_IRON_SWORD = null;
    	public final static Item VANILLA_GOLD_SWORD = null;
    	public final static Item VANILLA_DIAMOND_SWORD = null;
    	
    	public final static Item YKTE_BLOCK = null;
    	
    	public final static Item YKBLOCK_CHEST = null;
    	
    	public final static Item YK_MOON_ENCHANTMENT_TABLE = null;
    	
    	public final static Item YK_LIGHT_GLASS = null;
    	
    	public final static Item YK_CUPMEN = null;
    	
    }
    
    /**
     * ブロックインスタンス保持用
     */
    @ObjectHolder("yuzukotomod")
    public static class YuzuKotoBlocks{
    	public final static Block YUZUKOTO_BLOCK = null;
    	
    	public final static Block YKTE_BLOCK = null;
    	
    	public final static Block YKBLOCK_CHEST = null;
    	
    	public final static Block YK_MOON_ENCHANTMENT_TABLE = null;
    	
    	public final static Block YK_LIGHT_GLASS = null;
    	
    	public final static Block YK_CUPMEN = null;
    }
    

    /**
     * ブロックを登録するイベント
     */
    @SubscribeEvent
    protected static void registerBlocks(RegistryEvent.Register<Block> event) {

    	//　ブロックを登録する
        event.getRegistry().register(
//                new YuzuKotoBlock(Material.ROCK)
                new YuzuKotoBlock(Material.IRON)
                .setRegistryName(MODID, "yuzukoto_block")
                .setCreativeTab(YuzuKotoCreativeTab)
                .setUnlocalizedName("yuzukoto_block")
                .setHardness(0.5F)
                .setResistance(2000.0F)
        );
        
        
     //　ブロックを登録する
        event.getRegistry().register(
                new YKTileEntityBlock()
                .setRegistryName(MODID, "ykte_block")
                .setCreativeTab(YuzuKotoCreativeTab)
                .setUnlocalizedName("ykte_block")
                .setHardness(0.5F)
                .setResistance(1.0F)
        );
        
     //　ブロックを登録する
        event.getRegistry().register(
                new YKBlockChest()
                .setRegistryName(MODID, "ykblock_chest")
                .setCreativeTab(YuzuKotoCreativeTab)
                .setUnlocalizedName("ykblock_chest")
                .setHardness(0.5F)
                .setResistance(1.0F)
        );
        
     //　ブロックを登録する
        event.getRegistry().register(
                new YKMoonEnchantmentTable()
                .setRegistryName(MODID, "yk_moon_enchantment_table")
                .setCreativeTab(YuzuKotoCreativeTab)
                .setUnlocalizedName("yk_moon_enchantment_table")
                .setHardness(0.5F)
                .setResistance(1.0F)
        );
        
        //光るガラス
        event.getRegistry().register(
                new YKLightGlassBlock()
                .setRegistryName(MODID, "yk_light_glass")
                .setCreativeTab(YuzuKotoCreativeTab)
                .setUnlocalizedName("yk_light_glass")
                //.setHardness(0.5F)
                //.setResistance(1.0F)
        );
        
        //カップめん
        event.getRegistry().register(
                new YKNCBlock(Material.CAKE)
                .setRegistryName(MODID, "yk_cupmen")
                .setCreativeTab(YuzuKotoCreativeTab)
                .setUnlocalizedName("yk_cupmen")
                //.setHardness(0.5F)
                //.setResistance(1.0F)
        );
        
    }
    
    public static ToolMaterial tmPurpleDiamond;
    
    public static ArmorMaterial amPurpleDiamond;
    
    
    /**
     * アイテムを登録するイベント
     */
    @SubscribeEvent
    protected static void registerItems(RegistryEvent.Register<Item> event){
    	
    	event.getRegistry().register(new YKItemMushroom()
    			.setRegistryName(MODID, "yk_red_mushroom")
    			.setUnlocalizedName("yk_red_mushroom")
    			.setCreativeTab(YuzuKotoCreativeTab)
    	);
    	
    	
    	
    	

    	// アイテムを登録する
    	event.getRegistry().register(new YKItemTest()
    			.setRegistryName(MODID, "purple_diamond")
    			.setUnlocalizedName("purple_diamond")
    			.setCreativeTab(YuzuKotoCreativeTab)
    	);
    	
    	// アイテムブロックを登録する
    	event.getRegistry().register(new ItemBlock(YuzuKotoBlocks.YUZUKOTO_BLOCK)
    			.setRegistryName(MODID, "yuzukoto_block")
    	);
    	
    	//ツールマテリアル設定
    	//採掘レベル/耐久値/ブロック破壊速度/攻撃力/エンチャント補正
    	//tmPurpleDiamond = EnumHelper.addToolMaterial("PURPLE_DIAMOND", 3, 59, 8.0F, 3.0F, 22);
    	tmPurpleDiamond = EnumHelper.addToolMaterial("PURPLE_DIAMOND", 3, 1561, 8.0F, 3.0F, 22);
    	
    	//パープルダイヤ剣
    	event.getRegistry().register(new YKItemSword(tmPurpleDiamond)
    			.setRegistryName(MODID, "purplediamond_sword")
    			.setUnlocalizedName("purplediamond_sword")
    			.setCreativeTab(YuzuKotoCreativeTab)
    	);
    	
    	//パープルダイヤツルハシ
    	event.getRegistry().register(new YKItemPickaxe(tmPurpleDiamond)
    			.setRegistryName(MODID, "purplediamond_pickaxe")
    			.setUnlocalizedName("purplediamond_pickaxe")
    			.setCreativeTab(YuzuKotoCreativeTab)
    	);
    	
    	//パープルダイヤ斧
    	event.getRegistry().register(new YKItemAxe(tmPurpleDiamond, 8.0F, -3.0F)
    			.setRegistryName(MODID, "purplediamond_axe")
    			.setUnlocalizedName("purplediamond_axe")
    			.setCreativeTab(YuzuKotoCreativeTab)
    	);
    	
    	//パープルダイヤショベル
    	event.getRegistry().register(new ItemSpade(tmPurpleDiamond)
    			.setRegistryName(MODID, "purplediamond_shovel")
    			.setUnlocalizedName("purplediamond_shovel")
    			.setCreativeTab(YuzuKotoCreativeTab)
    	);
    	
    	//パープルダイヤ鍬
    	event.getRegistry().register(new YKItemHoe(tmPurpleDiamond)
    			.setRegistryName(MODID, "purplediamond_hoe")
    			.setUnlocalizedName("purplediamond_hoe")
    			.setCreativeTab(YuzuKotoCreativeTab)
    	);
    	
    	
    	//アーマーマテリアル設定
    	//採掘レベル/耐久値/ブロック破壊速度/攻撃力/エンチャント補正
    	//"PURPLE_DIAMOND", 3, 59, 8.0F, 3.0F, 22
    	//
    	/*
    	 *String name, //名前
    	 *String textureName,　//テクスチャの名前 ?これがどこにできるかがよくわからない？
    	 *int durability,  //これも定義設定じゃないっぽい
    	 *int[] reductionAmounts, 
    	 *int enchantability, エンチャント適性
    	 *SoundEvent soundOnEquip, サウンドイベント、音のなんか？
    	 *float toughness 
    	 *
    	 *DIAMOND("diamond", 33, new int[]{3, 6, 8, 3}, 10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F);
    	 */
    	
    	//アーマーマテリアルの設定
    	amPurpleDiamond = EnumHelper.addArmorMaterial("PURPLE_DIAMOND", 
    			"yuzukotomod:purplediamond_armor", 
    			5, 
    			new int[]{3, 6, 8, 3}, 
    			25, 
    			SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 
    			2.0F
    			);

    	//パープルダイヤヘルメット
    	event.getRegistry().register(new ItemArmor(amPurpleDiamond, -1, EntityEquipmentSlot.HEAD)
    			.setRegistryName(MODID, "purplediamond_helmet")
    			.setUnlocalizedName("purplediamond_helmet")
    			.setCreativeTab(YuzuKotoCreativeTab)
    	);
    	
    	//パープルダイヤチェストプレート
    	event.getRegistry().register(new ItemArmor(amPurpleDiamond, -1, EntityEquipmentSlot.CHEST)
    			.setRegistryName(MODID, "purplediamond_chestplate")
    			.setUnlocalizedName("purplediamond_chestplate")
    			.setCreativeTab(YuzuKotoCreativeTab)
    	);
    	
    	//パープルダイヤレギンス
    	event.getRegistry().register(new ItemArmor(amPurpleDiamond, -1, EntityEquipmentSlot.LEGS)
    			.setRegistryName(MODID, "purplediamond_leggings")
    			.setUnlocalizedName("purplediamond_leggings")
    			.setCreativeTab(YuzuKotoCreativeTab)
    	);
    	
    	//パープルダイヤブーツ
    	event.getRegistry().register(new ItemArmor(amPurpleDiamond, -1, EntityEquipmentSlot.FEET)
    			.setRegistryName(MODID, "purplediamond_boots")
    			.setUnlocalizedName("purplediamond_boots")
    			.setCreativeTab(YuzuKotoCreativeTab)
    	);
    	
    	
    	
    	/*
    	// アイテムを登録する
    	event.getRegistry().register(new YKItemSword(Item.ToolMaterial.DIAMOND)
    			.setRegistryName(MODID, "purple_diamond")
    			.setUnlocalizedName("purple_diamond")
    			.setCreativeTab(YuzuKotoCreativeTab)
    	);
    	*/
    	
    	
    	
    	/*
    	 * ToolMaterial newToolMaterial = EnumHelper.addToolMaterial("GOLDBLOCK", 2, 500, 6.0F, 2, 22);
    	 */
    	
    	/*
    	// アイテムを登録する
    	event.getRegistry().register(new YKItemSword(newToolMaterial)
    			.setRegistryName(MODID, "purple_diamond")
    			.setUnlocalizedName("purple_diamond")
    			.setCreativeTab(YuzuKotoCreativeTab)
    	);
    	*/
    	
    	//プレゼントBOX系(エンド系)
    	event.getRegistry().register(new YKItemLuckBox("minecraft:chests/end_city_treasure")
    			.setRegistryName(MODID, "luckbox_purple")
    			.setUnlocalizedName("luckbox_purple")
    			.setCreativeTab(YuzuKotoCreativeTab)
    	);
    	//プレゼントBOX系(ダンジョン系)
    	event.getRegistry().register(new YKItemLuckBox("minecraft:chests/simple_dungeon")
    			.setRegistryName(MODID, "luckbox_red")
    			.setUnlocalizedName("luckbox_red")
    			.setCreativeTab(YuzuKotoCreativeTab)
    	);
    	//プレゼントBOX系(村人)
    	event.getRegistry().register(new YKItemLuckBox("minecraft:chests/village_blacksmith")
    			.setRegistryName(MODID, "luckbox_blue")
    			.setUnlocalizedName("luckbox_blue")
    			.setCreativeTab(YuzuKotoCreativeTab)
    	);
    	//プレゼントBOX系(イグルー)
    	event.getRegistry().register(new YKItemLuckBox("minecraft:chests/igloo_chest")
    			.setRegistryName(MODID, "luckbox_yellow")
    			.setUnlocalizedName("luckbox_yellow")
    			.setCreativeTab(YuzuKotoCreativeTab)
    	);
    	
    	
    	//バニラの剣（木）
    	event.getRegistry().register(new YKItemVanillaSword(ToolMaterial.WOOD)
    			.setRegistryName(MODID, "vanilla_wood_sword")
    			.setUnlocalizedName("vanilla_wood_sword")
    			.setCreativeTab(YuzuKotoCreativeTab)
    	);
    	//バニラの剣（石）
    	event.getRegistry().register(new YKItemVanillaSword(ToolMaterial.STONE)
    			.setRegistryName(MODID, "vanilla_stone_sword")
    			.setUnlocalizedName("vanilla_stone_sword")
    			.setCreativeTab(YuzuKotoCreativeTab)
    	);
    	//バニラの剣（鉄）
    	event.getRegistry().register(new YKItemVanillaSword(ToolMaterial.IRON)
    			.setRegistryName(MODID, "vanilla_iron_sword")
    			.setUnlocalizedName("vanilla_iron_sword")
    			.setCreativeTab(YuzuKotoCreativeTab)
    	);
    	//バニラの剣（金）
    	event.getRegistry().register(new YKItemVanillaSword(ToolMaterial.GOLD)
    			.setRegistryName(MODID, "vanilla_gold_sword")
    			.setUnlocalizedName("vanilla_gold_sword")
    			.setCreativeTab(YuzuKotoCreativeTab)
    	);
    	//バニラの剣（ダイヤ）
    	event.getRegistry().register(new YKItemVanillaSword(ToolMaterial.DIAMOND)
    			.setRegistryName(MODID, "vanilla_diamond_sword")
    			.setUnlocalizedName("vanilla_diamond_sword")
    			.setCreativeTab(YuzuKotoCreativeTab)
    	);
    	
    	
    	// アイテムブロックを登録する
    	event.getRegistry().register(new ItemBlock(YuzuKotoBlocks.YKTE_BLOCK)
    			.setRegistryName(MODID, "ykte_block")
    	);

    	// アイテムブロックを登録する
    	event.getRegistry().register(new ItemBlock(YuzuKotoBlocks.YKBLOCK_CHEST)
    			.setRegistryName(MODID, "ykblock_chest")
    	);
    	
    	// アイテムブロックを登録する
    	event.getRegistry().register(new ItemBlock(YuzuKotoBlocks.YK_MOON_ENCHANTMENT_TABLE)
    			.setRegistryName(MODID, "yk_moon_enchantment_table")
    	);
    	
    	
    	// アイテムブロックを登録する
    	event.getRegistry().register(new ItemBlock(YuzuKotoBlocks.YK_LIGHT_GLASS)
    			.setRegistryName(MODID, "yk_light_glass")
    	);
    	
    	//かっぷめん
    	event.getRegistry().register(new ItemBlock(YuzuKotoBlocks.YK_CUPMEN)
    			.setRegistryName(MODID, "yk_cupmen")
    	);
    	
    	
    	//Blocks.PRISMARINE.setLightLevel(0.5F);
    	//Blocks.GLASS.setLightLevel(0.75F);
    	//Blocks.SEA_LANTERN.setLightLevel(1.0F);
    	
    	//Blocks.RAIL.setLightLevel(1F);
    	
    }
    
    /**
     * モデルを登録するイベント
     */
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    protected static void registerModels(ModelRegistryEvent event) {
    	
    	OBJLoader.INSTANCE.addDomain(MODID);
    	
    	
    	// アイテムのモデルを登録する
    	ModelLoader.setCustomModelResourceLocation(YuzuKotoItems.PURPLE_DIAMOND, 0,
    			new ModelResourceLocation(YuzuKotoItems.PURPLE_DIAMOND.getRegistryName(), "inventory"));
    	    	
    	// アイテムのモデルを登録する
    	ModelLoader.setCustomModelResourceLocation(YuzuKotoItems.YUZUKOTO_BLOCK, 0,
    			new ModelResourceLocation(YuzuKotoItems.YUZUKOTO_BLOCK.getRegistryName(), "inventory"));

    	// パープルダイヤの剣
    	ModelLoader.setCustomModelResourceLocation(YuzuKotoItems.PURPLEDIAMOND_SWORD, 0,
    			new ModelResourceLocation(YuzuKotoItems.PURPLEDIAMOND_SWORD.getRegistryName(), "inventory"));
    	
    	// パープルダイヤのツルハシ
    	ModelLoader.setCustomModelResourceLocation(YuzuKotoItems.PURPLEDIAMOND_PICKAXE, 0,
    			new ModelResourceLocation(YuzuKotoItems.PURPLEDIAMOND_PICKAXE.getRegistryName(), "inventory"));
    	
    	// パープルダイヤの斧
    	ModelLoader.setCustomModelResourceLocation(YuzuKotoItems.PURPLEDIAMOND_AXE, 0,
    			new ModelResourceLocation(YuzuKotoItems.PURPLEDIAMOND_AXE.getRegistryName(), "inventory"));
    	
    	// パープルダイヤのショベル
    	ModelLoader.setCustomModelResourceLocation(YuzuKotoItems.PURPLEDIAMOND_SHOVEL, 0,
    			new ModelResourceLocation(YuzuKotoItems.PURPLEDIAMOND_SHOVEL.getRegistryName(), "inventory"));
    	
    	// パープルダイヤの鍬
    	ModelLoader.setCustomModelResourceLocation(YuzuKotoItems.PURPLEDIAMOND_HOE, 0,
    			new ModelResourceLocation(YuzuKotoItems.PURPLEDIAMOND_HOE.getRegistryName(), "inventory"));
    	
    	// パープルダイヤヘルメット
    	ModelLoader.setCustomModelResourceLocation(YuzuKotoItems.PURPLEDIAMOND_HELMET, 0,
    			new ModelResourceLocation(YuzuKotoItems.PURPLEDIAMOND_HELMET.getRegistryName(), "inventory"));
    	// パープルダイヤチェストプレート
    	ModelLoader.setCustomModelResourceLocation(YuzuKotoItems.PURPLEDIAMOND_CHESTPLATE, 0,
    			new ModelResourceLocation(YuzuKotoItems.PURPLEDIAMOND_CHESTPLATE.getRegistryName(), "inventory"));
    	// パープルダイヤレギングス
    	ModelLoader.setCustomModelResourceLocation(YuzuKotoItems.PURPLEDIAMOND_LEGGINGS, 0,
    			new ModelResourceLocation(YuzuKotoItems.PURPLEDIAMOND_LEGGINGS.getRegistryName(), "inventory"));
    	// パープルダイヤブーツ
    	ModelLoader.setCustomModelResourceLocation(YuzuKotoItems.PURPLEDIAMOND_BOOTS, 0,
    			new ModelResourceLocation(YuzuKotoItems.PURPLEDIAMOND_BOOTS.getRegistryName(), "inventory"));
    	
    	// プレゼントBOX
    	ModelLoader.setCustomModelResourceLocation(YuzuKotoItems.LUCKBOX_PURPLE, 0,
    			new ModelResourceLocation(YuzuKotoItems.LUCKBOX_PURPLE.getRegistryName(), "inventory"));
    	ModelLoader.setCustomModelResourceLocation(YuzuKotoItems.LUCKBOX_RED, 0,
    			new ModelResourceLocation(YuzuKotoItems.LUCKBOX_RED.getRegistryName(), "inventory"));
    	ModelLoader.setCustomModelResourceLocation(YuzuKotoItems.LUCKBOX_BLUE, 0,
    			new ModelResourceLocation(YuzuKotoItems.LUCKBOX_BLUE.getRegistryName(), "inventory"));
    	ModelLoader.setCustomModelResourceLocation(YuzuKotoItems.LUCKBOX_YELLOW, 0,
    			new ModelResourceLocation(YuzuKotoItems.LUCKBOX_YELLOW.getRegistryName(), "inventory"));
    	
    	// バニラの剣
    	ModelLoader.setCustomModelResourceLocation(YuzuKotoItems.VANILLA_WOOD_SWORD, 0,
    			new ModelResourceLocation(YuzuKotoItems.VANILLA_WOOD_SWORD.getRegistryName(), "inventory"));
    	ModelLoader.setCustomModelResourceLocation(YuzuKotoItems.VANILLA_STONE_SWORD, 0,
    			new ModelResourceLocation(YuzuKotoItems.VANILLA_STONE_SWORD.getRegistryName(), "inventory"));
    	ModelLoader.setCustomModelResourceLocation(YuzuKotoItems.VANILLA_IRON_SWORD, 0,
    			new ModelResourceLocation(YuzuKotoItems.VANILLA_IRON_SWORD.getRegistryName(), "inventory"));
    	ModelLoader.setCustomModelResourceLocation(YuzuKotoItems.VANILLA_GOLD_SWORD, 0,
    			new ModelResourceLocation(YuzuKotoItems.VANILLA_GOLD_SWORD.getRegistryName(), "inventory"));
    	ModelLoader.setCustomModelResourceLocation(YuzuKotoItems.VANILLA_DIAMOND_SWORD, 0,
    			new ModelResourceLocation(YuzuKotoItems.VANILLA_DIAMOND_SWORD.getRegistryName(), "inventory"));
    	
    	
    	//にわとりのrenderer
    	/*
    	RenderingRegistry.registerEntityRenderingHandler(
			YKEntityChicken.class, new IRenderFactory<YKEntityChicken>() {
				@Override
				public Render<? super YKEntityChicken> createRenderFor(RenderManager manager) {
					// TODO 自動生成されたメソッド・スタブ
					return new YKRenderChicken(manager);
				}
    	});
    	*/
    	
    	
    	
    	//カスタムうさぎさんのRenderの登録
    	RenderingRegistry.registerEntityRenderingHandler(
    			YKEntityRabbit.class, new IRenderFactory<YKEntityRabbit>() {
				@Override
				public Render<? super YKEntityRabbit> createRenderFor(RenderManager manager) {
					return new YKRenderRabbit(manager);
				}
    	});
    	
    	
    	//マインカート
    	RenderingRegistry.registerEntityRenderingHandler(
    			YKMineCart.class, new IRenderFactory<YKMineCart>() {
				@SuppressWarnings({ "unchecked", "rawtypes" })
				@Override
				public Render<? super YKMineCart> createRenderFor(RenderManager manager) {
					return new YKMineCartRenderer(manager);
				}
    	});
    	
    	//マインカート
    	RenderingRegistry.registerEntityRenderingHandler(
    			YKMineCartBath.class, new IRenderFactory<YKMineCartBath>() {
				@SuppressWarnings({ "unchecked", "rawtypes" })
				@Override
				public Render<? super YKMineCartBath> createRenderFor(RenderManager manager) {
					return new YKRenderMineCartBath(manager);
				}
    	});
    	
    	
    	//やかん
    	RenderingRegistry.registerEntityRenderingHandler(
    			YKEntityKettle.class, new IRenderFactory<YKEntityKettle>() {
				@SuppressWarnings({ "unchecked", "rawtypes" })
				@Override
				public Render<? super YKEntityKettle> createRenderFor(RenderManager manager) {
					return new YKRenderKettle(manager);
				}
    	});
    	
    	
    	//やかんつきトロッコ
    	RenderingRegistry.registerEntityRenderingHandler(
    			YKCMinecart.class, new IRenderFactory<YKCMinecart>() {
				@SuppressWarnings({ "unchecked", "rawtypes" })
				@Override
				public Render<? super YKCMinecart> createRenderFor(RenderManager manager) {
					return new YKCRenderMinecart(manager);
				}
    	});
    	
    	
    	
    	// アイテムのモデルを登録する
    	ModelLoader.setCustomModelResourceLocation(YuzuKotoItems.YKTE_BLOCK, 0,
    			new ModelResourceLocation(YuzuKotoItems.YKTE_BLOCK.getRegistryName(), "inventory"));
    	
    	//TileEntity用のRendererを登録する
    	//ClientRegistry.registerTileEntity(YKTileEntity.class, "yktile_entity", new YKTileEntitySpRenderer());

    	//スペシャルレンダラー
    	ClientRegistry.bindTileEntitySpecialRenderer(YKTileEntity.class, new YKTileEntitySpRenderer());
    	
    	
    	// アイテムのモデルを登録する
    	ModelLoader.setCustomModelResourceLocation(YuzuKotoItems.YKBLOCK_CHEST, 0,
    			new ModelResourceLocation(YuzuKotoItems.YKBLOCK_CHEST.getRegistryName(), "inventory"));
    	
    	//moontable
    	ModelLoader.setCustomModelResourceLocation(YuzuKotoItems.YK_MOON_ENCHANTMENT_TABLE, 0,
    			new ModelResourceLocation(YuzuKotoItems.YK_MOON_ENCHANTMENT_TABLE.getRegistryName(), "inventory"));
    	
    	
    	//月の祭壇
    	ClientRegistry.bindTileEntitySpecialRenderer(YKTEMoonEnchantmentTable.class, new YKTESRMoonEnchantmentTable());
    	
    	
    	
    	// 光るガラス
    	ModelLoader.setCustomModelResourceLocation(YuzuKotoItems.YK_LIGHT_GLASS, 0,
    			new ModelResourceLocation(YuzuKotoItems.YK_LIGHT_GLASS.getRegistryName(), "inventory"));
    	
    	
    	//カップめん
    	ModelLoader.setCustomModelResourceLocation(YuzuKotoItems.YK_CUPMEN, 0,
    			new ModelResourceLocation(YuzuKotoItems.YK_CUPMEN.getRegistryName(), "inventory"));
    	
    }

}
