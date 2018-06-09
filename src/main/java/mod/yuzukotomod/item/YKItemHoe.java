package mod.yuzukotomod.item;

import java.util.ArrayList;

import mod.yuzukotomod.util.BlockPosUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class YKItemHoe extends ItemHoe {

	public YKItemHoe(ToolMaterial material) {
		super(material);
	}
		
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
		ItemStack itemstack = player.getHeldItem(hand);
		
		//メインハンドの場合のみ特殊処理を行う
		if (hand == EnumHand.MAIN_HAND && player.isSneaking()) {
			
			//net.minecraftforge.common.IPlantable
			
			ItemStack offhand = player.getHeldItem(EnumHand.OFF_HAND);
			
			//オフハンドが植物系かを確認する
			if (offhand.getItem() instanceof net.minecraftforge.common.IPlantable
					|| (offhand.getItem() instanceof ItemBlock 
							&& ((ItemBlock)offhand.getItem()).getBlock() instanceof net.minecraftforge.common.IPlantable)) {
				
				//植え込みチェック
				EnumActionResult offRet = offhand.onItemUse(player, worldIn, pos, EnumHand.OFF_HAND, facing, hitX, hitY, hitZ);
				//itemstack.setItemDamage(itemstack.getItemDamage() + 1);
				itemstack.damageItem(1, player);
				
				if (itemstack.isEmpty()) {
					//空になったら処理終了
					return EnumActionResult.SUCCESS;
				}
				
				System.out.print(player.getFoodStats().getFoodLevel());
				
				//1以下の場合は何もしない
				if (player.getFoodStats().getFoodLevel() <= 1) {
					if (worldIn.isRemote) {
						//クライアントだけメッセージをだす
						TextComponentTranslation langtext = 
								new TextComponentTranslation("tool.kjizuna.message", new Object[0]);
						
						player.sendMessage(langtext);
					}
					return EnumActionResult.SUCCESS;
				}
				
				//おなかぺこりん
				player.getFoodStats().setFoodLevel(Math.max(0, player.getFoodStats().getFoodLevel() - 1));
				
				//成功したときだけ連鎖開始
				if (offRet == EnumActionResult.SUCCESS ) {
					/*
					ArrayList<BlockPos> posList = BlockPosUtil.getBlockAreaList(pos, 32);
					int areaCount = 0;
					for (BlockPos harvestPos : posList) {
						offRet = offhand.onItemUse(player, worldIn, harvestPos, EnumHand.OFF_HAND, facing, hitX, hitY, hitZ);
						
						if (offRet == EnumActionResult.SUCCESS) {
							areaCount = 0;
							//成功したときだけダメージを与える
							//itemstack.setItemDamage(itemstack.getItemDamage() + 1);
							itemstack.damageItem(1, player);
							
							if (itemstack.isEmpty()) {
								//空になったら処理終了
								return EnumActionResult.SUCCESS;
							}
						} else {
							areaCount += 1;
						}
						if (areaCount >= 64) {
							//16個分なにもなかったらおわり
							return EnumActionResult.SUCCESS;
						}
					}
					
					*/
					//外周ごとにチェックを行うようにする
					ArrayList<ArrayList<BlockPos>> posArrayList = BlockPosUtil.getBlockAreaArrayList(pos, 32);
					int areaCountFlg = 0;
					for (ArrayList<BlockPos> posList : posArrayList) {
						boolean flg = false;
						for (BlockPos harvestPos : posList) {
							offRet = offhand.onItemUse(player, worldIn, harvestPos, EnumHand.OFF_HAND, facing, hitX, hitY, hitZ);
							
							if (offRet == EnumActionResult.SUCCESS) {
								
								flg = true;
								//成功したときだけダメージを与える
								//itemstack.setItemDamage(itemstack.getItemDamage() + 1);
								itemstack.damageItem(1, player);
								
								if (itemstack.isEmpty()) {
									//空になったら処理終了
									return EnumActionResult.SUCCESS;
								}
							}
						}
						//一度もない
						if (!flg) {
							areaCountFlg += 1;
						} else {
							areaCountFlg = 0;							
						}
						if (areaCountFlg >= 2) {
							//指定回数分存在してないからここで処理を終わる
							return EnumActionResult.SUCCESS;							
						}
					}
					
					return EnumActionResult.SUCCESS;
				}
				
				
			}
			
			
		}
		
		
		return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
		
		/*
		player.swingArm(hand);
		EnumActionResult ret = super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
		
		if (EnumActionResult.SUCCESS.equals(ret)) {
			
			player.swingArm(hand);
			
		}
		
		return null;
		*/
		

		
		////return EnumActionResult.SUCCESS;
		
    }
	

	
}
