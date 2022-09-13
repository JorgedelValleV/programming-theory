package objects;

import defaultt.Game;

public class ZombieCaraCubo extends Zombie{
	public ZombieCaraCubo(Game game){
		super(1,8,4,game,"[Z]ombieDeportista","W");//danyo, vida,frecuencia
	}
	public ZombieCaraCubo(){//para el avalibleZombies
		super(1,8,4,"[Z]ombieCaraCubo","W");
	}
	public String toString() {
		return "W ["+this.resistencia+"]";
	}
}
