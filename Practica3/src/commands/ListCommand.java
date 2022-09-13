package commands;

import defaultt.Game;
import factories.PlantFactory;

public class ListCommand extends NoParamsCommand{	
	public ListCommand() {
		super("list","[L]ist","print the list of available plants.");
	}
	@Override
	public boolean execute(Game game) {
		// TODO Auto-generated method stub
		System.out.println(PlantFactory.listOfAvilablePlants());
		return false;
	}
}