package mod.yuzukotomod.event;

import java.util.ArrayList;
import java.util.Random;

import mod.yuzukotomod.item.YKItemHoe;
import mod.yuzukotomod.item.YKItemPickaxe;
import mod.yuzukotomod.util.BlockPosUtil;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.BlockEvent.PlaceEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class YKEventBlockBreak {
	
	/**
	 * キャンセル可能
	 * @param event
	 */
	@SubscribeEvent
	public void HoeBreakEvent(BlockEvent.BreakEvent event) {
		EntityPlayer player = event.getPlayer();
		
		//スニークのときのみ処理を行う
		if (!player.isSneaking()) {
			return;
		}
		
		ItemStack itemstack = player.getHeldItem(player.getActiveHand());
		
		//クワだった場合に一括破壊したい
		if (itemstack.getItem() instanceof YKItemHoe) {
			
			//成長する植物かどうか
			if (event.getState().getBlock() instanceof IGrowable) {
				
				//一括破壊準備
				//おなかが1以下の場合は何もしない
				if (player.getFoodStats().getFoodLevel() <= 1) {
					//if (!event.getWorld().isRemote) {
						//クライアントだけメッセージをだす
						TextComponentTranslation langtext = 
								new TextComponentTranslation("tool.kjizuna.message", new Object[0]);
						
						player.sendMessage(langtext);
					//}
					return;
				}
				
				//おなかぺこりん
				player.getFoodStats().setFoodLevel(Math.max(0, player.getFoodStats().getFoodLevel() - 1));
				
				/*
				ArrayList<BlockPos> posList = BlockPosUtil.getBlockAreaList(event.getPos(), 32);
				
				for(BlockPos pos : posList) {
					
					//同等のメタのみ
					if (event.getWorld().getBlockState(pos) == event.getState()) {
						//ただのブロック破壊
						event.getWorld().destroyBlock(pos, true);
						itemstack.damageItem(1, player);
						if (itemstack.isEmpty()) {
							//空になったら処理終了
							return;
						}
					}
				}
				*/
				//外周ごとにチェックを行うようにする
				ArrayList<ArrayList<BlockPos>> posArrayList = BlockPosUtil.getBlockAreaArrayList(event.getPos(), 32);
				int areaCountFlg = 0;
				for (ArrayList<BlockPos> posList : posArrayList) {
					boolean flg = false;
					for(BlockPos pos : posList) {
						//同等のメタのみ
						if (event.getWorld().getBlockState(pos) == event.getState()) {
							//ただのブロック破壊
							event.getWorld().destroyBlock(pos, true);
							
							//空気への入れ替え
			            	event.getWorld().notifyBlockUpdate(pos, event.getState(), Blocks.AIR.getDefaultState(), 3);
			            	
							itemstack.damageItem(1, player);
							if (itemstack.isEmpty()) {
								//空になったら処理終了
								return;
							}
						}
					}
					//一度もない
					if (!flg) {
						areaCountFlg += 1;
					} else {
						areaCountFlg = 0;							
					}
					if (areaCountFlg >= 2) {
						//指定回数分存在してないからここで処理を終わる
						return;							
					}
				}
			}
		}	
	}
	
	
	/**
	 * 
	 * @param event
	 */
	@SubscribeEvent
	public void PickaxeBreakEvent(BlockEvent.BreakEvent event) {
		
		EntityPlayer player = event.getPlayer();
		//たぶんクライアントサイドじゃないとうごかないけど
		//いまはクライアントじゃないから動いてない
		//player.playSound(SoundEvents.BLOCK_CHEST_OPEN, 100.0F, 1.5F);
		ItemStack itemstack = player.getHeldItem(player.getActiveHand());
		
		if (itemstack.getItem() instanceof YKItemPickaxe) {
			
			//つるはしだったら
			ArrayList<BlockPos> list = BlockPosUtil.getBlockBoxAreaList(event.getPos());
			
			//溶岩or水を置換する
			boolean musicFlg = false;
			for (BlockPos pos : list) {
				
				IBlockState istate = event.getWorld().getBlockState(pos);
				
				
				//水の判断流水・水源問わず
				//if (istate.getMaterial() == Material.WATER && ((Integer)istate.getValue(BlockLiquid.LEVEL)).intValue() == 0)
	            if (istate.getMaterial() == Material.WATER) {
	            	musicFlg = true;
	            	//
	            	event.getWorld().setBlockState(pos, Blocks.PACKED_ICE.getDefaultState());
	            	//後ろのフラグはactivateをまねて
	            	event.getWorld().notifyBlockUpdate(pos, istate, Blocks.PACKED_ICE.getDefaultState(), 3);

                } else if (istate.getMaterial() == Material.LAVA 
                		&& ((Integer)istate.getValue(BlockLiquid.LEVEL)).intValue() == 0) {
                	musicFlg = true;
                	//溶岩源 -> マグマブロック
	            	//event.getWorld().setBlockState(pos, Blocks.MAGMA.getDefaultState());
                	//溶岩源 -> 黒曜石
	            	event.getWorld().setBlockState(pos, Blocks.OBSIDIAN.getDefaultState());
	            	//後ろのフラグはactivateをまねて
	            	event.getWorld().notifyBlockUpdate(pos, istate, Blocks.OBSIDIAN.getDefaultState(), 3);

                }else if (istate.getMaterial() == Material.LAVA 
                		&& ((Integer)istate.getValue(BlockLiquid.LEVEL)).intValue() > 0) {
                	//溶岩流 -> 丸石
                	musicFlg = true;
                	event.getWorld().setBlockState(pos, Blocks.COBBLESTONE.getDefaultState());
                	
                	//後ろのフラグはactivateをまねて
	            	event.getWorld().notifyBlockUpdate(pos, istate, Blocks.COBBLESTONE.getDefaultState(), 3);
                }
	          
			}
			
			//音はあとでかんがえる
			//サーバーからクライアントへのパケット投げて音を鳴らすとか
			//そのへんもちょっとかんがえる
			if (musicFlg) {				
			}
			
		}
		
	}
	
	/**
	 * ブロック破壊のスピード？
	 * @param event
	 */
	@SubscribeEvent
	public void BreakSpeed(PlayerEvent.BreakSpeed event) {

		EntityPlayer player = event.getEntityPlayer();
		/*
		if (player.getActiveHand() == null) {
			return;
		}
		*/
		//ItemStack itemstack = player.getHeldItem(player.getActiveHand());
		ItemStack itemstack = player.getHeldItemMainhand();
		if (itemstack.getItem() instanceof YKItemPickaxe
			&& event.getState().getBlock() == Blocks.OBSIDIAN) {
			
			event.setNewSpeed(event.getOriginalSpeed() * 10);
			System.out.println(event.getOriginalSpeed());
			System.out.println(event.getNewSpeed());

		}

		
	}
	

}
