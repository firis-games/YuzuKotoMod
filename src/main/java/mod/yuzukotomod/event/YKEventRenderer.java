package mod.yuzukotomod.event;

import mod.yuzukotomod.entity.YKMineCart;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


/**
 * 寝台トロッコ用の寝る処理
 * 呼び出ししていない
 * @author computer
 *
 */
public class YKEventRenderer {
	
	
	Entity ridingEntity = null;
	
	/**
	 * クライアントのイベントはこの定義がいるっぽい？
	 * @param event
	 */
	@SideOnly(Side.CLIENT)
	//@SubscribeEvent
	public void YKEventRendererRenderPlayerEventPre(net.minecraftforge.client.event.RenderPlayerEvent.Pre event) {
		
		//System.out.println("RenderPlayerEvent");
		
		if (event.getEntityPlayer().isRiding() && event.getEntityPlayer().getRidingEntity() instanceof YKMineCart) {
			ReflectionHelper.setPrivateValue(EntityPlayer.class, event.getEntityPlayer(), true, "sleeping", "field_71083_bS");
			
			/*
			ridingEntity = ReflectionHelper.getPrivateValue(EntityPlayer.class, event.getEntityPlayer(), "ridingEntity", "ridingEntity1");
			ReflectionHelper.setPrivateValue(EntityPlayer.class, event.getEntityPlayer(), null, "ridingEntity", "ridingEntity1");
			*/
			ridingEntity = event.getEntityPlayer().getRidingEntity();
			ReflectionHelper.setPrivateValue(Entity.class, event.getEntityPlayer(), null, "ridingEntity", "field_184239_as");
			
			event.getEntityPlayer().renderOffsetY = 0.65F;
			event.getEntityPlayer().renderOffsetX = 0.95F;

			
			this.prevRotationPitch = event.getEntityPlayer().prevRotationPitch;
			this.rotationPitch = event.getEntityPlayer().rotationPitch;
			this.prevRotationYawHead = event.getEntityPlayer().prevRotationYawHead;
			this.rotationYawHead = event.getEntityPlayer().rotationYawHead;
			this.prevRenderYawOffset = event.getEntityPlayer().prevRenderYawOffset;
			this.renderYawOffset = event.getEntityPlayer().renderYawOffset;
			
			event.getEntityPlayer().prevRotationPitch = 0F;
			event.getEntityPlayer().rotationPitch = 0F;
			event.getEntityPlayer().prevRotationYawHead = 0F;
			event.getEntityPlayer().rotationYawHead = 0F;

			event.getEntityPlayer().prevRenderYawOffset = 0F;
			event.getEntityPlayer().renderYawOffset = 0F;
			
			/*
			event.getEntityPlayer().prevRotationPitch = event.getEntityPlayer().rotationPitch;
			event.getEntityPlayer().prevRotationYawHead = event.getEntityPlayer().rotationYawHead;
			event.getEntityPlayer().prevRenderYawOffset = event.getEntityPlayer().renderYawOffset;
			*/
			
			
			//event.getEntityPlayer().prevRotationYawHead = 0F;
			
		}
        
	}
	
	protected float prevRotationPitch = 0;
	protected float rotationPitch = 0;
	protected float prevRotationYawHead = 0;
	protected float rotationYawHead = 0;
	protected float prevRenderYawOffset = 0;
	protected float renderYawOffset = 0;
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void YKEventRendererRenderPlayerEventPost(net.minecraftforge.client.event.RenderPlayerEvent.Post event) {
		if (ridingEntity != null) {
			ReflectionHelper.setPrivateValue(EntityPlayer.class, event.getEntityPlayer(), false, "sleeping", "field_71083_bS");
		
			ReflectionHelper.setPrivateValue(Entity.class, event.getEntityPlayer(), this.ridingEntity, "ridingEntity", "field_184239_as");
			
			
			
			event.getEntityPlayer().prevRotationPitch = this.prevRotationPitch;
			event.getEntityPlayer().rotationPitch = this.rotationPitch;
			event.getEntityPlayer().prevRotationYawHead = this.prevRotationYawHead;
			event.getEntityPlayer().rotationYawHead = this.rotationYawHead;
			event.getEntityPlayer().prevRenderYawOffset = this.prevRenderYawOffset;
			event.getEntityPlayer().renderYawOffset = this.renderYawOffset;
			
		}
		ridingEntity = null;
	}

}
