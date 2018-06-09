package mod.yuzukotomod.block;

import java.util.Random;

import mod.yuzukotomod.YuzuKotoMod;
import mod.yuzukotomod.gui.YKGuiHandler;
import mod.yuzukotomod.tileentity.YKTEMoonEnchantmentTable;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class YKMoonEnchantmentTable extends BlockContainer{
	
	/**
	 * 
	 * @param materialIn
	 */
	public YKMoonEnchantmentTable() {
		
		super(Material.ROCK, MapColor.PURPLE);
		
		//光の透過設定 0-255 fullblockだと255
        //this.setLightOpacity(255);
        
        this.setLightLevel(1.0F);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		// TODO 自動生成されたメソッド・スタブ
		return new YKTEMoonEnchantmentTable();
	}
	
	/**
	 * 立方体判定
	 */
	@Override
	public boolean isFullCube(IBlockState state)
    {
        return false;
    }
	
	@Override
	public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }
	
	/**
	 * 当たり判定
	 */
	protected static final AxisAlignedBB BLOCK_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D);
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return BLOCK_AABB;
    }
	
    /**
     * Called when the block is right clicked by a player.
     */
	@Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
    	//右クリックでGUIを開く
		playerIn.openGui(YuzuKotoMod.INSTANCE, YKGuiHandler.YKMOON_GUI, 
				worldIn, pos.getX(), pos.getY(), pos.getZ());
    	return true;
    }
	
	
	@SideOnly(Side.CLIENT)
	@Override
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
        super.randomDisplayTick(stateIn, worldIn, pos, rand);
        
        if (!(worldIn.getTileEntity(pos) instanceof YKTEMoonEnchantmentTable)) {
        	return;
        }
        YKTEMoonEnchantmentTable te = (YKTEMoonEnchantmentTable)worldIn.getTileEntity(pos);

        //処理してないときはエフェクトださない
        if (te.moonTimer == 0) {
        	return;
        }
        
        for (int i = -2; i <= 2; ++i)
        {
            for (int j = -2; j <= 2; ++j)
            {
                if (i > -2 && i < 2 && j == -1)
                {
                    j = 2;
                }

                for (int k = 0; k <= 1; ++k)
                {
                    BlockPos blockpos = pos.add(i, k, j);
                    //EnumParticleTypes.ENCHANTMENT_TABLE
                    worldIn.spawnParticle(EnumParticleTypes.PORTAL, 
                    		(double)pos.getX() + 0.5D, 
                    		(double)pos.getY() + 1.0D, 
                    		(double)pos.getZ() + 0.5D, 
                    		(double)((float)i + rand.nextFloat()) - 0.5D, 
                    		(double)((float)k - rand.nextFloat() - 1.0F), 
                    		(double)((float)j + rand.nextFloat()) - 0.5D, new int[0]
                    );
                }
            }
        }
    }
}
