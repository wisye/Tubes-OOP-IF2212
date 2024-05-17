package org.tubesoopif2212.Zombies;

public class PoleVaulting extends Zombies{


    public PoleVaulting(int timeCreated){
        super("Pole Vaulting Zombie", 175, 100, 1, false, timeCreated);
        setNextHop(true);
    }
}
