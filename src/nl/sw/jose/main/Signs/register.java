package nl.sw.jose.main.Signs;
import java.io.File;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.item.Item;
import cn.nukkit.level.Sound;
import cn.nukkit.math.Vector3;
import cn.nukkit.network.protocol.ChangeDimensionPacket;
import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import nl.sw.jose.main.Boss;
import nl.sw.jose.main.SkyWars;
import nl.sw.jose.main.Entity.Debug;
import cn.nukkit.event.block.SignChangeEvent;
import cn.nukkit.utils.Config;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockSignPost;
import cn.nukkit.blockentity.BlockEntitySign;
public class register implements Listener{

	private SkyWars sw;
	public register(SkyWars plugin){
		this.sw = plugin;
	}
	

@EventHandler
public void onRegister(SignChangeEvent event){
Player p = event.getPlayer();
if(p.isOp()){
if(event.getLine(0).equals("sw")){
	String name = event.getLine(1);
	if(!new File(this.sw.getDataFolder()+"/Arenas/"+name+".dat").exists()){
	event.setLine(0, "§cSW_Error");	
	event.setLine(1, "§cMap No exist");	
	event.setLine(2, "§cnull");	
	event.setLine(3, "§c0/0");	
	}else{
		String status = "§5Closed";
		Config game = new Config(this.sw.getDataFolder()+"/Arenas/"+name+".dat",Config.YAML);
		int pla = this.sw.getServer().getLevelByName(game.getString("level")).getPlayers().size();
		if(game.getString("status").equals("on")){
			status = "§aOpen";
		}
		if(game.getString("reset").equals("reset")){
			status = "§6Reseting";
		}
		
		event.setLine(0, "§b§lSkyWars");	
		event.setLine(1, name);	
		event.setLine(2, status);	
		event.setLine(3, "§b"+pla+"§7/§b4");	
		p.sendTip("Sign registrado");
		
	}
	
	
	
}
}
}
	
	
//join
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
@EventHandler
public void onJoinGameSign(PlayerInteractEvent event){
Player p = event.getPlayer();
Block b = event.getBlock();

if(b instanceof BlockSignPost){
	 BlockEntitySign sign = (BlockEntitySign) b.getLevel().getBlockEntity(b);
	 if(sign == null){
		 return;
	 }
	 
	String[] line = sign.getText(); 
	if(line[0].equals("§b§lSkyWars")){
		String map = line[1];
		Config data = new Config(this.sw.getDataFolder()+"/Arenas/"+map+".dat",Config.YAML);
		String status = data.getString("status");
		if(status.equals("on")){
	this.joinGame(p, map);
		}else{
			
		}
	}
	 
}
	
	
	
	
}
	
}
