package mod.yuzukotocore.asm.transformer;

import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.LineNumberNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;

/**
 * 
 * @author computer
 *
 */
public class TF_EntityPlayer_disableShield implements IClassTransformer, Opcodes{	
	/**
	 * name 難読化済クラス名
	 * transformedName 難読化解除クラス名
	 */
	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass) {
		
		/**
		 * net.minecraft.entity.player.EntityPlayer disableShieldを変換する
		 */
		String className = "net.minecraft.entity.player.EntityPlayer";
		String methodName = "disableShield";
		String methodNameOrg = "func_190777_m";
		String methodDesc = "(Z)V";
		
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
		
		//難読化されてるかを判断するフラグ
		//本番ならfalseのまま
		boolean devFlg = false;
		
		//対象メソッドを検索Nodeを取得する
		MethodNode mnode = null;
        for (MethodNode curMnode : (List<MethodNode>) cnode.methods)
        {
        	//メソッド名を難読化されてるものかそのままのものへ変換する
        	//変換しないとaaとかboとかアルファベット2文字のままになってる
        	String mName = FMLDeobfuscatingRemapper.INSTANCE.mapMethodName(name, curMnode.name, curMnode.desc);
        	String mdName = FMLDeobfuscatingRemapper.INSTANCE.mapMethodDesc(curMnode.desc);

        	//メソッド名と引数型が一致しているかを検索
            if (methodName.equals(mName) && methodDesc.equals(mdName))
            {
                mnode = curMnode;
                devFlg = true;
                break;
            }
            //難読化対策をとりあえず
            if (methodNameOrg.equals(mName) && methodDesc.equals(mdName))
            {
                mnode = curMnode;
                break;
            }
        }
        if (mnode == null) {
        	//対象がなければ何もしない
        	return basicClass;
        }
        
        //******************************************************************************************
  		// ここから書き換え
  		//******************************************************************************************
        /**
         * net.minecraft.entity.player.EntityPlayer disableShieldでやること
         * 
         * 1566行目の
         * this.getCooldownTracker().setCooldown(Items.SHIELD, 100);
         * を削除する
         * 
         * 変わりに
         * this.getCooldownTracker().setCooldown(this.activeItemStack,getItem(), 100);
         * としてSHIELD以外にCooldownが適応されるようにする
         */
        
        //難読化対策でフラグでどちらを使うか判断する
        String m_getCooldownTracker = devFlg ? "getCooldownTracker" : "func_184811_cZ";
        String m_activeItemStack = devFlg ? "activeItemStack" : "field_184627_bm";
        String m_getItem = devFlg ? "getItem" : "func_77973_b";
        String m_setCooldown = devFlg ? "setCooldown" : "func_185145_a";
        
        //指定行だとうまくいかなさそうなのでサーチする形式でやってみる
        //失敗してると落ちるけど、わかりやすいようにそうする
        //******************************************************************************************
        Integer nodeLineNumber = 0;
        {
        	LineNumberNode lnnode = null; 
	        for (AbstractInsnNode aiNode : mnode.instructions.toArray()) {
	        	
	        	//行数のNodeの場合は一時的に保管
	        	if (aiNode instanceof LineNumberNode) {
	        		lnnode = (LineNumberNode)aiNode;
	        	}
	        	//対象のNodeがでるまでチェックする
	        	if (aiNode instanceof MethodInsnNode) {
	        		MethodInsnNode tmp = (MethodInsnNode)aiNode;
	        		
	        		//名前がCoolDownと一致するかを判断する
	        		if(m_getCooldownTracker.equals((
	        				FMLDeobfuscatingRemapper.INSTANCE.mapMethodName(tmp.owner,tmp.name,tmp.desc)))) {
	        			//一致する場合は現時点のlnnodeが対象行番号となる
	        			break;
	        		}
	        		
	        	}
	        }
	        //行番号を保存
	        nodeLineNumber = lnnode.line;
        }
        
        
        //対象メソッドの指定行の処理を削除
        //******************************************************************************************
        Integer nodeIdx = 0;
        {
        	boolean flg1 = false;
        	boolean flg2 = false;
        	int i = 0;
	        for (AbstractInsnNode aiNode : mnode.instructions.toArray()) {
	        	
	        	//行数のNodeを確認
	        	if (aiNode instanceof LineNumberNode) {
	        		LineNumberNode tmp = (LineNumberNode)aiNode;
	        		if (flg1) {
        				flg2 = true;
        			}
        			//行数で直接取得
        			//if (tmp.line == 1566) {
           			if (tmp.line == nodeLineNumber) {
        				flg1 = true;
        				nodeIdx = i;
        			}
	        	} else {
	        		//flgが立ってる間は削除
	        		if (flg1 && !flg2) {
        				mnode.instructions.remove(aiNode);
            		}
	        	}
	        	i += 1;
	        }
	        
        }
        //削除位置へ追記する
        //******************************************************************************************
        
        InsnList overrideList = new InsnList();
        
        //バイトコードからを組み立て
        //this.getCooldownTracker().setCooldown(this.activeItemStack,getItem(), 100);
        overrideList.add(new VarInsnNode(ALOAD, 0));
        overrideList.add(new MethodInsnNode(INVOKEVIRTUAL, 
        		"net/minecraft/entity/player/EntityPlayer", 
        		m_getCooldownTracker, "()Lnet/minecraft/util/CooldownTracker;", false));
        
        overrideList.add(new VarInsnNode(ALOAD, 0));
        overrideList.add(new FieldInsnNode(GETFIELD, 
        		"net/minecraft/entity/player/EntityPlayer",
        		m_activeItemStack, "Lnet/minecraft/item/ItemStack;"));
                
        overrideList.add(new MethodInsnNode(INVOKEVIRTUAL, 
        		"net/minecraft/item/ItemStack",
        		m_getItem, "()Lnet/minecraft/item/Item;", false));
        
        //限界地はわかんないけど数字が大きすぎると
        //bipushじゃだめ
        overrideList.add(new IntInsnNode(BIPUSH, 100));
        //overrideList.add(new IntInsnNode(SIPUSH, 1000));

        overrideList.add(new MethodInsnNode(INVOKEVIRTUAL, 
        		"net/minecraft/util/CooldownTracker", 
        		m_setCooldown, "(Lnet/minecraft/item/Item;I)V", false));
        
        //削除した場所の次へ書き込み
        mnode.instructions.insert(mnode.instructions.get(nodeIdx), overrideList);
        
        // 改変したクラスファイルをバイト列に書き出します
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        cnode.accept(cw);
        basicClass = cw.toByteArray();
       
        return basicClass;
	}

}
