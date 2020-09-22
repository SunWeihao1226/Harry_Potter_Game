package model.characters;

//Represents a wizard called Hermione.
public class Hermione extends Wizards {

    //Constructor
    //MODIFIES: this
    //EFFECTS: Construct a Hermione wizard
    public Hermione(String name) {
        super(name);
        hp = 80;
        atk = 15;
    }
}
