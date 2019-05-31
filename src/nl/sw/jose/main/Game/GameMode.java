package nl.sw.jose.main.Game;

import java.io.File;

import cn.nukkit.Player;
import cn.nukkit.utils.Config;

public class GameMode {

	
	public void getMode(File file,String name,Player p){
		Config data = new Config(file+"/Arenas/"+name,Config.YAML);
		if(data.getString("chest").equals("normal")){
		p.sendTitle("§l§eSkyWars§r", "§aNormalMode");	
		}else{
			p.sendTitle("§l§eSkyWars§r", "§cINSANEMODE");		
		}
	}
	
	
	
}
