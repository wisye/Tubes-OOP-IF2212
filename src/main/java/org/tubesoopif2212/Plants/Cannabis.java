package org.tubesoopif2212.Plants;

// THE MOST OP PLANT IN THE GAME
// attacks in a 3x3 radius
public class Cannabis extends Plants{
	public Cannabis(int timeCreated){
		super("Cannabis", 500, 1, 1000, 3, 3, 10, timeCreated);
		super.setAOE(true);
	}
}
