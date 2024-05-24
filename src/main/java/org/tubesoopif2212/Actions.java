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

        int maxRange = plant.getRange() == -1 ? 10 : (plant.getRange() + row) >= 10 ? 10 : (plant.getRange() + row);
        boolean hasZombies = false;
        Tile tile = null;

        for (int i = row; i <= maxRange; i++) {
            tile = Map.getTile(column, i);
            if (!tile.getZombies().isEmpty()) {
                hasZombies = true;
                break;
            }
        }
    
        if (!hasZombies) {
            if(plant instanceof Planterra){
                for (int i = row + 1; i <= maxRange; i++) {
                    Tile frontTile = Map.getTile(column, i);
                    if (frontTile.getPlant() != null) {
                        Plants frontPlant = frontTile.getPlant();
                        frontPlant.setHealth(frontPlant.getHealth() - 25);
                        if(frontPlant.getHealth() <= 0){
                            Map.removePlant(i, column);
                        }
                        break;
                    }
                }
            }
            return;
        }

        if(plant.getAOE()){
            for (int i = Math.max(0, row - 1); i <= Math.min(9, row + 1); i++) {
                for (int j = Math.max(0, column - 1); j <= Math.min(9, column + 1); j++) {
                    Tile aoeTile = Map.getTile(j, i);
                    for (Zombies zombie : aoeTile.getZombies()) {
                        int damage = plant.getAttackDamage();

                        if(plant.getX2Damage()){
                            zombie.setX2Damage(true);
                        }
        
                        if(zombie.getX2Damage()){
                            damage *= 2;
                        }
            
                        zombie.setHealth(zombie.getHealth() - damage);
                        if(zombie.getHealth() > 0){
                            continue;
                        }
                        tile.removeZombie(zombie);
                        Zombies.amount--;
                    }
                }
            }
        } 
        else{
            for (Zombies zombie : tile.getZombies()) {
                int damage = plant.getAttackDamage();

                if(plant instanceof Snowpea){
                    slowed(zombie);
                }

                if(plant.getX2Damage()){
                    zombie.setX2Damage(true);
                }

                if(zombie.getX2Damage()){
                    damage *= 2;
                }
    
                zombie.setHealth(zombie.getHealth() - damage);
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
