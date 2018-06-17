package mod.yuzukotomod.entity.ykminecart;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class YKCMinecart3 extends EntityMinecart{

	public YKCMinecart3(World worldIn) {
		super(worldIn);
	}

	@Override
	public EntityMinecart.Type getType()
    {
        return EntityMinecart.Type.CHEST;
    }
	
	public IBlockState getDisplayTile()
    {
		return Blocks.CHEST.getDefaultState();
    }

}
