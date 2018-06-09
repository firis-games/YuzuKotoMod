package mod.yuzukotomod.event;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class YKEventEnchantmentChickenPower {
	/**
	 * エンチャント用のイベント
	 * 空から落ちる速度を調整する
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
        
		//プレイヤーの手に持ってるアイテムを確認
		ItemStack mainItem = player.getHeldItemMainhand();
		ItemStack offItem = player.inventory.offHandInventory.get(0);

		//エンチャントチェック
		Enchantment enc = Enchantment.getEnchantmentByLocation("yuzukotomod:chicken_power");
		boolean encflg = false;
		if (EnchantmentHelper.getEnchantmentLevel(enc, mainItem) > 0) {
			//エンチャントされている
			encflg = true;
		} else if (EnchantmentHelper.getEnchantmentLevel(enc, offItem) > 0) {
			encflg = true;
		}
		
		//エンチャントがついている場合
		if (encflg) {
			//落下距離を無効化
			player.fallDistance = 0;
			//スニークしてない場合のときだけ
			//落下スピードを減らす
			//にわとりさんと同じ
			if (!player.onGround && player.motionY < 0.0D && !player.isSneaking()) {
				player.motionY *= 0.6D;
		    }
		}		
	}	
}
