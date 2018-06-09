package mod.yuzukotocore.asm.transformer;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;

import net.minecraft.launchwrapper.IClassTransformer;

public class TF_ItemShield_Interface implements IClassTransformer, Opcodes{

	/**
	 * name 難読化済クラス名
	 * transformedName 難読化解除クラス名
	 */
	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass) {
		/**
		 * net.minecraft.item.ItemShield Interfaceを追加する
		 */
		String className = "net.minecraft.item.ItemShield";
		
		//対象以外は何もしない
		if (!className.equals(transformedName)) {
			return basicClass;
		}

		//******************************************************************************************
		// ここから実際の変換処理
		//******************************************************************************************
		
		//ASMでbyteをクラスで扱えるようにparseする
		ClassNode cnode = new ClassNode();
		ClassReader reader = new ClassReader(basicClass);
		reader.accept(cnode, 0);
		
		cnode.interfaces.add("mod/yuzukotocore/iInterface/YKIShield");

        // 改変したクラスファイルをバイト列に書き出します
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        cnode.accept(cw);
        basicClass = cw.toByteArray();
        
		return basicClass;
	}	

}
