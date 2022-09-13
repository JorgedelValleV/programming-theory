package commands;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import defaultt.Game;
import exceptions.CommandExecuteException;
import exceptions.CommandParserException;
import exceptions.FileContentsException;
import printers.MyStringUtils;

import commands.Command;
import commands.SaveCommand;


public class LoadCommand extends Command{
	private String fileName;
	public LoadCommand() {
		super("load","[Lo]ad <filename>","  Load the state of the game from a file");
	}
	public LoadCommand( String fichero) {
		super("load","[Lo]ad <filename>","  Load the state of the game from a file");
		fileName = fichero;
	}
	@Override
	public boolean execute(Game game) throws CommandExecuteException, CommandParserException {
		if(!MyStringUtils.isValidFilename(fileName))
			throw new CommandExecuteException("Invalid filename: the filename contains invalid characters");
		if(!MyStringUtils.fileExists(fileName))
			throw new CommandExecuteException("File not found");
//		Game juegoant = game;
		try(BufferedReader inStream = new BufferedReader(new FileReader(fileName))){
			if(!fileName.equals("auxiliar.dat")) {
				Command c =new SaveCommand("auxiliar");
				c.execute(game);
			}
			String s;
			s = inStream.readLine();
			s = inStream.readLine();
			//game = new Game(juegoant.getSemilla(),juegoant.getLevel());
			game.load(inStream);
			System.out.println("Game successfully loaded from ﬁle "+fileName);
			return  true;
		}catch (FileContentsException | IOException  e){
//			game=juegoant;
			Command c =new LoadCommand("auxiliar.dat");
			c.execute(game);
			throw new CommandExecuteException("Load " + fileName +" failed: invalid file contents");
		
		}
//		} catch (CommandExecuteException|CommandParserException e) {
//			System.out.println("Error al guardar la version anterior, no sera posible recuperarlo con auxiliar.dat");
//			return false;
//		}
		
	}

	@Override
	public Command parse(String[] commandWords) throws CommandParserException{
		// TODO Auto-generated method stub
		Command c = null;
		if(commandWords[0].equals(this.commandName)  || commandWords[0].equals("lo"))  {
			if(commandWords.length!=2)throw new CommandParserException("Incorrect number of arguments for load command: [Lo]ad fileName(.dat)");
			c=new LoadCommand(commandWords[1]);
		}
		return c;
	}
}