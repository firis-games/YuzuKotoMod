package mod.yuzukotomod.entity.kettle;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * やかん用
 * @author computer
 *
 */
public class YKEntityKettle extends Entity {

	public YKEntityKettle(World worldIn) {
		super(worldIn);
		
		//EntityとEntityLiving
		//こんかいは見た目だけのやかんだからEntityでやる
		//EntityLivingだとダメージとか押されて移動とかいろいろ処理をやってるよう
		
	}
		
	
	@Override
	protected void entityInit() {
		
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		// TODO 自動生成されたメソッド・スタブ
		
	}
	
	/**
     * Called to update the entity's position/logic.
     */
	@Override
    public void onUpdate()
    {
		super.onUpdate();

		//とりあえず無理やり座標の端数を落とす
		BlockPos pos = new BlockPos(this.posX, this.posY, this.posZ);
		this.setPosition(pos.getX(), pos.getY(), pos.getZ());
    }

	
}
