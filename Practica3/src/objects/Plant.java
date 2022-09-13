package objects;


public abstract class Plant extends GameObject{
	protected  int coste;
//	public Plant(int x, int y,int danyo, int resistencia, Game game, int coste,int frecuencia) {
//		super(x, y, danyo, resistencia, game, frecuencia);
//		this.coste=coste;
//	}
	public Plant(int danyo, int resistencia, int coste,String name,int frecuencia,String simbolo) {
		super(danyo, resistencia,name,frecuencia,simbolo);
		this.coste=coste;
	}
	public int getCoste() {
		return coste;
	}
	public boolean isPlantAdded() {
		return (game.getSuncoins()>=coste);
			
	}
	public void recibirDanyo(int dmg){
		resistencia-=dmg;
		if(resistencia<=0){
			vivo=false;
		}
	}
	public String listMsg() { 
		StringBuilder sb = new StringBuilder();
		sb.append(this.nombre).append(": Cost: ").append(this.coste).append(" suncoins Harm: ").append(this.danyo);
		return	sb.toString();
	} 
}
