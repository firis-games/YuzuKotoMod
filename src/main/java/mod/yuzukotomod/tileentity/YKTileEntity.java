package mod.yuzukotomod.tileentity;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class YKTileEntity extends TileEntity {

	
	//public Entity Chicken = null;
	
	public String saveItemId = "";
	
	
	
	public String entityId = "";
	public String entityCache_entityId = "";
	public Entity entityCache = null;
	
	
	/**
	 * 保存してあるEntityIDからオブジェクトを生成する
	 * 基本的にクライアントからしかよばれないはず
	 * @param world
	 * @return
	 */
	public Entity getEntity() {
		
		//IDが変更されるかEntityオブジェクトがない
		if (!this.entityId.equals(this.entityCache_entityId) 
				|| this.entityCache == null) {
			
			World world = this.getWorld();
			this.entityCache_entityId = this.entityId;
			
			//Entityを生成
			//ResourceLocation resourcelocation = new ResourceLocation("minecraft:wolf");
			ResourceLocation resourcelocation = new ResourceLocation(this.entityCache_entityId);
			entityCache = EntityList.createEntityByIDFromName(resourcelocation, world);
		}
		return entityCache;
	}
	
	
	/**
	 * コンストラクタ
	 */
	public YKTileEntity() {
		
		super();
		
		//Chicken = new EntityEnderman(worldIn);
		/*
		Chicken = new EntityItem(worldIn);
		((EntityItem) Chicken).setEntityItemStack(new ItemStack(Items.DIAMOND));
		*/
		
		/*
		World worldIn = this.getWorld();
		
		Chicken = new EntityRabbit(worldIn);
		
		//EntityからNBTを取得してる
		//EntityList.createEntityFromNBT(Chicken.getEntityData(), worldIn);
		
		
		ResourceLocation resourcelocation = new ResourceLocation("minecraft:wolf");
		Chicken = EntityList.createEntityByIDFromName(resourcelocation, worldIn);
		
		EntityWolf Chicken2 = (EntityWolf)Chicken;
		Chicken2.setSitting(true);
		//Chicken = Chicken2;
		
		
		Chicken = new EntityItem(worldIn);
		((EntityItem) Chicken).setEntityItemStack(new ItemStack(Items.DIAMOND));
		*/
		
		
		
	}
	
    /**
     * Return an {@link AxisAlignedBB} that controls the visible scope of a {@link TileEntitySpecialRenderer} associated with this {@link TileEntity}
     * Defaults to the collision bounding box {@link Block#getCollisionBoundingBoxFromPool(World, int, int, int)} associated with the block
     * at this location.
     *
     * @return an appropriately size {@link AxisAlignedBB} for the {@link TileEntity}
     */
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        
        //これがチェストのBOX定義
        //Renderの範囲をきめるやつっぽい
        //return new AxisAlignedBB(pos.add(-1, 0, -1), pos.add(2, 2, 2));
        return new AxisAlignedBB(pos.add(-1, 0, -1), pos.add(10, 10, 10));

	}
	
	
	/**
	 * NBTを読み込みクラスへ反映する処理
	 */
	//@Override
	public void readFromNBT1(NBTTagCompound compound)
    {
		//親クラスの書き込み読み込み処理
		super.readFromNBT(compound);
		
		//NBTからアイテムIDを取得して変数へセットする
		this.saveItemId = compound.getString("itemId");
    }
	
	/**
	 * クラスの情報をNBTへ反映する処理
	 */
	//@Override
	public NBTTagCompound writeToNBT1(NBTTagCompound compound)
    {
		//親クラスの書き込み処理
        compound = super.writeToNBT(compound);
        
        //アイテムIDを設定する
        compound.setString("itemId", this.saveItemId);
        
        return compound;
    }
	
	
	
	//**********
	
	
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
		
		//NBTからアイテムIDを取得する
		this.entityId = compound.getString("entityId");
		
		System.out.println("readFromNBT|" + this.entityId);
    }
	
	
	/**
	 * クラスの情報をNBTへ反映する処理
	 */
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
		
        compound = super.writeToNBT(compound);
        
        //アイテムIDを設定する
        compound.setString("entityId", this.entityId);
        
        System.out.println("writeToNBT|" + this.entityId);

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
