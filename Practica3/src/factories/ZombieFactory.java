package factories;

import defaultt.Game;
import objects.Zombie;
import objects.ZombieCaraCubo;
import objects.ZombieDeportista;
import objects.ZombieNormal;

public class ZombieFactory {
	private static Zombie[] availableZombies = {
		new ZombieNormal(),
		new ZombieCaraCubo(),
		new ZombieDeportista(),
	};
	public static Zombie getZombie(String zombieName,Game game){  
		Zombie z=null;
		switch(zombieName){
		case"n": case"Z":
			z= new ZombieNormal(game);break;
		case "c":case "W":
			z= new ZombieCaraCubo(game);break;
		case "d": case "X":
			z= new ZombieDeportista(game);break;
		}
		return z;
	}
	//list of available zombies
	public static String listOfAvilableZombies() { 
		StringBuilder sb = new StringBuilder();
		for(int i = 0;i<3;++i) {
			sb.append(availableZombies[i].listMsg()).append("\n");
		}
		return	sb.toString();
	} 
}
