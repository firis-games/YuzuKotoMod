package mod.yuzukotocore.asm;

import java.util.Arrays;

import com.google.common.eventbus.EventBus;

import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.ModMetadata;

/**
 * MOD定義を設定する
 */
public class YuzuKotoCoreAsmModContainer extends DummyModContainer{

	public YuzuKotoCoreAsmModContainer() {
		
		super(new ModMetadata());
		
		//MOD定義
		ModMetadata meta = getMetadata();
		meta.modId = "yuzukotocore";
		meta.name = "YuzuKoto Core";
		meta.version = "0.0.1";
		meta.authorList = Arrays.asList("yukari yuzuki", "akane kotonoha", "aoi kotonoha", "akari kizuna");
		meta.description = "";
		meta.url = "";
		meta.credits = "";
		this.setEnabledState(true);
		
	}
	
	@Override
	public boolean registerBus(EventBus bus, LoadController controller) {
		bus.register(this);
		return true;
	}
}
