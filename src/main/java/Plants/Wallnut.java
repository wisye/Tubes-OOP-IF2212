package Plants;

public class Wallnut extends Plants{


    public Wallnut(int timeCreated){
        super("Wall nut", 50, 1000, 0, 0, 0, 20, timeCreated);
    }

    public Wallnut create(int timeCreated){
        return new Wallnut(timeCreated);
    }
}
