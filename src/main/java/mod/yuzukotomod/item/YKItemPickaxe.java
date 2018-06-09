package mod.yuzukotomod.item;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class YKItemPickaxe extends ItemPickaxe {

	public YKItemPickaxe(ToolMaterial material) {
		super(material);
	}
	
	
	//ここでこのツールがなんかを示す
	//適正ツールの判断とか採掘スピードとかはここをみてるよう
	@Override
	public Set<String> getToolClasses(ItemStack stack)
	{
		//return toolClass != null ? com.google.common.collect.ImmutableSet.of(toolClass) : super.getToolClasses(stack);
		Set<String> ret = new HashSet<String>();
		//土も適正
		ret.add("pickaxe");
		ret.add("shovel");

		return ret;
	}
	
	
	/**
     * Check whether this Item can harvest the given Block
     */
	@Override
    public boolean canHarvestBlock(IBlockState blockIn)
    {
		//スコップの判定も追加する
		boolean ret = super.canHarvestBlock(blockIn);
		
		if (!ret) {
	        Block block = blockIn.getBlock();
	        ret = block == Blocks.SNOW_LAYER ? true : block == Blocks.SNOW;
		}
		
		return ret;
    }
	
	
	
	/**
     * Called when a Block is right-clicked with this Item
     */
	@Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack itemstack = player.getHeldItem(hand);

        if (!player.canPlayerEdit(pos.offset(facing), facing, itemstack))
        {
            return EnumActionResult.FAIL;
        }
        else
        {
            IBlockState iblockstate = worldIn.getBlockState(pos);
            Block block = iblockstate.getBlock();

            if (facing != EnumFacing.DOWN && worldIn.getBlockState(pos.up()).getMaterial() == Material.AIR && block == Blocks.GRASS)
            {
                IBlockState iblockstate1 = Blocks.GRASS_PATH.getDefaultState();
                worldIn.playSound(player, pos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);

                if (!worldIn.isRemote)
                {
                    worldIn.setBlockState(pos, iblockstate1, 11);
                    itemstack.damageItem(1, player);
                }

                return EnumActionResult.SUCCESS;
            }
            else
            {
                return EnumActionResult.PASS;
            }
        }
    }
}