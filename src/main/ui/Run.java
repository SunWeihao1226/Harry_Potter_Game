package ui;

import model.Archive;
import model.SavingSlot;
import model.Spell;
import model.persistence.Reader;
import model.persistence.Writer;
import org.json.simple.parser.ParseException;
import ui.panels.GamePanel;
import ui.panels.StartPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//Helper class of Main. Represents the user panel.
// // Reference: https://github.students.cs.ubc.ca/CPSC210/TellerApp/commit/f7fa8a0ecd7fb591e2ea0930e1f882e0f93baab7
////            https://github.com/def-not-ys/BCS-Degree-Navigator
////            https://howtodoinjava.com/java/library/json-simple-read-write-json-examples/
public class Run extends JFrame {
    SavingSlot slot;
    public Archive archive;

    public List<Spell> spellsToAdd;

    String fileName = "./data/data.json";
    Spell stupefy = new Spell("Stupefy", 10);
    Spell expelliarmus = new Spell("Expelliarmus", 15);
    Spell petrificus = new Spell("Petrificus Totalus", 18);
    Spell obliviate = new Spell("Obliviate", 20);
    Spell reducto = new Spell("Reducto", 25);

    static final Dimension FRAME_DIMENSION = new Dimension(1000, 700);

    public GamePanel game;

    //MODIFIES: this
    //EFFECTS: Construct a new object of Run class
    public Run() {
        super("Harry Potter the Magic World. Version 3.0.0");
        try {
            initialize();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        game = new StartPanel(this, archive);
        archive.addSpells(1, stupefy);
        archive.addSpells(2, expelliarmus);
        archive.addSpells(3, petrificus);
        archive.addSpells(4, obliviate);
        spellsToAdd = new ArrayList<>();
        spellsToAdd.add(reducto);
        goPanel();
    }

    public void goPanel() {
        setLayout(new GridBagLayout());
        setPreferredSize(FRAME_DIMENSION);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(game);
        addWindowListener(new SaveData());
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private class SaveData extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            int option = JOptionPane.showOptionDialog(
                    Run.this,
                    "Would you like to save the data?",
                    "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE,
                    null, null, null);
            if (option == JOptionPane.YES_OPTION) {
                save();
                System.out.println("save data!");
            } else {
                System.out.println("not saved!");
            }
            System.exit(0);
        }
    }


    // MODIFIES: this
    // EFFECTS: load the saving slot from the data file.
    //          Create new slot if not exists.
    public void loadArchive() throws org.json.simple.parser.ParseException {
        try {
            Reader reader = new Reader(fileName);
            slot = new SavingSlot();
            slot = reader.read();
        } catch (FileNotFoundException e) {
            slot = new SavingSlot();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // EFFECTS: save the data to JSON file
    public void save() {
        try {
            Writer writer = new Writer(fileName);
            writer.write(slot);
            writer.closeWriting();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // MODIFIES: this
    // EFFECTS: initialize the archive
    public void initialize() throws ParseException {
        archive = new Archive("noname", "noName", "0", "0", "0");
        loadArchive();
    }

    public SavingSlot getSlot() {
        return slot;
    }
}
