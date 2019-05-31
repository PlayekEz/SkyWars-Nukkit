package nl.sw.jose.main;
import cn.nukkit.Player;
import cn.nukkit.network.protocol.ChangeDimensionPacket;
import cn.nukkit.network.protocol.PlayStatusPacket;
import nl.sw.jose.main.SkyWars;
public class lobby  extends cn.nukkit.scheduler.Task {
	public int time = 6;
	private SkyWars sw;
	private Player pl;
	public lobby(SkyWars plugin, Player player){
		this.sw = plugin;
		this.pl = player;
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
			pl.teleport(sw.getServer().getDefaultLevel().getSafeSpawn());

			sw.getServer().getScheduler().cancelTask(this.getTaskId());
	}
	}
}
