package model.persistence;

import model.Archive;
import model.SavingSlot;
import model.Spell;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

// Represents a reader can read archives from the data file.
// Reference: https://github.students.cs.ubc.ca/CPSC210/TellerApp/commit/f7fa8a0ecd7fb591e2ea0930e1f882e0f93baab7
//            https://github.com/def-not-ys/BCS-Degree-Navigator
//            https://howtodoinjava.com/java/library/json-simple-read-write-json-examples/
public class Reader {

    FileReader fileReader;
    Archive archive;
    SavingSlot slot;


    // EFFECTS: Construct a reader with given name
    public Reader(String str) throws FileNotFoundException {
        fileReader = new FileReader(str);
    }



    // MODIFIES: this
    // EFFECTS: return the archive modified by parsing the json file
    public Archive parseArchive(JSONObject data) {
        String archiveName = (String) data.get("Archive Name");
        String selectedWizard = (String) data.get("Selected Wizard");
        String checkPoint = String.valueOf(data.get("Check Point"));
        String currentMaxHp = String.valueOf(data.get("Current MaxHP"));
        String currentATK = String.valueOf(data.get("Current ATK"));
        JSONArray spells = (JSONArray) data.get("Unlocked Spells");
        archive = new Archive(archiveName, selectedWizard, checkPoint, currentMaxHp, currentATK);
        parseSpellList(spells, archive);
        return archive;
    }

    // EFFECTS: parse the unlocked spells list from the JSON file and adds to the archive
    public void parseSpellList(JSONArray array, Archive archive) {
        for (Object object : array) {
            String str = (String) object;
            int i = Integer.parseInt(String.valueOf(str.charAt(0)));
            String spellName = str.substring(3, str.length() - 3);
            String spellAtk = str.substring(str.length() - 2, str.length());
            archive.addSpells(i, new Spell(spellName, Integer.parseInt(spellAtk)));
        }
    }

    // MODIFIES: this
    // EFFECTS: returns the slot read from the JSON file
    public SavingSlot read() throws IOException, ParseException {
        slot = new SavingSlot();
        JSONParser parser = new JSONParser();
        JSONObject data = (JSONObject) parser.parse(fileReader);
        HashMap<String, JSONObject> map = new HashMap<>();
        map.putAll((HashMap<String, JSONObject>) data);
        for (String str : map.keySet()) {
            Archive archive = parseArchive(map.get(str));
            slot.addArchives(archive);
        }
        return slot;
    }





}
