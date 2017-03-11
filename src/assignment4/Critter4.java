package assignment4;

public class Critter4 extends Critter {
	
	private int dir;
	private int confidence; // determines fighting/reproduction
	private int intellect; // determines fleeing
	
	public Critter4(){
		dir = Critter.getRandomInt(8);
		confidence = Critter.getRandomInt(100);
		intellect = Critter.getRandomInt(100);
	}
	
	@Override
	public String toString(){
		return "4";
	}

	@Override
	public void doTimeStep() {
		walk(dir);
		if(confidence > 90)
			reproduce(new Critter4(), Critter.getRandomInt(8));
	}

	@Override
	public boolean fight(String oponent) {
		if(confidence > 60){
			if(intellect < 70)
				run(dir);
			return true;
		}
		else{
			if(intellect < 70)
				run(dir);
			return false;
		}
	}
	
	public static void runStats(java.util.List<Critter> critters){
		System.out.print("Total Critter4's: " + critters.size() + "\t");
		int avgconf = 0;
		int avgint = 0;
		for(Critter c: critters){
			avgconf += ((Critter4)c).confidence;
			avgint += ((Critter4)c).intellect;
		}
		avgconf /= critters.size();
		avgint /= critters.size();
		System.out.print("Avg Confidence: " + avgconf + "\t");
		System.out.print("Avg Intellect: " + avgint);
		System.out.println();
	}
}
