package org.tubesoopif2212.Plants;

public abstract class Plants {
    private String name;
    private Integer cost;
    private Integer health;
    private Integer attackDamage;
    private Integer attackSpeed;
    private Integer range;
    private Integer cooldown;
    private Integer attackCooldown = 0;
    private Integer timeCreated = 0;
    private int ability = 0;

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

    public Integer getAttackCooldown(){
        return attackCooldown;
    }

    public void setAttackCooldown(Integer cooldown){
        this.attackCooldown = cooldown;
    }

    public void setInstant(boolean instant) {
        ability = instant ? (ability | 1) : (ability & ~1);
    }

    public boolean getInstant(){
        return ((ability & 1) == 1);
    }

    public void setAOE(boolean aoe){
        ability = aoe ? (ability | 2) : (ability & ~2);
    }

    public boolean getAOE(){
        return ((ability & 2) == 1);
    }

    public void setX2Damage(boolean x2Damage){
        ability = x2Damage ? (ability | 4) : (ability & ~4);
    }

    public boolean getX2Damage(){
        return ((ability & 4) == 1);
    }
}