package nl.sw.jose.main.Creador;

public class Configurator {
public static String owner = "";
public static int  MODE_TYPE_FULT = 0;
public static String arena = "";
public void setMode(int type){
	MODE_TYPE_FULT = type;
}
public void setOwner(String ow){
	if(owner.equals("")){
		return;
	}else{
		owner = ow;
	}
}
public void setArenaName(String name){
	arena = name;
}
public String getArenaName(){
	return arena;
}
public String getOwner(){
	return owner;
}
public int getMode(){
	return MODE_TYPE_FULT;
}

}
