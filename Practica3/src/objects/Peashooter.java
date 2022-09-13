package objects;
public class Peashooter extends Plant{
//	public Peashooter(int x, int y, Game game) {
//		super(x, y, 1, 3, game, 50,1);
//	}
	public Peashooter() {
		super(1, 3, 50,"[P]eashooter",1,"P");
	}
	public void update(){
		if(nextTime == game.getCiclos()) {
			game.realizarDisparo(this.x,this.danyo);
			nextTime= frecuencia + game.getCiclos();
		}
	}
	public String toString() {
		return "P ["+this.resistencia+"]";
	}
}