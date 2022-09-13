package printers;

import defaultt.Game;

public class GameDebugPrinter extends BoardPrinter
implements GamePrinter{
	public  GameDebugPrinter(Game game) {
		super(1,game.dameNumElem());
	}
		public String printGame(Game game) {
			encodeGame(game);
			StringBuilder str=new StringBuilder();
			str.append(game.dameAtrib());
			str.append("Seed: ").append(game.getSemilla()).append("\n");
			str.append(this.toString(19));//cambiarlo al game
			return str.toString();
		}
		public void encodeGame(Game game)  {
			dimY=game.dameNumElem();
			board = new String[dimX][dimY];
			for(int j = 0; j < dimY; j++) {
					board[0][j] =  game.funcionNuevilla(j);
			}
		}
}