package nl.sw.jose.main.Game;

import cn.nukkit.entity.Entity;
import cn.nukkit.level.Level;
import cn.nukkit.math.Vector3;
import nl.sw.jose.main.SkyWars;
import nl.sw.jose.main.Entity.DragonEntity;

public class MovePanthom  extends cn.nukkit.scheduler.Task{
	public int time = 10;
private Level lv;
private Vector3 ini;
private Vector3 ter;
private SkyWars sw;
public int x;
public int y;
public int z;
	public MovePanthom(SkyWars plugin,Level level,Vector3 inicial,Vector3 terminal){
		this.lv = level;
		this.ini = inicial;
		this.ter = terminal;
	   this.sw = plugin;
	
	}
	@Override
	public void onRun(int tick) {
		if(this.time > 0){
			this.time--;
		}
		for(Entity b: this.lv.getEntities()){
			if(b instanceof DragonEntity){
				int xMin = (int) Math.min(this.ini.x, this.ter.x);
				int xMax = (int) Math.max(this.ini.x, this.ter.x);
				int zMin = (int) Math.min(this.ini.z, this.ter.z);
				int zMax = (int) Math.max(this.ini.z, this.ter.z);
				int yMin = (int) Math.min(this.ini.y, this.ter.y);
				int yMax = (int) Math.max(this.ini.y, this.ter.y);	
	if(time > 0){
		 for( x = xMin; x < xMax; ++x){
			   for(z = zMin; z < zMax; ++z){
				   for(y = yMin; y < yMax; ++y){
					   b.setPosition(new Vector3(x,y,z));
					   sw.getServer().getLogger().info("Valoe x :"+x+" valor y : "+y+" valor z: "+z);
				   }
			   }
		 }
			
	}
		if(time == 0){
		sw.getServer().getScheduler().cancelTask(this.getTaskId());	
		}
			}
		}	
	}
	/*
public void setMove(Level level , Vector3 inicial , Vector3 terminal){

			int xMin = (int) Math.min(inicial.x, terminal.x);
			int xMax = (int) Math.max(inicial.x, terminal.x);
			int zMin = (int) Math.min(inicial.z, terminal.z);
			int zMax = (int) Math.max(inicial.z, terminal.z);
			int yMin = (int) Math.min(inicial.y, terminal.y);
			int yMax = (int) Math.max(inicial.y, terminal.y);
			
			 
				level.addParticle(new FlameParticle(new Vector3(x,y,z)));
				level.addParticle(new HugeExplodeParticle(new Vector3(x,y,z)));
					   }
				   }
		}
			  

}

*/


}
	
	
	
	

