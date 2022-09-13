package defaultt;

import java.util.Scanner;

import commands.Command;
import commands.CommandParser;
import exceptions.CommandExecuteException;
import exceptions.CommandParserException;


public class Controller { 
	private Game game;		
	private Scanner in;
	private final String prompt="Command> ";
	
	public Controller(Game juego) {
		this.game=juego;
		in=new Scanner(System.in);
	}
	
	public void run(){
		System.out.println("Welcome to PlantVsZombies 3.0");
		System.out.println("Random seed used: "+game.getSemilla());
		printGame();
		while (!game.isFinished()) { 
			System.out.print(prompt);
			String[] words = in.nextLine().toLowerCase().trim().split(" ");
			try{
				Command command = CommandParser.parseCommand(words);
				if (command != null) { 
					if(command.execute(game)){
						if(!game.isGameOver())printGame();
					}
				} 
				else { 
					System.err.println("Invalid comand"); 
				}
			}catch (CommandParserException | CommandExecuteException ex){
				System.out.format(ex.getMessage() + " %n %n");
			}
		}
//		if(game.ganaJugador())
//			System.out.println("\t******Ha ganado el jugador.LES DESTROZASTE EN:  "+ game.getCiclos()+" CICLOS******");
//		else
//			if(game.ganaZombie())
//				System.out.println("\t******Ganaron los zombies.PERDIISTE PINCHE WEYY!!******");
	}
	private void printGame() {
		System.out.println(game.gamePrinter.printGame(game));
	}


}