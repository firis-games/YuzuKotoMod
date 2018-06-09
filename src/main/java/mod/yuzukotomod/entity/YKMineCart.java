package mod.yuzukotomod.entity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityMinecartEmpty;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUseBed;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.world.World;

public class YKMineCart extends EntityMinecart {
	
    public YKMineCart(World worldIn)
    {
        super(worldIn);
        
        //ばうんでぃんぐBOXの場所しかかわらない
        //this.setSize(0.98F, 2.0F);
        //this.setSize(0.98F * 2, 0.7F);
        this.setSize(1.75F, 0.75F);
        
    }

    public YKMineCart(World worldIn, double x, double y, double z)
    {
        super(worldIn, x, y, z);
        
    }

    public static void registerFixesMinecartEmpty(DataFixer fixer)
    {
        EntityMinecart.registerFixesMinecart(fixer, EntityMinecartEmpty.class);
    }

    public boolean processInitialInteract(EntityPlayer player, EnumHand hand)
    {
        if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.entity.minecart.MinecartInteractEvent(this, player, hand))) return true;

        if (player.isSneaking())
        {
            return false;
        }
        else if (this.isBeingRidden())
        {
            return true;
        }
        else
        {
        	//うまのソースからもってきた
        	player.rotationYaw = this.rotationYaw + 5;
            player.rotationPitch = this.rotationPitch;
            
            //ちょっと実験用
            //これで上がり幅を調整できるみたいね、馬とかでつかわれてる
            player.stepHeight = 2.0F;
            
            if (!this.world.isRemote)
            {
                player.startRiding(this);
            	//player.isPlayerSleeping();
                
                //EntityPlayerMP playermp = (EntityPlayerMP) player;
                 

            }
            //player.setRenderOffsetForSleep(EnumFacing.NORTH);
            
            //player.renderOffsetX = -1.8F * (float)EnumFacing.NORTH.getFrontOffsetX();
            //player.renderOffsetZ = -1.8F * (float)EnumFacing.NORTH.getFrontOffsetZ();

            return true;
        }
    }

    /**
     * Called every tick the minecart is on an activator rail.
     */
    public void onActivatorRailPass(int x, int y, int z, boolean receivingPower)
    {
        if (receivingPower)
        {
            if (this.isBeingRidden())
            {
                this.removePassengers();
            }

            if (this.getRollingAmplitude() == 0)
            {
                this.setRollingDirection(-this.getRollingDirection());
                this.setRollingAmplitude(10);
                this.setDamage(50.0F);
                this.setBeenAttacked();
            }
        }
    }
    
    public EntityMinecart.Type getType()
    {
        return EntityMinecart.Type.RIDEABLE;
    }
    
    
    @Override
    public void onUpdate() {
    	
    	super.onUpdate();
    	
    	//System.out.println("aaaaaaaaaa");
    	
    }
    
    
    /**
     * Returns the Y offset from the entity's position for any entity riding this one.
     */
    @Override
    public double getMountedYOffset()
    {
    	//マインカートだと0にされてるみたい
    	//return super.getMountedYOffset();
        //return (double)this.height * 0.25D;
        return 0.5D;
    }
    
    @Override
    public IBlockState getDisplayTile()
    {
        //return !this.hasDisplayTile() ? this.getDefaultDisplayTile() : Block.getStateById(((Integer)this.getDataManager().get(DISPLAY_TILE)).intValue());
    	//return Blocks.BED.getDefaultState();
    	
    	return Blocks.BED.getBlockState()
    			.getBaseState()
    			.withProperty(BlockBed.PART, BlockBed.EnumPartType.HEAD)
    			.withProperty(BlockBed.OCCUPIED, Boolean.valueOf(false));
    }

}
