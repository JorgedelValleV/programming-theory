package defaultt;

public enum Level {
	EASY(3, 0.1), HARD(5, 0.2), INSANE(10, 0.3);
	private int numberOfZombies;
	private double zombieFrequency;
	private Level(int zombieNum, double zombieFreq){
		numberOfZombies = zombieNum;
		zombieFrequency = zombieFreq;
	} 
	public String toString() {
		String nivel = "";
		
		switch(this) {
		case EASY:
			nivel = "EASY";
			break;
	
		case HARD:
			nivel = "HARD";
			break;
	
		case INSANE:
			nivel = "INSANE";
			break;		
		}
		
		return nivel;
	}
	public int getNumberOfZombies() {
		return numberOfZombies; 
	} 
	public double getZombieFrequency() { 
		return zombieFrequency;
	}
	
	public static Level parse(String inputString) {
		for (Level level : Level.values()) 
			if (level .name().equalsIgnoreCase(inputString)) return level;
		return null;
	}
	public static String all(String separator) {
		StringBuilder sb = new StringBuilder(); 
		for (Level level : Level.values())
			sb.append(level.name() + separator);
		String allLevels = sb.toString();
		return allLevels.substring(0, allLevels.length() - separator.length());
	}
//	EASY, HARD, INSANE;
//	public double getFrecuencia(Level level){
//		double frecuencia=0.0;
//		switch(level) {
//		case EASY:
//			frecuencia = 0.1;
//			break;
//		case HARD:
//			frecuencia = 0.2;
//			break;
//		case INSANE:
//			frecuencia = 0.3;
//			break;
//		}
//		return frecuencia;
//	}
/*	public int getRemZombies(Level level){
	  int remZombies=0;
	switch(level) {
		case EASY:
			remZombies = 3;
			break;
		case HARD:
			remZombies = 5;
			break;
		case INSANE:
			remZombies =10;
			break;
		}
		return remZombies;
	}
	public String toString(Level level) {
		String s="";
		switch(level) {
		case EASY:
			s = "EASY";
			break;
		case HARD:
			s = "HARD";
			break;
		case INSANE:
			s = "INSANE";
			break;
		}
		return s;
		
	}
*/
}




