package defaultt;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

import java.util.Random;

import exceptions.CommandExecuteException;
import exceptions.CommandParserException;
import exceptions.FileContentsException;
import factories.PlantFactory;
import factories.ZombieFactory;
import managers.*;
import objects.Plant;
import objects.Zombie;
import printers.GamePrinter;

public class Game {
	private int ciclos;
	private long semilla;
	private Random rand;
	private Level level;
	private GameObjectList listZombies;
	private GameObjectList listPlantas;
	private SuncoinManager scm;
	private ZombieManager zm;
	private boolean exit;
	private boolean gameOver;
	final int filas = 4;
	final int columnas = 8;
	public static final String wrongPre = "unknown game attribute: ";
	public static final String lineTooLongMsg = "too many words on line commencing: ";
	public static final String lineTooShortMsg = "missing data on line commencing: ";

	GamePrinter gamePrinter;

	public Game(long seed, Level level) {
		this.ciclos = 0;
		this.semilla = seed;
		this.rand = new Random(seed);
		this.level = level;
		this.listPlantas = new GameObjectList();
		this.listZombies = new GameObjectList(level);
		this.scm = new SuncoinManager();
		this.zm = new ZombieManager(level);
		this.exit = false;
		this.gameOver = false;
	}

	// public Game(long seed) {
	// this.ciclos=0;
	// this.semilla=seed;
	// this.level=level;
	// this.rand=new Random(seed);
	// this.listPlantas=new GameObjectList();
	// this.listZombies=new GameObjectList(level);
	// this.scm = new SuncoinManager();
	// this.zm = new ZombieManager(level);
	// this.exit=false;
	// this.gameOver=false;
	// }
	public int getCiclos() {
		return ciclos;
	}

	public int getSuncoins() {
		return scm.getSuncoins();
	}

	public long getSemilla() {
		return semilla;
	}

	public void setFinishedt(boolean finished) {
		this.gameOver = finished;
	}

	public void setExit(boolean exit) {
		this.exit = exit;
	}

	public void sumarCiclos() {
		ciclos++;
	}

	public void anadirSoles(int n) {
		scm.anadirSoles(n);
	}

	public String dameAtrib() {
		return "Number of cycles: " + getCiclos() + '\n' + "Suncoins: "
				+ scm.getSuncoins() + '\n' + "Zombies remaining: "
				+ zm.getRemZombies() + '\n' + "Level: " + level.toString()
				+ '\n' + "Frequency appear: " + zm.getFrecuencia() + '\n';
	}

	public String funcionNuevilla(int n) {
		// primero todas la plantas y luego todos los zombies
		if (n < listPlantas.getNumElem()) {
			return this.listPlantas.dameCositas(n);
		} else {
			return this.listZombies.dameCositas(n - listPlantas.getNumElem());
		}
	}

	// public String toString() {
	// this.impresora=new GamePrinter(this, filas, columnas);
	// return "Number of cycles: "+getCiclos()+'\n'+
	// "Suncoins: "+scm.getSuncoins()+'\n'+
	// "Zombies remaining: "+zm.getRemZombies()+'\n'+
	// "Frequency appear: "+zm.getFrecuencia()+'\n'+
	// "Level: "+ level.toString(this.level)+'\n'+
	// impresora.toString();
	// }
	public String dameString(int x, int y) {
		return this.listPlantas.toString(x, y)
				+ this.listZombies.toString(x, y);
	}

	public boolean controlCoordenadas(int x, int y) {// coordenadas tablero
		return (x >= 0 && x < 4 && y >= 0 && y < 7);
	}

	public boolean coordenadasNuevas(int x, int y) {// comprueba que no hay
													// ningun elemento en una
													// posicion
		return (listPlantas.isPositionEmpty(x, y) && listZombies
				.isPositionEmpty(x, y));
	}

	public void update() {
		listPlantas.update();
		if (ganaJugador()) {
			gameOver = true;
			System.out
					.println("\t******Ha ganado el jugador.LES DESTROZASTE EN:  "
							+ this.ciclos + " CICLOS******");
		} else {
			listZombies.update();
			if (ganaZombie()) {
				gameOver = true;
				System.out
						.println("\t******Ganaron los zombies.PERDIISTE PINCHE WEYY!!******");
			}
			this.addZombieToList();
			listPlantas.remove();
			listZombies.remove();
			this.sumarCiclos();
		}
	}

	public boolean ganaZombie() {
		return listZombies.ganaZombie();
	}

	public boolean ganaJugador() {
		return zm.getZombiesMatar() == 0;
	}

	public boolean isFinished() {
		return gameOver || exit;
	}

	public void hacerDanyo(int x, int y, int dmg) {// hay un zombie en la pos
													// (x,y+1) que hace daño a
													// una planta en (x,y)
		if (!listPlantas.isPositionEmpty(x, y))
			listPlantas.recibirDanyo(x, y, dmg);

	}

	public void realizarDisparo(int n, int dmg) {// un peashooter en la fila n
													// dispara
		listZombies.recibirDisparo(n, dmg);
	}

	public void explotar(int x, int y, int dmg) {// una petacereza explota en
													// x,y
		listZombies.recibirExplosion(x, y, dmg);
	}

	public void zombieMuerto() {
		zm.zombieMuerto();
	}

	public void addZombieToList() {
		if (zm.isZombieAdded(this.rand)) {
			Zombie z = ZombieFactory.getZombie(zm.whichZombieAdded(rand, this),
					this);
			// Zombie z = zm.whichZombieAdded(rand,this);
			if (listZombies.isPositionEmpty(z.getX(), z.getX())) {
				listZombies.addObject(z);
				zm.nuevoZomb();
			}
		}
	}

	public void reset() {
		this.ciclos = 0;
		this.listZombies = new GameObjectList(level);
		this.listPlantas = new GameObjectList();
		this.scm = new SuncoinManager();
		this.zm = new ZombieManager(level);
	}

	public boolean addPlantToGame(Plant plant, int x, int y, String name)
			throws CommandExecuteException {
		plant.inicializar(x, y, this);
		if (controlCoordenadas(x, y)) {
			if (coordenadasNuevas(x, y)) {
				if (plant.isPlantAdded()) {
					listPlantas.addObject(plant);
					scm.restarSoles(plant.getCoste());
				} else {
					throw new CommandExecuteException("Failed to add "
							+ PlantFactory.getName(name)
							+ ": not enough suncoins to buy it");
				}
			} else {
				throw new CommandExecuteException("Failed to add "
						+ PlantFactory.getName(name) + ": (" + x + "," + y
						+ ") is already occupied");
			}
		} else {
			throw new CommandExecuteException("Failed to add "
					+ PlantFactory.getName(name) + ": (" + x + "," + y
					+ ") is an invalid position");
		}
		return true;
	}

	public int dameNumElem() {
		return listPlantas.getNumElem() + listZombies.getNumElem();
	}

	public void setGamePrinter(GamePrinter printer) {
		this.gamePrinter = printer;
	}

	public void store(BufferedWriter outStream) throws IOException,
			FileContentsException {
		try {
			outStream.write("cycle: ");
			outStream.write(Integer.toString(ciclos));
			outStream.newLine();
			outStream.write("sunCoins: ");
			outStream.write(Integer.toString(scm.getSuncoins()));
			outStream.newLine();
			outStream.write("level: ");
			outStream.write(level.toString());
			outStream.newLine();
			outStream.write("remZombies: ");
			outStream.write(Integer.toString(zm.getRemZombies()));
			outStream.newLine();
			outStream.write("plantList: ");
			listPlantas.store(outStream);
			outStream.newLine();
			outStream.write("zombieList: ");
			listZombies.store(outStream);
			outStream.newLine();
		} catch (NumberFormatException e) {
			throw new FileContentsException(
					"Fallo en conversion de entero a string");
		}

	}

	public void load(BufferedReader inStream) throws IOException,
			FileContentsException, CommandParserException,
			CommandExecuteException {
		String[] restOfWords = loadLine(inStream, "cycle", false);
		if (Integer.parseInt(restOfWords[0]) < 0)
			throw new FileContentsException("invalid cycle num");
		this.ciclos = Integer.parseInt(restOfWords[0]);
		restOfWords = loadLine(inStream, "sunCoins", false);
		if (Integer.parseInt(restOfWords[0]) < 0)
			throw new FileContentsException("invalid suncoins num");
		this.scm.setSuncoins(Integer.parseInt(restOfWords[0]));
		restOfWords = loadLine(inStream, "level", false);
		this.level = Level.parse(restOfWords[0]);
		if (level == null)
			throw new FileContentsException("invalid level num");
		restOfWords = loadLine(inStream, "remZombies", false);
		if (Integer.parseInt(restOfWords[0]) < 0
				|| Integer.parseInt(restOfWords[0]) > level
						.getNumberOfZombies())
			throw new FileContentsException("invalid remZombies num");
		this.zm.setRemZombies(Integer.parseInt(restOfWords[0]));
		String[] plantStrings = loadLine(inStream, "plantList", true);
		if (plantStrings.length > filas * (columnas - 1))
			throw new FileContentsException("invalid plantList length");
		this.listPlantas = new GameObjectList(plantStrings, this);
		String[] zombieStrings = loadLine(inStream, "zombieList", true);
		if (zombieStrings.length > (filas * columnas) - plantStrings.length)
			throw new FileContentsException("invalid zombieList length");
		this.listZombies = new GameObjectList(zombieStrings, this, this.level);
		this.zm.setZombiesMatar(listZombies.getNumElem()
				+ this.zm.getRemZombies());
	}

	// public void igualar(Game nuevo){
	// this.ciclos=nuevo.ciclos;
	// this.semilla=nuevo.semilla;
	// this.rand=nuevo.rand;
	// this.level=nuevo.level;
	// this.listPlantas=nuevo.listPlantas;
	// this.listZombies=nuevo.listZombies;
	// this.scm = nuevo.scm;
	// this.zm = nuevo.zm;
	// this.exit=false;
	// this.gameOver=false;
	// }

	public String[] loadLine(BufferedReader inStream, String pre, boolean isList)
			throws IOException, FileContentsException {
		String line = inStream.readLine().trim();
		// absence of the pre is invalid
		if (!line.startsWith(pre + ":"))
			throw new FileContentsException(wrongPre + pre);
		// cut the prex and the following colon the line
		// then trim it to get the attribute contents
		String contentString = line.substring(pre.length() + 1).trim();
		String[] words;
		// the attribute contents are not empty
		if (!contentString.equals("")) {
			if (!isList) {
				// split non−list attribute contents into words
				// using 1−or−more−white−spaces as separator
				words = contentString.split("\\s+");
				// a non−list attribute with contents of more than one word is
				// invalid
				if (words.length != 1)
					throw new FileContentsException(lineTooLongMsg + pre);
			} else
				// split list attribute contents into words
				// using comma+0−or−more−white−spaces as separator
				words = contentString.split(",\\s*");
			// the attribute contents are empty
		} else {
			// a non−list attribute with empty contents is invalid
			if (!isList)
				throw new FileContentsException(lineTooShortMsg + pre);
			// a list attibute with empty contents is valid;
			// use a zero−length array to store its words
			words = new String[0];
		}
		return words;
	}

	public Level getLevel() {
		return level;
	}

	public boolean isGameOver() {
		return gameOver;
	}

}