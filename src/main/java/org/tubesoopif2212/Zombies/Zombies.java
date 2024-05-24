package org.tubesoopif2212.Zombies;
import java.util.List;

import java.util.ArrayList;

public abstract class Zombies {
    private String name;
    private Integer health;
    private Integer attackDamage;
    private Integer attackSpeed;
    private Boolean isAquatic;
    private int timeCreated;
    private int statusEffect = 0;
    private int slowedTime;
    private int ability = 0;
    public static List<Zombies> zoms = new ArrayList<Zombies>();
    
    public static int amount = 0;
    public static int maxAmount = 10;
    // Bit 0 = slowed
    // Bit 1 = 

    public Zombies(String name, Integer health, Integer attackDamage, Integer attackSpeed, Boolean isAquatic, int timeCreated) {
        this.name = name;
        this.health = health;
        this.attackDamage = attackDamage;
        this.attackSpeed = attackSpeed;
        this.isAquatic = isAquatic;
        this.timeCreated = timeCreated;
    }
    public Zombies(String name, Integer health, Integer attackDamage, Integer attackSpeed, Boolean isAquatic) {
        this.name = name;
        this.health = health;
        this.attackDamage = attackDamage;
        this.attackSpeed = attackSpeed;
        this.isAquatic = isAquatic;
    }

    public void setStatusEffect(int a){
        statusEffect = a;
    }

    public String getName() {
        return name;
    }

    public Integer getHealth() {
        return health;
    }

    public Integer getAttackDamage() {
        return attackDamage;
    }

    public Integer getAttackSpeed() {
        return attackSpeed;
    }

    public Boolean getIsAquatic() {
        return isAquatic;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHealth(Integer health) {
        this.health = health;
    }

    public void setAttackDamage(Integer attackDamage) {
        this.attackDamage = attackDamage;
    }

    public void setAttackSpeed(Integer attackSpeed) {
        this.attackSpeed = attackSpeed;
    }

    public void setIsAquatic(Boolean isAquatic) {
        this.isAquatic = isAquatic;
    }

    public int getTimeCreated(){
        return timeCreated;
    }

    public void setSlowed(boolean slowed) {
        statusEffect = slowed ? (statusEffect | 1) : (statusEffect & ~1);
    }

    public boolean getSlowed(){
        return ((statusEffect & 1) == 1);
    }

    public void setSlowedTime(int slowedTime) {
        this.slowedTime = slowedTime;
    }

    public int getSlowedTime() {
        return slowedTime;
    }


    public void setNextHop(boolean hop){
        ability = hop ? (ability | 1) : (ability & ~1);
    }

    public boolean getNextHop(){
        return ((ability & 1) == 1);
    }

    public static void addZombie(){
        zoms.add(new Normal());
        zoms.add(new ConeheadZombie());
        zoms.add(new BucketheadZombie());
        zoms.add(new PoleVaulting());
        zoms.add(new KureijiOllie());
        zoms.add(new Qiqi());
        zoms.add(new ShrekButZombie());
        zoms.add(new EntireZom100Cast());
        zoms.add(new DolphinRiderZombie());
        zoms.add(new DuckyTube());
    }

    public static String toString(Zombies zom) {
        String ret = new String();
        String separator = "--------------------------------------";
        String format = "%-15s | %s\n";
        
        ret += separator + "\n";
        ret += String.format(format, "Nama Atribut", "Keterangan");
        ret += separator + "\n";
        ret += String.format(format, "Name", zom.getName());
        ret += separator + "\n";
        ret += String.format(format, "Health", zom.getHealth());
        ret += separator + "\n";
        ret += String.format(format, "Attack Damage", zom.getAttackDamage());
        ret += separator + "\n";
        ret += String.format(format, "Attack Speed", zom.getAttackSpeed());
        ret += separator + "\n";
        ret += String.format(format, "Is aquatic", zom.getIsAquatic());
        ret += separator;
        
        return ret;
    }    
}