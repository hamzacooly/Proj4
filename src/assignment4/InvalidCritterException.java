package assignment4;
/* EE422C Project 4 submission by
 * Hamza Khatri
 * hak533
 * 16220
 * Slip days used: <0>
 * Mohammad Kedwaii
 * mak3799
 * 16238
 * Slip days used: <0>
 * Fall 2016
 */

public class InvalidCritterException extends Exception {
	String offending_class;
	
	public InvalidCritterException(String critter_class_name) {
		offending_class = critter_class_name;
	}
	
	public String toString() {
		return "Invalid Critter Class: " + offending_class;
	}

}
