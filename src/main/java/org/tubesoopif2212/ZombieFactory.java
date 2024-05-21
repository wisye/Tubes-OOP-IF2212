package org.tubesoopif2212;

import org.tubesoopif2212.Tile.Tile;
import org.tubesoopif2212.Zombies.*;
import java.util.Random;

public class ZombieFactory {
	public enum zombieTypeLand {
		Normal,
		Conehead,
		Buckethead,
		PoleVaulting,
		KureijiOllie,
		Qiqi,
		ShrekButZombie,
		EntireZom100Cast,
	};

	public enum zombieTypeWater {
		DolphinRider,
		DuckyTube,
	}

	public ZombieFactory.zombieTypeWater[] typesWater = ZombieFactory.zombieTypeWater.values();
	public ZombieFactory.zombieTypeLand[] typesLand = ZombieFactory.zombieTypeLand.values();

	public void spawnRandomZombies(Tile tile) {
		// random from length of array zombie
		if (Zombies.amount >= Zombies.maxAmount) {
			return;
		}

		Random random = new Random();
		if (tile.getWater()) {
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
		else {
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

	public void flag(){
		Random random = new Random();
		for (int i = 0; i < 6; i++) {
			Tile tile = Map.getTile(i, 10);
			if (random.nextFloat() < 0.9) {
			    spawnRandomZombies(tile);
			}
		}	
	}

	public void spawnZombies(){
		Random random = new Random();
		for (int i = 0; i < 6; i++) {
			Tile tile = Map.getTile(i, 10);
			if (random.nextFloat() < 0.3) {
			    spawnRandomZombies(tile);
			}
		}
	}
}
