package factories;

import exceptions.CommandParserException;
import objects.CherryBomb;
import objects.Peashooter;
import objects.Plant;
import objects.Sunflower;
import objects.WallNut;

public class PlantFactory {
	private static Plant[] availablePlants = {
			new Sunflower(),
			new Peashooter(),
			new CherryBomb(),
			new WallNut(),
	};
	public static Plant getPlant(String plantName)throws CommandParserException{  
		switch(plantName){
		case"s":case "S": case"sunflower":
			return new Sunflower();
		case "p": case"P": case"peashooter":
			return new Peashooter();
		case "c": case"C": case"cherrybomb":
			return new CherryBomb();
		case "n": case"N": case"nuez":
			return new WallNut();
		default:
			throw new CommandParserException("Unknown plant name: " + plantName +"\n");
		}	
	}
	public static String listOfAvilablePlants() { 
		StringBuilder sb = new StringBuilder();
		for(int i = 0;i<4;++i) {
			sb.append(availablePlants[i].listMsg()).append("\n");
		}		
		return	sb.toString();
	} 
	public static String getName(String plantName){  
		switch(plantName){
		case"s": case"sunflower":
			return "Sunflower";
		case "p":case"peashooter":
			return "Peashooter";
		case "c":case"cherryBomb":
			return "CherryBomb";
		case "n":case"nuez":
			return "WallNut";
		default:
			return "Plant";
		}
	}
}