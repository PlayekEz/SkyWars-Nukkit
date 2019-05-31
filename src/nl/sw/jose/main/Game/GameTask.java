package nl.sw.jose.main.Game;
import java.io.File;
import nl.sw.jose.main.Game.ChestRefill;
import nl.sw.jose.main.Game.GameMode;
import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.entity.Entity;
import cn.nukkit.level.Level;
import cn.nukkit.level.Sound;
import cn.nukkit.math.BlockFace;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.DoubleTag;
import cn.nukkit.nbt.tag.FloatTag;
import cn.nukkit.nbt.tag.ListTag;
import cn.nukkit.network.protocol.ChangeDimensionPacket;
import cn.nukkit.network.protocol.LevelSoundEventPacket;
import cn.nukkit.utils.Config;
import nl.sw.jose.main.SkyWars;
import nl.sw.jose.main.lobby;
import nl.sw.jose.main.Entity.DragonEntity;
import nl.sw.jose.main.Boss;
import nl.sw.jose.main.Kits;
import nl.sw.jose.main.Settings;
import nl.sw.jose.main.ResetMap;
import nl.sw.jose.main.Game.RemoveIsla;
public class GameTask extends cn.nukkit.scheduler.Task{
private SkyWars sw;
public GameTask(SkyWars plugin){
	this.sw = plugin;
}
@Override
public void onRun(int tick) {
	if(sw.countArchivos() > 0){
	String[] games =  new File(sw.getDataFolder()+"/Arenas/").list();
	for(String configs : games){
		Config arena = new Config(sw.getDataFolder()+"/Arenas/"+configs,Config.YAML);
		Level levelArena = sw.getServer().getLevelByName(arena.getString("level"));
		ChestRefill chest = new ChestRefill();
		if(levelArena.getPlayers().size() == 0){
			arena.set("status", "on");
			arena.set("time", 60*8);
			arena.set("start", 30);
			arena.save();
		}
		if(levelArena.getPlayers().size() == 1 && arena.getString("status").equals("on")){
			arena.set("status", "on");
			arena.set("time", 60*8);
			arena.set("start", 30);
			arena.save();
		}
		if(levelArena.getPlayers().size() >= 2 && arena.getInt("start") > 0){
			int time = arena.getInt("start");
			time--;
			arena.set("start", time);
			arena.save();
			
			if(time == 10){
				arena.set("status", "off");
				arena.save();
				for(Player p : levelArena.getPlayers().values()){
					p.getInventory().clearAll();
					new GameMode().getMode(sw.getDataFolder(), configs, p);
					if(arena.getString("slot1").equals(p.getName())){
					p.teleport(new Vector3(arena.getDouble("xslot1"),arena.getDouble("yslot1"),arena.getDouble("zslot1")));
					}else{
						if(arena.getString("slot2").equals(p.getName())){
							p.teleport(new Vector3(arena.getDouble("xslot2"),arena.getDouble("yslot2"),arena.getDouble("zslot2")));
						}else{
							if(arena.getString("slot3").equals(p.getName())){
								p.teleport(new Vector3(arena.getDouble("xslot3"),arena.getDouble("yslot3"),arena.getDouble("zslot3")));
							}else{
								if(arena.getString("slot4").equals(p.getName())){
									p.teleport(new Vector3(arena.getDouble("xslot4"),arena.getDouble("yslot4"),arena.getDouble("zslot4")));
								}	
							}
						}
	
					}
					
				
				}
new RemoveIsla().remove(new Vector3(arena.getInt("xpos1lobby"),arena.getInt("ypos1lobby"),arena.getInt("zpos1lobby")), new Vector3(arena.getInt("xpos2lobby"),arena.getInt("ypos2lobby"),arena.getInt("zpos2lobby")), sw.getServer().getLevelByName(arena.getString("level")));
			}
			if(time == 9){
				for(Player p : levelArena.getPlayers().values()){
					new GameMode().getMode(sw.getDataFolder(), configs, p);
				}
			}
			if(time == 8){
				for(Player p : levelArena.getPlayers().values()){
					new GameMode().getMode(sw.getDataFolder(), configs, p);
				}
			}
			if(time == 8){
				for(Player p : levelArena.getPlayers().values()){
					p.sendTitle("§l§eSkyWars§r", "§7Look for resources");
				}
			}
			if(time == 7){
				for(Player p : levelArena.getPlayers().values()){
					p.sendTitle("§l§eSkyWars§r", "§cReady to fight!");
				}
			}
			if(time == 6){
				for(Player p : levelArena.getPlayers().values()){
					p.sendTitle("§l§eSkyWars§r", "§cReady to fight!");
				}
			}
			if(time <= 10){
				for(Player p : levelArena.getPlayers().values()){
					p.setImmobile(true);
				}
			}
			if(time < 6){
				if(time >0){
				for(Player p : levelArena.getPlayers().values()){
					 LevelSoundEventPacket pk = new LevelSoundEventPacket();
	      				pk.sound = LevelSoundEventPacket.SOUND_NOTE;
	      				pk.x = (int)p.x;
	      				pk.y = (int)p.y;
	      				pk.z = (int)p.z;
	      				pk.extraData = 0;
	      				pk.entityIdentifier = ":";
	      				pk.isBabyMob = false;
	      				pk.isGlobal = true;
	      				p.dataPacket(pk);
					p.sendTitle("§b"+time);
					
				}
				}
			}
			if(time == 0){
				chest.clear(levelArena);
				chest.refillChest(sw.getDataFolder(), configs, levelArena);
				for(Player p : levelArena.getPlayers().values()){
					 LevelSoundEventPacket pk = new LevelSoundEventPacket();
	      				pk.sound = LevelSoundEventPacket.SOUND_NOTE;
	      				pk.x = (int)p.x;
	      				pk.y = (int)p.y;
	      				pk.z = (int)p.z;
	      				pk.extraData = 25;
	      				pk.entityIdentifier = ":";
	      				pk.isBabyMob = false;
	      				pk.isGlobal = true;
	      				p.dataPacket(pk);
	      		Vector3 pos = new Vector3(p.x,p.y,p.z);
	      		levelArena.setBlock(pos, Block.get(0,0));
	      		for(int i = 0; i <5; i++){
	      			levelArena.setBlock(pos, Block.get(0,0));
	      			BlockFace[] array = {BlockFace.NORTH,BlockFace.SOUTH,BlockFace.WEST,BlockFace.EAST};
	      			Vector3 lado = pos.add(0, i);
	      			levelArena.setBlock(new Vector3(lado.x, lado.y, lado.z), Block.get(0, 0));
	   
	      			for(BlockFace direccion : array){
	      					Vector3 lado2 = lado.getSide(direccion);
	      		levelArena.setBlock(new Vector3(lado2.x,lado2.y,lado2.z), Block.get(0, 0));
	      			}
	      		}
	      		new Kits().addKit(p, sw.getDataFolder());
	      		p.setImmobile(false);
	      		p.setGamemode(0);
				}
			}
		}
if(arena.getString("status").equals("off") && arena.getInt("start") == 0){
	int time = arena.getInt("time");
	time--;
	arena.set("time", time);
	arena.save();
	if(this.countPlayersGM(configs) == 1 && arena.getString("status").equals("off") && arena.getInt("start") == 0){
	arena.set("status", "reset");
	arena.save();
	String ganador = "";
	for(Player ar: levelArena.getPlayers().values()){
		ar.getInventory().clearAll();
		if(ar.getGamemode() == 0){
			ganador = ar.getName();
		}else{
			ar.sendTitle("§l§cGame Over", "§7More luck the next");
		}
	Player arganador = sw.getServer().getPlayer(ganador);
	arganador.sendTitle("§l§6Victory", "§7You are very skillful");
	levelArena.addSound(new Vector3(ar.x,ar.y,ar.z), Sound.MOB_ENDERDRAGON_GROWL);
	}
	
	}
	if(this.countPlayersGM(configs) == 0 && arena.getString("status").equals("off") && arena.getInt("start") == 0){
		for(Player p : levelArena.getPlayers().values()){
			p.teleport(sw.getServer().getDefaultLevel().getSafeSpawn());
			Boss.removeBossBar(p, arena.getInt("boss"));
			p.getInventory().clearAll();
			p.setGamemode(2);
			p.setExperience(0, 0);
			p.sendTitle("§l§cGame Over", "§7More luck the next");
			ChangeDimensionPacket dimension = new ChangeDimensionPacket();
			dimension.x = (int)p.x;
			dimension.y = (int)p.y+1;
			dimension.z = (int)p.z;
		        dimension.dimension = 1;
		p.dataPacket(dimension);
		p.teleport(sw.getServer().getLevelByName("debug").getSafeSpawn());
		sw.getServer().getScheduler().scheduleRepeatingTask(new lobby(sw,p), 10);
		}
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
		this.resetmap(configs);	
	}
	if(time == 60*8-1){
		for(Player p : levelArena.getPlayers().values()){
			p.sendMessage(Settings.title+"El juego ha comenzado buena suerte!");
			p.sendTitle("");
		}
	}
	if(time == 60*4){
		chest.clear(levelArena);
		chest.refillChest(sw.getDataFolder(), configs, levelArena);
		for(Player p : levelArena.getPlayers().values()){
			p.sendMessage(Settings.title+"§6Los cofres se han rellenado!");
			levelArena.addSound(new Vector3(p.x,p.y,p.z), Sound.MOB_BLAZE_HIT);
		}
	}
if(time == 60*2+10){
	for(Player p : levelArena.getPlayers().values()){
		p.sendMessage(Settings.title+"§6Destructor §eha sido spawneado §b10 §esegundos para la destruccion§c!");
		levelArena.addSound(new Vector3(p.x,p.y,p.z), Sound.AMBIENT_WEATHER_THUNDER);
	
	 CompoundTag nbt = sw.nbtdragon(new Vector3(arena.getInt("xspec"),arena.getInt("yspec"),arena.getInt("zspec")));
	 Entity ent = Entity.createEntity("DragonEntity", p.chunk, nbt);
	 ent.setNameTag("§l§6Destructor");
	 ent.spawnTo(p);
	}
	

	}


	if(time == 60*2){
		for(Player p : levelArena.getPlayers().values()){
			p.sendMessage(Settings.title+"§cIsla §e1 §celiminada");
			levelArena.addSound(new Vector3(p.x,p.y,p.z), Sound.AMBIENT_WEATHER_THUNDER);
		}
		for(Entity ent : levelArena.getEntities()){
			if(ent instanceof DragonEntity){
				ent.setPosition(new Vector3(arena.getInt("xisla1pos1"),arena.getInt("yisla1pos1"),arena.getInt("zisla1pos1")));
			}
		}
		new RemoveIsla().remove(new Vector3(arena.getInt("xisla1pos1"),arena.getInt("yisla1pos1"),arena.getInt("zisla1pos1")), new Vector3(arena.getInt("xisla1pos2"),arena.getInt("yisla1pos2"),arena.getInt("zisla1pos2")), sw.getServer().getLevelByName(arena.getString("level")));
	}
	if(time == 60*2-2){
		for(Entity ent : levelArena.getEntities()){
			if(ent instanceof DragonEntity){
				ent.setPosition(new Vector3(arena.getInt("xspec"),arena.getInt("yspec"),arena.getInt("zspec")));
			}
		}	
	}
if(time == 60*2-20){
	for(Player p : levelArena.getPlayers().values()){
		p.sendMessage(Settings.title+"§cIsla §e2 §celiminada");
		levelArena.addSound(new Vector3(p.x,p.y,p.z), Sound.AMBIENT_WEATHER_THUNDER);
	}
	new RemoveIsla().remove(new Vector3(arena.getInt("xisla2pos1"),arena.getInt("yisla2pos1"),arena.getInt("zisla2pos1")), new Vector3(arena.getInt("xisla2pos2"),arena.getInt("yisla2pos2"),arena.getInt("zisla2pos2")), sw.getServer().getLevelByName(arena.getString("level")));
	for(Entity ent : levelArena.getEntities()){
		if(ent instanceof DragonEntity){
			ent.setPosition(new Vector3(arena.getInt("xisla2pos1"),arena.getInt("yisla2pos1"),arena.getInt("zisla2pos1")));
		}
	}
	
	}
if(time == 60*2-22){
	for(Entity ent : levelArena.getEntities()){
		if(ent instanceof DragonEntity){
			ent.setPosition(new Vector3(arena.getInt("xspec"),arena.getInt("yspec"),arena.getInt("zspec")));
		}
	}
}
if(time == 60*2-40){
	for(Player p : levelArena.getPlayers().values()){
		p.sendMessage(Settings.title+"§cIsla §e3 §celiminada");
		levelArena.addSound(new Vector3(p.x,p.y,p.z), Sound.AMBIENT_WEATHER_THUNDER);
	}
	new RemoveIsla().remove(new Vector3(arena.getInt("xisla3pos1"),arena.getInt("yisla3pos1"),arena.getInt("zisla3pos1")), new Vector3(arena.getInt("xisla3pos2"),arena.getInt("yisla3pos2"),arena.getInt("zisla3pos2")), sw.getServer().getLevelByName(arena.getString("level")));	
	for(Entity ent : levelArena.getEntities()){
		if(ent instanceof DragonEntity){
			ent.setPosition(new Vector3(arena.getInt("xisla3pos1"),arena.getInt("yisla3pos1"),arena.getInt("zisla3pos1")));
		}
	}
}
if(time == 60*2-42){
	for(Entity ent : levelArena.getEntities()){
		if(ent instanceof DragonEntity){
			ent.setPosition(new Vector3(arena.getInt("xspec"),arena.getInt("yspec"),arena.getInt("zspec")));
		}
	}
}
if(time == 60*2-60){
	for(Player p : levelArena.getPlayers().values()){
		p.sendMessage(Settings.title+"§cIsla §e4 §celiminada");
		levelArena.addSound(new Vector3(p.x,p.y,p.z), Sound.AMBIENT_WEATHER_THUNDER);
	}
	new RemoveIsla().remove(new Vector3(arena.getInt("xisla4pos1"),arena.getInt("yisla4pos1"),arena.getInt("zisla4pos1")), new Vector3(arena.getInt("xisla4pos2"),arena.getInt("yisla4pos2"),arena.getInt("zisla4pos2")), sw.getServer().getLevelByName(arena.getString("level")));	
	for(Entity ent : levelArena.getEntities()){
		if(ent instanceof DragonEntity){
			ent.setPosition(new Vector3(arena.getInt("xisla4pos1"),arena.getInt("yisla4pos1"),arena.getInt("zisla4pos1")));
		}
	}
}
if(time == 60*2-61){
	for(Entity ent : levelArena.getEntities()){
		if(ent instanceof DragonEntity){
ent.close();
}
	}	
}
if(time == 1){
	for(Player p : levelArena.getPlayers().values()){
		p.teleport(sw.getServer().getDefaultLevel().getSafeSpawn());
		Boss.removeBossBar(p, arena.getInt("boss"));
		p.getInventory().clearAll();
		p.setGamemode(2);
		p.setExperience(0);
		p.setExperience(0, 0);
		p.sendTitle("§l§cGame Over", "§7More luck the next");
		ChangeDimensionPacket dimension = new ChangeDimensionPacket();
		dimension.x = (int)p.x;
		dimension.y = (int)p.y+1;
		dimension.z = (int)p.z;
	        dimension.dimension = 1;
	p.dataPacket(dimension);
	p.teleport(sw.getServer().getLevelByName("debug").getSafeSpawn());
	sw.getServer().getScheduler().scheduleRepeatingTask(new lobby(sw,p), 10);
	}
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
	this.resetmap(configs);
}
		
}
if(arena.getString("status").equals("reset") && arena.getInt("start") == 0){
	int time = arena.getInt("reset");
	time--;
	arena.set("reset", time);
	arena.save();
	if(arena.getInt("reset") > 1){
	for(Player al: levelArena.getPlayers().values()){
		if(al.getGamemode() == 0){
			 CompoundTag nbt = this.fallingblocknbt(al);
			 Entity ent = Entity.createEntity("FullBlock", al.chunk, nbt);
			 ent.spawnTo(al);
			 Entity ent2 = Entity.createEntity("FullBlock", al.chunk, nbt);
			 ent2.spawnTo(al);
		
		}
		
	}
	}
	if(time == 0){
		for(Player ar: levelArena.getPlayers().values()){
		ar.teleport(sw.getServer().getDefaultLevel().getSafeSpawn());
		Boss.removeBossBar(ar, arena.getInt("boss"));
		ar.getInventory().clearAll();
		ar.setExperience(0);
		ar.setExperience(0, 0);
		ar.setGamemode(2);
		ChangeDimensionPacket dimension = new ChangeDimensionPacket();
		dimension.x = (int)ar.x;
		dimension.y = (int)ar.y+1;
		dimension.z = (int)ar.z;
	        dimension.dimension = 1;
	ar.dataPacket(dimension);
	ar.teleport(sw.getServer().getLevelByName("debug").getSafeSpawn());
	sw.getServer().getScheduler().scheduleRepeatingTask(new lobby(sw,ar), 10);
		}
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
		this.resetmap(configs);	
	}
	
	
	
}
		
	}
	}
}
public CompoundTag fallingblocknbt(Player pos) {

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
            .add(new FloatTag("", (float) 0)));
     return nbt;

}
public int countPlayersGM(String name){
	int i = 0;
	Config arena = new Config(sw.getDataFolder()+"/Arenas/"+name,Config.YAML);
	for(Player p :sw.getServer().getLevelByName(arena.getString("level")).getPlayers().values()){
		if(p.getGamemode() == 0){
			i++;
		}
	}
	return i;
}
public void resetmap(String name){
	String kolu = name.replace(".dat", "");
	sw.getServer().unloadLevel(sw.getServer().getLevelByName(kolu));
	sw.deletePath(kolu);
	new ResetMap().setReset(sw.getDataFolder(), kolu, sw.getServer().getDataPath());
	sw.getServer().loadLevel(kolu);
	sw.getServer().getLevelByName(kolu).setTime(0);
	sw.getServer().getLevelByName(kolu).stopTime();
	sw.getServer().getLevelByName(kolu).setRaining(false);
	sw.getServer().getLevelByName(kolu).setThundering(false);
}


}
