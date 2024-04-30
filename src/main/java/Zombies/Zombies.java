package Zombies;

public abstract class Zombies {
    private String name;
    private Integer health;
    private Integer attack_damage;
    private Integer attack_speed;
    private Boolean is_aquatic;
    private int currentCol = 10;
    private int timeCreated;

    public Zombies(String name, Integer health, Integer attack_damage, Integer attack_speed, Boolean is_aquatic, int timeCreated) {
        this.name = name;
        this.health = health;
        this.attack_damage = attack_damage;
        this.attack_speed = attack_speed;
        this.is_aquatic = is_aquatic;
        this.timeCreated = timeCreated;
    }

    public String getName() {
        return name;
    }

    public Integer getHealth() {
        return health;
    }

    public Integer getAttack_damage() {
        return attack_damage;
    }

    public Integer getAttack_speed() {
        return attack_speed;
    }

    public Boolean getIs_aquatic() {
        return is_aquatic;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHealth(Integer health) {
        this.health = health;
    }

    public void setAttack_damage(Integer attack_damage) {
        this.attack_damage = attack_damage;
    }

    public void setAttack_speed(Integer attack_speed) {
        this.attack_speed = attack_speed;
    }

    public void setIs_aquatic(Boolean is_aquatic) {
        this.is_aquatic = is_aquatic;
    }

    public int getCurrentCol(){
        return currentCol;
    }

    public void moveLeft(){
        currentCol--;
    }

    public int getTimeCreated(){
        return timeCreated;
    }
}