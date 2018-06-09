package mod.yuzukotomod.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;

public class YKEnchantmentThunder extends Enchantment{

	/**
	 * エンチャントの設定
	 */
	protected YKEnchantmentThunder() {

		super(Rarity.COMMON, EnumEnchantmentType.WEAPON, EntityEquipmentSlot.values());
		this.setName("yukarin");
	}

}
