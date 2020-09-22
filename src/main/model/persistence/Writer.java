package model.persistence;

import model.Archive;
import model.SavingSlot;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

// Represent a writer class allowing to write data to a JSON file
// Reference: https://github.students.cs.ubc.ca/CPSC210/TellerApp/commit/f7fa8a0ecd7fb591e2ea0930e1f882e0f93baab7
//            https://github.com/def-not-ys/BCS-Degree-Navigator
//            https://howtodoinjava.com/java/library/json-simple-read-write-json-examples/
public class Writer {

    FileWriter fileWriter;

    //REQUIRES: the name of the file is  the name of one archive
    public Writer(String string) throws IOException {
        fileWriter = new FileWriter(string);
    }


    //MODIFIES: this
    //EFFECTS: write the saving slot and archives in it to the json file
    public void write(SavingSlot savingSlot) throws IOException {
        HashMap<String, Archive> slots = savingSlot.getSlot();
        JSONObject allArchives = new JSONObject();
        for (String string : slots.keySet()) {
            JSONObject archiveData = savingSlot.getArchive(string).toWrite();
            allArchives.put(string, archiveData);
        }
        fileWriter.write(allArchives.toJSONString());
        fileWriter.flush();
    }

    //MODIFIES: this
    //EFFECTS: close the writing
    public void closeWriting() throws IOException {
        fileWriter.close();
    }
}
