package mod.yuzukotomod.entity;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class YKRenderChicken extends RenderLiving<YKEntityChicken> {

	public YKRenderChicken(RenderManager rendermanagerIn) {
		//renderのマネージャー
		//モデルのベース
		//影のサイズ
		super(rendermanagerIn, new YKModelChicken(), 0.3F);
	}

	@Override
	protected ResourceLocation getEntityTexture(YKEntityChicken entity) {
		//return new ResourceLocation("minecraft:textures/entity/chicken.png");
		return new ResourceLocation("yuzukotomod:textures/entity/ykchicken.png");
	}
	
    /**
     * Defines what float the third param in setRotationAngles of ModelBase is
     */
	@Override
    protected float handleRotationFloat(YKEntityChicken livingBase, float partialTicks)
    {
        float f = livingBase.oFlap + (livingBase.wingRotation - livingBase.oFlap) * partialTicks;
        float f1 = livingBase.oFlapSpeed + (livingBase.destPos - livingBase.oFlapSpeed) * partialTicks;
        return (MathHelper.sin(f) + 1.0F) * f1;
    }

}
