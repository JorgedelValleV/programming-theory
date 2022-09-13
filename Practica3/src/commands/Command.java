package commands;


import exceptions.CommandExecuteException;
import defaultt.Game;
import exceptions.CommandParserException;

public abstract class Command {
	private String helpText;
	private String commandInfo;
	protected final String commandName;
	public Command(String commandText, String commandInfo, String helpInfo){
		this.commandInfo = commandInfo; 
		helpText = helpInfo; 
		commandName = commandText;
	}
//Some commands may generate an error in the execute or parse methods. In the absence of exceptions, they must the tell the controller not to print the board  
	public abstract boolean execute(Game game) throws CommandExecuteException, CommandParserException;
	public abstract Command parse(String[] commandWords) throws CommandParserException;
	public String helpText(){
		return " " + commandInfo + ": " + helpText + '\n';
	}
}