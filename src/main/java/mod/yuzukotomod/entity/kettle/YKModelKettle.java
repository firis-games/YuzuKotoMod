package mod.yuzukotomod.entity.kettle;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * やかん用
 * @author computer
 *
 */
@SideOnly(Side.CLIENT)
public class YKModelKettle extends ModelBase {

	//モデル保存
	public ModelRenderer[] baseModel = new ModelRenderer[1];
	
	/**
	 * コンストラクタで形を定義
	 */
	public YKModelKettle() {
		
		//まずは適当に
		
		//形を定義する
		this.baseModel[0] = new ModelRenderer(this, 0, 10).setTextureSize(64,64);
        this.baseModel[1] = new ModelRenderer(this, 0, 0).setTextureSize(64,64);
		
        //形はまだ何も考えない
        this.baseModel[0].addBox(-10.0F, -8.0F, -1.0F, 20, 16, 2, 0.0F);
        this.baseModel[1].addBox(-10.0F, -8.0F, -9.0F, 20, 16, 8, 0.0F);
		
	}
	
	/**
	 * 描画
     * Sets the models various rotation angles then renders the model.
     */
	@Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
    	
		//定義した形を描画
		for (int i = 0; i < baseModel.length; ++i)
        {
            this.baseModel[i].render(scale);
        }
    	
    }
	
}
