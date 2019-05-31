package nl.sw.jose.main.Game;

import cn.nukkit.block.Block;
import cn.nukkit.level.Level;
import cn.nukkit.math.Vector3;


public class RemoveIsla {

	
	
public void remove(Vector3 pos1,Vector3 pos2,Level level){
	
	int xMin = (int) Math.min(pos1.x, pos2.x);
	int xMax = (int) Math.max(pos1.x, pos2.x);
	int zMin = (int) Math.min(pos1.z, pos2.z);
	int zMax = (int) Math.max(pos1.z, pos2.z);
	int yMin = (int) Math.min(pos1.y, pos2.y);
	int yMax = (int) Math.max(pos1.y, pos2.y);
	
	  for(int x = xMin; x < xMax; ++x){
		   for(int z = zMin; z < zMax; ++z){
			   for(int y = yMin; y < yMax; ++y){
	   if(level.getBlock(new Vector3(x,y,z)) == Block.get(0)){
		   
	   }else{
		 level.setBlock(new Vector3(x,y,z), Block.get(0));
	   }
			   }
		   }
          }
}
	
	
}
