package model.characters;

//Represent an enemy called Quirrell.
public class Quirrell extends Enemies {

    //Constructor
    //MODIFIES: this
    //EFFECTS: Construct a Quirrell enemy
    public Quirrell(String name) {
        super(name);
        hp = 110;
        atk = 20;
    }
}
