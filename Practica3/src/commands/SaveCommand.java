package commands;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import defaultt.Game;
import exceptions.CommandParserException;
import exceptions.FileContentsException;
import printers.MyStringUtils;

public class SaveCommand extends Command{
	private String fileName;
	public SaveCommand() {
		super("save","[S]ave <filename>","  Save the state of the game to a file. ");
	}
	public SaveCommand( String fichero) {
		super("save","[S]ave <filename>","  Save the state of the game to a file. ");
		fileName = fichero;
	}
	@Override
	public boolean execute(Game game) {
		if(MyStringUtils.isValidFilename(fileName)){
			try(BufferedWriter outStream = new BufferedWriter(new FileWriter(fileName+".dat"))) {	
				outStream.write("Plants Vs Zombies v3.0");
				outStream.newLine();
				outStream.newLine();
				game.store(outStream);
				System.out.println("Game successfully saved in ﬁle "+fileName+".dat.Use the load command to reload it.");
				return  true;
			}catch (IOException | FileContentsException  e){
				System.out.format(e.getMessage() + " %n %n");
				//throw new FileContentsException("Problema en la coherencia de los datos leidos");
				return false;
			}
		}
		else {
			System.out.println("Invalid filename: "+fileName+"(the filename contains invalid characters)");
			return false;
		}
	}

	@Override
	public Command parse(String[] commandWords) throws CommandParserException{
		// TODO Auto-generated method stub
		Command c = null;
		if(commandWords[0].equals(this.commandName)  || commandWords[0].equals(this.commandName.substring(0, 1)))  {
			if(commandWords.length!=2)throw new CommandParserException("Incorrect number of arguments for save command: [S]ave fileName");
			c=new SaveCommand(commandWords[1]);
		}
		return c;
	}
}