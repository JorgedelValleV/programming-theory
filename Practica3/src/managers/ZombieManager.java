package managers;
import java.util.Random;

import defaultt.Game;
import defaultt.Level;

public class ZombieManager {
	private int remZombies;
	private Double frecuencia;
	private int zombiesMatar;
	public ZombieManager(Level level) {
		remZombies = level.getNumberOfZombies();
		zombiesMatar = remZombies;
		frecuencia = level.getZombieFrequency();
	}
	
	public int getRemZombies() {
		return remZombies;
	}
	public int getZombiesMatar() {
		return zombiesMatar;
	}
	
	public Double getFrecuencia() {
		return frecuencia;
	}
	public void nuevoZomb() {
		--remZombies;          
	}
	public void zombieMuerto(){
		--zombiesMatar;
	}
	public boolean isZombieAdded(Random rnd) {
		Double d = rnd.nextDouble();
		System.out.println("Frecquency generated: "+d);
		return d<frecuencia && remZombies>0;
	}
	public String whichZombieAdded(Random rnd,Game game) {
		int d = rnd.nextInt(100)%3;
		System.out.println("Number generated: "+d);
//		Zombie z= null;
		String s ="";
		if(d==1){
			s="d";
//			z=new ZombieDeportista(game);
			}
		else if(d==2){
			s="c";
//			z=new ZombieCaraCubo(game);
		}
		else {
			s="n";
//			z=new ZombieNormal(game);
		}
		return s;
//		return z;
	}

	public void setZombiesMatar(int zombiesMatar) {
		this.zombiesMatar = zombiesMatar;
	}

	public void setRemZombies(int restantes) {
		// TODO Auto-generated method stub
		remZombies= restantes;
	}
}