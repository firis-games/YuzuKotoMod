package mod.yuzukotocore.asm.transformer;

import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LineNumberNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;

import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;

/**
 * 
 * @author computer
 *
 */
public class TF_EntityPlayer_damageShield implements IClassTransformer, Opcodes{

	/*
	private void log(String log) {
		try {
			FileWriter filewriter = new FileWriter(new File("d:\\mclog.txt"), true);
			filewriter.write(log);
			filewriter.write("\r\n");
			filewriter.close();
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
		}
		
	}
	*/
	
	/**
	 * name 難読化済クラス名
	 * transformedName 難読化解除クラス名
	 */
	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass) {
		
		/**
		 * net.minecraft.entity.player.EntityPlayer damageShieldを変換する
		 */
		String className = "net.minecraft.entity.player.EntityPlayer";
		
		String methodName = "damageShield";
		String methodNameOrg = "func_184590_k";
		String methodDesc = "(F)V";
		
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
         * net.minecraft.entity.player.EntityPlayer damageShieldでやること
         * 
         * 1147行目の
         * if (damage >= 3.0F && this.activeItemStack.getItem() == Items.SHIELD)
         * を
         * 
         * if (damage >= 3.0F && this.activeItemStack.getItem() instanceof YKIShield)
         * とする
         * 
         * GETSTATIC net/minecraft/init/Items.SHIELD : Lnet/minecraft/item/Item;
         * IF_ACMPNE L1
         * 
         * INSTANCEOF mod/yuzukotocore/iInterface/YKIShield
         * POP
         * 
         * バイトコードとしてはこうなる
         * IFはジャンプ先の設定とかがあるみたいなので
         * 対象のところだけ削除をやる
         */
        
        //難読化対策でフラグでどちらを使うか判断する
        String f_SHIELD = devFlg ? "SHIELD" : "field_185159_cQ";
                 
        //指定行だとうまくいかなさそうなのでサーチする形式でやってみる
        //失敗してると落ちるけど、わかりやすいようにそうする
        //******************************************************************************************
        Integer nodeIdx = 0;
        Label ifJumpLabel = null;
        {
        	//LineNumberNode lnnode = null; 
	        for (AbstractInsnNode aiNode : mnode.instructions.toArray()) {
	        	
	        	//行数のNodeの場合は一時的に保管
	        	if (aiNode instanceof LineNumberNode) {
	        		//lnnode = (LineNumberNode)aiNode;
	        	}
	        	//対象のNodeがでるまでチェックする
	        	if (aiNode instanceof FieldInsnNode) {
	        		FieldInsnNode tmp = (FieldInsnNode)aiNode;
	        		
	        		//GETSTATIC net/minecraft/init/Items.SHIELD : Lnet/minecraft/item/Item;
	        		//名前がSTATICのSHIELDと一致するかを判断する
	        		if(f_SHIELD.equals((
	        				FMLDeobfuscatingRemapper.INSTANCE.mapFieldName(tmp.owner,tmp.name,tmp.desc)))) {
	        			//一致する場合は現時点のlnnodeが対象行番号となる
	        			nodeIdx += 1;
	        			JumpInsnNode wk = (JumpInsnNode)aiNode.getNext();
	        			ifJumpLabel = wk.label.getLabel();
	        			break;
	        		}
	        		
	        	}
		        //index番号を保存
	        	nodeIdx += 1;
	        }
        }
        
        
        //対象メソッドの指定行の処理を削除
        //******************************************************************************************
        {
        	int i = 0;
	        for (AbstractInsnNode aiNode : mnode.instructions.toArray()) {
	        	
	        	//対象行のNodeを削除する
	        	if (i == nodeIdx || i+1 == nodeIdx) {
	        		mnode.instructions.remove(aiNode);
	        	}
	        	i += 1;
	        }
	        
        }
        
        //削除位置へ追記する
        //******************************************************************************************
        
        //挿入場所が1つしたなので削除位置indexをひとつ上へ
        nodeIdx -= 1;
        
        InsnList overrideList = new InsnList();
        /*
         mod/yuzukotocore/iInterface/YKIShield
        */
        //バイトコードからを組み立て
        //ピンポイントで削除して、IF後のラベルを付け替え
        overrideList.add(new TypeInsnNode(INSTANCEOF, "mod/yuzukotocore/iInterface/YKIShield"));
        
        overrideList.add(new JumpInsnNode(IFEQ, new LabelNode(ifJumpLabel)));
        
        //削除した場所の次へ書き込み
        mnode.instructions.insert(mnode.instructions.get(nodeIdx), overrideList);
        
        // 改変したクラスファイルをバイト列に書き出します
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        cnode.accept(cw);
        basicClass = cw.toByteArray();
       
        return basicClass;
	}

}
