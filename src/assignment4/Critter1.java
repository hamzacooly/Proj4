package assignment4;

import java.util.List;

public class Critter1 extends Critter {


    public Critter1(){
        super();
    }

    /**
     * @return ASCII representation of critter
     */
    public String toString() { return "A"; }


    /** Makes baby in random direction */
    public void doTimeStep() {

        Critter1 baby = null;
        try {
            baby = this.getClass().newInstance();
        }
        catch (InstantiationException | IllegalAccessException e){}

        reproduce(baby, Critter.getRandomInt(7));
    }


    /**
     *  Makes baby in random direction
     * Then fights (to the death)
     * @param opponent String that tells you type of opponent
     */
    public boolean fight(String opponent){

        Critter1 baby = null;
        try {
            baby = this.getClass().newInstance();
        }
        catch (InstantiationException | IllegalAccessException e){}

        reproduce(baby, Critter.getRandomInt(7));

        return true;
    }

    /**
     * Displays total number of critters, along with average energy
     * @param critters List of Critter1 objects in population
     */
    public static void runStats (List<Critter> critters) {
        System.out.print("Total Critter1's: " + critters.size() + "\t");
        if (critters.size() > 0) {
            int avgNRG = 0;
            for (Critter c : critters) {
                avgNRG += ((Critter1)c).getEnergy();
            }
            avgNRG /= critters.size();
            System.out.print("Avg Energy: " + avgNRG);
        }
        System.out.println();
    }
}
