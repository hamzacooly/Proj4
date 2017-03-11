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
		// Produce babies corresponding to age lol
		if(age < 40){
			for(int k = 0; k < age; k++){
				Critter3 baby = new Critter3();
				reproduce(baby, Critter.getRandomInt(8));
			}
		}
		age++;
		if(Critter.getRandomInt(123232)%2 == 0)
			aggressiveness++;
		else
			aggressiveness--;
		// Decide if it wants to walk or run based on aggressiveness
		if(aggressiveness > 75)
			run(Critter.getRandomInt(8));
		else if(aggressiveness > 40)
			walk(Critter.getRandomInt(8));
	}
	
	/**
	 * Fights based on its aggressiveness level.
	 * @param oponent The opponent that the critter will be fighting
	 * @return If the critter wants to fight or not
	 */
	@Override
	public boolean fight(String oponent) {
		// If it's aggressive, it will fight.
		if(aggressiveness >= 50)
			return true;
		else
			return false;
	}
	
	/**
	 * @return name of critter on the board.
	 */
	@Override
	public String toString(){
		return "3";
	}
	/**
	 * Displays total # critters, avg aggressiveness, and avg age.
	 * @param critters List of Critter3 objects in the population
	 */
	public static void runStats(List<Critter> critters){
		System.out.print("Total Critter3's: " + critters.size() + "\t");
		if(critters.size() > 0){
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
		}
		System.out.println();
	}

}
