package org.tubesoopif2212.Zombies;

public abstract class Zombies {
    private String name;
    private Integer health;
    private Integer attackDamage;
    private Integer attackSpeed;
    private Boolean isAquatic;
    private int timeCreated;
    private int statusEffect = 0;
    private int ability = 0;
    private boolean slowed = false;
    
    public static int amount = 0;
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

    public void setStatusEffect(int a){
        statusEffect = a;
    }

    public void setSlowed(boolean slowed) {
        this.slowed = slowed;
    }

    public boolean getSlowed(){
        return slowed;
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

    public void setNextHop(boolean hop){
        ability = hop ? (ability | 1) : (ability & ~1);
    }

    public boolean getNextHop(){
        return ((ability & 1) == 1);
    }
}