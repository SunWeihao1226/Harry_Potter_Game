package ui.panels;

import model.Archive;
import model.Battle;
import model.characters.*;
import ui.Run;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// REFERENCE: https://github.com/def-not-ys/BCS-Degree-Navigator
public class SelectWizardsPanel extends GamePanel {

    private JLabel label;
    private JLabel note;
    private JButton selectHarry;
    private JButton selectRon;
    private JButton selectHermione;
    private JButton toBattleBtn;
    private JLabel harryIcon = new JLabel("");
    private JLabel ronIcon  = new JLabel("");
    private JLabel hermioneIcon = new JLabel("");

    public Harry harry = new Harry("Harry");
    public Ron ron = new Ron("Ron");
    public Hermione hermione = new Hermione("Hermione");

    public Battle battle1;
    public Battle battle2;
    public Battle battle3;
    public Enemies deathEater;
    public Enemies quir;
    public Enemies basilisk;


    // MODIFIES: this
    // EFFECTS: construct the panel.
    public SelectWizardsPanel(Run run, Archive archive) {
        super(run, archive);
        archive.setCheckPoint(1);
        initializeContents();
        initializeInteraction();
        addToPanel();

    }

    public void initializeBattle(){
        deathEater = new DeathEater("DeathEater");
        quir = new Quirrell("Quirrell");
        basilisk = new Basilisk("Basilisk");
        battle1 = new Battle("Battle1", archive.getSelectedWizard(), deathEater);
        battle2 = new Battle("Battle2", archive.getSelectedWizard(), quir);
        battle3 = new Battle("Battle3", archive.getSelectedWizard(), basilisk);
        archive.addBattle(battle1);
        archive.addBattle(battle2);
        archive.addBattle(battle3);

    }

    // MODIFIES: this
    // EFFECTS: initialize the contents
    @Override
    public void initializeContents() {
        label = new JLabel("Please select Your Wizard.");
        label.setFont(new Font("Serif", Font.ITALIC, 30));
        note = new JLabel("Please note that you cannot change the wizard during the game.");
        note.setFont(new Font("Serif", Font.ITALIC, 18));
        harryIcon.setIcon(new ImageIcon("./data/Harry.png"));
        ronIcon.setIcon(new ImageIcon("./data/Ron.png"));
        hermioneIcon.setIcon(new ImageIcon("./data/Hermione.png"));
        selectHarry = new JButton("Harry Potter");
        selectRon = new JButton("Ron Weasley");
        selectHermione = new JButton("Hermione Granger");
        toBattleBtn = new JButton("Continue");

    }

    // EFFECTS: initialize interactions
    @Override
    public void initializeInteraction() {
        initializeButton();
    }

    // MODIFIES: this
    // EFFECTS: initialize the buttons
    private void initializeButton() {
        selectHarry.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                archive.setSelectedWizard(harry);
                archive.setCheckPoint(1);
//                initializeBattle();
                selectWizardSuccessfulDialog();
            }
        });
        selectRon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                archive.setSelectedWizard(ron);
                archive.setCheckPoint(1);
//                initializeBattle();
                selectWizardSuccessfulDialog();
            }
        });
        selectHermione.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                archive.setSelectedWizard(hermione);
                archive.setCheckPoint(1);
//                initializeBattle();
                selectWizardSuccessfulDialog();
            }
        });
        initializeContinue();

    }

    // MODIFIES: this
    // EFFECTS: initialize the continue button
    private void initializeContinue() {
        toBattleBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (archive.selectedWizard.getName().equals("noName")) {
                    selectDialog();
                } else {
                    run.archive = archive;
                    goToBattle();
                }
            }
        });
    }

    private void selectDialog() {
        JOptionPane.showMessageDialog(this,
                "Please select your wizard!",
                "Error", JOptionPane.PLAIN_MESSAGE);
    }

    // MODIFIES: this
    // EFFECTS: go to the first battle
    public void goToBattle() {
        toBattleBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                run.archive = archive;
                GamePanel selectSpells = new SelectSpellsPanel(run, archive);
                selectSpells.updatePanel();
                run.setContentPane(selectSpells);
                setVisible(false);
                selectSpells.setVisible(true);
                run.validate();
//                setVisible(false);
////                run.game.setVisible(false);
//                run.battle1();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: set the visibility to false
    private void setInvisible() {
        this.setVisible(false);
    }

    // MODIFIES: this
    // EFFECTS: set the dialog window
    private void selectWizardSuccessfulDialog() {
        int option = JOptionPane.showConfirmDialog(this,
                "You have chosen the wizard : " + archive.getSelectedWizard().getName(),
                "Selected successful", JOptionPane.OK_CANCEL_OPTION);

    }

    // MODIFIES: this
    // EFFECTS: add the components to the panel
    @Override
    public void addToPanel() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 10, 5, 1);
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(label, gbc);

        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.PAGE_START;
        add(note, gbc);

        addHarryPanel(gbc);
        addRonPanel(gbc);
        addHermionePanel(gbc);

        gbc.gridx = 1;
        gbc.gridy = 10;
        gbc.anchor = GridBagConstraints.PAGE_END;
        add(toBattleBtn, gbc);
    }

    // MODIFIES: this
    // EFFECTS: add Harry's components
    private void addHarryPanel(GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = new Insets(5, 1, 0, 0);
        add(harryIcon, gbc);
        gbc.insets = new Insets(1, 1, 5, 0);
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.gridy = 5;
        add(selectHarry, gbc);
    }

    // MODIFIES: this
    // EFFECTS: add Ron's components
    private void addRonPanel(GridBagConstraints gbc) {
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.insets = new Insets(5, 0, 0, 1);
        add(ronIcon, gbc);
        gbc.insets = new Insets(1, 0, 5, 1);
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.gridy = 5;
        add(selectRon, gbc);
    }

    // MODIFIES: this
    // EFFECTS: add Hermione's components
    private void addHermionePanel(GridBagConstraints gbc) {
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.insets = new Insets(5, 1, 0, 1);
        add(hermioneIcon, gbc);
        gbc.insets = new Insets(1, 1, 5, 1);
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.gridy = 5;
        add(selectHermione, gbc);
    }

    // MODIFIES: this
    // EFFECTS: update the panel
    @Override
    public void updatePanel() {
        run.archive = archive;
        label.setText("Please select Your Wizard.");
        label.setFont(new Font("Serif", Font.ITALIC, 30));
    }
}
