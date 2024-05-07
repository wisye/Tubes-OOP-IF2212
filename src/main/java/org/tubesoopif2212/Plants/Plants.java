package org.tubesoopif2212.Plants;

public abstract class Plants {
    private String name;
    private Integer cost;
    private Integer health;
    private Integer attackDamage;
    private Integer attackSpeed;
    private Integer range;
    private Integer cooldown;
    private int timeCreated = 0;

    public Plants(String name, Integer cost, Integer health, Integer attackDamage, Integer attackSpeed, Integer range, Integer cooldown, int timeCreated){
        this.name = name;
        this.cost = cost;
        this.health = health;
        this.attackDamage = attackDamage;
        this.attackSpeed = attackSpeed;
        this.range = range;
        this.cooldown = cooldown;
        this.timeCreated = timeCreated;
    }

    public String getName() {
        return name;
    }

    public Integer getCost() {
        return cost;
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

    public Integer getRange() {
        return range;
    }

    public Integer getCooldown() {
        return cooldown;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
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

    public void setRange(Integer range) {
        this.range = range;
    }

    public void setCooldown(Integer cooldown) {
        this.cooldown = cooldown;
    }

    public int getTimeCreated(){
        return timeCreated;
    }

    public void setTimeCreated(int timeCreated){
        this.timeCreated = timeCreated;
    }
}