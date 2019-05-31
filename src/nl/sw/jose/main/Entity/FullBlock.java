package nl.sw.jose.main.Entity;
import nl.sw.jose.main.Entity.DR;


import java.util.Random;

import cn.nukkit.level.particle.DustParticle;
import cn.nukkit.level.particle.FlameParticle;
import cn.nukkit.math.Vector3;
import cn.nukkit.Server;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.data.ByteEntityData;
import cn.nukkit.entity.data.IntEntityData;
import cn.nukkit.entity.data.SlotEntityData;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.entity.EntityDamageEvent.DamageCause;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemFirework;
import cn.nukkit.level.Level;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.NBTIO;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.network.protocol.EntityEventPacket;
import cn.nukkit.network.protocol.LevelSoundEventPacket;
public class FullBlock extends DR{
	 private int fireworkAge;

	    private int lifetime;

	    private Item firework;
	 public static final int NID = 72;
	public FullBlock(FullChunk chunk, CompoundTag nbt) {
		super(chunk, nbt);
		  this.fireworkAge = 0;
	        Random rand = new Random();
	        this.lifetime = 30 + rand.nextInt(6) + rand.nextInt(7);
	        this.motionX = rand.nextGaussian() * 0.001D;
	        this.motionZ = rand.nextGaussian() * 0.001D;
	        this.motionY = 0.05D;
	        if (nbt.contains("FireworkItem")) {
	            firework = NBTIO.getItemHelper(nbt.getCompound("FireworkItem"));
	        } else {
	            firework = new ItemFirework();
	        }
	        this.setDataProperty(new SlotEntityData(Entity.DATA_DISPLAY_ITEM, firework));
	        this.setDataProperty(new IntEntityData(Entity.DATA_DISPLAY_OFFSET, 1));
	        this.setDataProperty(new ByteEntityData(Entity.DATA_HAS_DISPLAY, 1));
	}
	@Override
    public int getNetworkId() {
        return NID;
    }
	@Override

    public boolean onUpdate(int currentTick) {
        if (this.closed) {
            return false;
        }
        int tickDiff = currentTick - this.lastUpdate;
        if (tickDiff <= 0 && !this.justCreated) {
            return true;
        }
        this.lastUpdate = currentTick;
        this.timing.startTiming();
        boolean hasUpdate = this.entityBaseTick(tickDiff);
        if (this.isAlive()) {
            this.motionX *= 1.15D;
            this.motionZ *= 1.15D;
            this.motionY += 0.04D;
            this.move(this.motionX, this.motionY, this.motionZ);
            this.updateMovement();
   Server.getInstance().getLevelByName(this.getLevel().getFolderName()).addParticle(new FlameParticle(new Vector3(this.motionX,this.motionY,this.motionZ)));
            float f = (float) Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.yaw = (float) (Math.atan2(this.motionX, this.motionZ) * (180D / Math.PI));
            this.pitch = (float) (Math.atan2(this.motionY, (double) f) * (180D / Math.PI));
            if (this.fireworkAge == 0) {
                this.getLevel().addLevelSoundEvent(this, LevelSoundEventPacket.SOUND_LAUNCH);
            }
            this.fireworkAge++;
            hasUpdate = true;
            if (this.fireworkAge >= this.lifetime) {
                EntityEventPacket pk = new EntityEventPacket();
                pk.data = 0;
                pk.event = EntityEventPacket.FIREWORK_EXPLOSION;
                pk.eid = this.getId();
                level.addLevelSoundEvent(this, LevelSoundEventPacket.SOUND_LARGE_BLAST, -1, NETWORK_ID);
      this.sendExplode(Server.getInstance().getLevelByName(this.getLevel().getFolderName()), new Vector3(this.x,this.y,this.z));
                Server.broadcastPacket(getViewers().values(), pk);

                this.kill();
                hasUpdate = true;
            }
        }
        this.timing.stopTiming();
        return hasUpdate || !this.onGround || Math.abs(this.motionX) > 0.00001 || Math.abs(this.motionY) > 0.00001 || Math.abs(this.motionZ) > 0.00001;
    }



    @Override
    public boolean attack(EntityDamageEvent source) {
        return (source.getCause() == DamageCause.VOID ||
                source.getCause() == DamageCause.FIRE_TICK ||
                source.getCause() == DamageCause.ENTITY_EXPLOSION ||
                source.getCause() == DamageCause.BLOCK_EXPLOSION)
                && super.attack(source);
    }
    public void setFirework(Item item) {
        this.firework = item;
        this.setDataProperty(new SlotEntityData(Entity.DATA_DISPLAY_ITEM, item));
    }
    @Override
    public float getWidth() {
        return 0.25f;
    }

    @Override
    public float getHeight() {
        return 0.25f;
    }
    public void sendExplode(Level level,Vector3 pos){
    	Float radius = (float) 1.5;
    	int r = (int)(Math.random()*300);
    	int g = (int)(Math.random()*300);
    	int b = (int)(Math.random()*300);
    	DustParticle particle = new DustParticle(pos, r,g,b);
    	for(int i = 0; i<650; i++){
    		float yaw = (float) ((Math.random() / Math.max(i, 650) -0.5) * Math.PI);
    		float pitch = (float) ((float) Math.random() / Math.max(i, 650 -0.5) *2* Math.PI);
    		float y = (float) -Math.sin(pitch);
    		float delta = (float) Math.cos(pitch);
    		float x = (float) -Math.sin(yaw)+delta;
    		float z = (float) Math.cos(yaw) * delta;
    		Vector3 v = new Vector3(x,y,z);
    		Vector3 p = pos.add(v.normalize().multiply(radius));
    		particle.setComponents(p.x, p.y-1, p.z);
    		level.addParticle(particle);
    	}
    	
    }
}
