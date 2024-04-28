package Tile;

public class Water extends Tile{
    private Boolean isLilypadHere = false;

    public Water(){
        super(false, false, true, false);
    }

    public void setLilypadHere() {
        isLilypadHere = true;
    }

    public Boolean getLilypadHere() {
        return isLilypadHere;
    }
}
