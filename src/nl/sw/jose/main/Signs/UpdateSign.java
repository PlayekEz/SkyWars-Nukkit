package nl.sw.jose.main.Signs;
import java.util.Map;


import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.blockentity.BlockEntitySign;
import cn.nukkit.utils.Config;
import nl.sw.jose.main.SkyWars;
public class UpdateSign extends cn.nukkit.scheduler.Task{

private SkyWars sw;

public UpdateSign(SkyWars plugin){
this.sw = plugin;	
}
@Override
public void onRun(int tick){
	Map<Long, BlockEntity> be = this.sw.getServer().getDefaultLevel().getBlockEntities();
	for(BlockEntity b : be.values()){
	if(b instanceof BlockEntitySign){
		BlockEntitySign sign = (BlockEntitySign) b;	
		
		String[] line = sign.getText();
		if(line[0].equals("§b§lSkyWars")){
			String map = line[1];
			Config game = new Config(this.sw.getDataFolder()+"/Arenas/"+map+".dat",Config.YAML);
			String status = "§5Closed";
			int pla = this.sw.getServer().getLevelByName(game.getString("level")).getPlayers().size();
			if(game.getString("status").equals("on")){
				status = "§aOpen";
			}
			if(game.getString("reset").equals("reset")){
				status = "§6Reseting";
			}
			if(game.getString("reset").equals("off")){
				status = "§5Closed";
			}
			
			
			sign.setText("§b§lSkyWars",map,status,"§b"+pla+"§7/§b4");	
	}
		
	}
		
	}
	
	
	
}
	
	
}
	

