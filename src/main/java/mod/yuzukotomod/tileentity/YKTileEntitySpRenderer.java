package mod.yuzukotomod.tileentity;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelCreeper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderCreeper;
import net.minecraft.client.renderer.entity.RenderEntityItem;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.FakePlayer;

public class YKTileEntitySpRenderer extends TileEntitySpecialRenderer<YKTileEntity>{

	
	/**
	 * 描画場所？
	 */
	@Override
	public void renderTileEntityAt(YKTileEntity te, double x, double y, double z, float partialTicks, int destroyStage) {
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)x + 0.5F, (float)y, (float)z + 0.5F);
        //renderMob(te, x, y, z, partialTicks);
        renderMob0(te, x, y, z, partialTicks);
        GlStateManager.popMatrix();

        //実験用
        //renderMob2(te, x, y, z, partialTicks);

	}
	
	/**
     * Render the mob inside the mob spawner.
     */
    public void renderMob0(YKTileEntity te, double posX, double posY, double posZ, float partialTicks) {
    	
    	Entity entity = te.getEntity();
    	if (entity == null) {
    		return;
    	}
    	
    	//Mobの拡大率
    	float scale = 1.0F;
    	
    	//基本座標をブロック位置の中心部へ変更は上の階層でやってる
    	
    	GlStateManager.scale(scale, scale, scale);
    	
    	//ブロックの上に表示するために
        GlStateManager.translate(0.0F, 1.0F, 0.0F);
        
        float rot = te.getWorld().getWorldTime() % 360;
        
        rot = rot * 2;
        
        //GlStateManager.rotate(rot, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(0, 0.0F, 1.0F, 0.0F);
        
        //これを初期化してないと首がぷるぷるする
        partialTicks = 0;
        
        //Entityをレンダリング
        Minecraft.getMinecraft().getRenderManager().doRenderEntity(entity, 0.0D, 0.0D, 0.0D, 0.0F, partialTicks, false);
        
        
    }

	/***
	 * OpenGLで直接描画のテスト
	 * 失敗
	 * テクスチャの書き込み方がよくわかんないから独自にやれないか考える
	 * @param te
	 * @param x
	 * @param y
	 * @param z
	 * @param partialTicks
	 */
	public void renderMob2(YKTileEntity te, double x, double y, double z, float partialTicks) {
		
		GL11.glPushMatrix();
		/*
		 * 位置の調整と色の設定.
		 */
		//GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslatef((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F+2F);

		//GL11.glRotatef(-180F, 1.0F, 0.0F, 0.0F);
		//GL11.glTranslatef(0.0F, -0.75F, 0.0F);

		/*
		 * Y軸を回転させる処理.
		 */
		GL11.glRotatef(0.0F, 0.0F, 1.0F, 0.0F);

		/* https://github.com/reginn/Tutorial-Renderer/blob/master/src/main/java/com/sample/renderer/tileentity/MobRenderer.java
		ResourceLocation resource = new ResourceLocation("textures/entity/creeper/creeper.png");
		this.bindTexture(resource);
		
		ModelCreeper modelCreeper = new ModelCreeper();
		
		modelCreeper.render((Entity) null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
		*/
		
        //EntityItem用のレンダー定義
        //RenderCreeper creeperRender = new RenderCreeper(
        //		Minecraft.getMinecraft().getRenderManager());
        
        EntityCreeper entity = new EntityCreeper(te.getWorld());        
        //creeperRender.doRender(entity, 0, 0, 0, 0, 0);
        
        //デバッグ用？
        //Minecraft.getMinecraft().getRenderManager().setRenderOutlines(true);
		
        Minecraft.getMinecraft().getRenderManager().doRenderEntity(
        		entity, 0.0D, 0.0D, 0.0D, 0.0F, 0, false);
		
        /* 直接描画？*/
        GL11.glColor3d(1.0, 0.0, 0.0);
        //裏面描画なしを無効化
        GL11.glDisable(GL11.GL_CULL_FACE);
        
        
        //this.bindTexture(texture);
        //テクスチャのマネージャー
        TextureManager texturemanager = this.rendererDispatcher.renderEngine;
        ResourceLocation texture = new ResourceLocation("textures/entity/creeper/creeper.png");
        texturemanager.bindTexture(texture);
        ITextureObject textureObj = texturemanager.getTexture(texture);
        
        //どうにかこうにかしてテクスチャをバインドしたい
        
        //テクスチャをバインド
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureObj.getGlTextureId());
        
        
        GL11.glBegin(GL11.GL_QUADS);
        
        GL11.glVertex2d(-0.5, -0.5);
        GL11.glVertex2d(-0.5, 0.5);
        GL11.glVertex2d(0.5, 0.5);
        GL11.glVertex2d(0.5, -0.5);
        
        //ここから上までで四角を描画してる
       
        
        //GL11.glBindTexture 最終的にここまでやってるみたい
        
        
        
        
        
        //終了処理
        
        GL11.glEnd();
        GL11.glFlush();
        /* 直接描画？*/
        
        //裏面描画なしを有効化
        GL11.glEnable(GL11.GL_CULL_FACE);
        
		GL11.glPopMatrix();
        
		
        
	}
	
	/**
     * Render the mob inside the mob spawner.
     */
    public static void renderMob(YKTileEntity te, double posX, double posY, double posZ, float partialTicks) {
    	
        //Entity entity = te.Chicken;
        Entity entity = te.getEntity();
        
        //System.out.println(partialTicks);
        
        if (entity != null)
        {
            float f = 0.53125F;
            float f1 = Math.max(entity.width, entity.height);

            if ((double)f1 > 1.0D)
            {
                f /= f1;
            }
            
            
            f = 1.0F;
            /*
            //GLでなんかやってるっぽいけどわかんない
            //初期位置をずらす？
            //GlStateManager.translate(0.0F, 0.4F, 0.0F);
            //ブロックの上に表示するために
            GlStateManager.translate(0.0F, 1.0F, 0.0F);
            //回転させない？
            //GlStateManager.rotate(0.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(0.0F, 0.0F, 0.0F, 0.0F);
            //下に少し下げてるみたいだけど
            //GlStateManager.translate(0.0F, -0.2F, 0.0F);
            GlStateManager.translate(0.0F, 0.0F, 0.0F);
            //GlStateManager.rotate(-30.0F, 1.0F, 0.0F, 0.0F);
            float timeD = (float) (360.0 * (double) (System.currentTimeMillis() & 0x3FFFL) / (double) 0x3FFFL);
            GlStateManager.rotate(timeD, 0.0F, 0.0F, 0.0F);
            GlStateManager.scale(f, f, f);
            entity.setLocationAndAngles(posX, posY, posZ, 0.0F, 0.0F);
            partialTicks = 0F;
            */
            
            //Minecraft.getMinecraft().getRenderManager().doRenderEntity(entity, 0.0D, 0.0D, 0.0D, 0.0F, partialTicks, false);
            
            /*
            //ブロックの上に表示するために
            GlStateManager.translate(0.0F, 1.0F, 0.0F);
            
            float rot = te.getWorld().getWorldTime() % 360;
            
            rot = rot * 2;
            
            GlStateManager.rotate(rot, 0.0F, 1.0F, 0.0F);

            
            partialTicks = 0F;
            
            //サイズを設定する
            GlStateManager.scale(f, f, f);
            
            ((EntityItem) entity).hoverStart = 0;
            
            //Minecraft.getMinecraft().getRenderManager().setDebugBoundingBox(true);
            entity.setLocationAndAngles(posX, posY, posZ, 0.0F, 0.0F);
            Minecraft.getMinecraft().getRenderManager().doRenderEntity(
            		entity, 0.0D, 0.0D, 0.0D, 0.0F, partialTicks, false);

            */
            
            //EntityItem用のレンダー定義
            RenderEntityItem itemRenderer = new RenderEntityItem(
            		Minecraft.getMinecraft().getRenderManager(), 
            		Minecraft.getMinecraft().getRenderItem());
            //EntityItemの設定
            EntityItem customitem = new EntityItem(te.getWorld());
            customitem.setEntityItemStack(new ItemStack(Items.DIAMOND));
            
            //これを0に設定することで初期化できた
            customitem.hoverStart = 0;
            
            //ブロックの上に表示するために
            GlStateManager.translate(0.0F, 1.0F, 0.0F);
            
            float rot = te.getWorld().getWorldTime() % 360;
            
            rot = rot * 2;
            
            //GlStateManager.rotate(rot, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(0, 0.0F, 1.0F, 0.0F);

            
            
            itemRenderer.doRender(customitem, 0, 0, 0, 0, 0);
            
            //落下のやつでEntity化してるからそこで？
            //package net.minecraft.client.renderer.entity;
            //RenderFallingBlock
            //Minecraft.getMinecraft().getRenderManager()
            
            
        }
    }
}
