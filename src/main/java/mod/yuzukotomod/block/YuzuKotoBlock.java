package mod.yuzukotomod.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

/**
 * YuzuKotoブロック用クラス
 * 世界をこわす水増殖処理を削除
 */
public class YuzuKotoBlock extends Block {

	//public static final PropertyDirection FACING = BlockHorizontal.FACING;
	
	/*
	 * コンストラクタ
	 */
	public YuzuKotoBlock(Material materialIn) {
		super(materialIn);
	}

    
	/**
     * Returns the quantity of items to drop on block destruction.
     */
	@Override
    public int quantityDropped(Random random)
    {
        return 4;
    }
    
}
