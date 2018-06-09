package mod.yuzukotomod.util;

import java.util.ArrayList;

import net.minecraft.util.math.BlockPos;

public class BlockPosUtil {

	
	/**
	 * ベース座標を中心部にエリアサイズ分のブロックを取得する
	 * 基本的に十字方向（ひし形）
	 * northから時計まわり
	 * 基準点は含まない
	 * @param basePos
	 * @param areaSize
	 * @return
	 */
	public static ArrayList<BlockPos> getBlockAreaList(BlockPos basePos, int areaSize) {
		
		ArrayList<BlockPos> list = new ArrayList<BlockPos>();
		
		for (int area = 1; area <= areaSize; area++) {
			//north + east
			for (int north = area; 0 < north; north--) {
				list.add(basePos.north(north).east(area - north));
			}
			//east + south
			for (int east = area; 0 < east; east--) {
				list.add(basePos.east(east).south(area - east));
			}
			//south + west
			for (int south = area; 0 < south; south--) {
				list.add(basePos.south(south).west(area - south));
			}
			//west + north
			for (int west = area; 0 < west; west--) {
				list.add(basePos.west(west).north(area - west));
			}
		}
		return list;
	}
	
	/**
	 * ベース座標を中心部にエリアサイズ分のブロックを取得する
	 * 外周ごとに別のリストとして保存する
	 * 基本的に十字方向（ひし形）
	 * northから時計まわり
	 * 基準点は含まない
	 * @param basePos
	 * @param areaSize
	 * @return
	 */
	public static ArrayList<ArrayList<BlockPos>> getBlockAreaArrayList(BlockPos basePos, int areaSize) {
		
		ArrayList<ArrayList<BlockPos>> result = new ArrayList<ArrayList<BlockPos>>();
		ArrayList<BlockPos> list = new ArrayList<BlockPos>();
		
		for (int area = 1; area <= areaSize; area++) {
			//north + east
			for (int north = area; 0 < north; north--) {
				list.add(basePos.north(north).east(area - north));
			}
			//east + south
			for (int east = area; 0 < east; east--) {
				list.add(basePos.east(east).south(area - east));
			}
			//south + west
			for (int south = area; 0 < south; south--) {
				list.add(basePos.south(south).west(area - south));
			}
			//west + north
			for (int west = area; 0 < west; west--) {
				list.add(basePos.west(west).north(area - west));
			}
			result.add(list);
		}
		return result;
	}
	
	/**
	 * ベースを中心に立方体範囲で取得する
	 * @param basePos
	 * @return
	 */
	public static ArrayList<BlockPos> getBlockBoxAreaList(BlockPos basePos) {
		
		ArrayList<BlockPos> list = new ArrayList<BlockPos>();
		
		BlockPos pos = basePos.down();
		
		//これで中心に3x3
		for (int i = 0; i < 3; i++) {
			list.add(pos.north());
			list.add(pos.north().east());
			list.add(pos.east());
			list.add(pos.east().south());
			list.add(pos.south());
			list.add(pos.south().west());
			list.add(pos.west());
			list.add(pos.west().north());
			pos = pos.up();
		}
		
		
		list.add(basePos.down());
		list.add(basePos.up());
		
		return list;
	}
	
	
}
