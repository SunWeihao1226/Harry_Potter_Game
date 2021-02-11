package model.characters;

//Represents a wizard called Ron.
public class Ron extends Wizards {

    //Constructor
    //MODIFIES: this
    //EFFECTS: Construct a Ron wizard
    public Ron(String name) {
        super(name);
        hp = 90;
        atk = 20;
        maxHp = 90;
    }
}
