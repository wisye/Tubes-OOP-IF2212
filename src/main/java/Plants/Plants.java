package Plants;

public abstract class Plants {
    private String name;
    private Integer cost;
    private Integer health;
    private Integer attack_damage;
    private Integer attack_speed;
    private Integer range;
    private Integer cooldown;

    public Plants(String name, Integer cost, Integer health, Integer attack_damage, Integer attack_speed, Integer range, Integer cooldown){
        this.name = name;
        this.cost = cost;
        this.health = health;
        this.attack_damage = attack_damage;
        this.attack_speed = attack_speed;
        this.range = range;
        this.cooldown = cooldown;
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

    public Integer getAttack_damage() {
        return attack_damage;
    }

    public Integer getAttack_speed() {
        return attack_speed;
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

    public void setAttack_damage(Integer attack_damage) {
        this.attack_damage = attack_damage;
    }

    public void setAttack_speed(Integer attack_speed) {
        this.attack_speed = attack_speed;
    }

    public void setRange(Integer range) {
        this.range = range;
    }

    public void setCooldown(Integer cooldown) {
        this.cooldown = cooldown;
    }
}
