package commands;

import defaultt.Game;
import factories.ZombieFactory;

public class ZombieListCommand extends NoParamsCommand{	
	public ZombieListCommand() {
		super("zombieList","[Z]ombieList","print the list of available zombies.");
	}
	@Override
	public boolean execute(Game game) {
		System.out.println(ZombieFactory.listOfAvilableZombies());
		return false;
	}
}