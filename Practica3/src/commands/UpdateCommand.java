package commands;


import defaultt.Game;


public class UpdateCommand extends NoParamsCommand{
	public UpdateCommand() {
		super("none","[none]","Skips cycle. ");
	}
	@Override
	public boolean execute(Game game) {
		// TODO Auto-generated method stub
		game.update();
		return true;
	}

}
