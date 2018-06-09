package mod.yuzukotomod.enchantment;

import java.util.Random;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.DamageSource;

public class YKEnchantment extends Enchantment{

	/**
	 * コンストラクタ
	 */
	public YKEnchantment() {
		
		super(Rarity.COMMON, EnumEnchantmentType.ALL, EntityEquipmentSlot.values());
		this.setName("yukarin");
	}

	
	/**
     * Calculates the damage protection of the enchantment based on level and damage source passed.
     */
	@Override
    public int calcModifierDamage(int level, DamageSource source)
    {
		//0から20が上限
		//を25で終わった係数で計算して
		//1-係数
		//の倍率が実際のダメージになるはず
		//アーマーの4部位分を計算して最大が20になってるみたいね
        return 20;
    }
	
	
    /**
     * Calculates the additional damage that will be dealt by an item with this enchantment. This alternative to
     * calcModifierDamage is sensitive to the targets EnumCreatureAttribute.
     */
	@Override
    public float calcDamageByCreature(int level, EnumCreatureAttribute creatureType)
    {
		//ダメージ特殊攻撃用かな
		//種族ごとのダメージ増加につかわれてるみたいね
		
		//クリーチャータイプ
		//ぞんび
		//むし
		//むらびと
		//ぐらいしか区分けがないっぽい
		
        //return -15.0F;
        return -3.0F;
    }
	
	
	
    /**
     * Whenever an entity that has this enchantment on one of its associated items is damaged this method will be
     * called.
     */
	@Override
    public void onUserHurt(EntityLivingBase user, Entity attacker, int level)
    {
		//使用して壊れた場合に呼ばれる気がする？
		//ちがう
		//とげのよろいとかそっち系か？
		int a = 0;
		a = 1 + 1;
		
		//((EntityLivingBase)attacker).
		
		attacker.attackEntityFrom(DamageSource.causeThornsDamage(user), 100);
    }
    
    /**
     * Called whenever a mob is damaged with an item that has this enchantment on it.
     */
	@Override
    public void onEntityDamaged(EntityLivingBase user, Entity target, int level)
    {
		
		//矢とか？
		//ふつうになぐってもダメージが発生してる
		int a = 0;
		a = 1 + 1;
		
		target.setFire(5);
		//target.motionY = target.motionY + 0.5; 
		target.motionY = target.motionY + 0.5;
		
		
		//雷
		target.onStruckByLightning(null);
		
		//target.playSound(SoundEvents.ENTITY_LIGHTNING_IMPACT, 0.5F, 0.4F / ((new Random()).nextFloat() * 0.4F + 0.8F));
		target.playSound(SoundEvents.ENTITY_LIGHTNING_IMPACT, 
				0.4F, 
				0.4F / ((new Random()).nextFloat() * 0.4F + 0.8F));
		

    }
}
