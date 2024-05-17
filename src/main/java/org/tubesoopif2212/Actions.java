package org.tubesoopif2212;

import org.tubesoopif2212.Plants.*;
import org.tubesoopif2212.Tile.*;
import org.tubesoopif2212.Zombies.*;

import java.util.List;
import java.util.Random;

public class Actions implements ZombieFactory{

    public ZombieFactory.zombieTypeWater[] typesWater = ZombieFactory.zombieTypeWater.values();
    public ZombieFactory.zombieTypeLand[] typesLand = ZombieFactory.zombieTypeLand.values();

	public void moveZombie(int row, List<Zombies> zombies) {
        for(Zombies zombie : zombies){
            if((gameLoop.seconds - zombie.getTimeCreated()) % 5 != 0){
                continue;
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
                        } else if(leftTile.getPlant() != null && zombie.getSlowed()==true){
                            int waktu = gameLoop.seconds;
                            while(gameLoop.seconds - waktu <= 3){
                                if((gameLoop.seconds - zombie.getTimeCreated()) % 10 != 0){
                                    continue;
                                }
                            }
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

        for (int i = row; i < max_range; i++) {
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
                zombie.setSlowed(true);
                zombie.setAttackSpeed(zombie.getAttackSpeed()/2);
                zombie.setStatusEffect(1);
            }

            zombie.setHealth(zombie.getHealth() - plant.getAttackDamage());
            if(zombie.getHealth() > 0){
                continue;
            }
            tile.removeZombie(zombie);
            Zombies.amount--;
        }
        plant.setAttackCooldown(plant.getAttackSpeed() - 1);
    }

    public void attackZombie(Tile tile, Map map, int row, int column){
        for (Zombies zombie : tile.getZombies()) {
            if(gameLoop.seconds > zombie.getTimeCreated() && (gameLoop.seconds - zombie.getTimeCreated()) % zombie.getAttackSpeed() == 0){
                tile.getPlant().setHealth(tile.getPlant().getHealth() - zombie.getAttackDamage());
            }
            if(tile.getPlant().getHealth() <= 0){
                map.dig(row, column);
                break;
            }
        }
    }

    public void spawnRandomZombies(Tile tile){
        // random from length of array zombie
        if(Zombies.amount >= 10){
            return;
        }

        Random random = new Random();
        if(tile.getWater()){
            ZombieFactory.zombieTypeWater randomTypeWater = typesWater[random.nextInt(typesWater.length)];
            switch (randomTypeWater) {
                case DolphinRider:
                    tile.addZombie(new DolphinRider(gameLoop.seconds));
                    break;

                case DuckyTube:
                    tile.addZombie(new DuckyTube(gameLoop.seconds));
                    break;
                default:
                    break;
            }
        }
        else{
            ZombieFactory.zombieTypeLand randomTypeLand = typesLand[random.nextInt((typesLand.length))];
            switch (randomTypeLand) {
                case Normal:
                    tile.addZombie(new Normal(gameLoop.seconds));
                    break;
            
                case Conehead:
                    tile.addZombie(new Conehead(gameLoop.seconds));
                    break;

                case Buckethead:
                    tile.addZombie(new Buckethead(gameLoop.seconds));
                    break;

                case PoleVaulting:
                    tile.addZombie(new PoleVaulting(gameLoop.seconds));
                    break;
            
                case KureijiOllie:
                    tile.addZombie(new KureijiOllie(gameLoop.seconds));
                    break;
            
                case Qiqi:
                    tile.addZombie(new Qiqi(gameLoop.seconds));
                    break;
            
                case ShrekButZombie:
                    tile.addZombie(new ShrekButZombie(gameLoop.seconds));
                    break;
            
                case EntireZom100Cast:
                    tile.addZombie(new EntireZom100Cast(gameLoop.seconds));
                    break;
                    
                default:
                    break;
            }
        }
        Zombies.amount++;
    }

    public void jump(int row, int col, Zombies zombie){
        Tile currentTile = Map.getTile(row, col);
        Tile leftTile = Map.getTile(row, col - 1);
        Tile secondLeftTile = Map.getTile(row, col - 2);

        leftTile.setPlant(null);
        secondLeftTile.addZombie(zombie);
        currentTile.removeZombie(zombie);
        zombie.setNextHop(false);
    }
}
