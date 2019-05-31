package nl.sw.jose.main.Entity;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;
public class DragonEntity extends DR{
	 public static final int NID = 58;

	    public DragonEntity(FullChunk chunk, CompoundTag nbt) {
	        super(chunk, nbt);
	    }

	    

	    @Override
	    public int getNetworkId() {
	        return NID;
	    }
}
