package mod.yuzukotomod.container;

import mod.yuzukotomod.tileentity.YKTEMoonEnchantmentTable;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class YKGuiContainerMoonEncantmentTable extends GuiContainer {

	protected IInventory invTileEntity = null;
	
	public YKGuiContainerMoonEncantmentTable(IInventory iTeInv, IInventory playerInv) {
		
		super(new YKContainerMoonEncantmentTable(iTeInv, playerInv));
		
		invTileEntity = iTeInv;
	}

	/**
	 * GUI描画を行う
	 */
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

		//テクスチャロード
		ResourceLocation GuiTextures = 
				new ResourceLocation("yuzukotomod", "textures/gui/moon_enchanting_table.png");
        this.mc.getTextureManager().bindTexture(GuiTextures);
        
        //画面へバインド（かまどのGUIサイズ）
        int xSize = 176;
        int ySize = 166;
        //描画位置を計算
        int x = (this.width - xSize) / 2;
        int y = (this.height - ySize) / 2;

        //画面へ描画
        this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        
        
        /*
        //矢印の描画
        if (TileEntityFurnace.isBurning(this.tileFurnace))
        {
        	this.inventorySlots.i
            int point = this.getBurnLeftScaled(13);
            this.drawTexturedModalRect(x + 56, y + 36 + 12 - point, 176, 12 - point, 14, point + 1);
        }
        */

        //矢印の描画
        int l = this.getCookProgressScaled(24);
        this.drawTexturedModalRect(x + 79, y + 34, 176, 14, l + 1, 16);
        
        //ゆかりマークの描画
        if (((YKTEMoonEnchantmentTable) this.invTileEntity).isWorldNight()) {
	        //int l2 = this.getCookProgressScaled(31);
	        this.drawTexturedModalRect(x + 5, y + 14, 200, 0, 32, 49);
        }
	}
	
	
    private int getCookProgressScaled(int pixels)
    {
    	//int i = this.tileFurnace.getField(2);
        //int j = this.tileFurnace.getField(3);
        
        int i = ((YKTEMoonEnchantmentTable) this.invTileEntity).moonTimer;
        int j = ((YKTEMoonEnchantmentTable) this.invTileEntity).maxMoonTimer;
        
        
        return j != 0 && i != 0 ? i * pixels / j : 0;
    }
	
    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
	@Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
		//xx_xx.langから取得
		TextComponentTranslation langtext = 
				new TextComponentTranslation("gui.ykmoon_table.name", new Object[0]);
        String text = langtext.getFormattedText();
        
        int x = this.xSize / 2 - this.fontRendererObj.getStringWidth(text) / 2;
        int y = 6;
        
        //タイトル文字
        this.fontRendererObj.drawString(text, x, y, 4210752);
    }
	
	/**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
	@Override
    public boolean doesGuiPauseGame()
    {
		//GUIを開いてる間に処理をとめるかどうか
		//基本的には気にしなくてよさそう？
        return false;
    }


}
