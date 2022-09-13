package commands;

import exceptions.CommandParserException;


public class CommandParser {
	private static Command[] availableCommands = {
		new AddCommand(),
		new PrintModeCommand(),
		new HelpCommand(),
		new ResetCommand(),
		new ExitCommand(),
		new ListCommand(),
		new UpdateCommand(), 
		new ZombieListCommand(),
		new SaveCommand(),
		new LoadCommand(),
		
	}; 
	public static Command parseCommand(String[] commandWords) throws CommandParserException {
		Command c = null;
		for(int i = 0;i<10 && c==null;++i) {
			c=availableCommands[i].parse(commandWords);
		}
		if(c==null)
			throw new CommandParserException("Unknown command. Use ’help’ to see the available commands");
		return c;
	}
	public static String commandHelp() {
		String s="";
		for(int i = 0;i<7;++i) {
			s=s + availableCommands[i].helpText();
		}
		return s;
	}
}
