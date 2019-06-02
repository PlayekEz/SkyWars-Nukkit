package nl.sw.jose.main;
import cn.nukkit.entity.Entity;
import cn.nukkit.level.Level;
import nl.sw.jose.main.SkyWars;
import nl.sw.jose.main.Entity.SWEntity;
public class ProtocolUpdate extends cn.nukkit.scheduler.Task {
	private SkyWars sw;
public ProtocolUpdate(SkyWars plugin){
	this.sw = plugin;
}

@Override
public void onRun(int tick) {
	sw.sendBoss();
	sw.system();
	for(Level lv : sw.getServer().getLevels().values()){
		for(Entity swa : lv.getEntities()){
			if(swa instanceof SWEntity){
				swa.setNameTag(sw.getNameNPC());
				swa.setNameTagAlwaysVisible(true);
				swa.setNameTagVisible(true);
				swa.setScale((float)0.01);
				
			}
		}
	}
}


}
