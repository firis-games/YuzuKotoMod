package mod.yuzukotomod.entity;

import net.minecraft.client.model.ModelRabbit;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.util.ResourceLocation;

public class YKRenderRabbit2 extends RenderLiving<YKEntityRabbit> {

	public YKRenderRabbit2(RenderManager rendermanagerIn) {
//		super(rendermanagerIn, modelbaseIn, shadowsizeIn);
		//renderのマネージャー
		//モデルのベース
		//影のサイズ
		super(rendermanagerIn, new ModelRabbit(), 0.3F);
	}

	@Override
	protected ResourceLocation getEntityTexture(YKEntityRabbit entity) {
		// TODO 自動生成されたメソッド・スタブ
		return new ResourceLocation("yuzukotomod:textures/entity/ykrabbit.png");
	}
	/*
	 	public YKRenderRabbit(RenderManager rendermanagerIn) {
//		super(rendermanagerIn, modelbaseIn, shadowsizeIn);
		//renderのマネージャー
		//モデルのベース
		//影のサイズ
		super(rendermanagerIn, new ModelRabbit(), 0.3F);
	}

	@Override
	protected ResourceLocation getEntityTexture(YKEntityRabbit entity) {
		// TODO 自動生成されたメソッド・スタブ
		return new ResourceLocation("yuzukotomod:textures/entity/ykrabbit.png");
	}
	 */


}
