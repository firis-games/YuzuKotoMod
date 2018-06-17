package mod.yuzukotomod.item;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPrismarine;
import net.minecraft.block.BlockStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTable;

public class YKItemTest extends Item {
	
	/**
     * Called when a Block is right-clicked with this Item
     */
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {

		//return onItemUse_loottables2(player, worldIn, pos, hand, facing, hitZ, hitZ, hitZ);
		//return onItemUse_createBlock(player, worldIn, pos, hand, facing, hitZ, hitZ, hitZ);
		return onItemUse_createBlock2(player, worldIn, pos, hand, facing, hitZ, hitZ, hitZ);
    }
	
	public EnumActionResult onItemUse_createBlock(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) 
	{
		
		//クリックしたブロックから西へブロックを設置していく？
		
		IBlockState istateBase = Blocks.PRISMARINE.getStateFromMeta(BlockPrismarine.BRICKS_META);
		
		
		IBlockState istateLantern = Blocks.SEA_LANTERN.getDefaultState();
		IBlockState istateGlass = Blocks.GLASS.getDefaultState();

		IBlockState istateAir = Blocks.AIR.getDefaultState();
		
		IBlockState istateRail = Blocks.RAIL.getDefaultState();
				
		BlockPos basePos = pos;
		for (int row = 0; row < 200; row++) {
			for (int i = 0; i < 4; i++) {
				
				//設置していく
				world.setBlockState(basePos, istateBase);
				world.setBlockState(basePos.north(1), istateBase);
				world.setBlockState(basePos.north(2), istateBase);
				world.setBlockState(basePos.north(3), istateBase);
				world.setBlockState(basePos.south(1), istateBase);
				world.setBlockState(basePos.south(2), istateBase);
				world.setBlockState(basePos.south(3), istateBase);
				
				if (i == 1) {
					world.setBlockState(basePos.north(2), istateLantern);
				}
				if (i == 3) {
					world.setBlockState(basePos.north(1), istateLantern);
				}
				
				
				int glassTop = 5;
				//ガラスをつくる
				for (int glass = 1; glass < glassTop; glass++) {
					world.setBlockState(basePos.north(3).up(glass), istateGlass);
					world.setBlockState(basePos.south(3).up(glass), istateGlass);
					world.setBlockState(basePos.north(2).up(glass), istateAir);
					world.setBlockState(basePos.south(2).up(glass), istateAir);
					world.setBlockState(basePos.north(1).up(glass), istateAir);
					world.setBlockState(basePos.south(1).up(glass), istateAir);
					world.setBlockState(basePos.up(glass), istateAir);
				}
				//ガラスの天井
				BlockPos glassTopPos = basePos.up(glassTop);
				world.setBlockState(glassTopPos, istateGlass);
				world.setBlockState(glassTopPos.north(1), istateGlass);
				world.setBlockState(glassTopPos.north(2), istateGlass);
				world.setBlockState(glassTopPos.north(3), istateGlass);
				world.setBlockState(glassTopPos.south(1), istateGlass);
				world.setBlockState(glassTopPos.south(2), istateGlass);
				world.setBlockState(glassTopPos.south(3), istateGlass);
				
				//線路を設置
				//南の1に
				world.setBlockState(basePos.south(1).up(1), istateRail);
				
				basePos = basePos.west();
				
			}
		}
		//北と南に7マス設置？
		
		
		
		return EnumActionResult.SUCCESS; 
	}
	
	
	/***
	 * 通常線路用
	 * @param player
	 * @param world
	 * @param pos
	 * @param hand
	 * @param facing
	 * @param hitX
	 * @param hitY
	 * @param hitZ
	 * @return
	 */
	public EnumActionResult onItemUse_createBlock2(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) 
	{
		
		//クリックしたブロックを基点に設置していく
		IBlockState istateBase = Blocks.STONE.getStateFromMeta(BlockStone.EnumType.ANDESITE_SMOOTH.getMetadata());
		IBlockState istateAir = Blocks.AIR.getDefaultState();
		IBlockState istateRail = Blocks.RAIL.getDefaultState();
		
		IBlockState istateLantern = Blocks.SEA_LANTERN.getDefaultState();
				
		BlockPos basePos = pos;
		for (int row = 0; row < 200; row++) {
			
			//ベースの線路
			for (int i = 0; i < 18; i++) {
				
				//設置していくベース
				world.setBlockState(basePos, istateBase);
				world.setBlockState(basePos.north(1), istateBase);
				world.setBlockState(basePos.south(1), istateBase);
				
				//真ん中はランタン
				if (i == 8) {
					world.setBlockState(basePos.north(1), istateLantern);
					world.setBlockState(basePos.south(1), istateLantern);
				}
				
				//ベースの上の空間を空気と置き換え
				int airTop = 10;
				for (int idx = 1; idx < airTop; idx++) {
					world.setBlockState(basePos.north(1).up(idx), istateAir);
					world.setBlockState(basePos.south(1).up(idx), istateAir);
					world.setBlockState(basePos.up(idx), istateAir);
				}
				
				//線路を設置
				//線路
				world.setBlockState(basePos.up(1), istateRail);
				
				basePos = basePos.west();
				
			}
			
			//一回位置を戻す
			basePos = basePos.east();
			
			//1セット分に1回は柱を生成
			world.setBlockState(basePos.north(1), istateLantern);
			world.setBlockState(basePos.south(1), istateLantern);
			
			for (int i = 0; i < pos.getY() - 5; i++) {
				world.setBlockState(basePos.down(i), istateBase);
				
				if ((i % 6) == 5) {
					world.setBlockState(basePos.down(i), istateLantern);
				}
			}
			
			basePos = basePos.west();
		}
		
		return EnumActionResult.SUCCESS; 
	}
	
	//Loot table直接ドロップテスト用
	public EnumActionResult onItemUse_loottables2(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
		
		//ここでいろいろやる
		
		String loot = "minecraft:chests/spawn_bonus_chest";
		
		//loot tabbleを生成する
		LootTable table = worldIn.getLootTableManager().getLootTableFromLocation(new ResourceLocation("minecraft:chests/spawn_bonus_chest"));
		
		//たぶんサーバー側だけ？
		//サーバーサイド以外はおちる
		LootContext ctx = new LootContext.Builder((WorldServer) worldIn).build();

		List<ItemStack> stacks = table.generateLootForPools(worldIn.rand, ctx);
		
		

		return EnumActionResult.SUCCESS;
    }
	
	/**
     * Called when a Block is right-clicked with this Item
     */
	/*
	 * LOOTテーブルのテスト用のアイテム
	 */
	//@Override
    public EnumActionResult onItemUse_Loottables(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
		IBlockState iblockstate = worldIn.getBlockState(pos);
        Block block = iblockstate.getBlock();

        if (!block.isReplaceable(worldIn, pos))
        {
            pos = pos.offset(facing);
        }

        ItemStack itemstack = new ItemStack(Blocks.CHEST);
        
        
        Blocks.CHEST.onBlockPlacedBy(worldIn, pos, Blocks.CHEST.getDefaultState(), player, itemstack);
        
        TileEntity entity = worldIn.getTileEntity(pos);
        
        NBTTagCompound tag = new NBTTagCompound();
        entity.writeToNBT(tag);
        //tag.setString("LootTable", "minecraft:chests/nether_bridge");
        tag.setString("LootTable", "minecraft:chests/spawn_bonus_chest");
        entity.readFromNBT(tag);
        
        return EnumActionResult.SUCCESS;
    }
	
	
	 /**
     * Called each tick as long the item is on a player inventory. Uses by maps to check if is on a player hand and
     * update it's contents.
     */
	@Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
		//System.out.println("アップデート");
    }
	
	
	/**
	 * 
	 * 視線の先のブロックを拾う
	 * Entityは矢のロジックのなかにあるみたい
     * Called when the equipped item is right clicked.
     */
	@Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn)
    {
		
		if (worldIn.isRemote) {
			return new ActionResult(EnumActionResult.PASS, player.getHeldItem(handIn));
		}
    	
		//プレイヤーの視線ベクトル
		Vec3d playerLook = player.getLookVec();
		playerLook = playerLook.scale(10.0D);

		
		//playerLook.scale(100);
		
		//プレイヤーの位置ベクトル
		//Vec3d playerLoc = player.getPositionVector();
		//Vec3d playerLoc = new Vec3d(player.lastTickPosX, player.lastTickPosY, player.lastTickPosZ);
		
		Vec3d playerLoc = player.getPositionVector();
		playerLoc = playerLoc.addVector(0.0D, 1.6D, 0.0D);
		playerLoc = playerLoc.addVector(0.0D, player.eyeHeight, 0.0D);
		
		RayTraceResult ret2 = worldIn.rayTraceBlocks(playerLoc, playerLook.add(playerLoc));

		
		
		
		Block test = worldIn.getBlockState(ret2.getBlockPos()).getBlock();
		
		player.sendMessage(new TextComponentString(test.getRegistryName().toString()));
		
		
        return new ActionResult(EnumActionResult.PASS, player.getHeldItem(handIn));
    }

}
