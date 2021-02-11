package model;

import model.characters.Enemies;
import model.characters.Wizards;

import java.util.HashMap;
import java.util.Objects;

//Represents each battle
public class Battle {
    public String battleName;
    public HashMap<Integer, Spell> spellsToChoose;
    public HashMap<Integer, Spell> spellToUse;
    public Wizards player;
    public Enemies enemy;

    //Constructor
    //MODIFIES: this
    //EFFECTS: Construct a new battle
    public Battle(String name, Wizards player, Enemies enemy) {
        battleName = name;
        this.player = player;
        this.enemy = enemy;
        spellsToChoose = new HashMap<Integer, Spell>();
        spellToUse = new HashMap<>();

    }



    // MODIFIES: this
    // EFFECTS: return true if an enemy is dead
    public boolean isWin() {
        return enemy.isDead;
    }

    //MODIFIES: this
    // EFFECTS: add spells to the Hashmap spellsToChoose
    public void addSpellsToChoose(int i, Spell spell) {
        spellsToChoose.put(i, spell);
    }

    //MODIFIES: this
    //EFFECTS: add spells to the list spellToUse
    public void addSpellsToUse(int i, Spell spell) {
        spellToUse.put(i, spell);
    }

    //MODIFIES: this
    //EFFECTS: Choose 4 spells to use in each battle
    public void chooseSpells(int w, int x, int y, int z) {
        spellToUse.put(1, (spellsToChoose.get(w)));
        spellToUse.put(2, (spellsToChoose.get(x)));
        spellToUse.put(3, (spellsToChoose.get(y)));
        spellToUse.put(4, (spellsToChoose.get(z)));
    }

    //EFFECTS: make one wizard attack to the enemy
    //         hp of the defender - atk of the offender
    public int attackWizardToEnemy(Wizards wiz, Enemies ene, int spellNum) {
        if (!(spellAtk(spellNum) == 0)) {
            int damage = (int) (wiz.getAtk() + Math.random() * 10 + spellAtk(spellNum));
            ene.setHp(ene.getHp() - damage);
            ene.isDead();
            return damage;
        }
        return 0;
    }

    //EFFECTS: make enemy attack wizard
    public int attackEnemyToWizard(Enemies ene, Wizards wiz) {
        int damage = (int) (ene.getAtk() + Math.random() * 10);
        wiz.setHp(wiz.getHp() - damage);
        wiz.isDead();
        return damage;
    }


    // EFFECT: returns the ATK of the spell associated with the spell's number
    public int spellAtk(int index) { //!!!
//        if (i == 0) {
//            return spellToUse.get(1).atk;
//        } else if (i == 2) {
//            return spellToUse.get(2).atk;
//        } else if (i == 3) {
//            return spellToUse.get(3).atk;
//        } else if (i == 4) {
//            return spellToUse.get(4).atk;
//        } else {
//            return 0;
//        }
        if (index >= 0 && index < 5) {
            return spellToUse.get(index).atk;
        } else {
            return 0;
        }
    }


    //getter
    public String getBattleName() {
        return battleName;
    }

    //getter
    public HashMap<Integer, Spell> getSpellsToChoose() {
        return spellsToChoose;
    }

    //getter
    public HashMap<Integer, Spell> getSpellToUse() {
        return spellToUse;
    }

    //getter
    public Wizards getPlayer() {
        return player;
    }

    //getter
    public Enemies getEnemy() {
        return enemy;
    }

    //setter
    public void setBattleName(String battleName) {
        this.battleName = battleName;
    }

    //setter
    public void setSpellsToChoose(HashMap<Integer, Spell> spellsToChoose) {
        this.spellsToChoose = spellsToChoose;
    }

    //setter
    public void setSpellToUse(HashMap<Integer, Spell> spellToUse) {
        this.spellToUse = spellToUse;
    }

    //setter
    public void setPlayer(Wizards player) {
        this.player = player;
    }

    //setter
    public void setEnemy(Enemies enemy) {
        this.enemy = enemy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Battle)) {
            return false;
        }
        Battle battle = (Battle) o;
        return battleName.equals(battle.battleName)
                && spellsToChoose.equals(battle.spellsToChoose)
                && spellToUse.equals(battle.spellToUse)
                && player.equals(battle.player)
                && enemy.equals(battle.enemy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(battleName, spellsToChoose, spellToUse, player, enemy);
    }
}
