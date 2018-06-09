package mod.yuzukotomod.block;

import java.util.List;

import javax.annotation.Nullable;

import mod.yuzukotomod.tileentity.YKTileEntity;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * TileEntityテスト用のブロック
 * @author computer
 *
 */
public class YKTileEntityBlock extends BlockContainer {

	public YKTileEntityBlock() {
		
		//とりあえずチェストと同じ材質にしてる
		super(Material.WOOD);
		
		//何のひねりもない普通のブロックの見た目にする
		
	}

	/**
	 * 対応するTileEntityクラスを返却する
	 */
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new YKTileEntity();
	}
	
	
	/**
	 * BlockContainerで定義されているものを
	 * Blockクラスと同じになるように変更
	 */
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state)
    {
        //return EnumBlockRenderType.INVISIBLE;
        return EnumBlockRenderType.MODEL;
    }
	
	
	/**
	 * 透過ブロックの場合はオーバーライドが必要
	 */
	@Override
	@SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }
	
	/**
     * Used to determine ambient occlusion and culling when rebuilding chunks for render
     */
	@Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }
	@Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }
	
	@Override
	@Deprecated
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
		//ここで当たり判定を制御するけど
		//マルチブロック系でない限りへんなことやらないほうがよさそう
        //return FULL_BLOCK_AABB;
		return new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
    }
	
	@Override
	@Deprecated
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_)
    {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, state.getCollisionBoundingBox(worldIn, pos));
    }
	
	/**
	 * 不透明なブロックかどうか
	 * これを設定しないと隣接ブロックの接続部が透明になる
	 * デフォルトtrue
	 */
	/*
	@Override
	public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		//常にFalse(固体じゃない)と返す
		return false;
    }
    */
	
	
    /**
     * Called when the block is right clicked by a player.
     */
	//@Override
    public boolean onBlockActivated1(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
		//サーバーでだけ処理を行う
		if (!worldIn.isRemote) {
		
			ItemStack stack = playerIn.getHeldItemMainhand();
			//手持ちのアイテムを何か持っている場合
			if (stack != ItemStack.EMPTY) {
				
				//TileEntityを取得
				TileEntity te = worldIn.getTileEntity(pos);
				
				//TileEntityが一致するか確認
				if (te instanceof YKTileEntity) {
					//問題なければキャストしてアイテムIDを保存
					YKTileEntity ykte = (YKTileEntity)te;
					ykte.saveItemId = stack.getItem().getRegistryName().toString();
					//データ変更を通知
					ykte.markDirty();
					
					//データ同期用処理
					EntityPlayerMP entityPlayer = (EntityPlayerMP) playerIn;
					Packet<?> pkt = ykte.getUpdatePacket();
	                if (pkt != null) {
	                    entityPlayer.connection.sendPacket(pkt);
	                }
	                
				}
			} else {
				//TileEntityを取得
				TileEntity te = worldIn.getTileEntity(pos);
				
				if (te instanceof YKTileEntity) {
					YKTileEntity ykte = (YKTileEntity)te;
	
					//チャットウインドウへアイテムIDを表示する
					playerIn.sendMessage(
							new TextComponentString("Server Item_ID[" + ykte.saveItemId + "]"));
				}
			}
		} else {
			ItemStack stack = playerIn.getHeldItemMainhand();
			//手持ちのアイテムを何か持っている場合
			if (stack == ItemStack.EMPTY) {
				//TileEntityを取得
				TileEntity te = worldIn.getTileEntity(pos);
				
				if (te instanceof YKTileEntity) {
					YKTileEntity ykte = (YKTileEntity)te;
	
					//チャットウインドウへアイテムIDを表示する
					playerIn.sendMessage(
							new TextComponentString("Client Item_ID[" + ykte.saveItemId + "]"));
				}
			}
		}
        return true;
    }
	
	
	
    /**
     * Called when the block is right clicked by a player.
     */
	@Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
		
		/*
		if(worldIn.isRemote) {
			//TileEntityを取得
			TileEntity te = worldIn.getTileEntity(pos);
			
			if (te instanceof YKTileEntity) {
				YKTileEntity ykte = (YKTileEntity)te;
				playerIn.sendMessage(new TextComponentString("Client Item_ID["+ykte.saveItemId+"]"));
			}
			return true;
		}
		*/
		
		ItemStack stack = playerIn.getHeldItemMainhand();
		
		//モブエッグか確認する
		if (stack.getItem() instanceof ItemMonsterPlacer
				|| stack.getItem() == Items.SUGAR) {
			
			String mobId = "";
			if (stack.getItem() == Items.SUGAR) {
				mobId = "lmreengaged:LittleMaid";
			} else {
				//MobのIDを取得する
				ResourceLocation rc = ItemMonsterPlacer.getNamedIdFrom(stack);
				mobId = rc.toString();
			}
			
			TileEntity te = worldIn.getTileEntity(pos);
			
			if (te instanceof YKTileEntity) {
				
				YKTileEntity ykte = (YKTileEntity)te;
				
				//ykte.entityId = rc.toString();
				ykte.entityId = mobId;
				
				//ykte.entityId = "lmreengaged:LittleMaid";
				
				//同期処理？
				if (playerIn instanceof EntityPlayerMP) {
					//((EntityPlayerMP) playerIn).connection.sendPacket(ykte.getUpdatePacket());
					
					//自前で同期処理を行う
					//NBTのデータを保存するのはこれを使うっぽい
					ykte.markDirty();
					//ここから下が自前でServerとクライアントを保持
					//いまは個別にやってるけどマルチを考慮するなら
					//プレイヤーリストをワールドオブジェクトから取得して
					//そっちに対して同期用のパケットを投げたほうがいいかも？
					EntityPlayerMP entityPlayer = (EntityPlayerMP) playerIn;
					Packet<?> pkt = ykte.getUpdatePacket();
	                if (pkt != null)
	                {
	                    entityPlayer.connection.sendPacket(pkt);
	                }
				}
				
				//ykte.Chicken = EntityList.createEntityByIDFromName(rc, worldIn);
				
				return true;
				
			}
			//System.out.println(rc);
			
		}
		/*
		//それ以外の場合
		if (stack != ItemStack.EMPTY) {
			
			//TileEntityを取得
			TileEntity te = worldIn.getTileEntity(pos);
			
			if (te instanceof YKTileEntity) {
				
				YKTileEntity ykte = (YKTileEntity)te;
				
				ykte.saveItemId = stack.getItem().getRegistryName().toString();
				
				//同期処理？
				if (playerIn instanceof EntityPlayerMP) {
					//((EntityPlayerMP) playerIn).connection.sendPacket(ykte.getUpdatePacket());
					
					//自前で同期処理を行う
					EntityPlayerMP entityPlayer = (EntityPlayerMP) playerIn;
					Packet<?> pkt = ykte.getUpdatePacket();
	                if (pkt != null)
	                {
	                    entityPlayer.connection.sendPacket(pkt);
	                }
				}
				
                
				
				//同期？
				System.out.println(ykte.saveItemId);
				
			}
		} else {
			
			//TileEntityを取得
			TileEntity te = worldIn.getTileEntity(pos);
			
			if (te instanceof YKTileEntity) {
				YKTileEntity ykte = (YKTileEntity)te;
				
				playerIn.sendMessage(new TextComponentString("Item_ID["+ykte.saveItemId+"]"));
			}
			
		}
		*/
		
		//処理を無効化する
        return true;
    }
}
