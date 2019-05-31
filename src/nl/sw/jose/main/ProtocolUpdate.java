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
	sw.setPlayersArena();
	sw.sendBoss();
	sw.system();
	for(Level lv : sw.getServer().getLevels().values()){
		for(Entity ffagame : lv.getEntities()){
			if(ffagame instanceof SWEntity){
				ffagame.setNameTag(sw.getNameNPC());
				ffagame.setNameTagAlwaysVisible(true);
				ffagame.setNameTagVisible(true);
			}
		}
	}
}


}
