package commands;

import defaultt.Game;
public class HelpCommand extends NoParamsCommand{
	public HelpCommand() {
		super("help","[H]elp","print this help message.");
	}
	@Override
	public boolean execute(Game game) {
		// TODO Auto-generated method stub 
		System.out.println(CommandParser.commandHelp());
		return false;
	}
}
