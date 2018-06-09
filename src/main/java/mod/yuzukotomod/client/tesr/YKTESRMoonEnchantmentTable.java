package mod.yuzukotomod.client.tesr;

import mod.yuzukotomod.tileentity.YKTEMoonEnchantmentTable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;

/**
 * エンチャントテーブルの上にアイテムを描画する
 * @author computer
 *
 */
public class YKTESRMoonEnchantmentTable extends TileEntitySpecialRenderer<YKTEMoonEnchantmentTable> {

	/**
	 * 描画場所？
	 */
	@Override
	public void renderTileEntityAt(YKTEMoonEnchantmentTable te, double x, double y, double z, float partialTicks, int destroyStage) {
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)x + 0.5F, (float)y, (float)z + 0.5F);
        renderItem(te, x, y, z, partialTicks);
        GlStateManager.popMatrix();
	}
	
	/** 保存用 */
	protected ItemStack renderItemStack = ItemStack.EMPTY;
	protected EntityItem renderItemEntity = null;
	
	/**
     * Render the mob inside the mob spawner.
     */
    protected void renderItem(YKTEMoonEnchantmentTable te, double posX, double posY, double posZ, float partialTicks) {
    	
    	ItemStack itemstack = te.getStackInSlot(0);
    	//空チェック
    	if (itemstack == ItemStack.EMPTY) {
    		return;
    	}
    	
    	//アイテムIDを比較
    	//違う場合は表示用インスタンスを生成する
    	if (itemstack != renderItemStack) {
    		renderItemStack = itemstack;
    		renderItemEntity = new EntityItem(te.getWorld());
    		renderItemEntity.setEntityItemStack(itemstack.copy());
    		
    		//これを設定しないとEntityItemがあらぶる
    		renderItemEntity.hoverStart = 0;
    	}
    	
    	//Mobの拡大率
    	float scale = 1.0F;
    	
    	//基本座標をブロック位置の中心部へ変更は上の階層でやってる
    	
    	GlStateManager.scale(scale, scale, scale);
    	
    	//ブロックの上（エンチャントテーブルの高さ）に表示する
        GlStateManager.translate(0.0F, 0.75F - 0.1F, 0.0F);
        
        //ワールドのプレイ時間で回転させる
        float rot = te.getWorld().getTotalWorldTime() % 360;
        rot = rot * 2;
        
        GlStateManager.rotate(rot, 0.0F, 1.0F, 0.0F);
        
        //これを初期化してないとぷるぷるする
        partialTicks = 0;
        
        //Entityをレンダリング
        Minecraft.getMinecraft().getRenderManager().doRenderEntity(renderItemEntity, 0.0D, 0.0D, 0.0D, 0.0F, partialTicks, false);
        
    }
	
}
