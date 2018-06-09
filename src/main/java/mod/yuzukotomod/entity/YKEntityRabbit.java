package mod.yuzukotomod.entity;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class YKEntityRabbit extends EntityRabbit {
	
	public YKEntityRabbit(World worldIn) {
		super(worldIn);
		this.timeUntilNextItem = 3000 + this.rand.nextInt(3000);
	}
	
	//protected int timeUntilNextItem = 0;
	
	/**
	 * パラメータの設定
	 */
	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        //最大HPを20に設定
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
    }
	
	@Override
	public YKEntityRabbit createChild(EntityAgeable ageable)
    {
		//こどもはカスタムうさぎさん
		return new YKEntityRabbit(this.world);
		
    }
	
	//ウサギの皮用のカウンター
	protected int timeUntilNextItem = 0;
	
	/**
	 * ウサギの皮をドロップする
	 */
	public void onLivingUpdate() {
		
		super.onLivingUpdate();
		
		//サーバ側の処理
		//アイテムを落とす
		if (!this.world.isRemote && !this.isChild()&& --this.timeUntilNextItem <= 0)
        {
			//音
            this.playSound(SoundEvents.ENTITY_CHICKEN_EGG, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
            
            //ウサギの皮をドロップ
            this.dropItem(Items.RABBIT_HIDE, 1);
            
            //カウンターを設定
            this.timeUntilNextItem = 3000 + this.rand.nextInt(3000);
        }
		
	}
}
