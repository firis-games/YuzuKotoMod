package mod.yuzukotomod.event;

import java.util.Random;

import mod.yuzukotomod.YuzuKotoMod.YuzuKotoItems;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class YKEvents {

	/**
	 * Entityがダメージを受けたときに発生するイベント
	 * @param event
	 */
	@SubscribeEvent
	public void LivingHurtEvent(LivingHurtEvent event) {
		
		//プレイヤーかどうかを確認する
		if (!(event.getEntity() instanceof EntityPlayer)) {
			return;
		}
		
		//マグマダメージかどうかを確認
		if(!(DamageSource.LAVA.getDamageType().equals(event.getSource().damageType)
				|| DamageSource.ON_FIRE.getDamageType().equals(event.getSource().damageType))) {
			//マグマか炎上によるダメージでない
			return;
		}
		
		//EntityをEntityPlayerへ格納
		EntityPlayer player = (EntityPlayer) event.getEntity();
		
		//防具の種類を確認する
		if (!YuzuKotoItems.PURPLEDIAMOND_HELMET.equals(
				player.inventory.armorInventory.get(EntityEquipmentSlot.HEAD.getIndex()).getItem())) {
			//紫ダイヤヘルメットじゃない
			return;
		}
		if (!YuzuKotoItems.PURPLEDIAMOND_CHESTPLATE.equals(
				player.inventory.armorInventory.get(EntityEquipmentSlot.CHEST.getIndex()).getItem())) {
			//紫ダイヤチェストプレートじゃない
			return;
		}
		if (!YuzuKotoItems.PURPLEDIAMOND_LEGGINGS.equals(
				player.inventory.armorInventory.get(EntityEquipmentSlot.LEGS.getIndex()).getItem())) {
			//紫ダイヤレギンスじゃない
			return;
		}
		if (!YuzuKotoItems.PURPLEDIAMOND_BOOTS.equals(
				player.inventory.armorInventory.get(EntityEquipmentSlot.FEET.getIndex()).getItem())) {
			//紫ダイヤブーツじゃない
			return;
		}
		
		//条件を満たしたのでダメージを無効化
		event.setAmount(0);
		
		//Playerの炎上を消化
		player.extinguish();
		
	}
	
	/**
	 * Entityがダメージを受けたときに発生するイベント
	 * @param event
	 */
	@SubscribeEvent
	public void LivingUpdateEvent(LivingEvent.LivingUpdateEvent event) {
		
		
		//updateイベント
		//プレイヤーかどうかを確認する
		if (!(event.getEntity() instanceof EntityPlayer)) {
			return;
		}
		
		//EntityをEntityPlayerへ格納
		EntityPlayer player = (EntityPlayer) event.getEntity();
        
		//hpが最大なら何もしない
		if(player.getHealth() == player.getMaxHealth()) {
			return;
		}
		
		Item offItem = player.inventory.offHandInventory.get(0).getItem();
		
		if (Items.SUGAR == offItem) {
			//1回復
			player.heal(1.0f);
			//一つ消費
			player.inventory.offHandInventory.get(0).shrink(1);
			player.playSound(SoundEvents.ENTITY_ITEM_PICKUP, 0.5F, 0.4F / ((new Random()).nextFloat() * 0.4F + 0.8F));
			
			/*
			//音を鳴らしておわり
			player.world.playSound((EntityPlayer)null, player.posX, player.posY, player.posZ, 
					SoundEvents.ENTITY_ARROW_HIT_PLAYER, SoundCategory.NEUTRAL, 0.5F, 0.4F / ((new Random()).nextFloat() * 0.4F + 0.8F));
					*/
		}
		
	}

	
	
	/**
	 * ブロックを右クリックしたときの処理
	 * @param event
	 */
	@SubscribeEvent
	public void PlayerInteractEvent_RightClickBlock(PlayerInteractEvent.RightClickBlock event) {
			
		//右クリックイベント
		//プレイヤーかどうかを確認する
		/*
		if (!(event.getEntity() instanceof EntityPlayer)) {
			return;
		}
		*/
		//EntityをEntityPlayerへ格納
		EntityPlayer player = event.getEntityPlayer();
		//念のためチェック
		if (event.getEntityPlayer() == null) {
			return;
		}
		
		//ライフMAXならなにもしない
		//メイドさんはケーキがたくさんたべれる
		/*
		if(player.getHealth() == player.getMaxHealth()) {
			return;
		}
		*/
		
		Block cake = event.getEntityPlayer().world.getBlockState(event.getPos()).getBlock();
		if (cake == Blocks.CAKE) {
			
			//おなかいっぱいなら無理やり空腹にする
			//この方法だと問題がある
			//空腹状態ではないのでクリエイティブでたべてたら
			//かなりマイナスになるみたい
			/*
			if (!event.getEntityPlayer().canEat(false)) {
				player.getFoodStats().addStats(-1, 0.0F);
			}
			*/
			//クリエイティブとかきりかえてたらご飯が-1になるけどしょうがない
			if (!event.getEntityPlayer().getFoodStats().needFood()) {
				player.getFoodStats().addStats(-1, 0.0F);
			}
			
			//ライフ回復
			event.getEntityPlayer().heal(4.0F);
			
			//ポーション効果付与
			
			/*
			 * strength
			 * 攻撃力アップ
			 * 
			 * absorption
			 * ショック吸収？
			 * 
			 * resistance
			 * 耐性
			 * 
			 * speed
			 * 速度
			 * 
			 * 跳躍力
			 * jump_boost
			 */
			/*
			//PotionEffectの設定
			int time = 30 * 20; //tick単位
			int level = 1;
			PotionEffect Effect1 = new PotionEffect(
					Potion.getPotionFromResourceLocation("strength"), 
					time, level);
			PotionEffect Effect2 = new PotionEffect(
					Potion.getPotionFromResourceLocation("speed"), 
					time, level);
			PotionEffect Effect3 = new PotionEffect(
					Potion.getPotionFromResourceLocation("resistance"), 
					time, level);
			PotionEffect Effect4 = new PotionEffect(
					Potion.getPotionFromResourceLocation("jump_boost"), 
					time, level);
			
			player.addPotionEffect(Effect1);
			player.addPotionEffect(Effect2);
			player.addPotionEffect(Effect3);
			player.addPotionEffect(Effect4);
			*/
			
			//ポーション設定用
			this.setCakePosion(player, "strength", 0, 30);
			this.setCakePosion(player, "resistance", 0, 30);
			this.setCakePosion(player, "jump_boost", 1, 30);
			
			
			player.playSound(SoundEvents.ENTITY_ITEM_PICKUP, 0.5F, 0.4F / ((new Random()).nextFloat() * 0.4F + 0.8F));
			
			
		}
	}
	
	/**
	 * ケーキポーション設定用
	 */
	private void setCakePosion(EntityPlayer player, String postionName, int level, int time) {

		//最大は90秒まで（とりあえず）
		int maxTickTime = 90 * 20;
		
		//秒をtickへ
		int posionTickTime = time * 20;
		
		posionTickTime = (posionTickTime > maxTickTime) ? maxTickTime : posionTickTime;
		
		//ポーションの時間を計算する
		//今動いてるポーション取得
		PotionEffect actPosionEffect = player.getActivePotionEffect(
				Potion.getPotionFromResourceLocation(postionName));
		
		//存在する場合
		if (actPosionEffect != null) {
			//必要な時間を計算する
			//ポーションレベルが一致する場合
			if (actPosionEffect.getAmplifier() == level) {
				//加算した場合の時間
				posionTickTime = posionTickTime + actPosionEffect.getDuration();
				if (posionTickTime > maxTickTime) {
					posionTickTime = maxTickTime;
				}
			}
		}
		
		//付与するためのポーション定義を生成
		PotionEffect potionEffect = new PotionEffect(
				Potion.getPotionFromResourceLocation(postionName), 
				posionTickTime, level);
		
		//プレイヤーにポーションを設定する
		player.addPotionEffect(potionEffect);
	}
	
}
