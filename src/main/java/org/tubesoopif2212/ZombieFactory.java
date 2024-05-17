package org.tubesoopif2212;

import org.tubesoopif2212.Tile.Tile;

public interface ZombieFactory{
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

	public void spawnRandomZombies(Tile tile);
}
