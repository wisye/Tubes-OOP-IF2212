import Tile.*;

public class Map {
    private Tile[][] tiles;

    public Map(){
        tiles = new Tile[6][11];
        for (int i = 0; i < 6; i++) {
            tiles[i][0] = new ProtectedArea();
        }
        for(int i = 0; i < 2; i++){
            for(int j = 1; j < 10; j++){
                tiles[i][j] = new Grass();
            }
        }
        for(int i = 2; i < 4; i++){
            for(int j = 1; j < 10; j++){
                tiles[i][j] = new Water();
            }
        }
        for(int i = 4; i < 6; i++){
            for(int j = 1; j < 10; j++){
                tiles[i][j] = new Grass();
            }
        }
        for (int i = 0; i < 6; i++) {
            tiles[i][10] = new SpawnArea();
        }
    }

    public void printMap() {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                System.out.print(tiles[i][j].toString() + " ");
            }
            System.out.println();
        }
    }

}
