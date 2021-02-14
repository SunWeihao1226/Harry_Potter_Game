package ui.panels;

import model.Archive;
import model.Spell;
import ui.Run;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;

// REFERENCE: https://github.com/def-not-ys/BCS-Degree-Navigator
public class NewArchivePanel extends GamePanel {

    protected JList<Archive> archiveList;
    protected JScrollPane curUserPanel;
    private JButton newGame;
    private JButton continueGame;
    private JButton goBack;
    private JLabel label;
    private JLabel info;
    private JTextField archiveName;
    protected Archive selectedArchive;
    protected Archive curArchive;

    Spell stupefy = new Spell("Stupefy", 10);
    Spell expelliarmus = new Spell("Expelliarmus", 15);
    Spell petrificus = new Spell("Petrificus Totalus", 18);
    Spell obliviate = new Spell("Obliviate", 20);
    Spell reducto = new Spell("Reducto", 25);

    // MODIFIES: this
    // EFFECTS: Construct the panel
    public NewArchivePanel(Run run, Archive archive) {
        super(run, archive);
        initializeList();
        initializeContents();
        initializeInteraction();
        addToPanel();
    }

    // MODIFIES: this
    // EFFECTS: initialize the contents.
    @Override
    public void initializeContents() {
        updateSlot();
        curUserPanel = new JScrollPane(archiveList);
        curUserPanel.setPreferredSize(new Dimension(300, 600));
        curUserPanel.getHorizontalScrollBar().setPreferredSize(new Dimension(1, 1));
        newGame = new JButton("Create New Archive");
        goBack = new JButton("back");
        continueGame = new JButton("Continue");
        info = new JLabel("Please create your new archive, "
                 + "then select your new archive and double click continue.");
        label = new JLabel("Your existing archives: ");
        archiveName = new JTextField();
    }



    // MODIFIES : this
    // EFFECTS: initialize the list
    public void initializeList() {
        archiveList = new JList<>();
        updateSlot();
    }

    // MODIFIES: this
    // EFFECTS: update the slot
    public void updateSlot() {
        DefaultListModel<Archive> defaultListModel = new DefaultListModel<>();
        defaultListModel.removeAllElements();
        HashMap<String, Archive> archives = slot.getSlot();
        for (String str : archives.keySet()) {
            defaultListModel.addElement(archives.get(str));
        }
        try {
            archiveList.setModel(defaultListModel);
        } catch (NullPointerException e) {
            // throw exception
        }
        curArchive = archive;
    }

    // EFFECTS: initialize the interactions
    @Override
    public void initializeInteraction() {
        initializeListInteraction();
        initializeNewGameButton();
    }

    // MODIFIES: this
    // EFFECTS: initialize the buttons
    private void initializeNewGameButton() {
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    createNewArchive();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                updatePanel();
            }
        });
        continueGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!(selectedArchive == archive)) {
                    continueFailDialog();
                } else {
                    GamePanel selectWizardsPanel = new SelectWizardsPanel(run, archive);
                    archive = selectedArchive;
                    selectWizardsPanel.updatePanel();
                    run.setContentPane(selectWizardsPanel);
                    setVisible(false);
                    selectWizardsPanel.setVisible(true);
                    run.validate();
                }
            }
        });

        goBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GamePanel startPanel = new StartPanel(run, archive);
                startPanel.updatePanel();
                run.setContentPane(startPanel);
                setVisible(false);
                startPanel.setVisible(true);
                run.validate();
            }
        });
    }

    // EFFECTS: initialize the list interaction
    protected void initializeListInteraction() {
        ListSelectionListener listener = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                selectedArchive = archiveList.getSelectedValue();
            }
        };
        archiveList.addListSelectionListener(listener);
    }

    // EFFECTS: initialize the dialog window
    public void continueFailDialog() {
        JOptionPane.showMessageDialog(this,
                "Please create your archive or select your new archive.",
                "Fail to continue.", JOptionPane.PLAIN_MESSAGE);
    }

    // MODIFIES: this
    // EFFECTS: create new archives
    private void createNewArchive() throws IOException {
        Object[] message = {"Archive Name:", archiveName};
        int num = JOptionPane.showConfirmDialog(NewArchivePanel.this, message,
                "Create a new archive", JOptionPane.OK_CANCEL_OPTION);
        if (num == JOptionPane.OK_OPTION) {
            String archiveNameText = archiveName.getText();
            if (slot.getSlot().containsKey(archiveNameText)) {
                JOptionPane.showMessageDialog(this,
                        "This archive is already existed. Please enter another name.",
                        "existing archive", JOptionPane.PLAIN_MESSAGE);
                createNewArchive();
            } else {
                JOptionPane.showMessageDialog(this, "Your archive " + archiveNameText
                        + " is successful created", "successful create", JOptionPane.PLAIN_MESSAGE);
                archive = new Archive(archiveNameText);
                archive.setCheckPoint(0);
                run.archive = archive;
                initializeSpells();
                slot.addArchives(archive);
            }
        }
        updatePanel();
    }

    // MODIFIES: this
    // EFFECTS: initialize the spells
    public void initializeSpells() {
        super.archive.addSpells(1, stupefy);
        super.archive.addSpells(2, expelliarmus);
        super.archive.addSpells(3, petrificus);
        super.archive.addSpells(4, obliviate);
    }

    // MODIFIES: this
    // EFFECTS: add the components to the panel
    @Override
    public void addToPanel() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.insets = new Insets(20, 20, 0, 20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(label, gbc);

        gbc.insets = new Insets(0, 20, 20, 10);
        gbc.gridy = 1;
        gbc.gridheight = 100;
        add(curUserPanel, gbc);

        gbc.insets = new Insets(20, 40, 0, 20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        add(info, gbc);
        gbc.gridy = 2;
        add(newGame, gbc);
        gbc.gridy = 3;
        add(continueGame, gbc);
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.gridy = 4;
        add(goBack, gbc);

    }

    // MODIFIES: this
    // EFFECTS: update the panel.
    @Override
    public void updatePanel() {
        super.archive = run.archive;
        updateSlot();
        archiveName.setText("");
    }
}
