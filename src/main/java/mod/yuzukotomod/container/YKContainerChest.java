package mod.yuzukotomod.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class YKContainerChest extends Container {

	
	/**
	 * コンストラクタ
	 */
	public YKContainerChest (IInventory iTeInv, IInventory playerInv) {
		
		//独自スロット(かまど部分)
		this.addSlotToContainer(new Slot(iTeInv, 0, 56, 17));
		this.addSlotToContainer(new Slot(iTeInv, 1, 56, 53));
		this.addSlotToContainer(new Slot(iTeInv, 2, 116, 35));
		
		//基準座標
		int xBasePos = 0;
		int yBasePos = 0;
		
        //playerインベントリ基準座標設定
		xBasePos = 8;
		yBasePos = 84;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
            	int slotIndex = j + i * 9 + 9; //index 9 からスタート
            	int xPos = xBasePos + j * 18;
            	int yPos = yBasePos + i * 18;
            	this.addSlotToContainer(new Slot(playerInv, slotIndex, xPos, yPos));
            }
        }
        //playerホットバー
        xBasePos = 8;
		yBasePos = 142;
		for (int i = 0; i < 9; i++) {
			int slotIndex = i; //index 0 からスタート
        	int xPos = xBasePos + i * 18;
        	int yPos = yBasePos;
            this.addSlotToContainer(new Slot(playerInv, slotIndex, xPos, yPos));
		}
		
		
	}
	
	
	/**
	 * GUIを利用できるかどうかを判断する
	 */
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}
	
	@Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        return ItemStack.EMPTY;
    }
	
}
