package mod.yuzukotomod.block;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.BlockStone;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * YuzuKotoブロック用クラス
 */
public class YuzuKotoBlock extends Block {

	//public static final PropertyDirection FACING = BlockHorizontal.FACING;
	
	/*
	 * コンストラクタ
	 */
	public YuzuKotoBlock(Material materialIn) {
		super(materialIn);
		
		//this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.SOUTH));
	}
	
	/**
     * How many world ticks before ticking
     */
	@Override
    public int tickRate(World worldIn)
    {
        return 10;
    }
	
	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
		
		
        //現在日時取得
        Calendar c = Calendar.getInstance();
        //フォーマットを指定
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss SSS");
        System.out.println("logic " + sdf.format(c.getTime()));
        
        //上にブロックをおく
        BlockPos pos2 = pos.up();
        
        /*
        worldIn.setBlockState(pos2, this.getDefaultState());
        this.setScheduleBlockUpdate(worldIn, pos2);
        */
        workTest(worldIn, pos, state, rand);

        //this.setScheduleBlockUpdate(worldIn, pos);
    }
	
	public void workTest(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		
		boolean ret = false;
        //隣接ブロックを処理
        ArrayList<BlockPos> posList = new ArrayList<BlockPos>();
        posList.add(pos.up());
        posList.add(pos.down());
        posList.add(pos.north());
        posList.add(pos.south());
        posList.add(pos.east());
        posList.add(pos.west());
        
        //
        for (BlockPos wPos : posList) {
        	
        	//ブロックを確認
        	Block wBlock = worldIn.getBlockState(wPos).getBlock();
        	
        	//水または流水だったら置換する
        	if (Blocks.WATER == wBlock) {
        		worldIn.setBlockState(wPos, this.getDefaultState());
        		this.setScheduleBlockUpdate(worldIn, wPos);
        		ret = true;
        	}
        	
        }
        if (ret) {
        	//ひとつでも隣接ブロックに水があればもう一回動作する
        	this.setScheduleBlockUpdate(worldIn, pos);
        }
        
	}
	
	
	/**
     * Called by ItemBlocks after a block is set in the world, to allow post-place logic
     */
	@Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
		this.setScheduleBlockUpdate(worldIn, pos);
    }
	public void setScheduleBlockUpdate(World world, BlockPos pos) {

		world.scheduleBlockUpdate(pos, this, 10, 1);
		//現在日時取得
        Calendar c = Calendar.getInstance();
        //フォーマットを指定
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss SSS");
        System.out.println("start " + sdf.format(c.getTime()));
	}
    
    
    
	/*
	 * IBlockState iblockstate1 = this.block.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, i, player, hand);

            if (placeBlockAt(itemstack, player, worldIn, pos, facing, hitX, hitY, hitZ, iblockstate1))
            
            getStateForPlacementが呼ばれた後に
            
            onBlockPlacedByが呼ばれるので
            方向の処理を普通にやる程度ならgetStateForPlacementだけでよさそう？
            
	 */
	
	/**
     * Called by ItemBlocks after a block is set in the world, to allow post-place logic
     */
	/*
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        //worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
        
    }
    */
    
    /**
     * Called by ItemBlocks just before a block is actually set in the world, to allow for adjustments to the
     * IBlockstate
     */
	/*
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }
    */
	
    /*
	@Override
	protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {FACING});
    }
    */
	
	//メタデータを追加する場合はこの二つを設定したほうがいい
    /**
     * Convert the BlockState into the correct metadata value
     */
	/*
    public int getMetaFromState(IBlockState state)
    {
    	//return state.getValue(FACING).getIndex();
    	return ((EnumFacing)state.getValue(FACING)).getIndex();
    }
    
    */
    
    /**
     * Convert the given metadata into a BlockState for this Block
     */
	/*
    public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing enumfacing = EnumFacing.getFront(meta);

        if (enumfacing.getAxis() == EnumFacing.Axis.Y)
        {
            enumfacing = EnumFacing.NORTH;
        }

        return this.getDefaultState().withProperty(FACING, enumfacing);
	}
    */
    
	/**
     * Returns the quantity of items to drop on block destruction.
     */
	@Override
    public int quantityDropped(Random random)
    {
        return 4;
    }

	
	
	
	/**
     * Called after the block is set in the Chunk data, but before the Tile Entity is set
     */
	/*
	@Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
    {
        this.setDefaultFacing(worldIn, pos, state);
    }

    private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state)
    {
        if (!worldIn.isRemote)
        {
            IBlockState iblockstate = worldIn.getBlockState(pos.north());
            IBlockState iblockstate1 = worldIn.getBlockState(pos.south());
            IBlockState iblockstate2 = worldIn.getBlockState(pos.west());
            IBlockState iblockstate3 = worldIn.getBlockState(pos.east());
            EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);

            if (enumfacing == EnumFacing.NORTH && iblockstate.isFullBlock() && !iblockstate1.isFullBlock())
            {
                enumfacing = EnumFacing.SOUTH;
            }
            else if (enumfacing == EnumFacing.SOUTH && iblockstate1.isFullBlock() && !iblockstate.isFullBlock())
            {
                enumfacing = EnumFacing.NORTH;
            }
            else if (enumfacing == EnumFacing.WEST && iblockstate2.isFullBlock() && !iblockstate3.isFullBlock())
            {
                enumfacing = EnumFacing.EAST;
            }
            else if (enumfacing == EnumFacing.EAST && iblockstate3.isFullBlock() && !iblockstate2.isFullBlock())
            {
                enumfacing = EnumFacing.WEST;
            }

            worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
        }
    }
    */
    
    
}
