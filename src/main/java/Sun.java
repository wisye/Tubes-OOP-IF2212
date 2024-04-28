public class Sun {
    private static Integer amount = 25;

    public static Integer getAmount() {
        return amount;
    }

    public static synchronized void addSun(){
        amount += 25;
    }
}
