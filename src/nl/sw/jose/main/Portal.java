package nl.sw.jose.main;
import java.io.File;
import cn.nukkit.utils.Config;
import cn.nukkit.Player;
public class Portal {
public void loadConfig(File file){
if(new File(file+"/Portal.db").exists()){
		
	}else{
		Config config = new Config(file+"/Portal.db",Config.YAML);
		config.set("xpos1", 0);
		config.set("ypos1", 0);
		config.set("zpos1", 0);
		config.set("xpos2", 0);
		config.set("ypos2", 0);
		config.set("zpos2", 0);
		config.save();
	}
	}


public boolean getPosition(Player player,File file){
	Config config = new Config(file+"/Portal.db",Config.YAML);
	if(config.getInt("xpos2") == 0){
		return false;
	}else{
	int x1 = config.getInt("xpos1"); int y1 = config.getInt("ypos1"); int z1 = config.getInt("zpos1");
	int x2 = config.getInt("xpos2"); int y2 = config.getInt("ypos2"); int z2 = config.getInt("zpos2");
if((player.getX() <= Math.max(x1, x2)) & (player.getX() >= Math.min(x1, x2)) & (player.getY() <= Math.max(y1, y2)) &
		(player.getY() >= Math.min(y1, y2)) & (player.getZ() <= Math.max(z1, z2)) & (player.getZ() >= Math.min(z1, z2))){
	return true;
}else{
	return false;
}
	}
	

}

public void setPos1(File file, Player p,String pos){
	int pas = Integer.parseInt(pos);
	Config config = new Config(file+"/Portal.db",Config.YAML);
	float x = (float) p.x; float y = (float)p.y; float z = (float)p.z;
	if(pas == 1){
		config.set("xpos1", x);
		config.set("ypos1", y);
		config.set("zpos1", z);	
		config.save();
		p.sendMessage(Settings.title+" Pos1 configurada");
	}
	if(pas == 2){
		config.set("xpos2", x);
		config.set("ypos2", y);
		config.set("zpos2", z);	
		config.save();
		p.sendMessage(Settings.title+" Pos2 configurada");
	}
	
}


}
