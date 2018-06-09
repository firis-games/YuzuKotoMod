package mod.yuzukotomod.gui;

import mod.yuzukotomod.container.YKContainerChest;
import mod.yuzukotomod.container.YKContainerMoonEncantmentTable;
import mod.yuzukotomod.container.YKGuiContainerChest;
import mod.yuzukotomod.container.YKGuiContainerMoonEncantmentTable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class YKGuiHandler implements IGuiHandler {

	/**
	 * GUIのID YKチェスト用
	 */
	public static int YKCHEST_GUI = 1;
	
	
	/**
	 * GUIのID YKチェスト用
	 */
	public static int YKMOON_GUI = 2;
	
	/**
	 * サーバ側での処理
	 */
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		//IDで判断する
		if (ID == YKGuiHandler.YKCHEST_GUI) {
			
			//TileEntityを取得する
			IInventory inv = (IInventory) world.getTileEntity(new BlockPos(x, y ,z));
			
			return new YKContainerChest(inv, player.inventory);
		} else if (ID == YKGuiHandler.YKMOON_GUI) {
			
			//TileEntityを取得する
			IInventory inv = (IInventory) world.getTileEntity(new BlockPos(x, y ,z));
			
			return new YKContainerMoonEncantmentTable(inv, player.inventory);
		}
		return null;
	}

	/**
	 * クライアント側での処理
	 */
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		//IDで判断する
		if (ID == YKGuiHandler.YKCHEST_GUI) {
			//TileEntityを取得する
			IInventory inv = (IInventory) world.getTileEntity(new BlockPos(x, y ,z));

			return new YKGuiContainerChest(new YKContainerChest(inv, player.inventory));
		} else if (ID == YKGuiHandler.YKMOON_GUI) {
			
			//TileEntityを取得する
			IInventory inv = (IInventory) world.getTileEntity(new BlockPos(x, y ,z));
			
			return new YKGuiContainerMoonEncantmentTable(inv, player.inventory);
		}
		return null;
	}
}

