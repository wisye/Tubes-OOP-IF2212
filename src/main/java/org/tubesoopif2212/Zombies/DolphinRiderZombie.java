package org.tubesoopif2212.Zombies;

public class DolphinRiderZombie extends Zombies{


    public DolphinRiderZombie(int timeCreated){
        super("DolphinRiderZombie", 175, 100, 1, true, timeCreated);
        setNextHop(true);
    }
    public DolphinRiderZombie(){
        super("DolphinRiderZombie", 175, 100, 1, true);
        setNextHop(true);
    }
}
