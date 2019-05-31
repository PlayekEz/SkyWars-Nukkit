package nl.sw.jose.main.Entity;

import cn.nukkit.entity.Entity;
import cn.nukkit.level.Sound;
import cn.nukkit.level.particle.HugeExplodeParticle;
import cn.nukkit.level.particle.RedstoneParticle;
import cn.nukkit.math.Vector3;
import nl.sw.jose.main.SkyWars;


public class ActionKill  extends cn.nukkit.scheduler.Task{
	private SkyWars sw;
	private Entity ent;
	private String name;
	public int time = 6;
	public ActionKill(SkyWars plugin, Entity entity, String name){
		this.sw = plugin;
		this.ent = entity;
		this.name = name;
	}
	
	@Override
	public void onRun(int tick) {
		if(this.time > 0){
			this.time--;
			
		}
		
		this.ent.setNameTag(sw.getNameKill(this.time,name));
		if(this.time == 0){
		Vector3 vector1 = new Vector3(this.ent.x,this.ent.y,this.ent.z);
		Vector3 motion1 = new Vector3(this.ent.x-1,this.ent.y+2,this.ent.z+1);
		Vector3 motion2 = new Vector3(this.ent.x+1,this.ent.y+2,this.ent.z-1);
		sw.getServer().getLevelByName(this.ent.getLevel().getFolderName()).addParticle(new HugeExplodeParticle(vector1));
		sw.getServer().getLevelByName(this.ent.getLevel().getFolderName()).addParticle(new HugeExplodeParticle(motion1));
		sw.getServer().getLevelByName(this.ent.getLevel().getFolderName()).addParticle(new HugeExplodeParticle(motion2));
		sw.getServer().getLevelByName(this.ent.getLevel().getFolderName()).addParticle(new RedstoneParticle(vector1));
		sw.getServer().getLevelByName(this.ent.getLevel().getFolderName()).addParticle(new RedstoneParticle(motion1));
		sw.getServer().getLevelByName(this.ent.getLevel().getFolderName()).addParticle(new RedstoneParticle(motion2));
			sw.getServer().getLevelByName(this.ent.getLevel().getFolderName()).addSound(new Vector3(this.ent.x,this.ent.y,this.ent.z), Sound.MOB_ENDERDRAGON_HIT);
		sw.LuckyItem(vector1, sw.getServer().getLevelByName(this.ent.getLevel().getFolderName()), motion1);
		sw.LuckyItem(vector1, sw.getServer().getLevelByName(this.ent.getLevel().getFolderName()), motion2);
			this.ent.close();
			sw.getServer().getScheduler().cancelTask(this.getTaskId());
		}
		
	}	
	
	
	
	
}
