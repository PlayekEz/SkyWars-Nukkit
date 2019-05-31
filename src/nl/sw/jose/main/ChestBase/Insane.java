package nl.sw.jose.main.ChestBase;

import cn.nukkit.item.Item;


public class Insane {

	
public Item getCenter(){
	int v1 = 0; int v2= 0; int v3 = 0;
	int key = (int) (Math.random() * 40)+1;
	 //blocks
	if(key == 1){v1 = 1; v2 = 0;  v3 = 32;}
	if(key == 2){v1 = 3; v2 = 0;  v3 = 32;}
	if(key == 3){v1 = 5; v2 = 1;  v3 = 32;}
	if(key == 4){v1 = 43; v2 = 4;  v3 = 32;}
	//espadas
		if(key == 5){v1 = 267; v2 = 0;  v3 = 1;}
		if(key == 6){v1 = 276; v2 = 0;  v3 = 1;}
		//armor
		if(key == 7){v1 = 306; v2 = 0;  v3 = 1;}
		if(key == 8){v1 = 307; v2 = 0;  v3 = 1;}
		if(key == 9){v1 = 308; v2 = 0;  v3 = 1;}
		if(key == 10){v1 = 309; v2 = 0;  v3 = 1;}
		
		if(key == 11){v1 = 310; v2 = 0;  v3 = 1;}
		if(key == 12){v1 = 311; v2 = 0;  v3 = 1;}
		if(key == 13){v1 = 312; v2 = 0;  v3 = 1;}
		if(key == 14){v1 = 313; v2 = 0;  v3 = 1;}
		//agua lava
		if(key == 15){v1 = 9; v2 = 0;  v3 = 1;}
		if(key == 16){v1 = 1; v2 = 0;  v3 = 1;}
		//proyectil
		if(key == 17){v1 = 332; v2 = 0;  v3 = 10;}
		if(key == 18){v1 = 344; v2 = 0;  v3 = 10;}
		if(key == 19){v1 = 261; v2 = 0;  v3 = 1;}
		if(key == 20){v1 = 0; v2 = 0;  v3 = 0;}
		//comida
		if(key == 21){v1 = 364; v2 = 0;  v3 = 2;}
		if(key == 22){v1 = 366; v2 = 0;  v3 = 3;}
		if(key == 23){v1 = 322; v2 = 0;  v3 = 1;}
		//enchant
		if(key == 24){v1 = 351; v2 = 4;  v3 = 4;}
		if(key == 25){v1 = 384; v2 = 0;  v3 = 5;}
		//air
		if(key == 26){v1 = 0; v2 = 0;  v3 = 0;}
		if(key == 27){v1 = 0; v2 = 0;  v3 = 0;}
		if(key == 28){v1 = 0; v2 = 0;  v3 = 0;}
		if(key == 29){v1 = 0; v2 = 0;  v3 = 0;}
		if(key == 30){v1 = 0; v2 = 0;  v3 = 0;}
		if(key == 31){v1 = 0; v2 = 0;  v3 = 0;}
		if(key == 32){v1 = 0; v2 = 0;  v3 = 0;}
		if(key == 33){v1 = 0; v2 = 0;  v3 = 0;}
		if(key == 34){v1 = 0; v2 = 0;  v3 = 0;}
		if(key == 35){v1 = 0; v2 = 0;  v3 = 0;}
		if(key == 36){v1 = 0; v2 = 0;  v3 = 0;}
		if(key == 37){v1 = 0; v2 = 0;  v3 = 0;}
		if(key == 38){v1 = 0; v2 = 0;  v3 = 0;}
		if(key == 39){v1 = 0; v2 = 0;  v3 = 0;}
		if(key == 40){v1 = 0; v2 = 0;  v3 = 0;}
		if(key == 41){v1 = 0; v2 = 0;  v3 = 0;}
		
	return Item.get(v1,v2,v3);
}
	
public Item getIsla(){
	int v1 = 0; int v2= 0; int v3 = 0;
	int key = (int) (Math.random() * 49)+1;	
	 //blocks
	if(key == 1){v1 = 1; v2 = 0;  v3 = 32;}
	if(key == 2){v1 = 3; v2 = 0;  v3 = 32;}
	if(key == 3){v1 = 5; v2 = 1;  v3 = 32;}
	if(key == 4){v1 = 43; v2 = 4;  v3 = 32;}
	//espadas
	if(key == 5){v1 = 283; v2 = 0;  v3 = 1;}
	if(key == 6){v1 = 272; v2 = 0;  v3 = 1;}
	if(key == 7){v1 = 267; v2 = 0;  v3 = 1;}
	//armor
	if(key == 8){v1 = 302; v2 = 0;  v3 = 1;}
	if(key == 9){v1 = 303; v2 = 0;  v3 = 1;}
	if(key == 10){v1 = 304; v2 = 0;  v3 = 1;}
	if(key == 11){v1 = 305; v2 = 0;  v3 = 1;}
	
	if(key == 12){v1 = 306; v2 = 0;  v3 = 1;}
	if(key == 13){v1 = 307; v2 = 0;  v3 = 1;}
	if(key == 14){v1 = 308; v2 = 0;  v3 = 1;}
	if(key == 15){v1 = 309; v2 = 0;  v3 = 1;}
	
	if(key == 16){v1 = 314; v2 = 0;  v3 = 1;}
	if(key == 17){v1 = 315; v2 = 0;  v3 = 1;}
	if(key == 18){v1 = 316; v2 = 0;  v3 = 1;}
	if(key == 19){v1 = 317; v2 = 0;  v3 = 1;}
	//proyectil
	if(key == 20){v1 = 332; v2 = 0;  v3 = 10;}
	if(key == 21){v1 = 344; v2 = 0;  v3 = 10;}
	if(key == 22){v1 = 261; v2 = 0;  v3 = 1;}
	if(key == 23){v1 = 0; v2 = 0;  v3 = 0;}
	//comida
	if(key == 24){v1 = 364; v2 = 0;  v3 = 2;}
	if(key == 25){v1 = 366; v2 = 0;  v3 = 3;}
	if(key == 26){v1 = 322; v2 = 0;  v3 = 1;}
//air
	if(key == 27){v1 = 0; v2 = 0;  v3 = 0;}
	if(key == 28){v1 = 0; v2 = 0;  v3 = 0;}
	if(key == 29){v1 = 0; v2 = 0;  v3 = 0;}
	if(key == 30){v1 = 0; v2 = 0;  v3 = 0;}
	if(key == 31){v1 = 0; v2 = 0;  v3 = 0;}
	if(key == 32){v1 = 0; v2 = 0;  v3 = 0;}
	if(key == 33){v1 = 0; v2 = 0;  v3 = 0;}
	if(key == 34){v1 = 0; v2 = 0;  v3 = 0;}
	if(key == 35){v1 = 0; v2 = 0;  v3 = 0;}
	if(key == 36){v1 = 0; v2 = 0;  v3 = 0;}
	if(key == 37){v1 = 0; v2 = 0;  v3 = 0;}
	if(key == 38){v1 = 0; v2 = 0;  v3 = 0;}
	if(key == 39){v1 = 0; v2 = 0;  v3 = 0;}
	if(key == 40){v1 = 0; v2 = 0;  v3 = 0;}
	if(key == 41){v1 = 0; v2 = 0;  v3 = 0;}
	
	if(key == 42){v1 = 0; v2 = 0;  v3 = 0;}
	if(key == 43){v1 = 0; v2 = 0;  v3 = 0;}
	if(key == 44){v1 = 0; v2 = 0;  v3 = 0;}
	if(key == 45){v1 = 0; v2 = 0;  v3 = 0;}
	if(key == 46){v1 = 0; v2 = 0;  v3 = 0;}
	if(key == 47){v1 = 0; v2 = 0;  v3 = 0;}
	if(key == 48){v1 = 0; v2 = 0;  v3 = 0;}
	if(key == 49){v1 = 0; v2 = 0;  v3 = 0;}
	if(key == 50){v1 = 0; v2 = 0;  v3 = 0;}
	
	return  Item.get(v1,v2,v3);
}
	
	
}
