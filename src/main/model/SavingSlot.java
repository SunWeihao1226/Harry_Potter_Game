package model;

import java.util.HashMap;
import java.util.Objects;
import java.util.Set;

// Represents a collection of saved archives.
public class SavingSlot {

    HashMap<String, Archive> slot;

    //Constructor
    //MODIFIES: this
    //EFFECTS: Construct a new saving slot
    public SavingSlot() {
        slot = new HashMap<>();
    }

    //MODIFIES: this
    //EFFECTS: add a new archive to the saving slot
    public void addArchives(Archive archive) {
        slot.put(archive.getArchiveName(), archive);
    }

    //REQUIRES: slots is not empty
    //MODIFIES: this
    //EFFECTS: remove an existing battle
    public void removeArchive(Archive archive) {
        slot.remove(archive.getArchiveName());
    }

    //MODIFIES: this
    //EFFECTS: return the size of the saving slot
    public int getSize() {
        return slot.keySet().size();
    }

    //EFFECTS: get the archive with the given name
    public Archive getArchive(String str) {
        return slot.get(str);
    }

    //EFFECTS: return true if the slots contains the archive with the given name.
    public boolean containsArchive(String name) {
        return slot.containsKey(name);
    }

    // EFFECTS: return the slots
    public HashMap<String, Archive> getSlot() {
        return slot;
    }

    //EFFECTS: return the set of the archive names
    public Set<String> getNamesSet() {
        return slot.keySet();
    }

}
