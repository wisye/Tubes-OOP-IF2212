package org.tubesoopif2212.Zombies;

public abstract class Zombies {
    private String name;
    private Integer health;
    private Integer attackDamage;
    private Integer attackSpeed;
    private Boolean isAquatic;
    private int timeCreated;
    private int statusEffect = 0;
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

    public void setSlowed(boolean slow){
        statusEffect = slow ? (statusEffect | 1) : (statusEffect & ~1);
    }

    public boolean getSlowed(){
        return ((statusEffect & 1) == 1);
    }
}