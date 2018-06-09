package mod.yuzukotomod.item;

import java.util.List;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTable;

public class YKItemLuckBox extends Item{

	private String loottable = "";
	
	public YKItemLuckBox(String loottable) {
		super();
		this.loottable = loottable;		
	}
	
	/**
     * Called when a Block is right-clicked with this Item
     */
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
		//消費分減らす
		if (!player.capabilities.isCreativeMode)
        {
			player.getHeldItemMainhand().shrink(1);
//            itemstack.shrink(1);
        }
		
		if(worldIn.isRemote) {
			return EnumActionResult.SUCCESS;
		}
	
		//利用するLoot Tableを設定
		//String loot = "minecraft:chests/spawn_bonus_chest";
		//String loot = "minecraft:entities/cow";
		String loot = "";
		//村人
		loot = "minecraft:chests/village_blacksmith";
		//ダンジョンチェスト
		loot = "minecraft:chests/simple_dungeon";
		//エンド
		//loot = "minecraft:chests/end_city_treasure";
		
		loot = this.loottable;
		//Tableを構築
		LootTable table = worldIn.getLootTableManager()
				.getLootTableFromLocation(new ResourceLocation(loot));
		
		//CTXを構築
		/*
		 * ダメージタイプとか幸運とかはこのタイミングで構築する 
		 */
		LootContext.Builder ctxBuild = new LootContext.Builder((WorldServer) worldIn);
		
		//ctxBuild.withLuck(1000);
		
		
		LootContext ctx = ctxBuild.build();

		
		//ここでいろいろやる
		
		
		//loot tabbleを生成する
				//たぶんサーバー側だけ？
		//サーバーサイド以外はおちる
		

		List<ItemStack> stacks = table.generateLootForPools(worldIn.rand, ctx);
		
		BlockPos playerPos = new BlockPos(player.posX, player.posY, player.posZ);
		
		//クリックしたブロックの面を確認してからやる
		BlockPos ipos = new BlockPos(pos);
		
		if(facing == EnumFacing.UP) {
			ipos = ipos.up();
		} else if(facing == EnumFacing.DOWN) {
			ipos = ipos.down(2);
		} else if(facing == EnumFacing.EAST) {
			ipos = ipos.east();
		} else if(facing == EnumFacing.WEST) {
			ipos = ipos.west();
		} else if(facing == EnumFacing.NORTH) {
			ipos = ipos.north();
		} else if(facing == EnumFacing.SOUTH) {
			ipos = ipos.south();
		}
		
		
		for (ItemStack stack : stacks) {
			//Itemをスポーンさせる
			this.spawnAsEntity(worldIn, ipos, stack);
			
		}
		

		return EnumActionResult.SUCCESS;
		
		
    }
	
	/**
     * Spawns the given ItemStack as an EntityItem into the World at the given position
     */
    public void spawnAsEntity(World worldIn, BlockPos pos, ItemStack stack)
    {
        if (!worldIn.isRemote && !stack.isEmpty())
        {
            float f = 0.5F;
            double d0 = (double)(worldIn.rand.nextFloat() * 0.5F) + 0.25D;
            double d1 = (double)(worldIn.rand.nextFloat() * 0.5F) + 0.25D;
            double d2 = (double)(worldIn.rand.nextFloat() * 0.5F) + 0.25D;
            
            d0 = 0.5;
            //d1 = 0.5;
            d2 = 0.5;
            
            EntityItem entityitem = new EntityItem(worldIn, 
            		(double)pos.getX() + d0, 
            		(double)pos.getY() + d1, 
            		(double)pos.getZ() + d2, stack);
            entityitem.setDefaultPickupDelay();
            worldIn.spawnEntity(entityitem);
        }
    }
	
}
