package nl.sw.jose.main.Creador;
//import cn.nukkit.math.Vector3;
import cn.nukkit.utils.Config;
import nl.sw.jose.main.Boss;
import cn.nukkit.Player;
import java.io.File;
import nl.sw.jose.main.Settings;
import cn.nukkit.Server;
public class ArenaCreator {
public void ModeEditorLevel(Player player , String level){
	Server.getInstance().loadLevel(level);
	player.teleport(Server.getInstance().getLevelByName(level).getSafeSpawn());
	player.sendMessage(Settings.title+"Te ha sunido a modo editor usa /sw cancel para terminar");
	player.setGamemode(1);
}
	public void createAren(String name,Player player,String ida,String level,File file){
		int id = Integer.parseInt(ida);
	if(new File(file+"/Arenas/sw-"+id+".dat").exists()){
		player.sendMessage(Settings.title+"Este mapa ya existe");
	}else{		
	Config data = new Config(file+"/Arenas/sw-"+id+".dat",Config.YAML);	
	data.set("level", level);	
	data.set("name", name);
	data.set("status", "on");
	data.set("chest", "normal");
	data.set("voteop", 0);
	data.set("time", 60*8);
	data.set("start", 30);
	data.set("reset", 10);
	data.set("boss", Boss.getIDBoss());
	data.set("slot1", "undefine");
	data.set("slot2", "undefine");
	data.set("slot3", "undefine");
	data.set("slot4", "undefine");
	
	data.set("xlobby", 0);
	data.set("ylobby", 0);
	data.set("zlobby", 0);
	
	data.set("xslot1", 0);
	data.set("yslot1", 0);
	data.set("zslot1", 0);
	
	data.set("xslot2", 0);
	data.set("yslot2", 0);
	data.set("zslot2", 0);
	
	data.set("xslot3", 0);
	data.set("yslot3", 0);
	data.set("zslot3", 0);
	
	data.set("xslot4", 0);
	data.set("yslot4", 0);
	data.set("zslot4", 0);
	
	data.set("xspec", 0);
	data.set("yspec", 0);
	data.set("zspec", 0);
	
	data.set("xmin", 0);
	data.set("ymin", 0);
	data.set("zmin", 0);
	
	data.set("xpos1lobby", 0);
	data.set("ypos1lobby", 0);
	data.set("zpos1lobby", 0);
	
	data.set("xpos2lobby", 0);
	data.set("ypos2lobby", 0);
	data.set("zpos2lobby", 0);

	data.set("xisla1pos1", 0);
	data.set("yisla1pos1", 0);
	data.set("zisla1pos1", 0);
	
	data.set("xisla1pos2", 0);
	data.set("yisla1pos2", 0);
	data.set("zisla1pos2", 0);
	
	data.set("xisla2pos1", 0);
	data.set("yisla2pos1", 0);
	data.set("zisla2pos1", 0);
	
	data.set("xisla2pos2", 0);
	data.set("yisla2pos2", 0);
	data.set("zisla2pos2", 0);
	
	data.set("xisla3pos1", 0);
	data.set("yisla3pos1", 0);
	data.set("zisla3pos1", 0);
	
	data.set("xisla3pos2", 0);
	data.set("yisla3pos2", 0);
	data.set("zisla3pos2", 0);
	
	data.set("xisla4pos1", 0);
	data.set("yisla4pos1", 0);
	data.set("zisla4pos1", 0);
	
	data.set("xisla4pos2", 0);
	data.set("yisla4pos2", 0);
	data.set("zisla4pos2", 0);
	
	data.set("xchest1", 0);
	data.set("ychest1", 0);
	data.set("zchest1", 0);
	
	data.set("xchest2", 0);
	data.set("ychest2", 0);
	data.set("zchest2", 0);
	
	data.save();
player.sendMessage(Settings.title+"Se ha creado la arena : "+name+" id : "+id+" level : "+level);
player.teleport(Server.getInstance().getLevelByName(level).getSafeSpawn());
player.setGamemode(1);
	}
	}
	
	public void setSpawn(Player player, String ida,File file,String slot){
		int id = Integer.parseInt(ida);
		int slo = Integer.parseInt(slot);
		if(new File(file+"/Arenas/sw-"+id+".dat").exists()){
			Double x = player.getX(); Double y = player.getY(); Double z =  player.getZ();
				Config data = new Config(file+"/Arenas/sw-"+id+".dat",Config.YAML);	
				if(slo == 1){
					data.set("xslot1", x);
					data.set("yslot1", y);
					data.set("zslot1", z);
					player.sendMessage(Settings.title+"Registrado slot 1 id: "+id);
				}
				if(slo == 2){
					data.set("xslot2", x);
					data.set("yslot2", y);
					data.set("zslot2", z);
					player.sendMessage(Settings.title+"Registrado slot 2 id: "+id);
				}
				if(slo == 3){
					data.set("xslot3", x);
					data.set("yslot3", y);
					data.set("zslot3", z);
					player.sendMessage(Settings.title+"Registrado slot 3 id: "+id);
				}
				if(slo == 4){
					data.set("xslot4", x);
					data.set("yslot4", y);
					data.set("zslot4", z);
					player.sendMessage(Settings.title+"Registrado slot 4 id: "+id);
				}
				if(slo >4){
					player.sendMessage(Settings.title+"Maximo de slots: 4");	
				}
				data.save();
			
		}else{
			player.sendMessage(Settings.title+"Esta id no existe");
		}
	}
	public void setpositionislas(Player player, String ida, File file,String isla,String pos){
		int id = Integer.parseInt(ida);
		int num = Integer.parseInt(isla);
		int position = Integer.parseInt(pos);
		if(new File(file+"/Arenas/sw-"+id+".dat").exists()){
			int x = (int)player.getX(); int y = (int)player.getY(); int z = (int) player.getZ();
				Config data = new Config(file+"/Arenas/sw-"+id+".dat",Config.YAML);
		if(num == 1){
			if(position == 1){
				data.set("xisla1pos1", x);
				data.set("yisla1pos1", y);
				data.set("zisla1pos1", z);
				data.save();
				player.sendMessage(Settings.title+"Seleccionado punto de isla: "+num+" pos : "+position+" id : "+id);
			}
			if(position == 2){
				data.set("xisla1pos2", x);
				data.set("yisla1pos2", y);
				data.set("zisla1pos2", z);
				data.save();
		player.sendMessage(Settings.title+"Seleccionado punto de isla: "+num+" pos : "+position+" id : "+id);
			}
		}
		if(num == 2){
			if(position == 1){
				data.set("xisla2pos1", x);
				data.set("yisla2pos1", y);
				data.set("zisla2pos1", z);
				data.save();
				player.sendMessage(Settings.title+"Seleccionado punto de isla: "+num+" pos : "+position+" id : "+id);
			}
			if(position == 2){
				data.set("xisla2pos2", x);
				data.set("yisla2pos2", y);
				data.set("zisla2pos2", z);
				data.save();
		player.sendMessage(Settings.title+"Seleccionado punto de isla: "+num+" pos : "+position+" id : "+id);
			}
		}
		if(num == 3){
			if(position == 1){
				data.set("xisla3pos1", x);
				data.set("yisla3pos1", y);
				data.set("zisla3pos1", z);
				data.save();
				player.sendMessage(Settings.title+"Seleccionado punto de isla: "+num+" pos : "+position+" id : "+id);
			}
			if(position == 2){
				data.set("xisla3pos2", x);
				data.set("yisla3pos2", y);
				data.set("zisla3pos2", z);
				data.save();
		player.sendMessage(Settings.title+"Seleccionado punto de isla: "+num+" pos : "+position+" id : "+id);
			}
		}
		if(num == 4){
			if(position == 1){
				data.set("xisla4pos1", x);
				data.set("yisla4pos1", y);
				data.set("zisla4pos1", z);
				data.save();
				player.sendMessage(Settings.title+"Seleccionado punto de isla: "+num+" pos : "+position+" id : "+id);
			}
			if(position == 2){
				data.set("xisla4pos2", x);
				data.set("yisla4pos2", y);
				data.set("zisla4pos2", z);
				data.save();
		player.sendMessage(Settings.title+"Seleccionado punto de isla: "+num+" pos : "+position+" id : "+id);
			}
		}
		}else{
			player.sendMessage(Settings.title+"Esta id no existe");
		}

	}
	
	
	public void setLobby(Player player, String ida, File file){
		int id = Integer.parseInt(ida);	
		if(new File(file+"/Arenas/sw-"+id+".dat").exists()){
			int x = (int)player.getX(); int y = (int)player.getY(); int z = (int) player.getZ();
			Config data = new Config(file+"/Arenas/sw-"+id+".dat",Config.YAML);	
			data.set("xlobby", x);
			data.set("ylobby", y);
			data.set("zlobby", z);
			data.save();
			player.sendMessage(Settings.title+"Lobby registrado id: "+id);
		}else{
			player.sendMessage(Settings.title+"Esta id no existe");	
		}
	}
	public void setespectador(Player player, String ida, File file){
		int id = Integer.parseInt(ida);	
		if(new File(file+"/Arenas/sw-"+id+".dat").exists()){
			int x = (int)player.getX(); int y = (int)player.getY(); int z = (int) player.getZ();
			Config data = new Config(file+"/Arenas/sw-"+id+".dat",Config.YAML);	
			data.set("xspec", x);
			data.set("yspec", y);
			data.set("zspec", z);
			data.save();
			player.sendMessage(Settings.title+"espectador registrado id: "+id);
		}else{
			player.sendMessage(Settings.title+"Esta id no existe");	
		}
	}
	public void minvoid(Player player, String ida, File file){
		int id = Integer.parseInt(ida);	
		if(new File(file+"/Arenas/sw-"+id+".dat").exists()){
			int x = (int)player.getX(); int y = (int)player.getY(); int z = (int) player.getZ();
			Config data = new Config(file+"/Arenas/sw-"+id+".dat",Config.YAML);	
			data.set("xmin", x);
			data.set("ymin", y);
			data.set("zmin", z);
			data.save();
			player.sendMessage(Settings.title+"minvoid registrado id: "+id);
		}else{
			player.sendMessage(Settings.title+"Esta id no existe");	
		}
	}
	public void pos1lobby(Player player, String ida, File file,String pod){
		int id = Integer.parseInt(ida);	
		int position = Integer.parseInt(pod);
		if(new File(file+"/Arenas/sw-"+id+".dat").exists()){
			int x = (int)player.getX(); int y = (int)player.getY(); int z = (int) player.getZ();
			Config data = new Config(file+"/Arenas/sw-"+id+".dat",Config.YAML);	
			if(position == 1){
				data.set("xpos1lobby", x);
				data.set("ypos1lobby", y);
				data.set("zpos1lobby", z);
				data.save();
				player.sendMessage(Settings.title+"pos1lobby registrado id: "+id);	
			}
			if(position == 2){
				data.set("xpos2lobby", x);
				data.set("ypos2lobby", y);
				data.set("zpos2lobby", z);
				data.save();
				player.sendMessage(Settings.title+"pos2lobby registrado id: "+id);	
			}
		}else{
			player.sendMessage(Settings.title+"Esta id no existe");	
		}
	}
	
	public void setchest(Player player, String ida, File file,String pod){
		int id = Integer.parseInt(ida);	
		int position = Integer.parseInt(pod);
		if(new File(file+"/Arenas/sw-"+id+".dat").exists()){
			int x = (int)player.getX(); int y = (int)player.getY(); int z = (int) player.getZ();
			Config data = new Config(file+"/Arenas/sw-"+id+".dat",Config.YAML);	
			if(position == 1){
				data.set("xchest1", x);
				data.set("ychest1", y);
				data.set("zchest1", z);
				data.save();
				player.sendMessage(Settings.title+"Chest1 registrado id: "+id);	
			}
			if(position == 2){
				data.set("xchest2", x);
				data.set("ychest2", y);
				data.set("zchest2", z);
				data.save();
				player.sendMessage(Settings.title+"Chest2 registrado id: "+id);	
			}
		}else{
			player.sendMessage(Settings.title+"Esta id no existe");	
		}
	}
	
	
	public void save(Player player, String ida,File file){
		int id = Integer.parseInt(ida);	
		if(new File(file+"/Arenas/sw-"+id+".dat").exists()){
			player.sendMessage(Settings.title+"Se han guardado los cambios id: "+id);
			player.sendMessage(Settings.title+"use: /sw cancel");
			
		}else{
			player.sendMessage(Settings.title+"Esta id no existe");	
		}
	}
}
