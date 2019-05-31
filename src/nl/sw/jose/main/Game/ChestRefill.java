package nl.sw.jose.main.Game;

import java.io.File;
import nl.sw.jose.main.ChestBase.Insane;
import java.util.Map;
import nl.sw.jose.main.ChestBase.Basic;
import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.blockentity.BlockEntityChest;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.level.Level;
import cn.nukkit.utils.Config;
public class ChestRefill {
	
public void clear(Level level){
	Map<Long, BlockEntity> be = level.getBlockEntities();
for(BlockEntity b : be.values()){
if(b instanceof BlockEntityChest){
	BlockEntityChest chest = (BlockEntityChest) b;	
	for(int i = 0; i <= chest.getSize(); i++) {

		chest.getInventory().setItem(i, new Item(0,0,0));

	}
}
}
}

public void refillChest(File file,String name,Level level){
	Config chest = new Config(file+"/Arenas/"+name,Config.YAML);
	if(chest.getString("chest").equals("normal")){
		this.refillBasic(level,chest);
	}else{
		this.refillOp(level, chest);
	}
}

public void refillOp(Level level,Config conf){
	Map<Long, BlockEntity> be = level.getBlockEntities();
	for(BlockEntity e: be.values()){
		if(e instanceof BlockEntityChest){
			BlockEntityChest chest = (BlockEntityChest) e;
			int x1 = conf.getInt("xchest1"); int y1 = conf.getInt("ychest1"); int z1 = conf.getInt("zchest1");
			int x2 = conf.getInt("xchest2"); int y2 = conf.getInt("ychest2"); int z2 = conf.getInt("zchest2");
			if((chest.getX() <= Math.max(x1, x2)) & (chest.getX() >= Math.min(x1, x2)) & (chest.getY() <= Math.max(y1, y2)) &
					(chest.getY() >= Math.min(y1, y2)) & (chest.getZ() <= Math.max(z1, z2)) & (chest.getZ() >= Math.min(z1, z2))){
				
				
				for(int i = 0; i <= chest.getSize(); i++) {
                  Item item = new Insane().getCenter();
                  if(new Insane().getCenter().equals(Item.get(262,0,5))){
                	  
                  }else{
                	  item.addEnchantment(Enchantment.getEnchantment(Enchantment.ID_KNOCKBACK));  
                  }
                 
					chest.getInventory().setItem(i, item);

				}
			}else{
				for(int i = 0; i <= chest.getSize(); i++) {
    Item item = new Insane().getIsla();
    if(new Insane().getCenter().equals(Item.get(262,0,5))){
  	  
    }else{
  	  item.addEnchantment(Enchantment.getEnchantment(Enchantment.ID_KNOCKBACK));  
    }
		chest.getInventory().setItem(i, item);

				}		
				
				
			}
			
			
		}
	}
}

public void refillBasic(Level level,Config conf){
	
	Map<Long, BlockEntity> be = level.getBlockEntities();
	for(BlockEntity e: be.values()){
	if(e instanceof BlockEntityChest){
		BlockEntityChest chest = (BlockEntityChest) e;
		int x1 = conf.getInt("xchest1"); int y1 = conf.getInt("ychest1"); int z1 = conf.getInt("zchest1");
		int x2 = conf.getInt("xchest2"); int y2 = conf.getInt("ychest2"); int z2 = conf.getInt("zchest2");
if((chest.getX() <= Math.max(x1, x2)) & (chest.getX() >= Math.min(x1, x2)) & (chest.getY() <= Math.max(y1, y2)) &
			(chest.getY() >= Math.min(y1, y2)) & (chest.getZ() <= Math.max(z1, z2)) & (chest.getZ() >= Math.min(z1, z2))){
		for(int i = 0; i <= chest.getSize(); i++) {

			chest.getInventory().setItem(i, new Basic().getBasicOpIsla());

		}
	}else{
		for(int i = 0; i <= chest.getSize(); i++) {

			chest.getInventory().setItem(i, new Basic().getItemBasicIsla());

		}	
	}
	}
	}
	
}



}
