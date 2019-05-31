package nl.sw.jose.main;
import java.io.File;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import cn.nukkit.utils.Config;
import nl.sw.jose.main.Settings;
public class Kits {

	
	public void setDefaultKit(Player player){
		player.getInventory().clearAll();
		player.getInventory().setItem(0, Item.get(268,0,1));
		player.getInventory().setItem(1, Item.get(270,0,1));
		player.getInventory().setItem(2, Item.get(271,0,1));
		player.sendMessage(Settings.title+"§aHas recivido el kit §eDefault");
	}
	
	public void setPoseidonKit(Player player){
		player.getInventory().clearAll();
		player.getInventory().setItem(0, Item.get(351,4,5));
		player.getInventory().setItem(1, Item.get(384,4,5));
		player.sendMessage(Settings.title+"§aHas recivido el kit §eEnchant");
	}
	
	public void setArmor(Player player){
		player.getInventory().clearAll();
		player.getInventory().setItem(0, Item.get(298,0,1));
		player.getInventory().setItem(1, Item.get(299,0,1));
		player.getInventory().setItem(2, Item.get(300,0,1));
		player.getInventory().setItem(3, Item.get(301,0,1));
		player.sendMessage(Settings.title+"§aHas recivido el kit §eArmor");
	}
	public void loadKits(File file){
	if(new File(file+"/Kits.db").exists()){
		
	}else{
		Config config = new Config(file+"/Kits.db",Config.YAML);
		config.save();
	}
	}
	public void addKit(Player player,File file){
		Config config = new Config(file+"/Kits.db",Config.YAML);
		if(config.getString(player.getName()).equals("poseidon")){
			this.setPoseidonKit(player);
		}
		if(config.getString(player.getName()).equals("default")){
			this.setDefaultKit(player);
		}
		if(config.getString(player.getName()).equals("armor")){
			this.setArmor(player);
		}
		if(config.getString(player.getName()) == null){
			this.setDefaultKit(player);
		}
	}
	public void setKit(Player player, File file, String type){
		Config config = new Config(file+"/Kits.db",Config.YAML);
		if(type.equals("poseidon")){
			config.set(player.getName(), "poseidon");
			config.save();
			player.sendMessage(Settings.title+"§aKit §eEnchant§a activado.");	
		}
		if(type.equals("default")){
			config.set(player.getName(), "default");
			config.save();
			player.sendMessage(Settings.title+"§aKit §eDefault§a activado.");	
		}
		if(type.equals("armor")){
			config.set(player.getName(), "armor");
			config.save();
			player.sendMessage(Settings.title+"§aKit §eArmor§a activado.");	
		}
	}
}
