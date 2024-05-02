/*Inventory merupakan container yang berisikan tanaman tanaman yang dapat diakses oleh pemain. Sebelum permainan dimulai, pemain akan diberikan pilihan untuk memilih tanaman yang akan digunakan dalam permainan tersebut. Pemain dapat memilih, menukar posisi, dan menghapus tanaman yang dipilih untuk digunakan dalam deck tanaman.

+Aksi
-Deskripsi
+Memilih tanaman
-Mengubah slot pada deck tanaman menjadi tanaman yang dipilih. Dan memastikan tanaman yang dipilih tidak bisa dipilih kembali
+Menukar posisi tanaman
-Menukar posisi tanaman pada slot deck maupun inventory. Tidak akan ada penukaran antar slot deck dan inventory. Pastikan posisi tidak bisa ditukar dengan posisi sendiri ataupun posisi kosong.
+Menghapus tanaman
-Mengubah slot pada deck tanaman yang berisikan tanaman menjadi kosong. Pastikan slot yang dipilih untuk dihapus tidak kosong.


Note: Urutan tanaman pada inventory hanya dapat diubah dengan aksi “menukar posisi tanaman”  */


import java.util.Map;
import java.util.HashMap;
import Plants.*;

public class Inventory {
    private Map<Deck.PlantFactory<? extends Plants>, Boolean> inventory;

    public Inventory(){
        this.inventory = new HashMap<>();
    }

    //Memilih tanaman
    //Mengubah slot pada deck tanaman menjadi tanaman yang dipilih. Dan memastikan tanaman yang dipilih tidak bisa dipilih kembali
    public void choosePlant(Deck.PlantFactory<? extends Plants> plants, Deck<? extends Plants> deck){
        for (Map.Entry<Deck.PlantFactory<? extends Plants>, Boolean> entry : inventory.entrySet()) {
            if(entry.getKey().equals(plants) && entry.getValue()){
                throw new IllegalStateException("Plant already chosen");
            } else if(entry.getKey().equals(plants) && !entry.getValue()){
                deck.add(plants);
            } else {
                throw new IllegalStateException("Plant not found");
            }
        } 
    }

    //Menukar posisi tanaman
    //Menukar posisi tanaman pada slot deck maupun inventory. Tidak akan ada penukaran antar slot deck dan inventory. Pastikan posisi tidak bisa ditukar dengan posisi sendiri ataupun posisi kosong.
    public void swapPlant(Deck<? extends Plants> deck, int slot1, int slot2){
        if (deck.get(slot1) == deck.get(slot2)){
            throw new IllegalStateException("Cannot swap with the same slot");
        } else if (deck.get(slot1) == null || deck.get(slot2) == null){
            throw new IllegalStateException("Cannot swap with empty slot");
        } else {
            Deck.PlantFactory<? extends Plants> temp = deck.get(slot1);
            deck.add(deck.get(slot2));
            deck.add(temp);
        } 
    }

    //Menghapus tanaman
    //Mengubah slot pada deck tanaman yang berisikan tanaman menjadi kosong. Pastikan slot yang dipilih untuk dihapus tidak kosong.
    public void removePlant(int slot, Deck<? extends Plants> deck){
        if(deck.get(slot) != null){
            deck.remove(deck.get(slot));
        } else {
            throw new IllegalStateException("Slot is empty");
        }
    }
}
