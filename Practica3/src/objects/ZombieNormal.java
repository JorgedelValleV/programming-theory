package objects;

import defaultt.Game;

public class ZombieNormal extends Zombie{
	public ZombieNormal(Game game){
		super(1,5,2,game,"[Z]ombieNormal","Z");
	}
	public ZombieNormal(){//para el avalibleZombies
		super(1,5,2,"[Z]ombieNormal","Z");
	}
	public String toString() {
		return "Z ["+this.resistencia+"]";
	}
}
