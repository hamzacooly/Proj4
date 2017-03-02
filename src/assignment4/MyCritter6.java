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
 * Spring 2017
 */

import assignment4.Critter.TestCritter;

public class MyCritter6 extends TestCritter {
	
	@Override
	public void doTimeStep() {
	}

	@Override
	public boolean fight(String opponent) {
		run(getRandomInt(8));
		return false;
	}

	@Override
	public String toString () {
		return "5";
	}
}
