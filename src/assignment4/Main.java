package assignment4;
/* CRITTERS Main.java
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
import java.io.*;
import java.lang.reflect.*;


/*
 * Usage: java <pkgname>.Main <input file> test
 * input file is optional.  If input file is specified, the word 'test' is optional.
 * May not use 'test' argument without specifying input file.
 */
public class Main {

    static Scanner kb;	// scanner connected to keyboard input, or input file
    private static String inputFile;	// input file, used instead of keyboard input if specified
    static ByteArrayOutputStream testOutputString;	// if test specified, holds all console output
    private static String myPackage;	// package of Critter file.  Critter cannot be in default pkg.
    private static boolean DEBUG = false; // Use it or not, as you wish!
    static PrintStream old = System.out;	// if you want to restore output to console


    // Gets the package name.  The usage assumes that Critter and its subclasses are all in the same package.
    static {
        myPackage = Critter.class.getPackage().toString().split(" ")[1];
    }

    /**
     * Main method.
     * @param args args can be empty.  If not empty, provide two parameters -- the first is a file name, 
     * and the second is test (for test output, where all output to be directed to a String), or nothing.
     */
    public static void main(String[] args) { 
        if (args.length != 0) {
            try {
                inputFile = args[0];
                kb = new Scanner(new File(inputFile));			
            } catch (FileNotFoundException e) {
                System.out.println("USAGE: java Main OR java Main <input file> <test output>");
                e.printStackTrace();
            } catch (NullPointerException e) {
                System.out.println("USAGE: java Main OR java Main <input file>  <test output>");
            }
            if (args.length >= 2) {
                if (args[1].equals("test")) { // if the word "test" is the second argument to java
                    // Create a stream to hold the output
                    testOutputString = new ByteArrayOutputStream();
                    PrintStream ps = new PrintStream(testOutputString);
                    // Save the old System.out.
                    old = System.out;
                    // Tell Java to use the special stream; all console output will be redirected here from now
                    System.setOut(ps);
                }
            }
        } else { // if no arguments to main
            kb = new Scanner(System.in); // use keyboard and console
        }

        /* Do not alter the code above for your submission. */
        /* Write your code below. */
        
        while(true){
        	System.out.print("critters>");
        	String input = kb.nextLine();
        	StringTokenizer tok = new StringTokenizer(input);
        	String input1 = tok.nextToken();
            if(input.equals("quit")){
            	break;
            }
            else if(input.equals("show")){
            	Critter.displayWorld();
            }
            else if(input.matches("step \\d+")){
            	int steps = Integer.parseInt(tok.nextToken());
            	for(int k = 0; k < steps; k++){
            		Critter.worldTimeStep();
            	}
            }
            else if(input.equals("step")){
            	Critter.worldTimeStep();
            }
            else if(input.matches("seed \\d+")){
            	int seed = Integer.parseInt(tok.nextToken());
            	Critter.setSeed(seed);
            }
            else if(input.matches("make \\w+")){
            	try{
            		Critter.makeCritter(tok.nextToken());
            	}
            	catch(Exception e){
            		System.out.println("error processing: " + input);
        			continue;
            	}
            }
            else if(input.matches("make \\w+ \\d+")){
            	String name = tok.nextToken();
            	int num = Integer.parseInt(tok.nextToken());
            	// See if input is valid.
            	try{
        			Critter.makeCritter(name);
        		}
        		catch(Exception e){
        			System.out.println("error processing: " + input);
        			continue;
        		}
            	
            	for(int k = 1; k < num; k++){
            		try{
            			Critter.makeCritter(name);
            		}
            		catch(Exception e){
            		}
            	}
            }
            else if(input.matches("stats \\w+")){
            	List<Critter> critters;
            	String name = tok.nextToken();
            	try{
            		critters = Critter.getInstances(name);
            	}
            	catch(Exception e){
            		System.out.println("error processing: " + input);
            		continue;
            	}
            	Class<?> myCritter = null;
        		try {
        			myCritter = Class.forName(myPackage + "." + name); 	// Class object of specified name
        		} catch (ClassNotFoundException e) {
        			continue;
        		}
        		try{
        			Method method = myCritter.getMethod("runStats", List.class);
        			method.invoke(null, critters);
        		}
        		catch(Exception e){
        			Critter.runStats(critters);
        		}
            }
            else{
        		System.out.println("error processing: " + input);
        		continue;
            }
        }
        
        
        /* Write your code above */
        System.out.flush();

    }
}
