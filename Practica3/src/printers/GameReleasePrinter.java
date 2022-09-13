package printers;

import defaultt.Game;

public class GameReleasePrinter extends BoardPrinter
implements GamePrinter{
	public GameReleasePrinter(){
		super(4,8);
	}
	public String printGame(Game game) {
		encodeGame(game);
		StringBuilder str=new StringBuilder();
		str.append(game.dameAtrib());
		str.append(this.toString(7));
		return str.toString();
	}
	public void encodeGame(Game game)  {
		board = new String[dimX][dimY];
		for(int i = 0; i < dimX; i++) {
			for(int j = 0; j < dimY; j++) {
				board[i][j] =  game.dameString(i,j);
			}
		}
	}
}