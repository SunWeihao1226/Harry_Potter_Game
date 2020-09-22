package ui;

import model.Archive;
import model.Battle;
import model.SavingSlot;
import model.Spell;
import model.characters.*;
import model.exceptions.CannotFindArchiveException;
import model.exceptions.CannotFindSpellException;
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
import java.lang.Character;
import java.util.Scanner;

//Helper class of Main. Represents the user panel.
// // Reference: https://github.students.cs.ubc.ca/CPSC210/TellerApp/commit/f7fa8a0ecd7fb591e2ea0930e1f882e0f93baab7
////            https://github.com/def-not-ys/BCS-Degree-Navigator
////            https://howtodoinjava.com/java/library/json-simple-read-write-json-examples/
public class Run extends JFrame {

    SavingSlot slot;
    public Archive archive;
    Wizards harry;
    Wizards hermione;
    Wizards ron;
    public Battle battle1;

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

    //EFFECTS: print the welcome panel
    public void startGame() {
        System.out.println("=======================Developed by Weihao Sun==============================");
        System.out.println("----------------------------Version 2.0-------------------------------------");
        System.out.println("Welcome to the Harry Potter World!");
        System.out.println("You are a brave wizard of Hogwarts, and you will fight against evil enemies.");
        System.out.println("The adventure starts from NOW!");
    }

    //MODIFIES: this
    //EFFECTS: create, remove or read an archive
    public void handleArchives() {
        Scanner scanner = new Scanner(System.in);
        handleArchiveHelper();
        String str = scanner.nextLine();
        if (str.equals("1")) {
            createArchive();
            continueOrBackNew();
        } else if (str.equals("2")) {
            removeArchive();
            getBackAfterRemove();
        } else if (str.equals("3")) {
            save();
            System.out.println("Saved successfully!");
            handleArchives();
        } else if (str.equals("4")) {
            readArchive();
        } else if (str.equals("5")) {
            save();
            System.out.println("Automatically saved your archives. Good Bye!");
            System.exit(0);
        } else {
            System.out.println("Oops! Please enter the right number!");
            handleArchives();
        }
    }

    // EFFECTS: print the instruction of handling archives
    public void handleArchiveHelper() {
        System.out.println("Press 1 to create a new archive. \nPress 2 to remove an existing archive.");
        System.out.println("Press 3 to save the game.");
        System.out.println("Press 4 to read a archive. \nPress 5 to exit the game.");
    }

    //MODIFIES: this and flag
    //EFFECTS: Read an archive and continue to play the game from where the player quited the last time.
    public void readArchive() {
        System.out.println("Please enter the name of your archive that you want to continue: \nYour archives are:");
        for (String string : slot.getNamesSet()) {
            System.out.println(string);
        }
        Scanner scan = new Scanner(System.in);
        String string = scan.nextLine();
        boolean flag = false;
        for (String str : slot.getNamesSet()) {
            if (string.equals(str)) {
                archive = slot.getArchive(str);
                battleIndex(archive);
                flag = true;
                break;
            }
        }
        if (!flag) {
            System.out.println("Cannot find this archive.");
            getBackAfterRemove();
        }
    }


    //MODIFIES: this
    //EFFECTS: go to the associated battle according to checkPoint
    public void battleIndex(Archive archive) {
        if (archive.getCheckPoint() == 0) {
            continueOrBackNew();
        } else if (archive.getCheckPoint() == 1) {
            continueOrBackAfterChooseWizard();
        } else if (archive.getCheckPoint() == 2) {
            battle1ToBattle2();
        } else if (archive.getCheckPoint() == 3) {
            battle2ToBattle3();
        }
    }

    //MODIFIES: this
    //EFFECTS: create an archive and give it a name
    public void createArchive() {
        Scanner arcName = new Scanner(System.in);
        System.out.println("Please enter your new archive's name:");
        String name = arcName.nextLine();
        archive = new Archive(name);
        archive.save(slot);

        System.out.println("Your new archive " + name + " has been created successfully.");
    }

    //MODIFIES: this
    //EFFECTS: remove an archive by name
    public void removeArchive() {
        System.out.println("Your archives are: ");
        for (String string : slot.getNamesSet()) {
            System.out.println(string);
        }
        System.out.println("Please enter the name of archive that you want to remove: ");
        try {
            removeArchiveHelper();
        } catch (CannotFindArchiveException e) {
            System.out.println("Cannot find the archive, try again");
            removeArchive();
        }
    }

    //MODIFIES: this
    //EFFECTS: helper method of removeArchive method.
    public void removeArchiveHelper() throws CannotFindArchiveException {
        Scanner scanner2 = new Scanner(System.in);
        String toRemove = scanner2.nextLine();
        if (!slot.containsArchive(toRemove)) {
            throw new CannotFindArchiveException();
        } else {
            for (String string : slot.getNamesSet()) {
                if (toRemove.equals(string)) {
                    slot.removeArchive(slot.getArchive(toRemove));
                    System.out.println("The archive " + toRemove + " has been removed successfully.");
                    break;
                }
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: continue or back
    public void continueOrBackNew() {
        System.out.println("Enter \"continue\" to continue \nEnter \"back\" to get back.");
        Scanner scanner1 = new Scanner(System.in);
        String contOrBack = scanner1.nextLine();
        if (contOrBack.equals("continue")) {
            chooseWizard();
        } else if (contOrBack.equals("back")) {
            handleArchives();
        } else {
            System.out.println("Oops! Please enter the right order.");
            continueOrBackNew();
        }
    }

    //MODIFIES: this
    //EFFECTS: get back after removing an archive
    public void getBackAfterRemove() {
        System.out.println("Please enter \"back\" to get back");
        Scanner scan = new Scanner(System.in);
        String back = scan.nextLine();
        if (!back.equals("back")) {
            System.out.println("Oops! Please enter the correct order.");
            getBackAfterRemove();
        }
        handleArchives();
    }

    //MODIFIES: this
    //EFFECTS: choose an wizard by typing the wizard's name
    public void chooseWizard() {
        System.out.println("Please choose your wizard. \nNote that wizard cannot be changed during the game.");
        System.out.println("Here are the wizards that you can choose: \nHarry Potter \nRon Weasley \nHermione Granger");
        Scanner scan = new Scanner(System.in);
        String wizardName = scan.nextLine();
        chooseWizardHelper(wizardName);
        System.out.println("Your wizard is " + wizardName);
        archive.setCheckPoint(1);
        continueOrBackAfterChooseWizard();
    }

    //MODIFIES: this
    //EFFECTS: helper method of chooseWizard method
    public void chooseWizardHelper(String wizardName) {
        if (wizardName.equals("Harry Potter")) {
            harry = new Harry("Harry");
            archive.setSelectedWizard(harry);
        } else if (wizardName.equals("Ron Weasley")) {
            ron = new Ron("Ron");
            archive.setSelectedWizard(ron);
        } else if (wizardName.equals("Hermione Granger")) {
            hermione = new Hermione("hermione");
            archive.setSelectedWizard(hermione);
        } else {
            System.out.println("Oops! Please enter the wizard's full name!");
            chooseWizard();
        }
    }

    //MODIFIES: this
    //EFFECTS: continue or get back to the first panel after choosing the wizard
    public void continueOrBackAfterChooseWizard() {
        System.out.println("Enter \"continue\" to continue \nEnter \"back\" to get back.");
        Scanner scanner1 = new Scanner(System.in);
        String contOrBack = scanner1.nextLine();
        if (contOrBack.equals("continue")) {
            battle1();
        } else if (contOrBack.equals("back")) {
            handleArchives();
        } else {
            System.out.println("Oops! Please enter the right order.");
            continueOrBackAfterChooseWizard();
        }
    }

    //MODIFIES: this
    //EFFECTS: represents the first battle
    public void battle1() {
        Enemies e = new DeathEater("deathEater");
        Battle battle1 = new Battle("Battle1", archive.selectedWizard, e);
        battle1PrintHelper();
        beforeEachBattle(battle1);
        System.out.println("Now lets start. Kill the Death Eater.");
        archive.selectedWizard.setHp(archive.selectedWizard.maxHp);
        inBattle(battle1);
        battleResult(battle1, 5, 10);
        archive.setCheckPoint(2);
        battle1ToBattle2();
    }

    //MODIFIES: this
    //EFFECTS: continue or back panel between the first battle and the second battle
    public void battle1ToBattle2() {
        System.out.println("Please enter \"continue\" to continue. \nPress \"back\" to get back to the archive panel.");
        Scanner scan = new Scanner(System.in);
        String str = scan.nextLine();
        if (str.equals("continue")) {
            battle2();
        } else if (str.equals("back")) {
            save();
            new Run();
        } else {
            System.out.println("Oops! Please enter the right order.");
            battle1ToBattle2();
        }
    }

    //MODIFIES: this
    //EFFECTS: Represents the second battle.
    public void battle2() {
        Enemies quir = new Quirrell("Quirrell");
        Battle battle2 = new Battle("Battle2", archive.selectedWizard, quir);
        battle2PrintHelper();
        archive.addSpells(5, reducto);
        beforeEachBattle(battle2);
        System.out.println("Now let's start. Defeat Quirrell!");
        archive.selectedWizard.setHp(archive.selectedWizard.getMaxHp());
        inBattle(battle2);
        battleResult(battle2, 10, 20);
        archive.setCheckPoint(3);
        battle2ToBattle3();
    }

    //MODIFIES: this
    //EFFECTS: Continue or back panel between the second battle and the third battle
    public void battle2ToBattle3() {
        System.out.println("Please enter \"continue\" to continue. \nPress \"back\" to get back to the archive panel.");
        Scanner scan = new Scanner(System.in);
        String str = scan.nextLine();
        if (str.equals("continue")) {
            battle3();
        } else if (str.equals("back")) {
            save();
            new Run();
        } else {
            System.out.println("Oops! Please enter the right order.");
            battle2ToBattle3();
        }
    }

    //MODIFIES: this
    //EFFECTS: represents the third battle
    public void battle3() {
        Enemies basilisk = new Basilisk("Basilisk");
        Battle battle3 = new Battle("Battle3", archive.selectedWizard, basilisk);
        battle3PrintHelper();
        beforeEachBattle(battle3);
        System.out.println("Now let's start. Kill the Basilisk!");
        archive.selectedWizard.setHp(archive.selectedWizard.getMaxHp());
        inBattle(battle3);
        battleResult(battle3, 15, 25);
        archive.setCheckPoint(4);
        save();
        endPrinter();
        new Run();
    }

    //EFFECTS: print the plot information at the end of the game.
    public void endPrinter() {
        System.out.println("Dumbledore: You did it, " + archive.selectedWizard.name + "!");
        System.out.println("Dumbledore: I know you must be excited about life in Hogwarts"
                            + ", but the amazing adventure just starts!");
        System.out.println("So, " + archive.selectedWizard.name + ", be prepared! "
                            + "You will continue fighting for the wizard world very soon!");
        System.out.println("To be continued...");
    }

    //MODIFIES: this
    //EFFECTS: preparation of each battle
    public void beforeEachBattle(Battle battle) {
        archive.addBattle(battle);
        battleAddSpellsHelper(battle);
        showSpellsHelper();
        try {
            chooseSpellsHelper(battle);
        } catch (CannotFindSpellException e) {
            System.out.println("Oops. Please enter the correct number!");
            beforeEachBattle(battle);
        }
    }

    //MODIFIES: this
    //EFFECTS: Show all spells a player unlocked.
    public void showSpellsHelper() {
        System.out.println("Now enter the number to choose FOUR Spells you want to use in the battle:");
        for (Integer key : archive.gottenSpells.keySet()) {
            System.out.println(key + ". " + archive.gottenSpells.get(key).spellsName);
        }
    }

    //MODIFIES: this
    //EFFECTS: helper method of choosing spells to use in each battle
    public void chooseSpellsHelper(Battle battle) throws CannotFindSpellException {
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < 4; i++) {
            String str = scanner.nextLine();
            if (!isNumber(str) || !archive.gottenSpells.keySet().contains(Integer.parseInt(str))) {
                throw new CannotFindSpellException();
            }
            for (Integer key : archive.gottenSpells.keySet()) {
                if (str.equals(key.toString())) {
                    battle.addSpellsToUse(i + 1, archive.gottenSpells.get(key));
                    System.out.println("You have chosen " + archive.gottenSpells.get(key).spellsName);
                }
            }
        }
    }

    public boolean isNumber(String string) {
        for (int i = 0; i < string.length(); i++) {
            if (!Character.isDigit(string.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    //MODIFIES: this
    //EFFECTS: helper method of adding spells to gottenSpells
    public void battleAddSpellsHelper(Battle battle) {
        for (Integer key : archive.gottenSpells.keySet()) {
            battle.addSpellsToChoose(key, archive.gottenSpells.get(key));
        }
    }

    //EFFECTS: Print plots information of the first battle
    public void battle1PrintHelper() {
        System.out.println("Dumbledore: Hi " + archive.selectedWizard.name + ", I am Albus Dumbledore, "
                + "the headmaster of Hogwarts");
        System.out.println("Dumbledore: As a new fighter to protect our wizard world, "
                + "you must learn to use spells to fight.");
        System.out.println("Try to use these four spells to attack the enemy: ");
        System.out.println("Stupefy: Attack your enemy by making him shocked or unable to think or respond.");
        System.out.println("Expelliarmus: Attack your enemy by moving out his weapon.");
        System.out.println("Petrificus Totalus: Attack your enemy by freezing his body.");
        System.out.println("Obliviate: Attack your enemy by removing his memory.");
    }

    //EFFECTS: print plots information of the second battle
    public void battle2PrintHelper() {
        String name = archive.selectedWizard.name;
        System.out.println("Dumbledore: Good job, " + name);
        System.out.println("Dumbledore: Now it's a good time to learn some new spells!");
        System.out.println("You unlocked new spells: ");
        System.out.println("Reducto: Attack your enemy by reducing him into dust. This spell causes more damage.");
        System.out.println("Choose your spells wisely in the coming battle!");
        System.out.println("Dumbledore: Ahh " + name + ", looks like you have learned a mew spell!");
        System.out.println("Dumbledore: Oh no! That's Quirrell. He is stealing the Philosopher's Stone!");
        System.out.println("Dumbledore: He must be serving Lord Voldemort. " + name + ", You must stop Quirrell!");
    }

    //EFFECTS: print plot information of the third battle.
    public void battle3PrintHelper() {
        System.out.println("Dumbledore: You are a really smart wizard, " + archive.selectedWizard.name);
        System.out.println("Dumbledore: I'm sure you have heard of Lord Voldemort, I mean, the \"You-Know-Who\"");
        System.out.println("Dumbledore: Actually, his real name is Tom Riddle. He was a good boy, but lost himself"
                             + " in the dark arts. He once had a diary, which must be found and destroyed.");
        System.out.println("Dumbledore: I believe you can finish this task, although it is dangerous. Be careful!");
        System.out.println("You find the diary in the Chamber of Secrets.");
        System.out.println("Basilisk: Prepare to die, young wizard!");
    }

    //MODIFIES: this
    //EFFECTS: represents the battle.The battle ends when one character dies.
    public void inBattle(Battle battle) {
        Scanner scanner = new Scanner(System.in);
        while (!battle.isWin() || battle.player.isDead) {
            playerRoundInfo(battle);
            String str = scanner.nextLine();
            if (!(str.equals("1") || str.equals("2") || str.equals("3") || str.equals("4"))) {
                System.out.println("Oops! Please enter the correct number!");
                inBattle(battle);
            } else {
                int i = Integer.parseInt(str);
                String s = "You cause ";
                System.out.println(s + battle.attackWizardToEnemy(battle.player, battle.enemy, i) + " damage");
                if (battle.enemy.isDead) {
                    break;
                }
                enemyRoundInfo(battle);
                if (battle.player.isDead) {
                    break;
                }
            }
        }
    }

    //EFFECTS: print the information of both the enemy and the player at the beginning of the player's round
    public void playerRoundInfo(Battle battle) {
        System.out.println("Your current hp is: " + battle.getPlayer().getHp());
        System.out.println("The enemy's current hp is: " + battle.getEnemy().getHp());
        System.out.println("Please enter the number to choose a spell to use: ");
        for (Integer key : battle.spellToUse.keySet()) {
            System.out.println(key + ". " + battle.getSpellToUse().get(key).spellsName);
        }
    }

    //EFFECTS: represents the enemy's round.
    public void enemyRoundInfo(Battle battle) {
        System.out.println("Your current hp is: " + battle.getPlayer().getHp());
        System.out.println("The enemy's current hp is: " + battle.getEnemy().getHp());
        int damage = battle.attackEnemyToWizard(battle.getEnemy(), battle.getPlayer());
        System.out.println("The enemy attacks you! You lost " + damage + " HP");
    }

    //MODIFIES: this
    //EFFECTS: Show the result of one battle
    public void battleResult(Battle battle, int atkIncrease, int hpIncrease) {
        if (battle.isWin()) {
            System.out.println("Congratulations! You defeat the enemy!");
            System.out.println("Your attack ability has increased! HP+10! Your ATK has also increased!");
            archive.selectedWizard.setAtk(archive.selectedWizard.getAtk() + atkIncrease);
            archive.selectedWizard.setMaxHp(archive.selectedWizard.getMaxHp() + hpIncrease);
            System.out.println("Your current HP is: " + archive.selectedWizard.getMaxHp() + ".");
        } else {
            System.out.println("You lose. Please try again or quit.");
            Scanner scan = new Scanner(System.in);
            String tryorquit = scan.nextLine();
            if (tryorquit.equals("try again")) {
                lastBattle();
            } else if (tryorquit.equals("quit")) {
                handleArchives();
            }
        }
    }

    //EFFECTS: get back to the last battle if the player loses.
    public void lastBattle() {
        if (archive.checkPoint == 2) {
            battle1();
        } else if (archive.checkPoint == 3) {
            battle2();
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
