package mod.yuzukotocore.asm;

import java.util.Map;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;


/**
 * @TransformerExclusions
 * Transformerの対象外とする設定
 *
 */
@TransformerExclusions({"mod.yuzukotocore.asm"})
public class YuzuKotoCoreAsmPlugin implements IFMLLoadingPlugin {

	/**
	 * クラスをbyte変換するTransformerを定義したクラスを返却する
	 * net.minecraft.launchwrapper.IClassTransformer
	 */
	@Override
	public String[] getASMTransformerClass() {
		
		//基本はひとつでやる予定
		return new String[]{"mod.yuzukotocore.asm.transformer.TF_EntityPlayer_disableShield"
				,"mod.yuzukotocore.asm.transformer.TF_EntityPlayer_damageShield"
				,"mod.yuzukotocore.asm.transformer.TF_ItemShield_Interface"
		};
		
	}

	/**
	 * coreModのMod設定を定義したクラスを返却する
	 */
	@Override
	public String getModContainerClass() {
		//CoreModの設定を定義する部分
		return "mod.yuzukotocore.asm.YuzuKotoCoreAsmModContainer";
	}

	@Override
	public String getSetupClass() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public String getAccessTransformerClass() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	
	
}
