package model.characters;

//Represents an enemy called Basilisk.
public class Basilisk extends Enemies {

    //Constructor
    // MODIFIES: this
    // EFFECTS: Construct a Basilisk.
    public Basilisk(String name) {
        super(name);
        hp = 130;
        atk = 25;
    }
}
