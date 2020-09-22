package model.characters;

import java.util.Objects;

//Represents wizards
public class Wizards implements Character {

    public String name;
    public int hp;
    public int atk;
    public boolean isDead;
    public int maxHp;

    //Constructor
    //MODIFIES: this
    //EFFECTS: Construct a Wizard
    public Wizards(String name) {
        this.name = name;
        hp = 100;
        atk = 10;
        isDead = false;
        maxHp = 100;
    }

    // MODIFIES: this
    // EFFECTS: increase the Max HP of one wizard by i
    public void increaseMaxHP(int i) {
        setMaxHp(maxHp + i);
    }

    //MODIFIES: this
    //EFFECTS: decrease the hp of a wizard by i
    @Override
    public void decreaseHP(int i)  {
        setHp(hp - i);
    }

    //MODIFIES: this
    //EFFECTS: increase the ATK of a wizard by i
    public void increaseATK(int i) {
        setAtk(atk + i);
    }

    // MODIFIES: this
    // EFFECTS: set isDead to true if hp less than or equals zero
    public void isDead() {
        if (hp <= 0) {
            setDead(true);
        }
    }

    public void getByName() {

    }


    //getters
    public String getName() {
        return name;
    }

    //getters
    public int getHp() {
        return hp;
    }

    //getters
    public int getAtk() {
        return atk;
    }

    public int getMaxHp() {
        return maxHp;
    }

    //setters
    public void setName(String name) {
        this.name = name;
    }

    //setters
    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    //setters
    public void setAtk(int atk) {
        this.atk = atk;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Wizards)) {
            return false;
        }
        Wizards wizards = (Wizards) o;
        return hp == wizards.hp
                && atk == wizards.atk
                && isDead == wizards.isDead
                && maxHp == wizards.maxHp
                && name.equals(wizards.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, hp, atk, isDead, maxHp);
    }
}
