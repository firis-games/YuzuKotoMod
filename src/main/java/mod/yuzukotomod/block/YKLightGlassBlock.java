package mod.yuzukotomod.block;

import mod.yuzukotomod.YuzuKotoMod.YuzuKotoBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class YKLightGlassBlock extends Block {

	public YKLightGlassBlock() {
		
		//super(Material.GLASS);
		super(Material.GLASS);
		
		this.setHardness(0.3F);
		this.setSoundType(SoundType.STONE);
		
		this.setLightLevel(1.0F);
			
	}
	
	@Override
	public boolean isFullCube(IBlockState state)
    {
        return false;
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
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
    {
		//return BlockRenderLayer.CUTOUT;
		//半透明
        //return BlockRenderLayer.TRANSLUCENT;
        return BlockRenderLayer.CUTOUT;
    }
	
	/**
	 * 描画するかを判断する？
	 * falseにしたらきえた
	 */
	//@SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered1(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
		return false;
    }
}
