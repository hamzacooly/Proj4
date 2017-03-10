package assignment4;

/**
 * Created by Ali on 3/9/2017.
 */
public class Critter2 extends Critter {


    public Critter2(){
        super();
    }


    public String toString() { return "L"; }

    /**
     * Attempts to run in random direction
     * It will only successfully move once
     * For all other attempts, it will not move but still lose energy.
     * tbh it's a dumb critter
     */
    public void doTimeStep() {
        for (int i = 0; i < (Critter.getRandomInt(3) + 1); i++) {
            run(Critter.getRandomInt(7));
        }
    }

    /**
     * Continues attempting to move until at low energy
     * Fortunately it stops before it dies (if the walk energy is really high)
     * Otherwise it would be sad :(
     * If it's lucky enough to win a fight, it can live to walk another day
     * @param opponent String that tells you type of opponent
     */
    public boolean fight(String opponent){
        while(getEnergy() > 15) {
            walk(0);
        }

        return true;
    }
}
