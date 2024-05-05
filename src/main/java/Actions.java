import Tile.*;
import Plants.*;
import Zombies.*;
import java.util.List;

public class Actions{
    // public void run(){
        
    // }

    private static final String[] zombieList = {"Normal", "Conehead"};

	public void moveZombie(int row, List<Zombies> zombies) {
        for(Zombies zombie : zombies){
            if(Main.seconds > zombie.getTimeCreated() && (Main.seconds - zombie.getTimeCreated()) % 5 == 0){
                for (int col = 0; col < 11; col++) {
                    Tile tile = Map.getTile(row, col);
                    synchronized(tile){
                        if (tile.getZombies().contains(zombie)) {
                        // Move the zombie to the left
                            if (col > 0) {
                                Tile leftTile = Map.getTile(row, col - 1);
                                leftTile.addZombie(zombie);
                                tile.removeZombie(zombie);
                            }
                            break;
                        }
                    }
                }
            }
        }
	}

    public void attackPlant(int row, int column, Plants plant){
        if (plant.getRange() == -1) {
            if((Main.seconds - plant.getTimeCreated()) % plant.getAttackSpeed() == 0){
                for (int i = row; i < 11; i++) {
                    Tile tile = Map.getTile(column, i);
                    if (!tile.getZombies().isEmpty()) {
                        for (Zombies zombie : tile.getZombies()) {
                            zombie.setHealth(zombie.getHealth() - plant.getAttackDamage());
                            if(zombie.getHealth() <= 0){
                                tile.removeZombie(zombie);
                            }
                        }
                        break;
                    }
                }
            }
        }
    }

    public void attackZombie(Tile tile, Map map, int row, int column){
        for (Zombies zombie : tile.getZombies()) {
            if(Main.seconds > zombie.getTimeCreated() && (Main.seconds - zombie.getTimeCreated()) % zombie.getAttackSpeed() == 0){
                tile.getPlant().setHealth(tile.getPlant().getHealth() - zombie.getAttackDamage());
            }
            if(tile.getPlant().getHealth() <= 0){
                map.dig(row, column);
                break;
            }
        }
    }

    // public void spawnRandomZombies(){
    //     // random from length of array zombie
    //     result = zombieList[i];
    //     switch (result) {
    //         case "normal":
    //             new Normal
    //             break;
            
    //         case "Conehead":
    //             new Conehead
    //         default:
    //             break;
    //     }
    // }
}
