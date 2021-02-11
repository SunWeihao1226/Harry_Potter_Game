package model;

import model.characters.Wizards;
import model.persistence.Savable;
import org.json.simple.JSONObject;

import java.util.*;

//Represents an Archive can be saved into the saving slot and save player's data
public class Archive implements Savable {

    public String archiveName;
    public Wizards selectedWizard;
    public Integer checkPoint;
    //REQUIRES: gottenSpells no more than 9 spells.
    public HashMap<Integer, Spell> unlockedSpells;
    public List<Battle> battles;

    //Constructor
    //MODIFIES: this
    //EFFECTS: Construct a new archive
    public Archive(String name) {
        archiveName = name;
        selectedWizard = new Wizards("noName");
        this.checkPoint = 0;
        unlockedSpells = new HashMap<>();
        battles = new ArrayList<>();

    }

    public Archive(String name, String wn, String cp, String maxHP, String curAtk) {
        archiveName = name;
        selectedWizard = new Wizards("noName");
        selectedWizard.setName(wn);
        checkPoint = Integer.parseInt(cp);
        selectedWizard.setMaxHp(Integer.parseInt(maxHP));
        selectedWizard.setAtk(Integer.parseInt(curAtk));
        unlockedSpells = new HashMap<>();
        battles = new ArrayList<>();
    }

    @SuppressWarnings("unchecked")
    //EFFECTS: write details in an archive to the json file
    public JSONObject toWrite() {
        JSONObject object = new JSONObject();
        object.put("Archive Name", archiveName);
        object.put("Selected Wizard", selectedWizard.getName());
        object.put("Check Point", checkPoint);
        object.put("Current MaxHP", selectedWizard.getMaxHp());
        object.put("Current ATK", selectedWizard.getAtk());
        object.put("Unlocked Spells", spellList(unlockedSpells.keySet()));
        return object;
    }

    public List<String> spellList(Set<Integer> spells) {
        List<String> spellList = new ArrayList<>();
        for (Integer i : spells) {
            spellList.add(i + ". " + unlockedSpells.get(i).spellsName + "." + unlockedSpells.get(i).atk);
        }
        return spellList;
    }


    @Override
    public void save(SavingSlot slot) {
        if (slot.getSlot().containsKey(archiveName)) {
            slot.getSlot().replace(archiveName, this);
        } else {
            slot.addArchives(this);
        }
    }

    @Override
    public String toString() {
        return archiveName;
    }

    //MODIFIES: this
    //EFFECTS: select a wizard
    public void selectWizard(Wizards w) {
        selectedWizard = w;
    }

    //MODIFIES: this
    //EFFECTS: add spells that are unlocked and can be used
    public void addSpells(int i, Spell spell) {
        unlockedSpells.put(i, spell);
    }

    //EFFECTS: get the ith spells in the list
    public Spell getSpells(int i) {
        return unlockedSpells.get(i);
    }


    //MODIFIES: this
    //EFFECTS: Add a battle to the battles
    public void addBattle(Battle battle) {
        battles.add(battle);
    }


    //getter
    public String getArchiveName() {
        return archiveName;
    }

    //getter
    public Wizards getSelectedWizard() {
        return selectedWizard;
    }

    //getter
    public int getCheckPoint() {
        return checkPoint;
    }

    public HashMap<Integer, Spell> getUnlockedSpells() {
        return unlockedSpells;
    }

    public List<Battle> getBattles() {
        return battles;
    }

    //setter
    public void setArchiveName(String archiveName) {
        this.archiveName = archiveName;
    }

    //setter
    public void setSelectedWizard(Wizards selectedWizard) {
        this.selectedWizard = selectedWizard;
    }

    //setter
    public void setCheckPoint(int checkPoint) {
        this.checkPoint = checkPoint;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Archive)) {
            return false;
        }
        Archive archive = (Archive) o;
        return archiveName.equals(archive.archiveName)
                && Objects.equals(selectedWizard, archive.selectedWizard)
                && Objects.equals(checkPoint, archive.checkPoint)
                && Objects.equals(unlockedSpells, archive.unlockedSpells)
                && Objects.equals(battles, archive.battles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(archiveName, selectedWizard, checkPoint, unlockedSpells, battles);
    }
}

