package assignment4;

/**
 * Created by Ali on 3/7/2017.
 */
public class Critter1 extends Critter {


    public Critter1(){
        super();
    }


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
}
