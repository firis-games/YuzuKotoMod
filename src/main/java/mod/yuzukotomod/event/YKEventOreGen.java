package mod.yuzukotomod.event;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

import mod.yuzukotomod.YuzuKotoMod;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.OreGenEvent;

public class YKEventOreGen {
	
	/**
	 * 鉱石生成時のイベント
	 */
    @SubscribeEvent
    public void OreGenEvent(OreGenEvent.Post event) {
    	
    	//鉱石生成用の定義
    	//net.minecraft.world.gen.ChunkProviderSettingsに
    	//定義されている鉄鉱石生成のパラメータと同じもの
    	int oreSize = 9;
    	int oreCount = 20;
    	int oreMinHeight = 0;
    	int oreMaxHeight = 64;
    	
    	//鉱石生成用ジェネレーター
    	WorldGenerator oreGen = new WorldGenMinable(
    			YuzuKotoMod.YuzuKotoBlocks.YUZUKOTO_BLOCK.getDefaultState(), oreSize);
    	
    	//鉱石生成処理
    	//net.minecraft.world.biome.BiomeDecoratorに
    	//定義されているバニラの処理
    	this.genStandardOre1(event, oreCount, oreGen, oreMinHeight, oreMaxHeight);
    	
    }
    
    
    
    /**
     * バニラの処理を流用 package net.minecraft.world.biome.BiomeDecorator 
     * Standard ore generation helper. Generates most ores.
     */
    protected void genStandardOre1(OreGenEvent event, int blockCount, WorldGenerator generator, int minHeight, int maxHeight)
    {
        if (maxHeight < minHeight)
        {
            int i = minHeight;
            minHeight = maxHeight;
            maxHeight = i;
        }
        else if (maxHeight == minHeight)
        {
            if (minHeight < 255)
            {
                ++maxHeight;
            }
            else
            {
                --minHeight;
            }
        }

        BlockPos pos = event.getPos();
        Random rnd = event.getRand();
        World world = event.getWorld();
        
        for (int j = 0; j < blockCount; ++j)
        {
            BlockPos blockpos = pos.add(rnd.nextInt(16), rnd.nextInt(maxHeight - minHeight) + minHeight, rnd.nextInt(16));
            generator.generate(world, rnd, blockpos);
            
        }
    }
}
