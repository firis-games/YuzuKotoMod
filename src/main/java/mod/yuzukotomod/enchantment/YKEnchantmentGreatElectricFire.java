package mod.yuzukotomod.enchantment;
import java.util.Random;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;

public class YKEnchantmentGreatElectricFire extends Enchantment {
	
	/**
	 * コンストラクタ
	 */
	public YKEnchantmentGreatElectricFire() {
		
		super(Rarity.VERY_RARE, EnumEnchantmentType.WEAPON, new EntityEquipmentSlot[] {EntityEquipmentSlot.MAINHAND});
		
		//グレートエレキファイヤーの名前設定
		this.setName("great_electric_fire");
	}
	
    /**
     * ダメージ増加系エンチャント用の処理
     */
	@Override
    public float calcDamageByCreature(int level, EnumCreatureAttribute creatureType)
    {
		//攻撃力を[-3]する
        return -3.0F;
    }
	
	/**
     * 虫特攻で呼ばれてる処理
     */
	@Override
    public void onEntityDamaged(EntityLivingBase user, Entity target, int level)
    {
		//雷ダメージの処理を呼び出す
		target.onStruckByLightning(null);
		
		//落雷の音
		target.playSound(SoundEvents.ENTITY_LIGHTNING_IMPACT, 
				0.4F, 
				0.4F / ((new Random()).nextFloat() * 0.4F + 0.8F));
    }
	
    @Override
    public int getMinEnchantability(int enchantmentLevel)
    {
    	//ダメージ増加Lv4と同じ
        return 34;
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel)
    {
    	//ダメージ増加Lv5と同じ
        return 65;
    }
	
}
