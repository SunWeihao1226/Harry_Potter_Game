package model.characters;

//Represents an enemy called DeathEater
public class DeathEater extends Enemies {

    //Constructor
    //MODIFIES: this
    // EFFECTS: Construct a Death Eater.
    public DeathEater(String name) {
        super(name);
        hp = 100;
        atk = 18;
        maxHP = 100;
    }
}
