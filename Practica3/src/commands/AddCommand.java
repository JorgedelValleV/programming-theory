package commands;

import defaultt.Game;
import exceptions.CommandExecuteException;
import exceptions.CommandParserException;
import factories.PlantFactory;
import objects.Plant;

public class AddCommand extends Command {
	private int x;
	private int y;
	private String plantName;
	public AddCommand() {
		super("add","[A]dd <plant> <x> <y>","add flower.");
	}
	public AddCommand(String flor, int x, int y) {
		super("add","[A]dd <plant> <x> <y>","add flower.");
		plantName=flor;
		this.x=x;
		this.y=y;
	}
	@Override
	public boolean execute(Game game) throws  CommandExecuteException, CommandParserException  {
		// TODO Auto-generated method stub
		Plant plant = PlantFactory.getPlant(plantName);
		if(game.addPlantToGame(plant, x, y,plantName)){
			Command c = new UpdateCommand();
			c.execute(game);
			return true;
		}
		else return false;
	}

	@Override
	public Command parse(String[] commandWords) throws CommandParserException  {
		// TODO Auto-generated method stub
		Command c = null;
		if(commandWords[0].equals(this.commandName)  || commandWords[0].equals(this.commandName.substring(0, 1))){
			if (commandWords.length!=4)
				throw new CommandParserException("Incorrect number of arguments for add command: [A]dd <plant> <x> <y>");
			String flor = commandWords[1];
			try{
			int col = Integer.parseInt(commandWords[2]);
			int fil = Integer.parseInt(commandWords[3]);
			c=new AddCommand(flor,col,fil);
			}
			catch (NumberFormatException e){
				throw new CommandParserException("Invalid argument for add command, number expected: [A]dd <plant> <x> <y>");
			}
		}
		return c;
	}
}
