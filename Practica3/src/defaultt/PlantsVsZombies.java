package defaultt;

import java.util.Random;

import defaultt.Controller;
import exceptions.CommandExecuteException;
import printers.GameReleasePrinter;

public class PlantsVsZombies {

	public static void main(String[] args)  {
		Level level;
		try{
		if(args.length<1||args.length>2)
			throw new CommandExecuteException("Introdujo número erroneo de argumentos");	
		long seed = (args.length > 1) ? Long.parseLong(args[1]) : new Random().nextInt(1000);
		switch (args[0].toLowerCase()) {
			case "hard":
				level = Level.HARD;
				break;
			case "insane":
				level = Level.INSANE;
				break;
			case "easy":
				level = Level.EASY;
				break;
			default:
				throw new CommandExecuteException("No existe dicho nivel");
		}
		Game juego = new Game(seed, level);
		Controller control = new Controller(juego);
		juego.setGamePrinter(new GameReleasePrinter());
		control.run();
		}catch(NumberFormatException | CommandExecuteException e){
			System.out.format(e.getMessage());
		}
	}

}