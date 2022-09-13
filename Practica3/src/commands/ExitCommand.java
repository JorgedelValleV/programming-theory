package commands;


import defaultt.Game;


public class ExitCommand extends NoParamsCommand{
	public ExitCommand() {
		super("exit","[E]xit","Terminates the program.");
	}
	@Override
	public boolean execute(Game game) {
		// TODO Auto-generated method stub
		game.setExit(true);
		System.out.println("\t****** Game over!: User exit ******");
		return false;
	}
}
