package mod.yuzukotomod.entity;

import net.minecraft.client.model.ModelRabbit;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class YKRenderRabbit extends RenderLiving<YKEntityRabbit> {

	public YKRenderRabbit(RenderManager rendermanagerIn) {
		//Modelの設定はうさぎさん
		super(rendermanagerIn, new ModelRabbit(), 0.3F);
	}

	@Override
	protected ResourceLocation getEntityTexture(YKEntityRabbit entity) {
		//テクスチャは自分で用意したのを設定
		return new ResourceLocation("yuzukotomod:textures/entity/ykrabbit.png");
	}
}
