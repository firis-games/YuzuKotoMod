package mod.yuzukotomod.entity.kettle;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

/**
 * やかん用
 * @author computer
 *
 */
public class YKRenderKettle <T extends YKEntityKettle> extends Render<T>{

	/*
	 * 
	 */
	private static final ResourceLocation MINECART_TEXTURES = new ResourceLocation("yuzukotomod", 
    		"textures/entity/ykyakan.png");
	
    /** モデルのベース */
    protected ModelBase modelBase = new YKModelKettle();
    
	/**
	 * コンストラクタ
	 * @param renderManager
	 */
	public YKRenderKettle(RenderManager renderManager) {
		
		super(renderManager);
		
		//影のサイズ
		//this.shadowSize = 0.5F;
	}

	@Override
	protected ResourceLocation getEntityTexture(T entity) {
		return MINECART_TEXTURES;
	}
	
	/**
     * Renders the desired {@code T} type Entity.
     */
	@Override
    public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
    	//描画処理
		GlStateManager.pushMatrix();
        this.bindEntityTexture(entity);
        
        GlStateManager.translate(x, y, z);
        GlStateManager.scale(1.0F, 1.0F, 1.0F);
        
        
        this.modelBase.render(entity, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
        
        /*
        //ブロックを描画
        this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        
        GlStateManager.pushMatrix();
        //ブロックの描画
        Minecraft.getMinecraft().getBlockRendererDispatcher().renderBlockBrightness(Blocks.DIAMOND_BLOCK.getDefaultState(),
        		entity.getBrightness(partialTicks));

        GlStateManager.popMatrix();
        */
        
        GlStateManager.popMatrix();
        
        
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
		
    }

}
