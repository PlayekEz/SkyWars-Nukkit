package nl.sw.jose.main.Entity;
import cn.nukkit.Player;
import cn.nukkit.math.Vector3;
import cn.nukkit.network.protocol.ChangeDimensionPacket;
import cn.nukkit.network.protocol.PlayStatusPacket;
import nl.sw.jose.main.SkyWars;
public class Debug extends cn.nukkit.scheduler.Task  {
	public int time = 5;
	private SkyWars sw;
	private Player pl;
	private String level;
	private Vector3 pos;
	public Debug(SkyWars plugin, Player player,String levelname, Vector3 position){
		this.sw = plugin;
		this.pl = player;
		this.level = levelname;
		this.pos = position;
	}

	@Override
	public void onRun(int tick) {
	if(time >0){
		time--;
	}
	
	if(time == 0){
		ChangeDimensionPacket dimension1 = new ChangeDimensionPacket();
		dimension1.x = (int)pl.x;
		dimension1.y = (int)pl.y+1;
		dimension1.z = (int)pl.z;
	        dimension1.dimension = 0;
		pl.dataPacket(dimension1);
		 PlayStatusPacket respawn1 = new PlayStatusPacket();
			respawn1.status = PlayStatusPacket.PLAYER_SPAWN;
			pl.dataPacket(respawn1);
			pl.teleport(sw.getServer().getLevelByName(this.level).getSafeSpawn());
			pl.teleport(this.pos);
			sw.getServer().getScheduler().cancelTask(this.getTaskId());
	}
	}
}
