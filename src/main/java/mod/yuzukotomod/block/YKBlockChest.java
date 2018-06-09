package mod.yuzukotomod.block;

import mod.yuzukotomod.YuzuKotoMod;
import mod.yuzukotomod.gui.YKGuiHandler;
import mod.yuzukotomod.tileentity.YKTileEntityChest;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class YKBlockChest extends BlockContainer {

	public YKBlockChest() {
		//とりあえずチェストと同じ材質にしてる
		super(Material.WOOD);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		// TODO 自動生成されたメソッド・スタブ
		return new YKTileEntityChest();
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
     * Called when the block is right clicked by a player.
     */
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
    	//右クリックでGUIを開く
		playerIn.openGui(YuzuKotoMod.INSTANCE, YKGuiHandler.YKCHEST_GUI, 
				worldIn, pos.getX(), pos.getY(), pos.getZ());
    	return true;
    }

    
}
