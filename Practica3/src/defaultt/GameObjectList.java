package defaultt;

import java.io.BufferedWriter;
import java.io.IOException;

import exceptions.CommandExecuteException;
import exceptions.CommandParserException;
import exceptions.FileContentsException;
import factories.PlantFactory;
import factories.ZombieFactory;
import objects.GameObject;
import objects.Plant;
import objects.Zombie;

public class GameObjectList {
	private GameObject[] objetos;
	private int numElem;

	private final int[] incF = { -1, 0, 1, 1, 1, 0, -1, -1 };
	private final int[] incC = { 1, 1, 1, 0, -1, -1, -1, 0 };

	public GameObjectList(Level level) {
		objetos = new Zombie[level.getNumberOfZombies()];
		this.numElem = 0;
	}

	public GameObjectList() {
		objetos = new Plant[32];
		this.numElem = 0;
	}

	public GameObjectList(String[] plantStrings, Game nuevo)
			throws CommandExecuteException, FileContentsException {
		objetos = new Plant[32];
		try {
			for (this.numElem = 0; numElem < plantStrings.length; ++numElem) {
				String[] words = plantStrings[numElem].trim().split(":");
				Plant plant = PlantFactory.getPlant(words[0]);
				if (Integer.parseInt(words[2]) < 0
						|| Integer.parseInt(words[2]) > 3
						|| Integer.parseInt(words[3]) < 0
						|| Integer.parseInt(words[3]) > 6
						|| Integer.parseInt(words[1]) < 0
						|| Integer.parseInt(words[1]) > plant.getResistencia()
						|| Integer.parseInt(words[4]) < 0 || nuevo == null)
					throw new FileContentsException(
							"no se realizo la carga, datos incorrectos en archivo");
				plant.inicializar(Integer.parseInt(words[2]),
						Integer.parseInt(words[3]), Integer.parseInt(words[1]),
						Integer.parseInt(words[4]), nuevo);
				objetos[numElem] = plant;
			}
		} catch (CommandParserException e) {
			throw new CommandExecuteException(
					"No se realizo la carga, habia una planta no existente");
		}
	}

	public GameObjectList(String[] zombieStrings, Game nuevo, Level level)
			throws FileContentsException {
		objetos = new Zombie[level.getNumberOfZombies()];// valdria
															// nuevo.zm.getZombiesMatar
		for (this.numElem = 0; numElem < zombieStrings.length; ++numElem) {
			String[] words = zombieStrings[numElem].trim().split(":");
			Zombie z = ZombieFactory.getZombie(words[0], nuevo);
			if (z == null)
				throw new FileContentsException("Invalid zombie");
			if (Integer.parseInt(words[2]) < 0
					|| Integer.parseInt(words[2]) > 3
					|| Integer.parseInt(words[3]) < 0
					|| Integer.parseInt(words[3]) > 7
					|| Integer.parseInt(words[1]) < 0
					|| Integer.parseInt(words[1]) > z.getResistencia()
					|| Integer.parseInt(words[4]) < 0 || nuevo == null)
				throw new FileContentsException(
						"no se realizo la carga, datos incorrectos en archivo");
			z.inicializar(Integer.parseInt(words[2]),
					Integer.parseInt(words[3]), Integer.parseInt(words[1]),
					Integer.parseInt(words[4]), nuevo);// he anadido 1,4
			objetos[numElem] = z;
		}
	}

	public void addObject(GameObject o) {
		if (objetos.length == numElem)
			redimensionar();
		objetos[numElem] = o;
		numElem++;
	}

	public boolean isPositionEmpty(int x, int y) {
		int i = 0;
		boolean ok = true;
		while (i < numElem && ok) {
			if (this.getPosition(x, y) != null)
				ok = false;
			++i;
		}
		return ok;
	}

	private GameObject getPosition(int x, int y) {
		GameObject o = null;
		for (int i = 0; i < this.numElem && o == null; i++) {
			if (this.objetos[i].isVivo() && this.objetos[i].getX() == x
					&& this.objetos[i].getY() == y) {
				o = objetos[i];
			}
		}
		return o;
	}

	public void recibirDanyo(int x, int y, int dmg) {
		GameObject o = this.getPosition(x, y);
		if (o != null)
			o.recibirDanyo(dmg);
	}

	public void update() {
		for (int i = 0; i < numElem; ++i)
			objetos[i].update();
	}

	public String toString(int x, int y) {
		String str = "";
		if (this.getPosition(x, y) != null)
			str = this.getPosition(x, y).toString();
		return str;
	}

	public void remove() {
		GameObject[] lista = new GameObject[objetos.length];// this.length
		int cont = 0;
		for (int i = 0; i < numElem; ++i) {
			if (objetos[i].isVivo()) {
				lista[cont] = objetos[i];
				++cont;
			}
		}
		numElem = cont;
		objetos = lista;
	}

	public void redimensionar() {// solo plantas
		GameObject[] aux = new GameObject[2 * numElem];
		for (int i = 0; i < numElem; ++i) {
			aux[i] = objetos[i];
		}
		objetos = aux;
	}

	public int getNumElem() {
		return numElem;
	}

	public String dameCositas(int n) {
		StringBuilder str = new StringBuilder();
		str.append(objetos[n].getSymbol()).append("[l:")
				.append(objetos[n].getResistencia()).append(",");
		str.append("x:").append(objetos[n].getX()).append(",");
		str.append("y:").append(objetos[n].getY()).append(",");
		str.append("t:")
				.append(objetos[n].getNextTime()
						- objetos[n].getGame().getCiclos()).append("]");
		return str.toString();
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////
	// ////// Esta parte que sigue es unica de zombielist
	// ///////////////////////////
	// /////////////////////////////////////////////////////////////////////////////////////////////

	public void recibirDisparo(int n, int dmg) {
		int pos = 0;
		GameObject[] lista = new Zombie[numElem];
		int cont = 0;
		for (int i = 0; i < numElem; i++) {
			if (objetos[i].getX() == n && objetos[i].isVivo()) {
				lista[cont] = objetos[i];
				++cont;
			}
		}
		if (cont > 0) {
			pos = buscarMasAdelantado(lista, cont);
			lista[pos].recibirDanyo(dmg);
		}
	}

	private int buscarMasAdelantado(GameObject[] list, int cont) {
		int indice = 0;
		for (int i = 1; i < cont; i++) {
			if (list[i].getY() < list[i - 1].getY()) {
				indice = i;
			}
		}
		return indice;
	}

	public void recibirExplosion(int x, int y, int dmg) {
		for (int i = 0; i < 8; ++i) {
			GameObject z = getPosition(x + incF[i], y + incC[i]);
			if (z != null)
				z.recibirDanyo(dmg);
		}

	}

	public boolean ganaZombie() {
		boolean enc = false;
		int i = 0;
		while (i < numElem && !enc) {
			if (objetos[i].getY() == -1)
				enc = true;
			++i;
		}
		return enc;
	}

	public void store(BufferedWriter outStream) throws IOException {
		for (int i = 0; i < numElem; ++i) {
			objetos[i].store(outStream);
		}
	}

}
