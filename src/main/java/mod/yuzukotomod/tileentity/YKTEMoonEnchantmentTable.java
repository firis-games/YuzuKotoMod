package mod.yuzukotomod.tileentity;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import mod.yuzukotomod.YuzuKotoMod.YuzuKotoItems;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class YKTEMoonEnchantmentTable extends TileEntity implements ITickable, IInventory {

	
	public YKTEMoonEnchantmentTable() {
		
		//レシピの登録
		//紫水晶
		moonRecipesList.add(new MoonRecipes(
				new ItemStack(YuzuKotoItems.PURPLE_DIAMOND)
				,new ItemStack(Items.DIAMOND)
				,new ItemStack(Items.DYE, 1, 5)));
		
		//ノッチリンゴ
		moonRecipesList.add(new MoonRecipes(
				new ItemStack(Items.GOLDEN_APPLE, 1, 1)
				,new ItemStack(Items.GOLDEN_APPLE)
				,new ItemStack(Blocks.GOLD_BLOCK)
				,new ItemStack(Blocks.GOLD_BLOCK)
				,new ItemStack(Blocks.GOLD_BLOCK)
				,new ItemStack(Blocks.GOLD_BLOCK)));
		
		//ことのは
		moonRecipesList.add(new MoonRecipes(
				new ItemStack(YuzuKotoItems.PURPLEDIAMOND_PICKAXE)
				,new ItemStack(Items.DIAMOND_PICKAXE)
				,new ItemStack(Blocks.REDSTONE_BLOCK)
				,new ItemStack(Blocks.LAPIS_BLOCK)
				,new ItemStack(YuzuKotoItems.PURPLE_DIAMOND)));
		
		//きずな
		moonRecipesList.add(new MoonRecipes(
				new ItemStack(YuzuKotoItems.PURPLEDIAMOND_HOE)
				,new ItemStack(Items.DIAMOND_HOE)
				,new ItemStack(Blocks.PUMPKIN)
				,new ItemStack(Items.GOLDEN_APPLE, 1, 0)
				,new ItemStack(YuzuKotoItems.PURPLE_DIAMOND)));
		
		
		//ダイヤ一式をエンチャントできるように
		
		//エンチャントテスト
		MoonRecipes enc = new MoonRecipes(
				new ItemStack(Items.DIAMOND_SWORD)
				,new ItemStack(Items.DIAMOND_SWORD)
				,new ItemStack(Items.DYE, 1, 4)
				,new ItemStack(Items.GOLDEN_APPLE, 1, 1));
		enc.enchantment = true;
		moonRecipesList.add(enc);
		/*
		enc = new MoonRecipes(
				new ItemStack(Items.DIAMOND_HOE)
				,new ItemStack(Items.DIAMOND_HOE)
				,new ItemStack(Items.DYE, 1, 4)
				,new ItemStack(Items.GOLDEN_APPLE, 1, 1));
		enc.enchantment = true;
		moonRecipesList.add(enc);
		*/
		enc = new MoonRecipes(
				new ItemStack(Items.DIAMOND_PICKAXE)
				,new ItemStack(Items.DIAMOND_PICKAXE)
				,new ItemStack(Items.DYE, 1, 4)
				,new ItemStack(Items.GOLDEN_APPLE, 1, 1));
		enc.enchantment = true;
		moonRecipesList.add(enc);
		
		enc = new MoonRecipes(
				new ItemStack(Items.DIAMOND_AXE)
				,new ItemStack(Items.DIAMOND_AXE)
				,new ItemStack(Items.DYE, 1, 4)
				,new ItemStack(Items.GOLDEN_APPLE, 1, 1));
		enc.enchantment = true;
		moonRecipesList.add(enc);
		
		enc = new MoonRecipes(
				new ItemStack(Items.DIAMOND_SHOVEL)
				,new ItemStack(Items.DIAMOND_SHOVEL)
				,new ItemStack(Items.DYE, 1, 4)
				,new ItemStack(Items.GOLDEN_APPLE, 1, 1));
		enc.enchantment = true;
		moonRecipesList.add(enc);
		
		enc = new MoonRecipes(
				new ItemStack(Items.DIAMOND_HELMET)
				,new ItemStack(Items.DIAMOND_HELMET)
				,new ItemStack(Items.DYE, 1, 4)
				,new ItemStack(Items.GOLDEN_APPLE, 1, 1));
		enc.enchantment = true;
		moonRecipesList.add(enc);
		
		enc = new MoonRecipes(
				new ItemStack(Items.DIAMOND_CHESTPLATE)
				,new ItemStack(Items.DIAMOND_CHESTPLATE)
				,new ItemStack(Items.DYE, 1, 4)
				,new ItemStack(Items.GOLDEN_APPLE, 1, 1));
		enc.enchantment = true;
		moonRecipesList.add(enc);
		
		enc = new MoonRecipes(
				new ItemStack(Items.DIAMOND_LEGGINGS)
				,new ItemStack(Items.DIAMOND_LEGGINGS)
				,new ItemStack(Items.DYE, 1, 4)
				,new ItemStack(Items.GOLDEN_APPLE, 1, 1));
		enc.enchantment = true;
		moonRecipesList.add(enc);
		
		enc = new MoonRecipes(
				new ItemStack(Items.DIAMOND_BOOTS)
				,new ItemStack(Items.DIAMOND_BOOTS)
				,new ItemStack(Items.DYE, 1, 4)
				,new ItemStack(Items.GOLDEN_APPLE, 1, 1));
		enc.enchantment = true;
		moonRecipesList.add(enc);

		enc = new MoonRecipes(
				new ItemStack(YuzuKotoItems.PURPLEDIAMOND_PICKAXE)
				,new ItemStack(YuzuKotoItems.PURPLEDIAMOND_PICKAXE)
				,new ItemStack(Items.DYE, 1, 4)
				,new ItemStack(Items.GOLDEN_APPLE, 1, 1));
		enc.enchantment = true;
		moonRecipesList.add(enc);
		/*
		enc = new MoonRecipes(
				new ItemStack(YuzuKotoItems.PURPLEDIAMOND_HOE)
				,new ItemStack(YuzuKotoItems.PURPLEDIAMOND_HOE)
				,new ItemStack(Items.DYE, 1, 4)
				,new ItemStack(Items.GOLDEN_APPLE, 1, 1));
		enc.enchantment = true;
		moonRecipesList.add(enc);
		*/
		
		
	}
	
	public int moonTimer = 0;
	
	/** チック */
	public int maxMoonTimer = 200;
	
	/**
	 * ITickable
	 */
	@Override
	public void update() {

		//クライアントでは何もしない
		/*
		if (world.isRemote) {
			return;
		}
		*/
		//System.out.println("最初");
		if (!isWorldNight()) {
			//タイマーをリセット
			//System.out.println("よる");
			moonTimer = 0;
			return;
		}
		
		if (!isRecipes()) {
			//タイマーをリセット
			//System.out.println("れしぴ");
			moonTimer = 0;
			return;
		}
		
		if (moonTimer == 0) {
			this.playerServerSendPacket();
			//System.out.println("確認用Start");
		}
		
		moonTimer += 1;
		if (moonTimer == 100) {
			//System.out.println("確認用100");
			return;
		}
		if (moonTimer < maxMoonTimer) {
			return;
		}

		moonTimer = 0;

		//サーバー側しかやらないようにする
		if (this.getWorld().isRemote) {
			//System.out.println("確認用");
			return;
		}

		//System.out.println("確認用2");
		//アイテムを設定する
		MoonRecipes moonRecipe = getRecipesResult();
		ItemStack stack = moonRecipe.getResultItemStack();
		
		if (moonRecipe.enchantment) {
			//エンチャントの場合
			//結果のスタックは無視
			ItemStack encItemStack = this.getStackInSlot(0);
			int encLevel = 35;
			List<EnchantmentData> list = EnchantmentHelper.buildEnchantmentList(this.getWorld().rand, 
					encItemStack, encLevel, true);
			
			for (EnchantmentData enchantmentdata : list) {
				encItemStack.addEnchantment(enchantmentdata.enchantmentobj, enchantmentdata.enchantmentLevel);
			}
			
			stack = encItemStack;
		}
		
		System.out.println(stack);
		this.setInventorySlotContents(0, stack);
		
		this.decrStackSize(1, 1);
		this.decrStackSize(2, 1);
		this.decrStackSize(3, 1);
		this.decrStackSize(4, 1);
		/*
		if (!this.getStackInSlot(1).isEmpty()) {
			
		}
		if (!this.getStackInSlot(2).isEmpty()) {
			
		}
		if (!this.getStackInSlot(3).isEmpty()) {
			this.decrStackSize(3, 1);
		}
		if (!this.getStackInSlot(4).isEmpty()) {
			this.decrStackSize(4, 1);
		}
		*/
		
		//同期をとる
		this.playerServerSendPacket();
	}
	
	@Override
	public void markDirty()
    {
		super.markDirty();
		
    }
	
	public void playerServerSendPacket() {
		//player listを取得
		World world = this.getWorld();
		
		//サーバの場合のみ
		if (!world.isRemote) {
			
			this.markDirty();
			
			List<EntityPlayer> list = world.playerEntities;
			
			Packet<?> pkt = this.getUpdatePacket();
			if (pkt != null) {
				for (EntityPlayer player : list) {
					EntityPlayerMP mpPlayer = (EntityPlayerMP) player;
					mpPlayer.connection.sendPacket(pkt);
				}
			}
		}
	}
		
	public ArrayList<MoonRecipes> moonRecipesList = new ArrayList<MoonRecipes>();
	/**
	 * インナークラス
	 * @author computer
	 *
	 */
	public class MoonRecipes {
		
		public ItemStack resultItem = null;
		public ItemStack mainItem = null;
		public ArrayList<ItemStack> subItems = new ArrayList<ItemStack>();
		public boolean enchantment = false;
		
		public MoonRecipes(ItemStack result, ItemStack main) {
			this.setMoonRecipes(result, main, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY);
		}
		public MoonRecipes(ItemStack result, ItemStack main, ItemStack sub1) {
			this.setMoonRecipes(result, main, sub1, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY);
		}
		public MoonRecipes(ItemStack result, ItemStack main, ItemStack sub1, ItemStack sub2) {
			this.setMoonRecipes(result, main, sub1, sub2, ItemStack.EMPTY, ItemStack.EMPTY);
		}
		public MoonRecipes(ItemStack result, ItemStack main, ItemStack sub1, ItemStack sub2, ItemStack sub3) {
			this.setMoonRecipes(result, main, sub1, sub2, sub3, ItemStack.EMPTY);
		}
		public MoonRecipes(ItemStack result, ItemStack main, ItemStack sub1, ItemStack sub2, ItemStack sub3, ItemStack sub4) {
			this.setMoonRecipes(result, main, sub1, sub2, sub3, sub4);
		}
		
		public void setMoonRecipes(ItemStack result, ItemStack main, ItemStack sub1, ItemStack sub2, ItemStack sub3, ItemStack sub4) {
			this.clear();
			mainItem = main;
			if (!sub1.isEmpty()) {
				subItems.add(sub1);
			}
			if (!sub2.isEmpty()) {
				subItems.add(sub2);
			}
			if (!sub3.isEmpty()) {
				subItems.add(sub3);
			}
			if (!sub4.isEmpty()) {
				subItems.add(sub4);
			}
			resultItem = result;
		}
		
		/**
		 * 初期化
		 */
		public void clear() {
			resultItem = ItemStack.EMPTY;
			mainItem = ItemStack.EMPTY;
			subItems = new ArrayList<ItemStack>();
		}
		
		/**
		 * 返却値に設定しているものを取得する
		 * @return
		 */
		public ItemStack getResultItemStack() {
			return resultItem.copy();
		}
		
		/**
		 * IInventoryからチェックする
		 * @param inventory
		 * @return
		 */
		public ItemStack getResult(IInventory inventory) {
			
			ItemStack retStack = ItemStack.EMPTY;
			
			//メインが一致するか否か
			if (!ItemStack.areItemsEqual(mainItem, inventory.getStackInSlot(0))) {
				return retStack;
			}
			
			//エンチャント用の特殊判定
			if (this.enchantment) {
				//エンチャントがついてる場合はエラーとする
				if (EnchantmentHelper.getEnchantments(inventory.getStackInSlot(0)).size() > 0) {
					return retStack;
				}
			}
			
			//サブアイテムを比較する
			ArrayList<ItemStack> inventoryList = new ArrayList<ItemStack>();
			for (int i = 1 ; i < 5; i++) {
				if (!inventory.getStackInSlot(i).isEmpty()) {
					inventoryList.add(inventory.getStackInSlot(i).copy());
				}
			}
			
			//レシピ比較
			if (inventoryList.size() != subItems.size()) {
				return retStack;
			}
			
			boolean flg1 = true;
			for (ItemStack stack1 : subItems) {
			
				boolean flg2 = false;
				for (int i = 0; i < inventoryList.size(); i++) {
					if (ItemStack.areItemsEqual(stack1, inventoryList.get(i))) {
						inventoryList.remove(i);
						flg2 = true;
						break;
					}
				}
				if (!flg2) {
					flg1 = false;
					break;
				}
			}
			
			if (!flg1) {
				return retStack;
			}
			
			return resultItem.copy();
			
		}
		
	}
	
	/**
	 * 
	 * @return
	 */
	protected boolean isRecipes() {
		//今回は手動で判断
		if (getRecipesResult() == null) {
			return false;
		}
		return true;
	}
	
	/**
	 * 
	 * @return
	 */
	protected MoonRecipes getRecipesResult() {
		//今回は手動で判断
		if (this.getStackInSlot(0).isEmpty()) {
			return null;
		}
		for (MoonRecipes recipes : moonRecipesList) {
			
			ItemStack stack = recipes.getResult(this);
			
			if (!stack.isEmpty()) {
				return recipes;
			}
		}		
		return null;
	}
	
	/**
	 * 夜かどうかを判断する
	 * @return
	 */
	public boolean isWorldNight() {
		
		
		/*
		//ワールドタイムがワールド内部の時間
		//totalタイムがプレイ時間？起動時間とかそのへんっぽい
		System.out.println("wt:" + world.getWorldTime());
		System.out.println("at:" + world.getTotalWorldTime());
		
		//月の形はこれで拾えるみたい
		float moon = world.getCurrentMoonPhaseFactorBody();
		System.out.println("at:" + moon);
		*/
		
		
		//天気 ：雨
		if (this.getWorld().getWorldInfo().isRaining()) {
			return false;
		}
		//天気 ：雷？
		if (this.getWorld().getWorldInfo().isThundering()) {
			return false;
		}
		
		//空がみえるかどうか
		if(!this.getWorld().canSeeSky(this.getPos())) {
			return false;
		}
		
		//22:00 から 04:00までを夜中と判断する
		//06:00が0 07:00が1000
		
		//22:00が16000 04:00が22000の範囲の間は深夜とする
		
		//isDaytimeが昼夜を判定するみたい
		
		if (16000 <= this.getWorld().getWorldTime() 
				&& this.getWorld().getWorldTime() <= 22000) {
			return true;
		}
		
		return false;
		
	}
	
	//************************************************************
	
	/**
	 * IInventoryの保存用領域
	 */
	protected NonNullList<ItemStack> inventorySlotItemStack = 
			NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);

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

	@Override
	public int getSizeInventory() {
		// TODO 自動生成されたメソッド・スタブ
		return 5;
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
//		return 64;
		return 1;
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
		this.markDirty();
	}

	/**
	 * Inventoryを閉じる際の処理
	 */
	@Override
	public void closeInventory(EntityPlayer player) {
		this.markDirty();
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
        
        this.maxMoonTimer = compound.getInteger("maxMoonTimer");
        this.moonTimer = compound.getInteger("moonTimer");
    }
	
	
	/**
	 * クラスの情報をNBTへ反映する処理
	 */
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        compound = super.writeToNBT(compound);
        ItemStackHelper.saveAllItems(compound, this.inventorySlotItemStack);
        
        compound.setInteger("maxMoonTimer", this.maxMoonTimer);
        compound.setInteger("moonTimer", this.moonTimer);
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
