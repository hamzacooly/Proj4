package assignment4;
/* CRITTERS Critter.java
 * EE422C Project 4 submission by
 * Hamza Khatri
 * hak533
 * 16220
 * Slip days used: <0>
 * Mohammad Kedwaii
 * mak3799
 * 16238
 * Slip days used: <0>
 * Spring 2017
 */


import java.util.*;
import java.lang.reflect.*;

/* see the PDF for descriptions of the methods and fields in this class
 * you may add fields, methods or inner classes to Critter ONLY if you make your additions private
 * no new public, protected or default-package code or data can be added to Critter
 */


public abstract class Critter {
	private static String myPackage;
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();

	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}
	
	private static java.util.Random rand = new java.util.Random();
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}
	
	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}
	
	
	/* a one-character long string that visually depicts your critter in the ASCII interface */
	public String toString() { return ""; }
	
	private int energy = 0;
	protected int getEnergy() { return energy; }
	
	private int x_coord;
	private int y_coord;
	private boolean hasMoved;
	private boolean inFight;
	

	/**
	 * Moves 1 step in the given direction
	 * Checks if critter has already moved
	 * If critter is in fight, checks if it is moving to an already occupied space
	 * Deducts energy if critter moved/attempted to move
	 * @param direction 0-7, refers to cardinal directions
	 */
	protected final void walk(int direction) {
	    int initialX = this.x_coord;
	    int initialY = this.y_coord;
	    
	    energy -= Params.walk_energy_cost;

	    if(hasMoved) return;
	    
	    else {
	    	coordChange(direction, 1);
	    	hasMoved = true;
        	if (inFight) {
	        	for (Critter c : population) {
	            	if (this.x_coord == c.x_coord && this.y_coord == c.y_coord && !this.equals(c)){
	                	this.x_coord = initialX;
	                	this.y_coord = initialY;
                		hasMoved = false;
                	}
            	}
        	}
        }
	}
	
	/**
	 * Moves 2 steps in the given direction
	 * Checks if critter has already moved
	 * If critter is in fight, checks if it is moving to an already occupied space
	 * Deducts energy if critter moved/attempted to move
	 * @param direction 0-7, refers to cardinal directions
	 */
	protected final void run(int direction) {
        int initialX = this.x_coord;
        int initialY = this.y_coord;
        energy -= Params.run_energy_cost;

        if (hasMoved) return;
           
        else {
	    	coordChange(direction, 2);
	    	hasMoved = true;
        	if (inFight) {
	        	for (Critter c : population) {
	            	if (this.x_coord == c.x_coord && this.y_coord == c.y_coord && !this.equals(c)){
	                	this.x_coord = initialX;
	                	this.y_coord = initialY;
                		hasMoved = false;
                	}
            	}
        	}
        }
	}
	

	/**
	 * Checks if parent has enough energy to make baby
	 * If it does, initializes baby fields and adds it to Babies List
	 * @param offspring Critter passed from parent's doTimeStep()
	 * @param direction where baby will spawn in relation to parent
	 */
	protected final void reproduce(Critter offspring, int direction) {
        if (this.energy < Params.min_reproduce_energy) return;
        else {
            offspring.energy = (this.energy / 2);
            this.energy -= offspring.energy;

            offspring.x_coord = this.x_coord;
            offspring.y_coord = this.y_coord;
            offspring.coordChange(direction, 1);

            babies.add(offspring);
        }
	}

	public abstract void doTimeStep();
	public abstract boolean fight(String oponent);
	
	/**
	 * create and initialize a Critter subclass.
	 * critter_class_name must be the unqualified name of a concrete subclass of Critter, if not,
	 * an InvalidCritterException must be thrown.
	 * (Java weirdness: Exception throwing does not work properly if the parameter has lower-case instead of
	 * upper. For example, if craig is supplied instead of Craig, an error is thrown instead of
	 * an Exception.)
	 * @param critter_class_name
	 * @throws InvalidCritterException
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {
		Class<?> myCritter = null;
		Constructor<?> constructor = null;
		Object instanceOfMyCritter = null;

		try {
			myCritter = Class.forName(myPackage + "." + critter_class_name); 	// Class object of specified name
		} catch (ClassNotFoundException e) {
			throw new InvalidCritterException(myPackage + "." + critter_class_name);
		}
		try {
			constructor = myCritter.getConstructor();		// No-parameter constructor object
			instanceOfMyCritter = constructor.newInstance();	// Create new object using constructor
		} catch (Exception e) { // various exceptions
			// Do whatever is needed to handle the various exceptions here -- e.g. rethrow Exception
			throw new InvalidCritterException(myPackage + "." + critter_class_name);
		}
		Critter me = (Critter)instanceOfMyCritter;		// Cast to Critter
		me.x_coord = getRandomInt(Params.world_width);
		me.y_coord = getRandomInt(Params.world_height);
		me.energy = Params.start_energy;
		population.add(me);
	}
	
	/**
	 * Gets a list of critters of a specific type.
	 * @param critter_class_name What kind of Critter is to be listed.  Unqualified class name.
	 * @return List of Critters.
	 * @throws InvalidCritterException
	 */
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		List<Critter> result = new ArrayList<Critter>();
		Class<?> myCritter = null;
		try {
			myCritter = Class.forName(myPackage + "." + critter_class_name); 	// Class object of specified name
		} catch (ClassNotFoundException e) {
			throw new InvalidCritterException(myPackage + "." + critter_class_name);
		}
		for(Critter k : population){
			if(myCritter.isInstance(k))
				result.add(k);
		}
		return result;
	}
	
	/**
	 * Prints out how many Critters of each type there are on the board.
	 * @param critters List of Critters.
	 */
	public static void runStats(List<Critter> critters) {
		System.out.print("" + critters.size() + " critters as follows -- ");
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		for (Critter crit : critters) {
			String crit_string = crit.toString();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string,  1);
			} else {
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
		}
		String prefix = "";
		for (String s : critter_count.keySet()) {
			System.out.print(prefix + s + ":" + critter_count.get(s));
			prefix = ", ";
		}
		System.out.println();		
	}
	
	/* the TestCritter class allows some critters to "cheat". If you want to 
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here. 
	 * 
	 * NOTE: you must make sure that the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctly update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}
		
		protected void setX_coord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}
		
		protected void setY_coord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}
		
		protected int getX_coord() {
			return super.x_coord;
		}
		
		protected int getY_coord() {
			return super.y_coord;
		}
		

		/*
		 * This method getPopulation has to be modified by you if you are not using the population
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.
		 */
		protected static List<Critter> getPopulation() {
			return population;
		}
		
		/*
		 * This method getBabies has to be modified by you if you are not using the babies
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.  Babies should be added to the general population 
		 * at either the beginning OR the end of every timestep.
		 */
		protected static List<Critter> getBabies() {
			return babies;
		}
	}

	/**
	 * Clear the world of all critters, dead and alive
	 */
	public static void clearWorld() {
		// Complete this method.
		population.clear();
	}
	
	/** Causes time to move forward */
	public static void worldTimeStep() {
		// Do the time steps
		for(Critter c : population){
			c.hasMoved = false;
			c.doTimeStep();
		}
		
		// Resolve the fites
		encounters();
		
		// Update da rest energy
		for(Critter c: population){
			c.energy -= Params.rest_energy_cost;
		}
		
		// Add some algae boiii
		for(int k = 0; k < Params.refresh_algae_count; k++){
			try{
				makeCritter("Algae");
			}
			catch(Exception e){}
		}
		
		// Add da babies
		population.addAll(babies);
		babies.clear();
		
		// Remove da ded bugs
		Iterator<Critter> jj = population.iterator();
		while(jj.hasNext()){
			Critter k = jj.next();
			if(k.energy <= 0)
				jj.remove();
		}
	}
	
	/** 
	 * Takes care of instances of multiple critters occupying the same space
	 * by having them fight
	 * Goes through all critters in the population
	 */
	private static void encounters(){
		for(Critter c: population){
			for(Critter k: population){
				if(!c.equals(k) && (c.x_coord == k.x_coord) && (c.y_coord == k.y_coord) && (c.energy > 0) && (k.energy > 0)){
					c.inFight = true;
					k.inFight = true;
					boolean cfite = c.fight(k.toString());
					boolean kfite = k.fight(c.toString());
					boolean cwins = false;
					if((c.x_coord == k.x_coord) && (c.y_coord == k.y_coord) && (c.energy > 0) && (k.energy > 0)){
						int cdmg = (cfite)?getRandomInt(c.energy):0;
						int kdmg = (kfite)?getRandomInt(k.energy):0;
						if(cdmg > kdmg)
							cwins = true;
						if(cwins){
							c.energy += k.energy/2;
							k.energy = -1;
						}
						else{
							k.energy += c.energy/2;
							c.energy = -1;
						}
					}
					c.inFight = false;
					k.inFight = false;
				}
			}
		}
	}
	
	/** Prints grid display of world using ASCII characters onto console */
	public static void displayWorld() {
		// Complete this method.
		int[][] world = new int[Params.world_width+2][Params.world_height+2];
		if(population.size() > 0){
			for(int k = 0; k < population.size(); k++){
				int x = population.get(k).x_coord +1;
				int y = population.get(k).y_coord +1;
				if(world[x][y] == 0)
					world[x][y] = k+1;
			}
			for(int k = 0; k < Params.world_height+2; k++){
				for(int j = 0; j < Params.world_width+2; j++){
					// Account for the border
					if(k == 0 || k == Params.world_height+1){
						if(j == 0 || j == Params.world_width+1)
							System.out.print("+");
						else
							System.out.print("-");
								
					}
					else if(j == 0 || j == Params.world_width+1){
						if(k == 0 || k == Params.world_height+1)
							System.out.print("+");
						else
							System.out.print("|");
					}
					else{
						if(world[j][k] != 0)
							System.out.print(population.get(world[j][k] - 1).toString());
						else
							System.out.print(" ");
					}
				}
				System.out.println();
			}
		}
		else{
			for(int k = 0; k < Params.world_height+2; k++){
				for(int j = 0; j < Params.world_width+2; j++){
					// Account for the border
					if(k == 0 || k == Params.world_height+1){
						if(j == 0 || j == Params.world_width+1)
							System.out.print("+");
						else
							System.out.print("-");
								
					}
					else if(j == 0 || j == Params.world_width+1){
						if(k == 0 || k == Params.world_height+1)
							System.out.print("+");
						else
							System.out.print("|");
					}
					else{
						System.out.print(" ");
					}
				}
				System.out.println();
			}
		}
	}

	/** 
	 * Changes coordinates depending on direction and distance
	 * @param direction 0-7, refers to cardinal directions
	 * @param distance how many steps to move
	 */
	private void coordChange(int direction, int distance) {
        switch(direction) {
            case 0: x_coord += distance; break;
            case 1: x_coord += distance; y_coord -= distance; break;
            case 2: y_coord -= distance; break;
            case 3: x_coord -= distance; y_coord -= distance; break;
            case 4: x_coord -= distance; break;
            case 5: x_coord -= distance; y_coord += distance; break;
            case 6: y_coord += distance; break;
            case 7: x_coord += distance; y_coord += distance; break;
        }
        x_coord %= Params.world_width; 
        if(x_coord < 0)
        	x_coord += Params.world_width;
        y_coord %= Params.world_height; 
        if(y_coord < 0)
        	y_coord += Params.world_height;
    }

}
