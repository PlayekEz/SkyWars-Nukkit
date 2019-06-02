package nl.sw.jose.main;
import cn.nukkit.Player;
import nl.sw.jose.main.Signs.UpdateSign;
import nl.sw.jose.main.Signs.register;
import nl.sw.jose.main.Portal;
import nl.sw.jose.main.ResetMap;
import nl.sw.jose.main.Arenas;
import cn.nukkit.entity.Attribute;
import cn.nukkit.entity.Entity;
import cn.nukkit.item.Item;
import cn.nukkit.level.Level;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.DoubleTag;
import cn.nukkit.nbt.tag.FloatTag;
import cn.nukkit.nbt.tag.ListTag;
import cn.nukkit.network.protocol.LevelSoundEventPacket;
import cn.nukkit.network.protocol.UpdateAttributesPacket;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import nl.sw.jose.main.Settings;
import java.io.File;
import java.nio.charset.StandardCharsets;

import nl.sw.jose.main.ZipLib;
import nl.sw.jose.main.Entity.SWEntity;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.Command;
import nl.sw.jose.main.ProtocolUpdate;
import nl.sw.jose.main.Creador.ArenaCreator;
import nl.sw.jose.main.Entity.DragonEntity;
import nl.sw.jose.main.Entity.FullBlock;
import nl.sw.jose.main.Entity.Murder;
import nl.sw.jose.main.Entity.Putty;
import nl.sw.jose.main.Kits;
import nl.sw.jose.main.Game.GameTask;
public class SkyWars extends PluginBase{
	public int playersarena = 0;
	public int id = 0;
	public String namefinal = "NOFOUND";
public void onEnable(){
	new Kits().loadKits(this.getDataFolder());
	new Portal().loadConfig(this.getDataFolder());
Entity.registerEntity(SWEntity.class.getSimpleName(), SWEntity.class);
Entity.registerEntity(DragonEntity.class.getSimpleName(), DragonEntity.class);
Entity.registerEntity(Murder.class.getSimpleName(), Murder.class);
Entity.registerEntity(FullBlock.class.getSimpleName(), FullBlock.class);
this.getServer().getLogger().info(Settings.title+"Cargando recursos...");	
new File(this.getDataFolder()+"/Arenas").mkdirs();
new File(this.getDataFolder()+"/Mapas").mkdirs();
this.getSchedulersGame();
this.getEvent();
this.loadArenas();
}
public void getSchedulersGame(){
	this.getServer().getScheduler().scheduleRepeatingTask(new ProtocolUpdate(this), 5);
	this.getServer().getScheduler().scheduleRepeatingTask(new GameTask(this), 20);
	this.getServer().getScheduler().scheduleRepeatingTask(new UpdateSign(this), 10);
}
public void getEvent(){
	this.getServer().getPluginManager().registerEvents(new Putty(this), this);
	this.getServer().getPluginManager().registerEvents(new Arenas(this), this);
	this.getServer().getPluginManager().registerEvents(new register(this), this);
}
public void resetmap(String name){
	String kolu = name.replace(".dat", "");
	this.deletePath(kolu);
	new ResetMap().setReset(this.getDataFolder(), kolu, this.getServer().getDataPath());
	this.getServer().loadLevel(kolu);
	this.getServer().getLevelByName(kolu).setTime(0);
	this.getServer().getLevelByName(kolu).stopTime();
	this.getServer().getLevelByName(kolu).setRaining(false);
	this.getServer().getLevelByName(kolu).setThundering(false);
}

public void deletePath(String name){
	if(this.countArchivos() > 0){
		String[] path = new File(this.getServer().getDataPath()+"worlds/"+name+"/region/").list();
		for(String w: path){
		new File(this.getServer().getDataPath()+"worlds/"+name+"/region/"+w).delete();	
		}
	}
}
public void loadArenas(){
if(this.countArchivos() > 0){
String[] gameLevel = new File(this.getDataFolder()+"/Arenas/").list();	
for(String w : gameLevel){
	Config arena = new Config(this.getDataFolder()+"/Arenas/"+w,Config.YAML);
	arena.set("status", "on");
	arena.set("time", 60*8);
	arena.set("start", 30);
	arena.set("reset", 10);
	arena.set("slot1", "undefine");
	arena.set("slot2", "undefine");
	arena.set("slot3", "undefine");
	arena.set("slot4", "undefine");
	arena.set("chest", "normal");
	arena.set("voteop", 0);
	arena.save();
	this.resetmap(w.replace(".dat", ""));
	this.getServer().getLogger().info(Settings.title+" Cargando "+w.replace(".dat", ""));
	
}

}else{
	this.getServer().getLogger().info(Settings.title+" Se cargaron 0 arenas correctamente");	
}
}
public int countArchivos(){
	int total = 0;
	File archivos = new File(this.getDataFolder()+"/Arenas/");
	String[] isArchivo = archivos.list();
	total += isArchivo.length;
	  return total;
}
public CompoundTag nbt(Player sender,String name) {
    CompoundTag nbt = new CompoundTag()
     .putList(new ListTag<>("Pos")
            .add(new DoubleTag("", sender.x))
            .add(new DoubleTag("", sender.y))
            .add(new DoubleTag("", sender.z)))
      .putList(new ListTag<DoubleTag>("Motion")
            .add(new DoubleTag("", 0))
            .add(new DoubleTag("", 0))
            .add(new DoubleTag("", 0)))
     .putList(new ListTag<FloatTag>("Rotation")
            .add(new FloatTag("", (float) sender.getYaw()))
            .add(new FloatTag("", (float) sender.getPitch())))
     .putBoolean("Invulnerable", true)
     .putString("NameTag", name)  
    .putFloat("scale", 2); 
         nbt.putCompound("Skin", new CompoundTag()
                .putString("ModelId", sender.getSkin().getGeometryName())
                .putByteArray("Data", sender.getSkin().getSkinData())
                .putString("ModelId", sender.getSkin().getSkinId())
                .putByteArray("CapeData", sender.getSkin().getCapeData())
                .putString("GeometryName", sender.getSkin().getGeometryName())
                .putByteArray("GeometryData", sender.getSkin().getGeometryData().getBytes(StandardCharsets.UTF_8))
         );
         nbt.putBoolean("ishuman", true);
         nbt.putString("Item", sender.getInventory().getItemInHand().getName());
         nbt.putString("Helmet", sender.getInventory().getHelmet().getName());
         nbt.putString("Chestplate", sender.getInventory().getChestplate().getName());
         nbt.putString("Leggings", sender.getInventory().getLeggings().getName());
         nbt.putString("Boots", sender.getInventory().getBoots().getName());
     
     return nbt;

}
public String getNameNPC(){
	String title = "§l§eSkyWars§r";
String arena = "";
String playing = "";
if(this.namefinal.equals("NOFOUND")){
arena = "§cNo se encontro arena §b: ";
playing = "§7(§a0§7/§a0§7)";
}else{
Config data = new Config(this.getDataFolder()+"/Arenas/"+this.namefinal+".dat",Config.YAML);
int playings = this.getServer().getLevelByName(data.getString("level")).getPlayers().size();
playing = "§7(§a"+playings+"§7/§a4§7)";
arena = "§6"+data.getString("name")+" §b: ";
}
String enter = "§dPull to the portal to enter";
	String br = "\n";
	return title+br+br+arena+playing+br+br+enter+br+br+br+br+br;
}
@Override
public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
	 Player player = (Player) sender;
	 switch (command.getName().toLowerCase()) {
	 case "sw":
		 if(player.isOp()){
	if(args.length == 0){
		player.sendMessage(Settings.title+" Comando undefinido");
	}else{
		if(args[0].equals("setnpc")){
			  CompoundTag nbt = this.nbt(player,"swentity");  
	          Entity ent = Entity.createEntity("SWEntity", player.chunk, nbt);
	          ent.setNameTag(this.getNameNPC());
	          ent.setNameTagAlwaysVisible(true);
	          ent.setNameTagVisible(true);
	          ent.setScale((float) 0.01);
	  for(Player p : this.getServer().getOnlinePlayers().values()){
		  ent.spawnTo(p);
	  }
	   player.sendMessage(Settings.title+"NPC colocado");      
		}
		
		if(args[0].equals("removenpc")){
			for(Level lv : this.getServer().getLevels().values()){
				for(Entity game : lv.getEntities()){
					if(game instanceof SWEntity){
						game.kill();
					}
				}
			}
			  player.sendMessage(Settings.title+"NPCS removidos");
		}
		if(args[0].equals("cancel")){
			player.teleport(this.getServer().getDefaultLevel().getSafeSpawn());
			player.sendMessage(Settings.title+"Modo editor desactivado");
		}
		if(args[0].equals("edit")){
			if(args.length == 1){
				player.sendMessage(Settings.title+"usage : /sw edit {level}");
			}else{
				if(new File(this.getServer().getDataPath()+"worlds/"+args[1]).exists()){
					this.getServer().loadLevel(args[1]);
					player.teleport(this.getServer().getLevelByName(args[1]).getSafeSpawn());
					
				}else{
					 player.sendMessage(Settings.title+"Level no existe");
				}
				
			}
		}
		if(args[0].equals("create")){
			if(args.length <4){
				player.sendMessage(Settings.title+"usage : /sw create {name} {id} {level}");
			}else{
			if(new File(this.getServer().getDataPath()+"worlds/"+args[3]).exists()){
				ArenaCreator arena = new ArenaCreator();
				arena.createAren(args[1], player, args[2], args[3], this.getDataFolder());
			}else{
				 player.sendMessage(Settings.title+"Level no existe");	
			}	
		
		
			}

		}
		if(args[0].equals("setspawn")){
			if(args.length <3){
				player.sendMessage(Settings.title+"usage : /sw setspawn {id} {numero}");	
			}else{
					ArenaCreator arena = new ArenaCreator();
					arena.setSpawn(player, args[1], this.getDataFolder(), args[2]);
				
			}
		}
		
		if(args[0].equals("setlobby")){
			if(args.length <2){
				player.sendMessage(Settings.title+"usage : /sw setlobby {id}");	
			}else{
					ArenaCreator arena = new ArenaCreator();
					arena.setLobby(player, args[1], this.getDataFolder());
				
			}
		}	
		if(args[0].equals("setespc")){
			if(args.length <2){
				player.sendMessage(Settings.title+"usage : /sw setespec {id}");	
			}else{
					ArenaCreator arena = new ArenaCreator();
					arena.setespectador(player, args[1], this.getDataFolder());
				
			}
		}
		
		if(args[0].equals("islapos")){
			if(args.length <4){
				player.sendMessage(Settings.title+"usage : /sw islapos {id} {isla} {pos}");	
			}else{
					ArenaCreator arena = new ArenaCreator();
					arena.setpositionislas(player, args[1], this.getDataFolder(), args[2], args[3]);
				
			}
		}
		if(args[0].equals("lobbypos")){
			if(args.length <3){
				player.sendMessage(Settings.title+"usage : /sw lobbypos {id} {pos}");	
			}else{
					ArenaCreator arena = new ArenaCreator();
					arena.pos1lobby(player, args[1], this.getDataFolder(), args[2]);
				
			}
		}
		if(args[0].equals("chest")){
			if(args.length <3){
				player.sendMessage(Settings.title+"usage : /sw chest {id} {pos}");	
			}else{
					ArenaCreator arena = new ArenaCreator();
					arena.setchest(player, args[1], this.getDataFolder(), args[2]);
				
			}
		}
		if(args[0].equals("minvoid")){
			if(args.length <2){
				player.sendMessage(Settings.title+"usage : /sw minvoid {id}");	
			}else{
					ArenaCreator arena = new ArenaCreator();
					arena.minvoid(player, args[1], this.getDataFolder());
				
			}
		}
		if(args[0].equals("portal")){
			if(args.length <2){
				player.sendMessage(Settings.title+"usage : /sw portal {pos}");	
			}else{
					new Portal().setPos1(this.getDataFolder(), player, args[1]);
				
			}
		}
		if(args[0].equals("save")){
			if(args.length <2){
				player.sendMessage(Settings.title+"usage : /sw save {id}");	
			}else{
					ArenaCreator arena = new ArenaCreator();
					arena.save(player, args[1], this.getDataFolder());
				
			}
		}
		if(args[0].equals("savemap")){
			if(args.length == 1){
				player.sendMessage(Settings.title+"usage : /sw savemap {name}");	
			}else{
				if(new File(this.getServer().getDataPath()+"worlds/"+args[1]).exists()){
				try {
					new ZipLib().sendZip(this.getServer().getDataPath()+"worlds/"+args[1]+"/region/", this.getDataFolder()+"/Mapas/"+args[1]+".zip");
				} catch (Exception e) {
				
					e.printStackTrace();
				}
				player.sendMessage(Settings.title+"Se guardado datos del mapa : "+args[1]);
				}else{
					player.sendMessage(Settings.title+"Mapa no existe");	
				}
			}
		}
		if(args[0].equals("help")){
			player.sendMessage("§bSW§7: §bGuia de Configuracion");
			player.sendMessage("§6usage : /sw create {name} {id} {level}");
			player.sendMessage("§6usage : /sw setspawn {id} {numero}");
			player.sendMessage("§6usage : /sw setlobby {id}");	
			player.sendMessage("§6usage : /sw setespc {id}");	
			player.sendMessage("§6usage : /sw minvoid {id}");
			player.sendMessage("§6usage : /sw islapos {id} {isla} {pos}");
			player.sendMessage("§6usage : /sw lobbypos {id} {pos}");
			player.sendMessage("§6usage : /sw chest {id} {pos}");
			player.sendMessage("§6usage : /sw savemap {name} (no found)");
			player.sendMessage("§6usage : /sw save {id}");
			player.sendMessage("§6usage : /sw edit {level}");
			player.sendMessage("§6usage : /sw cancel");
			player.sendMessage("§6usage : /sw setnpc");
			player.sendMessage("§6usage : /sw removenpc");
			player.sendMessage("§6usage : /sw portal {pos}");
			player.sendMessage("§b=========================");
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
		}
	}
	
		 }else{
			 player.sendMessage("§eusa §a/commands §e para ver los comandos");
		 }
		 break;
	 }
	 return false;
}


public void setFood(Player player,int food){
	UpdateAttributesPacket upk = new UpdateAttributesPacket();
	upk.entityId = player.getId();
	Attribute attr = Attribute.getAttribute(Attribute.MAX_HUNGER);
	attr.setMaxValue(20);
	attr.setMinValue(0);
	attr.setValue(food);
	upk.entries = new Attribute[]{ attr };
	player.dataPacket(upk);
}
public void system(){
	if(this.countArchivos() >0){
		if(this.id >= this.countArchivos()){
			this.namefinal = "NOFOUND";
					this.id = 0;
		}
		Config game = new Config(this.getDataFolder()+"/Arenas/sw-"+id+".dat",Config.YAML);
		if(this.namefinal.equals("NOFOUND")){
			if(game.getString("status").equals("off")){
				this.id++;
			}else{
				if(game.getString("status").equals("reset")){
					this.id++;
				}else{
					namefinal = "sw-"+id;	
				}
				
			}
			
		}else{
			Config game2 = new Config(this.getDataFolder()+"/Arenas/"+namefinal+".dat",Config.YAML);
			if(game2.getString("status").equals("off")){
				this.id++;
			}else{
				if(game.getString("status").equals("reset")){
					this.id++;
				}else{
					this.namefinal = "sw-"+id;	
				}
				
			}
		}
	}else{
		namefinal = "NOFOUND";
	}
}
public String getBossText(Player player, String name){
	String text = "";
	Config game = new Config(this.getDataFolder()+"/Arenas/"+name,Config.YAML);
	int jugadores = this.getServer().getLevelByName(game.getString("level")).getPlayers().size();
	if(game.getString("status").equals("on") && jugadores <2 && game.getInt("start") >0){
		text = "§bBuscando§e 1 §bJugador§a..";
	}
	if(game.getString("status").equals("on") && jugadores >= 2 && game.getInt("start") >10){
		text = "§bComenzando en §9: §a"+game.getInt("start")+" §bsegundos";
	}
	if(game.getString("status").equals("off") && jugadores >= 2 && game.getInt("start") >0){
		text = "§bComenzando en §9: §a"+game.getInt("start")+" §bsegundos";
	}
	if(game.getString("status").equals("off") && jugadores >= 2 && game.getInt("start") == 0){
		text = "§bTermina en §9: §a"+this.getReloj(game.getInt("time"));
	}
	if(game.getString("status").equals("reset") && game.getInt("start") == 0){
		text = "§bTerminando partida §a: §e"+game.getInt("reset");
	}
	return text;
}
public void updateboss(Player player,String name){
	//Config game = new Config(this.getDataFolder()+"/Arenas/"+name,Config.YAML);
	//Boss.sendTitle(player, game.getInt("boss"), this.getBossText(player, name));
	//Boss.setVida(player, game.getInt("boss"), 100);//boss no auth
	player.sendTip(this.getBossText(player, name));
}

public void sendBoss(){
	if(this.countArchivos() >0){
		String[] games = new File(this.getDataFolder()+"/Arenas/").list();
		for(String w : games){
			Config game = new Config(this.getDataFolder()+"/Arenas/"+w,Config.YAML);
			for(Player p : this.getServer().getLevelByName(game.getString("level")).getPlayers().values()){
				this.updateboss(p, w);
			}
		}
	}
}

public String getReloj(int num){
    int hor=num/3600;
    int min=(num-(3600*hor))/60;
    int seg=num-((hor*3600)+(min*60));
return "§a"+min+"§b:§a"+seg;
}
public CompoundTag nbtdragon(Vector3 pos) {
    CompoundTag nbt = new CompoundTag()
     .putList(new ListTag<>("Pos")
            .add(new DoubleTag("", pos.x))
            .add(new DoubleTag("", pos.y))
            .add(new DoubleTag("", pos.z)))
      .putList(new ListTag<DoubleTag>("Motion")
            .add(new DoubleTag("", 0))
            .add(new DoubleTag("", 0))
            .add(new DoubleTag("", 0)))
     .putList(new ListTag<FloatTag>("Rotation")
            .add(new FloatTag("", (float) 0))
            .add(new FloatTag("", (float) 0)))
     .putBoolean("Invulnerable", true)
     .putString("NameTag", "destructor")
     .putFloat("scale", 7);    
     return nbt;

}
public Boolean voteOpPermisson(Player player){
	if(player.isOp()){
		return true;
	}else{
		return false;
	}
}

//lucky death
public String getNameKill(int vida,String dueño){
	String name = "";
	String title = "§l§7RIP.§e"+dueño+"\n";
	if(vida >=5){
		name = title+"§l§a▇▇▇▇▇";
	}
	if(vida ==4){
		name = title+"§l§a▇▇▇▇§c▇";
	}
	if(vida ==3){
		name = title+"§l§a▇▇▇§c▇▇";
	}
	if(vida ==2){
		name = title+"§l§a▇▇§c▇▇▇";
	}
	if(vida ==1){
		name = title+"§l§a▇§c▇▇▇▇";
	}
	if(vida ==0){
		name = title+"§l§c▇▇▇▇▇";
	}
	return name;
}

public void LuckyItem(Vector3 vector, Level level, Vector3 motion){

	int numero = (int) (Math.random() * 5);
	if(numero == 5){
		level.dropExpOrb(vector, 2);
		level.dropItem(motion, Item.get(258,0,1));
	}
	if(numero == 4){
		level.dropExpOrb(vector, 2);
		level.dropItem(motion, Item.get(267,0,1));
	}
	if(numero == 3){
		level.dropExpOrb(vector, 2);
		level.dropItem(motion, Item.get(264,0,3));
	}
	if(numero == 2){
		level.dropExpOrb(vector, 2);
		level.dropItem(motion, Item.get(310,0,1));
	}
	if(numero == 1){
		level.dropExpOrb(vector, 2);
		level.dropItem(motion, Item.get(322,0,4));
	}
	if(numero == 0){
		level.dropExpOrb(vector, 2);
		level.dropItem(motion, Item.get(320,0,5));
	}
}


}
