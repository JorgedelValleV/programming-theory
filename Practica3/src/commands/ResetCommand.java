package commands;

import defaultt.Game;

public class ResetCommand extends NoParamsCommand{
	public ResetCommand() {
		super("reset","[R]eset"," starts a new game.");
	}
	@Override
	public boolean execute(Game game) {
		// TODO Auto-generated method stub
		game.reset();
		return true;
	}
}
