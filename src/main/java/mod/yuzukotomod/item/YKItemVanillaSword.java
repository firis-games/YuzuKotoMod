package mod.yuzukotomod.item;

import javax.annotation.Nullable;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import mod.yuzukotocore.iInterface.YKIShield;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class YKItemVanillaSword extends ItemSword implements YKIShield{

	protected final float attackDamage;
	protected final Item.ToolMaterial material;
    
	/**
	 * 1.9より以前仕様のマイクラ剣
	 * @param material
	 */
	public YKItemVanillaSword(ToolMaterial material) {
		
		super(material);
		
		//ガードアクションでモデル設定を切り替える
		this.addPropertyOverride(new ResourceLocation("sword_blocking"), new IItemPropertyGetter() {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                return entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack ? 1.0F : 0.0F;
            }
        });
		
		//ItemSwordでprivateになっているためこちらでも参照できるように保持する
		this.material = material;
        this.attackDamage = 3.0F + material.getDamageVsEntity();
	}
	
	
    /**
     * ItemStack sensitive version of getItemAttributeModifiers
     * 剣の攻撃力やチャージ時間を設定する
     * ItemSwordではgetItemAttributeModifiersで定義されているが
     * このメソッドは非推奨になってるので利用しない
     */
	@Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
        Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();
        
        if (slot == EntityEquipmentSlot.MAINHAND) {
        	//攻撃力
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double)this.attackDamage, 0));
            //チャージ時間　（攻撃速度として+4した数値が表示されてるよう？）
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", 96D, 0));
        }
        
        return multimap;
    }
	
	
    /**
     * returns the action that specifies what animation to play when the items is being used
     * 右クリック時のアクション設定
     */
	@Override
    public EnumAction getItemUseAction(ItemStack stack)
    {
		//ガードアクション設定
        return EnumAction.BLOCK;
    }
    
    /**
     * How long it takes to use or consume an item
     * 右クリック時のアクションの時間 tick
     */
	@Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 72000;
    }
	
	/**
     * Called when the equipped item is right clicked.
     */
	@Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		
		//ガード用設定
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        playerIn.setActiveHand(handIn);
        
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
    }
}
