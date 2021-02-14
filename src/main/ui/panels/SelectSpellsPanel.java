package ui.panels;

import model.Archive;
import model.Battle;
import model.Spell;
import model.characters.Basilisk;
import model.characters.DeathEater;
import model.characters.Enemies;
import model.characters.Quirrell;
import ui.Run;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

// Selecting spells panel
public class SelectSpellsPanel extends GamePanel {
    private JLabel intro1;
    private JLabel intro2;
    private JLabel showSpells;
    private List<JButton> spellButtons;
    private JButton toBattle;
    public Battle currBattle;

    public Battle battle1;
    public Battle battle2;
    public Battle battle3;
    public Enemies deathEater;
    public Enemies quir;
    public Enemies basilisk;

    // Constructor
    public SelectSpellsPanel(Run run, Archive archive) {
        super(run, archive);
        initializeBattle();
        spellButtons = new ArrayList<>();
        currBattle = archive.getBattles().get(archive.getCheckPoint() - 1);
        initializeContents();
        addToPanel();
        initializeInteraction();

    }

    // Initialize contents
    @Override
    public void initializeContents() {
        intro1 = new JLabel("Hi! " + archive.getSelectedWizard().getName());
        intro2 = new JLabel("You can chose FOUR spells to use in next battle here.");
        intro1.setFont(new Font("Serif", Font.ITALIC, 18));
        intro2.setFont(new Font("Serif", Font.ITALIC, 18));
        for (int i = 0; i < archive.getUnlockedSpells().values().size(); i++) {
            spellButtons.add(new JButton(archive.getUnlockedSpells().get(i + 1).getSpellsName()));
        }
        toBattle = new JButton("Start Battle!");
        showSpells = new JLabel("Your chosen spells: ");
        showSpells.setFont(new Font("Serif", Font.ITALIC, 18));
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

    // Initializing buttons
    @Override
    public void initializeInteraction() {
        for (int i = 0; i < spellButtons.size(); i++) {
            Spell selectedSpell = archive.getUnlockedSpells().get(i + 1);
            String name = archive.getUnlockedSpells().get(i + 1).getSpellsName();
            int spellIndex = i;
            spellButtons.get(i).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (currBattle.spellToUse.size() == 4) {
                        spellsFull();
                    } else {
                        if (currBattle.spellToUse.containsValue(selectedSpell)) {
                            repeatedSelect();
                        } else {
                            currBattle.spellToUse.put(spellIndex,selectedSpell);
                            selectedDialog(name);
                            showSpells.setText(showSpells.getText() + selectedSpell.getSpellsName() + ", ");

                        }
                    }
                }
            });
        }

        toBattle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currBattle.spellToUse.size() < 4) {
                    continueSelectDialog();
                } else {
                    run.archive = archive;
                    archive.addBattle(currBattle);
                    GamePanel battleOnePanel = new BattlePanel(run, archive);
                    battleOnePanel.updatePanel();
                    run.setContentPane(battleOnePanel);
                    setVisible(false);
                    battleOnePanel.setVisible(true);
                    run.validate();
                }
            }
        });

    }

    // showing message when selecting less than four spells
    private void continueSelectDialog() {
        JOptionPane.showMessageDialog(this, "You must select FOUR spells! Please continue selection.");
    }

    // showing message when already selected four spells
    private void spellsFull() {
        JOptionPane.showMessageDialog(this, "You have already selected four spells! Please start your battle!");
    }

    // showing message after selecting one spell
    private void selectedDialog(String name) {
        JOptionPane.showMessageDialog(this, "You have selected spell:" + name);
    }

    // showing message when selecting one spell repeatedly
    private void repeatedSelect() {
        JOptionPane.showMessageDialog(this, "You have already selected this spell! Please choose another one");
    }


    // Add contents to the panel
    @Override
    public void addToPanel() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.ipady = 10;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(intro1, gbc);
        gbc.gridy = 1;
        add(intro2, gbc);
        gbc.gridy = 2;
        for (int i = 0; i < spellButtons.size(); i++) {
            gbc.gridy++;
            add(spellButtons.get(i), gbc);
        }
        gbc.gridy ++;
        add(toBattle, gbc);
        gbc.gridy ++;
        gbc.ipady = 30;
        add(showSpells, gbc);
    }

    @Override
    public void updatePanel() {
    }
}
