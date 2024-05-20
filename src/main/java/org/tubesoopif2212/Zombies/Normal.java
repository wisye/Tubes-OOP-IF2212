package org.tubesoopif2212.Zombies;

public class Normal extends Zombies{


    public Normal(int timeCreated){
        super("Normal Zombie", 125, 100, 1, false, timeCreated);
    }
    public Normal(){
        super("Normal Zombie", 125, 100, 1, false);
    }
}
