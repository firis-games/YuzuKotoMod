package mod.yuzukotomod.tileentity;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;

public class YKTileEntityChest extends TileEntity implements IInventory {
	
	/**
	 * IInventoryの保存用領域
	 */
	protected NonNullList<ItemStack> inventorySlotItemStack = 
			NonNullList.<ItemStack>withSize(3, ItemStack.EMPTY);
	
	@Override
	public String getName() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public boolean hasCustomName() {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	/**
	 * Inventoryの個数指定
	 */
	@Override
	public int getSizeInventory() {
		return 3;
	}

	/**
	 * Inventoryが空かどうか判定する
	 */
	@Override
	public boolean isEmpty() {
        for (ItemStack itemstack : this.inventorySlotItemStack)
        {
            if (!itemstack.isEmpty())
            {
                return false;
            }
        }
        return true;
	}

	/**
	 * 指定されたIndexのItemStackを返却する
	 */
	@Override
	public ItemStack getStackInSlot(int index) {
		return inventorySlotItemStack.get(index);
	}

	/**
	 * 指定したスロットから指定数分のアイテムを取得する
	 */
	@Override
	public ItemStack decrStackSize(int index, int count) {
		ItemStack itemstack = ItemStackHelper.getAndSplit(inventorySlotItemStack, index, count);

        if (!itemstack.isEmpty())
        {
            this.markDirty();
        }

        return itemstack;
	}

	/**
	 * 指定したスロットからItemStackを取得する
	 * 取得先のスロットは空になる
	 */
	@Override
	public ItemStack removeStackFromSlot(int index) {
		return ItemStackHelper.getAndRemove(inventorySlotItemStack, index);
	}

	/**
	 * 指定したスロットにアイテムスタックをセットする
	 */
	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		
		inventorySlotItemStack.set(index, stack);
        if (stack.getCount() > this.getInventoryStackLimit())
        {
            stack.setCount(this.getInventoryStackLimit());
        }
        this.markDirty();
	}

	/**
	 * InventoryのItemStackの上限数
	 */
	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	/**
	 * PlayerがTileEntityを使用できるかどうか
	 */
	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		return this.world.getTileEntity(this.pos) != this 
				? false : player.getDistanceSq((double)this.pos.getX() + 0.5D,
						(double)this.pos.getY() + 0.5D, 
						(double)this.pos.getZ() + 0.5D) <= 64.0D;
	}

	/**
	 * Inventoryを開く際の処理
	 */
	@Override
	public void openInventory(EntityPlayer player) {
	}

	/**
	 * Inventoryを閉じる際の処理
	 */
	@Override
	public void closeInventory(EntityPlayer player) {
	}

	/**
	 * 対象スロットの許可不許可チェック
	 */
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return true;
	}

	@Override
	public int getField(int id) {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public int getFieldCount() {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

	@Override
	public void clear() {
		inventorySlotItemStack.clear();
	}
	
	
	/**
	 * **************************************************
	 * NBT関連
	 * 基本的にreadFromNBT/writeToNBTでデータのやり取りを行う
	 * ただし、同期させるには手動で同期を行うかICrafting等を利用する必要あり
	 * 手動同期の場合はWorldのworldObj.markBlockForUpdate(this.pos);
	 * で行うことができる
	 * パケット通信についてはバニラデフォルトの機能を利用
	 * カスタムパケットはまた今度
	 * **************************************************
	 */
	
	/**
	 * NBTを読み込みクラスへ反映する処理
	 */
	@Override
	public void readFromNBT(NBTTagCompound compound)
    {
		super.readFromNBT(compound);
        this.inventorySlotItemStack = 
        		NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, this.inventorySlotItemStack);
    }
	
	
	/**
	 * クラスの情報をNBTへ反映する処理
	 */
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        compound = super.writeToNBT(compound);
        ItemStackHelper.saveAllItems(compound, this.inventorySlotItemStack);
        return compound;
    }
	
	/**
	 * ログインのタイミングの同期に使われてる？
	 * チャンクデータの同期のタイミングでつかわれてるっぽい
	 * SPacketChunkData
	 */
	@Override
    public NBTTagCompound getUpdateTag()
    {
		System.out.println("getUpdateTag");
		
        //return super.getUpdateTag();
		//NBTTagにTileEntityの情報を書き込む
		return this.writeToNBT(new NBTTagCompound());
    }
	
    /**
     * Called when the chunk's TE update tag, gotten from {@link #getUpdateTag()}, is received on the client.
     * <p>
     * Used to handle this tag in a special way. By default this simply calls {@link #readFromNBT(NBTTagCompound)}.
     *
     * @param tag The {@link NBTTagCompound} sent from {@link #getUpdateTag()}
     * 
     * チャンクのほう
     */
	@Override
    public void handleUpdateTag(NBTTagCompound tag)
    {
		System.out.println("handleUpdateTag");
        this.readFromNBT(tag);
    }
	
	/**
	 * 旧getDescriptionPacketっぽい
	 */
	@Override
    @Nullable
    public SPacketUpdateTileEntity getUpdatePacket()
    {
		System.out.println("getUpdatePacket");
        //return super.getUpdatePacket();
		return new SPacketUpdateTileEntity(this.pos, 0, this.writeToNBT(new NBTTagCompound()));
    }

    /**
     * Called when you receive a TileEntityData packet for the location this
     * TileEntity is currently in. On the client, the NetworkManager will always
     * be the remote server. On the server, it will be whomever is responsible for
     * sending the packet.
     *
     * @param net The NetworkManager the packet originated from
     * @param pkt The data packet
     */
	/**
	 * クライアントサイドでgetDescriptionPacketの処理のあとに呼び出されるとおもわれる
	 * NBTからクラス情報への反映等を行う?
	 * 
	 * 
	 * コメントによるとパケットを受け取ったタイミングで処理が行われるらしい
	 */
	@Override
    public void onDataPacket(net.minecraft.network.NetworkManager net, net.minecraft.network.play.server.SPacketUpdateTileEntity pkt)
    {
		System.out.println("onDataPacket");
		
		this.readFromNBT(pkt.getNbtCompound());
    }

}
