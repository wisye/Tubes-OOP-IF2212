package org.tubesoopif2212;

import org.tubesoopif2212.Plants.*;
import org.tubesoopif2212.Tile.*;
import org.tubesoopif2212.Zombies.*;

import java.util.List;
import java.util.Random;

public class Actions{

    private int moveTime = 10;

	public void moveZombie(int row, List<Zombies> zombies) {
        for(Zombies zombie : zombies){

            if(zombie.getSlowed()){
                if(gameLoop.seconds - zombie.getSlowedTime() >= 3){
                    zombie.setSlowed(false);
                    zombie.setAttackSpeed(zombie.getAttackSpeed() * 2 / 3);
                }
                if(zombie.getSlowed() && ((gameLoop.seconds - zombie.getTimeCreated()) % (moveTime * 2) != 0)){
                    continue;
                }
            } else {
                if(!zombie.getSlowed() && ((gameLoop.seconds - zombie.getTimeCreated()) % moveTime != 0)){
                    continue;
                }
            }

            for (int col = 0; col < 11; col++) {
                Tile tile = Map.getTile(row, col);
                synchronized(tile){
                    if (!tile.getZombies().contains(zombie)) {
                        continue;
                    }
                    if (col > 0) {
                        Tile leftTile = Map.getTile(row, col - 1);
                        if(leftTile.getPlant() != null && (zombie instanceof PoleVaulting || zombie instanceof DolphinRider)){
                            jump(row, col, zombie);
                        }
                        else{
                            leftTile.addZombie(zombie);
                            tile.removeZombie(zombie);
                        }
                    }
                    break;
                }
            }
        }
	}

    public void attackPlant(int row, int column, Plants plant){
        if (plant.getAttackDamage() == 0) {
            return;
        }

        if(plant.getAttackCooldown() > 0){
            plant.setAttackCooldown(plant.getAttackCooldown() - 1);
            return;
        }

        int max_range = plant.getRange() == -1 ? 10 : (plant.getRange() + row) >= 10 ? 10 : (plant.getRange() + row);
        boolean hasZombies = false;
        Tile tile = null;

        for (int i = row; i <= max_range; i++) {
            tile = Map.getTile(column, i);
            if (!tile.getZombies().isEmpty()) {
                hasZombies = true;
                break;
            }
        }
    
        if (!hasZombies) {
            return;
        }

        for (Zombies zombie : tile.getZombies()) {
            if(plant instanceof Snowpea){
                slowed(zombie);
            }

            zombie.setHealth(zombie.getHealth() - plant.getAttackDamage());
            if(zombie.getHealth() > 0){
                continue;
            }
            tile.removeZombie(zombie);
            Zombies.amount--;
        }

        if(plant.getInstant()){
            Map.removePlant(row, column);
        }

        plant.setAttackCooldown(plant.getAttackSpeed() - 1);
    }

    public void attackZombie(Tile tile, Map map, int row, int column){
        for (Zombies zombie : tile.getZombies()) {
            if(gameLoop.seconds > zombie.getTimeCreated() && (gameLoop.seconds - zombie.getTimeCreated()) % zombie.getAttackSpeed() == 0){
                tile.getPlant().setHealth(tile.getPlant().getHealth() - zombie.getAttackDamage());
            }
            if(tile.getPlant().getHealth() <= 0){
                Map.deletePlant(row, column);
                break;
            }
        }
    }

    public void jump(int row, int col, Zombies zombie){
        Tile currentTile = Map.getTile(row, col);
        Tile secondLeftTile = Map.getTile(row, col - 2);

        secondLeftTile.setPlant(null);
        secondLeftTile.addZombie(zombie);
        currentTile.removeZombie(zombie);
        zombie.setNextHop(false);
    }

    public void slowed(Zombies zombie){
        if(!zombie.getSlowed()){
            zombie.setSlowed(true);
            zombie.setAttackSpeed(zombie.getAttackSpeed() + zombie.getAttackSpeed()/2);
            zombie.setSlowedTime(gameLoop.seconds);
        }
    }

    public void addSun(Sunflower sunflower){
        if((sunflower.getTimeCreated() - gameLoop.seconds) % 3 == 0){
            Sun.addSun(25);
        }
    }
}
