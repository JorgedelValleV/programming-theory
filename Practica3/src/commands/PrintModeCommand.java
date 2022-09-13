package commands;

import defaultt.Game;
import exceptions.CommandExecuteException;
import exceptions.CommandParserException;
import printers.GameDebugPrinter;
import printers.GameReleasePrinter;


public class PrintModeCommand extends Command{
	private String mode;
	public PrintModeCommand() {
		super("printmode","[P]rintMode <mode>"," change print mode [Release|Debug]. ");
	}
	public PrintModeCommand( String modo) {
		super("printmode","[P]rintMode <mode>"," change print mode [Release|Debug]. ");
		mode = modo;
	}
	@Override
	public boolean execute(Game game) throws CommandExecuteException {
		// TODO Auto-generated method stub
		switch(mode){
		case "r":case "release":
			game.setGamePrinter(new GameReleasePrinter());
			System.out.println("Cambiado a release");return true;
		case "d":case "debug":
			game.setGamePrinter(new GameDebugPrinter(game));
			System.out.println("Cambiado a debug");return true;
		default:
			throw new CommandExecuteException ("Unknown print mode: "+ mode );
		}
		
	}

	@Override
	public Command parse(String[] commandWords) throws CommandParserException{
		// TODO Auto-generated method stub
		Command c = null;
		if(commandWords[0].equals(this.commandName)  || commandWords[0].equals(this.commandName.substring(0, 1)))  {
			if(commandWords.length!=2)throw new CommandParserException("Incorrect number of arguments for printmode command: [P]rintMode release|debug");
			c=new PrintModeCommand(commandWords[1]);
		}
		return c;
	}
}
