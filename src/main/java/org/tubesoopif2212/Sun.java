package org.tubesoopif2212;

public class Sun {
    private static Sun sun;
    private static int amount;

    private Sun(){
        amount = 25;
    }

    public static Sun getInstance(){
        if(sun == null){
            sun = new Sun();
        }
        return sun;
    }

    public static int getAmount() {
        return amount;
    }

    public static synchronized void addSun(int amount){
        Sun.amount += amount;
    }

    public static void reduceSun(int amount){
        Sun.amount -= amount;
    }

    public static void setSun(int amount){
        Sun.amount = amount;
    }
}
