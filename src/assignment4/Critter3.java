package assignment4;

import java.util.List;

public class Critter3 extends Critter {
	private int age;
	private int aggressiveness;
	
	public Critter3(){
		age = 0;
		aggressiveness = Critter.getRandomInt(100);
	}
	@Override
	public void doTimeStep() {
		// Produce babies correspoding to age lol
		if(age < 40){
			for(int k = 0; k < age; k++){
				Critter3 baby = new Critter3();
				reproduce(baby, Critter.getRandomInt(8));
			}
		}
		// Decide if it wants to walk or run based on aggressiveness
		if(aggressiveness > 75)
			run(Critter.getRandomInt(8));
		else if(aggressiveness > 40)
			walk(Critter.getRandomInt(8));
	}

	@Override
	public boolean fight(String oponent) {
		// If it's aggressive, it will fight.
		if(aggressiveness >= 50)
			return true;
		else
			return false;
	}
	
	@Override
	public String toString(){
		return "3";
	}
	
	public static void runStats(java.util.List<Critter> critters){
		System.out.print("Total Critter3's: " + critters.size() + "\t");
		int avgAg = 0;
		int avgAge = 0;
		for(Critter c: critters){
			avgAg += ((Critter3)c).aggressiveness;
			avgAge += ((Critter3)c).age;
		}
		avgAg /= critters.size();
		avgAge /= critters.size();
		System.out.print("Avg Aggressiveness: " + avgAg + "\t");
		System.out.print("Avg Age: " + avgAge);
		System.out.println();
		
	}

}
