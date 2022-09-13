package managers;

public class SuncoinManager {
	private int suncoins;
	
	public SuncoinManager(){
		this.suncoins=50;

	}
	public int getSuncoins() {
		return suncoins;
	}
	public void restarSoles(int coste){
		suncoins-=coste;
	}
	public void anadirSoles(int n){
		suncoins+=n;
	}
	public void setSuncoins(int solecillos) {
		this.suncoins=solecillos;
		
	}
}