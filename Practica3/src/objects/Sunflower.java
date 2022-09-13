package objects;
public class Sunflower extends Plant{
	private final int numSoles= 10;
	
//	public Sunflower(int x, int y, Game game) {
//		super(x, y, 0, 1, game, 20,2);
//	}
	public Sunflower() {
		super(0, 1, 20,"[S]unflower",2,"S");

	}
	public void update(){
		if(nextTime == game.getCiclos()) {
			game.anadirSoles(numSoles);
			nextTime= frecuencia + game.getCiclos();
		}
	}
	public String toString() {
		return "S ["+this.resistencia+"]";
	}

}