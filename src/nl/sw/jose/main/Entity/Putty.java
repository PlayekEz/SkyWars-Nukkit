package nl.sw.jose.main.Entity;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.player.PlayerMoveEvent;
import nl.sw.jose.main.Boss;
import cn.nukkit.event.Listener;
import nl.sw.jose.main.SkyWars;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.item.Item;
import cn.nukkit.math.Vector3;
import cn.nukkit.Player;
import nl.sw.jose.main.Entity.SWEntity;
import cn.nukkit.entity.Entity;
import nl.sw.jose.main.Settings;
import cn.nukkit.network.protocol.ChangeDimensionPacket;
import cn.nukkit.network.protocol.LevelSoundEventPacket;
import cn.nukkit.utils.Config;
import nl.sw.jose.main.Entity.Debug;
import cn.nukkit.level.Sound;
import nl.sw.jose.main.Portal;
public class Putty implements Listener{
private SkyWars sw;

public Putty(SkyWars plugin){
	this.sw = plugin;
}
@EventHandler
public void portal(PlayerMoveEvent event){
	Player player = event.getPlayer();
	if(player.getLevel() == sw.getServer().getDefaultLevel()){
	if(new Portal().getPosition(player, sw.getDataFolder()) == true){
		if(sw.countArchivos() == 0){
			player.sendMessage(Settings.title+" §cNo hay arenas configuradas");	
			LevelSoundEventPacket pk = new LevelSoundEventPacket();
			pk.sound = LevelSoundEventPacket.SOUND_NOTE;
			pk.x = (int)player.x;
			pk.y = (int)player.y;
			pk.z = (int)player.z;
			pk.extraData = 0;
			pk.entityIdentifier = ":";
			pk.isBabyMob = false;
			pk.isGlobal = true;
			player.dataPacket(pk);
			player.teleport(sw.getServer().getDefaultLevel().getSafeSpawn());
			}else{
				if(sw.namefinal.equals("NOFOUND")){
					player.sendMessage(Settings.title+" §aEl juego esta §6Full");
					LevelSoundEventPacket pk = new LevelSoundEventPacket();
					pk.sound = LevelSoundEventPacket.SOUND_NOTE;
					pk.x = (int)player.x;
					pk.y = (int)player.y;
					pk.z = (int)player.z;
					pk.extraData = 0;
					pk.entityIdentifier = ":";
					pk.isBabyMob = false;
					pk.isGlobal = true;
					player.dataPacket(pk);
					player.teleport(sw.getServer().getDefaultLevel().getSafeSpawn());
				}	else{
					this.joinGame(player, sw.namefinal);	
				}
			}
	}
	}
}
@EventHandler
public void touch(EntityDamageByEntityEvent event){
	Entity player = (Entity)event.getEntity();
	if(event.getDamager() instanceof Player){
	if(player instanceof SWEntity){
		event.setCancelled(true);
	}
}
}
public void managerslot(Player player,String id){
	Config game = new Config(sw.getDataFolder()+"/Arenas/"+id+".dat",Config.YAML);
	if(game.getString("slot1").equals("undefine")){
		game.set("slot1", player.getName());
		game.save();
	}else{
		if(game.getString("slot2").equals("undefine")){
			game.set("slot2", player.getName());
			game.save();
		}else{
			if(game.getString("slot3").equals("undefine")){
				game.set("slot3", player.getName());
				game.save();
			}else{
				if(game.getString("slot4").equals("undefine")){
					game.set("slot4", player.getName());
					game.save();
				}
			}
		}
	}
}
public void joinGame(Player player,String id){
	sw.getServer().loadLevel("debug");
	Config game = new Config(sw.getDataFolder()+"/Arenas/"+id+".dat",Config.YAML);
	ChangeDimensionPacket dimension = new ChangeDimensionPacket();
	dimension.x = (int)player.x;
	dimension.y = (int)player.y+1;
	dimension.z = (int)player.z;
        dimension.dimension = 1;
player.dataPacket(dimension);
player.teleport(sw.getServer().getLevelByName("debug").getSafeSpawn());
sw.getServer().getScheduler().scheduleRepeatingTask(new Debug(sw,player,game.getString("level"), new Vector3(game.getInt("xlobby"),game.getInt("ylobby"),game.getInt("zlobby"))), 10);
player.getInventory().clearAll();
player.setGamemode(2);
sw.setFood(player, 20);
player.setExperience(0);
player.setFoodEnabled(false);
player.setHealth(20);
player.getInventory().setItem(8, Item.get(152,0,1).setCustomName("§l§eSALIR"));
player.getInventory().setItem(0, Item.get(120,0,1).setCustomName("§l§eKITS"));
player.getInventory().setItem(4, Item.get(130,0,1).setCustomName("§l§eVOTE OP"));
this.managerslot(player, id);
Boss.sendBossBarToPlayer(player, game.getInt("boss"), "sw-min-data-boss");
Boss.setVida(player, game.getInt("boss"), 100);
for(Player online: sw.getServer().getLevelByName(game.getString("level")).getPlayers().values()){
	int jug = sw.getServer().getLevelByName(game.getString("level")).getPlayers().size()+1;
	online.sendMessage("§e"+player.getName()+" se ha unido (§b"+jug+"§e/§b4§e)");
sw.getServer().getLevelByName(game.getString("level")).addSound(new Vector3(online.x,online.y,online.z), Sound.RANDOM_LEVELUP);
}
}
}
