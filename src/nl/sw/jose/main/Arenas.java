package nl.sw.jose.main;
import java.io.File;

import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.event.player.PlayerMoveEvent;
import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.data.IntPositionEntityData;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.entity.EntityDamageEvent.DamageCause;
import cn.nukkit.event.player.PlayerDropItemEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.item.Item;
import cn.nukkit.level.Sound;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.network.protocol.ChangeDimensionPacket;
import cn.nukkit.network.protocol.LevelSoundEventPacket;
import cn.nukkit.utils.Config;
import nl.sw.jose.main.Entity.ActionKill;
import nl.sw.jose.main.Entity.Murder;
import nl.sw.jose.main.SkyWars;
public class Arenas implements Listener{
private SkyWars sw;

public Arenas(SkyWars plugin){
	this.sw = plugin;
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
public void morir(Player p, Config arena){
	p.setHealth(20);
p.teleport(new Vector3(arena.getInt("xspec"),arena.getInt("yspec"),arena.getInt("zspec")));
	p.getInventory().clearAll();
	p.setGamemode(3);
	p.setHealth(20);
	sw.setFood(p, 20);	
	p.sendTitle("§l§cDeath", "§7Now you are a spectator");	
}
@EventHandler
public void atacar(EntityDamageEvent  evento){
	boolean asesino = false;
	Entity player = (Entity)evento.getEntity();
	if(player instanceof Player){
	String[] files = new File(sw.getDataFolder()+"/Arenas/").list();
	for(String w : files){
		Config arena = new Config(sw.getDataFolder()+"/Arenas/"+w,Config.YAML);
		if(evento.getEntity().getLevel() == sw.getServer().getLevelByName(arena.getString("level"))){
    if(evento.getFinalDamage() >= player.getHealth()){
    	evento.setCancelled();
    	asesino = true;
    }
    
    
    if(asesino == true){
    	if(evento instanceof EntityDamageByEntityEvent){
    Player golpeador = (Player) ((EntityDamageByEntityEvent) evento).getDamager();
    if(evento.getCause() == DamageCause.PROJECTILE){
    	 if(player instanceof Player){
    	 for(Player d :sw.getServer().getLevelByName(arena.getString("level")).getPlayers().values()){
     		   d.sendMessage(Settings.title+"§c"+player.getName()+" §ele impacto un proyectil by §c"+golpeador.getName()); 
     		   d.sendTip("§e"+this.countPlayersGM(w)+" §arestantes");
     		   sw.getServer().getLevelByName(arena.getString("level")).addSound(new Vector3(d.x,d.y,d.z), Sound.MOB_CAT_HIT);
     		     }
     	  
     	 
     		  CompoundTag nbt = sw.nbt((Player) player, "murder");
     		 Entity ent = Entity.createEntity("Murder",player.chunk, nbt);
     		 ent.setNameTag(sw.getNameKill(5, player.getName()));
     		 ent.setNameTagAlwaysVisible(true);
     	    ent.setNameTagVisible(true);
     	    ent.setDataProperty(new IntPositionEntityData(Murder.DATA_PLAYER_BED_POSITION, (int) player.x, (int) player.y, (int) player.z));
     	    ent.setDataFlag(Murder.DATA_PLAYER_FLAGS, Murder.DATA_PLAYER_FLAG_SLEEP, true);
     	    for(Player death : sw.getServer().getLevelByName(arena.getString("level")).getPlayers().values()){
     	   	 ent.spawnTo(death);
     	   	
     	    }
     	    sw.getServer().getScheduler().scheduleRepeatingTask(new ActionKill(sw,ent,player.getName()), 10);
     	   this.morir((Player) player, arena);
     	   }
    }else{
    for(Player d :sw.getServer().getLevelByName(arena.getString("level")).getPlayers().values()){
  d.sendMessage(Settings.title+"§c"+player.getName()+" §emurio a manos de §c"+golpeador.getName()); 
  d.sendTip("§e"+this.countPlayersGM(w)+" §arestantes");
  sw.getServer().getLevelByName(arena.getString("level")).addSound(new Vector3(d.x,d.y,d.z), Sound.MOB_CAT_HIT);
    }
   
    CompoundTag nbt = sw.nbt((Player) player, "murder");
	 Entity ent = Entity.createEntity("Murder",player.chunk, nbt);
	 ent.setNameTag(sw.getNameKill(5, player.getName()));
	 ent.setNameTagAlwaysVisible(true);
    ent.setNameTagVisible(true);
    ent.setDataProperty(new IntPositionEntityData(Murder.DATA_PLAYER_BED_POSITION, (int) player.x, (int) player.y, (int) player.z));
    ent.setDataFlag(Murder.DATA_PLAYER_FLAGS, Murder.DATA_PLAYER_FLAG_SLEEP, true);
    for(Player death : sw.getServer().getLevelByName(arena.getString("level")).getPlayers().values()){
   	 ent.spawnTo(death);
   	
    }
    sw.getServer().getScheduler().scheduleRepeatingTask(new ActionKill(sw,ent,player.getName()), 10);
    this.morir((Player) player, arena);
    }
    }
    	if(evento.getCause() == DamageCause.LAVA){
    		
    	   	 for(Player d :sw.getServer().getLevelByName(arena.getString("level")).getPlayers().values()){
    	   		   d.sendMessage(Settings.title+"§c"+player.getName()+" §ese quemo");
    	   		   d.sendTip("§e"+this.countPlayersGM(w)+" §arestantes");
    	   		   sw.getServer().getLevelByName(arena.getString("level")).addSound(new Vector3(d.x,d.y,d.z), Sound.MOB_CAT_HIT);
    	   		     }
    	   	 
    	   		 
    	   	CompoundTag nbt = sw.nbt((Player) player, "murder");
         		 Entity ent = Entity.createEntity("Murder",player.chunk, nbt);
         		 ent.setNameTag(sw.getNameKill(5, player.getName()));
         		 ent.setNameTagAlwaysVisible(true);
         	    ent.setNameTagVisible(true);
         	    ent.setDataProperty(new IntPositionEntityData(Murder.DATA_PLAYER_BED_POSITION, (int) player.x, (int) player.y, (int) player.z));
         	    ent.setDataFlag(Murder.DATA_PLAYER_FLAGS, Murder.DATA_PLAYER_FLAG_SLEEP, true);
         	    for(Player death : sw.getServer().getLevelByName(arena.getString("level")).getPlayers().values()){
         	   	 ent.spawnTo(death);
         	   	
         	    }
         	    sw.getServer().getScheduler().scheduleRepeatingTask(new ActionKill(sw,ent,player.getName()), 10);
         	   this.morir((Player) player, arena);
    	    }
    	       if(evento.getCause() == DamageCause.HUNGER){
    	    	  
    	       	   for(Player d :sw.getServer().getLevelByName(arena.getString("level")).getPlayers().values()){
    	       		   d.sendMessage(Settings.title+"§c"+player.getName()+" §emurio de hambre"); 
    	       		   d.sendTip("§e"+this.countPlayersGM(w)+" §arestantes");
    	       		   sw.getServer().getLevelByName(arena.getString("level")).addSound(new Vector3(d.x,d.y,d.z), Sound.MOB_CAT_HIT);
    	       		     }
    	       	  
    	       		   
    	       		 CompoundTag nbt = sw.nbt( (Player) player, "murder");
    	     		 Entity ent = Entity.createEntity("Murder",player.chunk, nbt);
    	     		 ent.setNameTag(sw.getNameKill(5, player.getName()));
    	     		 ent.setNameTagAlwaysVisible(true);
    	     	    ent.setNameTagVisible(true);
    	     	    ent.setDataProperty(new IntPositionEntityData(Murder.DATA_PLAYER_BED_POSITION, (int) player.x, (int) player.y, (int) player.z));
    	     	    ent.setDataFlag(Murder.DATA_PLAYER_FLAGS, Murder.DATA_PLAYER_FLAG_SLEEP, true);
    	     	    for(Player death : sw.getServer().getLevelByName(arena.getString("level")).getPlayers().values()){
    	     	   	 ent.spawnTo(death);
    	     	   	
    	     	    }
    	     	    sw.getServer().getScheduler().scheduleRepeatingTask(new ActionKill(sw,ent,player.getName()), 10);
    	     	   this.morir((Player) player, arena);
    	       	   
    	       }
    	          if(evento.getCause() == DamageCause.LIGHTNING){
    	        	 
    	       	   for(Player d :sw.getServer().getLevelByName(arena.getString("level")).getPlayers().values()){
    	       		   d.sendMessage(Settings.title+"§c"+player.getName()+" §ele cayo un trueno"); 
    	       		   d.sendTip("§e"+this.countPlayersGM(w)+" §arestantes");
    	       		   sw.getServer().getLevelByName(arena.getString("level")).addSound(new Vector3(d.x,d.y,d.z), Sound.MOB_CAT_HIT);
    	       		     }
    	       	  
    	       		  
    	       		 CompoundTag nbt = sw.nbt((Player) player, "murder");
    	     		 Entity ent = Entity.createEntity("Murder",player.chunk, nbt);
    	     		 ent.setNameTag(sw.getNameKill(5, player.getName()));
    	     		 ent.setNameTagAlwaysVisible(true);
    	     	    ent.setNameTagVisible(true);
    	     	    ent.setDataProperty(new IntPositionEntityData(Murder.DATA_PLAYER_BED_POSITION, (int) player.x, (int) player.y, (int) player.z));
    	     	    ent.setDataFlag(Murder.DATA_PLAYER_FLAGS, Murder.DATA_PLAYER_FLAG_SLEEP, true);
    	     	    for(Player death : sw.getServer().getLevelByName(arena.getString("level")).getPlayers().values()){
    	     	   	 ent.spawnTo(death);
    	     	   	
    	     	    }
    	     	    sw.getServer().getScheduler().scheduleRepeatingTask(new ActionKill(sw,ent,player.getName()), 10);
    	     	   this.morir((Player) player, arena);
    	       	   
    	       }
    	          if(evento.getCause() == DamageCause.ENTITY_EXPLOSION){
    	        	 
    	       	   for(Player d :sw.getServer().getLevelByName(arena.getString("level")).getPlayers().values()){
    	       		   d.sendMessage(Settings.title+"§c"+player.getName()+" §eexploto en mil partes");  
    	       		   d.sendTip("§e"+this.countPlayersGM(w)+" §arestantes");
    	       		   sw.getServer().getLevelByName(arena.getString("level")).addSound(new Vector3(d.x,d.y,d.z), Sound.MOB_CAT_HIT);
    	       		     }
    	       	  
    	       		 
    	       		 CompoundTag nbt = sw.nbt((Player) player, "murder");
    	     		 Entity ent = Entity.createEntity("Murder",player.chunk, nbt);
    	     		 ent.setNameTag(sw.getNameKill(5, player.getName()));
    	     		 ent.setNameTagAlwaysVisible(true);
    	     	    ent.setNameTagVisible(true);
    	     	    ent.setDataProperty(new IntPositionEntityData(Murder.DATA_PLAYER_BED_POSITION, (int) player.x, (int) player.y, (int) player.z));
    	     	    ent.setDataFlag(Murder.DATA_PLAYER_FLAGS, Murder.DATA_PLAYER_FLAG_SLEEP, true);
    	     	    for(Player death : sw.getServer().getLevelByName(arena.getString("level")).getPlayers().values()){
    	     	   	 ent.spawnTo(death);
    	     	   	
    	     	    }
    	     	    sw.getServer().getScheduler().scheduleRepeatingTask(new ActionKill(sw,ent,player.getName()), 10);
    	     	   this.morir((Player) player, arena);
    	       	   
    	          }
    	          if(evento.getCause() == DamageCause.FALL){
    	        	 
    	       	   for(Player d :sw.getServer().getLevelByName(arena.getString("level")).getPlayers().values()){
    	       		   d.sendMessage(Settings.title+"§c"+player.getName()+" §eintento volar"); 
    	       		   d.sendTip("§e"+this.countPlayersGM(w)+" §arestantes");
    	       		   sw.getServer().getLevelByName(arena.getString("level")).addSound(new Vector3(d.x,d.y,d.z), Sound.MOB_CAT_HIT);
    	       		     }
    	       	   
    	       		
    	       		 CompoundTag nbt = sw.nbt((Player) player, "murder");
    	     		 Entity ent = Entity.createEntity("Murder",player.chunk, nbt);
    	     		 ent.setNameTag(sw.getNameKill(5, player.getName()));
    	     		 ent.setNameTagAlwaysVisible(true);
    	     	    ent.setNameTagVisible(true);
    	     	    ent.setDataProperty(new IntPositionEntityData(Murder.DATA_PLAYER_BED_POSITION, (int) player.x, (int) player.y, (int) player.z));
    	     	    ent.setDataFlag(Murder.DATA_PLAYER_FLAGS, Murder.DATA_PLAYER_FLAG_SLEEP, true);
    	     	    for(Player death : sw.getServer().getLevelByName(arena.getString("level")).getPlayers().values()){
    	     	   	 ent.spawnTo(death);
    	     	   	
    	     	    }
    	     	    sw.getServer().getScheduler().scheduleRepeatingTask(new ActionKill(sw,ent,player.getName()), 10);
    	     	   this.morir((Player) player, arena);
    	          }
    	          if(evento.getCause() == DamageCause.FIRE){
    	        	 
    	       	   for(Player d :sw.getServer().getLevelByName(arena.getString("level")).getPlayers().values()){
    	       		   d.sendMessage(Settings.title+"§c"+player.getName()+" §ese calento de mas");  
    	       		   d.sendTip("§e"+this.countPlayersGM(w)+" §arestantes");
    	       		   sw.getServer().getLevelByName(arena.getString("level")).addSound(new Vector3(d.x,d.y,d.z), Sound.MOB_CAT_HIT);
    	       		     }
    	       	 
    	       		  
    	       		 CompoundTag nbt = sw.nbt((Player) player, "murder");
    	     		 Entity ent = Entity.createEntity("Murder",player.chunk, nbt);
    	     		 ent.setNameTag(sw.getNameKill(5, player.getName()));
    	     		 ent.setNameTagAlwaysVisible(true);
    	     	    ent.setNameTagVisible(true);
    	     	    ent.setDataProperty(new IntPositionEntityData(Murder.DATA_PLAYER_BED_POSITION, (int) player.x, (int) player.y, (int) player.z));
    	     	    ent.setDataFlag(Murder.DATA_PLAYER_FLAGS, Murder.DATA_PLAYER_FLAG_SLEEP, true);
    	     	    for(Player death : sw.getServer().getLevelByName(arena.getString("level")).getPlayers().values()){
    	     	   	 ent.spawnTo(death);
    	     	   	
    	     	    }
    	     	    sw.getServer().getScheduler().scheduleRepeatingTask(new ActionKill(sw,ent,player.getName()), 10);
    	     	   this.morir( (Player) player, arena);  
    	       	   
    	          }
    	          if(evento.getCause() == DamageCause.MAGIC){
    	        	
    	       	   for(Player d :sw.getServer().getLevelByName(arena.getString("level")).getPlayers().values()){
    	       		   d.sendMessage(Settings.title+"§c"+player.getName()+" §emuro por jugar al mago"); 
    	       		   d.sendTip("§e"+this.countPlayersGM(w)+" §arestantes");
    	       		   sw.getServer().getLevelByName(arena.getString("level")).addSound(new Vector3(d.x,d.y,d.z), Sound.MOB_CAT_HIT);
    	       		     }
    	       	 
    	       		 
    	       		 CompoundTag nbt = sw.nbt((Player) player, "murder");
    	     		 Entity ent = Entity.createEntity("Murder",player.chunk, nbt);
    	     		 ent.setNameTag(sw.getNameKill(5, player.getName()));
    	     		 ent.setNameTagAlwaysVisible(true);
    	     	    ent.setNameTagVisible(true);
    	     	    ent.setDataProperty(new IntPositionEntityData(Murder.DATA_PLAYER_BED_POSITION, (int) player.x, (int) player.y, (int) player.z));
    	     	    ent.setDataFlag(Murder.DATA_PLAYER_FLAGS, Murder.DATA_PLAYER_FLAG_SLEEP, true);
    	     	    for(Player death : sw.getServer().getLevelByName(arena.getString("level")).getPlayers().values()){
    	     	   	 ent.spawnTo(death);
    	     	   	
    	     	    }
    	     	    sw.getServer().getScheduler().scheduleRepeatingTask(new ActionKill(sw,ent,player.getName()), 10);
    	     	   this.morir((Player) player, arena);	
    	          }
    	        
    	          if(evento.getCause() == DamageCause.SUICIDE){
    	        	 
    	       	   for(Player d :sw.getServer().getLevelByName(arena.getString("level")).getPlayers().values()){
    	       		   d.sendMessage(Settings.title+"§c"+player.getName()+" §ese suicido");  
    	       		   d.sendTip("§e"+this.countPlayersGM(w)+" §arestantes");
    	       		   sw.getServer().getLevelByName(arena.getString("level")).addSound(new Vector3(d.x,d.y,d.z), Sound.MOB_CAT_HIT);
    	       		     }
    	       	   
    	       		  
    	       		 CompoundTag nbt = sw.nbt((Player) player, "murder");
    	     		 Entity ent = Entity.createEntity("Murder",player.chunk, nbt);
    	     		 ent.setNameTag(sw.getNameKill(5, player.getName()));
    	     		 ent.setNameTagAlwaysVisible(true);
    	     	    ent.setNameTagVisible(true);
    	     	    ent.setDataProperty(new IntPositionEntityData(Murder.DATA_PLAYER_BED_POSITION, (int) player.x, (int) player.y, (int) player.z));
    	     	    ent.setDataFlag(Murder.DATA_PLAYER_FLAGS, Murder.DATA_PLAYER_FLAG_SLEEP, true);
    	     	    for(Player death : sw.getServer().getLevelByName(arena.getString("level")).getPlayers().values()){
    	     	   	 ent.spawnTo(death);
    	     	   	
    	     	    }
    	     	    sw.getServer().getScheduler().scheduleRepeatingTask(new ActionKill(sw,ent,player.getName()), 10);
    	     	   this.morir((Player) player, arena);	
    	          }
    	          if(evento.getCause() == DamageCause.DROWNING){
    
    	       	   for(Player d :sw.getServer().getLevelByName(arena.getString("level")).getPlayers().values()){
    	       		   d.sendMessage(Settings.title+"§c"+player.getName()+" §eno sabi nadar"); 
    	       		   sw.getServer().getLevelByName(arena.getString("level")).addSound(new Vector3(d.x,d.y,d.z), Sound.MOB_CAT_HIT);
    	       		   d.sendTip("§e"+this.countPlayersGM(w)+" §arestantes");
    	       		     }
    	       	
    	       		  
    	       		 CompoundTag nbt = sw.nbt((Player) player, "murder");
    	     		 Entity ent = Entity.createEntity("Murder",player.chunk, nbt);
    	     		 ent.setNameTag(sw.getNameKill(5, player.getName()));
    	     		 ent.setNameTagAlwaysVisible(true);
    	     	    ent.setNameTagVisible(true);
    	     	    ent.setDataProperty(new IntPositionEntityData(Murder.DATA_PLAYER_BED_POSITION, (int) player.x, (int) player.y, (int) player.z));
    	     	    ent.setDataFlag(Murder.DATA_PLAYER_FLAGS, Murder.DATA_PLAYER_FLAG_SLEEP, true);
    	     	    for(Player death : sw.getServer().getLevelByName(arena.getString("level")).getPlayers().values()){
    	     	   	 ent.spawnTo(death);
    	     	   	
    	     	    }
    	     	    sw.getServer().getScheduler().scheduleRepeatingTask(new ActionKill(sw,ent,player.getName()), 10);
    	     	   this.morir((Player) player, arena);
    	          }	
    	          if(evento.getCause() == DamageCause.BLOCK_EXPLOSION){
    	        	  
       	       	   for(Player d :sw.getServer().getLevelByName(arena.getString("level")).getPlayers().values()){
       	       		   d.sendMessage(Settings.title+"§c"+player.getName()+" §eexploto"); 
       	       		   sw.getServer().getLevelByName(arena.getString("level")).addSound(new Vector3(d.x,d.y,d.z), Sound.MOB_CAT_HIT);
       	       		   d.sendTip("§e"+this.countPlayersGM(w)+" §arestantes");
       	       		     }
       	       	 
       	       		  
       	       	 CompoundTag nbt = sw.nbt((Player) player, "murder");
         		 Entity ent = Entity.createEntity("Murder",player.chunk, nbt);
         		 ent.setNameTag(sw.getNameKill(5, player.getName()));
         		 ent.setNameTagAlwaysVisible(true);
         	    ent.setNameTagVisible(true);
         	    ent.setDataProperty(new IntPositionEntityData(Murder.DATA_PLAYER_BED_POSITION, (int) player.x, (int) player.y, (int) player.z));
         	    ent.setDataFlag(Murder.DATA_PLAYER_FLAGS, Murder.DATA_PLAYER_FLAG_SLEEP, true);
         	    for(Player death : sw.getServer().getLevelByName(arena.getString("level")).getPlayers().values()){
         	   	 ent.spawnTo(death);
         	   	
         	    }
         	    sw.getServer().getScheduler().scheduleRepeatingTask(new ActionKill(sw,ent,player.getName()), 10);
         	   this.morir((Player) player, arena);	
       	          }	
    }
    
 
		}
	}
	}
}

@EventHandler
public void minvoid(PlayerMoveEvent event){
	Player player = event.getPlayer();
	String[] files = new File(sw.getDataFolder()+"/Arenas/").list();
	for(String w : files){
		Config arena = new Config(sw.getDataFolder()+"/Arenas/"+w,Config.YAML);
		if(player.getLevel() == sw.getServer().getLevelByName(arena.getString("level"))){
			if(arena.getString("status").equals("off")){
			if(player.getY() <= arena.getInt("ymin")){
				if(player.getGamemode() == 0){
		player.getInventory().clearAll();
					player.setGamemode(3);
					player.sendTitle("§l§cDeath", "§7Now you are a spectator");
		player.teleport(new Vector3(arena.getInt("xspec"),arena.getInt("yspec"),arena.getInt("zspec")));
					for(Player ar : sw.getServer().getLevelByName(arena.getString("level")).getPlayers().values()){
						ar.sendMessage("§c"+player.getName()+"§e murio");
						ar.sendTip("§e"+this.countPlayersGM(w)+" §arestantes");
						
	sw.getServer().getLevelByName(arena.getString("level")).addSound(new Vector3(ar.x,ar.y,ar.z), Sound.MOB_CAT_HIT);
					}	
				}
			
			}
			}
			if(arena.getString("status").equals("reset")){
				if(player.getY() <= arena.getInt("ymin")){
				if(player.getGamemode() == 0){
			player.teleport(new Vector3(arena.getInt("xspec"),arena.getInt("yspec"),arena.getInt("zspec")));	
				}
				}	
			}
		}
	}
	
}
@EventHandler
public void debugjoin(PlayerJoinEvent event){
	Player player = event.getPlayer();
	String[] files = new File(sw.getDataFolder()+"/Arenas/").list();
	for(String w : files){
		Config arena = new Config(sw.getDataFolder()+"/Arenas/"+w,Config.YAML);
		if(player.getLevel() == sw.getServer().getLevelByName(arena.getString("level"))){
		player.teleport(sw.getServer().getDefaultLevel().getSafeSpawn());
		player.getInventory().clearAll();
		player.setGamemode(2);
		}
	}
}
@EventHandler
public void getQuitArena(PlayerQuitEvent event){
	Player player = event.getPlayer();
	String[] files = new File(sw.getDataFolder()+"/Arenas/").list();
	for(String w : files){
		Config arena = new Config(sw.getDataFolder()+"/Arenas/"+w,Config.YAML);
		if(player.getLevel() == sw.getServer().getLevelByName(arena.getString("level"))){
		if(arena.getString("status").equals("on")){
			this.unsetslot(player, w);	
			
		}
		}
	}
}
@EventHandler
public void NoDrop(PlayerDropItemEvent event){
	Player player = event.getPlayer();
	String[] files = new File(sw.getDataFolder()+"/Arenas/").list();
	for(String w : files){
		Config arena = new Config(sw.getDataFolder()+"/Arenas/"+w,Config.YAML);
		if(player.getLevel() == sw.getServer().getLevelByName(arena.getString("level"))){
			if(arena.getInt("start") > 0){
				event.setCancelled(true);
			}else{
				event.setCancelled(false);	
			}
		}
	}
	
}

@EventHandler
public void noDestroy(BlockBreakEvent event){
	Player player = event.getPlayer();
	String[] files = new File(sw.getDataFolder()+"/Arenas/").list();
	for(String w : files){
		Config arena = new Config(sw.getDataFolder()+"/Arenas/"+w,Config.YAML);
		if(player.getLevel() == sw.getServer().getLevelByName(arena.getString("level"))){
			if(arena.getInt("start") > 0){
				event.setCancelled(true);
			}else{
				event.setCancelled(false);	
			}
		}
	}
	}
@EventHandler
public void noPlace(BlockPlaceEvent event){
	Player player = event.getPlayer();
	String[] files = new File(sw.getDataFolder()+"/Arenas/").list();
	for(String w : files){
		Config arena = new Config(sw.getDataFolder()+"/Arenas/"+w,Config.YAML);
		if(player.getLevel() == sw.getServer().getLevelByName(arena.getString("level"))){
			if(arena.getInt("start") > 0){
				event.setCancelled(true);
				
			}else{
				event.setCancelled(false);	
			}
		}
	}
	}
@EventHandler
public void antiPVP(EntityDamageByEntityEvent e){
	Entity player = (Entity)e.getEntity();
	String[] files = new File(sw.getDataFolder()+"/Arenas/").list();
	for(String w : files){
		Config arena = new Config(sw.getDataFolder()+"/Arenas/"+w,Config.YAML);
		if(player.getLevel() == sw.getServer().getLevelByName(arena.getString("level"))){
			if(arena.getInt("start") > 0){
				e.setCancelled(true);
			}else{
				e.setCancelled(false);	
			}	
		}
	}
}


@EventHandler
public void PlayerInteractItems(PlayerInteractEvent event){
	Player player = event.getPlayer();
	String[] files = new File(sw.getDataFolder()+"/Arenas/").list();
	for(String w : files){
		Config arena = new Config(sw.getDataFolder()+"/Arenas/"+w,Config.YAML);
		if(player.getLevel() == sw.getServer().getLevelByName(arena.getString("level"))){
			if(arena.getInt("start") > 0){
				if(event.getItem().getId() == 152){
				//quit	
					this.unsetslot(player, w);
					player.getInventory().clearAll();
					player.setGamemode(2);
					Boss.removeBossBar(player, arena.getInt("boss"));
					ChangeDimensionPacket dimension = new ChangeDimensionPacket();
					dimension.x = (int)player.x;
					dimension.y = (int)player.y+1;
					dimension.z = (int)player.z;
				        dimension.dimension = 1;
				player.dataPacket(dimension);
				player.teleport(sw.getServer().getLevelByName("debug").getSafeSpawn());
				sw.getServer().getScheduler().scheduleRepeatingTask(new lobby(sw,player), 10);
				}
               if(event.getItem().getId() == 120 && event.getItem().getCustomName().equals("§l§eKITS")){
				//kits	
            	   player.getInventory().clearAll();
            	   player.getInventory().setItem(0, Item.get(120,0,1).setCustomName("§l§aDefault"));
            	   player.getInventory().setItem(1, Item.get(120,0,1).setCustomName("§l§aEnchant"));
            	   player.getInventory().setItem(2, Item.get(120,0,1).setCustomName("§l§aArmor"));
            	   player.getInventory().setItem(8, Item.get(120,0,1).setCustomName("§l§eRegresar"));
				}
               if(event.getItem().getId() == 120 && event.getItem().getCustomName().equals("§l§eRegresar")){
            	   player.getInventory().clearAll();
            	   player.getInventory().setItem(8, Item.get(152,0,1).setCustomName("§l§eSALIR"));
            	   player.getInventory().setItem(0, Item.get(120,0,1).setCustomName("§l§eKITS"));
            	   player.getInventory().setItem(4, Item.get(130,0,1).setCustomName("§l§eVOTE OP"));
            	   
               }
               if(event.getItem().getId() == 120 && event.getItem().getCustomName().equals("§l§aDefault")){
            	   new Kits().setKit(player, sw.getDataFolder(), "default");
            	   player.getInventory().clearAll();
            	   player.getInventory().setItem(8, Item.get(152,0,1).setCustomName("§l§eSALIR"));
            	   player.getInventory().setItem(0, Item.get(120,0,1).setCustomName("§l§eKITS"));
            	   player.getInventory().setItem(4, Item.get(130,0,1).setCustomName("§l§eVOTE OP"));
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
               if(event.getItem().getId() == 120 && event.getItem().getCustomName().equals("§l§aEnchant")){
            	   new Kits().setKit(player, sw.getDataFolder(), "poseidon");
            	   player.getInventory().clearAll();
            	   player.getInventory().setItem(8, Item.get(152,0,1).setCustomName("§l§eSALIR"));
            	   player.getInventory().setItem(0, Item.get(120,0,1).setCustomName("§l§eKITS"));
            	   player.getInventory().setItem(4, Item.get(130,0,1).setCustomName("§l§eVOTE OP"));
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
               if(event.getItem().getId() == 120 && event.getItem().getCustomName().equals("§l§aArmor")){
            	   new Kits().setKit(player, sw.getDataFolder(), "armor");
            	   player.getInventory().clearAll();
            	   player.getInventory().setItem(8, Item.get(152,0,1).setCustomName("§l§eSALIR"));
            	   player.getInventory().setItem(0, Item.get(120,0,1).setCustomName("§l§eKITS"));
            	   player.getInventory().setItem(4, Item.get(130,0,1).setCustomName("§l§eVOTE OP"));
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
               if(event.getItem().getId() == 130){
					//voteop
            	   if(sw.voteOpPermisson(player) == true){
            		   if(arena.getString("chest").equals("normal")){
            			   for(Player p: sw.getServer().getLevelByName(arena.getString("level")).getPlayers().values()){
                       		p.sendMessage("§a"+player.getName()+" §bha votado por §l§cInsaneMode");	
                              arena.set("chest", "op");
                               arena.save();
                          
        sw.getServer().getLevelByName(arena.getString("level")).addSound(new Vector3(p.x,p.y,p.z), Sound.MOB_ENDERDRAGON_GROWL);
                       		}   
            		   }else{
            	player.sendTip(Settings.title+"§cInsaneMode§a ya esta establecido");
            	LevelSoundEventPacket pk = new LevelSoundEventPacket();
 				pk.sound = LevelSoundEventPacket.SOUND_BLAST;
 				pk.x = (int)player.x;
 				pk.y = (int)player.y;
 				pk.z = (int)player.z;
 				pk.extraData = 0;
 				pk.entityIdentifier = ":";
 				pk.isBabyMob = false;
 				pk.isGlobal = true;
 				player.dataPacket(pk);
            		   }
            		
            	   }else{
            		   LevelSoundEventPacket pk = new LevelSoundEventPacket();
         				pk.sound = LevelSoundEventPacket.SOUND_BLAST;
         				pk.x = (int)player.x;
         				pk.y = (int)player.y;
         				pk.z = (int)player.z;
         				pk.extraData = 0;
         				pk.entityIdentifier = ":";
         				pk.isBabyMob = false;
         				pk.isGlobal = true;
         				player.dataPacket(pk);
         				player.sendTip(Settings.title+"§7Requieres §bVIP §7para usarlo");   
            	   }
            	  
				}
			}
		}
	}
	}
public void unsetslot(Player player,String id){
	Config game = new Config(sw.getDataFolder()+"/Arenas/"+id,Config.YAML);
	if(game.getString("slot1").equals(player.getName())){
		game.set("slot1", "undefine");
		game.save();
	}else{
		if(game.getString("slot2").equals(player.getName())){
			game.set("slot2", "undefine");
			game.save();
		}else{
			if(game.getString("slot3").equals(player.getName())){
				game.set("slot3", "undefine");
				game.save();
			}else{
				if(game.getString("slot4").equals(player.getName())){
					game.set("slot4", "undefine");
					game.save();
				}
			}
		}
	}
}
}
