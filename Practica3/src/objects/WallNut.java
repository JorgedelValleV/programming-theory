package objects;

public class WallNut extends Plant{
//	public WallNut(int x, int y, Game game) {
//		super(x, y, 0, 8, game, 50,0);
//	}
	public WallNut() {
		super(0, 10, 50,"[N]uez",0,"N");
	}
	public String toString() {
		return "N ["+resistencia+"]";
	}
	public void update() {
		//no hace nada solo recibe golpes
	}
}
