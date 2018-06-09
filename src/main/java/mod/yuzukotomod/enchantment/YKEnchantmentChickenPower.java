package mod.yuzukotomod.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;

public class YKEnchantmentChickenPower extends Enchantment {

	public YKEnchantmentChickenPower() {
		
		super(Rarity.VERY_RARE, EnumEnchantmentType.BREAKABLE, new EntityEquipmentSlot[] {EntityEquipmentSlot.MAINHAND});
		
		//にわとりの加護名前設定
		this.setName("chicken_power");
		
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
