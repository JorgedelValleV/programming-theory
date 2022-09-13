package commands;

import exceptions.CommandParserException;

public abstract class NoParamsCommand extends Command {
	public NoParamsCommand(String commandText, String commandInfo, String helpInfo) {
		super(commandText,commandInfo,helpInfo);
	}	
	@Override
	public Command parse(String[] commandWords)  throws CommandParserException{
		Command c = null;
		if(commandWords[0].equals(""))commandWords[0]="n";
		if(commandWords[0].equals(this.commandName)  || commandWords[0].equals(this.commandName.substring(0, 1))) {
			if(commandWords.length!=1)throw new CommandParserException(this.commandName + " has no arguments");
			c=this;
		}
		return c;
	}
}
