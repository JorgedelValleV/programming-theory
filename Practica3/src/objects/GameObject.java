package objects;

import java.io.BufferedWriter;
import java.io.IOException;

import defaultt.Game;

public abstract class GameObject {
	protected  int x;
	protected  int y;
	protected  int resistencia;
	protected  int danyo;
	protected  boolean vivo;
	protected  Game game;
	protected String nombre;//nombre del objeto para comando list
	protected int frecuencia;
	protected int nextTime;
	protected String symbol;
	public GameObject(int x, int y,int danyo, int resistencia,Game game,int frecuencia,String name,String simbolo) {//zombies
		this.x = x;
		this.y = y;
		this.danyo = danyo;
		this.resistencia = resistencia;
		this.vivo=true;
		this.game=game;
		this.nextTime = game.getCiclos()+1;
		this.frecuencia=frecuencia;
		this.nombre=name;
		this.symbol=simbolo;
	}
	public GameObject(int x, int y,int danyo, int resistencia,int frecuencia,String name,String simbolo) {//para el availableZombie
		this.x = x;
		this.y = y;
		this.danyo = danyo;
		this.resistencia = resistencia;
		this.vivo=true;
		this.frecuencia=frecuencia;
		this.nombre=name;
		this.symbol=simbolo;
	}
	public GameObject(int danyo, int resistencia,String name,int frecuencia,String simbolo) {//plantas
		this.danyo = danyo;
		this.resistencia = resistencia;
		this.vivo=true;
		this.nombre=name;
		this.frecuencia=frecuencia;
		this.symbol=simbolo;
	}
	public void inicializar(int x, int y, Game game) {//plantas en addPlantToGame
		this.x=x;
		this.y=y;
		this.game=game;
		this.nextTime = game.getCiclos()+1;
	}
	public void inicializar(int x, int y, Game game,int nextTime) {//plantas, al comentarlo no me salta error
		this.x=x;
		this.y=y;
		this.game=game;
		this.nextTime = nextTime;
	}
	public void inicializar(int x,int y,int vida,int nextTime, Game game){//cuando se hace el load de la lista de plantas/zombies
		this.x=x;
		this.y=y;
		this.game=game;
		this.nextTime = nextTime;
		this.resistencia=vida;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public boolean isVivo() {
		return vivo;
	}
	public int getDanyo() {
		return danyo;
	}
	public int getResistencia() {
		return resistencia;
	}

	public int getFrecuencia() {
		return frecuencia;
	}
	public String getNombre() {
		return this.nombre;
	}
	
	public Game getGame() {
		return game;
	}
	public int getNextTime() {
		return nextTime;
	}
	public String getSymbol() {
		return symbol;
	}

	public void store(BufferedWriter outStream) throws IOException{
		outStream.write(symbol);
		outStream.write(":");
		outStream.write(Integer.toString(resistencia));
		outStream.write(":");
		outStream.write(Integer.toString(x));
		outStream.write(":");
		outStream.write(Integer.toString(y));
		outStream.write(":");
		outStream.write(Integer.toString(nextTime));
		outStream.write(", ");
	}
	public abstract void recibirDanyo(int dmg);
	public abstract void update();
	public abstract String toString();
	
}
