package model.characters;

//Represents enemies
public class Enemies implements Character {

    public String name;
    public int hp;
    public int maxHP;
    public int atk;
    public boolean isDead;

    // Constructor
    // MODIFIES: this
    // EFFECTS: Construct an Enemies class
    public Enemies(String name) {
        this.name = name;
        hp = 80;
        maxHP = 80;
        atk = 8;
        isDead = false;
    }

    // MODIFIES: hp
    // EFFECTS: decrease the hp oof an enemy by i
    @Override
    public void decreaseHP(int i)  {
        setHp(hp - i);
    }


    // MODIFIES: isDead
    // EFFECTS: set isDead to true if hp less than or equals zero
    public void isDead() {
        if (hp <= 0) {
            setDead(true);
        }
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

    public int getMaxHP() {
        return maxHP;
    }

    //setters
    public void setName(String name) {
        this.name = name;
    }

    //setters
    public void setHp(int hp) {
        this.hp = hp;
    }

    //setters
    public void setAtk(int atk) {
        this.atk = atk;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }
}
