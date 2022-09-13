package objects;

import java.util.Random;

import defaultt.Game;

public abstract class Zombie extends GameObject{
	
	public Zombie(int danyo,int resistencia, int frecuencia,Game game,String name,String simbolo) {
		super(new Random().nextInt(4)%4,7,danyo,resistencia,game,frecuencia,name,simbolo);
	}
	public Zombie(int danyo,int resistencia, int frecuencia,String name,String simbolo) {//para el availableZombie
		super(new Random().nextInt(4)%4,7,danyo,resistencia, frecuencia,name,simbolo);
	}
	public void update() {
		if(vivo){
			//EN EL AVANZAR, SI NO SE PUEDE AVANZAR SE CONTROLA EL ATACAR/NO ATACAR.
			if(this.avanzarPosible(x,y,danyo)){
				if(nextTime==game.getCiclos()){
					y-=1;
					nextTime=frecuencia+game.getCiclos();
				}
			}
			else
				//SI NO AVANZA EN UN NUMERO N DE CICLOS, AL SIGUIENTE SIEMPRE AVANZA
				if(nextTime==game.getCiclos()){
					nextTime=game.getCiclos()+1;
				}
		}
	}
	private boolean avanzarPosible(int x,int y,int dmg){
		if(game.coordenadasNuevas(x,y-1))
			return true;
		else{
			game.hacerDanyo(x,y-1,dmg);
			return false;
		}
	}
	public void recibirDanyo(int dmg){
		resistencia-=dmg;
		if(resistencia<=0){
			vivo=false;
			game.zombieMuerto();
		}
	}
	public String listMsg() { 
		StringBuilder sb = new StringBuilder();
		sb.append(nombre).append(": speed: ").append(frecuencia).append(" Harm: ");
		sb.append(danyo).append(" Life: ").append(resistencia);
		return	sb.toString();
	}
}