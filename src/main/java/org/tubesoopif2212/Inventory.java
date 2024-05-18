package org.tubesoopif2212;

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

import org.tubesoopif2212.Plants.*;

import kotlin.OverloadResolutionByLambdaReturnType;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Inventory {
    private Map<Plants, Boolean> inventory;

    public Inventory() {
        this.inventory = new LinkedHashMap<>();
        this.inventory.put(new Sunflower(0), true);
        this.inventory.put(new Peashooter(0), true);
        this.inventory.put(new Wallnut(0), true);
        this.inventory.put(new Snowpea(0), true);
        this.inventory.put(new Squash(0), true);
        this.inventory.put(new Lilypad(0), true);
        this.inventory.put(new Nahida(0), true);
        this.inventory.put(new CeresFauna(0), true);
        this.inventory.put(new Cannabis(0), true);
        this.inventory.put(new Planterra(0), true);
    }

    // Memilih tanaman
    // Mengubah slot pada deck tanaman menjadi tanaman yang dipilih. Dan memastikan
    // tanaman yang dipilih tidak bisa dipilih kembali
    public void choosePlant(Plants plants, Deck<?> deck) throws Exception {
        for (Map.Entry<Plants, Boolean> entry : inventory.entrySet()) {
            if (entry.getKey().getName().equals(plants.getName())) {
                if (entry.getValue()) {
                    deck.add(plants);
                    entry.setValue(false);
                    return;
                } else {
                    throw new Exception("Plant already chosen");
                }
            }
        }
    }

    // Menukar posisi tanaman
    // Menukar posisi tanaman pada slot deck maupun inventory. Tidak akan ada
    // penukaran antar slot deck dan inventory. Pastikan posisi tidak bisa ditukar
    // dengan posisi sendiri ataupun posisi kosong.
    public void swapPlant(int slot1, int slot2, Deck<?> deck) throws Exception {
        if (slot1 == slot2) {
            throw new Exception("Cannot swap with the same slot");
        } else if (deck.get(slot1) == null || deck.get(slot2) == null) {
            throw new Exception("Cannot swap with empty slot");
        } else {
            deck.swap(slot1, slot2);
        }
    }

    // Menghapus tanaman
    // Mengubah slot pada deck tanaman yang berisikan tanaman menjadi kosong.
    // Pastikan slot yang dipilih untuk dihapus tidak kosong.
    public void removePlant(int slot, Deck<?> deck) throws Exception {
        if (deck.get(slot) != null) {
            Plants plant = deck.get(slot).create(0);
            deck.remove(deck.get(slot));
            for (Map.Entry<Plants, Boolean> entry : inventory.entrySet()) {
                if (entry.getKey().getName().equals(plant.getName())) {
                    entry.setValue(true);
                    return;
                }
            }
        } else {
            throw new Exception("Slot is empty");
        }
    }

    public int size() {
        return inventory.size();
    }

    public Plants get(int i) {
        List<Plants> plants = new ArrayList<>(inventory.keySet());
        if (i >= 0 && i < plants.size()) {
            return plants.get(i);
        }
        throw new IndexOutOfBoundsException("Index out of bounds");
    }

    @Override
    public String toString() {
        String ret = new String();
        int i = 1;
        for (Plants p : inventory.keySet()) {
            ret += i + ". " + p.getName() + (inventory.get(p) ? " - available" : " - taken") + "\n";
            i++;
        }
        return ret;
    }

    public String toString(Plants tans) {
        String ret = new String();
        String separator = "--------------------------------------";
        String format = "%-15s | %s\n";
        
        ret += separator + "\n";
        ret += String.format(format, "Atribut", "Value");
        ret += separator + "\n";
        ret += String.format(format, "Name", tans.getName());
        ret += separator + "\n";
        ret += String.format(format, "Cost", tans.getCost());
        ret += separator + "\n";
        ret += String.format(format, "Health", tans.getHealth());
        ret += separator + "\n";
        ret += String.format(format, "Attack Damage", tans.getAttackDamage());
        ret += separator + "\n";
        ret += String.format(format, "Attack Speed", tans.getAttackSpeed());
        ret += separator + "\n";
        ret += String.format(format, "Range", tans.getRange());
        ret += separator + "\n";
        ret += String.format(format, "Cooldown", tans.getCooldown());
        ret += separator;
        
        return ret;
    }    

}
