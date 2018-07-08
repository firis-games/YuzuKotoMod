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
	public ModelRenderer[] baseModel = new ModelRenderer[7];
	
	/**
	 * コンストラクタで形を定義
	 */
	public YKModelKettle() {
		
		//まずは適当に
		
		//形を定義する
		this.baseModel[0] = new ModelRenderer(this, 0, 0).setTextureSize(64,64);
		this.baseModel[1] = new ModelRenderer(this, 0, 16).setTextureSize(64,64);
		this.baseModel[2] = new ModelRenderer(this, 0, 20).setTextureSize(64,64);
		this.baseModel[3] = new ModelRenderer(this, 0, 20).setTextureSize(64,64);
		this.baseModel[4] = new ModelRenderer(this, 24, 20).setTextureSize(64,64);
		this.baseModel[5] = new ModelRenderer(this, 0, 25).setTextureSize(64,64);
		this.baseModel[6] = new ModelRenderer(this, 24, 29).setTextureSize(64,64);
		
        //形はまだ何も考えない
		//1ブロックあたりのサイズが16x16x16になってるみたい
        //this.baseModel[0].addBox(0.0F, 0.0F, 0.0F, 16, 16, 16, 0.0F);
		//ベース
        this.baseModel[0].addBox(0.0F, 0.0F, 0.0F, 8, 8, 8, 0.0F);
        this.baseModel[0].setRotationPoint(4F, 0F, 4F);
        
		/**
		 * AddBoxでベース座標に対する四角いオブジェクトを作成する
		 * setRotationPointでベース座標自体を移動する
		 * rotateAngleXYZでそれぞれ
		 * X軸Y軸Z軸方向への回転 円周率？Math.PI*2でちょうど1週になってるみたい
		 */
        //口の部分
        //this.baseModel[1].addBox(3.0F, -6.0F, 7.0F, 4, 2, 2, 0.0F);
        this.baseModel[1].addBox(0, 0, 0, 6, 2, 2, 0.0F);
        //回転軸の指定は軸をずらした分表示位置もずれるみたい
        //this.baseModel[1].setRotationPoint(2F, 1F, 1F);
        this.baseModel[1].setRotationPoint(-1F, 6F, 7F);
        
        
        this.baseModel[1].rotateAngleZ = (float) (Math.PI * 2 / 360 * (360 - 30));
        
        //回転軸はやっぱり円周率のこれで計算してるみたい
        //this.baseModel[1].rotateAngleZ = (float) (Math.PI / 4.0F * 3.0F);
        
        //とって部分の予定
        //this.baseModel[2].addBox(3.0F, -6.0F, 7.0F, 4, 2, 2, 0.0F);
        this.baseModel[2].addBox(0, 0, 0, 1, 4, 1, 0.0F);
        this.baseModel[2].setRotationPoint(4F, 8F, 4F + 3.5F);
        
        this.baseModel[3].addBox(0, 0, 0, 1, 4, 1, 0.0F);
        this.baseModel[3].setRotationPoint(11F, 8F, 4F + 3.5F);
        
        //とっての持つ部分
        this.baseModel[4].addBox(0, 0, 0, 1, 6, 1, 0.0F);
        this.baseModel[4].setRotationPoint(4F + 1F, 12F, 4F + 3.5F);
        this.baseModel[4].rotateAngleZ = (float) (Math.PI * 2 / 360 * (360 - 90));
        
        //やかんのふた1
        this.baseModel[5].addBox(0, 0, 0, 6, 1, 6, 0.0F);
        this.baseModel[5].setRotationPoint(4F + 1F, 8F, 4F + 1F);
        
        //やかんのふた2
        this.baseModel[6].addBox(0, 0, 0, 2, 1, 2, 0.0F);
        this.baseModel[6].setRotationPoint(4F + 3F, 8F + 1F, 4F + 3F);
        
        //this.baseModel[2].setRotationPoint(2F, 1F, 1F);
        //this.baseModel[2].rotateAngleY = 45.0F;
        
        //this.baseModel[1].addBox(-10.0F, -8.0F, -9.0F, 20, 16, 8, 0.0F);
		
	}
	
	/**
	 * 描画
     * Sets the models various rotation angles then renders the model.
     */
	@Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
		
		//float rot = (float) (entityIn.getEntityWorld().getTotalWorldTime() % 360) / 360;
		//System.out.println((float) (Math.PI*2* rot));
		//this.baseModel[1].rotateAngleZ = (float) (Math.PI*2 * rot);
    	
		//定義した形を描画
		for (int i = 0; i < baseModel.length; ++i)
        {
            this.baseModel[i].render(scale);
        }
    	
    }
	
}
