package objects;

public class CherryBomb extends Plant{
//	public CherryBomb(int x, int y, Game game) {
//		super(x, y, 10, 2, game, 50,2);
//		this.nextTime = game.getCiclos()+1;
//	}
	public CherryBomb() {
		super(10, 2, 50,"Peta[C]ereza",2,"C");//danyo,vida,coste,frecuencia
	}
	public void update(){
		if(nextTime == game.getCiclos()) {
			game.explotar(this.x,this.y,this.danyo);
			vivo=false;
		}
	}
	public String toString() {
		return "C ["+resistencia+"]";
	}
}
