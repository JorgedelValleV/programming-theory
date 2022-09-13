package objects;

import defaultt.Game;

public class ZombieDeportista extends Zombie{
	public ZombieDeportista(Game game){
		super(1,2,1,game,"[Z]ombieDeportista","X");
	}
	public ZombieDeportista(){//para el avalibleZombies
		super(1,2,1,"[Z]ombieDeportista","X");
	}
	public String toString() {
		return "X ["+this.resistencia+"]";
	}
}
